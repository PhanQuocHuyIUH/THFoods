package Gui;

import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JOptionPane;

import java.awt.Font;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

import java.awt.BorderLayout;
import java.awt.Color;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.BoxLayout;
import javax.swing.UIManager;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;
import java.awt.event.ActionEvent;
import java.awt.FlowLayout;

public class  QuanLyNhanVien extends JPanel {

    private static final long serialVersionUID = 1L;
    private JTextField txtMaNV;
    private JTextField txtHoTen;
    private JTextField txtNgaySinh;
    private DefaultTableModel model;
    private JButton btnThem;
    private JButton btnXoa;
    private JButton btnSua;
    private JButton btnTim;
    private JButton btnXuatFile;
    private JLabel lblSDT;
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
        setBounds(0, 50, 1750, 983);
        setLayout(null);

        JButton btnImage = new JButton("");
        btnImage.setBackground(new Color(255, 255, 255));
        btnImage.setIcon(new ImageIcon("D:\\HK1_2024_2025_Nam3\\PhatTrienUngDung\\THFOODS\\THFoods\\src\\img\\nhanvien.png"));
        btnImage.setBounds(10, 86, 283, 394);
        add(btnImage);

        JLabel lblNewLabel = new JLabel("QUẢN LÝ NHÂN VIÊN");
        lblNewLabel.setForeground(new Color(0, 100, 0));
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 40));
        lblNewLabel.setBounds(640, 11, 443, 64);
        add(lblNewLabel);

        JPanel jp_main = new JPanel();
        jp_main.setBackground(new Color(255, 255, 255));
        jp_main.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(192, 192, 192)));
        jp_main.setBounds(303, 86, 1437, 394);
        add(jp_main);
        jp_main.setLayout(null);

        JLabel lblMaNV = new JLabel("Mã nhân viên:");
        lblMaNV.setFont(new Font("Arial", Font.BOLD, 25));
        lblMaNV.setBounds(10, 23, 171, 34);
        jp_main.add(lblMaNV);

        JLabel lblHoTen = new JLabel("Họ tên:");
        lblHoTen.setFont(new Font("Arial", Font.BOLD, 25));
        lblHoTen.setBounds(10, 93, 171, 37);
        jp_main.add(lblHoTen);

        JLabel lblNgaySinh = new JLabel("Ngày sinh:");
        lblNgaySinh.setFont(new Font("Arial", Font.BOLD, 25));
        lblNgaySinh.setBounds(776, 22, 147, 37);
        jp_main.add(lblNgaySinh);

        txtMaNV = new JTextField();
        txtMaNV.setFont(new Font("Arial", Font.PLAIN, 17));
        txtMaNV.setBounds(200, 23, 528, 29);
        jp_main.add(txtMaNV);
        txtMaNV.setColumns(10);

        txtHoTen = new JTextField();
        txtHoTen.setFont(new Font("Arial", Font.PLAIN, 17));
        txtHoTen.setColumns(10);
        txtHoTen.setBounds(200, 93, 528, 29);
        jp_main.add(txtHoTen);

        txtNgaySinh = new JTextField();
        txtNgaySinh.setFont(new Font("Arial", Font.PLAIN, 17));
        txtNgaySinh.setColumns(10);
        txtNgaySinh.setBounds(935, 23, 492, 29);
        jp_main.add(txtNgaySinh);

        ButtonGroup btnGroup=new ButtonGroup();

        //nút thêm
        btnThem = new JButton("THÊM");
        btnThem.setIcon(new ImageIcon("D:\\HK1_2024_2025_Nam3\\PhatTrienUngDung\\THFOODS\\THFoods\\src\\img\\add.png"));
        btnThem.setBackground(new Color(255, 255, 255));
        btnThem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                themNhanVien();
            }
        });
        btnThem.setFont(new Font("Arial", Font.BOLD, 25));
        btnThem.setBounds(41, 302, 195, 67);
        jp_main.add(btnThem);

        btnXoa = new JButton("XÓA");
        btnXoa.setBounds(330, 302, 195, 67);
        btnXoa.setIcon(new ImageIcon("D:\\HK1_2024_2025_Nam3\\PhatTrienUngDung\\THFOODS\\THFoods\\src\\img\\delete.png"));
        btnXoa.setBackground(new Color(255, 255, 255));
        btnXoa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                xoaNhanVien();
            }
        });
        btnXoa.setFont(new Font("Arial", Font.BOLD, 25));
        jp_main.add(btnXoa);

        btnSua = new JButton("SỬA");
        btnSua.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                suaNhanVien();
            }
        });
        btnSua.setIcon(new ImageIcon("D:\\HK1_2024_2025_Nam3\\PhatTrienUngDung\\THFOODS\\THFoods\\src\\img\\fix.png"));
        btnSua.setBackground(new Color(255, 255, 255));
        btnSua.setFont(new Font("Arial", Font.BOLD, 25));
        btnSua.setBounds(615, 302, 195, 67);
        jp_main.add(btnSua);

        btnTim = new JButton("TÌM KIẾM");
        btnTim.setIcon(new ImageIcon("D:\\HK1_2024_2025_Nam3\\PhatTrienUngDung\\THFOODS\\THFoods\\src\\img\\find.png"));
        btnTim.setBackground(new Color(255, 255, 255));
        btnTim.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
        btnTim.setFont(new Font("Arial", Font.BOLD, 25));
        btnTim.setBounds(910, 301, 195, 68);
        jp_main.add(btnTim);

        btnXuatFile = new JButton("XUẤT FILE");
        btnXuatFile.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {

            }
        });
        btnXuatFile.setIcon(new ImageIcon("D:\\HK1_2024_2025_Nam3\\PhatTrienUngDung\\THFOODS\\THFoods\\src\\img\\xuatfile.png"));
        btnXuatFile.setFont(new Font("Arial", Font.BOLD, 25));
        btnXuatFile.setBackground(Color.WHITE);
        btnXuatFile.setBounds(1185, 302, 195, 67);
        jp_main.add(btnXuatFile);



        lblSDT = new JLabel("Số điện thoại:");
        lblSDT.setFont(new Font("Arial", Font.BOLD, 25));
        lblSDT.setBounds(10, 175, 171, 37);
        jp_main.add(lblSDT);

        txtSDT = new JTextField();
        txtSDT.setFont(new Font("Arial", Font.PLAIN, 17));
        txtSDT.setColumns(10);
        txtSDT.setBounds(200, 175, 526, 29);
        jp_main.add(txtSDT);

        JLabel lblEmail = new JLabel("Email:");
        lblEmail.setFont(new Font("Arial", Font.BOLD, 25));
        lblEmail.setBounds(776, 93, 149, 37);
        jp_main.add(lblEmail);

        txtEmail = new JTextField();
        txtEmail.setFont(new Font("Arial", Font.PLAIN, 17));
        txtEmail.setColumns(10);
        txtEmail.setBounds(935, 93, 492, 29);
        jp_main.add(txtEmail);

        // Tiêu đề
        String[] columnNames = {"Mã nhân viên", "Họ tên", "Số điện thoại", "Email", "Ngày sinh"};
        model = new DefaultTableModel(columnNames, 0);

        table = new JTable(model);
        table.setFont(new Font("Arial", Font.PLAIN, 17)); // Đặt cỡ chữ cho các hàng

        // Đặt cỡ chữ cho tiêu đề
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 20)); // Đặt cỡ chữ cho tiêu đề

        table.setRowHeight(30); // Đặt chiều cao cho tất cả các hàng

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(10, 491, 1730, 481);
        add(scrollPane);

        // Dữ liệu cần thêm
        Object[][] data = {
                {"NV01", "Phan Nhật Tiến", "0912345677", "pnt123@gmail.com", "2004-03-27"},
                {"NV02", "Phạm Ngọc Hùng", "0912345678", "pnh456@gmail.com", "2004-12-12"},
                {"NV03", "Nguyễn Văn Tòng", "0912345679", "nvt789@gmail.com", "2004-01-10"},
                {"NV04", "Nguyễn Đinh Xuân Trường", "0912345680", "ntdx000@gmail.com", "2004-02-20"}
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
