package Gui;

import DAO.NhanVien_Dao;
import DAO.QuanLy_Dao;
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

public class CapTaiKhoanQL extends JPanel implements ActionListener {
    // Thêm các trường cho Quản Lý
    private JTextField mgrNameField, mgrEmailField, mgrPhoneField, mgrIdField, mgrLoginNameField, mgrPasswordField;
    private JTable tableQL;
    private DefaultTableModel tableModelQL;
    private JButton confirmManagerButton;
    QuanLy_Dao quanLy_dao = new QuanLy_Dao();
    private TaiKhoan_Dao taiKhoan_dao = new TaiKhoan_Dao();

    public CapTaiKhoanQL() {
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
        tableModelQL = new DefaultTableModel(new Object[]{"Mã", "Họ Tên", "SDT", "Email", "Tên Đăng Nhập", "Mật Khẩu"}, 0);
        tableQL = new JTable(tableModelQL);
        tableQL.setFont(new Font("Arial", Font.PLAIN, 16));
        tableQL.setRowHeight(25);
        JScrollPane scrollPane = new JScrollPane(tableQL);
        add(scrollPane, BorderLayout.CENTER);

        // Load employee data
        try {
            taiKhoan_dao.loadQuanLyData(tableModelQL);
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
        mgrIdField = new JTextField();
        mgrIdField.setEditable(false);
        panel.add(mgrIdField);

        panel.add(new JLabel("Họ Tên:"));
        mgrNameField = new JTextField();
        panel.add(mgrNameField);

        panel.add(new JLabel("SDT:"));
        mgrPhoneField = new JTextField();
        panel.add(mgrPhoneField);

        panel.add(new JLabel("Email:"));
        mgrEmailField = new JTextField();
        panel.add(mgrEmailField);

        panel.add(new JLabel("Tên Đăng Nhập:"));
        mgrLoginNameField = new JTextField();
        mgrLoginNameField.setEditable(false);
        panel.add(mgrLoginNameField);

        panel.add(new JLabel("Mật Khẩu:"));
        mgrPasswordField = new JTextField();
        panel.add(mgrPasswordField);

        // Set Employee ID and Login Name
        try {
            setIDQL();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Confirm button
        confirmManagerButton = new JButton("Xác nhận");
        confirmManagerButton.setBackground(new Color(0, 153, 51));
        confirmManagerButton.setForeground(Color.WHITE);
        confirmManagerButton.setFont(new Font("Arial", Font.BOLD, 14));
        confirmManagerButton.addActionListener(this);
        panel.add(confirmManagerButton);

        return panel;
    }

    private void setIDQL() throws SQLException {
        String newMaNV = quanLy_dao.getIDMax();
        mgrIdField.setText(newMaNV);
        mgrLoginNameField.setText(newMaNV);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == confirmManagerButton) {
            if (validateEmployeeFields()) {
                confirmManagerButton.setEnabled(false); // Disable button to prevent double submissions
                try {
                    insertNvAndTk();
                    taiKhoan_dao.loadQuanLyData(tableModelQL); // Reload quản lý data
                    xoaRongEmployee(); // Clear fields
                    setIDQL(); // Reset ID
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Thêm Nhân viên thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                } finally {
                    confirmManagerButton.setEnabled(true); // Re-enable button after processing
                }
            }
        }
    }

    private boolean validateEmployeeFields() {
        // Validate Name
        if (mgrNameField.getText().trim().isEmpty()) {
            showError("Họ tên nhân viên không được để trống.");
            return false;
        }

        // Validate Phone
        String phone = mgrPhoneField.getText().trim();
        if (phone.isEmpty() || !phone.matches("^\\d{10}$")) {
            showError("Số điện thoại không hợp lệ (phải là 10 số).");
            return false;
        }

        // Validate Email
        String email = mgrEmailField.getText().trim();
        if (email.isEmpty() || !email.matches("^[A-Za-z0-9._%+-]+@gmail\\.com$")) {
            showError("Email không hợp lệ, phải có định dạng @gmail.com.");
            return false;
        }


        // Validate Password
        String password = mgrPasswordField.getText().trim();
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
        String id = mgrIdField.getText();
        String tenNV = mgrNameField.getText();
        String numberPhone = mgrPhoneField.getText();
        String email = mgrEmailField.getText();
        String tenDangNhap = mgrLoginNameField.getText();
        String matKhau = mgrPasswordField.getText();

        quanLy_dao.addTaiKhoanAndQuanLy(tenDangNhap, matKhau, id, tenNV, numberPhone, email);
    }

    public void xoaRongEmployee() {
        mgrNameField.setText("");
        mgrPhoneField.setText("");
        mgrEmailField.setText("");
        mgrPasswordField.setText("");
    }
}
