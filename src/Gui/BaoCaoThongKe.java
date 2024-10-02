package Gui;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.category.DefaultCategoryDataset;


public class BaoCaoThongKe extends JFrame
{
    // Các thành phần giao diện
    private JTable table;
    private JButton btnLoadReport;
    private DefaultCategoryDataset dataset;

    public BaoCaoThongKe()
    {
        // Thiết lập giao diện cơ bản
        setTitle("Báo cáo doanh thu");
        setSize(800, 1000); // Kích thước cửa sổ
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Hiển thị cửa sổ ở giữa màn hình

        // Tạo bảng hiển thị doanh thu
        table = new JTable();
        JScrollPane scrollPane = new JScrollPane(table); // Cho phép cuộn dữ liệu bảng

        // Tạo mô hình dữ liệu cho bảng
        DefaultTableModel model = new DefaultTableModel();
        model.addColumn("Order Date"); // Cột "Order Date"
        model.addColumn("Total Revenue"); // Cột "Total Revenue"

        // Gán mô hình dữ liệu vào bảng
        table.setModel(model);

        // Nút để tải báo cáo doanh thu
        btnLoadReport = new JButton("Load Revenue Report");
        btnLoadReport.addActionListener(e -> loadSampleRevenueReport(model));

        // Tạo dataset cho biểu đồ
        dataset = new DefaultCategoryDataset();

        // Tạo biểu đồ
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 400)); // Kích thước biểu đồ

        // Layout cho ứng dụng
        setLayout(new BorderLayout());
        add(scrollPane, BorderLayout.NORTH); // Đặt bảng ở trên cùng
        add(chartPanel, BorderLayout.CENTER); // Đặt biểu đồ ở giữa
        add(btnLoadReport, BorderLayout.SOUTH); // Đặt nút ở dưới cùng của cửa sổ
    }

    // Phương thức tải dữ liệu mẫu
    private void loadSampleRevenueReport(DefaultTableModel model)
    {
        // Xóa dữ liệu cũ trong bảng và biểu đồ
        model.setRowCount(0);
        dataset.clear();

        // Dữ liệu mẫu
        Object[][] sampleData = {
                {"2024-09-01", 351.25},
                {"2024-09-02", 300.00},
                {"2024-09-03", 250.50},
                {"2024-09-04", 400.75},
                {"2024-09-05", 500.00},
        };

        // Đổ dữ liệu mẫu vào bảng và biểu đồ
        for (Object[] row : sampleData) {
            model.addRow(row);
            dataset.addValue((Double) row[1], "Revenue", (String) row[0]); // Ép kiểu row[0] thành String
        }

        // Cập nhật biểu đồ
        JFreeChart chart = createChart(dataset);
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new Dimension(800, 1000)); // Kích thước biểu đồ
        remove(1); // Xóa biểu đồ cũ
        add(chartPanel, BorderLayout.CENTER); // Thêm biểu đồ mới
        revalidate(); // Cập nhật lại layout
    }

    private JFreeChart createChart(DefaultCategoryDataset dataset)
    {
        // Tạo biểu đồ
        return ChartFactory.createBarChart(
                "Revenue Report", // Tiêu đề biểu đồ
                "Order Date", // Nhãn trục x
                "Total Revenue", // Nhãn trục y
                dataset // Dataset
        );
    }

    public static void main(String[] args) {
        // Tạo và hiển thị cửa sổ báo cáo doanh thu
        BaoCaoThongKe baoCaoThongKe = new BaoCaoThongKe();
        baoCaoThongKe.setVisible(true);
    }
}
