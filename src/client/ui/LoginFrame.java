package client.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginFrame extends JFrame { //id username 프로필사진
    private JTextField usernameField;

    public LoginFrame() {
        setTitle("Hiscord");
        setSize(500, 400);  // 창 크기 키움
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);

        // 다크 테마 색상
        Color backgroundColor = new Color(54, 57, 63);
        Color textColor = new Color(220, 221, 222);
        Color buttonColor = new Color(88, 101, 242);
        
        // 전체 배경 색상
        getContentPane().setBackground(backgroundColor);

        // 제목 라벨
        JLabel titleLabel = new JLabel("Welcome to Hiscord");
        titleLabel.setBounds(50, 30, 400, 30);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(textColor);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel);

        // 유저네임 라벨
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 100, 80, 30);
        usernameLabel.setForeground(textColor);
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        add(usernameLabel);

        // 유저네임 입력 필드
        usernameField = new JTextField();
        usernameField.setBounds(140, 100, 200, 30);
        usernameField.setBackground(new Color(64, 68, 75));
        usernameField.setForeground(textColor);
        usernameField.setCaretColor(textColor);
        usernameField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(usernameField);

        // 로그인 버튼
        JButton loginButton = new JButton("Login");
        loginButton.setBounds(140, 160, 200, 40);
        loginButton.setBackground(buttonColor);
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        loginButton.setFocusPainted(false);
        loginButton.setBorder(BorderFactory.createEmptyBorder());
        add(loginButton);

        // 회원가입 버튼
        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(140, 220, 200, 40);  // 회원가입 버튼 위치 설정
        signUpButton.setBackground(new Color(60, 179, 113));  // 회원가입 버튼 색상
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setFont(new Font("Arial", Font.BOLD, 14));
        signUpButton.setFocusPainted(false);
        signUpButton.setBorder(BorderFactory.createEmptyBorder());
        add(signUpButton);

        // 버튼 이벤트 리스너
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String username = usernameField.getText();
                if (!username.isEmpty()) {
                    new MainFrame(username);
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(LoginFrame.this, "Please enter a username!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // 회원가입 버튼 이벤트 리스너
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 회원가입 화면을 띄우는 로직을 추가하세요
                // 예시로는 새로운 SignUpFrame을 열 수 있습니다.
                new SignUpFrame();  // 가상의 SignUpFrame 클래스를 호출
                dispose();  // 현재 로그인 창을 닫기
            }
        });

        setVisible(true);
    }
}
