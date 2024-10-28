package DAO;

import DB.Database;
import Entity.NguoiQuanLy;
import Entity.NhanVien;
import Entity.TaiKhoan;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class QuanLy_Dao {
    public QuanLy_Dao(){
    }

    public ArrayList<NguoiQuanLy> getInForQL() throws SQLException {
        ArrayList<NguoiQuanLy>  QL= new ArrayList<>();
        Database.getInstance();
        Connection con = Database.getConnection();
        String sql= "select * from NguoiQuanLy";

        Statement statement= con.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()){
            String maQL= rs.getString(1);
            String ten= rs.getString(2);
            String sdt= rs.getString(3);
            String email= rs.getString(4);

            TaiKhoan tenDangNhap = new TaiKhoan(rs.getString(5));

            QL.add(new NguoiQuanLy(maQL,ten,sdt,email,tenDangNhap));
        }
        return QL;
    }
}