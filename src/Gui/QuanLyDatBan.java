package Gui;

import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;

public class QuanLyDatBan extends JPanel {
    private final DefaultTableModel danhSachDatBantableModel;
    private final JTable danhSachDatBantableList;
    private final int SO_BAN = 20; // Số lượng bàn
    private boolean[] trangThaiBan; // Trạng thái của mỗi bàn
    private JTextField searchField; // Trường tìm kiếm
    private JButton searchButton;   // Nút tìm kiếm
    private JButton lastClickedButton; // Nút được nhấn gần đây (cho hover hiệu ứng)
    private JList<String> danhSachDatBanList; // Danh sách các bàn đã đặt
    private JButton btnBanTrong, btnBanDaDat, btnTatCa; // Các nút thêm
    private JPanel gridPanel;
    private int columns = 4; // Số cột cố định

    public QuanLyDatBan() {
        setLayout(new BorderLayout());

        JPanel northPanel = new JPanel(new BorderLayout());
        this.add(northPanel, BorderLayout.NORTH);

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        // Tạo trường tìm kiếm
        searchField = new JTextField(30);
        searchField.setPreferredSize(new Dimension(120, 30));
        leftPanel.add(searchField);

        // Mảng các tên nút
        String[] buttonNames = {"Tìm kiếm", "Bàn trống", "Bàn đã đặt", "Tất cả"};

        // Vòng lặp để tạo nút cho mỗi tên trong mảng buttonNames
        for (String name : buttonNames) {
            JButton button = new JButton(name);
            button.setBackground(new Color(0, 102, 204, 150)); // Áp dụng màu nền cho mỗi nút
            leftPanel.add(button);

            // Gán hành động cho từng nút
            if (name.equals("Tìm kiếm")) {
                searchButton = button;
                // Đặt hành động tìm kiếm tại đây nếu cần
                // searchButton.addActionListener(e -> timKiem());
            } else if (name.equals("Bàn trống")) {
                btnBanTrong = button;
                btnBanTrong.addActionListener(e -> locBan(false));
            } else if (name.equals("Bàn đã đặt")) {
                btnBanDaDat = button;
                btnBanDaDat.addActionListener(e -> locBan(true));
            } else if (name.equals("Tất cả")) {
                btnTatCa = button;
                btnTatCa.addActionListener(e -> hienThiTatCaBan());
            }
        }


        leftPanel.add(btnBanTrong);
        leftPanel.add(btnBanDaDat);
        leftPanel.add(btnTatCa);
        northPanel.add(leftPanel, BorderLayout.WEST);

        // Khởi tạo trạng thái các bàn (ban đầu tất cả đều trống)
        trangThaiBan = new boolean[SO_BAN];
        for (int i = 0; i < SO_BAN; i++) {
            trangThaiBan[i] = false;
        }

        // Khởi tạo gridPanel với GridLayout và thêm vào mainPanel
        gridPanel = new JPanel(new GridLayout(0, columns, 20, 20)); // 0 hàng, số cột cố định
        JPanel mainPanel = new JPanel(new BorderLayout()); // Main panel chứa gridPanel
        mainPanel.add(gridPanel, BorderLayout.NORTH); // Đặt gridPanel ở phía trên

        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Thêm padding
        this.add(mainPanel, BorderLayout.CENTER);

        capNhatGiaoDien(); // Gọi hàm cập nhật giao diện ban đầu

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);

        JPanel dsBanPanel = new JPanel();
        dsBanPanel.setLayout(new BorderLayout());
        dsBanPanel.setBorder(BorderFactory.createTitledBorder("Danh sách khách đặt bàn"));
        this.add(dsBanPanel, BorderLayout.EAST);

        String[] columnNames = {"Mã KH", "Tên KH", "Số điện thoại", "Số lượng khách", "Bàn đặt"};
        danhSachDatBantableModel = new DefaultTableModel(columnNames, 0);
        danhSachDatBantableList = new JTable(danhSachDatBantableModel);
        danhSachDatBantableList.setFont(new Font("Arial", Font.BOLD, 18));
        danhSachDatBantableList.getTableHeader().setBackground(new Color(52, 152, 219));
        danhSachDatBantableList.getTableHeader().setForeground(Color.WHITE);
        JScrollPane danhSachScrollPane = new JScrollPane(danhSachDatBantableList);
        dsBanPanel.add(danhSachScrollPane, BorderLayout.EAST);

        setVisible(true);

        btnBanTrong.addActionListener(e -> locBan(false));
        btnBanDaDat.addActionListener(e -> locBan(true));
        btnTatCa.addActionListener(e -> hienThiTatCaBan());
    }

    // Hàm tạo panel cho từng bàn ăn
    private JPanel createTablePanel(int soBan) {
        JPanel panel = new JPanel(new BorderLayout(10, 20));
        panel.setBorder(new RoundedLineBorder(15));
        panel.setPreferredSize(new Dimension(200, 350)); // Đặt kích thước cố định cho bàn
        panel.setMinimumSize(new Dimension(200, 350));
        panel.setMaximumSize(new Dimension(200, 350));

        // Hình ảnh minh họa cho bàn ăn với kích thước cố định
        ImageIcon icon = new ImageIcon("src\\img\\datban.jpg");
        Image img = icon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH); // Cố định kích thước ảnh
        JLabel imageLabel = new JLabel(new ImageIcon(img));
        imageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Tạo khoảng cách xung quanh ảnh
        panel.add(imageLabel, BorderLayout.CENTER);

        // Nhãn hiển thị mã bàn ăn
        JLabel tableLabel = new JLabel("Mã bàn: " + (100 + soBan), SwingConstants.CENTER);
        tableLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        panel.add(tableLabel, BorderLayout.NORTH);

        if (trangThaiBan[soBan]) {
            JLabel statusLabel = new JLabel("Đang dùng bữa", SwingConstants.CENTER);
            statusLabel.setForeground(Color.RED);
            statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
            panel.add(statusLabel, BorderLayout.SOUTH);
        } else {
            JButton datBanButton = createStyledButton("Đặt bàn");
            datBanButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    new DatBanDialog(soBan); // Hiển thị hộp thoại điền thông tin khi đặt bàn
                }
            });
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonPanel.add(datBanButton);
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0));
            panel.add(buttonPanel, BorderLayout.SOUTH);
        }

        return panel;
    }

    // Hàm cập nhật lại giao diện sau khi đặt bàn
    private void capNhatGiaoDien() {
        gridPanel.removeAll();
        int soBanDu = SO_BAN % columns == 0 ? 0 : columns - (SO_BAN % columns);

        for (int i = 0; i < SO_BAN; i++) {
            JPanel tablePanel = createTablePanel(i);
            gridPanel.add(tablePanel);
        }

        for (int i = 0; i < soBanDu; i++) {
            JPanel emptyPanel = new JPanel();
            emptyPanel.setOpaque(false);
            emptyPanel.setPreferredSize(new Dimension(200, 350));
            gridPanel.add(emptyPanel);
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }

    // Hàm lọc các bàn dựa trên trạng thái
    private void locBan(boolean daDat) {
        gridPanel.removeAll();
        int soBanHienThi = 0;

        for (int i = 0; i < SO_BAN; i++) {
            if (trangThaiBan[i] == daDat) {
                JPanel tablePanel = createTablePanel(i);
                gridPanel.add(tablePanel);
                soBanHienThi++;
            }
        }

        int soBanDu = soBanHienThi % columns == 0 ? 0 : columns - (soBanHienThi % columns);
        for (int i = 0; i < soBanDu; i++) {
            JPanel emptyPanel = new JPanel();
            emptyPanel.setOpaque(false);
            emptyPanel.setPreferredSize(new Dimension(200, 350));
            gridPanel.add(emptyPanel);
        }

        gridPanel.revalidate();
        gridPanel.repaint();
    }

    // Hàm hiển thị tất cả các bàn
    private void hienThiTatCaBan() {
        capNhatGiaoDien();
    }

    // Phương thức để tạo button với kiểu tùy chỉnh
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15);
                super.paintComponent(g);
            }
        };
        button.setFocusPainted(false);
        button.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        button.setForeground(new Color(255, 255, 255));
        button.setBackground(new Color(0, 102, 204, 150));
        button.setPreferredSize(new Dimension(130, 40));
        button.setMinimumSize(new Dimension(130, 40));
        button.setMaximumSize(new Dimension(130, 40));
        button.setBorder(null);
        button.setOpaque(false);

        return button;
    }

    // Inner class for custom border
    class RoundedLineBorder implements Border {
        private final int radius;

        RoundedLineBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius + 1, radius + 1, radius + 1, radius + 1);
        }

        @Override
        public boolean isBorderOpaque() {
            return true;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            g.drawRoundRect(x, y, width - 1, height - 1, radius, radius);
        }
    }

    // Lớp để hiển thị hộp thoại thông tin khách hàng
    private class DatBanDialog extends JDialog {
        private JTextField maKhachHangField, tenKhachHangField, soDienThoaiField, soLuongNguoiField;
        private JButton xacNhanButton, huyButton;

        public DatBanDialog(int soBan) {
            setTitle("Thông tin đặt bàn");
            setSize(400, 250);
            setLayout(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 5, 10); // Điều chỉnh khoảng cách giữa các thành phần
            gbc.fill = GridBagConstraints.HORIZONTAL;

            // Các trường nhập thông tin khách hàng
            maKhachHangField = new JTextField(15);
            tenKhachHangField = new JTextField(15);
            soDienThoaiField = new JTextField(15);
            soLuongNguoiField = new JTextField(15);

            // Thêm các label và text field
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;
            add(new JLabel("Mã khách hàng:"), gbc);
            gbc.gridx = 1;
            add(maKhachHangField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            add(new JLabel("Tên khách hàng:"), gbc);
            gbc.gridx = 1;
            add(tenKhachHangField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 2;
            add(new JLabel("Số điện thoại:"), gbc);
            gbc.gridx = 1;
            add(soDienThoaiField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 3;
            add(new JLabel("Số lượng người:"), gbc);
            gbc.gridx = 1;
            add(soLuongNguoiField, gbc);

            // Panel chứa các nút để căn chỉnh về góc phải
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            xacNhanButton = createStyledButton("Xác nhận");
            huyButton = createStyledButton("Hủy");

            xacNhanButton.setPreferredSize(new Dimension(100, 30));
            huyButton.setPreferredSize(new Dimension(100, 30));

            buttonPanel.add(xacNhanButton);
            buttonPanel.add(huyButton);

            // Thêm buttonPanel vào góc phải dưới
            gbc.gridx = 0;
            gbc.gridy = 4;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.SOUTHEAST;
            gbc.insets = new Insets(15, 10, 10, 10); // Khoảng cách lớn hơn ở phía dưới để nút dễ thấy
            add(buttonPanel, gbc);

            // Sự kiện cho nút xác nhận
            xacNhanButton.addActionListener(e -> {
                String maKhachHang = maKhachHangField.getText();
                String tenKhachHang = tenKhachHangField.getText();
                String soDienThoai = soDienThoaiField.getText();
                String soLuongNguoi = soLuongNguoiField.getText();

                if (!maKhachHang.isEmpty() && !tenKhachHang.isEmpty() && !soDienThoai.isEmpty() && !soLuongNguoi.isEmpty()) {
                    trangThaiBan[soBan] = true;

                    // Thêm thông tin khách hàng và mã bàn vào bảng
                    danhSachDatBantableModel.addRow(new Object[]{
                            maKhachHang,
                            tenKhachHang,
                            soDienThoai,
                            soLuongNguoi,
                            "Bàn " + (100 + soBan)  // Thêm mã bàn vào bảng
                    });

                    capNhatGiaoDien();
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(this, "Vui lòng điền đầy đủ thông tin!");
                }
            });


            // Sự kiện cho nút hủy
            huyButton.addActionListener(e -> dispose());

            setLocationRelativeTo(null);
            setVisible(true);
        }
    }
}

