package Gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;

import DAO.Ban_Dao;
import Entity.Ban;
import Entity.TrangThaiBan;

import java.awt.*;
import java.sql.SQLException;
import java.util.List;
import java.awt.event.*;
import java.util.Collections;
import java.util.Comparator;

public class QuanLyBan extends JPanel {
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
                new Color(52, 152, 219)));
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
                new Color(52, 152, 219)));
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
        btnThem.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                themBan();
            }
        });
        buttonPanel.add(btnThem);

        btnXoa = new JButton("Xóa");
        styleButton(btnXoa, new Color(244, 67, 54), Color.WHITE);
        btnXoa.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                xoaBan();
            }
        });
        buttonPanel.add(btnXoa);

        btnSua = new JButton("Sửa");
        styleButton(btnSua, new Color(33, 150, 243), Color.WHITE);
        btnSua.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                suaBan();
            }
        });
        buttonPanel.add(btnSua);

        // Adding button panel to formPanel, move to the last row
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // Span across two columns
        formPanel.add(buttonPanel, gbc);

        // Add form panel to frame
        add(formPanel, BorderLayout.EAST);

        // JTable to display tables with column headers
        String[] columnNames = { "Mã bàn", "Số ghế" };
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

        // Thêm vào constructor của QuanLyBan
        tableList.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tableList.getSelectedRow(); // Lấy chỉ số dòng được chọn
                if (row != -1) { // Kiểm tra xem có dòng nào được chọn không
                    String maBan = tableModel.getValueAt(row, 0).toString(); // Lấy mã bàn từ cột 0
                    int soGhe = Integer.parseInt(tableModel.getValueAt(row, 1).toString()); // Lấy số ghế từ cột 1

                    // Cập nhật thông tin vào các JTextField
                    txtMaBan.setText(maBan);
                    txtSoGhe.setText(String.valueOf(soGhe));
                }
            }
        });

        loadToTable();
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

    private void themBan() {
        String maBan = txtMaBan.getText();
        int soGhe = Integer.parseInt(txtSoGhe.getText());

        // Tạo đối tượng Ban với constructor 2 tham số
        Ban ban = new Ban(maBan, soGhe);

        // Kiểm tra mã bàn trong danh sách hiện có
        Ban_Dao dao = new Ban_Dao();
        try {
            List<Ban> danhSachBan = dao.getAllBan(); // Lấy danh sách bàn từ DAO
            for (Ban existingBan : danhSachBan) {
                if (existingBan.getMaBan().equals(maBan)) {
                    // Nếu mã bàn đã tồn tại, hiển thị thông báo
                    JOptionPane.showMessageDialog(this, "Mã bàn đã tồn tại: " + maBan, "Lỗi",
                            JOptionPane.ERROR_MESSAGE);
                    return; // Kết thúc phương thức nếu mã bàn đã tồn tại
                }
            }

            // Nếu mã bàn chưa tồn tại, thực hiện thêm bàn
            dao.themBan(ban); // Gọi phương thức themBan trong Ban_Dao
            loadToTable(); // Tải lại dữ liệu vào bảng
        } catch (SQLException e) {
            // Hiển thị thông báo lỗi cho người dùng
            JOptionPane.showMessageDialog(this, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void xoaBan() {
        String maBan = txtMaBan.getText().trim(); // Lấy mã bàn và loại bỏ khoảng trắng
        if (maBan.isEmpty()) { // Kiểm tra xem mã bàn có được nhập không
            JOptionPane.showMessageDialog(this, "Vui lòng nhập mã bàn để xóa.", "Lỗi", JOptionPane.ERROR_MESSAGE);
            return; // Kết thúc phương thức nếu mã bàn rỗng
        }

        Ban_Dao dao = new Ban_Dao();
        try {
            dao.xoaBan(maBan); // Gọi phương thức xoaBan trong Ban_Dao
            loadToTable(); // Tải lại dữ liệu vào bảng
            // Xóa dữ liệu trong các JTextField
            txtMaBan.setText("");
            txtSoGhe.setText("");
            JOptionPane.showMessageDialog(this, "Xóa bàn thành công.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE); // Hiển thị thông báo
                                                                                                   // lỗi
        }
    }

    private void suaBan() {
        String maBan = txtMaBan.getText();
        int soGhe = Integer.parseInt(txtSoGhe.getText());

        // Tạo đối tượng Ban với constructor 2 tham số
        Ban ban = new Ban(maBan, soGhe);
        Ban_Dao dao = new Ban_Dao();
        try {
            dao.suaBan(ban); // Gọi phương thức suaBan trong Ban_Dao
            loadToTable(); // Tải lại dữ liệu vào bảng
            JOptionPane.showMessageDialog(this, "Sửa bàn thành công.", "Thông báo", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE); // Hiển thị thông báo
                                                                                                   // lỗi
        }
    }

    private void loadToTable() {
        Ban_Dao dao = new Ban_Dao();
        try {
            List<Ban> danhSachBan = dao.getAllBan(); // Lấy danh sách bàn từ DAO

            // Sắp xếp danh sách bàn theo mã bàn với thứ tự số học
            Collections.sort(danhSachBan, new Comparator<Ban>() {
                @Override
                public int compare(Ban b1, Ban b2) {
                    String maBan1 = b1.getMaBan();
                    String maBan2 = b2.getMaBan();

                    // Tách phần chữ và số
                    String prefix1 = maBan1.replaceAll("[0-9]", "");
                    String prefix2 = maBan2.replaceAll("[0-9]", "");

                    if (prefix1.equals(prefix2)) {
                        // So sánh phần số
                        int num1 = Integer.parseInt(maBan1.replaceAll("[^0-9]", ""));
                        int num2 = Integer.parseInt(maBan2.replaceAll("[^0-9]", ""));
                        return Integer.compare(num1, num2);
                    }
                    return prefix1.compareTo(prefix2); // So sánh phần chữ
                }
            });

            tableModel.setRowCount(0);

            for (Ban ban : danhSachBan) {
                tableModel.addRow(new Object[] { ban.getMaBan(), ban.getSoGhe() });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
