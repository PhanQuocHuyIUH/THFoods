package Gui;

import DAO.MonAn_Dao;
import DB.Database;
import Entity.MonAn;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import javax.swing.plaf.basic.BasicComboBoxUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.sql.SQLException;
import java.time.LocalDateTime;
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
    private JButton lastClickedButton;
    ImageIcon hinhAnhMonAn;

    public QuanLyThucDon()
    {
        try {
            Database.getInstance().connect();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        setLayout(new BorderLayout());
        setBackground(AppColor.trang);

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
        detailPanel.setBackground(AppColor.trang);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(25, 25, 25, 25); // Thêm khoảng cách

        // Tiêu đề cho panel
        JLabel titleLabel = new JLabel("Quản lý thực đơn");
        titleLabel.setFont(new Font("Segoe UI", Font.BOLD, 20));
        titleLabel.setForeground(AppColor.xanh);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        gbc.weightx = 1.0; // Đặt trọng số chiều rộng
        detailPanel.add(titleLabel, gbc);
        // Them 1 nut de xoa thong tin trong cac field
        JButton clearButton = createStyledButton("                        \u21BA", e -> clearDetail());
        clearButton.setPreferredSize(new Dimension(60, 60));
        clearButton.setFont(new Font("Arial Unicode MS", Font.BOLD, 26));
        clearButton.setBackground(AppColor.trang);
        clearButton.setForeground(Color.BLUE);
        gbc.gridx = 1; gbc.gridy = 0; gbc.gridwidth = 1;
        gbc.weightx = 0.0;
        // Dua nut ra cuoi o ben phai
        gbc.anchor = GridBagConstraints.LAST_LINE_END;
        detailPanel.add(clearButton, gbc);

        // Nhập mã món
        JLabel maMonLabel = new JLabel("🆔 Mã món:");
        maMonLabel.setFont(new Font("Chalkduster", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 1; gbc.gridwidth = 1; gbc.weightx = 0.3;
        detailPanel.add(maMonLabel, gbc);
        maMon = new JTextField(30);
        maMon.setBorder(BorderFactory.createLineBorder(AppColor.xanh, 1));
        maMon.setFont(new Font("Consolas", Font.PLAIN, 14));
        maMon.setBackground(AppColor.xam);
        maMon.setEditable(false);
        gbc.gridx = 1; gbc.gridy = 1; gbc.weightx = 0.7;
        detailPanel.add(maMon, gbc);

        // Nhập tên món
        JLabel tenMonLabel = new JLabel("🍽️ Tên món:");
        tenMonLabel.setFont(new Font("Chalkduster", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 2;
        detailPanel.add(tenMonLabel, gbc);
        tenMon = new JTextField(30);
        tenMon.setBorder(BorderFactory.createLineBorder(AppColor.xanh, 1));
        tenMon.setFont(new Font("Consolas", Font.PLAIN, 14));
        tenMon.setBackground(AppColor.xam);
        gbc.gridx = 1; gbc.gridy = 2;
        detailPanel.add(tenMon, gbc);

        // Nhập loại món
        JLabel loaiMonLabel = new JLabel("🍲 Loại món:");
        loaiMonLabel.setFont(new Font("Chalkduster", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 3;
        detailPanel.add(loaiMonLabel, gbc);
        String[] loaiMonOptions = { " ", "Món chính", "Nước uống", "Món ăn nhẹ", "Món tráng miệng" };
        loaiMon = new JComboBox<>(loaiMonOptions);
        loaiMon.setFont(new Font("Consolas", Font.PLAIN, 14));
        loaiMon.setBackground(AppColor.xam);
        loaiMon.setBorder(BorderFactory.createLineBorder(AppColor.xanh, 1));
        loaiMon.setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                JButton arrowButton = super.createArrowButton();
                arrowButton.setBackground(AppColor.xanh);
                // màu của arrow thành màu trắng
                arrowButton.setForeground(AppColor.trang);
                //bỏ viền cho arrow
                arrowButton.setBorder(BorderFactory.createEmptyBorder());
                return arrowButton;
            }
        });
        gbc.gridx = 1; gbc.gridy = 3;
        detailPanel.add(loaiMon, gbc);

        // Nhập đơn giá
        JLabel donGiaLabel = new JLabel("💵 Đơn giá (VND):");
        donGiaLabel.setFont(new Font("Chalkduster", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 4;
        detailPanel.add(donGiaLabel, gbc);
        donGia = new JTextField(30);
        donGia.setBorder(BorderFactory.createLineBorder(AppColor.xanh, 1));
        donGia.setFont(new Font("Consolas", Font.PLAIN, 14));
        donGia.setBackground(AppColor.xam);
        gbc.gridx = 1; gbc.gridy = 4;
        detailPanel.add(donGia, gbc);

        // Nhập mô tả
        JLabel moTaLabel = new JLabel("📝 Mô tả:");
        moTaLabel.setFont(new Font("Chalkduster", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 5;
        detailPanel.add(moTaLabel, gbc);
        moTa = new JTextField(30);
        moTa.setBorder(BorderFactory.createLineBorder(AppColor.xanh, 1));
        moTa.setFont(new Font("Consolas", Font.PLAIN, 14));
        moTa.setBackground(AppColor.xam);
        gbc.gridx = 1; gbc.gridy = 5;
        detailPanel.add(moTa, gbc);

        // Nhập hình ảnh
        JLabel imageLabel = new JLabel("🖼️ Hình ảnh:");
        imageLabel.setFont(new Font("Chalkduster", Font.BOLD, 14));
        gbc.gridx = 0; gbc.gridy = 6;
        detailPanel.add(imageLabel, gbc);
        imageButton = createStyledButton("\uD83D\uDCC2 Duyệt", e -> chooseImage());
        imageButton.setFont(new Font("Arial Unicode MS", Font.PLAIN, 14));
        gbc.gridx = 1; gbc.gridy = 6;
        detailPanel.add(imageButton, gbc);

        // Tạo panel cho các nút
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER)); // Căn giữa các nút
        buttonPanel.setBackground(AppColor.trang);

        // Nút thêm
        JButton addButton = createStyledButton("➕ Thêm", e -> addDish());
        addButton.setPreferredSize(new Dimension(120, 40));
        addButton.setFont(new Font("Arial Unicode MS", Font.PLAIN, 18)); // Font cho nút
        buttonPanel.add(addButton);

        // Nút sửa
        JButton updateButton = createStyledButton("✏️ Sửa", e -> updateDish());
        updateButton.setPreferredSize(new Dimension(120, 40));
        updateButton.setFont(new Font("Arial Unicode MS", Font.PLAIN, 18)); // Font cho nút
        buttonPanel.add(updateButton);

        // Nút xóa
        JButton deleteButton = createStyledButton("🗑️ Xóa", e -> deleteDish());
        deleteButton.setPreferredSize(new Dimension(120, 40));
        deleteButton.setFont(new Font("Arial Unicode MS", Font.PLAIN, 18)); // Font cho nút
        deleteButton.setBackground(AppColor.red);
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
            String imagePath = selectedFile.getAbsolutePath();
            hinhAnhMonAn = new ImageIcon(imagePath);
            Image scaledImage = hinhAnhMonAn.getImage().getScaledInstance(100, 100, Image.SCALE_SMOOTH);
            imageButton.setIcon(new ImageIcon(scaledImage));
        }
    }

    private void clearDetail() {
        maMon.setText("");
        tenMon.setText("");
        loaiMon.setSelectedIndex(0);
        donGia.setText("");
        moTa.setText("");
        imageButton.setIcon(null);
        hinhAnhMonAn = null;
    }

    private void updateDish()
    {
        MonAn_Dao monAnDao = new MonAn_Dao();
        String maMonText = maMon.getText();
        String tenMonText = tenMon.getText();
        String loaiMonText = (String) loaiMon.getSelectedItem();
        String donGiaText = donGia.getText();
        String moTaText = moTa.getText();

        // Hoi truoc khi sua (hien ten mon an) va thuc hien sua mon an
        if (maMonText.equals("")) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn món ăn cần sửa",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int choice = JOptionPane.showConfirmDialog(null,
                "Bạn có chắc chắn muốn sửa món ăn ' " + tenMonText + " ' không?",
                "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION && checkEmpty()) {
            // kiem tra gia mon an co phai la so hay khong
            try {
                Double.parseDouble(donGiaText);
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(null, "Đơn giá phải là số",
                        "Thông báo", JOptionPane.ERROR_MESSAGE);
                donGia.requestFocus();
                return;
            }
            MonAn monAn = new MonAn(maMonText, tenMonText, loaiMonText, Double.parseDouble(donGiaText), moTaText);
            try {
                String tenMonCu = monAnDao.getTenMon(maMonText);
                // neu chon hinh anh moi thi cap nhat hinh anh moi va xoa hinh anh cu neu ko trung ten
                if(hinhAnhMonAn != null && !tenMonText.equals(tenMonCu)){
                    try {
                        // xu ly hinh anh de luu vao thu muc img
                        Image image = hinhAnhMonAn.getImage();
                        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null),
                                image.getHeight(null), BufferedImage.TYPE_INT_RGB);
                        Graphics2D g2d = bufferedImage.createGraphics();
                        g2d.drawImage(image, 0, 0, null);
                        g2d.dispose();
                        File file = new File("src\\img\\" + tenMonText.toLowerCase().replace(
                                    " ", "_") + ".jpg");
                        ImageIO.write(bufferedImage, "jpg", file);
                        // xoa hinh anh cu
                        File fileCu = new File("src\\img\\" + tenMonCu.toLowerCase().replace(
                                " ", "_") + ".jpg");
                        fileCu.delete();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                // neu chon hinh anh moi va ko trung ten thi ghi de hinh anh cu
                else if(hinhAnhMonAn != null && tenMonText.equals(tenMonCu))
                {
                    try {
                        // xu ly hinh anh de luu vao thu muc img
                        Image image = hinhAnhMonAn.getImage();
                        BufferedImage bufferedImage = new BufferedImage(image.getWidth(null),
                                image.getHeight(null), BufferedImage.TYPE_INT_RGB);
                        Graphics2D g2d = bufferedImage.createGraphics();
                        g2d.drawImage(image, 0, 0, null);
                        g2d.dispose();
                        File file = new File("src\\img\\" + tenMonText.toLowerCase().replace(
                                " ", "_") + ".jpg");
                        ImageIO.write(bufferedImage, "jpg", file);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                else {
                    // neu khong chon hinh anh moi thi giu nguyen hinh anh cu va doi ten hinh anh
                    File file = new File("src\\img\\" + tenMonCu.toLowerCase().replace(
                            " ", "_") + ".jpg");
                    File fileNew = new File("src\\img\\" + tenMonText.toLowerCase().replace(
                            " ", "_") + ".jpg");
                    file.renameTo(fileNew);
                }
                monAnDao.capNhatMonAn(monAn);
                JOptionPane.showMessageDialog(null, "Sửa món ăn thành công",
                            "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                // cap nhat lai menu
                JPanel menuPanel = (JPanel) ((JScrollPane) getComponent(1)).getViewport().getView();
                loadMenuItems(menuPanel);
                clearDetail();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Sửa món ăn thất bại",
                        "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void deleteDish()
    {
        // hoi truoc khi xoa (hien ten mon an) va thuc hien xoa mon an theo ma mon
        MonAn_Dao monAnDao = new MonAn_Dao();
        String tenMonText = tenMon.getText();
        String maMonText = maMon.getText();
        if (maMonText.equals("")) {
            JOptionPane.showMessageDialog(null, "Vui lòng chọn món ăn cần xóa",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
            return;
        }
        int choice = JOptionPane.showConfirmDialog(null,
                "Bạn có chắc chắn muốn xóa món ăn ' " + tenMonText + " ' không?",
                "Xác nhận", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            try {
                monAnDao.xoaMonAn(maMonText);
                // xoa hinh anh cua mon an
                File file = new File("src\\img\\" + tenMonText.toLowerCase().replace(
                        " ", "_") + ".jpg");
                file.delete();
                JOptionPane.showMessageDialog(null, "Xóa món ăn thành công",
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                // cap nhat lai menu
                JPanel menuPanel = (JPanel) ((JScrollPane) getComponent(1)).getViewport().getView();
                loadMenuItems(menuPanel);
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Xóa món ăn thất bại",
                        "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void addDish()
    {
        MonAn_Dao monAnDao = new MonAn_Dao();
        String maMonText = "M";
        String dt = LocalDateTime.now().toString();
        dt = dt.replaceAll("[^0-9]", "");
        maMonText += dt.substring(0, 14);
        String tenMonText = tenMon.getText();
        String loaiMonText = (String) loaiMon.getSelectedItem();
        String donGiaText = donGia.getText();
        String moTaText = moTa.getText();

        if(validatation()){
            MonAn monAn = new MonAn(maMonText, tenMonText, loaiMonText, Double.parseDouble(donGiaText), moTaText);
            try {
                monAnDao.themMonAn(monAn);
                // them hinh anh duoc chon vao thu muc img
                try {
                    // xu ly hinh anh de luu vao thu muc img
                    Image image = hinhAnhMonAn.getImage();
                    BufferedImage bufferedImage = new BufferedImage(image.getWidth(null),
                            image.getHeight(null), BufferedImage.TYPE_INT_RGB);
                    Graphics2D g2d = bufferedImage.createGraphics();
                    g2d.drawImage(image, 0, 0, null);
                    g2d.dispose();
                    File file = new File("src\\img\\" + tenMonText.toLowerCase().replace(
                            " ", "_") + ".jpg");
                    ImageIO.write(bufferedImage, "jpg", file);
                } catch (Exception e) {
                    e.printStackTrace();
                }

                JOptionPane.showMessageDialog(null, "Thêm món ăn thành công",
                        "Thông báo", JOptionPane.INFORMATION_MESSAGE);
                // cap nhat lai menu
                JPanel menuPanel = (JPanel) ((JScrollPane) getComponent(1)).getViewport().getView();
                loadMenuItems(menuPanel);
                clearDetail();
            } catch (SQLException e) {
                e.printStackTrace();
                JOptionPane.showMessageDialog(null, "Thêm món ăn thất bại",
                        "Thông báo", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private boolean validatation()
    {
        String tenMonText = tenMon.getText();
        String donGiaText = donGia.getText();
        // kiem tra gia mon an co phai la so hay khong
        try {
            Double.parseDouble(donGiaText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(null, "Đơn giá phải là số",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
            donGia.requestFocus();
            return false;
        }
        // kiem tra xem hinh anh da duoc chon chua
        if(hinhAnhMonAn == null){
            JOptionPane.showMessageDialog(null, "Chưa chọn hình ảnh",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
            return false;
        }
        // kiem tra xem ten mon da ton tai chua
        MonAn_Dao monAnDao = new MonAn_Dao();
        try {
            if(monAnDao.getMonAnByTenMon(tenMonText) != null){
                JOptionPane.showMessageDialog(null, "Món đã tồn tại",
                        "Thông báo", JOptionPane.ERROR_MESSAGE);
                tenMon.requestFocus();
                return false;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        checkEmpty();
        return true;
    }

    private boolean checkEmpty()
    {
        String tenMonText = tenMon.getText();
        String loaiMonText = (String) loaiMon.getSelectedItem();
        String donGiaText = donGia.getText();
        String moTaText = moTa.getText();
        if(tenMonText.equals("")){
            JOptionPane.showMessageDialog(null, "Tên món không được để trống",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
            tenMon.requestFocus();
            return false;
        }
        if(loaiMonText.equals(" ")){
            JOptionPane.showMessageDialog(null, "Loại món không được để trống",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
            loaiMon.requestFocus();
            return false;
        }
        if(donGiaText.equals("")){
            JOptionPane.showMessageDialog(null, "Đơn giá không được để trống",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
            donGia.requestFocus();
            return false;
        }
        if(moTaText.equals("")){
            JOptionPane.showMessageDialog(null, "Mô tả không được để trống",
                    "Thông báo", JOptionPane.ERROR_MESSAGE);
            moTa.requestFocus();
            return false;
        }
        return true;
    }

    private JPanel createHeaderPanel() {
        JPanel headerPanel = new JPanel();
        headerPanel.setBackground(AppColor.trang);
        headerPanel.setLayout(new FlowLayout(FlowLayout.LEFT));

        // Thêm chữ Menu
        JLabel menuLabel = new JLabel(" \u2630 MENU   ");
        menuLabel.setFont(new Font("Arial Unicode MS", Font.BOLD, 24));
        menuLabel.setForeground(AppColor.den);
        headerPanel.add(menuLabel);

        // Thêm các nút phân loại
        String[] categories = {"Món chính", "Nước uống", "Món ăn nhẹ", "Món tráng miệng", "Hiện tất cả"};
        for (String category : categories) {
            JButton categoryButton = createCategoryButton(category);
            headerPanel.add(categoryButton);
        }

        lastClickedButton = (JButton) headerPanel.getComponent(5); // Mặc định chọn nút đầu tiên
        // Đổi màu nền cho nút đầu tiên
        lastClickedButton.setBackground(AppColor.xanhNhat);

        // Thêm khoảng trống
        headerPanel.add(Box.createRigidArea(new Dimension(292, 0)));

        // Thanh tìm kiếm
        searchField = new JTextField(15);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setBackground(AppColor.xam);
        headerPanel.add(searchField);

        // Nút tìm kiếm
        JButton searchButton = createStyledButton("\uD83D\uDD0D Tìm kiếm", e -> filterMenuItemsBySearch());
        searchButton.setFont(new Font("Arial Unicode MS", Font.BOLD, 14));
        searchButton.setBackground(AppColor.xanh);
        headerPanel.add(searchButton);

        return headerPanel;
    }

    private JButton createStyledButton(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setBackground(AppColor.xanh);
        button.setForeground(AppColor.trang);
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
        String loaiMon = "";

        for (MonAn monAn : menuItems) {
            boolean matchesSearch = monAn.getTenMon().toLowerCase().contains(searchText);
            if (matchesSearch) {
                addDishToMenuPanel(menuPanel, monAn);
                loaiMon = monAn.getLoaiMon();
            }
        }
        // đổ mấy món cùng loại
        for (MonAn monAn : menuItems) {
            if (monAn.getLoaiMon().equals(loaiMon) && !monAn.getTenMon().toLowerCase().contains(searchText)) {
                addDishToMenuPanel(menuPanel, monAn);
            }
        }

        menuPanel.revalidate();
        menuPanel.repaint();
    }

    private JButton createCategoryButton(String category) {
        JButton button = createStyledButton(category, e -> filterMenuItemsByCategory(category));
        button.setBackground(AppColor.trang);
        button.setForeground(AppColor.den);

        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(AppColor.xanhNhat); // Đổi màu nền khi rê chuột vào
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                if (button != lastClickedButton) {
                    button.setBackground(AppColor.trang); // Màu nền trở lại
                }
            }
        });

        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (lastClickedButton != null) {
                    lastClickedButton.setBackground(AppColor.trang); // Đổi màu nền của nút trước đó
                }
                button.setBackground(AppColor.xanhNhat); // Đổi màu nền của nút được nhấn
                lastClickedButton = button; // Cập nhật nút được nhấn
            }
        });

        return button;
    }

    private JPanel createMenuPanel() {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new GridLayout(0, 4, 10, 10));
        menuPanel.setBackground(AppColor.trang);
        return menuPanel;
    }

    private void addDishToMenuPanel(JPanel menuPanel, MonAn monAn) {
        JPanel dishPanel = new JPanel();
        dishPanel.setLayout(new BorderLayout());
        dishPanel.setBackground(AppColor.xam);

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
