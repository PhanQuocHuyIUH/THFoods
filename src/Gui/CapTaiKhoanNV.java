package Gui;

import DAO.NhanVien_Dao;
import DAO.TaiKhoan_Dao;
import DB.Database;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class CapTaiKhoanNV extends JPanel implements ActionListener {
    private JTextField empNameField, empEmailField, empPhoneField, empBirthDateField, empIdField, empLoginNameField, empPasswordField;
    private JTable tableNV;
    private DefaultTableModel tableModelNV;
    private JButton confirmEmployeeButton;
    private NhanVien_Dao nhanVien_dao = new NhanVien_Dao();
    private TaiKhoan_Dao taiKhoan_dao = new TaiKhoan_Dao();

    public CapTaiKhoanNV() {
        try {
            Database.getInstance().connect();
        } catch (SQLException e) {
            e.printStackTrace();
            return; // If DB connection fails, do not continue
        }

        setLayout(new BorderLayout());

        // Create input panel
        JPanel inputPanel = createEmployeeInputPanel();
        add(inputPanel, BorderLayout.NORTH);

        // Create employee table
        tableModelNV = new DefaultTableModel(new Object[]{"Mã", "Họ Tên", "SDT", "Email", "Ngày Sinh", "Tên Đăng Nhập", "Mật Khẩu"}, 0);
        tableNV = new JTable(tableModelNV);
        tableNV.setFont(new Font("Arial", Font.PLAIN, 16));
        tableNV.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(tableNV);
        add(scrollPane, BorderLayout.CENTER);

        // Load employee data
        try {
            taiKhoan_dao.loadNhanVienData(tableModelNV);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu nhân viên.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private JPanel createEmployeeInputPanel() {
        JPanel panel = new JPanel(new GridLayout(8, 2, 10, 5));
        panel.setBorder(BorderFactory.createTitledBorder(BorderFactory.createEtchedBorder(), "Thông Tin Nhân Viên",
                0, 0, new Font("Arial", Font.BOLD, 16), new Color(0, 102, 204)));

        panel.add(new JLabel("Mã:"));
        empIdField = new JTextField();
        empIdField.setEditable(false);
        panel.add(empIdField);

        panel.add(new JLabel("Họ Tên:"));
        empNameField = new JTextField();
        panel.add(empNameField);

        panel.add(new JLabel("SDT:"));
        empPhoneField = new JTextField();
        panel.add(empPhoneField);

        panel.add(new JLabel("Email:"));
        empEmailField = new JTextField();
        panel.add(empEmailField);

        panel.add(new JLabel("Ngày Sinh:"));
        empBirthDateField = new JTextField();
        panel.add(empBirthDateField);

        panel.add(new JLabel("Tên Đăng Nhập:"));
        empLoginNameField = new JTextField();
        empLoginNameField.setEditable(false);
        panel.add(empLoginNameField);

        panel.add(new JLabel("Mật Khẩu:"));
        empPasswordField = new JTextField();
        panel.add(empPasswordField);

        // Set Employee ID and Login Name
        try {
            setIDNV();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Confirm button
        confirmEmployeeButton = new JButton("Xác nhận");
        confirmEmployeeButton.setBackground(new Color(0, 153, 51));
        confirmEmployeeButton.setForeground(Color.WHITE);
        confirmEmployeeButton.setFont(new Font("Arial", Font.BOLD, 14));
        confirmEmployeeButton.addActionListener(this);
        panel.add(confirmEmployeeButton);

        return panel;
    }

    private void setIDNV() throws SQLException {
        String newMaNV = nhanVien_dao.getIDMax();
        empIdField.setText(newMaNV);
        empLoginNameField.setText(newMaNV);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirmEmployeeButton) {
            if (validateEmployeeFields()) {
                confirmEmployeeButton.setEnabled(false); // Disable button to prevent double submissions
                try {
                    insertNvAndTk();
                    taiKhoan_dao.loadNhanVienData(tableModelNV); // Reload employee data
                    xoaRongEmployee(); // Clear fields
                    setIDNV(); // Reset ID
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Thêm Nhân viên thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                } finally {
                    confirmEmployeeButton.setEnabled(true); // Re-enable button after processing
                }
            }
        }
    }

    private boolean validateEmployeeFields() {
        // Validate Name
        if (empNameField.getText().trim().isEmpty()) {
            showError("Họ tên nhân viên không được để trống.");
            return false;
        }

        // Validate Phone
        String phone = empPhoneField.getText().trim();
        if (phone.isEmpty() || !phone.matches("^\\d{10}$")) {
            showError("Số điện thoại không hợp lệ (phải là 10 số).");
            return false;
        }

        // Validate Email
        String email = empEmailField.getText().trim();
        if (email.isEmpty() || !email.matches("^[A-Za-z0-9._%+-]+@gmail\\.com$")) {
            showError("Email không hợp lệ, phải có định dạng @gmail.com.");
            return false;
        }

        // Validate Birthdate
        String birthDate = empBirthDateField.getText().trim();
        if (birthDate.isEmpty()) {
            showError("Ngày sinh không được để trống.");
            return false;
        }
        try {
            LocalDate date = LocalDate.parse(birthDate);
            if (date.isAfter(LocalDate.now())) {
                showError("Ngày sinh không được là ngày tương lai.");
                return false;
            }
        } catch (DateTimeParseException e) {
            showError("Ngày sinh không hợp lệ.");
            return false;
        }

        // Validate Password
        String password = empPasswordField.getText().trim();
        if (password.isEmpty() || password.length() < 5) {
            showError("Mật khẩu phải có ít nhất 5 ký tự.");
            return false;
        }

        return true;
    }

    private void showError(String message) {
        JOptionPane.showMessageDialog(this, message, "Lỗi", JOptionPane.ERROR_MESSAGE);
    }

    public void insertNvAndTk() throws SQLException {
        String id = empIdField.getText();
        String tenNV = empNameField.getText();
        String numberPhone = empPhoneField.getText();
        String email = empEmailField.getText();
        String ngaySinh = empBirthDateField.getText();
        String tenDangNhap = empLoginNameField.getText();
        String matKhau = empPasswordField.getText();

        nhanVien_dao.addTaiKhoanAndNhanVien(tenDangNhap, matKhau, id, tenNV, numberPhone, email, ngaySinh);
    }

    public void xoaRongEmployee() {
        empNameField.setText("");
        empPhoneField.setText("");
        empEmailField.setText("");
        empBirthDateField.setText("");
        empPasswordField.setText("");
    }
}
