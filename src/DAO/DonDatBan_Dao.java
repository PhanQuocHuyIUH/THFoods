package DAO;

import DB.Database;
import Entity.Ban;
import Entity.DonDatBan;
import Entity.KhachHang;
import Entity.TrangThaiDonDat;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class DonDatBan_Dao {
    public DonDatBan_Dao() {
    }

    public ArrayList<DonDatBan> getAll_DonDatBan() throws SQLException {
        ArrayList<DonDatBan> dsDonDatBan = new ArrayList<>();

        Database.getInstance();
        Connection con = Database.getConnection();
        String sql = "SELECT * FROM DonDatBan";

        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        while (rs.next()){
            String maDDB = rs.getString(1);
            KhachHang tenKH = new KhachHang("", rs.getString(2), "");
            KhachHang sDT = new KhachHang("", "", rs.getString(3));
            String ngayDatStr = rs.getString(4);
            LocalDateTime ngayDat = LocalDateTime.parse(ngayDatStr, formatter);
            int soGhe = rs.getInt(5);
            String ghiChu = rs.getString(6);
            Ban maBan = new Ban(rs.getString(7), null, 0);
            TrangThaiDonDat trangThai = TrangThaiDonDat.valueOf(rs.getString(8));
            KhachHang maKH = new KhachHang(rs.getString(9), "", "");

            dsDonDatBan.add(new DonDatBan(maDDB, tenKH, sDT, ngayDat,soGhe, ghiChu,maBan,trangThai,maKH));
        }
        return dsDonDatBan;
    }

    public boolean addDonDatBan(DonDatBan donDatBan) throws Exception {
        Database.getInstance();
        Connection con = Database.getConnection();
        String sql = "INSERT INTO DonDatBan (maDDB, tenKH, sdt, ngayDatBan, soGhe, ghiChu, maBan, trangThaiDonDat, " +
                "maKH) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
            preparedStatement.setString(1, donDatBan.getMaDD());
            preparedStatement.setString(2, donDatBan.getTenKH() != null ? donDatBan.getTenKH().getTenKH() : null);
            preparedStatement.setString(3, donDatBan.getsDT() != null ? donDatBan.getsDT().getSdt() : null);

            // Kiểm tra nếu ngayDat là null trước khi gọi format
            if (donDatBan.getNgayDat() != null) {
                preparedStatement.setString(4, donDatBan.getNgayDat().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
            } else {
                preparedStatement.setNull(4, Types.TIMESTAMP); // Đặt giá trị null cho cột ngày nếu ngayDat là null
            }

            preparedStatement.setInt(5, donDatBan.getSoGhe());
            preparedStatement.setString(6, donDatBan.getGhiChu());
            preparedStatement.setString(7, donDatBan.getMaBan() != null ? donDatBan.getMaBan().getMaBan() : null);
            preparedStatement.setString(8, donDatBan.getTrangThaiDDB() != null ? donDatBan.getTrangThaiDDB().name() : null);
            preparedStatement.setString(9, donDatBan.getMaKH() != null ? donDatBan.getMaKH().getMaKH() : null);

            int rowsInserted = preparedStatement.executeUpdate();
            return rowsInserted > 0; // Trả về true nếu thêm thành công
        } catch (SQLException e) {
            e.printStackTrace();
            return false; // Trả về false nếu có lỗi
        }
    }

}




