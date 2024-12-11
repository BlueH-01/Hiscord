package client.ui;

import javax.swing.*;
import java.awt.*;
import java.io.*;
import java.net.Socket;

public class MainFrame extends JFrame {
    private JTextArea chatArea;
    private JTextField chatInput;
    private PrintWriter out;
    private DefaultListModel<String> channelListModel;
    private JList<String> channelList;

    public MainFrame(String username) {
        setTitle("Chat - " + username);
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // 채팅 영역
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        add(new JScrollPane(chatArea), BorderLayout.CENTER);

        chatInput = new JTextField();
        add(chatInput, BorderLayout.SOUTH);

        // 채널 목록 패널
        JPanel channelPanel = new JPanel(new BorderLayout());
        channelListModel = new DefaultListModel<>();
        channelList = new JList<>(channelListModel);
        channelPanel.add(new JScrollPane(channelList), BorderLayout.CENTER);

        // 새 채널 추가 UI
        JPanel addChannelPanel = new JPanel(new BorderLayout());
        JTextField newChannelField = new JTextField();
        JButton addChannelButton = new JButton("Add Channel");
        addChannelPanel.add(newChannelField, BorderLayout.CENTER);
        addChannelPanel.add(addChannelButton, BorderLayout.EAST);
        channelPanel.add(addChannelPanel, BorderLayout.SOUTH);

        add(channelPanel, BorderLayout.WEST);

        try {
            // 서버와 소켓 연결
            Socket socket = new Socket("localhost", 12345);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);

            // 서버에 사용자 이름 전송
            out.println(username);

            // 서버로부터 메시지 수신
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

        } catch (IOException e) {
            e.printStackTrace();
        }

        // 채팅 메시지 전송
        chatInput.addActionListener(e -> {
            String message = chatInput.getText();
            if (!message.isEmpty()) {
                out.println(message);
                chatInput.setText("");
            }
        });

        // 채널 전환
        channelList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedChannel = channelList.getSelectedValue();
                if (selectedChannel != null) {
                    out.println("/join " + selectedChannel); // 서버로 채널 전환 요청
                    chatArea.setText(""); // 채팅 창 초기화
                }
            }
        });

        // 새 채널 추가
        addChannelButton.addActionListener(e -> {
            String newChannel = newChannelField.getText().trim();
            if (!newChannel.isEmpty() && !channelListModel.contains(newChannel)) {
                channelListModel.addElement(newChannel);
                newChannelField.setText("");
            }
        });

        // 기본 채널 추가
        addDefaultChannels();

        setVisible(true);
    }

    private void addDefaultChannels() {
        channelListModel.addElement("general");
        channelListModel.addElement("sports");
        channelListModel.addElement("technology");
        channelListModel.addElement("music");
    }
}
