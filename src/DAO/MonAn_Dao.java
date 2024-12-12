package DAO;

import DB.Database;
import Entity.MonAn;
import Entity.NhanVien;
import Entity.TaiKhoan;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class MonAn_Dao {
    public MonAn_Dao() {
    }

    public ArrayList<MonAn> getInForNV() throws SQLException {
        ArrayList<MonAn> dsMonAn = new ArrayList<>();
        Database.getInstance();
        Connection con = Database.getConnection();
        String sql = "select * from MonAn";

        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {
            String maMon = rs.getString(1);
            String tenMon = rs.getString(2);
            String loaiMon = rs.getString(3);
            double donGia = rs.getDouble(4);
            String moTa = rs.getString(5);

            dsMonAn.add(new MonAn(maMon, tenMon, loaiMon, donGia, moTa));
        }
        return dsMonAn;
    }

    public double getGiaMonAn(String maMon) throws SQLException {
        Database.getInstance();
        Connection con = Database.getConnection();
        String sql = "select DonGia from MonAn where MaMon = '" + maMon + "'";

        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        if (rs.next()) {
            return rs.getDouble(1);
        }
        return 0;
    }

    //lấy tên món theo mã món
    public String getTenMon(String maMon) throws SQLException {
        Database.getInstance();
        Connection con = Database.getConnection();
        String sql = "select TenMon from MonAn where MaMon = '" + maMon + "'";

        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        if (rs.next()) {
            return rs.getString(1);
        }
        return null;
    }

    public String getLoaiMonAn(String maMon) throws SQLException {
        Database.getInstance();
        Connection con = Database.getConnection();
        String sql = "select LoaiMon from MonAn where MaMon = '" + maMon + "'";

        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        if (rs.next()) {
            return rs.getString(1);
        }
        return null;
    }

    public MonAn getMonAnByTenMon(String tenMon) throws SQLException {
        Database.getInstance();
        Connection con = Database.getConnection();
        String sql = "select * from MonAn where tenMon like N'" + tenMon + "'";

        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        if (rs.next()) {
            String maMon = rs.getString(1);
            String loaiMon = rs.getString(3);
            double donGia = rs.getDouble(4);
            String moTa = rs.getString(5);

            return new MonAn(maMon, tenMon, loaiMon, donGia, moTa);
        }
        return null;
    }

//    public int getSoLuongMonAn() throws SQLException {
//        Database.getInstance();
//        Connection con = Database.getConnection();
//        String sql = "select count(*) from MonAn";
//
//        Statement statement = con.createStatement();
//        ResultSet rs = statement.executeQuery(sql);
//
//        if (rs.next()) {
//            return rs.getInt(1);
//        }
//        return 0;
//    }

//    public MonAn getMonAnByMaMon(String maMon) throws SQLException {
//        Database.getInstance();
//        Connection con = Database.getConnection();
//        String sql = "select * from MonAn where MaMon = '" + maMon + "'";
//
//        Statement statement = con.createStatement();
//        ResultSet rs = statement.executeQuery(sql);
//
//        if (rs.next()) {
//            String tenMon = rs.getString(2);
//            String loaiMon = rs.getString(3);
//            double donGia = rs.getDouble(4);
//            String moTa = rs.getString(5);
//
//            return new MonAn(maMon, tenMon, loaiMon, donGia, moTa);
//        }
//        System.out.println("Không tìm thấy món ăn");
//        return null;
//    }

    public boolean xoaMonAn(String maMon) throws SQLException {
        Database.getInstance();
        Connection con = Database.getConnection();
        String sql = "delete from MonAn where MaMon = '" + maMon + "'";

        Statement statement = con.createStatement();
        return statement.executeUpdate(sql) > 0;
    }

    public boolean themMonAn(MonAn monAn) throws SQLException {
        Database.getInstance();
        Connection con = Database.getConnection();
        String sql = "insert into MonAn values ('" + monAn.getMaMon() + "', N'" + monAn.getTenMon()
                + "', N'" + monAn.getLoaiMon() + "', " + monAn.getDonGia() + ", N'"
                + monAn.getMoTa() + "')";

        Statement statement = con.createStatement();
        return statement.executeUpdate(sql) > 0;
    }

    public boolean capNhatMonAn(MonAn monAn) throws SQLException {
        Database.getInstance();
        Connection con = Database.getConnection();
        String sql = "update MonAn set TenMon = N'" + monAn.getTenMon() + "', LoaiMon = N'" +
                monAn.getLoaiMon() + "', DonGia = " + monAn.getDonGia() + ", MoTa = N'" +
                monAn.getMoTa() + "' where MaMon = '" + monAn.getMaMon() + "'";

        Statement statement = con.createStatement();
        return statement.executeUpdate(sql) > 0;
    }

    //Lấy mã món theo tên món
    public String getMaMonByTenMon(String tenMon) throws SQLException {
        Database.getInstance();
        Connection con = Database.getConnection();
        String sql = "select MaMon from MonAn where TenMon like N'" + tenMon + "'";

        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        if (rs.next()) {
            return rs.getString(1);
        }
        return null;
    }
}
