package Gui;

import javax.swing.*;
import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import DAO.NhanVien_Dao;
import Entity.NhanVien;
import Entity.TaiKhoan;

import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.awt.event.ActionEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.FocusAdapter;
import java.awt.event.FocusEvent;

public class QuanLyNhanVien extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField txtTimKiem; // Declare the JTextField
    private DefaultTableModel model;
    private JTable table;
    private JScrollPane scrollPane;
    private JButton lastClickedButton;
    private JButton btnXoa;
    private JButton btnSua;
    private JButton btnLamMoi;

    /**
     * Create the panel.
     */
    public QuanLyNhanVien() {
        setLayout(new BorderLayout()); // Đảm bảo sử dụng BorderLayout cho JPanel chính
        setBackground(AppColor.trang); // Đặt màu nền cho JPanel chính
        JPanel northPanel = new JPanel();
        northPanel.setLayout(new BorderLayout());
        northPanel.setBackground(AppColor.trang);
        add(northPanel, BorderLayout.NORTH);

        JPanel centerPanel = new JPanel();
        centerPanel.setLayout(new BorderLayout()); // Sử dụng BorderLayout cho centerPanel
        add(centerPanel, BorderLayout.CENTER);

        // Initialize the JTextField
        txtTimKiem = new JTextField(15);
        txtTimKiem.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtTimKiem.setBackground(AppColor.xam);
        txtTimKiem.setPreferredSize(new Dimension(600, 30));
        txtTimKiem.setText("Nhập nội dung tìm kiếm...");
        // Ẩn text khi focus và hiển thị lại khi blur
        txtTimKiem.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {
                txtTimKiem.setText("");
            }

            @Override
            public void focusLost(FocusEvent e) {
                txtTimKiem.setText("Nhập nội dung tìm kiếm...");
            }
        });

        btnXoa = createStyledButton("XÓA", e -> xoaNhanVien());
        btnSua = createStyledButton("SỬA", e -> suaNhanVien());
        btnLamMoi = createStyledButton("LÀM MỚI", e -> loadToTable());
        btnXoa.setIcon(new ImageIcon("src/img/delete_icon.png"));
        btnSua.setIcon(new ImageIcon("src/img/fix_icon.png"));
        btnLamMoi.setIcon(new ImageIcon("src/img/refresh_icon.png"));
        
        // Tạo JComboBox cho tiêu chí tìm kiếm
        String[] searchOptions = { "Tìm theo mã", "Tìm theo tên" };
        JComboBox<String> cboTim = new JComboBox<>(searchOptions);
        cboTim.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        cboTim.setPreferredSize(new Dimension(150, 30));
        cboTim.setBackground(AppColor.xam);
        cboTim.setBorder(BorderFactory.createLineBorder(AppColor.xanh));

        // Thêm KeyListener cho JTextField
        txtTimKiem.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == KeyEvent.VK_ENTER) {
                    timNhanVien(cboTim.getSelectedItem().toString()); // Gọi phương thức tìm nhân viên
                }
            }
        });

        // Tạo một panel cho các nút xóa và sửa
        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT)); // Panel bên trái
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        leftPanel.setBackground(AppColor.trang);
        leftPanel.add(btnXoa);
        leftPanel.add(btnSua);
        northPanel.add(leftPanel, BorderLayout.WEST); // Thêm panel bên trái vào northPanel

        // Tạo một panel cho ô tìm kiếm, JComboBox và nút làm mới
        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Panel bên phải
        rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        rightPanel.setBackground(AppColor.trang);
        rightPanel.add(txtTimKiem);
        rightPanel.add(cboTim);
        rightPanel.add(btnLamMoi);
        northPanel.add(rightPanel, BorderLayout.EAST); // Thêm panel bên phải vào northPanel

        // Tiêu đề
        String[] columnNames = { "Mã nhân viên", "Họ tên", "Số điện thoại", "Email", "Ngày sinh" };
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);

        customizeEmployeeTable();

        scrollPane = new JScrollPane(table);
        scrollPane.setBackground(AppColor.trang);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        loadToTable();
    }

    private void customizeEmployeeTable() {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.setBackground(AppColor.trang);
        table.setForeground(AppColor.den);
        table.setGridColor(AppColor.xanh);
        table.setFillsViewportHeight(true);
        table.setDefaultEditor(Object.class, null);

        // Tạo custom cell renderer với lề trái 5 pixel
        DefaultTableCellRenderer paddingRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (c instanceof JLabel) {
                    ((JLabel) c).setBorder(new EmptyBorder(0, 5, 0, 0)); // Cách lề trái 5 pixel
                }
                if (isSelected) {
                    c.setBackground(AppColor.xanhNhat);
                } else if (row % 2 == 0) {
                    c.setBackground(AppColor.xam);
                } else {
                    c.setBackground(AppColor.trang);
                }
                return c;
            }
        };

        // Áp dụng renderer cho tất cả các cột
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(paddingRenderer);
        }

        JTableHeader header = table.getTableHeader();
        header.setBackground(AppColor.xanh);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 17));
    }

    private JButton createStyledButton(String text, ActionListener action) { // Thêm kiểu trả về JButton
        JButton button = new JButton(text);
        button.addActionListener(action);
        // Thêm bất kỳ kiểu dáng bổ sung nào nếu cần
        button.setFont(new Font("Arial Unicode MS", Font.BOLD, 17));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setPreferredSize(new Dimension(150, 30));
        applyButtonColorChange(button);
        return button;
    }

    private void applyButtonColorChange(JButton button) {
        button.setBackground(AppColor.trang);
        button.setForeground(AppColor.den);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(AppColor.xanhNhat); // Đổi màu nền khi rê chuột vào
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (button != lastClickedButton) {
                    button.setBackground(AppColor.trang); // Màu nền trở lại
                }
            }
        });

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (lastClickedButton != null) {
                    lastClickedButton.setBackground(AppColor.trang); // Đổi màu nền của nút trước đó
                }
                button.setBackground(AppColor.xanhNhat); // Đổi màu nền của nút được nhấn
                lastClickedButton = button; // Cập nhật nút được nhấn
            }
        });
    }

    public void suaNhanVien() {
        int selectedRow = table.getSelectedRow(); // Lấy chỉ số hàng được chọn
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhân viên để sửa!");
            return;
        }
    
        // Lấy thông tin nhân viên từ hàng được chọn
        String maNV = model.getValueAt(selectedRow, 0).toString();
        String tenNV = model.getValueAt(selectedRow, 1).toString();
        String sdt = model.getValueAt(selectedRow, 2).toString();
        String email = model.getValueAt(selectedRow, 3).toString();
        String ngaySinhStr = model.getValueAt(selectedRow, 4).toString(); // Lấy ngày sinh dưới dạng String
        LocalDate ngaySinh = LocalDate.parse(ngaySinhStr, DateTimeFormatter.ofPattern("dd/MM/yyyy")); // Chuyển đổi từ String sang LocalDate
    
        // Tạo hộp thoại để chỉnh sửa thông tin nhân viên
        JTextField txtMaNV = new JTextField(maNV);
        txtMaNV.setEditable(false); // Không cho phép sửa mã nhân viên
        txtMaNV.setFont(new Font("Consolas", Font.PLAIN, 14));
        txtMaNV.setPreferredSize(new Dimension(300, 30)); // Đặt chiều dài cho JTextField

        JTextField txtTenNV = new JTextField(tenNV);
        txtTenNV.setFont(new Font("Consolas", Font.PLAIN, 14));
        txtTenNV.setPreferredSize(new Dimension(300, 30)); // Đặt chiều dài cho JTextField
    
        JTextField txtSDT = new JTextField(sdt);
        txtSDT.setFont(new Font("Consolas", Font.PLAIN, 14));
        txtSDT.setPreferredSize(new Dimension(300, 30)); // Đặt chiều dài cho JTextField

        JTextField txtEmail = new JTextField(email);
        txtEmail.setFont(new Font("Consolas", Font.PLAIN, 14));
        txtEmail.setPreferredSize(new Dimension(300, 30)); // Đặt chiều dài cho JTextField
        
        JTextField txtNgaySinh = new JTextField(ngaySinh.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"))); // Chuyển đổi LocalDate thành String với định dạng dd/MM/yyyy
        txtNgaySinh.setFont(new Font("Consolas", Font.PLAIN, 14));
        txtNgaySinh.setPreferredSize(new Dimension(300, 30)); // Đặt chiều dài cho JTextField

        // Tạo panel để chứa các thành phần
        JPanel panel = new JPanel(new GridLayout(5, 2, 10, 10)); // Sử dụng GridLayout để sắp xếp nhãn và trường nhập
        panel.add(new JLabel("Mã nhân viên:"));
        panel.add(txtMaNV);
        panel.add(new JLabel("Tên nhân viên:"));
        panel.add(txtTenNV);
        panel.add(new JLabel("Số điện thoại:"));
        panel.add(txtSDT);
        panel.add(new JLabel("Email:"));
        panel.add(txtEmail);
        panel.add(new JLabel("Ngày sinh:"));
        panel.add(txtNgaySinh);
    
        // Đặt font cho các JLabel
        for (Component comp : panel.getComponents()) {
            if (comp instanceof JLabel) {
                comp.setFont(new Font("Chalkduster", Font.BOLD, 14));
            }
        }
    
        // Hiển thị hộp thoại với panel
        JOptionPane optionPane = new JOptionPane(panel, JOptionPane.PLAIN_MESSAGE, JOptionPane.OK_CANCEL_OPTION);
        JDialog dialog = optionPane.createDialog("Sửa thông tin nhân viên");
        dialog.setPreferredSize(new Dimension(550, 300)); // Đặt kích thước cho hộp thoại
        dialog.pack(); // Đảm bảo hộp thoại được đóng gói lại với kích thước mới
        dialog.setLocationRelativeTo(null); // Đặt vị trí hộp thoại ở giữa màn hình
        dialog.setVisible(true);
    
        // Xử lý kết quả từ hộp thoại
        if (optionPane.getValue() != null && optionPane.getValue().equals(JOptionPane.OK_OPTION)) {
            // Cập nhật thông tin nhân viên
            try {
                NhanVien_Dao dao = new NhanVien_Dao();
                NhanVien nhanVien = new NhanVien(maNV, txtTenNV.getText(), txtSDT.getText(), txtEmail.getText(), LocalDate.parse(txtNgaySinh.getText(), DateTimeFormatter.ofPattern("dd/MM/yyyy")), new TaiKhoan(txtMaNV.getText()));
                boolean isUpdated = dao.update(nhanVien); // Gọi phương thức cập nhật từ NhanVien_Dao
                if (isUpdated) {
                    JOptionPane.showMessageDialog(this, "Cập nhật thông tin nhân viên thành công!");
                    loadToTable(); // Tải lại bảng để cập nhật dữ liệu
                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên với mã " + maNV + "!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật thông tin nhân viên: " + e.getMessage());
            } catch (Exception e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi chuyển đổi ngày sinh: " + e.getMessage());
            }
        }
    }
    

//    private void xoaNhanVien() {
//        int selectedRow = table.getSelectedRow(); // Lấy chỉ số hàng được chọn
//        if (selectedRow == -1) {
//            JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhân viên để xóa!");
//            return;
//        }
//
//        String maNV = model.getValueAt(selectedRow, 0).toString(); // Lấy mã nhân viên từ cột đầu tiên
//
//        // Kiểm tra xem nhân viên có đang được tham chiếu trong bảng khác không
//        if (isEmployeeReferenced(maNV)) {
//            JOptionPane.showMessageDialog(this,
//                    "Không thể xóa nhân viên này vì nó đang được tham chiếu trong bảng khác!");
//            return;
//        }
//
//        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa nhân viên có mã " + maNV + "?",
//                "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
//        if (confirm == JOptionPane.YES_OPTION) {
//            try {
//                NhanVien_Dao dao = new NhanVien_Dao();
//                boolean isDeleted = dao.delete(maNV); // Gọi phương thức xóa từ NhanVien_Dao
//                if (isDeleted) {
//                    JOptionPane.showMessageDialog(this, "Xóa nhân viên thành công!");
//                    loadToTable(); // Tải lại bảng để cập nhật dữ liệu
//                } else {
//                    JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên với mã " + maNV + "!");
//                }
//            } catch (SQLException e) {
//                e.printStackTrace();
//                JOptionPane.showMessageDialog(this, "Lỗi khi xóa nhân viên: " + e.getMessage());
//            }
//        }
//    }

    private void xoaNhanVien() {
        int selectedRow = table.getSelectedRow(); // Lấy chỉ số hàng được chọn
        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhân viên để xóa!");
            return;
        }

        String maNV = model.getValueAt(selectedRow, 0).toString(); // Lấy mã nhân viên từ cột đầu tiên

        // Kiểm tra xem nhân viên có đang được tham chiếu trong bảng khác không
        if (isEmployeeReferenced(maNV)) {
            JOptionPane.showMessageDialog(this,
                    "Không thể xóa nhân viên này vì nó đang được tham chiếu trong bảng khác!");
            return;
        }

        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn xóa nhân viên có mã " + maNV + "?",
                "Xác nhận xóa", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                NhanVien_Dao dao = new NhanVien_Dao();
                boolean isDeleted = dao.deleteEmployeeAndAccount(maNV); // Gọi phương thức xóa nhân viên và tài khoản
                if (isDeleted) {
                    JOptionPane.showMessageDialog(this, "Xóa nhân viên và tài khoản thành công!");
                    loadToTable(); // Tải lại bảng để cập nhật dữ liệu
                } else {
                    JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên với mã " + maNV + "!");
                }
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(this, "Lỗi khi xóa nhân viên: " + e.getMessage());
            }
        }
    }

    // Phương thức kiểm tra xem nhân viên có đang được tham chiếu không
    private boolean isEmployeeReferenced(String maNV) {
        // Gọi phương thức từ NhanVien_Dao để kiểm tra tham chiếu
        // Ví dụ: kiểm tra trong bảng đơn hàng
        try {
            // Giả sử có phương thức checkEmployeeReferences trong NhanVien_Dao
            NhanVien_Dao dao = new NhanVien_Dao();
            return dao.checkEmployeeReferences(maNV); // Trả về true nếu có tham chiếu
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Nếu có lỗi, giả sử không có tham chiếu
        }
    }

    private void timNhanVien(String searchOption) {
        String searchText = txtTimKiem.getText().trim(); // Lấy nội dung tìm kiếm từ ô nhập liệu
        if (searchText.isEmpty() || searchText.equals("Nhập nội dung tìm kiếm...")) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập nội dung tìm kiếm!");
            return;
        }

        try {
            NhanVien_Dao dao = new NhanVien_Dao();
            ArrayList<NhanVien> dsNV;

            if (searchOption.equals("Tìm theo mã")) {
                dsNV = dao.getAllNhanVienByMa(searchText); // Gọi phương thức tìm theo mã
            } else {
                dsNV = dao.getAllNhanVienByTen(searchText); // Gọi phương thức tìm theo tên
            }

            model.setRowCount(0); // Xóa dữ liệu cũ trong bảng
            for (NhanVien nv : dsNV) {
                model.addRow(
                        new Object[] { nv.getMaNV(), nv.getTenNV(), nv.getSdt(), nv.getEmail(), nv.getNgaySinh() });
            }

            if (dsNV.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên nào!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tìm kiếm nhân viên: " + e.getMessage());
        }
    }

    private void loadToTable() {
        try {
            NhanVien_Dao dao = new NhanVien_Dao();
            ArrayList<NhanVien> dsNV = dao.getAllNhanVien();
            model.setRowCount(0); // Xóa dữ liệu cũ trong bảng
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy"); // Định dạng ngày
    
            for (NhanVien nv : dsNV) {
                model.addRow(new Object[] {
                    nv.getMaNV(),
                    nv.getTenNV(),
                    nv.getSdt(),
                    nv.getEmail(),
                    nv.getNgaySinh().format(formatter) // Định dạng ngày sinh
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu nhân viên!");
        }
    }
}