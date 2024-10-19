package DAO;

import DB.Database;
import Entity.NhanVien;
import Entity.TaiKhoan;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class TaiKhoan_Dao {
    public TaiKhoan_Dao() {

    }
    public ArrayList<TaiKhoan> getAllTK() throws SQLException {
        ArrayList<TaiKhoan> dsTK= new ArrayList<>();
        Database.getInstance();
        Connection con = Database.getConnection();
        String sql= "select * from TaiKhoan";

        Statement statement= con.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()){
            String tenDangNhap= rs.getString(1);
            String matKhau= rs.getString(2);


            dsTK.add(new TaiKhoan(tenDangNhap,matKhau));
        }
        return dsTK;
    }
}
