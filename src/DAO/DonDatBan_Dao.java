package DAO;

import DB.Database;
import Entity.Ban;
import Entity.DonDatBan;
import Entity.PhieuDatMon;
import Entity.TrangThaiBan;

import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class DonDatBan_Dao {
    public DonDatBan_Dao() {
    }

    public ArrayList<DonDatBan> getAllDonDatBan() throws SQLException {
        ArrayList<DonDatBan> dsDonDatBan = new ArrayList<>();
        Database.getInstance();
        Connection con = Database.getConnection();
        String sql = "SELECT * FROM DonDatBan";

        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {
            String maDDB = rs.getString(1);
            String ngayDatBan = rs.getString(2);
            int soGhe = rs.getInt(3);
            String ghiChu = rs.getString(4);

            // Giả sử cột 5 chứa thông tin mã của `Ban` cần khởi tạo
            Ban maBan = new Ban(rs.getString(5), null, 0);

            String khachHang = rs.getString(6);
            String sdt = rs.getString(7);

            // Thêm `DonDatBan` vào danh sách
            dsDonDatBan.add(new DonDatBan(maDDB, ngayDatBan, soGhe, ghiChu, maBan, khachHang, sdt));
        }
        rs.close();


        return dsDonDatBan;
    }

    public int getSoLuongDonDat() throws SQLException {
        Database.getInstance();
        Connection con = Database.getConnection();

        // Sử dụng COUNT để lấy tổng số lượng bàn
        String sql = "SELECT COUNT(maDDB) FROM DonDatBan";
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        // Kiểm tra kết quả và trả về số lượng
        int soLuongDonDat = 0;
        if (rs.next()) {
            soLuongDonDat = rs.getInt(1); // Lấy tổng số lượng từ cột đầu tiên
        }

        rs.close();
        statement.close();

        return soLuongDonDat;
    }

    public void deleteDonDatBan(String maDon) throws SQLException {
        Database.getInstance();
        Connection con = Database.getConnection();
        String sql = "delete from DonDatBan where maDDB = '"+maDon+"'";
        Statement statement = con.createStatement();
        statement.executeUpdate(sql);
    }

    public void addDonDatBan(DonDatBan donDat) throws Exception {
        Database.getInstance();
        Connection con = Database.getConnection();

        String sql = "INSERT INTO DonDatBan (maDDB, ngayDatBan, soGhe, ghiChu, maBan, khachHang, sdt) VALUES (?, ?, ?, ?, ?, ?, ?)";
        Statement statement = con.createStatement();
        PreparedStatement preparedStatement = con.prepareStatement(sql);

        try {
            preparedStatement.setString(1, donDat.getMaDDB());

            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            java.util.Date parsedDate = format.parse(donDat.getNgayDatBan());
            java.sql.Date sqlDate = new java.sql.Date(parsedDate.getTime());
            preparedStatement.setDate(2, sqlDate);

            preparedStatement.setInt(3, donDat.getSoGhe());
            preparedStatement.setString(4, donDat.getGhiChu());
            preparedStatement.setString(5, donDat.getBan().getMaBan());
            preparedStatement.setString(6, donDat.getKhachHang());
            preparedStatement.setString(7, donDat.getSdt());

            preparedStatement.executeUpdate();

        } catch (SQLException e) {
            System.out.println("SQL State: " + e.getSQLState());
            System.out.println("Error Code: " + e.getErrorCode());
            System.out.println("Message: " + e.getMessage());
            throw new Exception("Insert failed", e); // Ghi log và ném ngoại lệ lại
        } finally {
            preparedStatement.close();
            statement.close();
            con.close();
        }
    }





}
