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
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Discord color scheme
        Color backgroundColor = new Color(54, 57, 63);
        Color panelColor = new Color(47, 49, 54);
        Color textColor = new Color(220, 221, 222);
        Color buttonColor = new Color(88, 101, 242);
        Color inputColor = new Color(64, 68, 75);

        // 메인화면 배경색
        getContentPane().setBackground(backgroundColor);

        // 채팅 영역
        chatArea = new JTextArea();
        chatArea.setEditable(false);
        chatArea.setBackground(panelColor);
        chatArea.setForeground(textColor);
        chatArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JScrollPane chatScrollPane = new JScrollPane(chatArea);
        chatScrollPane.setBorder(BorderFactory.createEmptyBorder());
        add(chatScrollPane, BorderLayout.CENTER);

        // 채팅 입력 영역
        JPanel inputPanel = new JPanel(new BorderLayout());
        inputPanel.setBackground(panelColor);

        chatInput = new JTextField();
        chatInput.setBackground(inputColor);
        chatInput.setForeground(textColor);
        chatInput.setCaretColor(textColor);
        inputPanel.add(chatInput, BorderLayout.CENTER);

        JButton sendButton = new JButton("Send");
        sendButton.setBackground(buttonColor);
        sendButton.setForeground(Color.WHITE);
        sendButton.setFocusPainted(false);
        sendButton.setBorder(BorderFactory.createEmptyBorder());
        inputPanel.add(sendButton, BorderLayout.EAST);

        add(inputPanel, BorderLayout.SOUTH);

        // 채널 목록 패널
        JPanel channelPanel = new JPanel(new BorderLayout());
        channelPanel.setBackground(panelColor);

        JLabel channelLabel = new JLabel("채널");
        channelLabel.setForeground(textColor);
        channelLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        channelPanel.add(channelLabel, BorderLayout.NORTH);

        channelListModel = new DefaultListModel<>();
        channelList = new JList<>(channelListModel);
        channelList.setBackground(panelColor);
        channelList.setForeground(textColor);
        channelList.setSelectionForeground(Color.WHITE);
        channelList.setCellRenderer(new CircleCellRenderer());

        JScrollPane channelScrollPane = new JScrollPane(channelList);
        channelScrollPane.setBorder(BorderFactory.createEmptyBorder());
        channelScrollPane.getVerticalScrollBar().setOpaque(false);
        channelScrollPane.getVerticalScrollBar().setPreferredSize(new Dimension(0, 0));
        channelScrollPane.getHorizontalScrollBar().setPreferredSize(new Dimension(0, 0));

        // 스크롤바 동작은 유지
        channelScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        channelScrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        channelScrollPane.getVerticalScrollBar().setUnitIncrement(16); // 스크롤 속도 설정
       
        channelPanel.add(channelScrollPane, BorderLayout.CENTER);

        // 새 채널 추가 UI
        JPanel addChannelPanel = new JPanel(new BorderLayout());
        addChannelPanel.setBackground(panelColor);

        JTextField newChannelField = new JTextField();
        newChannelField.setBackground(inputColor);
        newChannelField.setForeground(textColor);
        newChannelField.setCaretColor(textColor);
        addChannelPanel.add(newChannelField, BorderLayout.CENTER);

        JButton addChannelButton = new JButton("채널 추가");
        addChannelButton.setBackground(buttonColor);
        addChannelButton.setForeground(Color.WHITE);
        addChannelButton.setFocusPainted(false);
        addChannelButton.setBorder(BorderFactory.createEmptyBorder());
        addChannelPanel.add(addChannelButton, BorderLayout.EAST);

        channelPanel.add(addChannelPanel, BorderLayout.SOUTH);
        add(channelPanel, BorderLayout.WEST);

        // 서버와 소켓 연결
        try {
            Socket socket = new Socket("localhost", 12345);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            out = new PrintWriter(socket.getOutputStream(), true);
            out.println(username);

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
                    out.println("/join " + selectedChannel);
                    chatArea.setText("");
                }
            }
        });

        // 새 채널 추가
        addChannelButton.addActionListener(e -> {
            String newChannel = newChannelField.getText().trim();
            if (!newChannel.isEmpty() && !channelListModel.contains(newChannel)) {
                out.println("/addchannel " + newChannel);
                newChannelField.setText("");
            }
        });

        // 기본 채널 추가
        addDefaultChannels();

        setVisible(true);
    }

    private void addDefaultChannels() {
        channelListModel.addElement("네프 회의");
        channelListModel.addElement("채팅 공부");
        channelListModel.addElement("웹만들기");
        channelListModel.addElement("한성대학교");
        channelListModel.addElement("환수월드");
        channelListModel.addElement("준선월드");
    }

    private class CircleCellRenderer extends DefaultListCellRenderer {

        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {

            JPanel panel = new JPanel() {

                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);

                    // 기본 배경
                    g.setColor(new Color(78, 84, 92)); // 회색 동그라미
                    g.fillOval(0, 0, getWidth(), getHeight());

                    // 선택된 경우의 배경
                    if (isSelected) {
                        g.setColor(new Color(88, 101, 242)); // 파란색 동그라미
                        g.fillOval(0, 0, getWidth(), getHeight());
                    }
                }
            };

            panel.setLayout(new BorderLayout());

            // 채널 이름을 표시하는 라벨
            JLabel label = new JLabel(value.toString(), SwingConstants.CENTER);
            label.setForeground(Color.WHITE);

            // 폰트 크기 및 굵기 변경
            label.setFont(new Font("맑은 고딕", Font.BOLD, 16));  // 글꼴은 Arial, 굵게, 크기는 16

            panel.add(label, BorderLayout.CENTER);
            panel.setPreferredSize(new Dimension(100, 100)); // 동그라미 크기 설정
            panel.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5)); // 여백 설정
            panel.setOpaque(false); // 투명하게 설정

            return panel;
        }
    }
}
