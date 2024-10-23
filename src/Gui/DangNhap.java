package Gui;

import DAO.Admin_Dao;
import DAO.NhanVien_Dao;
import DAO.TaiKhoan_Dao;
import DB.Database;
import Entity.NguoiQuanTri;
import Entity.NhanVien;
import Entity.TaiKhoan;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class DangNhap extends JFrame implements ActionListener {
    private JButton loginButton,exitButton;
    private  JPasswordField passwordText;
    private JTextField userText;
    private JPanel panel;

    Admin_Dao admin_dao = new Admin_Dao();
    TaiKhoan_Dao taiKhoan_dao = new TaiKhoan_Dao();

    private HashMap<String, String> adminUsers = new HashMap<>();
    private HashMap<String, String> nvUser = new HashMap<>();

    //Biến lưu thông tin đăng nhập
    static NhanVien nvdn = new NhanVien();
    public DangNhap() {

        try {
            Database.getInstance().connect();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // Tạo JPanel chính với BorderLayout
        panel = new JPanel();
        panel.setLayout(new BorderLayout());

        // Thêm hình ảnh vào giao diện (bên trái)
        JLabel imageLabel = new JLabel();
        ImageIcon imageIcon = new ImageIcon("src\\img\\logo2.png"); // Đường dẫn tới ảnh
        Image image = imageIcon.getImage();
        Image imageScale = image.getScaledInstance(250, 400, Image.SCALE_SMOOTH); // Thay đổi kích thước ảnh
        ImageIcon scaledIcon = new ImageIcon(imageScale);
        imageLabel.setIcon(scaledIcon);
        panel.add(imageLabel, BorderLayout.WEST); // Đặt ảnh ở phía trái

        // Tạo JPanel chứa form đăng nhập (bên phải) và set màu nền
        JPanel formPanel = new JPanel();
        formPanel.setBackground(new Color(173, 216, 230)); // Màu xanh da trời nhạt
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS)); // Sử dụng BoxLayout theo chiều dọc

        // Tạo tile
        formPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Tạo khoảng cách trên tiêu đề
        JLabel brand = new JLabel("THFOODS");
        brand.setForeground(Color.WHITE);
        brand.setFont(new Font("Segoe UI", Font.BOLD, 60));
        brand.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(brand);
        JLabel formTitle = new JLabel("LOGIN");
        formTitle.setFont(new Font("Segoe UI", Font.BOLD, 40));
        formTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        formPanel.add(formTitle);

        // Tạo nhãn và trường nhập cho username
        JLabel userLabel = new JLabel("Username:");
        userText = new JTextField();
        userLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        userText.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        userLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        userText.setMaximumSize(new Dimension(200, 30)); // Đặt kích thước tối đa cho trường nhập

        formPanel.add(userLabel);
        formPanel.add(userText);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Tạo khoảng cách giữa các thành phần

        // Tạo nhãn và trường nhập cho password
        JLabel passwordLabel = new JLabel("Password:");
        passwordText = new JPasswordField();
        passwordLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        passwordText.setFont(new Font("Segoe UI", Font.PLAIN, 20));
        passwordLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        passwordText.setMaximumSize(new Dimension(200, 30)); // Đặt kích thước tối đa cho trường nhập mật khẩu
        formPanel.add(passwordLabel);
        formPanel.add(passwordText);
        formPanel.add(Box.createRigidArea(new Dimension(0, 10))); // Tạo khoảng cách giữa các thành phần

        // Tạo nút Login
        loginButton = new JButton("Login");
        loginButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        loginButton.setPreferredSize(new Dimension(80, 30));  // Điều chỉnh kích thước phù hợp
        loginButton.setAlignmentX(Component.CENTER_ALIGNMENT);

// Tạo nút Thoát
        exitButton = new JButton("Exit");
        exitButton.setFont(new Font("Segoe UI", Font.BOLD, 15));
        exitButton.setPreferredSize(new Dimension(80, 30));  // Điều chỉnh kích thước phù hợp
        exitButton.setAlignmentX(Component.CENTER_ALIGNMENT);

// Sử dụng FlowLayout để hai nút nằm cạnh nhau
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 10, 0));  // Khoảng cách giữa các nút là 10
        buttonPanel.setBackground(new Color(173, 216, 230));
        buttonPanel.add(loginButton);
        buttonPanel.add(exitButton);
        formPanel.add(buttonPanel);
        // Thêm panel form vào giao diện (bên phải)
        panel.add(formPanel, BorderLayout.CENTER);
        add(panel);

        // Frame
        setTitle("THFOODS");
        setSize(600, 400);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);

        // Đăng kí sự kiện
        loginButton.addActionListener(this);
        exitButton.addActionListener(this);
    }

    public static void main(String[] args) {
        new DangNhap();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
        // Khi nhấn nút đăng nhập thành công sẽ hiện frame mới và đóng frame cũ đi
        if (obj == loginButton) {
            try {
                ArrayList<NguoiQuanTri> admin = admin_dao.getInFor();
                ArrayList<TaiKhoan> tk = taiKhoan_dao.getAllTK();
                String userName = userText.getText();
                char[] password = passwordText.getPassword();
                String convertPassword = new String(password);
                for (NguoiQuanTri ad : admin) {
                    adminUsers.put(ad.getTenDangNhap(),ad.getMatKhau());
                }
                for(TaiKhoan taiKhoan : tk) {
                    nvUser.put(taiKhoan.getTenDangNhap(),taiKhoan.getMatKhau());
                }

                if(adminUsers.containsKey(userName) && adminUsers.get(userName).equals(convertPassword)) {
                    new CapTaiKhoan();
                    this.dispose();
                }else if(nvUser.containsKey(userName) && nvUser.get(userName).equals(convertPassword)){
                    if (userName.startsWith("NV")) {
                        new TrangChuNV(); // Chuyển đến trang chủ nhân viên
                        this.dispose();
                    } else if (userName.startsWith("QL")) {
                        new TrangChu(); // Chuyển đến trang chủ quản lý
                        this.dispose();
                    }else {
                        JOptionPane.showMessageDialog(this, "Invalid Username or Password", "Login Failed", JOptionPane.ERROR_MESSAGE);
                    }

                    //Lấy tên nhân viên từ tên đăng nhập
                    NhanVien_Dao nhanVien_dao = new NhanVien_Dao();
                    ArrayList<NhanVien> nhanViens = nhanVien_dao.getInForNV();
                    for(NhanVien nv : nhanViens) {
                        if(nv.getTenDangNhap().getTenDangNhap().equals(userName)) {
                            nvdn = nv;
                        }
                    }
                    new TrangChu();
                    this.dispose();
                }else {
                    JOptionPane.showMessageDialog(this, "Invalid Username or Password", "Login Failed", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Invalid Username or Password", "Login Failed", JOptionPane.ERROR_MESSAGE);
            }
        }else if(obj == exitButton){
            int exitClick = JOptionPane.showConfirmDialog(panel,"Do you want to exit?","Confirm",JOptionPane.YES_NO_OPTION);
            if (exitClick == JOptionPane.YES_OPTION) {
                System.exit(0);
            }
        }
    }
}
