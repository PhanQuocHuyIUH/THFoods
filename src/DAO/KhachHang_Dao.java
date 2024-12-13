package DAO;

import DB.Database;
import Entity.KhachHang;

import java.sql.*;
import java.util.ArrayList;

public class KhachHang_Dao {
    public KhachHang_Dao() {
    }

    public ArrayList<KhachHang> getAll_KhachHang() throws SQLException {
        ArrayList<KhachHang> dsKhachHang = new ArrayList<>();
        Database.getInstance();
        Connection con = Database.getConnection();
        String sql = "SELECT * FROM KhachHang";

        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()){
            String maKH = rs.getString(1);
            String tenKH = rs.getString(2);
            String sDT = rs.getString(3);
            dsKhachHang.add(new KhachHang(maKH, tenKH, sDT));
        }
        return  dsKhachHang;
    }

    public boolean addKhachHang(KhachHang khachHang) throws SQLException{
        Database.getInstance();
        Connection con = Database.getConnection();
        String sql = "INSERT INTO KhachHang (maKH, tenKH, sdt) VALUES (?, ?, ?)";
        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setString(1, khachHang.getMaKH());
            preparedStatement.setString(2, khachHang.getTenKH());
            preparedStatement.setString(3, khachHang.getSdt());
            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

    }

    public KhachHang getKhachHangBySoDienThoai(String soDienThoai) throws SQLException {
        Database.getInstance();
        Connection con = Database.getConnection();
        String sql = "SELECT * FROM KhachHang WHERE sdt = ?";
        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setString(1, soDienThoai);
            ResultSet rs = preparedStatement.executeQuery();
            if (rs.next()) {
                String maKH = rs.getString("maKH");
                String tenKH = rs.getString("tenKH");
                return new KhachHang(maKH, tenKH, soDienThoai);
            }
        }
        return null; // Khách hàng không tồn tại
    }
    public String getNameKhachHang(String soDienThoai) throws SQLException {
        // Lấy kết nối từ Database
        Database.getInstance();
        Connection con = Database.getConnection();

        // SQL query để lấy tên khách hàng từ số điện thoại
        String sql = "SELECT tenKH FROM KhachHang WHERE sdt = ?";

        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setString(1, soDienThoai);  // Set số điện thoại vào câu lệnh SQL
            ResultSet rs = preparedStatement.executeQuery();

            if (rs.next()) {
                // Trả về tên khách hàng nếu có kết quả
                return rs.getString("tenKH");
            }
        }
        return null; // Trả về null nếu không tìm thấy khách hàng với số điện thoại này
    }


}
