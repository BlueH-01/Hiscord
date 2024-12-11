package client.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

public class ChatPanel extends JPanel {
    private JTextArea chatArea;
    private JTextField chatInput;
    private PrintWriter out;
    private String currentChannel;

    public ChatPanel(PrintWriter out, BufferedReader in, String initialChannel) {
        this.out = out;
        this.currentChannel = initialChannel;

        setLayout(new BorderLayout());

        chatArea = new JTextArea();
        chatArea.setEditable(false);
        JScrollPane scrollPane = new JScrollPane(chatArea);
        add(scrollPane, BorderLayout.CENTER);

        chatInput = new JTextField();
        add(chatInput, BorderLayout.SOUTH);

        // 메시지 보내기
        chatInput.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String message = chatInput.getText();
                if (!message.isEmpty()) {
                    out.println(message);
                    chatInput.setText("");
                }
            }
        });

        // 서버로부터 받은 메시지 읽기
        new Thread(() -> {
            try {
                String message;
                while ((message = in.readLine()) != null) {
                    chatArea.append(message + "\n");
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }).start();
    }

    public void switchChannel(String newChannel) {
        this.currentChannel = newChannel;
        chatArea.setText(""); // 기존 메시지 초기화
        out.println("/join " + newChannel);
    }
}
