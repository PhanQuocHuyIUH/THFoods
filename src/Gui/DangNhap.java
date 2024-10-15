package Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DangNhap extends JFrame implements ActionListener {
    private JButton loginButton;
    private  JPasswordField passwordText;
    private JTextField userText;
    public DangNhap() {
        // Tạo JPanel chính với BorderLayout
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Thêm hình ảnh vào giao diện (bên trái)
        JLabel imageLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon("D:\\Project-java\\THFoods\\src\\IMG\\logIn.jpg"); // Đường dẫn tới ảnh
        Image image = imageIcon.getImage();
        Image imageScale = image.getScaledInstance(600, 900, Image.SCALE_SMOOTH); // Thay đổi kích thước ảnh
        ImageIcon scaledIcon = new ImageIcon(imageScale);
        imageLabel.setIcon(scaledIcon);
        panel.add(imageLabel, BorderLayout.WEST); // Đặt ảnh ở phía trái

        // Tạo JPanel chứa form đăng nhập (bên phải) và set màu nền
        JPanel formPanel = new JPanel();
        formPanel.setBackground(new Color(173, 216, 230)); // Màu xanh da trời nhạt
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS)); // Sử dụng BoxLayout theo chiều dọc

        // Tạo tile
        formPanel.add(Box.createRigidArea(new Dimension(0, 100))); // Tạo khoảng cách dưới tiêu đề
        JLabel brand = new JLabel("THFOODS");
        brand.setForeground(Color.WHITE);
        brand.setFont(new Font("Arial", Font.BOLD, 60));
        brand.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(brand);
        formPanel.add(Box.createRigidArea(new Dimension(0, 100))); // Tạo khoảng cách dưới tiêu đề
        JLabel formTitle = new JLabel("LOGIN");
        formTitle.setFont(new Font("Arial", Font.BOLD, 40));
        formTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(formTitle);
        formPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Tạo khoảng cách dưới tiêu đề
        // Tạo nhãn và trường nhập cho username
        JLabel userLabel = new JLabel("Username:");
        userText = new JTextField();
        userLabel.setFont(new Font("Arial", Font.BOLD, 20));
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        userText.setMaximumSize(new Dimension(300, 30)); // Đặt kích thước tối đa cho trường nhập

        formPanel.add(userLabel);
        formPanel.add(userText);
        formPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Tạo khoảng cách giữa các thành phần

        // Tạo nhãn và trường nhập cho password
        JLabel passwordLabel = new JLabel("Password:");
        passwordText = new JPasswordField();
        passwordLabel.setFont(new Font("Arial", Font.BOLD, 20));
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordText.setMaximumSize(new Dimension(300, 30)); // Đặt kích thước tối đa cho trường nhập mật khẩu
        formPanel.add(passwordLabel);
        formPanel.add(passwordText);
        formPanel.add(Box.createRigidArea(new Dimension(0, 20))); // Tạo khoảng cách giữa các thành phần

        // Tạo nút đăng nhập
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Arial", Font.BOLD, 15));
        loginButton.setPreferredSize(new Dimension(40,40));
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(loginButton);

        // Thêm panel form vào giao diện (bên phải)
        panel.add(formPanel, BorderLayout.CENTER);
        add(panel);

        // Frame
        setTitle("THFOODS");
        setSize(1200, 1200);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

        // Đăng kí sự kiện
        loginButton.addActionListener(this);
    }

    public static void main(String[] args) {
        new DangNhap();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        // Khi nhấn nút đăng nhập thành công sẽ hiện frame mới và đóng frame cũ đi
        if (obj == loginButton) {
            new TrangChu();
            dispose();
        }
    }
}
