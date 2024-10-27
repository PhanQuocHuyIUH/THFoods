package Gui;

import DAO.MonAn_Dao;
import DB.Database;
import Entity.MonAn;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;

public class QuanLyThucDon extends JPanel
{
    private JTextField searchField;
    private ArrayList<MonAn> menuItems;
    private HashMap<String, Integer> orderQuantity;
    private JTextField maMon;
    private JTextField tenMon;
    private JComboBox<String> loaiMon;
    private JTextField donGia;
    private JTextField moTa;
    private JButton imageButton;

    public QuanLyThucDon()
    {
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

        // Phần bên phải hiển thị thông tin chi tiết món ăn
        JPanel detailPanel = createDetailPanel();
        add(detailPanel, BorderLayout.EAST);


        // Tải menu món từ cơ sở dữ liệu
        loadMenuItems(menuPanel);

        orderQuantity = new HashMap<>(); // Khởi tạo HashMap
    }

    private JPanel createDetailPanel() {
        JPanel detailPanel = new JPanel();
        detailPanel.setLayout(new GridBagLayout());
        detailPanel.setBackground(Color.WHITE);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(25, 25, 25, 25); // Thêm khoảng cách

        // Tiêu đề cho panel
        JLabel titleLabel = new JLabel("Quản lý món ăn");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(new Color(0, 102, 204));
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.weightx = 1.0; // Đặt trọng số chiều rộng
        gbc.anchor = GridBagConstraints.CENTER; // Căn giữa tiêu đề
        detailPanel.add(titleLabel, gbc);

        // Nhập mã món
        JLabel maMonLabel = new JLabel("🆔 Mã món:");
        maMonLabel.setFont(new Font("Arial Unicode MS", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1; gbc.weightx = 0.3;
        detailPanel.add(maMonLabel, gbc);
        maMon = new JTextField(15);
        maMon.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 1));
        maMon.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        maMon.setBackground(new Color(230, 240, 255));
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.7;
        detailPanel.add(maMon, gbc);

        // Nhập tên món
        JLabel tenMonLabel = new JLabel("🍽️ Tên món:");
        tenMonLabel.setFont(new Font("Arial Unicode MS", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 2;
        detailPanel.add(tenMonLabel, gbc);
        tenMon = new JTextField(15);
        tenMon.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 1));
        tenMon.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tenMon.setBackground(new Color(230, 240, 255));
        gbc.gridx = 1; gbc.gridy = 2;
        detailPanel.add(tenMon, gbc);

        // Nhập loại món
        JLabel loaiMonLabel = new JLabel("🍲 Loại món:");
        loaiMonLabel.setFont(new Font("Arial Unicode MS", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 3;
        detailPanel.add(loaiMonLabel, gbc);
        String[] loaiMonOptions = { "Món chính", "Nước uống", "Món ăn nhẹ", "Món tráng miệng" };
        loaiMon = new JComboBox<>(loaiMonOptions);
        loaiMon.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        loaiMon.setBackground(new Color(230, 240, 255));
        gbc.gridx = 1; gbc.gridy = 3;
        detailPanel.add(loaiMon, gbc);

        // Nhập đơn giá
        JLabel donGiaLabel = new JLabel("💵 Đơn giá (VND):");
        donGiaLabel.setFont(new Font("Arial Unicode MS", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 4;
        detailPanel.add(donGiaLabel, gbc);
        donGia = new JTextField(15);
        donGia.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 1));
        donGia.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        donGia.setBackground(new Color(230, 240, 255));
        gbc.gridx = 1; gbc.gridy = 4;
        detailPanel.add(donGia, gbc);

        // Nhập mô tả
        JLabel moTaLabel = new JLabel("📝 Mô tả:");
        moTaLabel.setFont(new Font("Arial Unicode MS", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 5;
        detailPanel.add(moTaLabel, gbc);
        moTa = new JTextField(15);
        moTa.setBorder(BorderFactory.createLineBorder(new Color(0, 102, 204), 1));
        moTa.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        moTa.setBackground(new Color(230, 240, 255));
        gbc.gridx = 1; gbc.gridy = 5;
        detailPanel.add(moTa, gbc);

        // Nhập hình ảnh
        JLabel imageLabel = new JLabel("🖼️ Hình ảnh:");
        imageLabel.setFont(new Font("Arial Unicode MS", Font.PLAIN, 14));
        gbc.gridx = 0; gbc.gridy = 6;
        detailPanel.add(imageLabel, gbc);
        imageButton = createStyledButton("\uD83D\uDCC2 Chọn hình ảnh", e -> chooseImage());
        imageButton.setFont(new Font("Arial Unicode MS", Font.PLAIN, 14));
        gbc.gridx = 1; gbc.gridy = 6;
        detailPanel.add(imageButton, gbc);

        // Tạo panel cho các nút
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Căn giữa các nút
        buttonPanel.setBackground(Color.WHITE);

        // Nút thêm
        JButton addButton = createStyledButton("➕ Thêm", e -> addDish());
        addButton.setPreferredSize(new Dimension(120, 40));
        addButton.setFont(new Font("Arial Unicode MS", Font.PLAIN, 14)); // Font cho nút
        buttonPanel.add(addButton);

        // Nút sửa
        JButton updateButton = createStyledButton("✏️ Sửa", e -> updateDish());
        updateButton.setPreferredSize(new Dimension(120, 40));
        updateButton.setFont(new Font("Arial Unicode MS", Font.PLAIN, 14)); // Font cho nút
        buttonPanel.add(updateButton);

        // Nút xóa
        JButton deleteButton = createStyledButton("🗑️ Xóa", e -> deleteDish());
        deleteButton.setPreferredSize(new Dimension(120, 40));
        deleteButton.setFont(new Font("Arial Unicode MS", Font.PLAIN, 14)); // Font cho nút
        deleteButton.setBackground(new Color(255, 0, 15));
        buttonPanel.add(deleteButton);

        // Thêm buttonPanel vào detailPanel
        gbc.gridx = 0; gbc.gridy = 7; gbc.gridwidth = 2; gbc.weightx = 1.0; // Chiếm toàn bộ chiều rộng
        detailPanel.add(buttonPanel, gbc);

        // Thiết lập chiều cao tối đa cho các trường nhập liệu
        for (Component component : detailPanel.getComponents()) {
            if (component instanceof JTextField || component instanceof JComboBox || component instanceof JButton) {
                component.setPreferredSize(new Dimension(150, 30));
            }
        }

        return detailPanel;
    }

    private void chooseImage() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileFilter(new FileNameExtensionFilter("Image files", "jpg"));
        int returnValue = fileChooser.showOpenDialog(null);
        if (returnValue == JFileChooser.APPROVE_OPTION) {
            File selectedFile = fileChooser.getSelectedFile();
            // Xử lý hình ảnh đã chọn (hiển thị hoặc lưu vào biến)
            // Ví dụ: setImage(selectedFile.getAbsolutePath());
        }
    }

    private void updateDish() {
    }

    private void deleteDish() {
        
    }

    private void addDish() {
        
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(new Color(0, 102, 204, 150));
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Thêm chữ Menu
        JLabel menuLabel = new JLabel(" \u2630 MENU   ");
        menuLabel.setFont(new Font("Arial Unicode MS", Font.BOLD, 24));
        menuLabel.setForeground(Color.WHITE);
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

    private JButton createCategoryButton(String category) {
        JButton button = createStyledButton(category, e -> filterMenuItemsByCategory(category));
        button.setBackground(new Color(100, 100, 255));
        return button;
    }

    private JPanel createMenuPanel() {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(0, 4, 10, 10));
        menuPanel.setBackground(Color.WHITE);
        return menuPanel;
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
                addMonAnToDetail(monAn.getTenMon(), monAn.getDonGia());
            }
        });

        menuPanel.add(dishPanel);
    }
    private void addMonAnToDetail(String dishName, double price) {
        MonAn_Dao monAnDao = new MonAn_Dao();
        try {
            MonAn monAn = monAnDao.getMonAnByTenMon(dishName);
            maMon.setText(monAn.getMaMon());
            tenMon.setText(monAn.getTenMon());
            loaiMon.setSelectedItem(monAn.getLoaiMon());
            donGia.setText(String.valueOf(monAn.getDonGia()));
            moTa.setText(monAn.getMoTa());
        } catch (SQLException e) {
            e.printStackTrace();
        }
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
}
