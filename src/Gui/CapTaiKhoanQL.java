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
    private JButton confirmManagerButton, editButton, deleteButton;
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
        tableQL.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) { // Chỉ xử lý khi sự kiện đã hoàn tất
                int selectedRow = tableQL.getSelectedRow();
                if (selectedRow != -1) { // Nếu có dòng được chọn
                    loadSelectedRowToFields(selectedRow);
                }
            }
        });

        // Load employee data
        try {
            taiKhoan_dao.loadQuanLyData(tableModelQL);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu nhân viên.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void loadSelectedRowToFields(int rowIndex) {
        // Lấy giá trị từ hàng được chọn
        String maQL = tableModelQL.getValueAt(rowIndex, 0).toString();
        String tenNV = tableModelQL.getValueAt(rowIndex, 1).toString();
        String sdt = tableModelQL.getValueAt(rowIndex, 2).toString();
        String email = tableModelQL.getValueAt(rowIndex, 3).toString();
        String tenDangNhap = tableModelQL.getValueAt(rowIndex, 4).toString();
        String matKhau = tableModelQL.getValueAt(rowIndex, 5).toString();

        // Gán giá trị vào các JTextField
        mgrIdField.setText(maQL);
        mgrNameField.setText(tenNV);
        mgrPhoneField.setText(sdt);
        mgrEmailField.setText(email);
        mgrLoginNameField.setText(tenDangNhap);
        mgrPasswordField.setText(matKhau);
    }


    private JPanel createEmployeeInputPanel() {
        JPanel panel = new JPanel(new GridLayout(8, 2, 10, 5));
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Thông Tin Quản Lý",
                0,
                0,
                new Font("Arial", Font.BOLD, 16),
                new Color(0, 102, 204)
        ));

        // Fields for Employee Information
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

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new GridLayout(1, 3, 10, 0));

        // Confirm Button
        confirmManagerButton = new JButton("Xác nhận");
        confirmManagerButton.setBackground(new Color(0, 153, 51));
        confirmManagerButton.setForeground(Color.WHITE);
        confirmManagerButton.setFont(new Font("Arial", Font.BOLD, 14));
        confirmManagerButton.addActionListener(this);
        buttonsPanel.add(confirmManagerButton);

        // Edit Button
         editButton = new JButton("Chỉnh sửa");
        editButton.setBackground(new Color(0, 102, 204));
        editButton.setForeground(Color.WHITE);
        editButton.setFont(new Font("Arial", Font.BOLD, 14));
        editButton.addActionListener(this);
        buttonsPanel.add(editButton);

        // Delete Button
         deleteButton = new JButton("Xóa");
        deleteButton.setBackground(new Color(204, 0, 0));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFont(new Font("Arial", Font.BOLD, 14));
        deleteButton.addActionListener(this);
        buttonsPanel.add(deleteButton);

        // Add Buttons Panel to Main Panel
        panel.add(new JLabel()); // Empty cell to align buttons in grid
        panel.add(buttonsPanel);

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
        }else if (e.getSource() == deleteButton) {
            deleteEmployee();
        }else {
            updateQuanLy();
            try {
                setIDQL();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
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

    private void deleteEmployee() {
        int selectedRow = tableQL.getSelectedRow();
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn quản lý để xóa.", "Cảnh báo", JOptionPane.WARNING_MESSAGE);
            return;
        }

        String maNV = (String) tableModelQL.getValueAt(selectedRow, 0); // Lấy Mã NV từ bảng
        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc muốn xóa nhân viên này?",
                "Xác nhận xóa",
                JOptionPane.YES_NO_OPTION
        );

        if (confirm == JOptionPane.YES_OPTION) {
            try {
                quanLy_dao.deleteTaiKhoanAndQuanLy(maNV); // Gọi DAO để xóa nhân viên
                JOptionPane.showMessageDialog(this, "Xóa quản lý thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
                taiKhoan_dao.loadQuanLyData(tableModelQL); // Reload danh sách quản lý
                xoaRongEmployee(); // Clear các trường nhập liệu
                setIDQL(); // Reset ID
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Xóa quản lý thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        }
    }

    private void updateQuanLy() {
        // Lấy dữ liệu từ các JTextField
        String maQL = mgrIdField.getText();
        String tenNV = mgrNameField.getText();
        String sdt = mgrPhoneField.getText();
        String email = mgrEmailField.getText();
        String matKhau = mgrPasswordField.getText();

        // Kiểm tra dữ liệu đầu vào
        if (!validateEmployeeFields()) {
            return; // Nếu không hợp lệ, dừng xử lý
        }

        // Gọi DAO để cập nhật thông tin quản lý
        try {
            quanLy_dao.updateQuanLy(maQL, tenNV, sdt, email, matKhau);
            JOptionPane.showMessageDialog(this, "Cập nhật thông tin quản lý thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);

            // Reload dữ liệu lên bảng
            taiKhoan_dao.loadQuanLyData(tableModelQL);

            // Xóa rỗng các trường nhập liệu
            xoaRongEmployee();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Cập nhật thông tin quản lý thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }



}
