package Gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;

public class QuanLyPhieuDat extends JPanel {
    private JTable tableChiTietPhieu;
    private DefaultTableModel tableModel;
    private JLabel lblTongTien, lblGhiChu, lblSoLuongBanDangDung;
    private JButton btnThanhToan, btnXoaChiTiet, btnXoaPhieuDat;
    private JTextField txtTimKiemBan;
    private JPanel panelBan;

    public QuanLyPhieuDat() {
        setLayout(new BorderLayout());
        initUI();
        loadSampleBan();
    }

    private void initUI() {
        // Panel trái chứa bàn
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(600, getHeight())); // Mở rộng chiều rộng
        leftPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        leftPanel.setBackground(new Color(105, 165, 225)); // Màu nền cho panel trái

        // Phần tìm kiếm bàn
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        //set màu nền cho searchPanel
        searchPanel.setBackground(new Color(230, 240, 255));
        JLabel lblTimKiem = new JLabel("\uD83D\uDD0D Tìm kiếm:");
        lblTimKiem.setFont(new Font("Arial Unicode MS", Font.PLAIN, 15));
        txtTimKiemBan = new JTextField(20);

        searchPanel.add(lblTimKiem);
        searchPanel.add(txtTimKiemBan);
        leftPanel.add(searchPanel, BorderLayout.NORTH);

        // Phần hiển thị số lượng bàn đang dùng
        lblSoLuongBanDangDung = new JLabel("   Bàn đang dùng: 0");
        lblSoLuongBanDangDung.setFont(new Font("Arial Unicode MS", Font.BOLD, 14));
        lblSoLuongBanDangDung.setForeground(new Color(0, 102, 204));
        searchPanel.add(lblSoLuongBanDangDung, BorderLayout.SOUTH);

        // Phần hiển thị các bàn với cuộn dọc
        panelBan = new JPanel();
        panelBan.setLayout(new GridLayout(0, 2, 10, 10)); // Hiển thị bàn theo dạng hai cột
        JScrollPane scrollPaneBan = new JScrollPane(panelBan);
        scrollPaneBan.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneBan.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Bỏ cuộn ngang
        leftPanel.add(scrollPaneBan, BorderLayout.CENTER);
        add(leftPanel, BorderLayout.WEST);

        // Panel phải chứa chi tiết phiếu
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        rightPanel.setBackground(new Color(255, 255, 255)); // Màu nền cho panel phải

        // Tạo bảng chi tiết phiếu
        String[] columnNames = {"Món ăn", "Số lượng", "Đơn giá", "Thành tiền"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tableChiTietPhieu = new JTable(tableModel);
        JScrollPane scrollPanePhieu = new JScrollPane(tableChiTietPhieu);
        rightPanel.add(scrollPanePhieu, BorderLayout.NORTH);

        tableChiTietPhieu.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tableChiTietPhieu.setRowHeight(30);
        tableChiTietPhieu.setBackground(new Color(230, 240, 255));
        tableChiTietPhieu.setForeground(new Color(50, 50, 50));
        tableChiTietPhieu.setSelectionBackground(new Color(0, 0, 255, 150));
        tableChiTietPhieu.setSelectionForeground(Color.WHITE);
        tableChiTietPhieu.setGridColor(new Color(50, 150, 200));

        JTableHeader header = tableChiTietPhieu.getTableHeader();
        header.setBackground(new Color(105, 165, 225));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 14));

        // Phần ghi chú và tổng tiền
        JPanel panelThongTin = new JPanel(new GridLayout(2, 1));
        panelThongTin.setBorder(new EmptyBorder(10, 0, 10, 0)); // Thêm khoảng cách
        lblGhiChu = new JLabel("Ghi chú: ", JLabel.LEFT);
        lblGhiChu.setFont(new Font("Arial", Font.BOLD, 16));
        lblGhiChu.setForeground(new Color(0, 102, 204));

        lblTongTien = new JLabel("Tổng tiền: 0.0", JLabel.LEFT);
        lblTongTien.setFont(new Font("Arial", Font.BOLD, 16));
        lblTongTien.setForeground(new Color(255, 51, 51));

        panelThongTin.add(lblGhiChu);
        panelThongTin.add(lblTongTien);
        rightPanel.add(panelThongTin, BorderLayout.CENTER);

        // Panel chứa các nút điều khiển
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        btnThanhToan = new JButton("Thanh toán");
        btnThanhToan.setBackground(new Color(0, 102, 204));
        btnThanhToan.setForeground(Color.WHITE);
        btnXoaChiTiet = new JButton("Xóa chi tiết");
        btnXoaChiTiet.setBackground(new Color(204, 0, 0));
        btnXoaChiTiet.setForeground(Color.WHITE);
        btnXoaPhieuDat = new JButton("Xóa phiếu đặt");
        btnXoaPhieuDat.setBackground(new Color(255, 51, 51));
        btnXoaPhieuDat.setForeground(Color.WHITE);

        panelButtons.add(btnThanhToan);
        panelButtons.add(btnXoaChiTiet);
        panelButtons.add(btnXoaPhieuDat);
        rightPanel.add(panelButtons, BorderLayout.SOUTH);

        add(rightPanel, BorderLayout.CENTER);
    }

    // Hàm tải dữ liệu mẫu cho các bàn
    private void loadSampleBan() {
        panelBan.removeAll();
        Random rand = new Random();
        int banDangDung = 0; // Biến đếm số lượng bàn đang dùng
        for (int i = 1; i <= 15; i++) {
            JPanel panelItem = new JPanel();
            panelItem.setLayout(new BorderLayout());
            panelItem.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            panelItem.setBackground(new Color(255, 255, 224)); // Màu nền cho bàn

            // Hình ảnh bàn
            JLabel lblImage = new JLabel(new ImageIcon("src/img/datban.jpg")); // Thay đường dẫn bằng hình ảnh thật
            panelItem.add(lblImage, BorderLayout.CENTER);

            // Số phiếu và trạng thái
            int soPhieu = rand.nextInt(3); // Số phiếu ngẫu nhiên từ 0 đến 2
            if (soPhieu > 0) {
                banDangDung++; // Tăng số lượng bàn đang dùng nếu có phiếu
            }
            String trangThai = soPhieu > 0 ? "Đang dùng" : "Trống";
            JLabel lblThongTin = new JLabel("<html>Mã bàn: Bàn " + i + "<br>Số phiếu: " + soPhieu + "<br>Trạng thái: " + trangThai + "</html>");
            lblThongTin.setHorizontalAlignment(SwingConstants.CENTER);
            panelItem.add(lblThongTin, BorderLayout.SOUTH);

            // Xử lý sự kiện khi nhấn vào bàn
            panelItem.addMouseListener(new java.awt.event.MouseAdapter() {
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    loadSampleDataForTable(soPhieu); // Giả lập nạp chi tiết phiếu
                }
            });

            panelBan.add(panelItem);
        }
        lblSoLuongBanDangDung.setText("    Bàn đang dùng: " + banDangDung);
        panelBan.revalidate();
        panelBan.repaint();
    }

    // Hàm tải dữ liệu mẫu cho bảng chi tiết phiếu
    private void loadSampleDataForTable(int soPhieu) {
        tableModel.setRowCount(0); // Xóa các hàng cũ
        Random rand = new Random();
        double tongTien = 0;
        for (int i = 0; i < soPhieu; i++) {
            String tenMon = "Món " + (i + 1);
            int soLuong = rand.nextInt(5) + 1;
            double donGia = rand.nextDouble() * 100;
            double thanhTien = soLuong * donGia;
            tongTien += thanhTien;

            Object[] row = {tenMon, soLuong, String.format("%.2f", donGia), String.format("%.2f", thanhTien)};
            tableModel.addRow(row);
        }
        lblTongTien.setText("Tổng tiền: " + String.format("%.2f", tongTien));
        lblGhiChu.setText("Ghi chú: Phiếu có " + soPhieu + " chi tiết.");
    }
}
