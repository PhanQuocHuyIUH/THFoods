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

import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class QuanLyNhanVien extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField txtMaNV;
    private JTextField txtHoTen;
    private JTextField txtNgaySinh;
    private DefaultTableModel model;
    private JButton btnThem;
    private JButton btnXoa;
    private JButton btnSua;
    private JButton btnTim;
    private JButton btnLuu;
    private JTextField txtSDT;
    private JTextField txtEmail;
    private JTable table;
    private JScrollPane scrollPane;


    /**
     * Create the panel.
     */
    public QuanLyNhanVien() {
        setBackground(new Color(255, 255, 255));
        setForeground(new Color(0, 100, 0));
        setBounds(0, 50, 1372, 800);
        setLayout(null);

        JButton btnImage = new JButton("");
        btnImage.setBackground(new Color(255, 255, 255));
        btnImage.setIcon(new ImageIcon("src\\img\\nhanvien.png"));
        btnImage.setBounds(10, 86, 201, 300);
        add(btnImage);

        JLabel lblNewLabel = new JLabel("QUẢN LÝ NHÂN VIÊN");
        lblNewLabel.setForeground(new Color(0, 100, 0));
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 25));
        lblNewLabel.setBounds(647, 11, 269, 64);
        add(lblNewLabel);

        JPanel jp_main = new JPanel();
        jp_main.setBackground(new Color(255, 255, 255));
        jp_main.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(192, 192, 192)));
        jp_main.setBounds(221, 86, 1141, 300);
        add(jp_main);
        jp_main.setLayout(null);

        JLabel lblMaNV = new JLabel("Mã nhân viên:");
        lblMaNV.setFont(new Font("Arial", Font.BOLD, 16));
        lblMaNV.setBounds(22, 23, 116, 34);
        jp_main.add(lblMaNV);

        JLabel lblHoTen = new JLabel("Họ tên:");
        lblHoTen.setFont(new Font("Arial", Font.BOLD, 16));
        lblHoTen.setBounds(22, 81, 116, 37);
        jp_main.add(lblHoTen);

        JLabel lblNgaySinh = new JLabel("Ngày sinh:");
        lblNgaySinh.setFont(new Font("Arial", Font.BOLD, 16));
        lblNgaySinh.setBounds(593, 22, 96, 37);
        jp_main.add(lblNgaySinh);

        txtMaNV = new JTextField();
        txtMaNV.setFont(new Font("Arial", Font.PLAIN, 14));
        txtMaNV.setBounds(148, 23, 389, 29);
        jp_main.add(txtMaNV);
        txtMaNV.setColumns(10);
        // Cách lề trái cho JTextField
        txtMaNV.setMargin(new Insets(5, 5, 5, 0));

        txtHoTen = new JTextField();
        txtHoTen.setFont(new Font("Arial", Font.PLAIN, 14));
        txtHoTen.setColumns(10);
        txtHoTen.setBounds(148, 81, 389, 29);
        jp_main.add(txtHoTen);
        // Cách lề trái cho JTextField
        txtHoTen.setMargin(new Insets(5, 5, 5, 0));

        txtNgaySinh = new JTextField();
        txtNgaySinh.setFont(new Font("Arial", Font.PLAIN, 14));
        txtNgaySinh.setColumns(10);
        txtNgaySinh.setBounds(727, 27, 389, 29);
        // Cách lề trái cho JTextField
        txtNgaySinh.setMargin(new Insets(5, 5, 5, 0));
        jp_main.add(txtNgaySinh);

        JLabel lblSDT = new JLabel("Số điện thoại:");
        lblSDT.setFont(new Font("Arial", Font.BOLD, 16));
        lblSDT.setBounds(22, 144, 116, 37);
        jp_main.add(lblSDT);

        txtSDT = new JTextField();
        txtSDT.setFont(new Font("Arial", Font.PLAIN, 14));
        txtSDT.setColumns(10);
        txtSDT.setBounds(148, 148, 389, 29);
        txtSDT.setMargin(new Insets(5, 5, 5, 0));
        jp_main.add(txtSDT);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Arial", Font.BOLD, 16));
        lblEmail.setBounds(593, 81, 96, 37);
        jp_main.add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setFont(new Font("Arial", Font.PLAIN, 14));
        txtEmail.setColumns(10);
        txtEmail.setBounds(727, 86, 389, 29);
        txtEmail.setMargin(new Insets(5, 5, 5, 0));
        jp_main.add(txtEmail);


        //nút thêm
        btnThem = new JButton("THÊM");
        btnThem.setIcon(new ImageIcon("src\\img\\add.png"));
        btnThem.setBackground(new Color(255, 255, 255));
        btnThem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                themNhanVien();
            }
        });
        btnThem.setFont(new Font("Chalkduster", Font.BOLD, 18));
        btnThem.setBounds(53, 221, 147, 50);
        jp_main.add(btnThem);

        btnXoa = new JButton("XÓA");
        btnXoa.setBounds(271, 221, 147, 50);
        btnXoa.setIcon(new ImageIcon("src\\img\\delete.png"));
        btnXoa.setBackground(new Color(255, 255, 255));
        btnXoa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                xoaNhanVien();
            }
        });
        btnXoa.setFont(new Font("Chalkduster", Font.BOLD, 18));
        jp_main.add(btnXoa);

        btnSua = new JButton("SỬA");
        btnSua.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                suaNhanVien();
            }
        });
        btnSua.setIcon(new ImageIcon("src\\img\\fix.png"));
        btnSua.setBackground(new Color(255, 255, 255));
        btnSua.setFont(new Font("Chalkduster", Font.BOLD, 18));
        btnSua.setBounds(486, 221, 147, 50);
        jp_main.add(btnSua);

        btnTim = new JButton("TÌM KIẾM");
        btnTim.setIcon(new ImageIcon("src\\img\\find.png"));
        btnTim.setBackground(new Color(255, 255, 255));
        btnTim.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
        btnTim.setFont(new Font("Chalkduster", Font.BOLD, 18));
        btnTim.setBounds(697, 221, 147, 50);
        jp_main.add(btnTim);

        btnLuu = new JButton("LƯU");
        btnLuu.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
        btnLuu.setIcon(new ImageIcon("src\\img\\xuatfile.png"));
        btnLuu.setFont(new Font("Chalkduster", Font.BOLD, 18));
        btnLuu.setBackground(Color.WHITE);
        btnLuu.setBounds(917, 221, 147, 50);
        jp_main.add(btnLuu);

        // Tiêu đề
        String[] columnNames = {"Mã nhân viên", "Họ tên", "Số điện thoại", "Email", "Ngày sinh"};
        model = new DefaultTableModel(columnNames, 0);

        table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 14)); // Đặt cỡ chữ cho các hàng

        // Đặt cỡ chữ cho tiêu đề
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 17)); // Đặt cỡ chữ cho tiêu đề

        table.setRowHeight(20); // Đặt chiều cao cho tất cả các hàng


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
        scrollPane.setBounds(10, 397, 1352, 392);
        add(scrollPane);

        // Dữ liệu cần thêm
        Object[][] data = {
                {"NV01", "Phan Nhật Tiến", "0912345677", "pnt123@gmail.com", "2004-03-27"},
                {"NV02", "Phạm Ngọc Hùng", "0912345678", "pnh456@gmail.com", "2004-12-12"},
                {"NV03", "Nguyễn Văn Tòng", "0912345679", "nvt789@gmail.com", "2004-01-10"},
                {"NV04", "Nguyễn Đinh Xuân Trường", "0912345680", "ntdx000@gmail.com", "2004-02-20"},
                {"NV05", "Trần Thị Thanh Mai", "0912345681", "ttm123@gmail.com", "2004-04-15"},
                {"NV06", "Lê Văn Bình", "0912345682", "lvb456@gmail.com", "2004-05-18"},
                {"NV07", "Ngô Thị Ngọc Lan", "0912345683", "ntnl789@gmail.com", "2004-06-22"},
                {"NV08", "Bùi Minh Châu", "0912345684", "bmc123@gmail.com", "2004-07-30"},
                {"NV09", "Đỗ Quốc Bảo", "0912345685", "dqb456@gmail.com", "2004-08-12"},
                {"NV10", "Hoàng Tấn Đạt", "0912345686", "htd789@gmail.com", "2004-09-25"},
                {"NV11", "Võ Thanh Phong", "0912345687", "vtp123@gmail.com", "2004-10-16"},
                {"NV12", "Phạm Hữu Tài", "0912345688", "pht456@gmail.com", "2004-11-21"},
                {"NV13", "Nguyễn Nhật Quang", "0912345689", "nnq789@gmail.com", "2004-12-31"},
                {"NV14", "Lý Đình Vũ", "0912345690", "ldv123@gmail.com", "2004-03-14"}
        };


        // Thêm dữ liệu vào model
        for (Object[] row : data) {
            model.addRow(row);
        }


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
                        String ngaySinh = (String) table.getValueAt(index, 4);

                        // Hiển thị thông tin lên các JTextField
                        txtMaNV.setText(maNV);
                        txtHoTen.setText(hoTen);
                        txtSDT.setText(sdt);
                        txtEmail.setText(email);
                        txtNgaySinh.setText(ngaySinh);
                    }
                }
            }
        });


    }

    private void themNhanVien() {
        // Kiểm tra dữ liệu nhập vào
        if (txtMaNV.getText().isBlank() || txtHoTen.getText().isBlank() || txtSDT.getText().isBlank() || txtEmail.getText().isBlank() || txtNgaySinh.getText().isBlank()) {
            JOptionPane.showMessageDialog(this, "Vui lòng nhập đầy đủ thông tin!");
            return;
        }
        //Kiểm tra trùng mã nhân viên
        for (int i = 0; i < model.getRowCount(); i++) {
            if (txtMaNV.getText().equals(model.getValueAt(i, 0))) {
                JOptionPane.showMessageDialog(this, "Mã nhân viên đã tồn tại!");
                return;
            }
        }
        // Thêm nhân viên mới vào bảng
        Object[] rowData = {
                txtMaNV.getText(),
                txtHoTen.getText(),
                txtSDT.getText(),
                txtEmail.getText(),
                txtNgaySinh.getText()
        };
        model.addRow(rowData);
        xoaRongTextFields(); // Xóa dữ liệu trong text fields
    }

    private void suaNhanVien() {
        // Sửa thông tin nhân viên đã chọn
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            model.setValueAt(txtMaNV.getText(), selectedRow, 0);
            model.setValueAt(txtHoTen.getText(), selectedRow, 1);
            model.setValueAt(txtSDT.getText(), selectedRow, 2);
            model.setValueAt(txtEmail.getText(), selectedRow, 3);
            model.setValueAt(txtNgaySinh.getText(), selectedRow, 4);
            xoaRongTextFields(); // Xóa dữ liệu trong text fields
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần sửa!");
        }
    }

    private void xoaNhanVien() {
        // Xóa nhân viên đã chọn
        int selectedRow = table.getSelectedRow();
        if (selectedRow != -1) {
            model.removeRow(selectedRow);
            xoaRongTextFields(); // Xóa dữ liệu trong text fields
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn nhân viên cần xóa!");
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


}

