package Gui;

import javax.swing.*;

import java.awt.*;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
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

public class QuanLyNhanVien extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField txtMaNV;
    private JTextField txtHoTen;
    private JTextField txtNgaySinh;
    private JTextField txtSDT;
    private JTextField txtEmail;
    private DefaultTableModel model;
    private JButton btnXoa;
    private JButton btnSua;
    private JButton btnTim;
    private JTable table;
    private JScrollPane scrollPane;

    /**
     * Create the panel.
     */
    public QuanLyNhanVien() {

        setBackground(new Color(255, 255, 255));
        setForeground(new Color(0, 100, 0));
        setBounds(0, 50, 1390, 800);
        setLayout(null);

        JLabel lblNewLabel = new JLabel("QUẢN LÝ NHÂN VIÊN");
        lblNewLabel.setForeground(new Color(0, 0, 0));
        lblNewLabel.setFont(new Font("Consolas", Font.BOLD, 24));
        lblNewLabel.setBounds(587, 11, 249, 64);
        add(lblNewLabel);

        JPanel jp_main = new JPanel();
        jp_main.setBackground(new Color(255, 255, 255));
        jp_main.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(192, 192, 192)));
        jp_main.setBounds(10, 75, 1359, 269);
        add(jp_main);
        jp_main.setLayout(null);

        JLabel lblMaNV = new JLabel("Mã nhân viên:");
        lblMaNV.setFont(new Font("Chalkduster", Font.BOLD, 14));
        lblMaNV.setBounds(64, 23, 116, 34);
        jp_main.add(lblMaNV);

        JLabel lblHoTen = new JLabel("Họ tên:");
        lblHoTen.setFont(new Font("Chalkduster", Font.BOLD, 14));
        lblHoTen.setBounds(64, 81, 116, 37);
        jp_main.add(lblHoTen);

        JLabel lblNgaySinh = new JLabel("Ngày sinh:");
        lblNgaySinh.setFont(new Font("Chalkduster", Font.BOLD, 14));
        lblNgaySinh.setBounds(765, 22, 96, 37);
        jp_main.add(lblNgaySinh);

        JLabel lblSDT = new JLabel("Số điện thoại:");
        lblSDT.setFont(new Font("Chalkduster", Font.BOLD, 14));
        lblSDT.setBounds(64, 144, 116, 37);
        jp_main.add(lblSDT);

        JLabel lblEmail = new JLabel("Email");
        lblEmail.setFont(new Font("Chalkduster", Font.BOLD, 14));
        lblEmail.setBounds(765, 81, 96, 37);
        jp_main.add(lblEmail);

        txtMaNV = new JTextField();
        txtMaNV.setBounds(219, 29, 450, 29);
        jp_main.add(txtMaNV);
        txtMaNV.setFont(new Font("Consolas", Font.PLAIN, 14));
        txtMaNV.setBackground(new Color(230, 240, 255));
        txtMaNV.setColumns(10);
        // Cách lề trái cho JTextField
        txtMaNV.setMargin(new Insets(0, 5, 0, 0));

        txtHoTen = new JTextField();
        txtHoTen.setFont(new Font("Consolas", Font.PLAIN, 14));
        txtHoTen.setColumns(10);
        txtHoTen.setBounds(219, 88, 450, 29);
        txtHoTen.setBackground(new Color(230, 240, 255));
        jp_main.add(txtHoTen);
        // Cách lề trái cho JTextField
        txtHoTen.setMargin(new Insets(0, 5, 0, 0));

        txtNgaySinh = new JTextField();
        txtNgaySinh.setFont(new Font("Consolas", Font.PLAIN, 14));
        txtNgaySinh.setColumns(10);
        txtNgaySinh.setBounds(890, 29, 416, 29);
        txtNgaySinh.setBackground(new Color(230, 240, 255));
        // Cách lề trái cho JTextField
        txtNgaySinh.setMargin(new Insets(0, 5, 0, 0));
        jp_main.add(txtNgaySinh);

        txtSDT = new JTextField();
        txtSDT.setFont(new Font("Consolas", Font.PLAIN, 14));
        txtSDT.setColumns(10);
        txtSDT.setBounds(219, 151, 450, 29);
        txtSDT.setBackground(new Color(230, 240, 255));
        txtSDT.setMargin(new Insets(0, 5, 0, 0));
        jp_main.add(txtSDT);

        txtEmail = new JTextField();
        txtEmail.setFont(new Font("Consolas", Font.PLAIN, 14));
        txtEmail.setColumns(10);
        txtEmail.setBounds(890, 88, 416, 29);
        txtEmail.setBackground(new Color(230, 240, 255));
        txtEmail.setMargin(new Insets(0, 5, 0, 0));
        jp_main.add(txtEmail);

        btnXoa = new RoundedButton("XÓA");
        btnXoa.setForeground(new Color(255, 255, 255));
        btnXoa.setBounds(485, 209, 96, 34);
        btnXoa.setIcon(new ImageIcon("src\\img\\delete.png"));
        btnXoa.setBackground(new Color(105, 165, 225));
        btnXoa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                xoaNhanVien();
            }
        });
        btnXoa.setFont(new Font("Segoe UI", Font.BOLD, 14));
        jp_main.add(btnXoa);

        btnSua = new RoundedButton("SỬA");
        btnSua.setForeground(new Color(255, 255, 255));
        btnSua.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                suaNhanVien();
            }
        });
        btnSua.setIcon(new ImageIcon("src\\img\\fix.png"));
        btnSua.setBackground(new Color(105, 165, 225));
        btnSua.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnSua.setBounds(657, 209, 96, 34);
        jp_main.add(btnSua);

        btnTim = new RoundedButton("TÌM");
        btnTim.setForeground(new Color(255, 255, 255));
        btnTim.setIcon(new ImageIcon("src\\img\\find.png"));
        btnTim.setBackground(new Color(105, 165, 225));
        btnTim.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timNhanVien();
            }
        });
        btnTim.setFont(new Font("Segoe UI", Font.BOLD, 14));
        btnTim.setBounds(817, 209, 96, 34);
        jp_main.add(btnTim);

        // Tiêu đề
        String[] columnNames = { "Mã nhân viên", "Họ tên", "Số điện thoại", "Email", "Ngày sinh" };
        model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Đặt cỡ chữ cho các hàng

        // Đặt cỡ chữ cho tiêu đề
        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(105, 165, 225));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setPreferredSize(new Dimension(100, 30)); // Đặt chiều cao cho tiêu đề
        table.setRowHeight(30); // Đặt chiều cao cho tất cả các hàng
        table.setBackground(new Color(230, 240, 255)); // Màu nền cho bảng
        table.setForeground(new Color(10, 10, 10)); // Màu chữ cho bảng
        table.setSelectionBackground(new Color(0, 0, 255, 150)); // Màu nền khi chọn hàng
        table.setSelectionForeground(new Color(255, 255, 255)); // Màu chữ khi chọn hàng

        // Tạo custom cell renderer
        DefaultTableCellRenderer paddingRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                    boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (c instanceof JLabel) {
                    ((JLabel) c).setBorder(new EmptyBorder(0, 5, 0, 0)); // Cách lề trái 5 pixel
                }
                return c;
            }
        };

        // Áp dụng renderer cho tất cả các cột
        for (int i = 0; i < table.getColumnCount(); i++) {
            table.getColumnModel().getColumn(i).setCellRenderer(paddingRenderer);
        }

        scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 366, 1359, 423);
        add(scrollPane);

        // Thêm ListSelectionListener vào bảng
        table.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if (!e.getValueIsAdjusting()) {
                    int index = table.getSelectedRow();
                    if (index != -1) {
                        // Lấy thông tin từ hàng đã chọn
                        String maNV = (String) table.getValueAt(index, 0);
                        String hoTen = (String) table.getValueAt(index, 1);
                        String sdt = (String) table.getValueAt(index, 2);
                        String email = (String) table.getValueAt(index, 3);
                        LocalDate ngaySinh = (LocalDate) table.getValueAt(index, 4); // Lấy giá trị LocalDate

                        // Chuyển đổi LocalDate thành String với định dạng "dd/MM/yyyy"
                        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                        String ngaySinhString = ngaySinh != null ? ngaySinh.format(formatter) : "";

                        // Hiển thị thông tin lên các JTextField
                        txtMaNV.setText(maNV);
                        txtHoTen.setText(hoTen);
                        txtSDT.setText(sdt);
                        txtEmail.setText(email);
                        txtNgaySinh.setText(ngaySinhString); // Gán giá trị đã chuyển đổi
                    }
                }
            }
        });
        // Gọi phương thức loadToTable() để nạp dữ liệu vào bảng
        loadToTable();

    }

    public void suaNhanVien() {
        // Lấy chỉ số dòng đã chọn trong bảng
        int index = table.getSelectedRow();
        if (index == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một nhân viên để sửa.");
            return;
        }
    
        // Lấy giá trị từ các JTextField
        String maNV = txtMaNV.getText();
        String hoTen = txtHoTen.getText();
        String sdt = txtSDT.getText();
        String email = txtEmail.getText();
        String ngaySinhInput = txtNgaySinh.getText();
    
        // Tạo DateTimeFormatter với định dạng "dd/MM/yyyy"
        DateTimeFormatter inputFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        LocalDate ngaySinh;
    
        try {
            // Phân tích chuỗi ngày tháng thành LocalDate
            ngaySinh = LocalDate.parse(ngaySinhInput, inputFormatter);
        } catch (DateTimeParseException e) {
            JOptionPane.showMessageDialog(this, "Ngày sinh không hợp lệ. Vui lòng nhập theo định dạng dd/MM/yyyy.");
            return;
        }
    
        // Cập nhật thông tin nhân viên trong bảng
        NhanVien nhanVien = new NhanVien(maNV, hoTen, sdt, email, ngaySinh); // Sử dụng constructor 5 tham số
        NhanVien_Dao dao = new NhanVien_Dao();
        
        try {
            dao.update(nhanVien); // Gọi phương thức update mà không cần gán giá trị
            // Cập nhật lại bảng
            loadToTable(); // Gọi phương thức để nạp lại dữ liệu vào bảng
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi cập nhật thông tin nhân viên!");
        }
    }

    private void xoaNhanVien() {
        String maNV = txtMaNV.getText();
        try {
            NhanVien_Dao dao = new NhanVien_Dao();
            dao.delete(maNV);
            loadToTable(); // Cập nhật bảng sau khi xóa
            xoaRongTextFields(); // Xóa dữ liệu trong các trường nhập
            JOptionPane.showMessageDialog(this, "Xóa nhân viên thành công!");
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi xóa nhân viên!");
        }
    }

    private void timNhanVien() {
        String maNV = txtMaNV.getText();
        try {
            NhanVien_Dao dao = new NhanVien_Dao();
            NhanVien nhanVien = dao.select(maNV);
            if (nhanVien != null) {
                txtHoTen.setText(nhanVien.getTenNV());
                txtSDT.setText(nhanVien.getSdt());
                txtEmail.setText(nhanVien.getEmail());
                
                // Định dạng lại ngày sinh
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                String ngaySinhString = nhanVien.getNgaySinh() != null ? nhanVien.getNgaySinh().format(formatter) : "";
                txtNgaySinh.setText(ngaySinhString);
            } else {
                JOptionPane.showMessageDialog(this, "Không tìm thấy nhân viên!");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tìm nhân viên!");
        }
    }

    private void loadToTable() {
        try {
            NhanVien_Dao dao = new NhanVien_Dao();
            ArrayList<NhanVien> dsNV = dao.getAllNhanVien();
            model.setRowCount(0); // Xóa dữ liệu cũ trong bảng
            for (NhanVien nv : dsNV) {
                model.addRow(
                        new Object[] { nv.getMaNV(), nv.getTenNV(), nv.getSdt(), nv.getEmail(), nv.getNgaySinh() });
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu nhân viên!");
        }
    }

    private void xoaRongTextFields() {
        // Xóa dữ liệu trong các trường nhập
        txtMaNV.setText("");
        txtHoTen.setText("");
        txtSDT.setText("");
        txtEmail.setText("");
        txtNgaySinh.setText("");
    }

    // Lớp RoundedButton bên trong để tạo nút bo tròn
    private class RoundedButton extends JButton {
        public RoundedButton(String text) {
            super(text);
            setContentAreaFilled(false);
            setOpaque(false);
            setBorderPainted(false);
        }

        @Override
        protected void paintComponent(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            // Vẽ nền bo góc tròn
            g2.setColor(getBackground());
            g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);

            // Khoảng cách giữa icon và văn bản
            int iconTextGap = 5;

            // Tính toán vị trí của icon và văn bản
            int iconX = 10; // Đặt icon cách lề trái 10px
            int iconY = (getHeight() - (getIcon() != null ? getIcon().getIconHeight() : 0)) / 2;

            int textX = iconX + (getIcon() != null ? getIcon().getIconWidth() : 0) + iconTextGap;
            FontMetrics fm = g2.getFontMetrics();
            int textY = (getHeight() + fm.getAscent()) / 2 - 2;

            // Vẽ icon nếu có
            if (getIcon() != null) {
                getIcon().paintIcon(this, g2, iconX, iconY);
            }

            // Vẽ văn bản
            g2.setColor(getForeground());
            g2.drawString(getText(), textX, textY);

            g2.dispose();
        }

        @Override
        protected void paintBorder(Graphics g) {
            Graphics2D g2 = (Graphics2D) g.create();
            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
            g2.setColor(getForeground());
            g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 15, 15);
            g2.dispose();
        }
    }
}
