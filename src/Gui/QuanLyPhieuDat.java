package Gui;

import DAO.Ban_Dao;
import DAO.HoaDon_Dao;
import DAO.PhieuDatMon_Dao;
import DB.Database;
import Entity.*;
import DAO.MonAn_Dao;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;

public class QuanLyPhieuDat extends JPanel {
    private JTable tableChiTietPhieu;
    private DefaultTableModel tableModel;
    private JLabel lblTongTien, lblGhiChu, lblSoLuongBanDangDung;
    private JButton btnThanhToan, btnXoaChiTiet, btnXoaPhieuDat, btnThemMon;
    private JTextField txtTimKiemBan;
    private JPanel panelBan;
    private ArrayList<Ban> dsBan;
    private Ban_Dao banDao;
    private HoaDon hoaDon = new HoaDon();
    private HoaDon_Dao hoaDonDao = new HoaDon_Dao();
    private ChiTietHoaDon chiTietHoaDon = new ChiTietHoaDon();
    private MonAn_Dao monAnDao = new MonAn_Dao();
    private PhieuDatMon_Dao phieuDatMonDao = new PhieuDatMon_Dao();
    private JLabel lblBan;
    private JLabel lblSoPhieu;
    private JLabel lblNhanVien;

    public QuanLyPhieuDat() {
        try {
            Database.getInstance().connect();
            banDao = new Ban_Dao();
            dsBan = new ArrayList<Ban>();
            initUI();
            loadBanFromDatabase(); // Load dữ liệu từ cơ sở dữ liệu
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Khởi tạo giao diện
    private void initUI() {
        setLayout(new BorderLayout());
        // Panel trái chứa bàn
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.setPreferredSize(new Dimension(700, getHeight()));
        leftPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        leftPanel.setBackground(new Color(105, 165, 225));

        // Phần tìm kiếm bàn
        JPanel searchPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchPanel.setBackground(new Color(230, 240, 255));
        JLabel lblTimKiem = new JLabel("🔍 Tìm kiếm:");
        lblTimKiem.setFont(new Font("Arial Unicode MS", Font.PLAIN, 15));
        txtTimKiemBan = new JTextField(20);

        searchPanel.add(lblTimKiem);
        searchPanel.add(txtTimKiemBan);
        leftPanel.add(searchPanel, BorderLayout.NORTH);

        // Hiển thị số lượng bàn đang dùng
        lblSoLuongBanDangDung = new JLabel("Bàn đang dùng: 0");
        lblSoLuongBanDangDung.setFont(new Font("Arial Unicode MS", Font.BOLD, 14));
        lblSoLuongBanDangDung.setForeground(new Color(0, 102, 204));
        searchPanel.add(lblSoLuongBanDangDung, BorderLayout.SOUTH);

        // Phần hiển thị các bàn với cuộn dọc
        panelBan = new JPanel();
        panelBan.setLayout(new GridLayout(0, 2, 10, 10));
        JScrollPane scrollPaneBan = new JScrollPane(panelBan);
        scrollPaneBan.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPaneBan.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPaneBan.getVerticalScrollBar().setUnitIncrement(20);// Tăng tốc độ cuộn
        leftPanel.add(scrollPaneBan, BorderLayout.CENTER);
        add(leftPanel, BorderLayout.WEST);

        // Panel phải chứa chi tiết phiếu
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        rightPanel.setBackground(new Color(255, 255, 255));

        // Bảng chi tiết phiếu
        String[] columnNames = {"Món ăn", "Số lượng", "Đơn giá", "Thành tiền"};
        tableModel = new DefaultTableModel(columnNames, 0);
        tableChiTietPhieu = new JTable(tableModel);
        JScrollPane scrollPanePhieu = new JScrollPane(tableChiTietPhieu);
        rightPanel.add(scrollPanePhieu, BorderLayout.NORTH);

        tableChiTietPhieu.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tableChiTietPhieu.setRowHeight(30);
        tableChiTietPhieu.setBackground(new Color(230, 240, 255));
        tableChiTietPhieu.setSelectionBackground(new Color(0, 0, 255, 150));
        tableChiTietPhieu.setSelectionForeground(Color.WHITE);

        JTableHeader header = tableChiTietPhieu.getTableHeader();
        header.setBackground(new Color(105, 165, 225));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 14));

        // Ghi chú và tổng tiền
        JPanel panelThongTin = new JPanel(new GridLayout(5, 2));
        panelThongTin.setBackground(new Color(255, 255, 255));
        // bên trái là tiêu đề bên phải là hiển thị nội dung
        JLabel lblTieuDeGhiChu = new JLabel("       \uD83D\uDCDD Ghi chú :");
        lblTieuDeGhiChu.setFont(new Font("Arial Unicode MS", Font.BOLD, 16));
        lblGhiChu = new JLabel();
        lblGhiChu.setFont(new Font("Arial Unicode MS", Font.BOLD, 16));
        JLabel lblTieuDeSoPhieu = new JLabel("      \uD83D\uDCC5 Ngày :");
        lblTieuDeSoPhieu.setFont(new Font("Arial Unicode MS", Font.BOLD, 16));
        lblSoPhieu = new JLabel();
        lblSoPhieu.setFont(new Font("Arial Unicode MS", Font.BOLD, 16));
        JLabel lblTenNhanVien = new JLabel("      \uD83D\uDC64 Nhân viên:");
        lblTenNhanVien.setFont(new Font("Arial Unicode MS", Font.BOLD, 16));
        lblNhanVien = new JLabel();
        JLabel lblTieuDeBan = new JLabel("      \u25A4 Bàn:");
        lblTieuDeBan.setFont(new Font("Arial Unicode MS", Font.BOLD, 16));
        lblBan = new JLabel();
        lblBan.setFont(new Font("Arial Unicode MS", Font.BOLD, 16));
        lblTongTien = new JLabel("\uD83D\uDCB0 Tổng tiền : 0 ₫");
        lblTongTien.setFont(new Font("Arial Unicode MS", Font.BOLD, 20));
        lblTongTien.setForeground(new Color(0, 102, 204));

        panelThongTin.add(lblTieuDeSoPhieu);
        panelThongTin.add(lblSoPhieu);
        panelThongTin.add(lblTenNhanVien);
        panelThongTin.add(lblNhanVien);
        panelThongTin.add(lblTieuDeBan);
        panelThongTin.add(lblBan);
        panelThongTin.add(lblTieuDeGhiChu);
        panelThongTin.add(lblGhiChu);
        panelThongTin.add(lblTongTien);
        rightPanel.add(panelThongTin, BorderLayout.CENTER);

        // Các nút điều khiển
        JPanel panelButtons = new JPanel(new FlowLayout(FlowLayout.CENTER));
        panelButtons.setBackground(new Color(255, 255, 255));
        btnThemMon = createStyledButton("\uD83C\uDF7D Thêm món", e -> themMon());
        btnThemMon.setFont(new Font("Arial Unicode MS", Font.BOLD, 22));
        btnThanhToan = createStyledButton("\uD83D\uDCB5 Thanh toán", e -> thanhToan());
        btnThanhToan.setFont(new Font("Arial Unicode MS", Font.BOLD, 22));
        btnXoaChiTiet = createStyledButton("\uD83D\uDDD1\uFE0F\n", e -> xoaChiTiet());
        btnXoaChiTiet.setFont(new Font("Arial Unicode MS", Font.BOLD, 30));
        btnXoaChiTiet.setBackground(new Color(255, 255, 255));
        btnXoaChiTiet.setForeground(new Color(255, 0, 0));

        panelButtons.add(btnThemMon);
        panelButtons.add(Box.createRigidArea(new Dimension(10, 0)));
        panelButtons.add(btnThanhToan);
        panelButtons.add(Box.createRigidArea(new Dimension(10, 0)));
        panelButtons.add(btnXoaChiTiet);
        rightPanel.add(panelButtons, BorderLayout.SOUTH);

        add(rightPanel, BorderLayout.CENTER);
    }


    // Tải danh sách bàn từ cơ sở dữ liệu
    public void loadBanFromDatabase() {
        try {
            panelBan.removeAll();
            dsBan = banDao.getAllBan();
            int banDangDung = 0;

            for (Ban ban : dsBan) {
                // nếu bàn có trạng thái đang dùng thì mới hiển thị
                if (ban.getTrangThai() != TrangThaiBan.DangDung) {
                    continue;
                }
                JPanel panelItem = new JPanel(new BorderLayout());
                panelItem.setBorder(BorderFactory.createLineBorder(Color.BLUE, 2));
                panelItem.setBackground(new Color(255, 255, 255));

                JLabel lblImage = new JLabel(new ImageIcon("src/img/datban.jpg"));
                panelItem.add(lblImage, BorderLayout.CENTER);

                String trangThai = ban.getTrangThai().toString();
                if (ban.getTrangThai()== TrangThaiBan.DangDung) {
                    banDangDung++;
                }
                // lấy so phieu theo mã bàn từ phương thức getSoPhieu() của Ban_Dao
                String soPhieu = banDao.getSoPhieu(ban.getMaBan());
                JLabel lblThongTin = new JLabel("<html>Mã bàn: " + ban.getMaBan() + "<br>Trạng thái: " + trangThai + "</html>");
                lblThongTin.setHorizontalAlignment(SwingConstants.CENTER);
                lblThongTin.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
                panelItem.add(lblThongTin, BorderLayout.SOUTH);

                panelItem.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        loadSampleDataForTable(ban.getMaBan());
                    }
                });

                // add action để khi nhấn vào panelTiem sẽ highlight màu và viền còn lại sẽ mất highlight
                panelItem.addMouseListener(new java.awt.event.MouseAdapter() {
                    public void mouseClicked(java.awt.event.MouseEvent evt) {
                        for (Component c : panelBan.getComponents()) {
                            c.setBackground(new Color(255, 255, 255));
                        }
                        panelItem.setBackground(new Color(255, 255, 224));
                    }
                });

                panelBan.add(panelItem);
            }
            lblSoLuongBanDangDung.setText("Bàn đang dùng: " + banDangDung);
            panelBan.revalidate();
            panelBan.repaint();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void themMon() {
        //hiển thị trang đặt món và lấy mã bàn
        String maBan = lblBan.getText();
        //tạo frame mơ chua dat mon
        JFrame frame = new JFrame("Thêm món");
        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        frame.setSize(1000, 700);
        frame.setLocationRelativeTo(null);
        frame.setLayout(new BorderLayout());
        frame.add(new DatMon(maBan));
        frame.setVisible(true);
        //nếu frame đóng thì load lại table chi tiết phiếu
        frame.addWindowListener(new java.awt.event.WindowAdapter() {
            @Override
            public void windowClosed(java.awt.event.WindowEvent windowEvent) {
                loadSampleDataForTable(lblBan.getText());
            }
        });
    }

    private void thanhToan() {
        try {
            // Tạo hóa đơn mới
            hoaDon = new HoaDon();
            LocalDateTime current = LocalDateTime.now();
            hoaDon.setNgayTao(current);

            // Mã hóa đơn sẽ là số thứ tự tiếp theo của hd
            hoaDon.setMaHD("HD" + (hoaDonDao.getAllHD().size() + 1));

            // Các chi tiết hóa đơn sẽ lấy từ bảng chi tiết phiếu
            hoaDon.setChiTietHoaDon(new ArrayList<>());
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String tenMon = (String) tableModel.getValueAt(i, 0);

                // Lấy đơn giá và chuyển sang kiểu double
                String donGiaString = (String) tableModel.getValueAt(i, 2); // Lấy giá trị đơn giá dưới dạng String
                double donGia = Double.parseDouble(donGiaString);  // Chuyển đổi thành double

                int soLuong = (int) tableModel.getValueAt(i, 1);

                // Thêm chi tiết hóa đơn vào danh sách
                hoaDon.getChiTietHoaDon().add(new ChiTietHoaDon(hoaDon.getMaHD(), tenMon, soLuong, donGia));
            }

            // Thêm hóa đơn vào cơ sở dữ liệu
            hoaDonDao.createHD(hoaDon);

            // Thêm chi tiết hóa đơn vào cơ sở dữ liệu
            for (ChiTietHoaDon cthd : hoaDon.getChiTietHoaDon()) {
                hoaDonDao.createCTHD(cthd);
            }

            // Lấy mã bàn từ text bàn
            String maBan = lblBan.getText();

            // Lấy danh sách mã phiếu theo mã bàn
            ArrayList<String> dsPhieu = banDao.getDSPhieu(maBan);
            System.out.println(dsPhieu);

            // Chạy vòng lập xóa chi tiết phiếu theo mã phiếu
            for (String maPhieu : dsPhieu) {
                banDao.deleteCTPhieu(maPhieu);
            }

            // Xóa phiếu theo mã bàn
            banDao.deletePhieu(maBan);

            // Cập nhật trạng thái bàn
            banDao.updateTrangThaiBan(maBan,"Trong");

            // Cập nhật table và danh sách bàn
            tableModel.setRowCount(0);
            loadBanFromDatabase();

            JOptionPane.showMessageDialog(this, "Thanh toán thành công!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    private void xoaChiTiet() {
        int selectedRow = tableChiTietPhieu.getSelectedRow();
        if (selectedRow != -1) {
            tableModel.removeRow(selectedRow);
            JOptionPane.showMessageDialog(this, "Món đã được xóa!");
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn món để xóa!");
        }
    }

    private void xoaPhieuDat() {
        int confirm = JOptionPane.showConfirmDialog(this, "Bạn có chắc muốn xóa phiếu này?");
        if (confirm == JOptionPane.YES_OPTION) {

            JOptionPane.showMessageDialog(this, "Phiếu đã được xóa!");
            loadBanFromDatabase();
        }
    }

    private JButton createStyledButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setBackground(new Color(105, 165, 225));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 20));
        button.addActionListener(actionListener);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        return button;
    }

    // Hàm để tải dữ liệu mẫu cho bảng chi tiết phiếu từ số lượng phiếu
    private void loadSampleDataForTable(String maBan) {
        tableModel.setRowCount(0); // Xóa các hàng cũ trong bảng

        // lấy danh sách phiếu theo mã bàn
        ArrayList<String> dsPhieu = new ArrayList<>();
        try {
            dsPhieu = banDao.getDSPhieu(maBan);
        } catch (SQLException e) {
            e.printStackTrace();
        }
        //Lấy danh sách chi tiết phiếu theo mã phiếu
        ArrayList<ChiTietDatMon> dsCTPDM = new ArrayList<>();
        for (String maPhieu : dsPhieu) {
            try {
                dsCTPDM.addAll(banDao.getDSCTPhieu(maPhieu));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //lấy thông tin món ăn từ mã món ăn
        for (ChiTietDatMon ctpdm : dsCTPDM) {
            String maMon = ctpdm.getMaMon();
            //lấy tên món ăn
            String tenMon = "";
            try {
                tenMon = monAnDao.getTenMon(maMon);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            int soLuong = ctpdm.getSoLuong();
            double donGia = 0;
            try {
                donGia = monAnDao.getGiaMonAn(maMon);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            double thanhTien = donGia * soLuong;
            //nếu món ăn đã có trong bảng thì cộng thêm số lượng
            boolean isExist = false;
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                if (tableModel.getValueAt(i, 0).equals(tenMon)) {
                    tableModel.setValueAt((int) tableModel.getValueAt(i, 1) + soLuong, i, 1);
                    tableModel.setValueAt((double) tableModel.getValueAt(i, 2), i, 2);
                    tableModel.setValueAt((double) tableModel.getValueAt(i, 3) + thanhTien, i, 3);
                    isExist = true;
                    break;
                }
            }
            //nếu món ăn chưa có trong bảng thì thêm vào
            if (!isExist) {
                tableModel.addRow(new Object[]{tenMon, soLuong, donGia, thanhTien});
            }
        }
        //cập nhật tổng tiền
        double tongTien = 0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            tongTien += (double) tableModel.getValueAt(i, 3);
        }
        lblTongTien.setText("\uD83D\uDCB0 Tổng tiền: " + tongTien + " ₫");
        //lấy phiếu đặt món theo mã phiếu
        ArrayList<PhieuDatMon> dsPhieuDatMon = new ArrayList<>();
        for (String maPhieu : dsPhieu) {
            try {
                dsPhieuDatMon.add(phieuDatMonDao.getPhieuDatMonByMaPDB(maPhieu));
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        //lấy ghi chú từ phiếu đặt món
        String ghiChu = "";
        for (PhieuDatMon phieuDatMon : dsPhieuDatMon) {
            if(!phieuDatMon.getGhiChu().equals("")){
                ghiChu += phieuDatMon.getGhiChu() + ", ";
        }
        LocalDate localDate = LocalDate.now();
        //Lấy số phiếu từ số phiếu
        lblSoPhieu.setText(localDate.toString());
        lblGhiChu.setText( ghiChu);
        lblBan.setText( maBan);
        lblNhanVien.setText("Nhân viên");
    }}

}
