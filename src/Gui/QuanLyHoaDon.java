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
import javax.swing.table.DefaultTableCellRenderer;
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
        )) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Trả về false để ngăn chỉnh sửa mọi ô
                return false;
            }
        };;
        JScrollPane hoaDonScrollPane = new JScrollPane(hoaDonTable);
        hoaDonScrollPane.setPreferredSize(new Dimension(600, 150));
        hoaDonScrollPane.setBorder(new EmptyBorder(10, 0, 0, 0)); // Cách phần header ra một đoạn

        // Trang trí bảng hóa đơn
        hoaDonTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        hoaDonTable.setRowHeight(30);
        hoaDonTable.setBackground(AppColor.trang);
        hoaDonTable.setForeground(AppColor.den);
        hoaDonTable.setGridColor(AppColor.xanh);
        hoaDonTable.setFillsViewportHeight(true);
        hoaDonTable.setDefaultEditor(Object.class, null);
        hoaDonTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if(isSelected){
                    c.setBackground(AppColor.xanhNhat);
                }
                else if (row % 2 == 0) {
                    c.setBackground(AppColor.xam);
                } else {
                    c.setBackground(AppColor.trang);
                }
                return c;
            }
        });

        //đổi màu dòng khi có event click
        JTableHeader header = hoaDonTable.getTableHeader();
        header.setBackground(AppColor.xanh);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 14));

        // Bảng chi tiết hóa đơn
        chiTietTable = new JTable(new DefaultTableModel(
                new Object[]{"Mã Hóa Đơn", "Tên Món", "Số Lượng", "Giá", "Thành Tiền"}, 0
        )) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Trả về false để ngăn chỉnh sửa mọi ô
                return false;
            }
        };;
        JScrollPane chiTietScrollPane = new JScrollPane(chiTietTable);
        chiTietScrollPane.setPreferredSize(new Dimension(600, 150));

        // Trang trí bảng chi tiết hóa đơn
        chiTietTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        chiTietTable.setRowHeight(30);
        chiTietTable.setBackground(AppColor.trang);
        chiTietTable.setForeground(AppColor.den);
        chiTietTable.setGridColor(AppColor.xanh);
        chiTietTable.setFillsViewportHeight(true);
        chiTietTable.setDefaultEditor(Object.class, null);
        chiTietTable.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if(isSelected){
                    c.setBackground(AppColor.xanhNhat);
                }
                else if (row % 2 == 0) {
                    c.setBackground(AppColor.xam);
                } else {
                    c.setBackground(AppColor.trang);
                }
                return c;
            }
        });

        //đổi màu dòng khi có event click
        JTableHeader headerDetail = chiTietTable.getTableHeader();
        headerDetail.setBackground(AppColor.xanh);
        headerDetail.setForeground(Color.WHITE);
        headerDetail.setFont(new Font("Arial", Font.BOLD, 14));

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
