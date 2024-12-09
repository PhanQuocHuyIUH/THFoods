package Gui;

import Entity.HoaDon;
import Entity.ChiTietHoaDon;

import javax.swing.*;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class HoaDonPanel extends JPanel {
    public HoaDonPanel(HoaDon hoaDon) {
        // Thiết lập bố cục và màu sắc cho panel
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS)); // Bố cục dọc
        setBackground(Color.WHITE); // Màu nền trắng
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Cách viền 20 pixel

        // Tiêu đề hóa đơn
        JLabel title = new JLabel("HÓA ĐƠN", SwingConstants.CENTER);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(new Color(0, 102, 204)); // Màu xanh dương
        title.setAlignmentX(CENTER_ALIGNMENT); // Căn giữa

        // Thông tin cửa hàng
        JLabel storeInfo = new JLabel("<html><center>Số 4 Đường Nguyễn Văn Bảo, Gò Vấp<br>SĐT: 0906766000</center></html>", SwingConstants.CENTER);
        storeInfo.setFont(new Font("Arial", Font.PLAIN, 14));
        storeInfo.setForeground(Color.BLACK);
        storeInfo.setAlignmentX(CENTER_ALIGNMENT); // Căn giữa

        // Khu vực thông tin hóa đơn
        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setBackground(Color.WHITE);

        // Format ngày và giờ
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
        String ngayTao = hoaDon.getNgayTao().format(dateFormatter);
        String gioTao = hoaDon.getNgayTao().format(timeFormatter);

        JLabel maHDLabel = new JLabel("Mã hóa đơn: " + hoaDon.getMaHD());
        JLabel ngayTaoLabel = new JLabel("Ngày tạo: " + ngayTao);
        JLabel gioTaoLabel = new JLabel("Giờ xuất: " + gioTao);
        JLabel banLabel = new JLabel("Bàn: Bàn số 5"); // Giả định thông tin bàn

        // Cài đặt phông chữ
        Font infoFont = new Font("Arial", Font.PLAIN, 14);
        maHDLabel.setFont(infoFont);
        ngayTaoLabel.setFont(infoFont);
        gioTaoLabel.setFont(infoFont);
        banLabel.setFont(infoFont);

        // Căn chỉnh các nhãn thông tin
        maHDLabel.setAlignmentX(CENTER_ALIGNMENT);
        ngayTaoLabel.setAlignmentX(CENTER_ALIGNMENT);
        gioTaoLabel.setAlignmentX(CENTER_ALIGNMENT);
        banLabel.setAlignmentX(CENTER_ALIGNMENT);

        infoPanel.add(maHDLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        infoPanel.add(ngayTaoLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        infoPanel.add(gioTaoLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        infoPanel.add(banLabel);

        // Bảng sản phẩm
        String[] columnNames = {"Món ăn", "SL", "Đơn giá", "Tổng"};
        List<ChiTietHoaDon> chiTietHoaDons = hoaDon.getChiTietHoaDon();
        Object[][] data = new Object[chiTietHoaDons.size()][4];

        double tongTien = 0;
        for (int i = 0; i < chiTietHoaDons.size(); i++) {
            ChiTietHoaDon chiTiet = chiTietHoaDons.get(i);
            data[i][0] = chiTiet.getMonAn();
            data[i][1] = chiTiet.getSoLuong();
            data[i][2] = chiTiet.getDonGia();
            data[i][3] = chiTiet.getSoLuong() * chiTiet.getDonGia();
            tongTien += chiTiet.getSoLuong() * chiTiet.getDonGia();
        }

        JTable table = new JTable(data, columnNames);
        table.setFont(new Font("Arial", Font.PLAIN, 14));
        table.setRowHeight(25);
        table.setEnabled(false); // Không cho chỉnh sửa
        table.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(240, 240, 240)); // Màu nền header
        table.getTableHeader().setForeground(Color.BLACK);

        // Tùy chỉnh độ rộng cột
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(150); // Món ăn
        columnModel.getColumn(1).setPreferredWidth(50);  // Số lượng
        columnModel.getColumn(2).setPreferredWidth(100); // Đơn giá
        columnModel.getColumn(3).setPreferredWidth(100); // Tổng

        JScrollPane tableScrollPane = new JScrollPane(table);
        tableScrollPane.setAlignmentX(CENTER_ALIGNMENT);
        tableScrollPane.setPreferredSize(new Dimension(300, 120)); // Đặt chiều rộng cố định

        // Tổng tiền
        JPanel totalPanel = new JPanel();
        totalPanel.setLayout(new BoxLayout(totalPanel, BoxLayout.Y_AXIS));
        totalPanel.setBackground(Color.WHITE);

        JLabel totalLabel = new JLabel("Tổng tiền: " + String.format("%.2f", tongTien) + " VND");

        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));
        totalLabel.setForeground(new Color(255, 51, 51)); // Màu đỏ nổi bật
        totalLabel.setAlignmentX(CENTER_ALIGNMENT);

        totalPanel.add(totalLabel);

        // Cảm ơn khách hàng
        JLabel thankYouLabel = new JLabel("CẢM ƠN QUÝ KHÁCH!", SwingConstants.CENTER);
        thankYouLabel.setFont(new Font("Arial", Font.BOLD, 16));
        thankYouLabel.setForeground(new Color(0, 102, 204)); // Màu xanh dương
        thankYouLabel.setAlignmentX(CENTER_ALIGNMENT);

        // Thêm các thành phần vào panel
        add(title);
        add(Box.createRigidArea(new Dimension(0, 10))); // Khoảng cách
        add(storeInfo);
        add(Box.createRigidArea(new Dimension(0, 10))); // Khoảng cách
        add(infoPanel);
        add(Box.createRigidArea(new Dimension(0, 10))); // Khoảng cách
        add(tableScrollPane);
        add(Box.createRigidArea(new Dimension(0, 10))); // Khoảng cách
        add(totalPanel);
        add(Box.createRigidArea(new Dimension(0, 10))); // Khoảng cách
        add(thankYouLabel);
    }
}
