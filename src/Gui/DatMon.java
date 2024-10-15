package Gui;

import javax.swing.*;
import java.awt.*;
import javax.swing.table.DefaultTableModel;

public class DatMon extends JPanel {
    private JTable orderTable;
    private DefaultTableModel tableModel;
    private JLabel totalLabel;
    private JTextField totalField;
    private JComboBox<String> tableComboBox;

    public DatMon() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Panel chứa danh sách món ăn
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(4, 4, 10, 10)); // Hiển thị 16 món ăn theo lưới 4x4
        menuPanel.setBackground(Color.WHITE);

        // Tạo danh sách món ăn mẫu
        String[] dishNames = {"Phở Bò", "Bún Chả", "Cơm Gà", "Mì Quảng", "Gỏi Cuốn", "Bánh Mì",
                "Chả Giò", "Hủ Tiếu", "Bò Kho", "Cà Ri Gà", "Bánh Xèo", "Nem Nướng",
                "Bánh Tráng Trộn", "Cháo Lòng", "Xôi Xéo", "Mì Xào Hải Sản"};
        int[] dishPrices = {50000, 60000, 55000, 45000, 40000, 30000, 35000, 45000, 70000, 65000,
                50000, 60000, 35000, 45000, 30000, 70000};

        // Thêm món ăn vào panel
        for (int i = 0; i < dishNames.length; i++) {
            JPanel panel = createMenuItemPanel(dishNames[i], dishPrices[i]);
            menuPanel.add(panel);
        }

        // Panel hiển thị danh sách món ăn bên trái
        JPanel leftPanel = new JPanel(new BorderLayout());
        leftPanel.add(menuPanel, BorderLayout.CENTER);
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Panel đặt món (phía bên phải)
        JPanel orderPanel = new JPanel();
        orderPanel.setLayout(new BorderLayout());
        orderPanel.setBackground(Color.WHITE);

        // Bảng hiển thị món đã đặt
        String[] columnNames = {"Tên món", "Số lượng", "Đơn giá", "Thành tiền"};
        tableModel = new DefaultTableModel(columnNames, 0);
        orderTable = new JTable(tableModel);
        orderTable.setFont(new Font("Arial", Font.PLAIN, 14));
        orderTable.setRowHeight(30);
        orderTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 16));
        orderTable.getTableHeader().setBackground(new Color(0, 102, 204));
        orderTable.getTableHeader().setForeground(Color.WHITE);
        orderTable.setSelectionBackground(new Color(0, 128, 255));

        JScrollPane scrollPane = new JScrollPane(orderTable);
        orderPanel.add(scrollPane, BorderLayout.CENTER);

        // Panel chứa tổng tiền, bàn và các nút Đặt món, Hủy, Xóa
        JPanel bottomPanel = new JPanel(new GridLayout(4, 3, 10, 10)); // 4 hàng, 3 cột
        bottomPanel.setBackground(Color.WHITE);
        bottomPanel.setPreferredSize(new Dimension(100, 250)); // Chiếm nửa chiều cao

        totalLabel = new JLabel("Tổng tiền:");
        totalLabel.setFont(new Font("Arial", Font.BOLD, 16));

        totalField = new JTextField();
        totalField.setEditable(false);
        totalField.setFont(new Font("Arial", Font.PLAIN, 14));

        JLabel tableLabel = new JLabel("Chọn bàn:");
        tableLabel.setFont(new Font("Arial", Font.BOLD, 16));

        // ComboBox chọn bàn
        String[] tableNumbers = {"Bàn 1", "Bàn 2", "Bàn 3", "Bàn 4", "Bàn 5"};
        tableComboBox = new JComboBox<>(tableNumbers);
        tableComboBox.setFont(new Font("Arial", Font.PLAIN, 14));

        JButton orderButton = new JButton("Đặt Món");
        orderButton.setBackground(new Color(0, 102, 204));
        orderButton.setForeground(Color.WHITE);
        orderButton.setFont(new Font("Arial", Font.BOLD, 14));

        JButton cancelButton = new JButton("Hủy");
        cancelButton.setBackground(new Color(255, 69, 0));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFont(new Font("Arial", Font.BOLD, 14));

        JButton deleteButton = new JButton("Xóa");
        deleteButton.setBackground(new Color(128, 0, 0));
        deleteButton.setForeground(Color.WHITE);
        deleteButton.setFont(new Font("Arial", Font.BOLD, 14));

        bottomPanel.add(totalLabel);
        bottomPanel.add(totalField);
        bottomPanel.add(new JLabel()); // Ô trống để căn chỉnh
        bottomPanel.add(tableLabel);
        bottomPanel.add(tableComboBox);
        bottomPanel.add(new JLabel()); // Ô trống để căn chỉnh
        bottomPanel.add(orderButton);
        bottomPanel.add(cancelButton);
        bottomPanel.add(deleteButton);

        orderPanel.add(bottomPanel, BorderLayout.SOUTH);

        // Chia khu vực đặt món và bảng chi tiết
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, orderPanel);
        splitPane.setDividerLocation(700);
        add(splitPane, BorderLayout.CENTER);
    }

    // Tạo panel món ăn
    private JPanel createMenuItemPanel(String name, int price) {
        JPanel panel = new JPanel();
        panel.setLayout(new BorderLayout());
        panel.setBackground(Color.WHITE);
        panel.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 1)); // Đường viền màu xanh

        // Giả định hình ảnh món ăn (có thể thay thế bằng ảnh thực tế)
        JLabel imageLabel = new JLabel(new ImageIcon("C:\\Users\\Admins\\OneDrive\\Máy tính\\deadline\\java\\THFoods\\src\\img\\Mì Quảng.jpg"));
        imageLabel.setHorizontalAlignment(SwingConstants.CENTER);
        panel.add(imageLabel, BorderLayout.CENTER);

        // Tên và giá món
        JLabel nameLabel = new JLabel(name + " - " + price + " VND", SwingConstants.CENTER);
        nameLabel.setFont(new Font("Arial", Font.BOLD, 14));
        panel.add(nameLabel, BorderLayout.SOUTH);

        return panel;
    }
}
