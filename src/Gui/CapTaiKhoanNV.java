package Gui;

import DAO.NhanVien_Dao;
import DAO.TaiKhoan_Dao;
import DB.Database;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
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
    private JButton confirmEmployeeButton, editEmployeeButton, resetButton;
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

        // Tạo DefaultTableModel tùy chỉnh để không cho phép chỉnh sửa
        tableModelNV = new DefaultTableModel(
                new Object[]{"Mã", "Họ Tên", "SDT", "Email", "Ngày Sinh", "Tên Đăng Nhập", "Mật Khẩu"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Trả về false để ngăn chỉnh sửa mọi ô
                return false;
            }
        };

        tableNV = new JTable(tableModelNV);

// Tăng kích thước font và chiều cao hàng
        tableNV.setFont(new Font("Arial", Font.PLAIN, 16));
        tableNV.setRowHeight(25);

// Tạo renderer tùy chỉnh để đổi màu từng dòng
        tableNV.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(
                    JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

                if (isSelected) {
                    c.setBackground(new Color(184, 207, 229)); // Màu khi chọn dòng
                    c.setForeground(Color.BLACK);
                } else {
                    if (row % 2 == 0) {
                        c.setBackground(new Color(240, 240, 240)); // Màu xám nhạt cho dòng chẵn
                    } else {
                        c.setBackground(Color.WHITE); // Màu trắng cho dòng lẻ
                    }
                    c.setForeground(Color.BLACK);
                }
                return c;
            }
        });

// Thêm JScrollPane để cuộn bảng
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
        JPanel panel = new JPanel(new GridLayout(8, 2, 10, 5));
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Thông Tin Nhân Viên",
                0,
                0,
                new Font("Arial", Font.BOLD, 16),
                new Color(0, 102, 204)
        ));

        // Định dạng font cho JLabel và JTextField
        Font labelFont = new Font("Arial", Font.BOLD, 14);  // Tăng kích thước font chữ cho JLabel
        Font textFieldFont = new Font("Arial", Font.PLAIN, 14); // Font chữ cho JTextField

        // Fields for Employee Information
        JLabel empIdLabel = new JLabel("Mã:");
        empIdLabel.setFont(labelFont); // Áp dụng font chữ cho JLabel
        panel.add(empIdLabel);

        empIdField = new JTextField();
        empIdField.setEditable(false);
        empIdField.setFont(textFieldFont); // Áp dụng font chữ cho JTextField
        panel.add(empIdField);

        JLabel empNameLabel = new JLabel("Họ Tên:");
        empNameLabel.setFont(labelFont);
        panel.add(empNameLabel);

        empNameField = new JTextField();
        empNameField.setFont(textFieldFont);
        panel.add(empNameField);

        JLabel empPhoneLabel = new JLabel("SDT:");
        empPhoneLabel.setFont(labelFont);
        panel.add(empPhoneLabel);

        empPhoneField = new JTextField();
        empPhoneField.setFont(textFieldFont);
        panel.add(empPhoneField);

        JLabel empEmailLabel = new JLabel("Email:");
        empEmailLabel.setFont(labelFont);
        panel.add(empEmailLabel);

        empEmailField = new JTextField();
        empEmailField.setFont(textFieldFont);
        panel.add(empEmailField);

        JLabel empBirthDateLabel = new JLabel("Ngày Sinh:");
        empBirthDateLabel.setFont(labelFont);
        panel.add(empBirthDateLabel);

        empBirthDateField = new JTextField();
        empBirthDateField.setFont(textFieldFont);
        panel.add(empBirthDateField);

        JLabel empLoginNameLabel = new JLabel("Tên Đăng Nhập:");
        empLoginNameLabel.setFont(labelFont);
        panel.add(empLoginNameLabel);

        empLoginNameField = new JTextField();
        empLoginNameField.setEditable(false);
        empLoginNameField.setFont(textFieldFont);
        panel.add(empLoginNameField);

        JLabel empPasswordLabel = new JLabel("Mật Khẩu:");
        empPasswordLabel.setFont(labelFont);
        panel.add(empPasswordLabel);

        empPasswordField = new JTextField();
        empPasswordField.setFont(textFieldFont);
        panel.add(empPasswordField);

        // Set Employee ID and Login Name
        try {
            setIDNV();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new GridLayout(1, 3, 10, 0));

// Tạo nút với hiệu ứng hover và nhấn
        confirmEmployeeButton = createCustomButton("Thêm", new Color(0, 153, 51));
        buttonsPanel.add(confirmEmployeeButton);

        editEmployeeButton = createCustomButton("Chỉnh sửa", new Color(0, 102, 204));
        buttonsPanel.add(editEmployeeButton);

        resetButton = createCustomButton("Làm mới", AppColor.xanh);
        buttonsPanel.add(resetButton);

// Add Buttons Panel to Main Panel
        panel.add(new JLabel()); // Empty cell to align buttons in grid
        panel.add(buttonsPanel);
        confirmEmployeeButton.addActionListener(this);
        editEmployeeButton.addActionListener(this);
        resetButton.addActionListener(this);
        return panel;
    }

    // Hàm tạo nút tùy chỉnh
    private JButton createCustomButton(String text, Color baseColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial Unicode MS", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(baseColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Thêm hiệu ứng hover
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(baseColor.brighter()); // Màu sáng hơn khi hover
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(baseColor); // Trở lại màu gốc khi không hover
            }

            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                button.setBackground(baseColor.darker()); // Màu tối hơn khi nhấn
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                button.setBackground(baseColor); // Trở lại màu gốc khi thả nút
            }
        });

        return button;
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
        }else if(e.getSource() == editEmployeeButton) {
            updateEmployee();
            try {
                setIDNV();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }else {
            xoaRongEmployee();
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
