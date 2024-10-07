package Gui;

import javax.swing.*;
import javax.swing.border.AbstractBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.sql.SQLException;
import java.util.ArrayList;

public class QuanLyThucDon extends JFrame {

    private DefaultTableModel tableModel;
    private JTable productTable;
    private JButton btnAdd;
    private JButton btnDelete;
    private JButton btnSearch;
    private JTextField txtProductCode;
    private JTextField txtProductName;
    private JTextField txtUnitPrice;
    private JTextField txtDescription;
    private JComboBox<String> cmbProductType;
    private JRadioButton radCompleted;
    private JRadioButton radInProgress;

    public QuanLyThucDon() {
        super();

        this.setLayout(new BorderLayout());
        JPanel inputPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;

        gbc.gridx = 0;
        gbc.gridy = 0;
        inputPanel.add(new JLabel("Mã sản phẩm (chỉ nhập mã để tìm):"), gbc);
        txtProductCode = new JTextField(15);
        txtProductCode.setBorder(BorderFactory.createCompoundBorder(
                new RoundedCornerBorder(),
                new EmptyBorder(10, 10, 10, 10)));
        gbc.gridx = 1;
        inputPanel.add(txtProductCode, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        inputPanel.add(new JLabel("Tên sản phẩm:"), gbc);
        txtProductName = new JTextField(15);
        txtProductName.setBorder(BorderFactory.createCompoundBorder(
                new RoundedCornerBorder(),
                new EmptyBorder(10, 10, 10, 10)));
        gbc.gridx = 1;
        inputPanel.add(txtProductName, gbc);

        String[] productTypes = {"Cà phê", "Đóng chai", "Nước Ép", "Đá chanh", "Trà", "Đá xay"};
        cmbProductType = new JComboBox<>(productTypes);
        gbc.gridx = 0;
        gbc.gridy = 2;
        inputPanel.add(new JLabel("Loại sản phẩm:"), gbc);
        gbc.gridx = 1;
        inputPanel.add(cmbProductType, gbc);

        gbc.gridx = 0;
        gbc.gridy = 3;
        inputPanel.add(new JLabel("Trạng thái:"), gbc);
        radCompleted = new JRadioButton("Đang bán");
        radInProgress = new JRadioButton("Ngưng bán");
        ButtonGroup statusGroup = new ButtonGroup();
        statusGroup.add(radCompleted);
        statusGroup.add(radInProgress);
        JPanel statusPanel = new JPanel();
        statusPanel.add(radCompleted);
        statusPanel.add(radInProgress);
        gbc.gridx = 1;
        inputPanel.add(statusPanel, gbc);

        gbc.gridx = 0;
        gbc.gridy = 4;
        inputPanel.add(new JLabel("Đơn giá:"), gbc);
        txtUnitPrice = new JTextField(15);
        txtUnitPrice.setBorder(BorderFactory.createCompoundBorder(
                new RoundedCornerBorder(),
                new EmptyBorder(10, 10, 10, 10)));
        gbc.gridx = 1;
        inputPanel.add(txtUnitPrice, gbc);

        gbc.gridx = 0;
        gbc.gridy = 5;
        inputPanel.add(new JLabel("Mô tả:"), gbc);
        txtDescription = new JTextField(15);
        txtDescription.setBorder(BorderFactory.createCompoundBorder(
                new RoundedCornerBorder(),
                new EmptyBorder(10, 10, 10, 10)));
        gbc.gridx = 1;
        inputPanel.add(txtDescription, gbc);

        gbc.gridx = 0;
        gbc.gridy = 6;
        gbc.gridwidth = 2;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        btnAdd = new JButton("Thêm");
        btnDelete = new JButton("Xóa");
        btnSearch = new JButton("Tìm");
        buttonPanel.add(btnAdd);
        buttonPanel.add(btnDelete);
        buttonPanel.add(btnSearch);
        inputPanel.add(buttonPanel, gbc);

        tableModel = new DefaultTableModel();
        tableModel.addColumn("Mã Món");
        tableModel.addColumn("Tên Món");
        tableModel.addColumn("Trạng thái");
        tableModel.addColumn("Đơn giá");
        tableModel.addColumn("Mô tả");
        tableModel.addColumn("Loại ");

        productTable = new JTable(tableModel);
        productTable.setBackground(new Color(255, 220, 255));

        JScrollPane tableScrollPane = new JScrollPane(productTable);

        TitledBorder border = BorderFactory.createTitledBorder("Menu của quán");
        border.setTitleColor(Color.BLUE);
        border.setTitleFont(new Font("Arial", Font.BOLD, 30));
        border.setTitleJustification(TitledBorder.CENTER);
        tableScrollPane.setBorder(border);

        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, inputPanel, tableScrollPane);
        splitPane.setResizeWeight(0.3);
        add(splitPane, BorderLayout.CENTER);
    }

    class RoundedCornerBorder extends AbstractBorder {
        @Override
        public void paintBorder(Component c, Graphics g, int x, int y, int width, int height) {
            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setColor(c.getBackground().darker());
            g2d.drawRoundRect(x, y, width - 1, height - 1, 10, 10);
            g2d.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                QuanLyThucDon gui = new QuanLyThucDon();
                gui.setTitle("Quản lý thực đơn");
                gui.setSize(1000, 800);
                gui.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                gui.setVisible(true);
                gui.setLocationRelativeTo(null);
            }
        });
    }
}

