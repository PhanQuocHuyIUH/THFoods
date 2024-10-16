package Gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;

public class QuanLyBan extends JPanel{
    private JTextField txtMaBan, txtSoGhe;
    private JButton btnThem, btnXoa, btnSua;
    private JTable tableList;
    private DefaultTableModel tableModel;

    public QuanLyBan() {
        setLayout(new BorderLayout(20, 20)); // Tăng khoảng cách giữa các khu vực

        // Header
        JLabel lblHeader = new JLabel("QUẢN LÝ BÀN", JLabel.CENTER);
        lblHeader.setFont(new Font("Serif", Font.BOLD, 36));
        lblHeader.setForeground(new Color(52, 73, 94)); // Thay đổi màu header cho nhẹ nhàng

        // Tạo JPanel cho khu vực header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.add(lblHeader, BorderLayout.CENTER);
        headerPanel.setBorder(BorderFactory.createTitledBorder( // Thêm viền cho header
                BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
                "Tiêu đề", // Tiêu đề cho viền (có thể bỏ trống nếu không cần)
                0,
                0,
                new Font("Serif", Font.BOLD, 20),
                new Color(52, 152, 219)
        ));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Tạo padding cho header

        add(headerPanel, BorderLayout.NORTH); // Thêm headerPanel vào khu vực NORTH

        // Form to input table info
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
                "Thông tin bàn",
                0,
                0,
                new Font("Serif", Font.BOLD, 20),
                new Color(52, 152, 219)
        ));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15); // Tăng khoảng cách giữa các thành phần
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Ma Ban
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblMaBan = new JLabel("Mã bàn:");
        lblMaBan.setFont(new Font("Serif", Font.PLAIN, 18));
        formPanel.add(lblMaBan, gbc);

        gbc.gridx = 1;
        txtMaBan = new JTextField(20);
        txtMaBan.setFont(new Font("Serif", Font.PLAIN, 18));
        formPanel.add(txtMaBan, gbc);

        // So Ghe
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lblSoGhe = new JLabel("Số ghế:");
        lblSoGhe.setFont(new Font("Serif", Font.PLAIN, 18));
        formPanel.add(lblSoGhe, gbc);

        gbc.gridx = 1;
        txtSoGhe = new JTextField(20);
        txtSoGhe.setFont(new Font("Serif", Font.PLAIN, 18));
        formPanel.add(txtSoGhe, gbc);

        // Buttons for actions inside formPanel
        JPanel buttonPanel = new JPanel(new GridLayout(1, 3, 15, 0)); // Tăng khoảng cách giữa các nút

        btnThem = new JButton("Thêm");
        styleButton(btnThem, new Color(76, 175, 80), Color.WHITE); // Sử dụng hàm styleButton để định dạng
        buttonPanel.add(btnThem);

        btnXoa = new JButton("Xóa");
        styleButton(btnXoa, new Color(244, 67, 54), Color.WHITE);
        buttonPanel.add(btnXoa);

        btnSua = new JButton("Sửa");
        styleButton(btnSua, new Color(33, 150, 243), Color.WHITE);
        buttonPanel.add(btnSua);

        // Adding button panel to formPanel, move to the last row
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // Span across two columns
        formPanel.add(buttonPanel, gbc);

        // Add form panel to frame
        add(formPanel, BorderLayout.EAST);

        // JTable to display tables with column headers
        String[] columnNames = {"Mã bàn", "Số ghế"};
        tableModel = new DefaultTableModel(columnNames, 0); // Bảng trống với tiêu đề cột
        tableList = new JTable(tableModel);
        tableList.setFont(new Font("Serif", Font.PLAIN, 18)); // Đổi font cho danh sách bàn
        tableList.getTableHeader().setFont(new Font("Serif", Font.BOLD, 20)); // Font cho tiêu đề cột
        tableList.getTableHeader().setBackground(new Color(52, 152, 219)); // Màu nền cho tiêu đề cột
        tableList.getTableHeader().setForeground(Color.WHITE); // Màu chữ cho tiêu đề cột
        JScrollPane tableScrollPane = new JScrollPane(tableList);

        // Add the table to the right side of the frame
        add(tableScrollPane, BorderLayout.CENTER);

        // Set padding and spacing
        setBorderAndSpacing();
    }

    // Hàm định dạng cho button
    private void styleButton(JButton button, Color background, Color foreground) {
        button.setBackground(background);
        button.setForeground(foreground);
        button.setFont(new Font("Serif", Font.BOLD, 18));
        button.setFocusPainted(false); // Bỏ đường viền khi focus
        button.setBorder(BorderFactory.createLineBorder(background.darker(), 2)); // Tạo đường viền bo
        button.setPreferredSize(new Dimension(120, 50)); // Tăng kích thước nút
    }

    private void setBorderAndSpacing() {
        // Add padding around the content
        setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
    }

    // Hàm để thêm dữ liệu vào bảng
    public void addTable(String maBan, String soGhe) {
        tableModel.addRow(new Object[]{maBan, soGhe});
    }

    // Hàm để xóa dữ liệu khỏi bảng
    public void removeSelectedTable() {
        int selectedRow = tableList.getSelectedRow();
        if (selectedRow != -1) {
            tableModel.removeRow(selectedRow);
        }
    }

    // Hàm để sửa dữ liệu trên bảng
    public void editSelectedTable(String maBan, String soGhe) {
        int selectedRow = tableList.getSelectedRow();
        if (selectedRow != -1) {
            tableModel.setValueAt(maBan, selectedRow, 0);
            tableModel.setValueAt(soGhe, selectedRow, 1);
        }
    }
}
