package Gui;

public class QuanLyDatBan {
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class QuanLyDatBan extends JPanel {
    private JPanel mainPanel;
    private final int SO_BAN = 20; // Số lượng bàn (có thể điều chỉnh)
    private boolean[] trangThaiBan; // Trạng thái của mỗi bàn (true: đang dùng, false: còn trống)
    private JTextField searchField; // Trường tìm kiếm
    private JButton searchButton;   // Nút tìm kiếm

    public QuanLyDatBan() {
        setLayout(new BorderLayout());


        JPanel northPanel = new JPanel(new BorderLayout());
        this.add(northPanel, BorderLayout.NORTH);

        JPanel leftPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        searchField = new JTextField(30); // Ô nhập tìm kiếm
        searchField.setPreferredSize(new Dimension(120, 30));
        searchButton = new JButton("Tìm kiếm");// Nút tìm kiếm
        styleButton(searchButton, Color.cyan, Color.BLACK);
        leftPanel.add(searchField);
        leftPanel.add(searchButton);
        northPanel.add(leftPanel);

        this.add(leftPanel, BorderLayout.NORTH);

//        // PopupMenu bên phải
//        JPanel rightPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
//        JButton optionsButton = new JButton("Tùy chọn"); // Nút để mở JPopupMenu
//        rightPanel.add(optionsButton);

//        northPanel.add(rightPanel, BorderLayout.NORTH);

        // Khởi tạo trạng thái các bàn (ban đầu tất cả đều trống)
        trangThaiBan = new boolean[SO_BAN];
        for (int i = 0; i < SO_BAN; i++) {
            trangThaiBan[i] = false; // false: bàn còn trống
        }

        // Tạo panel chính với lưới bố cục
        mainPanel = new JPanel(new GridLayout(0, 4, 50, 50)); // Số cột cố định là 2, số hàng là động
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
        JPanel panel = new JPanel(new BorderLayout());

        // Thêm đường viền cho từng bàn ăn
        panel.setBorder(BorderFactory.createLineBorder(Color.BLACK, 2)); // Đường viền màu đen dày 2px

        // Hình ảnh minh họa cho bàn ăn (sử dụng hình mặc định)
        JLabel imageLabel = new JLabel(new ImageIcon("src\\img\\datban.png"));
        panel.add(imageLabel, BorderLayout.CENTER);

        // Nhãn hiển thị mã bàn ăn
        JLabel tableLabel = new JLabel("Mã bàn: " + (100 + soBan), SwingConstants.CENTER);
        panel.add(tableLabel, BorderLayout.NORTH);

        // Nhãn hoặc nút cho trạng thái đặt bàn
        if (trangThaiBan[soBan]) {
            // Bàn đang dùng
            JLabel statusLabel = new JLabel("Đang dùng bữa", SwingConstants.CENTER);
            statusLabel.setForeground(Color.RED);
            panel.add(statusLabel, BorderLayout.SOUTH);
        } else {
            // Bàn trống, hiển thị nút "Đặt bàn"
            JButton datBanButton = new JButton("Đặt bàn");
            styleButton(datBanButton, Color.cyan, Color.BLACK); // Áp dụng kiểu cho nút
            datBanButton.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    datBan(soBan);
                }
            });
            panel.add(datBanButton, BorderLayout.SOUTH);
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

    // Phương thức để tạo kiểu cho JButton
    private void styleButton(JButton button, Color background, Color foreground) {
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFont(new Font("Serif", Font.BOLD, 18));
        button.setFocusPainted(false); // Bỏ đường viền khi focus
        button.setBorder(BorderFactory.createLineBorder(background.darker(), 2)); // Tạo đường viền bo
        button.setPreferredSize(new Dimension(120, 50)); // Tăng kích thước nút
    }
}
