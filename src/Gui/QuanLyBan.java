package Gui;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;

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
    private JButton lastClickedButton;

    public QuanLyBan() {
        setLayout(new BorderLayout(20, 20)); // Tăng khoảng cách giữa các khu vực
        setBackground(AppColor.trang);

        // Header
        JLabel lblHeader = new JLabel("QUẢN LÝ BÀN", JLabel.CENTER);
        lblHeader.setFont(new Font("Consolas", Font.BOLD, 30));
        lblHeader.setForeground(AppColor.den); 
        lblHeader.setPreferredSize(new Dimension(0, 50));

        // Tạo JPanel cho khu vực header
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(AppColor.trang);
        headerPanel.add(lblHeader, BorderLayout.CENTER);
        headerPanel.setBorder(BorderFactory.createTitledBorder( // Thêm viền cho header
                BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
                "Tiêu đề", // Tiêu đề cho viền (có thể bỏ trống nếu không cần)
                0,
                0,
                new Font("Consolas", Font.BOLD, 20),
                AppColor.xanh));
        headerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20)); // Tạo padding cho header

        add(headerPanel, BorderLayout.NORTH); // Thêm headerPanel vào khu vực NORTH

        // Form to input table info
        JPanel formPanel = new JPanel();
        formPanel.setBackground(AppColor.trang);
        formPanel.setLayout(new GridBagLayout());
        formPanel.setBorder(BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(new Color(52, 152, 219), 2),
                "Thông tin bàn",
                0,
                0,
                new Font("Consolas", Font.BOLD, 20),
                AppColor.xanh));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(15, 15, 15, 15); // Tăng khoảng cách giữa các thành phần
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Ma Ban
        gbc.gridx = 0;
        gbc.gridy = 0;
        JLabel lblMaBan = new JLabel("Mã bàn:");
        lblMaBan.setFont(new Font("Chalkduster", Font.BOLD, 14));
        formPanel.add(lblMaBan, gbc);

        gbc.gridx = 1;
        txtMaBan = new JTextField(20);
        txtMaBan.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtMaBan.setBackground(AppColor.xam);
        formPanel.add(txtMaBan, gbc);

        // So Ghe
        gbc.gridx = 0;
        gbc.gridy = 1;
        JLabel lblSoGhe = new JLabel("Số ghế:");
        lblSoGhe.setFont(new Font("Chalkduster", Font.BOLD, 14));
        formPanel.add(lblSoGhe, gbc);

        gbc.gridx = 1;
        txtSoGhe = new JTextField(20);
        txtSoGhe.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtSoGhe.setBackground(AppColor.xam);
        formPanel.add(txtSoGhe, gbc);

        // Buttons for actions inside formPanel
        JPanel buttonPanel = new JPanel(); // Tăng khoảng cách giữa các nút
        buttonPanel.setBackground(AppColor.trang);

        btnThem = createStyledButton("Thêm", e -> themBan());
        btnThem.setIcon(new ImageIcon("src/img/add_icon.png"));
        buttonPanel.add(btnThem);


        btnXoa = createStyledButton("Xóa", e -> xoaBan());
        btnXoa.setIcon(new ImageIcon("src/img/delete_icon.png"));
        buttonPanel.add(btnXoa);

        btnSua = createStyledButton("Sửa", e -> suaBan());
        btnSua.setIcon(new ImageIcon("src/img/fix_icon.png"));
        buttonPanel.add(btnSua);

        // Adding button panel to formPanel, move to the last row
        gbc.gridx = 0;
        gbc.gridy = 2;
        gbc.gridwidth = 2; // Span across two columns
        formPanel.add(buttonPanel, gbc);

        // Add form panel to frame
        add(formPanel, BorderLayout.EAST);

        // JTable to display tables with column headers
        String[] columnNames = { "Mã bàn", "Số ghế", "Trạng thái" };
        tableModel = new DefaultTableModel(columnNames, 0); // Bảng trống với tiêu đề cột
        tableList = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(tableList);
        customizeTable();

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

    private void customizeTable() {
        tableList.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        tableList.setRowHeight(30);
        tableList.setBackground(AppColor.trang);
        tableList.setForeground(AppColor.den);
        tableList.setGridColor(AppColor.xanh);
        tableList.setFillsViewportHeight(true);
        tableList.setDefaultEditor(Object.class, null);

        // Tạo custom cell renderer với lề trái 5 pixel
        DefaultTableCellRenderer paddingRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
                    boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                if (c instanceof JLabel) {
                    ((JLabel) c).setBorder(new EmptyBorder(0, 5, 0, 0)); // Cách lề trái 5 pixel
                }
                if (isSelected) {
                    c.setBackground(AppColor.xanhNhat);
                } else if (row % 2 == 0) {
                    c.setBackground(AppColor.xam);
                } else {
                    c.setBackground(AppColor.trang);
                }
                return c;
            }
        };

        // Áp dụng renderer cho tất cả các cột
        for (int i = 0; i < tableList.getColumnCount(); i++) {
            tableList.getColumnModel().getColumn(i).setCellRenderer(paddingRenderer);
        }

        JTableHeader header = tableList.getTableHeader();
        header.setBackground(AppColor.xanh);
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 17));
    }


private JButton createStyledButton(String text, ActionListener action) { // Thêm kiểu trả về JButton
        JButton button = new JButton(text);
        button.addActionListener(action);
        // Thêm bất kỳ kiểu dáng bổ sung nào nếu cần
        button.setFont(new Font("Arial Unicode MS", Font.BOLD, 17));
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        applyButtonColorChange(button);
        return button;
    }

    private void applyButtonColorChange(JButton button) {
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
            JOptionPane.showMessageDialog(this, e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE); // Hiển thị thông báo lỗi
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

            String trangThai = "";

            for (Ban ban : danhSachBan) {
                if(ban.getTrangThai() == TrangThaiBan.Trong) {
                    trangThai = "Trống";
                }
//                else if(ban.getTrangThai() == TrangThaiBan.DaDat) {
//                    trangThai = "Đã đặt";
//                }
                else if(ban.getTrangThai() == TrangThaiBan.DangDung) {
                    trangThai = "Đang dùng";
                }
                tableModel.addRow(new Object[] { ban.getMaBan(), ban.getSoGhe(), trangThai });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
