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
    private static final Map<String, MatchGroup> matchGroups = new ConcurrentHashMap<>();

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

        sendMessage(session, "role_selected", "你已选择" + (role.equals("HOST")? "主机" : "客户端") + "角色");
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

        String newRole = "HOST".equals(currentRole)? "CLIENT" : "HOST";
        userRoles.put(userId, newRole);

        if ("HOST".equals(newRole)) {
            hostQueue.add(userId);
        } else {
            clientQueue.add(userId);
        }

        sendMessage(session, "role_switched", "你已切换到" + (newRole.equals("HOST")? "主机" : "客户端") + "角色");
        tryToMatchUsers();
    }

    void handleHerbNameMessage(String hostId, String message) {
        if (hostId == null) {
            log.error("主机ID为空，无法处理药材名称消息");
            return;
        }

        try {
            JSONObject jsonMessage = new JSONObject(message);
            String herbName = jsonMessage.getString("herbName");

            MatchGroup match = matchGroups.get(hostId);
            if (match == null) {
                log.warn("未找到用户 {} 的匹配信息", hostId);
                return;
            }

            String client1Id = match.getClient1Id();
            String client2Id = match.getClient2Id();

            if (client1Id == null) {
                log.warn("未找到用户 {} 的从机1信息", hostId);
                return;
            }

            Session client1Session = users.get(client1Id);
            Session client2Session = client2Id != null ? users.get(client2Id) : null;

            String outMessage = String.format("{\"action\":\"herb_selected\",\"herbName\":\"%s\"}", herbName);

            // 发送给第一个从机
            if (client1Session != null && client1Session.isOpen()) {
                client1Session.getBasicRemote().sendText(outMessage);
                log.info("已将药材 {} 发送给从机1 {}", herbName, client1Id);
            }

            // 如果存在第二个从机，也发送消息
            if (client2Session != null && client2Session.isOpen()) {
                client2Session.getBasicRemote().sendText(outMessage);
                log.info("已将药材 {} 发送给从机2 {}", herbName, client2Id);
            }

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

        // 移除包含该用户的匹配组
        matchGroups.entrySet().removeIf(entry -> {
            MatchGroup group = entry.getValue();
            return group.getHostId().equals(userId) ||
                    group.getClient1Id().equals(userId) ||
                    (group.getClient2Id() != null && group.getClient2Id().equals(userId));
        });

        users.remove(userId);
        tryToMatchUsers();
    }

    @OnError
    public void onError(Session session, Throwable error) {
        String userId = sessionToUserId.get(session);
        log.error("用户 {} 会话 {} 发生错误: {}",
                userId != null? userId : "未知",
                session.getId(),
                error.getMessage(),
                error);
    }

    private void tryToMatchUsers() {
        log.info("尝试匹配用户，主机队列大小: {}, 客户端队列大小: {}", hostQueue.size(), clientQueue.size());

        hostQueue.removeIf(matchGroups::containsKey);
        clientQueue.removeIf(userId -> matchGroups.containsKey(userId) ||
                matchGroups.values().stream().anyMatch(g -> g.getClient2Id() != null && g.getClient2Id().equals(userId)));

        // 首先处理主机和客户端的一对一匹配
        while (!hostQueue.isEmpty() && !clientQueue.isEmpty()) {
            String hostId = hostQueue.poll();
            String clientId = clientQueue.poll();

            if (hostId != null && clientId != null &&
                    !matchGroups.containsKey(hostId) &&!matchGroups.containsKey(clientId)) {

                MatchGroup match = new MatchGroup(hostId, clientId, null);
                matchGroups.put(hostId, match);
                matchGroups.put(clientId, match);

                log.info("匹配成功: 主机 {} 和客户端 {}", hostId, clientId);
                sendMatchInfo(hostId, clientId, null);
            }
        }

        // 然后尝试将额外的客户端添加到已有匹配组中
        if (!clientQueue.isEmpty()) {
            String extraClientId = clientQueue.poll();
            if (extraClientId != null && !matchGroups.containsKey(extraClientId)) {
                // 查找可以添加第二个客户端的匹配组
                MatchGroup groupToAdd = matchGroups.values().stream()
                        .filter(g -> g.getClient2Id() == null)
                        .findFirst()
                        .orElse(null);

                if (groupToAdd != null) {
                    groupToAdd.setClient2Id(extraClientId);
                    matchGroups.put(extraClientId, groupToAdd);

                    log.info("添加额外客户端成功: 主机 {}，客户端 {} 和 {}",
                            groupToAdd.getHostId(), groupToAdd.getClient1Id(), extraClientId);

                    // 通知所有三方
                    sendMatchInfo(groupToAdd.getHostId(), groupToAdd.getClient1Id(), extraClientId);
                } else {
                    // 没有合适的组可以添加，将客户端放回队列
                    clientQueue.add(extraClientId);
                }
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

    private void sendMatchInfo(String hostId, String client1Id, String client2Id) {
        try {
            // 发送给主机
            Session hostSession = users.get(hostId);
            if (hostSession != null && hostSession.isOpen()) {
                String message = createMatchMessage("HOST", client1Id, client2Id);
                hostSession.getBasicRemote().sendText(message);
                log.info("已将匹配信息发送给主机 {}，消息内容: {}", hostId, message);
            }

            // 发送给第一个客户端
            Session client1Session = users.get(client1Id);
            if (client1Session != null && client1Session.isOpen()) {
                String message = createMatchMessage("CLIENT", hostId, client2Id);
                client1Session.getBasicRemote().sendText(message);
                log.info("已将匹配信息发送给客户端 {}，消息内容: {}", client1Id, message);
            }

            // 如果有第二个客户端，发送给第二个客户端
            if (client2Id != null) {
                Session client2Session = users.get(client2Id);
                if (client2Session != null && client2Session.isOpen()) {
                    String message = createMatchMessage("CLIENT", hostId, client1Id);
                    client2Session.getBasicRemote().sendText(message);
                    log.info("已将匹配信息发送给客户端 {}，消息内容: {}", client2Id, message);
                }
            }
        } catch (Exception e) {
            log.error("发送匹配信息失败: {}", e.getMessage(), e);
        }
    }

    private String createMatchMessage(String role, String opponent1Id, String opponent2Id) {
        try {
            JSONObject message = new JSONObject();
            message.put("action", "matched");
            message.put("role", role);
            message.put("opponent1Id", opponent1Id);

            if (opponent2Id != null) {
                message.put("opponent2Id", opponent2Id);
            }

            return message.toString();
        } catch (JSONException e) {
            log.error("创建匹配消息失败: {}", e.getMessage(), e);
            return String.format("{\"action\":\"matched\",\"role\":\"%s\",\"opponent1Id\":\"%s\"}",
                    role, opponent1Id);
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

    static class MatchGroup {
        private final String hostId;
        private final String client1Id;
        private String client2Id;

        public MatchGroup(String hostId, String client1Id, String client2Id) {
            this.hostId = hostId;
            this.client1Id = client1Id;
            this.client2Id = client2Id;
        }

        public String getHostId() {
            return hostId;
        }

        public String getClient1Id() {
            return client1Id;
        }

        public String getClient2Id() {
            return client2Id;
        }

        public void setClient2Id(String client2Id) {
            this.client2Id = client2Id;
        }
    }
}