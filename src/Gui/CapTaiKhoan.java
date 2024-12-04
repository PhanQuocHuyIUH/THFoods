package Gui;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class CapTaiKhoan extends JFrame {
    private JPanel mainPanel; // Đưa mainPanel thành thuộc tính của lớp
    private JButton lastClickedButton; // Nút được nhấn trước đó

    public CapTaiKhoan() throws SQLException {
        // Cấu hình cho frame
        setTitle("Nhà Hàng TH Food");
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
        leftPanel.setBackground(new Color(255, 255, 255)); // Màu nền trắng cho leftPanel

        // Logo trên phần nút chức năng
        ImageIcon logoIcon = new ImageIcon("src\\img\\logo2.png");
        Image logoImage = logoIcon.getImage().getScaledInstance(140, 180, Image.SCALE_SMOOTH);
        JLabel logoLabel = new JLabel(new ImageIcon(logoImage));
        logoLabel.setHorizontalAlignment(SwingConstants.CENTER);
        leftPanel.add(logoLabel, BorderLayout.NORTH);

        // Panel chứa các nút chức năng
        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(255,255,255));
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS)); // Thay đổi layout thành BoxLayout
        buttonPanel.setOpaque(false); // Tắt nền mặc định của panel

        // Tạo 2 nút chức năng với màu nền
        String[] buttonLabels = {
                "\uD83D\uDC68\u200D NHÂN VIÊN",
                "\uD83D\uDC68\u200D QUẢN LÝ",

        };
        // Tạo một biến để lưu nút ĐẶT MÓN
        JButton firstButton = null;

        for (String label : buttonLabels) {
            JButton button = createStyledButton(label); // Nút có màu sắc hài hòa

            //nếu là nút thong ke thì đổi ten nut
            if(label.equals("\uD83D\uDC68\u200D NHÂN VIÊN")) {
                button.setText("\uD83D\uDC68\u200D NHÂN VIÊN");
            }

            //neu la nut nhan vien thi doi ten nut
            if(label.equals("\uD83D\uDC68\u200D QUẢN LÝ")) {
                button.setText("\uD83D\uDC68\u200D QUẢN LÝ");
            }
            buttonPanel.add(button);
            button.addActionListener(new ButtonClickListener(this));

            // Thêm khoảng cách giữa các nút
            buttonPanel.add(Box.createRigidArea(new Dimension(10, 30))); // Thêm khoảng cách 10px giữa các nút

            // Lưu nút NHÂN VIÊN
            if (label.equals("\uD83D\uDC68\u200D NHÂN VIÊN")) {
                firstButton = button;
            }
        }

        // Tạo nút ĐĂNG XUẤT
        JButton logoutBT = new JButton("🚪");
        logoutBT.setBackground(new Color(255, 255, 255));
        logoutBT.setBorder(null);
        //bỏ chọn
        logoutBT.setFocusPainted(false);
        logoutBT.addActionListener(new ButtonClickListener(this));
        // SET FONT
        logoutBT.setFont(new Font("Arial Unicode MS", Font.BOLD, 60)); // Font hỗ trợ tốt emoji
        //PENABLE BUTTON
        JPanel logoutPN = new JPanel();
        logoutPN.setLayout(new BorderLayout());
        logoutPN.add(logoutBT);
        leftPanel.add(buttonPanel, BorderLayout.CENTER);
        leftPanel.add(logoutPN, BorderLayout.SOUTH);

        //SET FONT CHO NÚT CUỐI CÙNG BỰ 40
        buttonPanel.getComponent(buttonPanel.getComponentCount()-1).setFont(new Font("Arial Unicode MS", Font.PLAIN, 40));

        // Tạo panel bên phải cho phần hiển thị nội dung chức năng (main panel)
        mainPanel = new JPanel(); // Khai báo và khởi tạo mainPanel
        mainPanel.setLayout(new CardLayout());
        mainPanel.setBackground(Color.WHITE); // Màu nền trắng cho main panel
        mainPanel.setBorder(BorderFactory.createLineBorder(Color.GRAY, 2)); // Đường viền đẹp cho main panel

        // Thêm các nội dung tương ứng cho mỗi chức năng

        mainPanel.add(new CapTaiKhoanNV(), "\uD83D\uDC68\u200D NHÂN VIÊN");
        mainPanel.add(new CapTaiKhoanQL(), "\uD83D\uDC68\u200D QUẢN LÝ");


        // Tạo JSplitPane để chia khu vực bên trái và bên phải, bỏ đi thanh điều chỉnh kích thước
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, leftPanel, mainPanel);
        splitPane.setDividerLocation(110); // Thay đổi kích thước chia tay
        splitPane.setResizeWeight(0.1);    // 10% cho panel trái, 90% cho panel phải
        // Thêm splitPane vào frame
        add(splitPane);

        // Tự động đổi màu cho nút NHÂN VIÊN khi chương trình khởi động
        if (firstButton != null) {
            firstButton.setBackground(new Color(230,240,255)); // Đổi màu cho nút ĐẶT MÓN
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
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 15, 15); // Bo góc với bán kính 15
                super.paintComponent(g);
            }
        };
        button.setFocusPainted(false);
        button.setFont(new Font("Arial Unicode MS", Font.PLAIN, 14)); // Font hỗ trợ tốt emoji
        button.setForeground(new Color(0)); // Màu chữ trắng nổi bật
        button.setBackground(new Color(255, 255, 255)); // Màu nền xanh hài hòa với độ trong suốt
        button.setPreferredSize(new Dimension(130, 40)); // Đã điều chỉnh chiều rộng và chiều cao của nút
        button.setMinimumSize(new Dimension(130, 40)); // Kích thước tối thiểu
        button.setMaximumSize(new Dimension(130, 40)); // Kích thước tối đa
        button.setBorder(null); // Bỏ viền cho nút
        button.setOpaque(false); // Đặt nút trong suốt


        // Hiệu ứng hover khi rê chuột
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                // Nếu nút không phải là nút được nhấn và không phải là nút quản lý
                if (button != lastClickedButton && !button.getText().equals("\uD83C\uDF7D")) {
                    button.setBackground(new Color(230,240,255)); // Đổi màu nền khi rê chuột vào
                }
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                // Nếu nút không phải là nút được nhấn và không phải là nút quản lý
                if (button != lastClickedButton ) {
                    button.setBackground(new Color(255,255,255)); // Màu nền trở lại
                }
            }
        });

        return button;
    }

    // Lớp xử lý sự kiện khi nhấn nút
    private class ButtonClickListener extends Component implements ActionListener {
        private CapTaiKhoan capTaiKhoan; // Tham chiếu đến capTaiKhoan

        public ButtonClickListener(CapTaiKhoan capTaiKhoan) {
            this.capTaiKhoan = capTaiKhoan; // Khởi tạo tham chiếu
        }
        @Override
        public void actionPerformed(ActionEvent e) {
            JButton sourceButton = (JButton) e.getSource();
            CardLayout cl = (CardLayout) mainPanel.getLayout();
            String command = sourceButton.getText();

            // Kiểm tra nút đăng xuất
            if (command.equals("\uD83D\uDEAA")) {
                int response = JOptionPane.showConfirmDialog(this, "Bạn có chắc chắn muốn đăng xuất không?", "Xác Nhận Đăng Xuất", JOptionPane.YES_NO_OPTION);
                if (response == JOptionPane.YES_OPTION) {
                    // Nếu người dùng chọn "Có", thực hiện đăng xuất (đóng JFrame hoặc chuyển đến màn hình đăng nhập)
                    new DangNhap();
                    capTaiKhoan.dispose();
                }
                return; // Trở ra không thực hiện các thao tác khác
            }

            // Đổi màu nút đã nhấn
            if (lastClickedButton != null) {
                lastClickedButton.setBackground(new Color(255,255,255)); // Đặt lại màu cho nút trước đó
            }
            sourceButton.setBackground(new Color(230,240,255)); // Đổi màu nút hiện tại
            lastClickedButton = sourceButton; // Cập nhật nút được nhấn trước đó

            // Chuyển đổi giữa các nội dung dựa trên nút nhấn
            switch (command) {
                case "\uD83D\uDC68\u200D NHÂN VIÊN":
                    cl.show(mainPanel, "\uD83D\uDC68\u200D NHÂN VIÊN");
                    break;
                case "\uD83D\uDC68\u200D QUẢN LÝ":
                    cl.show(mainPanel, "\uD83D\uDC68\u200D QUẢN LÝ");
                    break;
            }
        }

    }
}
