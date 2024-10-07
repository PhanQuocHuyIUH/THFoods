package Gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class QuanLyHoaDon extends JFrame
{
    private DefaultTableModel modelTable;
    private DefaultTableModel modelHoaDonMiddle;
    private JTable table;
    private JTable tableHoaDonMiddle;
    private JTextField searchField;
    private JLabel lblMaHoaDon;
    private JLabel lblKhachHang;
    private JLabel lblSoDienThoai;
    private JLabel lblThoiGianTao;
    private JLabel lblGhiChu;
    private JLabel lblTrangThai;
    private JLabel lblBan;
    private JLabel lblTongTien;

    private JButton btnSearch;

    public QuanLyHoaDon()
    {
        // Left side
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        // Label header
        JLabel lbHead = new JLabel("LỊCH SỬ HÓA ĐƠN");
        lbHead.setFont(new Font("Arial",Font.BOLD, 24));
        lbHead.setForeground(Color.BLUE);

        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(lbHead);
        leftPanel.add(Box.createVerticalStrut(20));

        JPanel pHead = new JPanel();
        JLabel lblMaHD = new JLabel("Mã HD: ");
        pHead.add(lblMaHD);
        pHead.add(Box.createHorizontalStrut(10));
        pHead.add(searchField =new

                JTextField(20));
        pHead.add(Box.createHorizontalStrut(10));
        btnSearch =new

                JButton("Tìm");
        btnSearch.setFont(new

                Font("Arial",Font.BOLD, 15));
        btnSearch.setForeground(Color.BLACK);
        btnSearch.setBackground(new

                Color(109,183,252));
        pHead.add(btnSearch);
        pHead.add(Box.createVerticalStrut(20));

        leftPanel.add(pHead);
        // Add North panel to the main panel
        leftPanel.add(Box.createVerticalStrut(20));

        // Center - Table - Information
        String[] cols = {"Mã HD", "Người tạo", "Khách hàng", "Thời gian tạo", "Tổng tiền", "Trạng thái"};
        modelTable =new

                DefaultTableModel(cols, 0);

        table =new

                JTable(modelTable);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);

        JScrollPane scrollPane = new JScrollPane(table);
        leftPanel.add(new

                JScrollPane(table));
        leftPanel.add(Box.createVerticalStrut(20));

        // Right side
        JPanel rightPanel = new JPanel(new BorderLayout());

        // Right Middle - Table
        JPanel middle = new JPanel();
        middle.setLayout(new

                BoxLayout(middle, BoxLayout.Y_AXIS));
        JLabel middleLabel = new JLabel("HÓA ĐƠN CHI TIẾT");
        middleLabel.setFont(new

                Font("Arial",Font.BOLD, 24));
        middleLabel.setForeground(Color.BLUE);
        middleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        middle.add(Box.createVerticalStrut(20));
        middle.add(middleLabel);
        middle.add(Box.createVerticalStrut(20));

        // Right - table
        String[] colsMiddle = {"Tên Món", "Đơn giá", "Số lượng", "Thành tiền", "Ghi chú"};
        modelHoaDonMiddle =new

                DefaultTableModel(colsMiddle, 0);

        JTable tableHoaDonMiddle = new JTable(modelHoaDonMiddle);
        JScrollPane scrollPaneMiddle = new JScrollPane(tableHoaDonMiddle);
        scrollPaneMiddle.setPreferredSize(new

                Dimension(450,300));
        middle.add(scrollPaneMiddle);
        middle.add(Box.createVerticalStrut(20));

        // Add middle to the center of rightPanel
        rightPanel.add(middle,BorderLayout.CENTER);

        // Create 1 panel under the table
        JPanel rightBottomPanel = new JPanel();
        rightBottomPanel.setLayout(new

                GridLayout(1,2));

        // Right - Left
        JPanel rightLeft = new JPanel();
        rightLeft.setLayout(new

                BoxLayout(rightLeft, BoxLayout.Y_AXIS));

        lblMaHoaDon =new

                JLabel("Mã hóa đơn: ");
        rightLeft.add(lblMaHoaDon);
        rightLeft.add(Box.createVerticalStrut(10));

        lblKhachHang =new

                JLabel("Khách hàng: ");
        rightLeft.add(lblKhachHang);
        rightLeft.add(Box.createVerticalStrut(10));

        lblSoDienThoai =new

                JLabel("Số điện thoại: ");
        rightLeft.add(lblSoDienThoai);
        rightLeft.add(Box.createVerticalStrut(10));

        lblThoiGianTao =new

                JLabel("Thời gian tạo: ");
        rightLeft.add(lblThoiGianTao);
        rightLeft.add(Box.createVerticalStrut(10));


        // Right - Right
        JPanel rightRight = new JPanel();
        rightRight.setLayout(new

                BoxLayout(rightRight, BoxLayout.Y_AXIS));

        lblTrangThai =new

                JLabel("Trạng thái: ");
        rightRight.add(lblTrangThai);
        rightRight.add(Box.createVerticalStrut(10));

        lblTongTien =new

                JLabel("Tổng tiền hóa đơn: ");
        rightRight.add(lblTongTien);
        rightRight.add(Box.createVerticalStrut(10));

        // Add rightRight and rightLeft into 1 panel
        rightBottomPanel.add(rightLeft);
        rightBottomPanel.add(rightRight);

        rightPanel.add(rightBottomPanel,BorderLayout.PAGE_END);

        // Add leftPanel to the west and rightPanel to the east
        add(leftPanel, BorderLayout.WEST);

        add(rightPanel, BorderLayout.EAST);
    }

    public static void main(String[] args)
    {
        QuanLyHoaDon quanLyHoaDon = new QuanLyHoaDon();
        quanLyHoaDon.setTitle("Quản lý hóa đơn");
        quanLyHoaDon.setSize(800, 600);
        quanLyHoaDon.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        quanLyHoaDon.setVisible(true);
    }

}