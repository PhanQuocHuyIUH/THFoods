package Gui;

import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;

public class QuanLyDatBan extends JPanel {
    private JPanel mainPanel;
    private final int SO_BAN = 20; // Số lượng bàn
    private boolean[] trangThaiBan; // Trạng thái của mỗi bàn
    private JTextField searchField; // Trường tìm kiếm
    private JButton searchButton;   // Nút tìm kiếm
    private JButton lastClickedButton; // Nút được nhấn gần đây (cho hover hiệu ứng)

    public QuanLyDatBan() {
        setLayout(new BorderLayout());

        JPanel northPanel = new JPanel(new BorderLayout());
        this.add(northPanel, BorderLayout.NORTH);

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(30); // Ô nhập tìm kiếm
        searchField.setPreferredSize(new Dimension(120, 30));
        searchButton = createStyledButton("Tìm kiếm"); // Sử dụng createStyledButton thay vì styleButton
        leftPanel.add(searchField);
        leftPanel.add(searchButton);
        northPanel.add(leftPanel);

        // Khởi tạo trạng thái các bàn (ban đầu tất cả đều trống)
        trangThaiBan = new boolean[SO_BAN];
        for (int i = 0; i < SO_BAN; i++) {
            trangThaiBan[i] = false; // false: bàn còn trống
        }

        // Tạo panel chính với lưới bố cục
        mainPanel = new JPanel(new GridLayout(0, 4, 45, 45)); // Số cột cố định là 4
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Thêm padding (top, left, bottom, right)
        this.add(mainPanel);

        // Tạo các bàn ăn và thêm vào mainPanel
        for (int i = 0; i < SO_BAN; i++) {
            mainPanel.add(createTablePanel(i));
        }

        // Bọc mainPanel trong JScrollPane để có thanh cuộn
        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER); // Không cho cuộn ngang
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        // Thêm scrollPane vào JFrame
        add(scrollPane);
        setVisible(true);
    }

    // Hàm tạo panel cho từng bàn ăn
    private JPanel createTablePanel(int soBan) {
        JPanel panel = new JPanel(new BorderLayout(10, 20)); // Adjust horizontal and vertical gaps
        panel.setBorder(new RoundedLineBorder(15)); // Đường viền bo tròn

        // Hình ảnh minh họa cho bàn ăn (sử dụng hình mặc định)
        JLabel imageLabel = new JLabel(new ImageIcon("src\\img\\datban.jpg"));
        imageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Tạo khoảng cách xung quanh ảnh
        panel.add(imageLabel, BorderLayout.CENTER);

        // Nhãn hiển thị mã bàn ăn
        JLabel tableLabel = new JLabel("Mã bàn: " + (100 + soBan), SwingConstants.CENTER);
        tableLabel.setFont(new Font("Serif", Font.BOLD, 18)); // Set a larger font for table label
        panel.add(tableLabel, BorderLayout.NORTH);

        // Nhãn hoặc nút cho trạng thái đặt bàn
        if (trangThaiBan[soBan]) {
            // Bàn đang dùng
            JLabel statusLabel = new JLabel("Đang dùng bữa", SwingConstants.CENTER);
            statusLabel.setForeground(Color.RED);
            statusLabel.setFont(new Font("Serif", Font.BOLD, 16)); // Adjust the font size for status label
            panel.add(statusLabel, BorderLayout.SOUTH);
        } else {
            // Bàn trống, hiển thị nút "Đặt bàn"
            JButton datBanButton = createStyledButton("Đặt bàn"); // Sử dụng createStyledButton

            datBanButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    datBan(soBan);
                }
            });
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER)); // Center the button horizontally
            buttonPanel.add(datBanButton);
            buttonPanel.setBorder(BorderFactory.createEmptyBorder(10, 0, 10, 0)); // Add space above and below the button
            panel.add(buttonPanel, BorderLayout.SOUTH);
        }

        return panel;
    }

    // Hàm xử lý khi đặt bàn
    private void datBan(int soBan) {
        // Kiểm tra trạng thái bàn
        if (!trangThaiBan[soBan]) {
            // Nếu bàn trống, cập nhật trạng thái thành đang dùng
            trangThaiBan[soBan] = true;
            JOptionPane.showMessageDialog(this, "Bạn đã đặt bàn " + (100 + soBan));
            // Cập nhật lại giao diện
            capNhatGiaoDien();
        }
    }

    // Hàm cập nhật lại giao diện sau khi đặt bàn
    private void capNhatGiaoDien() {
        mainPanel.removeAll(); // Xóa hết các thành phần cũ
        for (int i = 0; i < SO_BAN; i++) {
            mainPanel.add(createTablePanel(i)); // Tạo lại các panel
        }
        mainPanel.revalidate(); // Cập nhật lại bố cục
        mainPanel.repaint();    // Vẽ lại giao diện
    }

    // Phương thức để tạo button với kiểu tùy chỉnh
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                // Vẽ hình dạng bo tròn
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15); // Bo góc với bán kính 15
                super.paintComponent(g);
            }
        };
        button.setFocusPainted(false);
        button.setFont(new Font("Arial Unicode MS", Font.PLAIN, 14)); // Font hỗ trợ tốt emoji
        button.setForeground(new Color(255, 255, 255)); // Màu chữ trắng nổi bật
        button.setBackground(new Color(0, 102, 204, 150)); // Màu nền xanh hài hòa với độ trong suốt
        button.setPreferredSize(new Dimension(130, 40)); // Đã điều chỉnh chiều rộng và chiều cao của nút
        button.setMinimumSize(new Dimension(130, 40)); // Kích thước tối thiểu
        button.setMaximumSize(new Dimension(130, 40)); // Kích thước tối đa
        button.setBorder(null); // Bỏ viền cho nút
        button.setOpaque(false); // Đặt nút trong suốt

        // Hiệu ứng hover khi rê chuột
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseExited(java.awt.event.MouseEvent evt) {
                // Nếu nút không phải là nút được nhấn, màu trở lại bình thường
                if (button != lastClickedButton) {
                    button.setBackground(new Color(0, 102, 204, 150)); // Màu nền trở lại
                }
            }
        });

        return button;
    }

    public class RoundedLineBorder implements Border {

        private int radius;

        public RoundedLineBorder(int radius) {
            this.radius = radius;
        }

        @Override
        public Insets getBorderInsets(Component c) {
            return new Insets(radius + 1, radius + 1, radius + 2, radius);
        }

        @Override
        public boolean isBorderOpaque() {
            return true;
        }

        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2 = (Graphics2D) g;
            g2.setColor(Color.BLACK); // Border color
            g2.setStroke(new BasicStroke(4)); // Border thickness
            g2.drawRoundRect(x, y, width - 1, height - 1, radius, radius); // Rounded border
        }
    }
}
