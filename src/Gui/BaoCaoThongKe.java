package Gui;

import DAO.HoaDon_Dao;
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
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.Map;

public class BaoCaoThongKe extends JPanel {

    private HoaDon_Dao hoaDon_dao;
    private JPanel chartPanelContainer;
    private DefaultCategoryDataset lineDataset;
    private DefaultCategoryDataset barDataset;
    private DefaultPieDataset pieDataset;
    private JTable table;
    private JComboBox<String> reportTypeComboBox;
    private DefaultTableModel tableModel;

    public BaoCaoThongKe() {
        setLayout(new BorderLayout());
        setBackground(Color.WHITE);

        // Panel chứa biểu đồ
        chartPanelContainer = new JPanel();
        chartPanelContainer.setLayout(new BorderLayout());

        // Thanh chọn loại báo cáo (ComboBox)
        reportTypeComboBox = new JComboBox<>(new String[] {
                "Doanh thu 7 ngày gần nhất",
                "Doanh thu theo tháng",
                "Các món ăn bán chạy nhất",
                "Các món ăn bán chậm nhất",
        });
        reportTypeComboBox.setSelectedIndex(0);

        // Làm nổi bật ComboBox
        reportTypeComboBox.setFont(new Font("Chalkduster", Font.BOLD, 14)); // Tăng kích thước và làm đậm font chữ
        reportTypeComboBox.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(Color.GRAY, 2),  // Đường viền ngoài
                new EmptyBorder(10, 10, 10, 10)                   // Tạo khoảng cách xung quanh ComboBox
        ));
        reportTypeComboBox.setMaximumSize(new Dimension(400, 50)); // Tăng kích thước tối đa của ComboBox (độ cao 50 pixel)
        reportTypeComboBox.setPreferredSize(new Dimension(400, 50)); // Tăng kích thước ưu tiên của ComboBox (độ cao 50 pixel)
        reportTypeComboBox.setBackground(new Color(230, 240, 255));

        // Bảng để hiển thị thông tin chi tiết
        tableModel = new DefaultTableModel();
        table = new JTable(tableModel);
        JScrollPane tableScrollPane = new JScrollPane(table);
        customizeOrderTable();

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

//         Thêm sự kiện cho thanh chọn loại báo cáo
        reportTypeComboBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedReport = (String) reportTypeComboBox.getSelectedItem();
                if (selectedReport.equals("Doanh thu 7 ngày gần nhất")) {
                    loadRevenueLast7Days();
                } else if (selectedReport.equals("Doanh thu theo tháng")) {
                    loadRevenueByMonth();
                } else if (selectedReport.equals("Các món ăn bán chạy nhất")) {
                    loadBestSellingDishes();
                } else if (selectedReport.equals("Các món ăn bán chậm nhất")) {
                    loadWorstSellingDishes();
                }
            }
        });

        // Hiển thị mặc định biểu đồ tròn khi khởi chạy
        loadRevenueLast7Days();
    }

//     Hàm tải dữ liệu và hiển thị các món ăn bán chạy nhất (biểu đồ cột)
    private void loadBestSellingDishes() {
        hoaDon_dao = new HoaDon_Dao();
        Map<String, Integer> monAnMap = null;
        try {
            monAnMap = hoaDon_dao.getBestSellingDishes();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        barDataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Integer> entry : monAnMap.entrySet()) {
            barDataset.addValue(entry.getValue(), "Số lượng", entry.getKey());
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "Các món ăn bán chạy nhất (30 ngày qua)",
                "Món ăn",
                "Số lượng",
                barDataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        // Tùy chỉnh màu sắc biểu đồ cột
        CategoryPlot plot = barChart.getCategoryPlot();
        BarRenderer renderer = new BarRenderer();
        renderer.setSeriesPaint(0, new Color(0, 102, 204));
        plot.setRenderer(renderer);

        // Cập nhật bảng thông tin
        updateTable(new String[] {"Món ăn", "Số lượng"}, convertMapToTableData2(monAnMap));

        updateChart(barChart);
    }

    // Hàm tải dữ liệu và hiển thị các món ăn bán chậm nhất
    private void loadWorstSellingDishes() {
        hoaDon_dao = new HoaDon_Dao();
        Map<String, Integer> monAnMap = null;
        try {
            monAnMap = hoaDon_dao.getWorstSellingDishes();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        barDataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Integer> entry : monAnMap.entrySet()) {
            barDataset.addValue(entry.getValue(), "Số lượng", entry.getKey());
        }

        JFreeChart barChart = ChartFactory.createBarChart(
                "Các món ăn bán chậm nhất (30 ngày qua)",
                "Món ăn",
                "Số lượng",
                barDataset,
                PlotOrientation.VERTICAL,
                true,
                true,
                false
        );

        // Tùy chỉnh màu sắc biểu đồ cột
        CategoryPlot plot = barChart.getCategoryPlot();
        BarRenderer renderer = new BarRenderer();
        renderer.setSeriesPaint(0, new Color(0, 102, 204));
        plot.setRenderer(renderer);

        // Cập nhật bảng thông tin
        updateTable(new String[] {"Món ăn", "Số lượng"}, convertMapToTableData2(monAnMap));

        updateChart(barChart);
    }

    // Hàm tải dữ liệu và hiển thị biểu đồ đường (doanh thu 7 ngày gần nhất)
    private void loadRevenueLast7Days() {
        hoaDon_dao = new HoaDon_Dao();
        lineDataset = new DefaultCategoryDataset();
        Map<String, Double> doanhThuMap = null;
        try {
            doanhThuMap = hoaDon_dao.getRevenueLast7Days();
            for (Map.Entry<String, Double> entry : doanhThuMap.entrySet()) {
                lineDataset.addValue(entry.getValue(), "Doanh thu", entry.getKey());
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

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
        renderer.setSeriesPaint(0, new Color(0, 102, 204, 150));
        plot.setRenderer(renderer);

        // Cập nhật bảng thông tin
        updateTable(new String[] {"Ngày", "Doanh thu (VND)"}, convertMapToTableData(doanhThuMap));

        updateChart(lineChart);
    }

    // Hàm tải dữ liệu và hiển thị biểu đồ cột (doanh thu theo tháng)
    private void loadRevenueByMonth() {
        hoaDon_dao = new HoaDon_Dao();
        Map<String, Double> doanhThuMap = null;
        try{
            doanhThuMap = hoaDon_dao.getRevenueByMonth();
        }
        catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        barDataset = new DefaultCategoryDataset();
        for (Map.Entry<String, Double> entry : doanhThuMap.entrySet()) {
            barDataset.addValue(entry.getValue(), "Doanh thu", entry.getKey());
        }

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
        renderer.setSeriesPaint(0, new Color(0, 102, 204));
        plot.setRenderer(renderer);

        // Cập nhật bảng thông tin
        updateTable(new String[] {"Tháng", "Doanh thu (VND)"}, convertMapToTableData(doanhThuMap));

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

    private void customizeOrderTable() {
        table.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        table.setRowHeight(30);
        table.setBackground(new Color(230, 240, 255));
        table.setForeground(new Color(50, 50, 50));
        table.setSelectionBackground(new Color(0, 0, 255, 150));
        table.setSelectionForeground(Color.WHITE);
        table.setGridColor(new Color(50, 150, 200));
        table.setDefaultEditor(Object.class, null);


        JTableHeader header = table.getTableHeader();
        header.setBackground(new Color(105, 165, 225));
        header.setForeground(Color.WHITE);
        header.setFont(new Font("Arial", Font.BOLD, 14));
    }

    private Object[][] convertMapToTableData(Map<String, Double> map) {
        Object[][] data = new Object[map.size()][2];
        int index = 0;
        for (Map.Entry<String, Double> entry : map.entrySet()) {
            data[index][0] = entry.getKey();
            data[index][1] = entry.getValue();
            index++;
        }
        return data;
    }

    private Object[][] convertMapToTableData2(Map<String, Integer> monAnMap) {
        Object[][] data = new Object[monAnMap.size()][2];
        int index = 0;
        for (Map.Entry<String, Integer> entry : monAnMap.entrySet()) {
            data[index][0] = entry.getKey();
            data[index][1] = entry.getValue();
            index++;
        }
        return data;
    }

}