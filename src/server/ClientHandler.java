package server;

import java.io.*;
import java.net.Socket;
import java.util.*;

public class ClientHandler implements Runnable {
    private Socket socket;
    private BufferedReader in;
    private PrintWriter out;
    String username;
    private String currentChannel = "general";

    public ClientHandler(Socket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {
        try {
            in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // Handle login
            out.println("Enter your username:");
            username = in.readLine();
            ChannelManager.joinChannel(currentChannel, this);
            out.println("Welcome to the chat, " + username + "!");

            String message;
            while ((message = in.readLine()) != null) {
                if (message.startsWith("/join ")) {
                    String newChannel = message.split(" ")[1];
                    ChannelManager.leaveChannel(currentChannel, this);
                    currentChannel = newChannel;
                    ChannelManager.joinChannel(currentChannel, this);
                    out.println("Switched to channel: " + currentChannel);
                } else {
                    ChannelManager.broadcast(currentChannel, username + ": " + message);
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                socket.close();
                ChannelManager.leaveChannel(currentChannel, this);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public void sendMessage(String message) {
        out.println(message);
    }
}
