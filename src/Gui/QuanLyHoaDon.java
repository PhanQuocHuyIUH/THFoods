package Gui;

import DAO.Ban_Dao;
import DAO.ChiTietHoaDon_Dao;
import DAO.HoaDon_Dao;
import DAO.MonAn_Dao;
import DB.Database;
import Entity.Ban;
import Entity.HoaDon;
import Entity.ChiTietHoaDon;
import Entity.MonAn;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class QuanLyHoaDon extends JPanel {

    private JTable hoaDonTable;
    private JTable chiTietTable;
    private JTextField searchField;
    private JComboBox<String> tableComboBox;
    private HoaDon_Dao hoaDon_dao = new HoaDon_Dao();
    private ChiTietHoaDon_Dao chiTietHoaDon_dao = new ChiTietHoaDon_Dao();
    private MonAn_Dao monAn_dao = new MonAn_Dao();

    public QuanLyHoaDon() {
        try {
            Database.getInstance().connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        setLayout(new BorderLayout());

        // Panel chứa các chức năng tìm kiếm và nút, set màu nền trắng
        JPanel searchPanel = new JPanel(new BorderLayout());
        searchPanel.setBackground(Color.WHITE);

        // Panel con chứa phần chọn bàn, set màu nền
        JPanel selectTablePanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        selectTablePanel.setBackground(new Color(230, 240, 255));

        tableComboBox = new JComboBox<>(new String[]{"Tất cả", "Bàn 1", "Bàn 2", "Bàn 3", "Bàn 4", "Bàn 5"});
        JLabel ban = new JLabel("Chọn Bàn:");
        ban.setFont(new Font("Arial Unicode MS", Font.BOLD, 20));
        selectTablePanel.add(ban);
        selectTablePanel.add(tableComboBox);

        // Panel con chứa phần tìm kiếm, set màu nền
        JPanel searchFieldPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 15, 15));
        searchFieldPanel.setBackground(new Color(230, 240, 255));

        searchField = new JTextField(30);
        JLabel searchLabel = new JLabel("\uD83D\uDD0D Tìm Kiếm"); // Icon cho tìm kiếm
        searchLabel.setFont(new Font("Arial Unicode MS", Font.BOLD, 22));
        searchFieldPanel.add(searchLabel);
        searchFieldPanel.add(searchField);

        // Panel con chứa nút làm mới, set màu nền
        JPanel refreshPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 15, 15));
        refreshPanel.setBackground(new Color(230, 240, 255));

        JButton refreshButton = new JButton("\u21BA Làm Mới");
        refreshButton.setFont(new Font("Arial Unicode MS", Font.BOLD, 20));
        refreshButton.setBackground(new Color(105, 165, 225));
        refreshButton.setForeground(Color.WHITE);
        refreshButton.setFocusPainted(false);
        refreshButton.setBorderPainted(false);
        refreshPanel.add(refreshButton);

        // Thêm các panel con vào searchPanel
        searchPanel.add(selectTablePanel, BorderLayout.WEST);
        searchPanel.add(searchFieldPanel, BorderLayout.CENTER);
        searchPanel.add(refreshPanel, BorderLayout.EAST);

        add(searchPanel, BorderLayout.NORTH);

        // Bảng hiển thị hóa đơn
        hoaDonTable = new JTable(new DefaultTableModel(
                new Object[]{"Mã Hóa Đơn", "Số Lượng Món", "Ngày", "Tổng Tiền"}, 0
        ));
        JScrollPane hoaDonScrollPane = new JScrollPane(hoaDonTable);
        hoaDonScrollPane.setPreferredSize(new Dimension(600, 150));
        hoaDonScrollPane.setBorder(new EmptyBorder(10, 0, 0, 0)); // Cách phần header ra một đoạn

        // Trang trí bảng hóa đơn
        Font tableFont = new Font("Arial Unicode MS", Font.PLAIN, 14);
        hoaDonTable.setFont(tableFont);
        hoaDonTable.setRowHeight(30);
        hoaDonTable.setBackground(Color.WHITE);
        hoaDonTable.setForeground(new Color(50, 50, 50));
        hoaDonTable.setSelectionBackground(new Color(0, 0, 255, 150));
        hoaDonTable.setSelectionForeground(Color.WHITE);
        hoaDonTable.setGridColor(new Color(50, 150, 200));

        JTableHeader header = hoaDonTable.getTableHeader();
        header.setBackground(new Color(105, 165, 225));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 14));

        // Bảng chi tiết hóa đơn
        chiTietTable = new JTable(new DefaultTableModel(
                new Object[]{"Mã Hóa Đơn", "Tên Món", "Số Lượng", "Giá", "Thành Tiền"}, 0
        ));
        JScrollPane chiTietScrollPane = new JScrollPane(chiTietTable);
        chiTietScrollPane.setPreferredSize(new Dimension(600, 150));

        // Trang trí bảng chi tiết hóa đơn
        chiTietTable.setFont(tableFont);
        chiTietTable.setRowHeight(30);
        chiTietTable.setBackground(Color.WHITE);
        chiTietTable.setForeground(new Color(50, 50, 50));
        chiTietTable.setSelectionBackground(new Color(0, 0, 255, 150));
        chiTietTable.setSelectionForeground(Color.WHITE);
        chiTietTable.setGridColor(new Color(50, 150, 200));
        JTableHeader header1 = chiTietTable.getTableHeader();
        header1.setBackground(new Color(105, 165, 225));
        header1.setForeground(Color.WHITE);
        header1.setFont(new Font("Arial", Font.BOLD, 14));

        // Panel chứa 2 bảng, set màu nền trắng
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new GridLayout(2, 1));
        tablePanel.setBackground(Color.WHITE);
        tablePanel.add(hoaDonScrollPane);
        tablePanel.add(chiTietScrollPane);

        add(tablePanel, BorderLayout.CENTER);

        // Xử lý sự kiện làm mới
        refreshButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                refreshHoaDon();
            }
        });

        // Xử lý sự kiện nhấn vào hóa đơn để xem chi tiết
        hoaDonTable.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                showChiTietHoaDon();
            }
        });

        // Lấy dữ liệu ban đầu cho bảng hóa đơn
        refreshHoaDon();
    }

    private void refreshHoaDon() {
        try {
            // Lấy tất cả hóa đơn
            List<HoaDon> hoaDons = hoaDon_dao.getAllHD();

            // Sắp xếp các hóa đơn theo ngày tạo (mới nhất trước)
            hoaDons.sort((hd1, hd2) -> hd2.getNgayTao().compareTo(hd1.getNgayTao())); // Sắp xếp giảm dần

            DefaultTableModel hoaDonModel = (DefaultTableModel) hoaDonTable.getModel();
            hoaDonModel.setRowCount(0); // Xóa tất cả các dòng trong bảng

            // Lặp qua từng hóa đơn
            for (HoaDon hoaDon : hoaDons) {
                // Lấy chi tiết hóa đơn cho mỗi hóa đơn
                List<ChiTietHoaDon> chiTietHoaDons = chiTietHoaDon_dao.getCTHDByMaHD(hoaDon.getMaHD());

                // Tính tổng tiền cho hóa đơn
                int tongTien = 0;
                for (ChiTietHoaDon chiTietHoaDon : chiTietHoaDons) {
                    tongTien += chiTietHoaDon.getSoLuong() * chiTietHoaDon.getDonGia();
                }

                // Định dạng ngày tạo hóa đơn (nếu là LocalDateTime)
                String ngayTao = hoaDon.getNgayTao().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));

                // Thêm thông tin vào bảng
                hoaDonModel.addRow(new Object[]{
                        hoaDon.getMaHD(),
                        chiTietHoaDons.size(),
                        ngayTao, // Sử dụng ngày giờ đã định dạng
                        tongTien
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu hóa đơn từ database: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

        // Reset các trường tìm kiếm
        searchField.setText("");
        tableComboBox.setSelectedIndex(0);
    }


    private void showChiTietHoaDon() {
        int selectedRow = hoaDonTable.getSelectedRow();
        if (selectedRow != -1) {
            String maHoaDon = (String) hoaDonTable.getValueAt(selectedRow, 0);
            try {
                List<ChiTietHoaDon> chiTietHoaDons = chiTietHoaDon_dao.getCTHDByMaHD(maHoaDon);
                DefaultTableModel chiTietModel = (DefaultTableModel) chiTietTable.getModel();
                chiTietModel.setRowCount(0);
                for (ChiTietHoaDon chiTiet : chiTietHoaDons) {
                    chiTietModel.addRow(new Object[]{
                            chiTiet.getMaHD(),
                            chiTiet.getMonAn(),
                            chiTiet.getSoLuong(),
                            chiTiet.getDonGia(),
                            chiTiet.getDonGia() * chiTiet.getSoLuong()
                    });
                }
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu chi tiết hóa đơn từ database: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            }
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn để xem chi tiết.");
        }
    }
}
