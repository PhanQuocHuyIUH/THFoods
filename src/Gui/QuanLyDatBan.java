package Gui;

import DAO.Ban_Dao;
import DAO.DonDatBan_Dao;
import DAO.KhachHang_Dao;
import DB.Database;
import Entity.Ban;
import Entity.DonDatBan;
import Entity.KhachHang;
import Entity.TrangThaiDonDat;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;


public class QuanLyDatBan extends JPanel {
    private JButton btn_XacNhanDon;
    private DefaultTableModel ds_DatBan_TableModel;
    private JTable ds_DatBan_TableList;
    final boolean[] isToday = {true};
    private JButton btn_DatBan;
    private JButton btn_HuyChonBan;
    private JButton tableButton;
    final JButton[] selectedButton = {null}; // Biến duy nhất lưu nút đã chọn
    private JPanel buttonPanel;
    private JComboBox<String> timeComboBox;
    private JComboBox<String> dateComboBox;
    private JTextField tableTextField;
    private JTextField phoneTextField;
    private JTextField nameTextField;
    private JTextField notesTextField;
    private JComboBox<String> chonBanComboBox;
    private JTextField soGheTextField;
    private JButton resetButton;
    private KhachHang_Dao ds_KhachHang_dao;
    private DonDatBan_Dao ds_DonDat_dao;

    QuanLyDatBan(){
        try {
            Database.getInstance().connect();
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        this.setLayout(new BorderLayout());

        // Phần bảng danh sách đặt bàn
        JPanel dsDatBan_mainPanel = new JPanel(new BorderLayout());

        // Thêm padding cho dsDatBan_mainPanel
        dsDatBan_mainPanel.setBorder(new EmptyBorder(15, 10, 0, 10)); // Thêm padding

        this.add(dsDatBan_mainPanel, BorderLayout.CENTER);
        dsDatBan_mainPanel.add(createTable(), BorderLayout.CENTER);

        JPanel dsDatBan_ButtonPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        dsDatBan_ButtonPanel.setBorder(new EmptyBorder(15, 0, 15, 0));
        dsDatBan_mainPanel.add(dsDatBan_ButtonPanel, BorderLayout.SOUTH);

        btn_XacNhanDon = new JButton();
        btn_XacNhanDon = createStyledButton("Xác nhận đơn đặt");
        dsDatBan_ButtonPanel.add(btn_XacNhanDon);


        // Phần tạo đơn đặt bàn

        JPanel panel_Bill = new JPanel(new BorderLayout());
        panel_Bill.setBorder(BorderFactory.createMatteBorder(0, 2, 0, 0, Color.BLACK));
        this.add(panel_Bill, BorderLayout.EAST);
        panel_Bill.add(panel_CreateBill());


    }

    private JPanel panel_CreateBill() {
        JPanel panel_CreateBill = new JPanel();
        panel_CreateBill.setLayout(new BorderLayout());
        panel_CreateBill.setBackground(AppColor.trang);
        panel_CreateBill.setBorder(new EmptyBorder(15, 10, 0, 10));

        // Tạo JPanel cho phần North và thiết lập GridBagLayout
        JPanel northPanel = new JPanel();
        northPanel.setBackground(AppColor.trang);
        GridBagLayout gridBagLayout = new GridBagLayout();
        northPanel.setLayout(gridBagLayout);

        // GridBagConstraints để kiểm soát vị trí các phần tử
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Tăng kích thước margin và padding
        gbc.insets = new Insets(10, 10, 10, 10);  // Khoảng cách giữa các phần tử, tăng lên 10px

        // Cập nhật cỡ chữ cho JLabel
        Font labelFont = new Font("Arial Unicode MS", Font.BOLD, 16);

        // 1. ComboBox chọn ngày
        JLabel lblDate = new JLabel("\uD83D\uDCC5 Chọn ngày đặt:");
        lblDate.setFont(labelFont); // Cập nhật font cho JLabel
        gbc.gridx = 0;
        gbc.gridy = 0;
        northPanel.add(lblDate, gbc);

        dateComboBox = new JComboBox<>(generateDateOptions());
        dateComboBox.setPreferredSize(new Dimension(200, 30)); // Cập nhật kích thước cho ComboBox
        dateComboBox.setFont(new Font("Arial", Font.PLAIN, 14)); // Cập nhật cỡ chữ cho ComboBox
        gbc.gridx = 1;
        gbc.gridy = 0;
        northPanel.add(dateComboBox, gbc);

        // 2. ComboBox chọn giờ
        JLabel lblTime = new JLabel("\ud83d\udd5f Chọn giờ:");
        lblTime.setFont(labelFont); // Cập nhật font cho JLabel
        gbc.gridx = 0;
        gbc.gridy = 1;
        northPanel.add(lblTime, gbc);

        timeComboBox = new JComboBox<>(generateHourOptions(isToday[0]));
        timeComboBox.setPreferredSize(new Dimension(200, 30)); // Cập nhật kích thước cho ComboBox
        timeComboBox.setFont(new Font("Arial", Font.PLAIN, 14)); // Cập nhật cỡ chữ cho ComboBox
        dateComboBox.addActionListener(e -> {
            int selectedIndex = dateComboBox.getSelectedIndex();
            isToday[0] = (selectedIndex == 0); // Ngày đầu tiên trong danh sách là hôm nay
            timeComboBox.setModel(new DefaultComboBoxModel<>(generateHourOptions(isToday[0])));
        });
        gbc.gridx = 1;
        gbc.gridy = 1;
        northPanel.add(timeComboBox, gbc);

        // 3. TextField nhập số điện thoại
        JLabel lblPhone = new JLabel("\u260e\ufe0f Số điện thoại:");
        lblPhone.setFont(labelFont); // Cập nhật font cho JLabel
        gbc.gridx = 0;
        gbc.gridy = 2;
        northPanel.add(lblPhone, gbc);

        phoneTextField = new JTextField(20);
        phoneTextField.setPreferredSize(new Dimension(200, 30)); // Cập nhật kích thước cho TextField
        phoneTextField.setFont(new Font("Arial", Font.PLAIN, 14)); // Cập nhật cỡ chữ cho TextField
        gbc.gridx = 1;
        gbc.gridy = 2;
        northPanel.add(phoneTextField, gbc);

        // 4. TextField nhập tên khách hàng
        JLabel lblName = new JLabel("\uD83D\uDC68\u200D Tên khách hàng:");
        lblName.setFont(labelFont); // Cập nhật font cho JLabel
        gbc.gridx = 0;
        gbc.gridy = 3;
        northPanel.add(lblName, gbc);

        nameTextField = new JTextField(20);
        nameTextField.setPreferredSize(new Dimension(200, 30)); // Cập nhật kích thước cho TextField
        nameTextField.setFont(new Font("Arial", Font.PLAIN, 14)); // Cập nhật cỡ chữ cho TextField
        gbc.gridx = 1;
        gbc.gridy = 3;
        northPanel.add(nameTextField, gbc);

        // 5. TextField nhập số ghế
        JLabel lblsoGhe = new JLabel("\u2795 Số người:");
        lblsoGhe.setFont(labelFont); // Cập nhật font cho JLabel
        gbc.gridx = 0;
        gbc.gridy = 4;
        northPanel.add(lblsoGhe, gbc);

        soGheTextField = new JTextField(20);
        soGheTextField.setPreferredSize(new Dimension(200, 30)); // Cập nhật kích thước cho TextField
        soGheTextField.setFont(new Font("Arial", Font.PLAIN, 14)); // Cập nhật cỡ chữ cho TextField
        gbc.gridx = 1;
        gbc.gridy = 4;
        northPanel.add(soGheTextField, gbc);

        // 6. TextField nhập ghi chú
        JLabel lblNotes = new JLabel("\uD83D\uDCDD Ghi chú:");
        lblNotes.setFont(labelFont); // Cập nhật font cho JLabel
        gbc.gridx = 0;
        gbc.gridy = 5;
        northPanel.add(lblNotes, gbc);

        notesTextField = new JTextField();
        notesTextField.setPreferredSize(new Dimension(200, 30)); // Cập nhật kích thước cho TextField
        notesTextField.setFont(new Font("Arial", Font.PLAIN, 14)); // Cập nhật cỡ chữ cho TextField
        gbc.gridx = 1;
        gbc.gridy = 5;
        northPanel.add(notesTextField, gbc);

        // Thêm combobox để chọn bàn
        JLabel lblSlectTable = new JLabel("\u25A4 Chọn bàn:");
        lblSlectTable.setFont(labelFont); // Cập nhật font cho JLabel
        gbc.gridx = 0;
        gbc.gridy = 6;
        northPanel.add(lblSlectTable, gbc);

        chonBanComboBox = new JComboBox<>(new String[]{
                "Tất cả",
                "Bàn 2",
                "Bàn 4",
                "Bàn 6",
                "Bàn 8",
                "Bàn 10"
        });
        chonBanComboBox.setPreferredSize(new Dimension(200, 30)); // Cập nhật kích thước cho ComboBox
        chonBanComboBox.setFont(new Font("Arial", Font.PLAIN, 14)); // Cập nhật cỡ chữ cho ComboBox
        gbc.gridx = 1;
        gbc.gridy = 6;
        northPanel.add(chonBanComboBox, gbc);

        // TextFile khi chọn bàn đặt
        tableTextField = new JTextField(20);
        tableTextField.setEditable(false);
        tableTextField.setPreferredSize(new Dimension(200, 30)); // Cập nhật kích thước cho TextField
        tableTextField.setFont(new Font("Arial", Font.PLAIN, 14)); // Cập nhật cỡ chữ cho TextField
        gbc.gridx = 1;
        gbc.gridy = 7;
        northPanel.add(tableTextField, gbc);


        // Thêm northPanel vào phần North của panel_CreateBill
        panel_CreateBill.add(northPanel, BorderLayout.NORTH);

        // Phần center
        JScrollPane centerPanelScroll = new JScrollPane();
        centerPanelScroll.setBackground(AppColor.trang);
        panel_CreateBill.add(centerPanelScroll, BorderLayout.CENTER);
        centerPanelScroll.setWheelScrollingEnabled(true);
        centerPanelScroll.getVerticalScrollBar().setUnitIncrement(16); // Tăng tốc cuộn dọc

        // Thêm panel chứa các button vào JScrollPane
        buttonPanel = null;
        try {
            // Xác định số bàn cần hiển thị dựa trên lựa chọn của combobox
            buttonPanel = list_BanPanel("Tất cả"); // Truyền trực tiếp selectedOption vào hàm list_BanPanel
            centerPanelScroll.setViewportView(buttonPanel);
        } catch (SQLException e1) {
            throw new RuntimeException(e1);
        }

        // Xử lý hành động cho combobox chọn bàn
        chonBanComboBox.addActionListener(e -> {
            String selectedOption = (String) chonBanComboBox.getSelectedItem();
            if (selectedOption != null && selectedOption.length() > 4 && selectedOption != "Tất cả") {
                selectedOption = selectedOption.substring(4); // Cắt 4 ký tự đầu
            } else {
                selectedOption = selectedOption != null ? selectedOption : "";
            }

            try {
                // Xác định số bàn cần hiển thị dựa trên lựa chọn của combobox
                buttonPanel = list_BanPanel(selectedOption); // Truyền trực tiếp selectedOption vào hàm list_BanPanel
                centerPanelScroll.setViewportView(buttonPanel);
            } catch (SQLException e1) {
                throw new RuntimeException(e1);
            }
        });

        // Phần south
        JPanel southPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        southPanel.setBackground(AppColor.trang);
        southPanel.setBorder(new EmptyBorder(15, 0, 15, 0));
        panel_CreateBill.add(southPanel, BorderLayout.SOUTH);
        btn_DatBan = new JButton();
        btn_DatBan = createStyledButton("Đặt bàn");

        btn_DatBan.addActionListener(e -> {
            addRowToTable();
            resetFields();
            resizeColumns();
            if (selectedButton[0] != null) {
                // Đặt lại màu của nút đã chọn về màu gốc
                selectedButton[0].setBackground(AppColor.xanh); // Màu gốc
                selectedButton[0] = null; // Xóa nút đã chọn

                // Vô hiệu hóa nút Hủy khi không còn bàn được chọn
                btn_HuyChonBan.setEnabled(false); // Vô hiệu hóa nút hủy
                tableTextField.setText("");
            }
        });
        southPanel.add(btn_DatBan);

        // Nút reset
        resetButton = new JButton();
        resetButton = createStyledButtonAc("\u21BA", e -> resetFields());
        resetButton.setFont(new Font("Arial Unicode MS", Font.BOLD, 26));
        resetButton.setBackground(AppColor.trang);
        resetButton.setForeground(Color.BLUE);
        resetButton.addActionListener(e -> {
            // Kiểm tra nếu có nút đã chọn
            if (selectedButton[0] != null) {
                // Đặt lại màu của nút đã chọn về màu gốc
                selectedButton[0].setBackground(AppColor.xanh); // Màu gốc
                selectedButton[0] = null; // Xóa nút đã chọn

                // Vô hiệu hóa nút Hủy khi không còn bàn được chọn
                btn_HuyChonBan.setEnabled(false); // Vô hiệu hóa nút hủy
                tableTextField.setText("");
            }
        });
        southPanel.add(resetButton);

        btn_HuyChonBan = new JButton("Hủy chọn");
        btn_HuyChonBan.setBackground(AppColor.red);
        btn_HuyChonBan.setForeground(AppColor.trang);
        btn_HuyChonBan.setFont(new Font("Segoe UI", Font.BOLD, 18));
        btn_HuyChonBan.setFocusPainted(false);
        btn_HuyChonBan.setBorderPainted(false);
        btn_HuyChonBan.setEnabled(false);
        southPanel.add(btn_HuyChonBan);
        btn_HuyChonBan.addActionListener(e -> {
            // Kiểm tra nếu có nút đã chọn
            if (selectedButton[0] != null) {
                // Đặt lại màu của nút đã chọn về màu gốc
                selectedButton[0].setBackground(AppColor.xanh); // Màu gốc
                selectedButton[0] = null; // Xóa nút đã chọn

                // Vô hiệu hóa nút Hủy khi không còn bàn được chọn
                btn_HuyChonBan.setEnabled(false); // Vô hiệu hóa nút hủy
                tableTextField.setText("");
            } else {
                // Nếu không có bàn nào được chọn, thông báo cho người dùng
                JOptionPane.showMessageDialog(null, "Vui lòng chọn một bàn trước khi hủy chọn!");
            }
        });
        return panel_CreateBill;
    }


    public JPanel list_BanPanel(String selectedBanType) throws SQLException {
        // Lấy danh sách bàn từ cơ sở dữ liệu và lọc theo loại bàn
        ArrayList<Ban> listBans = new Ban_Dao().getAllBan_Sort();
        ArrayList<Ban> filteredBans = new ArrayList<>();

        for (Ban ban : listBans) {
            if (selectedBanType.equals("Tất cả") || Integer.toString(ban.getSoGhe()).equals(selectedBanType)) {
                filteredBans.add(ban);
            }
        }

        // Tạo panel chính với GridBagLayout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10); // Khoảng cách giữa các nút
        gbc.fill = GridBagConstraints.NONE; // Kích thước nút không giãn
        gbc.anchor = GridBagConstraints.FIRST_LINE_START; // Căn về góc trái trên

        int columns = 3; // Số cột cố định

        // Thêm các nút vào panel
        for (int i = 0; i < filteredBans.size(); i++) {
            Ban ban = filteredBans.get(i);

            JButton tableButton = new JButton(ban.getMaBan());
            tableButton.setPreferredSize(new Dimension(100, 100)); // Kích thước cố định cho nút bàn
            tableButton.setFont(new Font("Arial", Font.PLAIN, 16));
            tableButton.setBackground(AppColor.xanh); // Màu xanh nhạt
            tableButton.setForeground(Color.BLACK);
            tableButton.setFocusPainted(false);
            tableButton.setBorderPainted(true);

            // Thêm sự kiện click cho mỗi nút
            tableButton.addActionListener(e -> {
                // Nếu có nút đã chọn trước đó, đặt lại màu gốc
                if (selectedButton[0] != null) {
                    selectedButton[0].setBackground(AppColor.xanh); // Đặt lại màu gốc
                }

                // Đổi màu cho nút hiện tại
                tableButton.setBackground(AppColor.red); // Đổi màu nút được chọn
                btn_HuyChonBan.setEnabled(true); // Kích hoạt nút hủy chọn
                selectedButton[0] = tableButton; // Lưu nút đã chọn

                // Cập nhật mã bàn vào tableTextField
                tableTextField.setText(tableButton.getText());
            });


            // Vị trí của nút trên lưới
            gbc.gridx = i % columns; // Cột
            gbc.gridy = i / columns; // Hàng
            mainPanel.add(tableButton, gbc);
        }

        // Thêm các ô trống nếu cần thiết để giữ bố cục cố định
        int totalCells = ((filteredBans.size() + columns - 1) / columns) * columns;
        for (int i = filteredBans.size(); i < totalCells; i++) {
            gbc.gridx = i % columns;
            gbc.gridy = i / columns;
            mainPanel.add(Box.createRigidArea(new Dimension(100, 100)), gbc); // Ô trống giữ chỗ
        }

        // Tạo panel bọc với FlowLayout để căn chỉnh mainPanel về góc trái trên
        JPanel wrapperPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 0, 0));
        wrapperPanel.add(mainPanel);

        return wrapperPanel;
    }

    private String[] generateHourOptions(boolean isToday) {
        int startHour;
        int endHour = 22; // Giờ kết thúc là 10 giờ tối

        if (isToday) {
            // Nếu là ngày hôm nay, bắt đầu từ giờ hiện tại
            startHour = LocalTime.now().getHour();
            // Thêm 1 giờ vào giờ hiện tại
            startHour = (startHour + 1) % 24;
        } else {
            // Nếu là ngày khác, bắt đầu từ 6 giờ sáng
            startHour = 6;
        }

        // Đảm bảo giờ bắt đầu không vượt quá giờ kết thúc
        if (startHour > endHour) {
            startHour = endHour;
        }

        // Tạo danh sách giờ từ startHour đến endHour
        String[] hours = new String[endHour - startHour + 1];
        for (int i = startHour, idx = 0; i <= endHour; i++, idx++) {
            hours[idx] = String.format("%02d:00", i); // Định dạng giờ
        }

        return hours;
    }

    private String[] generateDateOptions() {
        String[] dates = new String[8]; // Mảng chứa 8 ngày
        LocalDate today = LocalDate.now(); // Ngày hiện tại

        // Lấy ngày hôm nay và 7 ngày tiếp theo
        for (int i = 0; i < 8; i++) {
            dates[i] = today.plusDays(i).format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        }

        return dates;
    }

    private JButton createStyledButton(String text) {
        JButton button = new JButton(text);
        button.setBackground(AppColor.xanh);
        button.setForeground(AppColor.trang);
        button.setFont(new Font("Segoe UI", Font.BOLD, 18));
        button.setFocusPainted(false);
        button.setBorderPainted(false);

        return button;
    }

    private JButton createStyledButtonAc(String text, ActionListener actionListener) {
        JButton button = new JButton(text);
        button.setBackground(AppColor.xanh);
        button.setForeground(AppColor.trang);
        button.setFont(new Font("Segoe UI", Font.BOLD, 14));
        button.addActionListener(actionListener);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        return button;
    }
    // Hàm làm mới
    private void resetFields() {
        // Đặt lại tên khách hàng
        nameTextField.setText("");

        // Đặt lại số điện thoại
        phoneTextField.setText("");

        // Đặt lại comboBox ngày
        dateComboBox.setModel(new DefaultComboBoxModel<>(generateDateOptions()));

        // Đặt lại comboBox giờ
        timeComboBox.setModel(new DefaultComboBoxModel<>(generateHourOptions(isToday[0])));

        // Đặt lại số người
        soGheTextField.setText("");

        // Đặt lại ghi chú
        notesTextField.setText("");

        // Đặt lại mã bàn
        tableTextField.setText("");
    }

    // Khởi tạo bảng danh sách đon đặt bàn
    private JScrollPane createTable() {
        String[] columnNames = {"Tên khách hàng", "SĐT", "Ngày đặt", "Số người", "Ghi chú",
                "Mã Bàn", "Tình trạng đơn"};
        ds_DatBan_TableModel = new DefaultTableModel(columnNames, 0);
        ds_DatBan_TableList = new JTable(ds_DatBan_TableModel);

        // Cấu hình bảng để các cột không thể thay đổi vị trí hoặc kích thước
        ds_DatBan_TableList.getTableHeader().setReorderingAllowed(false); // Ngừng cho phép di chuyển cột
        ds_DatBan_TableList.getTableHeader().setResizingAllowed(false);   // Ngừng cho phép thay đổi kích thước cột

        // Tùy chỉnh cuộn
        JScrollPane scrollPane = new JScrollPane(ds_DatBan_TableList);
        scrollPane.setWheelScrollingEnabled(true); // Kích hoạt cuộn mượt

        // Tăng tốc độ cuộn
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // Tăng tốc cuộn dọc
        scrollPane.getHorizontalScrollBar().setUnitIncrement(16); // Tăng tốc cuộn ngang (nếu cần)

        // Tự động điều chỉnh chiều rộng cột sau khi thêm dữ liệu
        resizeColumns();

        // Gọi hàm tùy chỉnh bảng (nếu cần thêm các tùy chỉnh)
        customizeTable();

        return scrollPane;
    }

    private void resizeColumns() {
        // Tính chiều rộng tối đa cho mỗi cột dựa trên dữ liệu và tiêu đề
        for (int columnIndex = 0; columnIndex < ds_DatBan_TableList.getColumnCount(); columnIndex++) {
            int maxWidth = 0;

            // Tính chiều rộng của tiêu đề cột
            String header = ds_DatBan_TableList.getColumnName(columnIndex);
            maxWidth = Math.max(maxWidth, header.length() * 10);  // Giả sử 10 là độ rộng mỗi ký tự

            // Duyệt qua tất cả các hàng để tìm chiều rộng tối đa của mỗi cột
            for (int rowIndex = 0; rowIndex < ds_DatBan_TableList.getRowCount(); rowIndex++) {
                Object value = ds_DatBan_TableList.getValueAt(rowIndex, columnIndex);
                int cellWidth = value != null ? value.toString().length() * 10 : 0; // Tính chiều dài chuỗi và nhân với độ rộng mỗi ký tự
                maxWidth = Math.max(maxWidth, cellWidth);
            }

            // Cập nhật chiều rộng cột dựa trên chiều rộng tối đa (cả tiêu đề và dữ liệu)
            ds_DatBan_TableList.getColumnModel().getColumn(columnIndex).setPreferredWidth(maxWidth);
        }
        ds_DatBan_TableList.revalidate();
        ds_DatBan_TableList.repaint();
    }




    private void addRowToTable() {
        ds_KhachHang_dao = new KhachHang_Dao();
        ds_DonDat_dao = new DonDatBan_Dao();
        try {
            String maDonDat = "MDD" + taoMaTuDong();
            if (maDonDat == null || maDonDat.isEmpty()) {
                JOptionPane.showMessageDialog(null, "Mã đơn đặt không hợp lệ!", "Lỗi",
                        JOptionPane.ERROR_MESSAGE);
                return;
            }

            // Retrieve values from input fields
            String tenKhachHang = nameTextField.getText();
            String soDienThoai = phoneTextField.getText();
            String ngayDat = dateComboBox.getSelectedItem().toString();
            String gioDat = timeComboBox.getSelectedItem().toString();
            String ngayGioDat = ngayDat + " " + gioDat;
            int soNguoi = Integer.parseInt(soGheTextField.getText());
            String ghiChu = notesTextField.getText();
            String maBan = tableTextField.getText();

            // Set default status to "Chờ xử lý"
            TrangThaiDonDat tinhTrangDon = TrangThaiDonDat.ChoXuLy;

            // Check if customer exists in the database
            KhachHang khachHang = ds_KhachHang_dao.getKhachHangBySoDienThoai(soDienThoai);
            boolean isNewCustomer = false;

            if (khachHang == null) {
                // Customer does not exist, create a new one
                String maKhachHang = "KH" + taoMaTuDong();
                khachHang = new KhachHang(maKhachHang, tenKhachHang, soDienThoai);
                isNewCustomer = true; // Mark as new customer
            }

            // Create a new DonDatBan object
            LocalDateTime parsedNgayGioDat = LocalDateTime.parse(ngayGioDat, DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm"));
            DonDatBan donDatBan = new DonDatBan(
                    maDonDat,
                    khachHang,
                    soDienThoai,
                    parsedNgayGioDat,
                    soNguoi,
                    ghiChu,
                    new Ban(maBan),
                    tinhTrangDon
            );

            // Add DonDatBan to the database
            boolean addedOrder = ds_DonDat_dao.addDonDatBan(donDatBan);
            if (!addedOrder) {
                JOptionPane.showMessageDialog(null, "Thêm đơn đặt bàn thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                return;
            }

            // If the customer is new and the order was added successfully, add the customer
            if (isNewCustomer) {
                boolean addedCustomer = ds_KhachHang_dao.addKhachHang(khachHang);
                if (!addedCustomer) {
                    JOptionPane.showMessageDialog(null, "Thêm khách hàng thất bại!", "Lỗi", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            // Add row to the table on the UI
            ds_DatBan_TableModel.addRow(new Object[]{
                    tenKhachHang,
                    soDienThoai,
                    ngayGioDat,
                    soNguoi,
                    ghiChu,
                    maBan,
                    tinhTrangDon
            });

            JOptionPane.showMessageDialog(null, "Thêm đơn đặt bàn thành công!", "Thông báo", JOptionPane.INFORMATION_MESSAGE);

        } catch (Exception e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(null, "Có lỗi xảy ra: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }

    }

    private void customizeTable() {
        ds_DatBan_TableList.setFont(new Font("Segoe UI", Font.PLAIN, 18));
        ds_DatBan_TableList.setRowHeight(30);
        ds_DatBan_TableList.setBackground(AppColor.trang);
        ds_DatBan_TableList.setForeground(AppColor.den);
        ds_DatBan_TableList.setGridColor(AppColor.xanh);
        //MÀU CỦA BẢNG KHI CHƯA CÓ CÁC DÒNG
        ds_DatBan_TableList.setFillsViewportHeight(true);
        //set editable = false
        ds_DatBan_TableList.setDefaultEditor(Object.class, null);
        ds_DatBan_TableList.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if(isSelected){
                    c.setBackground(AppColor.xanhNhat);
                }
                else if (row % 2 == 0) {
                    c.setBackground(AppColor.xam);
                } else {
                    c.setBackground(AppColor.trang);
                }
                return c;
            }
        });
        //đổi màu dòng khi có event click

        JTableHeader header =  ds_DatBan_TableList.getTableHeader();
        header.setBackground(AppColor.xanh);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 14));
    }

    public String taoMaTuDong() {
        // Lấy thời gian hiện tại
        LocalDateTime now = LocalDateTime.now();

        // Định dạng thời gian thành chuỗi
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        String formattedDateTime = now.format(formatter);

        // Tạo mã tự động
        String maTuDong = formattedDateTime;

        return maTuDong;
    }

}

