package Gui;

import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class BaoCaoThongKe extends JPanel
{
    // Các thành phần giao diện
    private JTable table;
    private JPanel chartPanelContainer;
    private DefaultCategoryDataset dataset;

    public BaoCaoThongKe() {
        setLayout(new BorderLayout());

        // Khởi tạo dataset cho biểu đồ
        dataset = new DefaultCategoryDataset();

        // Bảng dữ liệu
        String[] columnNames = {"Date", "Doanh thu"};
        DefaultTableModel model = new DefaultTableModel(columnNames, 0);
        table = new JTable(model);
        JScrollPane tableScrollPane = new JScrollPane(table);

        // Nút để vẽ biểu đồ
        JButton btnLoadChart1 = new JButton("Load Chart 1");
        JButton btnLoadChart2 = new JButton("Load Chart 2");
        JButton btnLoadChart3 = new JButton("Doanh thu theo tuần");

        // Panel chứa bảng và nút
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BorderLayout());
        leftPanel.add(tableScrollPane, BorderLayout.CENTER);

        // Panel chứa nút
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new GridLayout(0, 1)); // Sắp xếp nút theo cột
        buttonPanel.add(btnLoadChart1);
        buttonPanel.add(btnLoadChart2);
        buttonPanel.add(btnLoadChart3);

        leftPanel.add(buttonPanel, BorderLayout.SOUTH);

        // Panel chứa biểu đồ
        chartPanelContainer = new JPanel();
        chartPanelContainer.setLayout(new BorderLayout());

        // Thêm các panel vào JFrame
        add(leftPanel, BorderLayout.WEST);
        add(chartPanelContainer, BorderLayout.CENTER);

        // Thêm hành động cho nút
//        btnLoadChart1.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                loadSampleData1(model);
//            }
//        });
//
//        btnLoadChart2.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                loadSampleData2(model);
//            }
//        });
//
//        btnLoadChart3.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                loadSampleData3(model);
//            }
//        });
    }
}
