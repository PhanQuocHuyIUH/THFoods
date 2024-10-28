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

import DAO.Ban_Dao;
import DAO.CTPDM_Dao;
import DAO.MonAn_Dao;
import DAO.PhieuDatMon_Dao;
import DB.Database;
import Entity.*;

public class DatMon extends JPanel {
    private JTable orderTable;
    private DefaultTableModel tableModel;
    private JComboBox<String> tableComboBox;
    private JLabel totalLabel;
    private JTextField searchField;
    private ArrayList<MonAn> menuItems;
    private HashMap<String, Integer> orderQuantity; // Thêm để theo dõi số lượng món ăn
    private double totalPrice = 0.0;
    private JTextField noteField;
    private JLabel nvField;

    private ArrayList<Ban> dsBan;

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
        headerPanel.setBackground(new Color(230, 240, 255));
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Thêm chữ Menu
        JLabel menuLabel = new JLabel(" \u2630 MENU   ");
        menuLabel.setFont(new Font("Arial Unicode MS", Font.BOLD, 24));
        menuLabel.setForeground(new Color(105, 165, 225));
        headerPanel.add(menuLabel);

        // Thêm các nút phân loại
        String[] categories = {"Món chính", "Nước uống", "Món ăn nhẹ", "Món tráng miệng", "Hiện tất cả"};
        for (String category : categories) {
            JButton categoryButton = createCategoryButton(category);
            headerPanel.add(categoryButton);
        }

        // Thêm khoảng trống
        headerPanel.add(Box.createRigidArea(new Dimension(70, 0)));

        // Thanh tìm kiếm
        searchField = new JTextField(15);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        headerPanel.add(searchField);

        // Nút tìm kiếm
        JButton searchButton = createStyledButton("\uD83D\uDD0D Tìm kiếm", e -> filterMenuItemsBySearch());
        searchButton.setFont(new Font("Arial Unicode MS", Font.BOLD, 14));
        searchButton.setBackground(new Color(80, 80, 255));
        headerPanel.add(searchButton);

        return headerPanel;
    }

    private JButton createCategoryButton(String category) {
        JButton button = createStyledButton(category, e -> filterMenuItemsByCategory(category));
        button.setBackground(new Color(105, 165, 225));
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
        orderPanel.add(orderScrollPane, BorderLayout.NORTH);

        // Phần dưới cùng hiển thị tổng tiền và các nút
        // cả hai phần đều ở dưới cùng
        JPanel bottomPanel = createBottomPanel();
        //đường viền xanh
        bottomPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(0, 0, 255)));
        orderPanel.add(bottomPanel, BorderLayout.CENTER);

        // Nút đặt món và nút reset
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
        header.setBackground(new Color(105, 165, 225));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 14));
    }

    private JPanel createBottomPanel() {
        JPanel bottomPanel = new JPanel();
        bottomPanel.setLayout(new GridLayout(5, 2, 10, 10));
        bottomPanel.setBackground(new Color(255, 255, 255));

        // ComboBox chọn bàn
        JLabel tableLabel = new JLabel(" \u25A4 Chọn bàn:");
        tableLabel.setFont(new Font("Chalkduster", Font.BOLD, 14));
        bottomPanel.add(tableLabel);
        //lấy danh sách bàn từ Dao
        try {
            Ban_Dao banDao = new Ban_Dao();
            dsBan = banDao.getAllBan();
        } catch (Exception e) {
            e.printStackTrace();
        }
        //sort theo mã bàn tăng dần (mã bàn có dạng Bxx) chỉ lấy 2 số cuối để sort
        dsBan.sort((b1, b2) -> Integer.parseInt(b1.getMaBan().substring(1)) - Integer.parseInt(b2.getMaBan().substring(1)));
        String[] tables = new String[dsBan.size()];
        tables[0] = "Bàn ăn";
        for (int i = 1; i < dsBan.size(); i++) {
            //nếu bàn đã đặt thì không hiển thị
            if (dsBan.get(i).getTrangThai() == TrangThaiBan.DaDat) {
                continue;
            }
            tables[i] = dsBan.get(i-1).getMaBan();
        }
        tableComboBox = new JComboBox<>(tables);
        tableComboBox.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        //xanh nhạt
        tableComboBox.setBackground(new Color(230, 240, 255));
        bottomPanel.add(tableComboBox);

        //ghi chú
        JLabel noteLabel = new JLabel(" \uD83D\uDCDD Ghi chú:");
        noteLabel.setFont(new Font("Chalkduster", Font.BOLD, 14));
        bottomPanel.add(noteLabel);
        noteField = new JTextField();
        noteField.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        // xanh nhạt
        noteField.setBackground(new Color(230, 240, 255));
        bottomPanel.add(noteField);

        // ngày
        JLabel dateLabel = new JLabel(" \uD83D\uDCC5 Ngày:");
        dateLabel.setFont(new Font("Chalkduster", Font.BOLD, 14));
        bottomPanel.add(dateLabel);
        //tự động cập nhật ngày
        JLabel dateField = new JLabel();
        dateField.setFont(new Font("Comic Sans MS", Font.PLAIN, 14));
        dateField.setText(java.time.LocalDate.now().toString());
        bottomPanel.add(dateField);

        //nhân viên (lấy từ đăng nhập)
        JLabel nvLabel = new JLabel(" \uD83D\uDC68\u200D Nhân viên:");
        nvLabel.setFont(new Font("Chalkduster", Font.BOLD, 14));
        bottomPanel.add(nvLabel);
        nvField = new JLabel();
        if(DangNhap.nvdn != null){
            nvField.setText(DangNhap.nvdn.getTenNV());
        }else {
            nvField.setText(DangNhap.qldn.getTenQL());
        }
        nvField.setFont(new Font("Chalkboard", Font.PLAIN, 14));
        bottomPanel.add(nvField);

        // Hiển thị tổng tiền
        JLabel totalLabelTitle = new JLabel(" Tổng Tiền:");
        totalLabelTitle = new JLabel("\uD83D\uDCB0 Tổng tiền :");
        totalLabelTitle.setFont(new Font("Arial Unicode MS", Font.BOLD, 20));
        totalLabelTitle.setForeground(new Color(0, 102, 204));
        bottomPanel.add(totalLabelTitle);
        totalLabel = new JLabel("0 VND");
        totalLabel.setForeground(new Color(0, 102, 204));
        totalLabel.setFont(new Font("Chalkduster", Font.BOLD, 20));
        bottomPanel.add(totalLabel);

        return bottomPanel;
    }

    private JPanel createButtonPanel() {
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        buttonPanel.setBackground(new Color(255, 255, 255));
        JButton orderButton = createStyledButton("\uD83C\uDF7D Đặt Món", e -> placeOrder());
        orderButton.setFont(new Font("Arial Unicode MS", Font.BOLD, 22));
        JButton resetButton = createStyledButton("\u21BA", e -> resetOrder());
        resetButton.setPreferredSize(new Dimension(60, 60));
        resetButton.setFont(new Font("Arial Unicode MS", Font.BOLD, 26));
        resetButton.setBackground(new Color(255, 255, 255));
        resetButton.setForeground(Color.BLUE);

        JButton cancelButton = createStyledButton("  \u274C Hủy  ", e -> cancelSelectedItem());
        cancelButton.setBackground(new Color(255, 0, 15));
        cancelButton.setFont(new Font("Arial Unicode MS", Font.BOLD, 22));

        buttonPanel.add(orderButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        buttonPanel.add(resetButton);
        buttonPanel.add(Box.createRigidArea(new Dimension(20, 0)));
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
        dishLabel.setFont(new Font("Comic Sans MS", Font.PLAIN, 12));
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
        button.setBackground(new Color(105, 165, 225));
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
        // Lấy thông tin từ các trường
        String table = (String) tableComboBox.getSelectedItem();
        String note = noteField.getText();
        String date = java.time.LocalDate.now().toString();
        String nv = nvField.getText();
        // kiểm tra xem đã chọn món chưa
        if (tableModel.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn món trước khi đặt!", "Thông báo", JOptionPane.WARNING_MESSAGE);
            return;
        }
        //kiểm tra trạng thái bàn
        Ban ban = null;
        for (Ban b : dsBan) {
            if (b.getMaBan().equals(table)) {
                ban = b;
                break;
            }
        }
        // Thêm phiếu đặt món vào database
        try {
            //thêm phiếu đặt món
            PhieuDatMon phieuDatMon = new PhieuDatMon("PDB" + System.currentTimeMillis(), date, note, ban, DangNhap.nvdn);
            PhieuDatMon_Dao phieuDatMonDao = new PhieuDatMon_Dao();
            phieuDatMonDao.addPhieuDatMon(phieuDatMon);
            //thêm chi tiết phiếu đặt món
            for (int i = 0; i < tableModel.getRowCount(); i++) {
                String tenMon = (String) tableModel.getValueAt(i, 0);
                int soLuong = (int) tableModel.getValueAt(i, 2);
                MonAn monAn = null;
                for (MonAn mon : menuItems) {
                    if (mon.getTenMon().equals(tenMon)) {
                        monAn = mon;
                        break;
                    }
                }
                ChiTietDatMon chiTietDatMon = new ChiTietDatMon(phieuDatMon.getMaPDB(), monAn.getMaMon(), soLuong);
                CTPDM_Dao ctpdmDao = new CTPDM_Dao();
                ctpdmDao.addCTPDM(chiTietDatMon);
            }
            //cập nhật trạng thái bàn
            Ban_Dao banDao = new Ban_Dao();
            Ban banUpdate = new Ban(ban.getMaBan(), TrangThaiBan.DangDung, ban.getSoGhe());
            banDao.updateTrangThaiBan(banUpdate);
            JOptionPane.showMessageDialog(this, "Đặt món thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
            resetOrder();
        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Đặt món thất bại!", "Thông báo", JOptionPane.ERROR_MESSAGE);
        }
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

}
