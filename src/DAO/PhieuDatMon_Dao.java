package DAO;

import DB.Database;
import Entity.Ban;
import Entity.DonDatBan;
import Entity.NhanVien;
import Entity.PhieuDatMon;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;

public class PhieuDatMon_Dao{
    public PhieuDatMon_Dao(){
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

            // Giả sử cột 5 chứa thông tin mã của Ban cần khởi tạo
            Ban maBan = new Ban(rs.getString(5), null, 0);

            String khachHang = rs.getString(6);
            String sdt = rs.getString(7);

            // Thêm DonDatBan vào danh sách
            dsDonDatBan.add(new DonDatBan(maDDB, ngayDatBan, soGhe, ghiChu, maBan, khachHang, sdt));
        }
        rs.close();


        return dsDonDatBan;
    }


    //thêm phiếu đặt món vào dâtabase
    public void addPhieuDatMon(PhieuDatMon phieuDatMon) throws Exception {
        Database.getInstance();
        Connection con = Database.getConnection();

        // Chuyển LocalDateTime thành Timestamp
        Timestamp ngayDat = Timestamp.valueOf(phieuDatMon.getNgayDat());

        // Sử dụng PreparedStatement để tránh SQL Injection và xử lý đúng ngày giờ
        String sql = "INSERT INTO PhieuDatMon (MaPDB, NgayDat, GhiChu, MaBan, nhanVien) VALUES (?, ?, ?, ?, ?)";
        PreparedStatement statement = con.prepareStatement(sql);

        statement.setString(1, phieuDatMon.getMaPDB());
        statement.setTimestamp(2, ngayDat); // Chuyển LocalDateTime sang Timestamp
        statement.setString(3, phieuDatMon.getGhiChu());
        statement.setString(4, phieuDatMon.getMaBan());
        statement.setString(5, phieuDatMon.getTenNV());

        statement.executeUpdate();
    }

    //Lấy phiếu đặt món theo mã phiếu
    public PhieuDatMon getPhieuDatMonByMaPDB(String maPDB) throws SQLException {
        Database.getInstance();
        Connection con = Database.getConnection();
        String sql = "SELECT * FROM PhieuDatMon WHERE MaPDB = ?";

        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1, maPDB);

        ResultSet rs = preparedStatement.executeQuery();

        if (rs.next()) {
            // Lấy dữ liệu ngày giờ từ ResultSet và chuyển đổi thành LocalDateTime
            Timestamp ngayDatTimestamp = rs.getTimestamp(2);  // Cột thứ 2 là ngày đặt
            LocalDateTime ngayDat = ngayDatTimestamp != null ? ngayDatTimestamp.toLocalDateTime() : null;

            String ghiChu = rs.getString(3);
            Ban maBan = new Ban(rs.getString(4), null, 0); // Giả sử cột 4 chứa thông tin mã của `Ban`
            String tenNV = rs.getNString(5);
            return new PhieuDatMon(maPDB, ngayDat, ghiChu, maBan, tenNV);
        }

        rs.close();
        preparedStatement.close();
        con.close();

        return null; // Nếu không tìm thấy kết quả
    }


}
