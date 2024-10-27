package DAO;

import DB.Database;
import Entity.ChiTietHoaDon;
import Entity.HoaDon;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class HoaDon_Dao
{
    public List<HoaDon> getAllHD() throws SQLException
    {
        List<HoaDon> dsHD = new ArrayList<HoaDon>();
        Database.getInstance().connect();
        Connection con = Database.getConnection();
        String sql = "select * from HoaDon";

        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next())
        {
            String maHD = rs.getString(1);
            Date ngayTao = rs.getDate(2);
            List<ChiTietHoaDon> dsCTHD = new ChiTietHoaDon_Dao().getCTHDByMaHD(maHD);

            dsHD.add(new HoaDon(maHD, ngayTao, dsCTHD));
        }

        return dsHD;
    }

    public double getTongTienHDByMAHD(String maHD) throws SQLException
    {
        double ThanhTien = 0;
        List<ChiTietHoaDon> dsCTHD = new ChiTietHoaDon_Dao().getCTHDByMaHD(maHD);
        for (ChiTietHoaDon cthd : dsCTHD)
        {
            ThanhTien += (new MonAn_Dao().getGiaMonAn(cthd.getMaMon()) * cthd.getSoLuong());
        }
        return ThanhTien;
    }

    public double getTongTienHD(HoaDon HD) throws SQLException
    {
        double ThanhTien = 0;
        List<ChiTietHoaDon> dsCTHD = HD.getChiTietHoaDon();
        for (ChiTietHoaDon cthd : dsCTHD)
        {
            ThanhTien += (new MonAn_Dao().getGiaMonAn(cthd.getMaMon()) * cthd.getSoLuong());
        }
        return ThanhTien;
    }

    public List<HoaDon> getDSHDToday() throws SQLException
    {
        List<HoaDon> dsHD = new ArrayList<HoaDon>();
        Database.getInstance().connect();
        Connection con = Database.getConnection();
        Date today = new Date(2024, 10, 16);
        String todayStr = today.getYear() + "-" + (today.getMonth()) + "-" + (today.getDate());
        String sql = "select * from HoaDon where ngayTao like '" + todayStr + "'";

        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next())
        {
            String maHD = rs.getString(1);
            Date ngayTao = rs.getDate(2);
            List<ChiTietHoaDon> dsCTHD = new ChiTietHoaDon_Dao().getCTHDByMaHD(maHD);

            dsHD.add(new HoaDon(maHD, ngayTao, dsCTHD));
        }
        return dsHD;
    }

    public static void main(String[] args) {
        try {
            List<HoaDon> dsHD = new HoaDon_Dao().getDSHDToday();
            for (HoaDon hd : dsHD) {
                double tt = new HoaDon_Dao().getTongTienHD(hd);
                System.out.println("MaHD: " + hd.getMaHD() + " NgayTao: " + hd.getNgayTao() + " SoCTHD: "
                        + hd.getChiTietHoaDon().size() + " TongTien: " + tt);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
    //Tạo hóa đơn mới
    public void createHD(HoaDon hd) throws SQLException
    {
        //format: "dd-MM-yyyy"
        Database.getInstance().connect();
        Connection con = Database.getConnection();
        String sql = "insert into HoaDon values('" + hd.getMaHD() + "','" + hd.getNgayTao() + "')";
        Statement statement = con.createStatement();
        statement.executeUpdate(sql);
    }
    //tạo chi tiết hóa đơn
    public void createCTHD(ChiTietHoaDon cthd) throws SQLException
    {
        Database.getInstance().connect();
        Connection con = Database.getConnection();
        String sql = "insert into CTHD values('" + cthd.getMaHD() + "','"+ cthd.getMaMon() +"','" + cthd.getSoLuong() + "')";
        Statement statement = con.createStatement();
        statement.executeUpdate(sql);
    }

}
