package Gui;

import DAO.NhanVien_Dao;
import DAO.TaiKhoan_Dao;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;

public class CapTaiKhoan implements ActionListener {

    private JFrame frame;
    private JTextField nameField, emailField, phoneField, addressField, birthDateField, idField, loginNameFiled, passwordField;
    private JTable table;
    private DefaultTableModel tableModel;
    private JButton addButton;
    NhanVien_Dao nhanVien_dao = new NhanVien_Dao();
    TaiKhoan_Dao taiKhoan_dao = new TaiKhoan_Dao();

    public CapTaiKhoan() throws SQLException {
        frame = new JFrame("Cấp Tài Khoản");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new BorderLayout());

        // Thêm màu nền cho khung
        frame.getContentPane().setBackground(new Color(230, 230, 250));

        // Panel nhập thông tin nhân viên
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(8, 2, 10, 5)); // Tăng khoảng cách giữa các trường nhập
        inputPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Thông Tin Nhân Viên",
                0, 0, new Font("Arial", Font.BOLD, 16), new Color(0, 102, 204)));

        // Các trường nhập thông tin
        inputPanel.add(new JLabel("Mã:"));
        idField = new JTextField();
        inputPanel.add(idField);
        idField.setEditable(false);

        inputPanel.add(new JLabel("Họ Tên:"));
        nameField = new JTextField();
        inputPanel.add(nameField);

        inputPanel.add(new JLabel("SDT:"));
        phoneField = new JTextField();
        inputPanel.add(phoneField);

        inputPanel.add(new JLabel("Email:"));
        emailField = new JTextField();
        inputPanel.add(emailField);

        inputPanel.add(new JLabel("Ngày Sinh:"));
        birthDateField = new JTextField();
        inputPanel.add(birthDateField);

        inputPanel.add(new JLabel("Tên Đăng Nhập:"));
        loginNameFiled = new JTextField();
        loginNameFiled.setEditable(false);
        inputPanel.add(loginNameFiled);

        inputPanel.add(new JLabel("Mật Khẩu:"));
        passwordField = new JTextField();
        inputPanel.add(passwordField);
        setID();
        // Nút Thêm Nhân Viên
        addButton = new JButton("Xác nhận");
        addButton.setBackground(new Color(0, 153, 51));
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        inputPanel.add(addButton);

        frame.add(inputPanel, BorderLayout.NORTH);

        // Đẩy danh sách thông tin tài khoản nhân viên vào bảng

        // Bảng danh sách nhân viên
        tableModel = new DefaultTableModel(new Object[]{"Mã", "Họ Tên", "SDT", "Email", "Ngày Sinh", "Tên Đăng Nhập", "Mật Khẩu"}, 0);
        table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 16));
        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);
        taiKhoan_dao.loadNhanVienData(tableModel);


        frame.setVisible(true);

        addButton.addActionListener(this);
    }


        @Override
        public void actionPerformed(ActionEvent e) {
        Object obj = e.getSource();
            if(obj.equals(addButton)) {
                if(validateFields()) {
                    try {
                        insertNvAndTk();
                        taiKhoan_dao.loadNhanVienData(tableModel);
                        xoaRong();
                        setID();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(frame,"Thêm thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    }
                }
            }


        }
        // Lấy ra mã lớn nhất và gán cho mã nhân viên và tên đăng nhập
        public void setID() throws SQLException {
        String newMaNV = nhanVien_dao.getIDMax();
        idField.setText(newMaNV);
        loginNameFiled.setText(newMaNV);
        }

        // Kiểm tra các trường
        private boolean validateFields() {
            // Kiểm tra họ tên
            String name = nameField.getText().trim();
            if(name.trim().isEmpty()){
                JOptionPane.showMessageDialog(frame, "Họ tên không được để trống.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            if (!name.matches("^(?=.*\\s)([A-Z][a-zA-Z]*(\\s+[A-Z][a-zA-Z]*)*)+$")) {
                JOptionPane.showMessageDialog(frame, "Họ tên phải có ít nhất 2 từ, mỗi từ bắt đầu bằng chữ hoa.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // Kiểm tra số điện thoại
            String phone = phoneField.getText().trim();

            if(phone.isEmpty()){
                JOptionPane.showMessageDialog(frame, "Số điện thoại  không được để trống.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            if (!phone.matches("^\\d{10}$")) {
                JOptionPane.showMessageDialog(frame, "Số điện thoại phải là 10 số.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // Kiểm tra email
            String email = emailField.getText().trim();

            if(email.isEmpty()){
                JOptionPane.showMessageDialog(frame, "Email  không được để trống.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            if (!email.matches("^[A-Za-z0-9._%+-]+@gmail\\.com$")) {
                JOptionPane.showMessageDialog(frame, "Email phải có định dạng @gmail.com.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // Kiểm tra ngày sinh
            String birthDate = birthDateField.getText();
            String regex = "^\\d{4}-\\d{2}-\\d{2}$";

            if(birthDate.isEmpty()){
                JOptionPane.showMessageDialog(frame, "Ngày sinh không được để trống.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            if (!birthDate.matches(regex)) {
                JOptionPane.showMessageDialog(frame, "Ngày sinh phải có định dạng yyyy-MM-dd.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            // Kiểm tra tính hợp lệ của ngày
            try {
                LocalDate date = LocalDate.parse(birthDate);
                if (date.isAfter(LocalDate.now())) {
                    JOptionPane.showMessageDialog(frame, "Ngày sinh không được là ngày tương lai.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return false;
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(frame, "Ngày sinh không hợp lệ.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            // Kiểm tra mật khẩu
            String password = passwordField.getText().trim();

            if(password.isEmpty()){
                JOptionPane.showMessageDialog(frame, "Mật khẩu không được để trống.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }
            if (password.length() < 5) {
                JOptionPane.showMessageDialog(frame, "Mật khẩu phải có ít nhất 5 ký tự.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return false;
            }

            return true; // Tất cả các trường hợp lệ
        }

        public void insertNvAndTk() throws SQLException {
            String id = idField.getText();
            String tenNV = nameField.getText();
            String numberPhone = phoneField.getText();
            String email = emailField.getText();
            String ngaySinh = birthDateField.getText();
            String tenDangNhap = loginNameFiled.getText();
            String matKhau = passwordField.getText();
            nhanVien_dao.addTaiKhoanAndNhanVien(tenDangNhap,matKhau,id,tenNV,numberPhone,email,ngaySinh);
        }

        public void xoaRong() {
            nameField.setText("");
            phoneField.setText("");
            emailField.setText("");
            birthDateField.setText("");
            passwordField.setText("");

        }


    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new CapTaiKhoan();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }


}
