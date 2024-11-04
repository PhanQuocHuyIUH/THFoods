package Gui;

import DAO.Ban_Dao;
import DAO.DonDatBan_Dao;
import DB.Database;
import Entity.Ban;
import Entity.DonDatBan;
import Entity.TrangThaiBan;
import org.jdatepicker.impl.DateComponentFormatter;
import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.SqlDateModel;

import java.awt.event.ActionListener;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.Properties;

public class QuanLyDatBan extends JPanel {
    private DefaultTableModel danhSachDatBantableModel;
    private JTable danhSachDatBantableList;
    private  int SO_BAN; // Số lượng bàn
    private TrangThaiBan[] trangThaiBan; // Trạng thái của mỗi bàn
    private JTextField searchField; // Trường tìm kiếm
    private JButton searchButton;   // Nút tìm kiếm
    private JButton lastClickedButton; // Nút được nhấn gần đây (cho hover hiệu ứng)
    private JList<String> danhSachDatBanList; // Danh sách các bàn đã đặt
    private JButton btnBanTrong, btnBanDaDat, btnTatCa, btnBanDangDung; // Các nút thêm
    private JPanel gridPanel;
    private int columns = 4; // Số cột cố định
    private ActionListener actionListener;
    Ban_Dao dataBan = new Ban_Dao();
    DonDatBan_Dao dataDonDatBan = new DonDatBan_Dao();
    private ArrayList<Ban> dsBan;
    private ArrayList<DonDatBan> dsDonDatBan;
    private JFrame danhSachFrameInstance = null;
    private DatBanDialog datBanDialogInstance = null;

    public QuanLyDatBan() {
        try {
            Database.getInstance().connect();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        setLayout(new BorderLayout());

        JPanel northPanel = new JPanel(new BorderLayout());
        this.add(northPanel, BorderLayout.NORTH);

        JPanel leftPanel = new JPanel();
        leftPanel.setBackground(Color.WHITE);
        leftPanel.setLayout(new FlowLayout(FlowLayout.LEFT));


        JLabel menuLabel = new JLabel(" \u2630 MENU   ");
        menuLabel.setFont(new Font("Arial Unicode MS", Font.BOLD, 24));
        menuLabel.setForeground(AppColor.den);
        leftPanel.add(menuLabel);
        // Mảng các tên nút
        String[] buttonNames = {"Danh sách đặt bàn","Bàn trống", "Bàn đang dùng","Bàn đã đặt", "Hiện tất cả"};

        // Vòng lặp để tạo nút cho mỗi tên trong mảng buttonNames và áp dụng createStyledButton
        for (String name : buttonNames) {
            JButton button = createCategoryButton(name);
            leftPanel.add(button);

            switch (name) {
                case "Bàn trống":
                    btnBanTrong = button;
                    btnBanTrong.addActionListener(e -> locBan(TrangThaiBan.Trong));
                    break;
                case "Bàn đang dùng":
                    btnBanDangDung = button;
                    btnBanDangDung.addActionListener(e -> locBan(TrangThaiBan.DangDung));
                    break;
                case "Bàn đã đặt":
                    btnBanDaDat = button;
                    btnBanDaDat.addActionListener(e -> locBan(TrangThaiBan.DaDat));
                    break;
                case "Hiện tất cả":
                    btnTatCa = button;
                    btnTatCa.addActionListener(e -> hienThiTatCaBan());
                    break;
                case "Danh sách đặt bàn":
                    button.addActionListener(e -> hienThiDanhSachDatBan());
                    break;
            }
        }

        lastClickedButton = (JButton) leftPanel.getComponent(5); // Mặc định chọn nút đầu tiên
        // Đổi màu nền cho nút đầu tiên
        lastClickedButton.setBackground(AppColor.xanhNhat);
        leftPanel.add(Box.createRigidArea(new Dimension(270, 0)));

        // Thanh tìm kiếm
        searchField = new JTextField(15);
        searchField.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        searchField.setBackground(AppColor.xam);
        leftPanel.add(searchField);

        // Nút tìm kiếm
        JButton searchButton = createStyledButton("\uD83D\uDD0D Tìm kiếm");
        searchButton.setFont(new Font("Arial Unicode MS", Font.BOLD, 14));
        searchButton.setBackground(AppColor.xanh);
        leftPanel.add(searchButton);

        northPanel.add(leftPanel, BorderLayout.WEST);

        // Khởi tạo trạng thái các bàn (ban đầu tất cả đều trống)
        try {
            SO_BAN = dataBan.getSoLuongBan();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        trangThaiBan = new TrangThaiBan[SO_BAN];
        for (int i = 0; i < SO_BAN; i++) {
            trangThaiBan[i] = TrangThaiBan.Trong;
        }

        // Khởi tạo gridPanel với GridLayout và thêm vào mainPanel
        gridPanel = new JPanel(new GridLayout(0, columns, 20, 20)); // 0 hàng, số cột cố định
        gridPanel.setBackground(AppColor.trang);
        JPanel mainPanel = new JPanel(new BorderLayout()); // Main panel chứa gridPanel
        mainPanel.setBackground(AppColor.trang);
        mainPanel.add(gridPanel, BorderLayout.NORTH); // Đặt gridPanel ở phía trên

        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Thêm padding
        this.add(mainPanel, BorderLayout.CENTER);

        capNhatGiaoDien(); // Gọi hàm cập nhật giao diện ban đầu

        JScrollPane scrollPane = new JScrollPane(mainPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        scrollPane.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);

        setVisible(true);


    }

    // Phương thức tự động điều chỉnh độ rộng của các cột
    private void packColumn(JTable table, int columnIndex, int margin) {
        DefaultTableColumnModel columnModel = (DefaultTableColumnModel) table.getColumnModel();
        TableColumn column = columnModel.getColumn(columnIndex);
        int width = 0;

        // Lấy chiều rộng tối đa của các ô trong cột
        for (int row = 0; row < table.getRowCount(); row++) {
            TableCellRenderer renderer = table.getCellRenderer(row, columnIndex);
            Component comp = table.prepareRenderer(renderer, row, columnIndex);
            width = Math.max(comp.getPreferredSize().width + margin, width);
        }

        // Thiết lập độ rộng của tiêu đề
        TableCellRenderer headerRenderer = table.getTableHeader().getDefaultRenderer();
        Component headerComp = headerRenderer.getTableCellRendererComponent(table, column.getHeaderValue(), false, false, 0, columnIndex);
        width = Math.max(headerComp.getPreferredSize().width + margin, width);

        // Thiết lập độ rộng cho cột
        column.setPreferredWidth(width);
    }


    // Phương thức để tải dữ liệu từ cơ sở dữ liệu và hiển thị vào bảng
    private void loadDataToTable() {
        try {
            dsDonDatBan = dataDonDatBan.getAllDonDatBan(); // Gọi DAO để lấy dữ liệu từ SQL
            danhSachDatBantableModel.setRowCount(0); // Xóa dữ liệu cũ trong bảng

            for (DonDatBan ddb : dsDonDatBan) {
                Object[] row = new Object[]{
                        ddb.getMaDDB(),
                        ddb.getNgayDatBan(),
                        ddb.getSoGhe(),
                        ddb.getGhiChu(),
                        ddb.getBan().getMaBan(),
                        ddb.getKhachHang(),
                        ddb.getSdt()
                };
                danhSachDatBantableModel.addRow(row); // Thêm từng hàng vào model của bảng
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi tải dữ liệu từ CSDL: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private void hienThiDanhSachDatBan() {
        try {
            Database.getInstance().connect();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        // Kiểm tra nếu JFrame hiện tại đã tồn tại thì đóng nó lại trước khi tạo JFrame mới
        if (danhSachFrameInstance != null) {
            danhSachFrameInstance.dispose();
        }

        String[] columnNames = {"Mã đơn", "Ngày đặt", "Số ghế", "Ghi chú", "Mã bàn", "Tên KH", "SĐT"};
        danhSachDatBantableModel = new DefaultTableModel(columnNames, 0) {
            // Override phương thức này để cho phép sửa đổi trong bảng
            @Override
            public boolean isCellEditable(int row, int column) {
                // Cho phép sửa đổi các cột 1, 2, 3, 5, và 6
                return column == 1 || column == 2 || column == 3 || column == 5 || column == 6;
            }
        };

        danhSachDatBantableList = new JTable(danhSachDatBantableModel);
        danhSachDatBantableList.setFont(new Font("Arial", Font.BOLD, 18));
        danhSachDatBantableList.getTableHeader().setBackground(new Color(52, 152, 219));
        danhSachDatBantableList.getTableHeader().setForeground(Color.WHITE);
        danhSachDatBantableList.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        danhSachDatBantableList.getTableHeader().setResizingAllowed(true);
        customizeTable();
        loadDataToTable();

        // Tạo JFrame mới để hiển thị danh sách đặt bàn
        danhSachFrameInstance = new JFrame("Danh Sách Đặt Bàn");
        danhSachFrameInstance.setSize(1000, 400);
        danhSachFrameInstance.setLayout(new BorderLayout());

        // Tạo bảng chứa danh sách đặt bàn với thanh cuộn
        JScrollPane danhSachScrollPane = new JScrollPane(danhSachDatBantableList);
        danhSachFrameInstance.add(danhSachScrollPane, BorderLayout.CENTER);

        // Tạo panel dưới để chứa nút xác nhận
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT)); // Nút nằm ở góc dưới bên phải
        JButton xacNhanButton = new JButton("Xác nhận");

        // Tùy chỉnh nút "Xác nhận" nếu cần thiết
        xacNhanButton.setFont(new Font("Arial", Font.PLAIN, 14)); // Đặt phông chữ
        xacNhanButton.setBackground(AppColor.xanh); // Màu nền
        xacNhanButton.setForeground(Color.WHITE); // Màu chữ

        // Thêm nút vào panel
        buttonPanel.add(xacNhanButton);

        // Thêm panel nút vào vị trí dưới của JFrame
        danhSachFrameInstance.add(buttonPanel, BorderLayout.SOUTH);

        // Đặt vị trí của cửa sổ và hiển thị
        danhSachFrameInstance.setLocationRelativeTo(null);
        danhSachFrameInstance.setVisible(true);


        xacNhanButton.addActionListener(e -> {
            doi_Trang_Thai(); // Gọi phương thức doi_Trang_Thai() khi nút được nhấn
            delete_rowTable(); // Gọi phương thức delete_rowTable() ngay sau đó
        });

    }


    private void delete_rowTable() {
        int selectRow = danhSachDatBantableList.getSelectedRow();

        // Kiểm tra xem có hàng nào được chọn không
        if (selectRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một hang");
            return; // Dừng lại nếu chưa chọn hàng nào
        }

        // Lấy mã đơn từ dòng đã chọn
        Object maDonObj = danhSachDatBantableList.getValueAt(selectRow, 0); // Cột thứ 0 là mã đơn
        if (maDonObj == null || maDonObj.toString().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã đơn không hợp lệ, không thể xóa!");
            return; // Dừng lại nếu mã đơn null hoặc rỗng
        }

        String maDon = maDonObj.toString();



        // Xóa hàng khỏi model bảng
        danhSachDatBantableModel.removeRow(selectRow);

        //Xóa đơn đặt bàn khỏi cơ sở dữ liệu

        try {
            dataDonDatBan.deleteDonDatBan(maDon);
            JOptionPane.showMessageDialog(this, "Xóa đơn đặt bàn thành công!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi xóa đơn đặt bàn: " + e.getMessage());
            e.printStackTrace(); // In chi tiết lỗi ra console để dễ debug hơn
        }
    }


    private void doi_Trang_Thai() {
        int selectRow = danhSachDatBantableList.getSelectedRow(); // Lấy dòng được chọn

        // Kiểm tra xem có hàng nào được chọn không
        if (selectRow == -1) {
            JOptionPane.showMessageDialog(this, "Vui lòng chọn một hàng trước khi thay đổi trạng thái!");
            return; // Dừng lại nếu chưa chọn hàng nào
        }

        // Lấy mã bàn từ dòng đã chọn và kiểm tra nếu null
        Object maBanObj = danhSachDatBantableList.getValueAt(selectRow, 4); // Cột thứ 4 là mã bàn
        if (maBanObj == null || maBanObj.toString().isEmpty()) {
            JOptionPane.showMessageDialog(this, "Mã bàn không hợp lệ!");
            return; // Dừng lại nếu mã bàn null hoặc rỗng
        }

        String maBan = maBanObj.toString(); // Chuyển mã bàn sang chuỗi

        try {
            // Cập nhật trạng thái bàn thành "Đang dùng"
            dataBan.updateTrangThaiBan(maBan, "DangDung");
            JOptionPane.showMessageDialog(this, "Trạng thái bàn đã được cập nhật thành 'Đang dùng'!");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi thay đổi trạng thái bàn: " + e.getMessage());
            e.printStackTrace(); // In chi tiết lỗi ra console để dễ debug hơn
        }
    }




    // Hàm tạo panel cho từng bàn ăn
    private JPanel createTablePanel(Ban ban) {
        JPanel panel = new JPanel(new BorderLayout(10, 20));
        panel.setBorder(new RoundedLineBorder(15));
        panel.setPreferredSize(new Dimension(200, 350));

        // Kiểm tra đường dẫn hình ảnh và xử lý
        ImageIcon icon = new ImageIcon("src/img/datban.jpg");
        if (icon.getIconWidth() == -1) {
            // Xử lý nếu không tìm thấy hình ảnh
            icon = new ImageIcon(); // Hoặc sử dụng một hình ảnh mặc định
        }
        Image img = icon.getImage().getScaledInstance(180, 180, Image.SCALE_SMOOTH);
        JLabel imageLabel = new JLabel(new ImageIcon(img));
        imageLabel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        panel.add(imageLabel, BorderLayout.CENTER);

        JLabel tableLabel = new JLabel("Mã bàn: " + ban.getMaBan(), SwingConstants.CENTER);
        tableLabel.setFont(new Font("Segoe UI", Font.BOLD, 18));
        panel.add(tableLabel, BorderLayout.NORTH);

        // Cập nhật trạng thái bàn
        JLabel statusLabel;
        if (ban.getTrangThai() == TrangThaiBan.DaDat) {
            statusLabel = new JLabel("Đã đặt", SwingConstants.CENTER);
            statusLabel.setForeground(Color.RED);
        } else if (ban.getTrangThai() == TrangThaiBan.DangDung) {
            statusLabel = new JLabel("Đang dùng", SwingConstants.CENTER);
            statusLabel.setForeground(Color.ORANGE);
        } else {
            JButton datBanButton = createStyledButton("Đặt bàn");
            datBanButton.addActionListener(e -> new DatBanDialog(ban));
            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
            buttonPanel.add(datBanButton);
            panel.add(buttonPanel, BorderLayout.SOUTH);
            return panel; // Trả về ngay nếu bàn đang trống
        }
        statusLabel.setFont(new Font("Segoe UI", Font.BOLD, 16));
        panel.add(statusLabel, BorderLayout.SOUTH);

        return panel;
    }

    // Hàm cập nhật lại giao diện sau khi đặt bàn
    private void capNhatGiaoDien() {
        try {
            dsBan = dataBan.getAllBan();  // Lấy tất cả bàn từ CSDL
            if (dsBan != null && !dsBan.isEmpty()) {
                hienThiBan(dsBan);  // Hiển thị danh sách bàn lên giao diện
            } else {
                JOptionPane.showMessageDialog(this, "Không có bàn nào để hiển thị.");
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Lỗi khi lấy dữ liệu từ CSDL: " + e.getMessage());
            e.printStackTrace(); // In lỗi ra console để debug
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Đã xảy ra lỗi: " + e.getMessage());
            e.printStackTrace(); // Xử lý các lỗi không mong muốn
        }
    }


    // Phương thức hiển thị các bàn theo danh sách truyền vào
    private void hienThiBan(ArrayList<Ban> danhSachBan) {
        gridPanel.removeAll(); // Xóa các bàn cũ
        int soBanHienThi = 0;

        for (Ban ban : danhSachBan) {
            JPanel tablePanel = createTablePanel(ban);  // Tạo panel cho từng bàn
            gridPanel.add(tablePanel);  // Thêm panel của bàn vào gridPanel
            soBanHienThi++;
        }

        // Tính số lượng ô trống cần thêm để lấp đầy hàng cuối cùng
        if (columns > 0) {
            int soBanDu = columns - (soBanHienThi % columns);
            if (soBanDu < columns) { // Thêm các ô trống để lấp đầy hàng cuối
                for (int i = 0; i < soBanDu; i++) {
                    JPanel emptyPanel = new JPanel();
                    emptyPanel.setOpaque(false);
                    emptyPanel.setPreferredSize(new Dimension(200, 350));
                    gridPanel.add(emptyPanel);
                }
            }
        } else {
            JOptionPane.showMessageDialog(this, "Số cột không hợp lệ.");
        }

        gridPanel.revalidate();
        gridPanel.repaint(); // Cập nhật giao diện
    }


    // Hàm lọc các bàn dựa trên trạng thái

    private void locBan(TrangThaiBan tr_thai) {
        gridPanel.removeAll(); // Xóa các bàn cũ
        try {
            dsBan = dataBan.getDanhSachBanTheoTrangThai(tr_thai); // Lọc bàn theo trạng thái
            hienThiBan(dsBan); // Cập nhật giao diện với danh sách bàn theo trạng thái đã chọn
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Hàm hiển thị tất cả các bàn

    private void hienThiTatCaBan() {
        try {
            // Lấy danh sách tất cả các bàn từ cơ sở dữ liệu
            dsBan = dataBan.getAllBan();

            if (dsBan != null && !dsBan.isEmpty()) {
                // Gọi hàm hienThiBan với danh sách bàn lấy được từ CSDL
                hienThiBan(dsBan);
            } else {
                JOptionPane.showMessageDialog(this, "Không có bàn nào để hiển thị.");
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Lỗi khi lấy dữ liệu từ CSDL: " + e.getMessage());
        }
    }

    // Phương thức để tạo button với kiểu tùy chỉnh
    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(AppColor.xanh);
        button.setForeground(Color.WHITE);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.addActionListener(actionListener);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        return button;
    }

    private JButton createCategoryButton(String category) {
        JButton button = createStyledButton(category);
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

    public class DatBanDialog extends JDialog {
        private JTextField maDonField, soGheField, ghiChuField, maBanField, tenKhachHangField, soDienThoaiField;
        private JDatePickerImpl ngayDatPicker;
        private JButton xacNhanButton, huyButton;
        private Ban ban;

        public DatBanDialog(Ban ban) {
            this.ban = ban; // Lưu đối tượng Ban vào biến instance để sử dụng trong dialog

            setTitle("Thông tin đặt bàn");
            setSize(400, 350);
            setLayout(new GridBagLayout());

            GridBagConstraints gbc = new GridBagConstraints();
            gbc.insets = new Insets(10, 10, 5, 10); // Điều chỉnh khoảng cách giữa các thành phần
            gbc.fill = GridBagConstraints.HORIZONTAL;



            // Các trường nhập thông tin khách hàng
            maDonField = new JTextField(String.format("DDB"), 15);
            maDonField.setEditable(true);

            // Tạo model cho JDatePicker
            SqlDateModel model = new SqlDateModel();
            Properties p = new Properties();
            p.put("text.today", "Today");
            p.put("text.month", "Month");
            p.put("text.year", "Year");
            JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
            ngayDatPicker = new JDatePickerImpl(datePanel, new DateComponentFormatter());

            soGheField = new JTextField(15);
            ghiChuField = new JTextField(15);

            // Hiển thị mã bàn từ đối tượng ban truyền vào
            maBanField = new JTextField(ban.getMaBan(), 15);
            maBanField.setEditable(false);  // Không cho phép chỉnh sửa mã bàn

            tenKhachHangField = new JTextField(15);
            soDienThoaiField = new JTextField(15);

            // Thêm các label và component vào giao diện
            gbc.gridx = 0;
            gbc.gridy = 0;
            gbc.anchor = GridBagConstraints.WEST;
            add(new JLabel("Mã đơn:"), gbc);
            gbc.gridx = 1;
            add(maDonField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 1;
            add(new JLabel("Ngày đặt:"), gbc);
            gbc.gridx = 1;
            add(ngayDatPicker, gbc); // Sử dụng JDatePicker thay vì JTextField

            gbc.gridx = 0;
            gbc.gridy = 2;
            add(new JLabel("Số ghế:"), gbc);
            gbc.gridx = 1;
            add(soGheField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 3;
            add(new JLabel("Ghi chú:"), gbc);
            gbc.gridx = 1;
            add(ghiChuField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 4;
            add(new JLabel("Mã bàn:"), gbc);
            gbc.gridx = 1;
            add(maBanField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 5;
            add(new JLabel("Tên KH:"), gbc);
            gbc.gridx = 1;
            add(tenKhachHangField, gbc);

            gbc.gridx = 0;
            gbc.gridy = 6;
            add(new JLabel("SĐT:"), gbc);
            gbc.gridx = 1;
            add(soDienThoaiField, gbc);

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            xacNhanButton = createStyledButton("Xác nhận");
            huyButton = createStyledButton("Hủy");

            xacNhanButton.setPreferredSize(new Dimension(100, 30));
            huyButton.setPreferredSize(new Dimension(100, 30));

            buttonPanel.add(xacNhanButton);
            buttonPanel.add(huyButton);

            gbc.gridx = 0;
            gbc.gridy = 7;
            gbc.gridwidth = 2;
            gbc.anchor = GridBagConstraints.SOUTHEAST;
            gbc.insets = new Insets(15, 10, 10, 10);
            add(buttonPanel, gbc);

            // Sự kiện cho nút xác nhận
            xacNhanButton.addActionListener(e -> {
                String maDon = maDonField.getText();
                Date selectedDate = (Date) ngayDatPicker.getModel().getValue(); // Lấy ngày từ JDatePicker
                String soGhe = soGheField.getText();
                String ghiChu = ghiChuField.getText();
                String tenKhachHang = tenKhachHangField.getText();
                String soDienThoai = soDienThoaiField.getText();

                if (!maDon.isEmpty() && selectedDate != null && !soGhe.isEmpty() && !tenKhachHang.isEmpty() && !soDienThoai.isEmpty()) {
                    DonDatBan donDatBan = new DonDatBan(maDon, selectedDate.toString(), Integer.parseInt(soGhe), ghiChu, ban, tenKhachHang, soDienThoai);

                    try {
                        dataBan.updateTrangThaiBan(ban.getMaBan(), "DaDat");
                        capNhatGiaoDien();
                        try {
                            dataDonDatBan.addDonDatBan(donDatBan);
                        } catch (Exception ex) {
                            throw new RuntimeException(ex);
                        }
                        JOptionPane.showMessageDialog(this, "Đơn đặt bàn đã được thêm thành công!");
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(this, "Lỗi khi thêm đơn đặt bàn: " + ex.getMessage());
                        ex.printStackTrace();
                    }


                    dispose(); // Đóng dialog sau khi xác nhận
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



    private void customizeTable() {
        danhSachDatBantableList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        danhSachDatBantableList.setRowHeight(30);
        danhSachDatBantableList.setBackground(AppColor.trang);
        danhSachDatBantableList.setForeground(AppColor.den);
        danhSachDatBantableList.setGridColor(AppColor.xanh);
        danhSachDatBantableList.setFillsViewportHeight(true);
        danhSachDatBantableList.setDefaultEditor(Object.class, null);

        // Tự động điều chỉnh độ rộng của từng cột
        for (int i = 0; i < danhSachDatBantableList.getColumnCount(); i++) {
            packColumn(danhSachDatBantableList, i, 10); // 10 là lề cho mỗi cột
        }

        danhSachDatBantableList.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if(isSelected){
                    c.setBackground(AppColor.xanhNhat);
                } else if (row % 2 == 0) {
                    c.setBackground(AppColor.xam);
                } else {
                    c.setBackground(AppColor.trang);
                }
                return c;
            }
        });

        JTableHeader header = danhSachDatBantableList.getTableHeader();
        header.setBackground(AppColor.xanh);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 14));
    }


}

