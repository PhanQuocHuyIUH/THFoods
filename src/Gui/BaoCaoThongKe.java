package Gui;

import DB.Database;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.DefaultCategoryDataset;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class BaoCaoThongKe extends JPanel
{
    // Các thành phần giao diện
    private JTable table;
    private JPanel chartPanelContainer;
    private DefaultCategoryDataset dataset;

    public BaoCaoThongKe() {
        // link db
        try{
            Database.getInstance().connect();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

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
        btnLoadChart1.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadSampleData1(model);
            }
        });

        btnLoadChart2.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadSampleData2(model);
            }
        });

        btnLoadChart3.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                loadSampleData3(model);
            }
        });
    }


    // Hàm để tải dữ liệu cho Biểu đồ 1
    private void loadSampleData1(DefaultTableModel model) {
        model.setRowCount(0); // Xóa dữ liệu cũ
        dataset.clear(); // Xóa dữ liệu biểu đồ

        Object[][] sampleData = {
                {"2024-09-01", 351.25},
                {"2024-09-02", 300.00},
                {"2024-09-03", 250.50},
                {"2024-09-04", 400.75},
                {"2024-09-05", 500.00},
        };

        for (Object[] row : sampleData) {
            model.addRow(row);
            dataset.addValue((Double) row[1], "Revenue", (String) row[0]);
        }

        updateChart();
    }

    // Hàm để tải dữ liệu cho Biểu đồ 2
    private void loadSampleData2(DefaultTableModel model) {
        model.setRowCount(0);
        dataset.clear();

        Object[][] sampleData = {
                {"2024-09-06", 450.00},
                {"2024-09-07", 300.50},
                {"2024-09-08", 200.75},
                {"2024-09-09", 600.00},
                {"2024-09-10", 700.25},
        };

        for (Object[] row : sampleData) {
            model.addRow(row);
            dataset.addValue((Double) row[1], "Revenue", (String) row[0]);
        }

        updateChart();
    }

    // Hàm để tải dữ liệu cho Biểu đồ 3
    private void loadSampleData3(DefaultTableModel model) {
        model.setRowCount(0);
        dataset.clear();

        Object[][] sampleData = {
                {"2024-09-11", 500.00},
                {"2024-09-12", 450.50},
                {"2024-09-13", 300.00},
                {"2024-09-14", 700.50},
                {"2024-09-15", 800.25},
                {"2024-09-16", 700.50},
                {"2024-09-17", 800.25},
        };

        for (Object[] row : sampleData) {
            model.addRow(row);
            dataset.addValue((Double) row[1], "Doanh thu (Triệu VND)", (String) row[0]);
        }

        updateChart();
    }

    // Hàm để cập nhật biểu đồ
    private void updateChart() {
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanelContainer.removeAll(); // Xóa biểu đồ cũ
        chartPanelContainer.add(chartPanel, BorderLayout.CENTER); // Thêm biểu đồ mới
        chartPanelContainer.revalidate(); // Cập nhật layout
        chartPanelContainer.repaint(); // Vẽ lại
    }

    // Hàm tạo biểu đồ
    private JFreeChart createChart(DefaultCategoryDataset dataset) {
        return org.jfree.chart.ChartFactory.createBarChart(
                "Báo cáo doanh thu", // Title
                "Date", // X-Axis Label
                "Doanh thu", // Y-Axis Label
                dataset, // Dataset
                PlotOrientation.VERTICAL,
                true, // Show Legend
                true, // Use Tooltips
                false // Show URLs
        );
    }
}
