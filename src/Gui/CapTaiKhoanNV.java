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
    private JButton confirmEmployeeButton, editEmployeeButton;
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
        tableNV.getSelectionModel().addListSelectionListener(event -> {
            if (!event.getValueIsAdjusting()) { // Chỉ xử lý khi sự kiện đã hoàn tất
                int selectedRow = tableNV.getSelectedRow();
                if (selectedRow != -1) { // Nếu có dòng được chọn
                    loadSelectedRowToFields(selectedRow);
                }
            }
        });

        // Load employee data
        try {
            taiKhoan_dao.loadNhanVienData(tableModelNV);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu nhân viên.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    private void loadSelectedRowToFields(int rowIndex) {
        // Lấy giá trị từ hàng được chọn
        String maQL = tableModelNV.getValueAt(rowIndex, 0).toString();
        String tenNV = tableModelNV.getValueAt(rowIndex, 1).toString();
        String sdt = tableModelNV.getValueAt(rowIndex, 2).toString();
        String email = tableModelNV.getValueAt(rowIndex, 3).toString();
        String ngaySinh = tableModelNV.getValueAt(rowIndex,4).toString();
        String tenDangNhap = tableModelNV.getValueAt(rowIndex, 5).toString();
        String matKhau = tableModelNV.getValueAt(rowIndex, 6).toString();

        // Gán giá trị vào các JTextField
        empIdField.setText(maQL);
        empNameField.setText(tenNV);
        empPhoneField.setText(sdt);
        empEmailField.setText(email);
        empBirthDateField.setText(ngaySinh);
        empLoginNameField.setText(tenDangNhap);
        empPasswordField.setText(matKhau);
    }

    private JPanel createEmployeeInputPanel() {
        JPanel panel = new JPanel(new GridLayout(9, 2, 10, 5)); // Thay đổi số hàng từ 8 lên 9
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

        // Nút "Xác nhận"
        confirmEmployeeButton = new JButton("Xác nhận");
        confirmEmployeeButton.setBackground(new Color(0, 153, 51));
        confirmEmployeeButton.setForeground(Color.WHITE);
        confirmEmployeeButton.setFont(new Font("Arial", Font.BOLD, 14));
        confirmEmployeeButton.addActionListener(this);
        panel.add(confirmEmployeeButton);

        // Nút "Chỉnh sửa"
        editEmployeeButton = new JButton("Chỉnh sửa");
        editEmployeeButton.setBackground(new Color(255, 153, 0));
        editEmployeeButton.setForeground(Color.WHITE);
        editEmployeeButton.setFont(new Font("Arial", Font.BOLD, 14));
        editEmployeeButton.addActionListener(this);
        panel.add(editEmployeeButton);

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
        }else {
            updateEmployee();
            try {
                setIDNV();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
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

    private void updateEmployee() {
        // Lấy dữ liệu từ các JTextField
        String maNV = empIdField.getText();
        String tenNV = empNameField.getText();
        String sdt = empPhoneField.getText();
        String email = empEmailField.getText();
        String ngaySinh = empBirthDateField.getText();
        String matKhau = empPasswordField.getText();

        // Kiểm tra dữ liệu đầu vào
        if (!validateEmployeeFields()) {
            return; // Nếu không hợp lệ, dừng xử lý
        }

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc muốn chỉnh sửa thông tin này?",
                "Xác nhận chỉnh sửa",
                JOptionPane.YES_NO_OPTION
        );

        // Gọi DAO để cập nhật thông tin nhân viên
        try {
            nhanVien_dao.updateNhanVien(maNV, tenNV, sdt, email, ngaySinh, matKhau);
            JOptionPane.showMessageDialog(this, "Cập nhật thông tin nhân viên thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);

            // Reload dữ liệu lên bảng
            taiKhoan_dao.loadNhanVienData(tableModelNV);

            // Xóa rỗng các trường nhập liệu
            xoaRongEmployee();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Cập nhật thông tin nhân viên thất bại.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }


    public void xoaRongEmployee() {
        empNameField.setText("");
        empPhoneField.setText("");
        empEmailField.setText("");
        empBirthDateField.setText("");
        empPasswordField.setText("");
    }
}
