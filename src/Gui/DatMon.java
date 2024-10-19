package Gui;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

import DAO.MonAn_Dao;
import DB.Database;
import Entity.MonAn;

public class DatMon extends JPanel {
    private JTable orderTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> tableComboBox;
    private JLabel totalLabel;
    private JTextField searchField;
    private ArrayList<MonAn> menuItems;
    private HashMap<String, Integer> orderQuantity; // Thêm để theo dõi số lượng món ăn
    private double totalPrice = 0.0;

    public DatMon() {
        try {
            Database.getInstance().connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Phần header
        JPanel headerPanel = createHeaderPanel();
        add(headerPanel, BorderLayout.NORTH);

        // Phần bên trái hiển thị menu món ăn
        JPanel menuPanel = createMenuPanel();
        JScrollPane menuScrollPane = new JScrollPane(menuPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        menuScrollPane.getVerticalScrollBar().setUnitIncrement(16); // Tăng tốc độ cuộn
        add(menuScrollPane, BorderLayout.CENTER);

        // Phần bên phải hiển thị chi tiết đặt món
        JPanel orderPanel = createOrderPanel();
        add(orderPanel, BorderLayout.EAST);

        // Tải menu món từ cơ sở dữ liệu
        loadMenuItems(menuPanel);

        orderQuantity = new HashMap<>(); // Khởi tạo HashMap
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 102, 204, 150));
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Thêm chữ Menu
        JLabel menuLabel = new JLabel("MENU   ");
        menuLabel.setFont(new Font("Segoe UI", Font.BOLD, 24));
        menuLabel.setForeground(Color.WHITE);
        headerPanel.add(menuLabel);

        // Thêm các nút phân loại
        String[] categories = {"Món chính", "Nước uống", "Món ăn nhẹ", "Món tráng miệng", "Hiện tất cả"};
        for (String category : categories) {
            JButton categoryButton = createCategoryButton(category);
            headerPanel.add(categoryButton);
        }

        // Thêm khoảng trống
        headerPanel.add(Box.createRigidArea(new Dimension(100, 0)));

        // Thanh tìm kiếm
        searchField = new JTextField(15);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        headerPanel.add(searchField);

        // Nút tìm kiếm
        JButton searchButton = createStyledButton("Tìm kiếm", e -> filterMenuItemsBySearch());
        headerPanel.add(searchButton);

        return headerPanel;
    }

    private JButton createCategoryButton(String category) {
        JButton button = createStyledButton(category, e -> filterMenuItemsByCategory(category));
        button.setBackground(new Color(0, 102, 204, 150));
        return button;
    }

    private JPanel createMenuPanel() {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(0, 4, 10, 10));
        menuPanel.setBackground(Color.WHITE);
        return menuPanel;
    }

    private JPanel createOrderPanel() {
        JPanel orderPanel = new JPanel();
        orderPanel.setLayout(new BorderLayout());
        orderPanel.setBackground(new Color(245, 245, 255));

        // Bảng hiển thị chi tiết đặt món
        String[] columnNames = {"Tên món", "Đơn giá", "Số lượng", "Thành tiền"};
        tableModel = new DefaultTableModel(columnNames, 0);
        orderTable = new JTable(tableModel);
        customizeOrderTable();

        JScrollPane orderScrollPane = new JScrollPane(orderTable);
        orderPanel.add(orderScrollPane, BorderLayout.CENTER);

        // Phần dưới cùng hiển thị tổng tiền và các nút
        JPanel bottomPanel = createBottomPanel();
        orderPanel.add(bottomPanel, BorderLayout.NORTH);

        JPanel buttonPanel = createButtonPanel();
        orderPanel.add(buttonPanel, BorderLayout.SOUTH);

        return orderPanel;
    }

    private void customizeOrderTable() {
        orderTable.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        orderTable.setRowHeight(30);
        orderTable.setBackground(new Color(230, 240, 255));
        orderTable.setForeground(new Color(50, 50, 50));
        orderTable.setSelectionBackground(new Color(0, 0, 255, 150));
        orderTable.setSelectionForeground(Color.WHITE);
        orderTable.setGridColor(new Color(50, 150, 200));

        JTableHeader header = orderTable.getTableHeader();
        header.setBackground(new Color(0, 102, 204, 150));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 14));
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(2, 2, 10, 10));
        bottomPanel.setBackground(new Color(245, 245, 255));

        // ComboBox chọn bàn
        bottomPanel.add(new JLabel("Chọn bàn:"));
        String[] tables = {"Bàn 1", "Bàn 2", "Bàn 3", "Bàn 4", "Bàn 5"};
        tableComboBox = new JComboBox<>(tables);
        tableComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        bottomPanel.add(tableComboBox);

        // Hiển thị tổng tiền
        bottomPanel.add(new JLabel("Tổng tiền:"));
        totalLabel = new JLabel("0 VND");
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        bottomPanel.add(totalLabel);

        return bottomPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel();
        JButton orderButton = createStyledButton("\uD83C\uDF7D Đặt Món", e -> placeOrder());
        orderButton.setFont(new Font("Arial Unicode MS", Font.BOLD, 22));
        JButton resetButton = createStyledButton("\u21BA", e -> resetOrder());
        resetButton.setPreferredSize(new Dimension(60, 60));
        resetButton.setFont(new Font("Arial Unicode MS", Font.BOLD, 26));
        resetButton.setBackground(new Color(238, 238, 238));
        resetButton.setForeground(Color.BLUE);
        JButton cancelButton = createStyledButton("\u274C Hủy", e -> cancelSelectedItem());
        cancelButton.setBackground(new Color(255, 0, 0, 150));
        cancelButton.setFont(new Font("Arial Unicode MS", Font.BOLD, 22));

        buttonPanel.add(orderButton);
        buttonPanel.add(resetButton);
        buttonPanel.add(cancelButton);

        return buttonPanel;
    }

    private void loadMenuItems(JPanel menuPanel) {
        try {
            MonAn_Dao monAnDao = new MonAn_Dao();
            menuItems = monAnDao.getInForNV(); // Lấy danh sách món ăn từ database

            menuPanel.removeAll(); // Xóa các món ăn cũ

            for (MonAn monAn : menuItems) {
                addDishToMenuPanel(menuPanel, monAn);
            }
            menuPanel.revalidate();
            menuPanel.repaint();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void addDishToMenuPanel(JPanel menuPanel, MonAn monAn) {
        JPanel dishPanel = new JPanel();
        dishPanel.setLayout(new BorderLayout());

        // Tạo hình ảnh món ăn
        String imagePath = "src\\img\\" + monAn.getTenMon().toLowerCase().replace(" ", "_") + ".jpg";
        ImageIcon dishIcon = new ImageIcon(imagePath);
        Image scaledImage = dishIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        JLabel dishImageLabel = new JLabel(new ImageIcon(scaledImage));
        dishPanel.add(dishImageLabel, BorderLayout.CENTER);

        // Tạo tên món ăn và đơn giá
        JLabel dishLabel = new JLabel("<html>" + monAn.getTenMon() + "<br>" + monAn.getDonGia() + " VND</html>", SwingConstants.CENTER);
        dishLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        dishPanel.add(dishLabel, BorderLayout.SOUTH);

        // Thêm sự kiện khi nhấn vào món ăn
        dishPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
        dishPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
        dishPanel.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                addDishToOrder(monAn.getTenMon(), monAn.getDonGia());
            }
        });

        menuPanel.add(dishPanel);
    }

    private void filterMenuItemsByCategory(String category) {
        JPanel menuPanel = (JPanel) ((JScrollPane) getComponent(1)).getViewport().getView();
        menuPanel.removeAll(); // Xóa các món ăn cũ

        for (MonAn monAn : menuItems) {
            boolean matchesCategory = category.equals("Hiện tất cả") || monAn.getLoaiMon().equals(category);
            if (matchesCategory) {
                addDishToMenuPanel(menuPanel, monAn);
            }
        }

        menuPanel.revalidate();
        menuPanel.repaint();
    }

    private void filterMenuItemsBySearch() {
        String searchText = searchField.getText().toLowerCase();
        JPanel menuPanel = (JPanel) ((JScrollPane) getComponent(1)).getViewport().getView();
        menuPanel.removeAll(); // Xóa các món ăn cũ

        for (MonAn monAn : menuItems) {
            boolean matchesSearch = monAn.getTenMon().toLowerCase().contains(searchText);
            if (matchesSearch) {
                addDishToMenuPanel(menuPanel, monAn);
            }
        }

        menuPanel.revalidate();
        menuPanel.repaint();
    }

    private JButton createStyledButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setBackground(new Color(0, 102, 204, 150));
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.addActionListener(actionListener);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        return button;
    }

    private void addDishToOrder(String dishName, double price) {
        int quantity = orderQuantity.getOrDefault(dishName, 0) + 1; // Tăng số lượng nếu đã có
        double totalDishPrice = quantity * price;
        totalPrice += price; // Chỉ cộng thêm giá trị đơn giá vào tổng tiền

        // Cập nhật số lượng và thành tiền
        orderQuantity.put(dishName, quantity);

        // Kiểm tra xem món ăn đã có trong bảng chưa
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 0).equals(dishName)) {
                tableModel.setValueAt(quantity, i, 2); // Cập nhật số lượng
                tableModel.setValueAt(totalDishPrice, i, 3); // Cập nhật thành tiền
                totalLabel.setText(totalPrice + " VND"); // Cập nhật tổng tiền
                return;
            }
        }

        // Nếu chưa có, thêm mới vào bảng
        tableModel.addRow(new Object[]{dishName, price, quantity, totalDishPrice});
        totalLabel.setText(totalPrice + " VND"); // Cập nhật tổng tiền
    }

    private void placeOrder() {
        // Xử lý đặt món ở đây
        JOptionPane.showMessageDialog(this, "Đặt món thành công!");
        resetOrder();
    }

    private void resetOrder() {
        tableModel.setRowCount(0); // Xóa các món trong bảng
        orderQuantity.clear(); // Xóa thông tin số lượng món
        totalPrice = 0.0;
        totalLabel.setText("0 VND");
    }

    private void cancelSelectedItem() {
        int selectedRow = orderTable.getSelectedRow();
        if (selectedRow >= 0) {
            double price = (double) tableModel.getValueAt(selectedRow, 3);
            String dishName = (String) tableModel.getValueAt(selectedRow, 0);
            totalPrice -= price;
            totalLabel.setText(totalPrice + " VND");
            tableModel.removeRow(selectedRow);
            orderQuantity.remove(dishName); // Xóa món khỏi danh sách theo dõi số lượng
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Đặt Món");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(800, 600);
        frame.setContentPane(new DatMon());
        frame.setVisible(true);
    }
}
