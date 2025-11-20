package handler;

import io.netty.channel.Channel;
import lombok.Data;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 共享状态处理器，用于在多个NettyServerHandler实例之间共享状态
 */
@Data
public class SharedStateHandler {
    // 使用单例模式确保所有处理器实例共享同一个状态
    private static final SharedStateHandler INSTANCE = new SharedStateHandler();

    // 在线的客户端
    private final ConcurrentHashMap<String, Channel> online = new ConcurrentHashMap<>();

    // 相互绑定的两个客户端
    private final ConcurrentHashMap<String, String> bindMap = new ConcurrentHashMap<>();

    private SharedStateHandler() {
    }

    public static SharedStateHandler getInstance() {
        return INSTANCE;
    }

    public void addToOnline(String clientId, Channel channel) {
        online.put(clientId, channel);
    }

    public void removeFromOnline(String clientId) {
        online.remove(clientId);
    }

    public Channel getChannel(String clientId) {
        return online.get(clientId);
    }

    public void bind(String from, String to) {
        bindMap.put(from, to);
    }

    public void unbind(String from) {
        bindMap.remove(from);
    }

    public String getBindTarget(String from) {
        return bindMap.get(from);
    }

    public boolean isOnline(String clientId) {
        return online.containsKey(clientId);
    }

    public void removeChannel(Channel channel) {
        // 找到对应的客户端ID
        String clientId = online.entrySet().stream()
                .filter(entry -> entry.getValue() == channel)
                .map(Map.Entry::getKey)
                .findFirst()
                .orElse(null);

        if (clientId != null) {
            online.remove(clientId);
            bindMap.remove(clientId);
        }
    }
}