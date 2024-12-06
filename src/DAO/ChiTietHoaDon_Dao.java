package DAO;

import DB.Database;
import Entity.ChiTietHoaDon;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class ChiTietHoaDon_Dao
{
    public List<ChiTietHoaDon> getCTHDByMaHD(String maHD) throws SQLException
    {
        List<ChiTietHoaDon> dsCTHD = new ArrayList<ChiTietHoaDon>();
        Database.getInstance();
        Connection con = Database.getConnection();
        String sql = "select * from CTHD where maHD = '" + maHD + "'";

        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next())
        {
            String monAn = rs.getString(2);
            int soLuong = rs.getInt(3);
            double donGia = rs.getDouble(4);

            dsCTHD.add(new ChiTietHoaDon(maHD, monAn, soLuong,donGia));
        }

        return dsCTHD;
    }
}
