package Gui;

import DB.Database;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.CategoryPlot;
import org.jfree.chart.plot.PiePlot;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.chart.renderer.category.BarRenderer;
import org.jfree.chart.renderer.category.LineAndShapeRenderer;
import org.jfree.data.category.DefaultCategoryDataset;
import org.jfree.data.general.DefaultPieDataset;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class BaoCaoThongKe extends JPanel {

    private JPanel chartPanelContainer;
    private DefaultCategoryDataset lineDataset;
    private DefaultCategoryDataset barDataset;
    private DefaultPieDataset pieDataset;
    private JTable table;
    private JComboBox<String> reportTypeComboBox;
    private DefaultTableModel tableModel;

    public BaoCaoThongKe() {
        setLayout(new BorderLayout());

        // Panel chứa biểu đồ
        chartPanelContainer = new JPanel();
        chartPanelContainer.setLayout(new BorderLayout());

        // Thanh chọn loại báo cáo (ComboBox)
        reportTypeComboBox = new JComboBox<>(new String[] {
                "Doanh thu theo loại món ăn (Biểu đồ tròn)",
                "Doanh thu 7 ngày gần nhất (Biểu đồ đường)",
                "Doanh thu theo tháng (Biểu đồ cột)"
        });
        reportTypeComboBox.setSelectedIndex(0);

        // Làm nổi bật ComboBox
        reportTypeComboBox.setFont(new Font("Arial", Font.BOLD, 16)); // Tăng kích thước và làm đậm font chữ
        reportTypeComboBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 2),  // Đường viền ngoài
                new EmptyBorder(10, 10, 10, 10)                   // Tạo khoảng cách xung quanh ComboBox
        ));
        reportTypeComboBox.setMaximumSize(new Dimension(400, 50)); // Tăng kích thước tối đa của ComboBox (độ cao 50 pixel)
        reportTypeComboBox.setPreferredSize(new Dimension(400, 50)); // Tăng kích thước ưu tiên của ComboBox (độ cao 50 pixel)

        // Bảng để hiển thị thông tin chi tiết
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);

        // Panel chứa các thành phần giao diện (ComboBox)
        JPanel topPanel = new JPanel();
        topPanel.setLayout(new FlowLayout(FlowLayout.CENTER));
        topPanel.add(reportTypeComboBox);

        // Chia layout thành hai phần: Biểu đồ (65%) và Bảng (35%)
        JSplitPane splitPane = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT, chartPanelContainer, tableScrollPane);
        splitPane.setDividerLocation(0.65); // 65% cho biểu đồ, 35% cho bảng
        splitPane.setResizeWeight(0.65); // Cố định tỉ lệ
        splitPane.setEnabled(false); // Vô hiệu hóa khả năng thay đổi tỉ lệ bằng chuột

        // Thêm các thành phần vào JFrame
        add(topPanel, BorderLayout.NORTH);
        add(splitPane, BorderLayout.CENTER); // Đặt JSplitPane vào trung tâm

        // Thêm sự kiện cho thanh chọn loại báo cáo
        reportTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedReport = (String) reportTypeComboBox.getSelectedItem();
                if (selectedReport.equals("Doanh thu theo loại món ăn (Biểu đồ tròn)")) {
                    loadPieChart();
                } else if (selectedReport.equals("Doanh thu 7 ngày gần nhất (Biểu đồ đường)")) {
                    loadLineChart();
                } else if (selectedReport.equals("Doanh thu theo tháng (Biểu đồ cột)")) {
                    loadBarChart();
                }
            }
        });

        // Hiển thị mặc định biểu đồ tròn khi khởi chạy
        loadPieChart();
    }

    // Hàm tải dữ liệu và hiển thị biểu đồ tròn
    private void loadPieChart() {
        pieDataset = new DefaultPieDataset();
        pieDataset.setValue("Món chính", 50.0);
        pieDataset.setValue("Món phụ", 20.0);
        pieDataset.setValue("Món tráng miệng", 15.0);
        pieDataset.setValue("Nước uống", 15.0);

        JFreeChart pieChart = ChartFactory.createPieChart(
                "Doanh thu theo loại món ăn (Hôm nay)",
                pieDataset,
                true,
                true,
                false
        );

        // Tùy chỉnh màu sắc biểu đồ tròn
        PiePlot plot = (PiePlot) pieChart.getPlot();
        plot.setSectionPaint("Món chính", new Color(255, 99, 132));
        plot.setSectionPaint("Món phụ", new Color(54, 162, 235));
        plot.setSectionPaint("Món tráng miệng", new Color(255, 206, 86));
        plot.setSectionPaint("Nước uống", new Color(75, 192, 192));

        // Cập nhật bảng thông tin
        updateTable(new String[] {"Loại món ăn", "Doanh thu (VND)"}, new Object[][] {
                {"Món chính", 500000},
                {"Món phụ", 200000},
                {"Món tráng miệng", 150000},
                {"Nước uống", 150000}
        });

        updateChart(pieChart);
    }

    // Hàm tải dữ liệu và hiển thị biểu đồ đường
    private void loadLineChart() {
        lineDataset = new DefaultCategoryDataset();
        lineDataset.addValue(100, "Doanh thu", "2024-10-14");
        lineDataset.addValue(200, "Doanh thu", "2024-10-15");
        lineDataset.addValue(300, "Doanh thu", "2024-10-16");
        lineDataset.addValue(250, "Doanh thu", "2024-10-17");
        lineDataset.addValue(350, "Doanh thu", "2024-10-18");
        lineDataset.addValue(400, "Doanh thu", "2024-10-19");
        lineDataset.addValue(500, "Doanh thu", "2024-10-20");

        JFreeChart lineChart = ChartFactory.createLineChart(
                "Doanh thu 7 ngày gần nhất",
                "Ngày",
                "Doanh thu (VND)",
                lineDataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        // Tùy chỉnh màu sắc biểu đồ đường
        CategoryPlot plot = lineChart.getCategoryPlot();
        LineAndShapeRenderer renderer = new LineAndShapeRenderer();
        renderer.setSeriesPaint(0, Color.BLUE);  // Màu xanh dương cho đường biểu đồ
        plot.setRenderer(renderer);

        // Cập nhật bảng thông tin
        updateTable(new String[] {"Ngày", "Doanh thu (VND)"}, new Object[][] {
                {"2024-10-14", 100000},
                {"2024-10-15", 200000},
                {"2024-10-16", 300000},
                {"2024-10-17", 250000},
                {"2024-10-18", 350000},
                {"2024-10-19", 400000},
                {"2024-10-20", 500000}
        });

        updateChart(lineChart);
    }

    // Hàm tải dữ liệu và hiển thị biểu đồ cột
    private void loadBarChart() {
        barDataset = new DefaultCategoryDataset();
        barDataset.addValue(5000, "Doanh thu", "Tháng 1");
        barDataset.addValue(7000, "Doanh thu", "Tháng 2");
        barDataset.addValue(8000, "Doanh thu", "Tháng 3");
        barDataset.addValue(6000, "Doanh thu", "Tháng 4");
        barDataset.addValue(9000, "Doanh thu", "Tháng 5");

        JFreeChart barChart = ChartFactory.createBarChart(
                "Doanh thu theo tháng",
                "Tháng",
                "Doanh thu (VND)",
                barDataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        // Tùy chỉnh màu sắc biểu đồ cột
        CategoryPlot plot = barChart.getCategoryPlot();
        BarRenderer renderer = new BarRenderer();
        renderer.setSeriesPaint(0, Color.ORANGE); // Màu cam cho các cột
        plot.setRenderer(renderer);

        // Cập nhật bảng thông tin
        updateTable(new String[] {"Tháng", "Doanh thu (VND)"}, new Object[][] {
                {"Tháng 1", 5000000},
                {"Tháng 2", 7000000},
                {"Tháng 3", 8000000},
                {"Tháng 4", 6000000},
                {"Tháng 5", 9000000}
        });

        updateChart(barChart);
    }

    // Hàm cập nhật biểu đồ mới
    private void updateChart(JFreeChart chart) {
        chartPanelContainer.removeAll();
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanelContainer.add(chartPanel, BorderLayout.CENTER);
        chartPanelContainer.validate();
    }

    // Hàm cập nhật bảng thông tin
    private void updateTable(String[] columnNames, Object[][] data) {
        tableModel.setDataVector(data, columnNames);
    }

}