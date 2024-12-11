package client.ui;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame {
    private JTextField usernameField;

    public LoginFrame() {
        setTitle("Chat Login");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        JLabel usernameLabel = new JLabel("Enter Username:");
        usernameLabel.setBounds(20, 20, 100, 30);
        add(usernameLabel);

        usernameField = new JTextField();
        usernameField.setBounds(120, 20, 150, 30);
        add(usernameField);

        JButton loginButton = new JButton("Login");
        loginButton.setBounds(100, 70, 100, 30);
        add(loginButton);

        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                if (!username.isEmpty()) {
                    new MainFrame(username);
                    dispose();
                }
            }
        });

        setVisible(true);
    }
}
