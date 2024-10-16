package Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class TrangChu extends JFrame {
    private JPanel mainPanel; // Đưa mainPanel thành thuộc tính của lớp
    private JButton lastClickedButton; // Nút được nhấn trước đó

    public TrangChu() {
        // Cấu hình cho frame
        setTitle(" Nhà Hàng TH Food");
        setSize(900, 600); // Có thể giữ dòng này hoặc bỏ nếu không cần thiết
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Đặt chương trình ở chế độ toàn màn hình
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Tạo panel bên trái chứa logo và các nút chức năng
        JPanel leftPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                // Vẽ hình nền cho leftPanel
                ImageIcon icon = new ImageIcon("background_image.jpg"); // Đường dẫn tới hình nền cho leftPanel
                Image img = icon.getImage();
                g.drawImage(img, 0, 0, getWidth(), getHeight(), this);
            }
        };
        leftPanel.setLayout(new BorderLayout());

        // Logo trên phần nút chức năng
        ImageIcon logoIcon = new ImageIcon("src\\img\\logo.png");
        Image logoImage = logoIcon.getImage().getScaledInstance(80, 80, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(logoImage));
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        leftPanel.add(logoLabel, BorderLayout.NORTH);

        // Panel chứa các nút chức năng
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(8, 1, 10, 10)); // Thêm khoảng cách giữa các nút
        buttonPanel.setOpaque(false); // Tắt nền mặc định của panel

        // Tạo 8 nút chức năng với màu nền
        String[] buttonLabels = {"ĐẶT MÓN", "QUẢN LÝ ĐẶT BÀN", "QUẢN LÝ BÀN", "QUẢN LÝ HÓA ĐƠN",
                "QUẢN LÝ NHÂN VIÊN", "QUẢN LÝ PHIẾU ĐẶT", "QUẢN LÝ THỰC ĐƠN", "BÁO CÁO THỐNG KÊ"};

        // Tạo một biến để lưu nút ĐẶT MÓN
        JButton firstButton = null;

        for (String label : buttonLabels) {
            JButton button = createStyledButton(label); // Nút có màu sắc hài hòa
            buttonPanel.add(button);
            button.addActionListener(new ButtonClickListener());

            // Lưu nút ĐẶT MÓN
            if (label.equals("ĐẶT MÓN")) {
                firstButton = button;
            }
        }

        leftPanel.add(buttonPanel, BorderLayout.CENTER);

        // Tạo panel bên phải cho phần hiển thị nội dung chức năng (main panel)
        mainPanel = new JPanel(); // Khai báo và khởi tạo mainPanel
        mainPanel.setLayout(new CardLayout());
        mainPanel.setBackground(Color.WHITE); // Màu nền trắng cho main panel

        // Thêm các nội dung tương ứng cho mỗi chức năng
        mainPanel.add(new DatMon(), "DatMon");
        mainPanel.add(new JLabel("Nội dung Quản lý Đặt Bàn", SwingConstants.CENTER), "QuanLyDatBan");
        mainPanel.add(new JLabel("Nội dung Quản lý Bàn", SwingConstants.CENTER), "QuanLyBan");
        mainPanel.add(new QuanLyHoaDon(), "QuanLyHoaDon");
        mainPanel.add(new QuanLyNhanVien(), "QuanLyNhanVien");
        mainPanel.add(new JLabel("Nội dung Quản lý Phiếu Đặt", SwingConstants.CENTER), "QuanLyPhieuDat");
        mainPanel.add(new JLabel("Nội dung Quản lý Thực Đơn", SwingConstants.CENTER), "QuanLyThucDon");
        mainPanel.add(new BaoCaoThongKe(), "BaoCaoThongKe");

        // Tạo JSplitPane để chia khu vực bên trái và bên phải
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, mainPanel);
        splitPane.setDividerLocation(200);
        splitPane.setOneTouchExpandable(true); // Thêm thanh kéo

        // Thêm splitPane vào frame
        add(splitPane);

        // Tự động đổi màu cho nút ĐẶT MÓN khi chương trình khởi động
        if (firstButton != null) {
            firstButton.setBackground(new Color(0, 0, 255, 150)); // Đổi màu cho nút ĐẶT MÓN
            lastClickedButton = firstButton; // Cập nhật nút được nhấn trước đó
        }

        // Hiển thị frame
        setVisible(true);
    }

    // Phương thức tạo nút với màu sắc hài hòa
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text) {
            @Override
            protected void paintComponent(Graphics g) {
                // Vẽ hình dạng bo tròn
                Graphics2D g2 = (Graphics2D) g;
                g2.setColor(getBackground());
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25); // Bo góc với bán kính 25
                super.paintComponent(g);
            }
        };
        button.setFocusPainted(false);
        button.setFont(new Font("Arial", Font.BOLD, 14)); // Đổi sang font Arial
        button.setForeground(new Color(255, 255, 255)); // Màu chữ trắng nổi bật
        button.setBackground(new Color(0, 102, 204, 150)); // Màu nền xanh hài hòa với độ trong suốt
        button.setPreferredSize(new Dimension(100, 60)); // Kích thước nút nhỏ hơn
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

    // Lớp xử lý sự kiện khi nhấn nút
    private class ButtonClickListener implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton sourceButton = (JButton) e.getSource();
            CardLayout cl = (CardLayout) mainPanel.getLayout();
            String command = sourceButton.getText();

            // Đổi màu nút đã nhấn
            if (lastClickedButton != null) {
                lastClickedButton.setBackground(new Color(0, 102, 204, 150)); // Đặt lại màu cho nút trước đó
            }
            sourceButton.setBackground(new Color(0, 0, 255, 150)); // Đổi màu nút hiện tại
            lastClickedButton = sourceButton; // Cập nhật nút được nhấn trước đó

            // Chuyển đổi giữa các nội dung dựa trên nút nhấn
            switch (command) {
                case "ĐẶT MÓN":
                    cl.show(mainPanel, "DatMon");
                    break;
                case "QUẢN LÝ ĐẶT BÀN":
                    cl.show(mainPanel, "QuanLyDatBan");
                    break;
                case "QUẢN LÝ BÀN":
                    cl.show(mainPanel, "QuanLyBan");
                    break;
                case "QUẢN LÝ HÓA ĐƠN":
                    cl.show(mainPanel, "QuanLyHoaDon");
                    break;
                case "QUẢN LÝ NHÂN VIÊN":
                    cl.show(mainPanel, "QuanLyNhanVien");
                    break;
                case "QUẢN LÝ PHIẾU ĐẶT":
                    cl.show(mainPanel, "QuanLyPhieuDat");
                    break;
                case "QUẢN LÝ THỰC ĐƠN":
                    cl.show(mainPanel, "QuanLyThucDon");
                    break;
                case "BÁO CÁO THỐNG KÊ":
                    cl.show(mainPanel, "BaoCaoThongKe");
                    break;
                default:
                    break;
            }
        }
    }

    public static void main(String[] args) {
        // Khởi động ứng dụng
        SwingUtilities.invokeLater(() -> new TrangChu());
    }
}
