package cn.herbal.visualization.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicBoolean;

@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {

    private static final Logger logger = LoggerFactory.getLogger(WebSocketConfig.class);

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(deviceHandler(), "/ws/device")
                .setAllowedOrigins("*");
    }

    @Bean
    public DeviceHandler deviceHandler() {
        return new DeviceHandler();
    }

    public static class DeviceHandler extends TextWebSocketHandler {
        private static final Map<String, WebSocketSession> clients = new ConcurrentHashMap<>();
        private static final Map<String, String> deviceTypeMap = new ConcurrentHashMap<>();
        private static final Map<String, DeviceGroup> deviceGroups = new ConcurrentHashMap<>();
        private static final long HEARTBEAT_INTERVAL = 30000;
        private static final String HEARTBEAT_MESSAGE = "HEARTBEAT";
        private static final String HEARTBEAT_ACK = "HEARTBEAT_ACK";
        private final ObjectMapper objectMapper = new ObjectMapper();

        @Override
        public void afterConnectionEstablished(WebSocketSession session) {
            String clientId = session.getId();
            clients.put(clientId, session);
            startHeartbeatTask(session);
            logger.info("Client connected: {}", clientId);
        }

        private void startHeartbeatTask(WebSocketSession session) {
            String clientId = session.getId();
            ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
            AtomicBoolean isRunning = new AtomicBoolean(true);

            scheduler.scheduleAtFixedRate(() -> {
                if (!isRunning.get()) {
                    scheduler.shutdown();
                    return;
                }

                try {
                    if (session.isOpen()) {
                        synchronized (session) {
                            session.sendMessage(new TextMessage(HEARTBEAT_MESSAGE));
                        }
                    } else {
                        isRunning.set(false);
                        closeSession(session);
                    }
                } catch (IOException e) {
                    logger.error("Error sending heartbeat to client {}: {}", clientId, e.getMessage());
                    isRunning.set(false);
                    closeSession(session);
                }
            }, HEARTBEAT_INTERVAL, HEARTBEAT_INTERVAL, TimeUnit.MILLISECONDS);
        }

        @Override
        protected void handleTextMessage(WebSocketSession session, TextMessage message) {
            String payload = message.getPayload();

            if ("HEARTBEAT".equals(payload)) {
                try {
                    synchronized (session) {
                        session.sendMessage(new TextMessage("HEARTBEAT_ACK"));
                    }
                } catch (IOException e) {
                    logger.error("Error sending heartbeat ack to client {}: {}", session.getId(), e.getMessage());
                    closeSession(session);
                }
                return;
            }

            if ("HEARTBEAT_ACK".equals(payload)) {
                return;
            }

            try {
                Map<String, String> json = objectMapper.readValue(payload, Map.class);
                String deviceType = json.get("deviceType");
                String deviceId = json.get("deviceId");
                String pageName = json.get("pageName");

                if (deviceType != null && deviceId != null) {
                    deviceTypeMap.put(deviceId, deviceType);
                    logger.info("Device {} registered as {}", deviceId, deviceType);
                    checkAndJoinDeviceGroup(deviceId, deviceType, pageName);
                }
            } catch (IOException e) {
                logger.error("Error parsing message from client {}: {}", session.getId(), e.getMessage());
            }
        }

        private void checkAndJoinDeviceGroup(String deviceId, String deviceType, String pageName) {
            for (Map.Entry<String, DeviceGroup> entry : deviceGroups.entrySet()) {
                DeviceGroup group = entry.getValue();
                if (group.canJoin(deviceType)) {
                    group.addDevice(deviceId, deviceType);
                    if ("host".equals(deviceType) && pageName != null) {
                        forwardMessageToSlave(group, pageName);
                    }
                    return;
                }
            }

            DeviceGroup newGroup = new DeviceGroup();
            newGroup.addDevice(deviceId, deviceType);
            deviceGroups.put(deviceId, newGroup);
        }

        private void forwardMessageToSlave(DeviceGroup group, String pageName) {
            String slaveId = group.getSlaveId();
            if (slaveId != null) {
                WebSocketSession slaveSession = clients.get(slaveId);
                if (slaveSession != null && slaveSession.isOpen()) {
                    try {
                        synchronized (slaveSession) {
                            slaveSession.sendMessage(new TextMessage(pageName));
                        }
                    } catch (IOException e) {
                        logger.error("Error forwarding message to slave {}: {}", slaveId, e.getMessage());
                    }
                }
            }
        }

        @Override
        public void afterConnectionClosed(WebSocketSession session, org.springframework.web.socket.CloseStatus status) {
            String clientId = session.getId();
            clients.remove(clientId);
            deviceTypeMap.entrySet().removeIf(entry -> entry.getKey().equals(clientId));

            for (Map.Entry<String, DeviceGroup> entry : deviceGroups.entrySet()) {
                DeviceGroup group = entry.getValue();
                if (group.removeDevice(clientId)) {
                    if (group.isEmpty()) {
                        deviceGroups.remove(entry.getKey());
                    }
                    break;
                }
            }

            logger.info("Client disconnected: {}", clientId);
        }

        private void closeSession(WebSocketSession session) {
            String clientId = session.getId();
            try {
                session.close();
            } catch (IOException e) {
                logger.error("Error closing session for client {}: {}", clientId, e.getMessage());
            }
            afterConnectionClosed(session, null);
        }

        private static class DeviceGroup {
            private String hostId;
            private String slaveId;

            public boolean canJoin(String deviceType) {
                if ("host".equals(deviceType) && slaveId != null) {
                    return true;
                } else if ("slave".equals(deviceType) && hostId != null) {
                    return true;
                }
                return false;
            }

            public void addDevice(String deviceId, String deviceType) {
                if ("host".equals(deviceType)) {
                    hostId = deviceId;
                } else if ("slave".equals(deviceType)) {
                    slaveId = deviceId;
                }
            }

            public boolean removeDevice(String deviceId) {
                if (hostId != null && hostId.equals(deviceId)) {
                    hostId = null;
                } else if (slaveId != null && slaveId.equals(deviceId)) {
                    slaveId = null;
                }
                return isEmpty();
            }

            public boolean isEmpty() {
                return hostId == null && slaveId == null;
            }

            public String getSlaveId() {
                return slaveId;
            }
        }
    }
}