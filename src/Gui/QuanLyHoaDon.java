package Gui;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class QuanLyHoaDon extends JPanel {

    private JTable hoaDonTable; // Bảng hiển thị danh sách hóa đơn
    private JTable chiTietTable; // Bảng hiển thị chi tiết hóa đơn
    private JTextField searchField; // Ô tìm kiếm mã hóa đơn
    private JComboBox<String> tableComboBox; // ComboBox để chọn số bàn

    public QuanLyHoaDon() {
        setLayout(new BorderLayout());

        // Panel trên cùng chứa các chức năng tìm kiếm và nút, làm rộng hơn
        JPanel searchPanel = new JPanel();
        searchPanel.setLayout(new GridLayout(2, 1));
        searchPanel.setPreferredSize(new Dimension(800, 100));
        searchPanel.setBackground(Color.LIGHT_GRAY); // Trang trí màu nền

        JPanel searchSubPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        // Tạo ComboBox để tìm kiếm theo bàn
        tableComboBox = new JComboBox<>(new String[]{"Tất cả", "Bàn 1", "Bàn 2", "Bàn 3", "Bàn 4", "Bàn 5"});
        searchSubPanel.add(new JLabel("Chọn Bàn:"));
        searchSubPanel.add(tableComboBox);

        // Ô tìm kiếm theo mã hóa đơn
        searchField = new JTextField(15);
        searchSubPanel.add(new JLabel("Tìm kiếm mã hóa đơn:"));
        searchSubPanel.add(searchField);

        // Nút tìm kiếm
        JButton searchButton = new JButton("Tìm Kiếm");
        searchSubPanel.add(searchButton);

        // Nút làm mới
        JButton refreshButton = new JButton("Làm Mới");
        searchSubPanel.add(refreshButton);

        // Font và màu sắc cho các nút
        searchButton.setFont(new Font("Arial", Font.BOLD, 14));
        searchButton.setBackground(new Color(60, 179, 113)); // Màu xanh lá
        searchButton.setForeground(Color.WHITE);

        refreshButton.setFont(new Font("Arial", Font.BOLD, 14));
        refreshButton.setBackground(new Color(30, 144, 255)); // Màu xanh da trời
        refreshButton.setForeground(Color.WHITE);

        searchPanel.add(searchSubPanel);
        add(searchPanel, BorderLayout.NORTH);

        // Bảng hiển thị hóa đơn
        hoaDonTable = new JTable(new DefaultTableModel(
                new Object[]{"Mã Hóa Đơn", "Bàn", "Ngày", "Tổng Tiền"}, 0
        ));
        JScrollPane hoaDonScrollPane = new JScrollPane(hoaDonTable);
        hoaDonScrollPane.setPreferredSize(new Dimension(600, 150));

        // Trang trí bảng hóa đơn
        hoaDonTable.setFont(new Font("Arial", Font.PLAIN, 14));
        hoaDonTable.setRowHeight(25);
        hoaDonTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        hoaDonTable.getTableHeader().setBackground(new Color(70, 130, 180)); // Xanh biển
        hoaDonTable.getTableHeader().setForeground(Color.WHITE);

        // Bảng chi tiết hóa đơn
        chiTietTable = new JTable(new DefaultTableModel(
                new Object[]{"Mã Hóa Đơn", "Tên Món", "Số Lượng", "Giá", "Thành Tiền"}, 0
        ));
        JScrollPane chiTietScrollPane = new JScrollPane(chiTietTable);
        chiTietScrollPane.setPreferredSize(new Dimension(600, 150));

        // Trang trí bảng chi tiết hóa đơn
        chiTietTable.setFont(new Font("Arial", Font.PLAIN, 14));
        chiTietTable.setRowHeight(25);
        chiTietTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        chiTietTable.getTableHeader().setBackground(new Color(70, 130, 180)); // Xanh biển
        chiTietTable.getTableHeader().setForeground(Color.WHITE);

        // Panel chứa 2 bảng
        JPanel tablePanel = new JPanel();
        tablePanel.setLayout(new GridLayout(2, 1));
        tablePanel.add(hoaDonScrollPane);
        tablePanel.add(chiTietScrollPane);

        add(tablePanel, BorderLayout.CENTER);

        // Thêm dữ liệu mẫu vào bảng hóa đơn
        DefaultTableModel hoaDonModel = (DefaultTableModel) hoaDonTable.getModel();
        hoaDonModel.addRow(new Object[]{"HD001", "Bàn 1", "12/10/2024", "500.000đ"});
        hoaDonModel.addRow(new Object[]{"HD002", "Bàn 2", "13/10/2024", "300.000đ"});
        hoaDonModel.addRow(new Object[]{"HD003", "Bàn 3", "14/10/2024", "450.000đ"});
        hoaDonModel.addRow(new Object[]{"HD004", "Bàn 4", "15/10/2024", "700.000đ"});

        // Xử lý sự kiện tìm kiếm
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                searchHoaDon();
            }
        });

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

    // Giả lập tìm kiếm hóa đơn
    private void searchHoaDon() {
        String maHoaDon = searchField.getText();
        String selectedTable = tableComboBox.getSelectedItem().toString();

        // Ví dụ giả lập thêm dữ liệu vào bảng hóa đơn
        DefaultTableModel model = (DefaultTableModel) hoaDonTable.getModel();
        model.setRowCount(0); // Xóa các dòng hiện tại trong bảng

        // Dữ liệu mẫu dựa trên điều kiện tìm kiếm
        if (selectedTable.equals("Tất cả") && maHoaDon.isEmpty()) {
            model.addRow(new Object[]{"HD001", "Bàn 1", "12/10/2024", "500.000đ"});
            model.addRow(new Object[]{"HD002", "Bàn 2", "13/10/2024", "300.000đ"});
            model.addRow(new Object[]{"HD003", "Bàn 3", "14/10/2024", "450.000đ"});
            model.addRow(new Object[]{"HD004", "Bàn 4", "15/10/2024", "700.000đ"});
        } else if (!maHoaDon.isEmpty()) {
            // Giả lập lọc theo mã hóa đơn
            model.addRow(new Object[]{"HD001", "Bàn 1", "12/10/2024", "500.000đ"});
        } else if (!selectedTable.equals("Tất cả")) {
            // Giả lập lọc theo bàn
            if (selectedTable.equals("Bàn 1")) {
                model.addRow(new Object[]{"HD001", "Bàn 1", "12/10/2024", "500.000đ"});
            }
        }
    }

    // Giả lập làm mới bảng hóa đơn
    private void refreshHoaDon() {
        DefaultTableModel model = (DefaultTableModel) hoaDonTable.getModel();
        model.setRowCount(0);
        // Giả lập thêm lại dữ liệu sau khi làm mới
        model.addRow(new Object[]{"HD001", "Bàn 1", "12/10/2024", "500.000đ"});
        model.addRow(new Object[]{"HD002", "Bàn 2", "13/10/2024", "300.000đ"});
        model.addRow(new Object[]{"HD003", "Bàn 3", "14/10/2024", "450.000đ"});
        model.addRow(new Object[]{"HD004", "Bàn 4", "15/10/2024", "700.000đ"});
        searchField.setText(""); // Xóa nội dung ô tìm kiếm
        tableComboBox.setSelectedIndex(0); // Đặt lại lựa chọn bàn
    }

    // Hiển thị chi tiết hóa đơn khi nhấn vào bảng hóa đơn
    private void showChiTietHoaDon() {
        int selectedRow = hoaDonTable.getSelectedRow();
        if (selectedRow != -1) {
            // Lấy mã hóa đơn từ hàng được chọn
            String maHoaDon = (String) hoaDonTable.getValueAt(selectedRow, 0);

            // Giả lập dữ liệu chi tiết hóa đơn
            DefaultTableModel model = (DefaultTableModel) chiTietTable.getModel();
            model.setRowCount(0); // Xóa các dòng hiện tại trong bảng chi tiết
            model.addRow(new Object[]{maHoaDon, "Món 1", "2", "100.000đ", "200.000đ"});
            model.addRow(new Object[]{maHoaDon, "Món 2", "1", "150.000đ", "150.000đ"});
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn hóa đơn để xem chi tiết.");
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Quản Lý Hóa Đơn");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.add(new QuanLyHoaDon());
        frame.setVisible(true);
    }
}
