package client.ui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;

public class SignUpFrame extends JFrame {
    private JTextField nameField, usernameField;
    private JPasswordField passwordField;

    public SignUpFrame() {
        setTitle("Sign Up");
        setSize(400, 300);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(null);

        // 다크 테마 색상
        Color backgroundColor = new Color(54, 57, 63);
        Color textColor = new Color(220, 221, 222);
        Color buttonColor = new Color(88, 101, 242);

        // 전체 배경 색상
        getContentPane().setBackground(backgroundColor);

        // 제목 라벨
        JLabel titleLabel = new JLabel("Create Your Account");
        titleLabel.setBounds(50, 30, 300, 30);
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);
        titleLabel.setForeground(textColor);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        add(titleLabel);

        // 이름 라벨
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setBounds(50, 80, 80, 30);
        nameLabel.setForeground(textColor);
        nameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        add(nameLabel);

        // 이름 입력 필드
        nameField = new JTextField();
        nameField.setBounds(140, 80, 200, 30);
        nameField.setBackground(new Color(64, 68, 75));
        nameField.setForeground(textColor);
        nameField.setCaretColor(textColor);
        nameField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(nameField);

        // 아이디 라벨
        JLabel usernameLabel = new JLabel("Username:");
        usernameLabel.setBounds(50, 120, 80, 30);
        usernameLabel.setForeground(textColor);
        usernameLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        add(usernameLabel);

        // 아이디 입력 필드
        usernameField = new JTextField();
        usernameField.setBounds(140, 120, 200, 30);
        usernameField.setBackground(new Color(64, 68, 75));
        usernameField.setForeground(textColor);
        usernameField.setCaretColor(textColor);
        usernameField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(usernameField);

        // 비밀번호 라벨
        JLabel passwordLabel = new JLabel("Password:");
        passwordLabel.setBounds(50, 160, 80, 30);
        passwordLabel.setForeground(textColor);
        passwordLabel.setFont(new Font("Arial", Font.PLAIN, 14));
        add(passwordLabel);

        // 비밀번호 입력 필드
        passwordField = new JPasswordField();
        passwordField.setBounds(140, 160, 200, 30);
        passwordField.setBackground(new Color(64, 68, 75));
        passwordField.setForeground(textColor);
        passwordField.setCaretColor(textColor);
        passwordField.setBorder(BorderFactory.createEmptyBorder(5, 5, 5, 5));
        add(passwordField);

        // 회원가입 버튼
        JButton signUpButton = new JButton("Sign Up");
        signUpButton.setBounds(140, 200, 200, 40);
        signUpButton.setBackground(buttonColor);
        signUpButton.setForeground(Color.WHITE);
        signUpButton.setFont(new Font("Arial", Font.BOLD, 14));
        signUpButton.setFocusPainted(false);
        signUpButton.setBorder(BorderFactory.createEmptyBorder());
        add(signUpButton);

        // 회원가입 버튼 이벤트 리스너
        signUpButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String name = nameField.getText();
                String username = usernameField.getText();
                String password = new String(passwordField.getPassword());

                // 입력값 확인
                if (name.isEmpty() || username.isEmpty() || password.isEmpty()) {
                    JOptionPane.showMessageDialog(SignUpFrame.this, "Please fill all fields.", "Error", JOptionPane.ERROR_MESSAGE);
                } else {
                    // 사용자 정보 저장
                    saveUserInfo(name, username, password);
                    JOptionPane.showMessageDialog(SignUpFrame.this, "Sign Up Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
                    dispose(); // 회원가입 후 창 닫기
                    new LoginFrame(); // 로그인 프레임 열기
                }
            }
        });

        setVisible(true);
    }

    // 사용자 정보를 Hiscord/resources/user.txt 파일에 저장하는 메서드
    private void saveUserInfo(String name, String username, String password) {
        try {
            // 현재 디렉토리 출력
            String currentDir = System.getProperty("user.dir");
            String path = currentDir + File.separator + "resources" + File.separator + "user.txt";

            
            File dir = new File("Hiscord/resources");

            // 디렉토리가 없으면 생성
            if (!dir.exists()) {
                dir.mkdirs();
                System.out.println("디렉토리 생성됨: " + dir.getAbsolutePath());
            }

            // 파일을 덧붙이기 모드로 열기
            BufferedWriter writer = new BufferedWriter(new FileWriter(path, true));
            writer.write(name + "," + username + "," + password);
            writer.newLine();  // 새 줄 추가
            writer.close();

            System.out.println("파일에 저장됨: " + path);
        } catch (IOException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving user info!", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }



}
