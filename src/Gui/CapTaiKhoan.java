package Gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class CapTaiKhoan {

    private JFrame frame;
    private JTextField nameField, emailField, phoneField, addressField, birthDateField, idField, loginNameFiled, passwordField;
    private JTable table;
    private DefaultTableModel tableModel;

    public CapTaiKhoan() {
        frame = new JFrame("Cấp Tài Khoản");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setLayout(new BorderLayout());

        // Thêm màu nền cho khung
        frame.getContentPane().setBackground(new Color(230, 230, 250));

        // Panel nhập thông tin nhân viên
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(new GridLayout(8, 2, 10, 10)); // Tăng khoảng cách giữa các trường nhập
        inputPanel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Thông Tin Nhân Viên",
                0, 0, new Font("Arial", Font.BOLD, 16), new Color(0, 102, 204)));

        // Các trường nhập thông tin
        inputPanel.add(new JLabel("Mã:"));
        idField = new JTextField();
        inputPanel.add(idField);

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
        inputPanel.add(loginNameFiled);

        inputPanel.add(new JLabel("Mật Khẩu:"));
        passwordField = new JTextField();
        inputPanel.add(passwordField);

        // Nút Thêm Nhân Viên
        JButton addButton = new JButton("Xác nhận");
        addButton.setBackground(new Color(0, 153, 51));
        addButton.setForeground(Color.WHITE);
        addButton.setFont(new Font("Arial", Font.BOLD, 14));
        addButton.addActionListener(new AddEmployeeAction());
        inputPanel.add(addButton);

        frame.add(inputPanel, BorderLayout.NORTH);

        // Bảng danh sách nhân viên
        tableModel = new DefaultTableModel(new Object[]{"Mã", "Họ Tên", "SDT", "Email", "Ngày Sinh", "Tên Đăng Nhập", "Mật Khẩu"}, 0);
        table = new JTable(tableModel);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(table);
        frame.add(scrollPane, BorderLayout.CENTER);

        // Thêm màu nền cho bảng
        table.setBackground(Color.WHITE);
        table.setForeground(Color.BLACK);
        table.setFillsViewportHeight(true);

        frame.setVisible(true);
    }

    private class AddEmployeeAction implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            String id = idField.getText();
            String name = nameField.getText();
            String phone = phoneField.getText();
            String email = emailField.getText();
            String birthDate = birthDateField.getText();
            String nameLogin = loginNameFiled.getText();
            String pasword = passwordField.getText();


            if (!name.isEmpty() && !id.isEmpty() && !email.isEmpty() && !phone.isEmpty() && !nameLogin.isEmpty() && !birthDate.isEmpty() && !pasword.isEmpty()) {
                // Thêm nhân viên mới vào bảng
                tableModel.addRow(new Object[]{tableModel.getRowCount() + 1, id, name, phone, email, birthDate, nameLogin, pasword});

            } else {
                JOptionPane.showMessageDialog(frame, "Vui lòng nhập đầy đủ thông tin!", "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(CapTaiKhoan::new);
    }
}
