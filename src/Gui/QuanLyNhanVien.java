package Gui;

import javax.swing.*;
import java.awt.*;

public class QuanLyNhanVien extends JPanel {

    public QuanLyNhanVien() {
        setLayout(new BorderLayout());

        // Tạo tiêu đề cho phần quản lý nhân viên
        JLabel titleLabel = new JLabel("Quản lý Nhân viên", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // Tạo bảng để hiển thị danh sách nhân viên
        String[] columnNames = {"ID", "Tên", "Chức vụ", "Lương"};
        Object[][] data = {
                {"001", "Nguyễn Văn A", "Quản lý", "15,000,000"},
                {"002", "Trần Thị B", "Nhân viên", "7,000,000"},
                {"003", "Lê Văn C", "Nhân viên", "6,500,000"}
        };

        JTable employeeTable = new JTable(data, columnNames);
        JScrollPane tableScrollPane = new JScrollPane(employeeTable);

        add(tableScrollPane, BorderLayout.CENTER);

        // Tạo panel chứa các nút chức năng
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new FlowLayout(FlowLayout.CENTER));

        JButton addButton = new JButton("Thêm Nhân Viên");
        JButton editButton = new JButton("Sửa Thông Tin");
        JButton deleteButton = new JButton("Xóa Nhân Viên");

        buttonPanel.add(addButton);
        buttonPanel.add(editButton);
        buttonPanel.add(deleteButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }
}
