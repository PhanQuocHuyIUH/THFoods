package Test;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class QuanLyHoaDon extends JFrame {
    private DefaultTableModel modelTable;
    private DefaultTableModel modelHoaDonMiddle;
    private JTable table;
    private JTable tableHoaDonMiddle;
    private JTextField searchField;
    private JLabel lblMaHoaDon;
    private JLabel lblKhachHang;
    private JLabel lblSoDienThoai;
    private JLabel lblThoiGianTao;
    private JLabel lblTrangThai;
    private JLabel lblTongTien;
    private JButton btnSearch;

    public QuanLyHoaDon() {
        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(20, 20));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Padding for main panel

        // Left panel setup
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBackground(Color.WHITE);

        // Header for left side
        JLabel lbHead = new JLabel("LỊCH SỬ HÓA ĐƠN", SwingConstants.CENTER);
        lbHead.setFont(new Font("Segoe UI", Font.BOLD, 28));
        lbHead.setForeground(new Color(41, 128, 185)); // Soft Blue
        lbHead.setAlignmentX(Component.CENTER_ALIGNMENT);

        leftPanel.add(lbHead);
        leftPanel.add(Box.createVerticalStrut(20)); // Spacing

        // Search panel
        JPanel pHead = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10)); // Center and space out
        pHead.setBackground(Color.WHITE);

        JLabel lblMaHD = new JLabel("Mã HD: ");
        lblMaHD.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        searchField = new JTextField(20);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setBorder(BorderFactory.createLineBorder(Color.GRAY, 1, true));

        // Search button with rounded corners
        btnSearch = new JButton("Tìm");
        btnSearch.setFont(new Font("Segoe UI", Font.BOLD, 15));
        btnSearch.setForeground(Color.WHITE);
        btnSearch.setBackground(new Color(52, 152, 219)); // Light Blue
        btnSearch.setFocusPainted(false);
        btnSearch.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15)); // Padding for button
        btnSearch.setBorder(BorderFactory.createLineBorder(new Color(52, 152, 219), 1, true)); // Rounded corners

        // Button hover effect
        btnSearch.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseEntered(MouseEvent e) {
                btnSearch.setBackground(new Color(41, 128, 185)); // Darker Blue on hover
            }

            @Override
            public void mouseExited(MouseEvent e) {
                btnSearch.setBackground(new Color(52, 152, 219)); // Default color when mouse exits
            }
        });

        pHead.add(lblMaHD);
        pHead.add(searchField);
        pHead.add(btnSearch);

        leftPanel.add(pHead);
        leftPanel.add(Box.createVerticalStrut(20)); // Spacing

        // Table for left panel
        String[] cols = {"Mã HD", "Người tạo", "Khách hàng", "Thời gian tạo", "Tổng tiền", "Trạng thái"};
        modelTable = new DefaultTableModel(cols, 0);
        table = new JTable(modelTable) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component component = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    component.setBackground(row % 2 == 0 ? new Color(245, 245, 245) : Color.WHITE); // Striped rows
                } else {
                    component.setBackground(new Color(102, 178, 255)); // Highlight row when selected
                }
                return component;
            }
        };

        // Adjust column widths
        TableColumnModel columnModel = table.getColumnModel();
        columnModel.getColumn(0).setPreferredWidth(70);  // Mã HD
        columnModel.getColumn(1).setPreferredWidth(100); // Người tạo
        columnModel.getColumn(2).setPreferredWidth(100); // Khách hàng
        columnModel.getColumn(3).setPreferredWidth(150); // Thời gian tạo
        columnModel.getColumn(4).setPreferredWidth(100); // Tổng tiền
        columnModel.getColumn(5).setPreferredWidth(100); // Trạng thái

        table.setRowHeight(25); // Increase row height for better readability

        // Custom table header
        table.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        table.getTableHeader().setBackground(new Color(41, 128, 185)); // Light blue background for header
        table.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPane = new JScrollPane(table);
        leftPanel.add(scrollPane);
        leftPanel.add(Box.createVerticalStrut(20)); // Spacing

        mainPanel.add(leftPanel, BorderLayout.WEST); // Add left panel to main

        // Right panel setup
        JPanel rightPanel = new JPanel(new BorderLayout());
        rightPanel.setBackground(Color.WHITE);

        // Right Middle - Table
        JPanel middle = new JPanel();
        middle.setLayout(new BoxLayout(middle, BoxLayout.Y_AXIS));
        middle.setBackground(Color.WHITE);

        JLabel middleLabel = new JLabel("HÓA ĐƠN CHI TIẾT", SwingConstants.CENTER);
        middleLabel.setFont(new Font("Segoe UI", Font.BOLD, 26));
        middleLabel.setForeground(new Color(41, 128, 185)); // Soft Blue
        middleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        middle.add(middleLabel);
        middle.add(Box.createVerticalStrut(20)); // Spacing

        // Right - table
        String[] colsMiddle = {"Tên Món", "Đơn giá", "Số lượng", "Thành tiền", "Ghi chú"};
        modelHoaDonMiddle = new DefaultTableModel(colsMiddle, 0);
        tableHoaDonMiddle = new JTable(modelHoaDonMiddle) {
            @Override
            public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
                Component component = super.prepareRenderer(renderer, row, column);
                if (!isRowSelected(row)) {
                    component.setBackground(row % 2 == 0 ? new Color(245, 245, 245) : Color.WHITE); // Striped rows
                } else {
                    component.setBackground(new Color(102, 178, 255)); // Highlight row when selected
                }
                return component;
            }
        };

        // Adjust column widths
        TableColumnModel middleColumnModel = tableHoaDonMiddle.getColumnModel();
        middleColumnModel.getColumn(0).setPreferredWidth(150); // Tên Món
        middleColumnModel.getColumn(1).setPreferredWidth(100); // Đơn giá
        middleColumnModel.getColumn(2).setPreferredWidth(100); // Số lượng
        middleColumnModel.getColumn(3).setPreferredWidth(150); // Thành tiền
        middleColumnModel.getColumn(4).setPreferredWidth(150); // Ghi chú

        tableHoaDonMiddle.setRowHeight(25); // Increase row height for better readability

        // Custom table header
        tableHoaDonMiddle.getTableHeader().setFont(new Font("Segoe UI", Font.BOLD, 14));
        tableHoaDonMiddle.getTableHeader().setBackground(new Color(41, 128, 185)); // Light blue background for header
        tableHoaDonMiddle.getTableHeader().setForeground(Color.WHITE);

        JScrollPane scrollPaneMiddle = new JScrollPane(tableHoaDonMiddle);
        scrollPaneMiddle.setPreferredSize(new Dimension(450, 300));
        middle.add(scrollPaneMiddle);
        middle.add(Box.createVerticalStrut(20)); // Spacing

        rightPanel.add(middle, BorderLayout.CENTER);

        // Add right panel to main
        mainPanel.add(rightPanel, BorderLayout.CENTER);

        // JFrame setup
        add(mainPanel);
        setTitle("Quản lý hóa đơn");
        setSize(1200, 800);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);
        addSampleData();

    }

    public void addSampleData() {
        // Dữ liệu mẫu cho bảng hóa đơn
        Object[][] sampleDataTable = {
                {"HD001", "Nguyễn Văn A", "Lê Thị B", "2024-10-01 14:30", "500.000", "Đã thanh toán"},
                {"HD002", "Nguyễn Văn B", "Trần Thị C", "2024-10-02 15:45", "300.000", "Chưa thanh toán"},
                {"HD003", "Nguyễn Văn C", "Phạm Văn D", "2024-10-03 16:10", "700.000", "Đã thanh toán"}
        };

        // Thêm dữ liệu vào bảng hóa đơn
        for (Object[] row : sampleDataTable) {
            modelTable.addRow(row);
        }

        // Dữ liệu mẫu cho bảng chi tiết hóa đơn
        Object[][] sampleDataHoaDonMiddle = {
                {"Bánh mì", "20.000", "2", "40.000", "Không"},
                {"Cà phê", "30.000", "1", "30.000", "Thêm đường"},
                {"Phở", "50.000", "1", "50.000", "Không hành"}
        };

        // Thêm dữ liệu vào bảng chi tiết hóa đơn
        for (Object[] row : sampleDataHoaDonMiddle) {
            modelHoaDonMiddle.addRow(row);
        }
    }


    public static void main(String[] args) {
        new QuanLyHoaDon();
    }
}
