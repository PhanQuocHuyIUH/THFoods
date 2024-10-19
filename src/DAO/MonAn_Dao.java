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
}
