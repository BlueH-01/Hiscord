package client.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintWriter;

public class ChannelPanel extends JPanel {
    private DefaultListModel<String> channelListModel;
    private JList<String> channelList;
    private PrintWriter out;
    private ChatPanel chatPanel;

    public ChannelPanel(PrintWriter out, ChatPanel chatPanel) {
        this.out = out;
        this.chatPanel = chatPanel;

        setLayout(new BorderLayout());

        // 채널 목록 모델 및 JList
        channelListModel = new DefaultListModel<>();
        channelList = new JList<>(channelListModel);
        JScrollPane scrollPane = new JScrollPane(channelList);
        add(scrollPane, BorderLayout.CENTER);

        // 채널 전환 버튼
        JButton joinButton = new JButton("Join Channel");
        add(joinButton, BorderLayout.SOUTH);

        joinButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedChannel = channelList.getSelectedValue();
                if (selectedChannel != null) {
                    chatPanel.switchChannel(selectedChannel);
                }
            }
        });

        // 기본 채널 추가
        addDefaultChannels();
    }

    private void addDefaultChannels() {
        channelListModel.addElement("general");
        channelListModel.addElement("sports");
        channelListModel.addElement("technology");
        channelListModel.addElement("music");
    }

    public void addChannel(String channelName) {
        if (!channelListModel.contains(channelName)) {
            channelListModel.addElement(channelName);
        }
    }
}
