// ChannelPanel.java
package client.ui;

import javax.swing.*;
import java.awt.*;
import java.io.PrintWriter;

public class ChannelPanel extends JPanel {
    private DefaultListModel<String> channelListModel;
    private JList<String> channelList;

    public ChannelPanel(PrintWriter out) {
        setLayout(new BorderLayout());
        setBackground(new Color(47, 49, 54));

        JLabel channelLabel = new JLabel("채널");
        channelLabel.setForeground(new Color(220, 221, 222));
        channelLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        add(channelLabel, BorderLayout.NORTH);

        channelListModel = new DefaultListModel<>();
        channelList = new JList<>(channelListModel);
        channelList.setBackground(new Color(47, 49, 54));
        channelList.setForeground(new Color(220, 221, 222));
        channelList.setSelectionForeground(Color.WHITE);

        JScrollPane channelScrollPane = new JScrollPane(channelList);
        channelScrollPane.setBorder(BorderFactory.createEmptyBorder());
        channelScrollPane.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        add(channelScrollPane, BorderLayout.CENTER);

        JPanel addChannelPanel = new JPanel(new BorderLayout());
        addChannelPanel.setBackground(new Color(47, 49, 54));

        JTextField newChannelField = new JTextField();
        newChannelField.setBackground(new Color(64, 68, 75));
        newChannelField.setForeground(new Color(220, 221, 222));
        newChannelField.setCaretColor(new Color(220, 221, 222));
        addChannelPanel.add(newChannelField, BorderLayout.CENTER);

        JButton addChannelButton = new JButton("+");
        addChannelButton.setBackground(new Color(88, 101, 242));
        addChannelButton.setForeground(Color.WHITE);
        addChannelButton.setFocusPainted(false);
        addChannelPanel.add(addChannelButton, BorderLayout.EAST);

        add(addChannelPanel, BorderLayout.SOUTH);

        // 새 채널 추가 이벤트
        addChannelButton.addActionListener(e -> {
            String newChannel = newChannelField.getText().trim();
            if (!newChannel.isEmpty() && !channelListModel.contains(newChannel)) {
                out.println("/addchannel " + newChannel);
                newChannelField.setText("");
            }
        });

        // 채널 전환 이벤트
        channelList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                String selectedChannel = channelList.getSelectedValue();
                if (selectedChannel != null) {
                    out.println("/join " + selectedChannel);
                }
            }
        });

        addDefaultChannels();
    }

    private void addDefaultChannels() {
        channelListModel.addElement("네프 회의");
        channelListModel.addElement("채팅 공부");
        channelListModel.addElement("웹만들기");
        channelListModel.addElement("한성대학교");
        channelListModel.addElement("환수월드");
        channelListModel.addElement("준선월드");
    }
}
