package cn.herbal.visualization.config;

import jakarta.annotation.PreDestroy;
import jakarta.websocket.*;
import jakarta.websocket.server.ServerEndpoint;
import lombok.extern.slf4j.Slf4j;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;
import java.util.Queue;
import java.util.UUID;
import java.util.concurrent.*;

@ServerEndpoint("/match")
@Component
@Slf4j
public class WsServerMatching {

    private static final Map<String, Session> users = new ConcurrentHashMap<>();
    private static final Map<String, String> userRoles = new ConcurrentHashMap<>();
    private static final Map<Session, String> sessionToUserId = new ConcurrentHashMap<>();

    private static final Queue<String> hostQueue = new ConcurrentLinkedQueue<>();
    private static final Queue<String> clientQueue = new ConcurrentLinkedQueue<>();
    private static final Map<String, MatchPair> matchPairs = new ConcurrentHashMap<>();

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);
    private final Map<String, ScheduledFuture<?>> pendingMatches = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session) {
        String userId = UUID.randomUUID().toString();
        users.put(userId, session);
        sessionToUserId.put(session, userId);
        log.info("用户连接: {}, 总在线人数: {}", session.getId(), users.size());
    }

    @OnMessage
    public void onMessage(String message, Session session) {
        String userId = sessionToUserId.get(session);
        log.info("原始消息内容: {}", message);
        if (userId == null) {
            log.warn("未识别的会话接收到消息: {}", session.getId());
            return;
        }

        log.debug("用户 {} 发送消息: {}", userId, message);
        log.info("接收到来自用户 {} 的消息: {}", userId, message);

        try {
            JSONObject jsonMessage = new JSONObject(message);
            String action = jsonMessage.getString("action");

            if ("choose_host".equalsIgnoreCase(action)) {
                handleRoleSelection(userId, "HOST", session);
            } else if ("choose_client".equalsIgnoreCase(action)) {
                handleRoleSelection(userId, "CLIENT", session);
            } else if ("switch_role".equalsIgnoreCase(action)) {
                handleRoleSwitch(userId, session);
            } else if ("herb_selected".equals(action)) {
                handleHerbNameMessage(userId, message);
            }
        } catch (JSONException e) {
            try {
                String command = message.trim().toLowerCase();
                if ("choose_host".equals(command)) {
                    handleRoleSelection(userId, "HOST", session);
                } else if ("choose_client".equals(command)) {
                    handleRoleSelection(userId, "CLIENT", session);
                } else if ("switch_role".equals(command)) {
                    handleRoleSwitch(userId, session);
                } else {
                    String role = userRoles.get(userId);
                    if ("HOST".equals(role)) {
                        JSONObject herbMessage = new JSONObject();
                        herbMessage.put("action", "herb_selected");
                        herbMessage.put("herbName", message);
                        handleHerbNameMessage(userId, herbMessage.toString());
                    }
                }
            } catch (Exception ex) {
                log.error("处理简单命令时发生错误: {}", ex.getMessage(), ex);
            }
        } catch (Exception e) {
            log.error("处理消息时发生错误: {}", e.getMessage(), e);
        }
    }

    private void handleRoleSelection(String userId, String role, Session session) {
        if (userRoles.containsKey(userId)) {
            log.warn("用户 {} 已选择角色: {}", userId, userRoles.get(userId));
            sendMessage(session, "error", "你已经选择了角色，不能重复选择");
            return;
        }

        userRoles.put(userId, role);
        if ("HOST".equals(role)) {
            hostQueue.add(userId);
            log.info("用户 {} 加入主机队列，当前主机队列大小: {}", userId, hostQueue.size());
        } else {
            clientQueue.add(userId);
            log.info("用户 {} 加入客户端队列，当前客户端队列大小: {}", userId, clientQueue.size());
        }

        sendMessage(session, "role_selected", "你已选择" + (role.equals("HOST") ? "主机" : "客户端") + "角色");
        tryToMatchUsers();
    }

    private void handleRoleSwitch(String userId, Session session) {
        String currentRole = userRoles.get(userId);
        if (currentRole == null) {
            log.warn("用户 {} 尚未选择角色", userId);
            sendMessage(session, "error", "你还没有选择角色");
            return;
        }

        if ("HOST".equals(currentRole)) {
            hostQueue.remove(userId);
        } else {
            clientQueue.remove(userId);
        }

        String newRole = "HOST".equals(currentRole) ? "CLIENT" : "HOST";
        userRoles.put(userId, newRole);

        if ("HOST".equals(newRole)) {
            hostQueue.add(userId);
        } else {
            clientQueue.add(userId);
        }

        sendMessage(session, "role_switched", "你已切换到" + (newRole.equals("HOST") ? "主机" : "客户端") + "角色");
        tryToMatchUsers();
    }

    void handleHerbNameMessage(String hostId, String message) {
        try {
            JSONObject jsonMessage = new JSONObject(message);
            String herbName = jsonMessage.getString("herbName");

            MatchPair match = matchPairs.get(hostId);
            if (match == null || !match.getHostId().equals(hostId)) {
                log.warn("未找到用户 {} 的匹配信息", hostId);
                return;
            }

            String clientId = match.getClientId();
            Session clientSession = users.get(clientId);
            if (clientSession == null || !clientSession.isOpen()) {
                log.warn("客户端 {} 会话不存在或已关闭", clientId);
                return;
            }

            String outMessage = String.format("{\"action\":\"herb_selected\",\"herbName\":\"%s\"}", herbName);
            clientSession.getBasicRemote().sendText(outMessage);
            log.info("已将药材 {} 发送给客户端 {}，消息内容: {}", herbName, clientId, outMessage);
        } catch (JSONException | IOException e) {
            log.error("处理药材名称消息失败: {}", e.getMessage(), e);
        }
    }

    @OnClose
    public void onClose(Session session) {
        String userId = sessionToUserId.remove(session);
        if (userId == null) {
            log.warn("未识别的会话关闭: {}", session.getId());
            return;
        }

        log.info("用户 {} 断开连接，会话ID: {}", userId, session.getId());

        ScheduledFuture<?> future = pendingMatches.remove(userId);
        if (future != null) {
            future.cancel(false);
        }

        String role = userRoles.remove(userId);
        if ("HOST".equals(role)) {
            hostQueue.remove(userId);
        } else if ("CLIENT".equals(role)) {
            clientQueue.remove(userId);
        }

        matchPairs.entrySet().removeIf(entry ->
                entry.getValue().getHostId().equals(userId) || entry.getValue().getClientId().equals(userId));

        users.remove(userId);
        tryToMatchUsers();
    }

    @OnError
    public void onError(Session session, Throwable error) {
        String userId = sessionToUserId.get(session);
        log.error("用户 {} 会话 {} 发生错误: {}",
                userId != null ? userId : "未知",
                session.getId(),
                error.getMessage(),
                error);
    }

    private void tryToMatchUsers() {
        log.info("尝试匹配用户，主机队列大小: {}, 客户端队列大小: {}", hostQueue.size(), clientQueue.size());

        hostQueue.removeIf(matchPairs::containsKey);
        clientQueue.removeIf(matchPairs::containsKey);

        while (!hostQueue.isEmpty() && !clientQueue.isEmpty()) {
            String hostId = hostQueue.poll();
            String clientId = clientQueue.poll();

            if (hostId != null && clientId != null &&
                    !matchPairs.containsKey(hostId) && !matchPairs.containsKey(clientId)) {

                MatchPair match = new MatchPair(hostId, clientId);
                matchPairs.put(hostId, match);
                matchPairs.put(clientId, match);

                log.info("匹配成功: 主机 {} 和客户端 {}", hostId, clientId);
                sendMatchInfo(hostId, true);
                sendMatchInfo(clientId, false);
            }
        }

        schedulePendingMatches(hostQueue);
        schedulePendingMatches(clientQueue);
    }

    private void schedulePendingMatches(Queue<String> queue) {
        queue.forEach(userId -> {
            if (!pendingMatches.containsKey(userId)) {
                ScheduledFuture<?> future = scheduler.schedule(() -> {
                    try {
                        if ((userRoles.get(userId).equals("HOST") && hostQueue.contains(userId)) ||
                                (userRoles.get(userId).equals("CLIENT") && clientQueue.contains(userId))) {
                            if (hostQueue.remove(userId)) {
                                hostQueue.add(userId);
                            } else if (clientQueue.remove(userId)) {
                                clientQueue.add(userId);
                            }
                            tryToMatchUsers();
                        }
                    } catch (Exception e) {
                        log.error("处理用户 {} 匹配任务时出错: {}", userId, e.getMessage(), e);
                    }
                }, 5, TimeUnit.SECONDS);

                pendingMatches.put(userId, future);
            }
        });
    }

    private void sendMatchInfo(String userId, boolean isHost) {
        Session session = users.get(userId);
        if (session == null || !session.isOpen()) {
            return;
        }

        try {
            MatchPair match = matchPairs.get(userId);
            String role = isHost ? "HOST" : "CLIENT";
            String opponentId = isHost ? match.getClientId() : match.getHostId();

            String message = String.format("{\"action\":\"matched\",\"role\":\"%s\",\"opponentId\":\"%s\"}",
                    role, opponentId);

            session.getBasicRemote().sendText(message);
            log.info("已将匹配信息发送给用户 {}，消息内容: {}", userId, message);
        } catch (Exception e) {
            log.error("发送匹配信息给用户 {} 失败: {}", userId, e.getMessage(), e);
        }
    }

    private void sendMessage(Session session, String action, String content) {
        try {
            String message = String.format("{\"action\":\"%s\",\"content\":\"%s\"}", action, content);
            session.getBasicRemote().sendText(message);
            log.info("已向用户 {} 发送消息，消息内容: {}", sessionToUserId.get(session), message);
        } catch (IOException e) {
            log.error("发送消息失败: {}", e.getMessage(), e);
        }
    }

    @PreDestroy
    public void cleanup() {
        log.info("清理WebSocket服务器资源...");
        scheduler.shutdownNow();
        try {
            if (!scheduler.awaitTermination(10, TimeUnit.SECONDS)) {
                log.warn("调度器未能在指定时间内关闭");
            }
        } catch (InterruptedException e) {
            log.error("等待调度器关闭时被中断", e);
            Thread.currentThread().interrupt();
        }
        log.info("WebSocket服务器资源清理完成");
    }

    static class MatchPair {
        private final String hostId;
        private final String clientId;

        public MatchPair(String hostId, String clientId) {
            this.hostId = hostId;
            this.clientId = clientId;
        }

        public String getHostId() {
            return hostId;
        }

        public String getClientId() {
            return clientId;
        }
    }
}