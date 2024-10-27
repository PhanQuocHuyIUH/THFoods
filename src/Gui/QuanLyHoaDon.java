package Gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class QuanLyHoaDon extends JPanel {

    private JTable hoaDonTable;
    private JTable chiTietTable;
    private JTextField searchField;
    private JComboBox<String> tableComboBox;

    public QuanLyHoaDon() {
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
                new Object[]{"Mã Hóa Đơn", "Bàn", "Ngày", "Tổng Tiền"}, 0
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

        // Thêm dữ liệu mẫu vào bảng hóa đơn
        DefaultTableModel hoaDonModel = (DefaultTableModel) hoaDonTable.getModel();
        hoaDonModel.addRow(new Object[]{"HD001", "Bàn 1", "12/10/2024", "500.000đ"});
        hoaDonModel.addRow(new Object[]{"HD002", "Bàn 2", "13/10/2024", "300.000đ"});
        hoaDonModel.addRow(new Object[]{"HD003", "Bàn 3", "14/10/2024", "450.000đ"});
        hoaDonModel.addRow(new Object[]{"HD004", "Bàn 4", "15/10/2024", "700.000đ"});

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
    }

    private void refreshHoaDon() {
        DefaultTableModel model = (DefaultTableModel) hoaDonTable.getModel();
        model.setRowCount(0);
        model.addRow(new Object[]{"HD001", "Bàn 1", "12/10/2024", "500.000đ"});
        model.addRow(new Object[]{"HD002", "Bàn 2", "13/10/2024", "300.000đ"});
        model.addRow(new Object[]{"HD003", "Bàn 3", "14/10/2024", "450.000đ"});
        model.addRow(new Object[]{"HD004", "Bàn 4", "15/10/2024", "700.000đ"});
        searchField.setText("");
        tableComboBox.setSelectedIndex(0);
    }

    private void showChiTietHoaDon() {
        int selectedRow = hoaDonTable.getSelectedRow();
        if (selectedRow != -1) {
            String maHoaDon = (String) hoaDonTable.getValueAt(selectedRow, 0);
            DefaultTableModel model = (DefaultTableModel) chiTietTable.getModel();
            model.setRowCount(0);
            model.addRow(new Object[]{maHoaDon, "Món 1", "2", "100.000đ", "200.000đ"});
            model.addRow(new Object[]{maHoaDon, "Món 2", "1", "150.000đ", "150.000đ"});
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn để xem chi tiết.");
        }
    }
}
