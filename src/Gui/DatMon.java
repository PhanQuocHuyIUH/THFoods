package Gui;
import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class DatMon extends JPanel {
    private JTable orderTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> tableComboBox;
    private JLabel totalLabel;
    private double totalPrice = 0.0;

    public DatMon() {
        setLayout(new BorderLayout());

        // Phần bên trái hiển thị menu món ăn
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(0, 4, 10, 10)); // 4 món trên 1 hàng

        // Thêm các món ăn vào menu
        String[] dishNames = {"Phở Bò", "Bún Chả", "Gỏi Cuốn", "Cơm Tấm", "Bánh Mì", "Bánh Xèo",
                "Hủ Tiếu", "Mì Quảng", "Bánh Cuốn", "Chả Cá", "Xôi Gà", "Lẩu Thái",
                "Bánh Canh", "Bánh Bèo", "Nem Nướng", "Chè Ba Màu", "Bún Bò Huế",
                "Lẩu Bò", "Gỏi Xoài", "Cá Kho Tộ", "Gà Rán", "Chân Gà Ngâm Sả Tắc",
                "Salad Tôm", "Thịt Nướng", "Sushi", "Trà Sữa", "Cà Phê Sữa Đá",
                "Bánh Trung Thu", "Sò Điệp Nướng Mỡ Hành", "Chả Giò"};

        double[] dishPrices = {50000, 45000, 30000, 40000, 25000, 60000, 55000, 50000, 35000, 75000,
                20000, 120000, 40000, 30000, 45000, 25000, 55000, 130000, 35000, 80000,
                70000, 40000, 60000, 80000, 15000, 25000, 30000, 20000, 45000, 55000,
                30000, 35000};

        String[] dishImages = {"src\\img\\pho_bo.jpg", "bun_cha.png", "goi_cuon.png", "com_tam.png",
                "banh_mi.png", "banh_xeo.png", "hu_tieu.png", "mi_quang.png",
                "banh_cuon.png", "cha_ca.png", "xoi_ga.png", "lau_thai.png",
                "banh_canh.png", "banh_beo.png", "nem_nuong.png", "che_ba_mau.png",
                "bun_bo_hue.png", "lau_bo.png", "goi_xoai.png", "ca_kho_to.png",
                "ga_ran.png", "chan_ga_ngam_sa_tac.png", "salad_tom.png", "thit_nuong.png",
                "sushi.png", "tra_sua.png", "ca_phe_sua_da.png", "banh_trung_thu.png",
                "so_diep_nuong_mo_hanh.png", "cha_gio.png"};

        for (int i = 0; i < dishNames.length; i++) {
            JPanel dishPanel = new JPanel();
            dishPanel.setLayout(new BorderLayout());

            // Tạo hình ảnh món ăn
            ImageIcon dishIcon = new ImageIcon(dishImages[i]);
            Image scaledImage = dishIcon.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH); // Giữ tỉ lệ
            JLabel dishImageLabel = new JLabel(new ImageIcon(scaledImage));
            dishPanel.add(dishImageLabel, BorderLayout.CENTER);

            // Tạo tên món ăn và đơn giá
            JLabel dishLabel = new JLabel("<html>" + dishNames[i] + "<br>" + dishPrices[i] + " VND</html>", SwingConstants.CENTER);
            dishLabel.setFont(new Font("Segoe UI", Font.PLAIN, 12)); // Font chữ đẹp hơn
            dishPanel.add(dishLabel, BorderLayout.SOUTH);

            // Thêm sự kiện khi nhấn vào món ăn
            dishPanel.setCursor(new Cursor(Cursor.HAND_CURSOR));
            dishPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY));
            int index = i;
            dishPanel.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    addDishToOrder(dishNames[index], dishPrices[index]);
                }
            });

            menuPanel.add(dishPanel);
        }

        // Thêm JScrollPane cho menu món ăn, chỉ kéo theo chiều dọc
        JScrollPane scrollPane = new JScrollPane(menuPanel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Tăng tốc độ cuộn
        add(scrollPane, BorderLayout.CENTER);

        // Phần bên phải hiển thị chi tiết đặt món
        JPanel orderPanel = new JPanel();
        orderPanel.setLayout(new BorderLayout());
        orderPanel.setBackground(new Color(245, 245, 255)); // Màu nền cho phần bên phải

        // Bảng hiển thị chi tiết đặt món với màu sắc hài hòa
        String[] columnNames = {"Tên món", "Đơn giá", "Số lượng", "Thành tiền"};
        tableModel = new DefaultTableModel(columnNames, 0);
        orderTable = new JTable(tableModel);
        orderTable.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Font chữ đẹp hơn
        orderTable.setRowHeight(30);
        orderTable.setBackground(new Color(230, 240, 255)); // Màu nền của bảng
        orderTable.setForeground(new Color(50, 50, 50)); // Màu chữ
        // Đặt màu nền cho các dòng đã chọn
        orderTable.setSelectionBackground(new Color(100, 150, 255)); // Màu nền khi chọn dòng

        // Đặt màu chữ cho các dòng đã chọn
        orderTable.setSelectionForeground(Color.WHITE); // Màu chữ khi chọn dòng

        // Đặt màu viền cho các ô trong JTable
        orderTable.setGridColor(new Color(50, 150, 200)); // Màu viền giữa các ô

        JTableHeader header = orderTable.getTableHeader();
        header.setBackground(new Color(10, 0, 200));
        header.setForeground(Color.WHITE); // Đặt màu chữ tiêu đề cột thành trắng

        Font font = new Font("Arial", Font.BOLD, 14);
        header.setFont(font); // Đặt font chữ cho tiêu đề cột




        JScrollPane orderScrollPane = new JScrollPane(orderTable);
        orderPanel.add(orderScrollPane, BorderLayout.CENTER);

        // Phần dưới cùng hiển thị tổng tiền và các nút
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(2, 2, 10, 10));
        bottomPanel.setBackground(new Color(245, 245, 255)); // Đồng nhất màu nền

        // ComboBox chọn bàn với giao diện đẹp hơn
        bottomPanel.add(new JLabel("Chọn bàn:"));
        UIManager.put("ComboBox.background", new ColorUIResource(230, 240, 255));
        UIManager.put("ComboBox.foreground", new ColorUIResource(50, 50, 50));
        String[] tables = {"Bàn 1", "Bàn 2", "Bàn 3", "Bàn 4", "Bàn 5"};
        tableComboBox = new JComboBox<>(tables);
        tableComboBox.setFont(new Font("Segoe UI", Font.PLAIN, 14)); // Font đẹp hơn
        bottomPanel.add(tableComboBox);

        // Hiển thị tổng tiền
        bottomPanel.add(new JLabel("Tổng tiền:"));
        totalLabel = new JLabel("0 VND");
        totalLabel.setFont(new Font("Segoe UI", Font.BOLD, 16)); // Font chữ lớn cho tổng tiền
        bottomPanel.add(totalLabel);

        // Nút Đặt Món, Hủy và Xóa với màu sắc phù hợp
        JPanel buttonPanel = new JPanel();
        JButton orderButton = createStyledButton("Đặt Món", new Color(0, 102, 204));
        JButton cancelButton = createStyledButton("Hủy", new Color(204, 0, 0));
        JButton deleteButton = createStyledButton("Xóa", new Color(255, 153, 51));

        buttonPanel.add(orderButton);
        buttonPanel.add(cancelButton);
        buttonPanel.add(deleteButton);

        orderPanel.add(bottomPanel, BorderLayout.NORTH);
        orderPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(orderPanel, BorderLayout.EAST);

        // Xử lý sự kiện cho các nút
        orderButton.addActionListener(e -> placeOrder());
        cancelButton.addActionListener(e -> cancelOrder());
        deleteButton.addActionListener(e -> deleteSelectedItem());
    }

    // Phương thức thêm món vào giỏ hàng
    private void addDishToOrder(String dishName, double price) {
        boolean dishExists = false;

        // Kiểm tra nếu món đã có trong giỏ hàng
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            if (tableModel.getValueAt(i, 0).equals(dishName)) {
                int quantity = (int) tableModel.getValueAt(i, 2) + 1;
                tableModel.setValueAt(quantity, i, 2);
                tableModel.setValueAt(quantity * price, i, 3);
                dishExists = true;
                break;
            }
        }

        if (!dishExists) {
            // Thêm món mới vào giỏ hàng
            tableModel.addRow(new Object[]{dishName, price, 1, price});
        }

        updateTotalPrice();
    }

    // Phương thức cập nhật tổng tiền
    private void updateTotalPrice() {
        totalPrice = 0.0;
        for (int i = 0; i < tableModel.getRowCount(); i++) {
            totalPrice += (double) tableModel.getValueAt(i, 3);
        }
        totalLabel.setText(String.format("%.0f VND", totalPrice));
    }

    // Phương thức xử lý đặt món
    private void placeOrder() {
        JOptionPane.showMessageDialog(this, "Đặt món thành công! Tổng tiền: " + totalLabel.getText());
        tableModel.setRowCount(0); // Xóa giỏ hàng
        updateTotalPrice();
    }

    // Phương thức xử lý khi nhấn nút Hủy
    private void cancelOrder() {
        tableModel.setRowCount(0); // Xóa giỏ hàng
        updateTotalPrice();
    }

    // Phương thức xóa món đã chọn trong giỏ hàng
    private void deleteSelectedItem() {
        int selectedRow = orderTable.getSelectedRow();
        if (selectedRow != -1) {
            tableModel.removeRow(selectedRow);
            updateTotalPrice();
        } else {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn món để xóa.");
        }
    }

    // Tạo nút với màu sắc
    private JButton createStyledButton(String text, Color color) {
        JButton button = new JButton(text);
        button.setBackground(color);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setPreferredSize(new Dimension(100, 40)); // Kích thước nút
        return button;
    }

}
