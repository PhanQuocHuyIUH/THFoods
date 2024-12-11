package Gui;

import DAO.NhanVien_Dao;
import DAO.QuanLy_Dao;
import DAO.TaiKhoan_Dao;
import DB.Database;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class CapTaiKhoanQL extends JPanel implements ActionListener {
    // Thêm các trường cho Quản Lý
    private JTextField mgrNameField, mgrEmailField, mgrPhoneField, mgrIdField, mgrLoginNameField, mgrPasswordField;
    private JTable tableQL;
    private DefaultTableModel tableModelQL;
    private JButton confirmManagerButton, editButton, deleteButton, resetButton;
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

        // Create manager table
        // Tạo DefaultTableModel tùy chỉnh để không cho phép chỉnh sửa
        tableModelQL = new DefaultTableModel(
                new Object[]{"Mã", "Họ Tên", "SDT", "Email", "Tên Đăng Nhập", "Mật Khẩu"},
                0
        ) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Trả về false để ngăn chỉnh sửa mọi ô
                return false;
            }
        };

        tableQL = new JTable(tableModelQL);

// Tăng kích thước font và chiều cao hàng
        tableQL.setFont(new Font("Arial", Font.PLAIN, 16));
        tableQL.setRowHeight(25);

// Tạo renderer tùy chỉnh để đổi màu từng dòng
        tableQL.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
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
        JPanel panel = new JPanel(new GridLayout(8, 2, 15, 10)); // Tăng khoảng cách giữa các hàng và cột
        panel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createEtchedBorder(),
                "Thông Tin Quản Lý",
                0,
                0,
                new Font("Arial", Font.BOLD, 18), // Font lớn hơn cho tiêu đề
                new Color(0, 102, 204)
        ));

        // Định dạng font chữ lớn hơn
        Font labelFont = new Font("Arial", Font.BOLD, 14); // Tăng kích thước chữ của JLabel
        Font textFieldFont = new Font("Arial", Font.PLAIN, 14); // Font chữ lớn hơn cho JTextField
        Font buttonFont = new Font("Arial Unicode MS", Font.BOLD, 14); // Font chữ lớn hơn cho JButton

        // Fields for Manager Information
        JLabel mgrIdLabel = new JLabel("Mã:");
        mgrIdLabel.setFont(labelFont);
        panel.add(mgrIdLabel);

        mgrIdField = new JTextField();
        mgrIdField.setEditable(false);
        mgrIdField.setFont(textFieldFont);
        panel.add(mgrIdField);

        JLabel mgrNameLabel = new JLabel("Họ Tên:");
        mgrNameLabel.setFont(labelFont);
        panel.add(mgrNameLabel);

        mgrNameField = new JTextField();
        mgrNameField.setFont(textFieldFont);
        panel.add(mgrNameField);

        JLabel mgrPhoneLabel = new JLabel("SDT:");
        mgrPhoneLabel.setFont(labelFont);
        panel.add(mgrPhoneLabel);

        mgrPhoneField = new JTextField();
        mgrPhoneField.setFont(textFieldFont);
        panel.add(mgrPhoneField);

        JLabel mgrEmailLabel = new JLabel("Email:");
        mgrEmailLabel.setFont(labelFont);
        panel.add(mgrEmailLabel);

        mgrEmailField = new JTextField();
        mgrEmailField.setFont(textFieldFont);
        panel.add(mgrEmailField);

        JLabel mgrLoginNameLabel = new JLabel("Tên Đăng Nhập:");
        mgrLoginNameLabel.setFont(labelFont);
        panel.add(mgrLoginNameLabel);

        mgrLoginNameField = new JTextField();
        mgrLoginNameField.setEditable(false);
        mgrLoginNameField.setFont(textFieldFont);
        panel.add(mgrLoginNameField);

        JLabel mgrPasswordLabel = new JLabel("Mật Khẩu:");
        mgrPasswordLabel.setFont(labelFont);
        panel.add(mgrPasswordLabel);

        mgrPasswordField = new JTextField();
        mgrPasswordField.setFont(textFieldFont);
        panel.add(mgrPasswordField);

        // Set Manager ID and Login Name
        try {
            setIDQL();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        // Buttons Panel
        JPanel buttonsPanel = new JPanel(new GridLayout(1, 4, 15, 0)); // Tăng khoảng cách giữa các nút

        // Create custom buttons with hover and press effects
        confirmManagerButton = createCustomButton("Thêm", new Color(0, 153, 51));
        editButton = createCustomButton("Chỉnh sửa", new Color(0, 102, 204));
        deleteButton = createCustomButton("Xóa", new Color(204, 0, 0));
        resetButton = createCustomButton("Làm mới", AppColor.xanh);

        buttonsPanel.add(confirmManagerButton);
        buttonsPanel.add(editButton);
        buttonsPanel.add(deleteButton);
        buttonsPanel.add(resetButton);

        // Đăng kí sự kiện
        confirmManagerButton.addActionListener(this);
        editButton.addActionListener(this);
        deleteButton.addActionListener(this);
        resetButton.addActionListener(this);

        // Add Buttons Panel to Main Panel
        panel.add(new JLabel()); // Ô trống để căn chỉnh lưới
        panel.add(buttonsPanel);

        return panel;
    }

    // Helper method to create custom buttons with hover and click effects
    private JButton createCustomButton(String text, Color backgroundColor) {
        JButton button = new JButton(text);
        button.setFont(new Font("Arial Unicode MS", Font.BOLD, 14));
        button.setForeground(Color.WHITE);
        button.setBackground(backgroundColor);
        button.setFocusPainted(false);
        button.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        // Mouse hover effect
        button.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                button.setBackground(backgroundColor.darker()); // Màu tối hơn khi hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                button.setBackground(backgroundColor); // Màu gốc khi không hover
            }

            @Override
            public void mousePressed(MouseEvent e) {
                button.setBackground(backgroundColor.brighter()); // Màu sáng hơn khi nhấn
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                button.setBackground(backgroundColor.darker()); // Màu tối hơn khi nhả chuột
            }
        });

        return button;
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
        }else if(e.getSource() == editButton) {
            updateQuanLy();
            try {
                setIDQL();
            } catch (SQLException ex) {
                throw new RuntimeException(ex);
            }
        }else {
            xoaRongEmployee();
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

        int confirm = JOptionPane.showConfirmDialog(
                this,
                "Bạn có chắc muốn chỉnh sửa thông tin này?",
                "Xác nhận chỉnh sửa",
                JOptionPane.YES_NO_OPTION
        );

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
