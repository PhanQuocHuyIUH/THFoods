package Gui;

import javax.swing.*;
import javax.swing.border.MatteBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuanLyNhanVien extends JFrame {

    private static final long serialVersionUID = 1L;
    private JTextField txtMaNV;
    private JTextField txtHoTen;
    private JTextField txtTuoi;
    private JTextField txtLuong;
    private JRadioButton radiobtnNam;
    private JRadioButton radiobtnNu;
    private DefaultTableModel model;
    private JButton btnThem;
    private JButton btnXoa;
    private JButton btnSua;
    private JButton btnTim;
    private JTable table;
    private JButton btnXuatFile;

    public QuanLyNhanVien() {
        // Thiết lập JFrame
        setTitle("Quản Lý Nhân Viên");
        setSize(960, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Đưa màn hình ra giữa
        getContentPane().setLayout(null);

        JPanel panelroot = new JPanel();
        panelroot.setBounds(0, 0, 945, 534);
        panelroot.setLayout(null);
        getContentPane().add(panelroot);

        JButton btnNewButton = new JButton("");
        btnNewButton.setBackground(new Color(255, 255, 255));
        btnNewButton.setIcon(new ImageIcon("D:\\HK1_2024_2025_Nam3\\PhatTrienUngDung\\THFOODS\\icon\\nhanvien.png"));
        btnNewButton.setBounds(10, 48, 146, 191);
        panelroot.add(btnNewButton);

        JLabel lblNewLabel = new JLabel("QUẢN LÝ NHÂN VIÊN");
        lblNewLabel.setForeground(new Color(0, 100, 0));
        lblNewLabel.setFont(new Font("Tahoma", Font.BOLD, 20));
        lblNewLabel.setBounds(369, 0, 250, 45);
        panelroot.add(lblNewLabel);

        JPanel panel = new JPanel();
        panel.setBackground(new Color(255, 255, 255));
        panel.setBorder(new MatteBorder(1, 1, 1, 1, (Color) new Color(192, 192, 192)));
        panel.setBounds(166, 48, 769, 191);
        panel.setLayout(null);
        panelroot.add(panel);

        JLabel lblNewLabel_1 = new JLabel("Mã nhân viên:");
        lblNewLabel_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel_1.setBounds(10, 11, 119, 19);
        panel.add(lblNewLabel_1);

        JLabel lblNewLabel_1_1 = new JLabel("Họ tên:");
        lblNewLabel_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel_1_1.setBounds(10, 51, 119, 19);
        panel.add(lblNewLabel_1_1);

        JLabel lblNewLabel_1_1_1 = new JLabel("Giới tính:");
        lblNewLabel_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel_1_1_1.setBounds(10, 84, 119, 19);
        panel.add(lblNewLabel_1_1_1);

        JLabel lblNewLabel_1_1_1_1 = new JLabel("Tuổi:");
        lblNewLabel_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel_1_1_1_1.setBounds(406, 11, 119, 19);
        panel.add(lblNewLabel_1_1_1_1);

        JLabel lblNewLabel_1_1_1_1_1 = new JLabel("Lương:");
        lblNewLabel_1_1_1_1_1.setFont(new Font("Tahoma", Font.PLAIN, 15));
        lblNewLabel_1_1_1_1_1.setBounds(406, 51, 119, 19);
        panel.add(lblNewLabel_1_1_1_1_1);

        txtMaNV = new JTextField();
        txtMaNV.setBounds(118, 12, 258, 20);
        panel.add(txtMaNV);
        txtMaNV.setColumns(10);

        txtHoTen = new JTextField();
        txtHoTen.setColumns(10);
        txtHoTen.setBounds(118, 52, 258, 20);
        panel.add(txtHoTen);

        txtTuoi = new JTextField();
        txtTuoi.setColumns(10);
        txtTuoi.setBounds(470, 12, 258, 20);
        panel.add(txtTuoi);

        txtLuong = new JTextField();
        txtLuong.setColumns(10);
        txtLuong.setBounds(470, 52, 258, 20);
        panel.add(txtLuong);

        radiobtnNam = new JRadioButton("Nam");
        radiobtnNam.setBackground(new Color(255, 255, 255));
        radiobtnNam.setFont(new Font("Tahoma", Font.PLAIN, 17));
        radiobtnNam.setBounds(128, 84, 111, 23);
        panel.add(radiobtnNam);

        radiobtnNu = new JRadioButton("Nữ");
        radiobtnNu.setBackground(new Color(255, 255, 255));
        radiobtnNu.setFont(new Font("Tahoma", Font.PLAIN, 17));
        radiobtnNu.setBounds(241, 84, 111, 23);
        panel.add(radiobtnNu);

        ButtonGroup btnGroup = new ButtonGroup();
        btnGroup.add(radiobtnNu);
        btnGroup.add(radiobtnNam);

        btnThem = new JButton("THÊM");
        btnThem.setIcon(new ImageIcon("D:\\HK1_2024_2025_Nam3\\PhatTrienUngDung\\THFOODS\\icon\\add.png"));
        btnThem.setBackground(new Color(255, 255, 255));
        btnThem.setFont(new Font("Arial", Font.BOLD, 15));
        btnThem.setBounds(28, 136, 111, 45);
        panel.add(btnThem);

        btnXoa = new JButton("XÓA");
        btnXoa.setIcon(new ImageIcon("D:\\HK1_2024_2025_Nam3\\PhatTrienUngDung\\THFOODS\\icon\\delete.png"));
        btnXoa.setBackground(new Color(255, 255, 255));
        btnXoa.setFont(new Font("Arial", Font.BOLD, 15));
        btnXoa.setBounds(170, 136, 97, 45);
        panel.add(btnXoa);

        btnSua = new JButton("SỬA");
        btnSua.setIcon(new ImageIcon("D:\\HK1_2024_2025_Nam3\\PhatTrienUngDung\\THFOODS\\icon\\fix.png"));
        btnSua.setBackground(new Color(255, 255, 255));
        btnSua.setFont(new Font("Arial", Font.BOLD, 15));
        btnSua.setBounds(301, 136, 97, 45);
        panel.add(btnSua);

        btnTim = new JButton("TÌM KIẾM");
        btnTim.setIcon(new ImageIcon("D:\\HK1_2024_2025_Nam3\\PhatTrienUngDung\\THFOODS\\icon\\find.png"));
        btnTim.setBackground(new Color(255, 255, 255));
        btnTim.setFont(new Font("Arial", Font.BOLD, 15));
        btnTim.setBounds(428, 136, 132, 45);
        panel.add(btnTim);

        btnXuatFile = new JButton("XUẤT FILE");
        btnXuatFile.setIcon(new ImageIcon("D:\\HK1_2024_2025_Nam3\\PhatTrienUngDung\\THFOODS\\icon\\xuatfile.png"));
        btnXuatFile.setBackground(new Color(255, 255, 255));
        btnXuatFile.setFont(new Font("Arial", Font.BOLD, 15));
        btnXuatFile.setBounds(591, 136, 145, 45);
        panel.add(btnXuatFile);

        JPanel jp_table = new JPanel();
        jp_table.setBounds(10, 247, 925, 264);
        jp_table.setLayout(new BoxLayout(jp_table, BoxLayout.X_AXIS));
        panelroot.add(jp_table);

        String[] columnName = {"Mã nhân viên", "Họ tên", "Tuổi", "Giới tính", "Lương"};
        model = new DefaultTableModel(columnName, 0);
        table = new JTable(model);
        table.setRowHeight(30);
        JScrollPane scrollPane = new JScrollPane(table);
        jp_table.add(scrollPane);

        // Thiết lập JFrame visible sau khi đã thiết lập xong mọi thứ
        setVisible(true);
    }

    public static void main(String[] args) {
        new QuanLyNhanVien();
    }
}

