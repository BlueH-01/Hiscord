package server;

import java.util.*;

public class ChannelManager {
    private static Map<String, List<ClientHandler>> channels = new HashMap<>();

    public static synchronized void joinChannel(String channel, ClientHandler client) {
        channels.putIfAbsent(channel, new ArrayList<>());
        channels.get(channel).add(client);
        broadcast(channel, "User joined: " + client.username);
    }

    public static synchronized void leaveChannel(String channel, ClientHandler client) {
        if (channels.containsKey(channel)) {
            channels.get(channel).remove(client);
            broadcast(channel, "User left: " + client.username);
        }
    }

    public static synchronized void broadcast(String channel, String message) {
        if (channels.containsKey(channel)) {
            for (ClientHandler client : channels.get(channel)) {
                client.sendMessage(message);
            }
        }
    }
}
