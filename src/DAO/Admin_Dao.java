package DAO;

import DB.Database;
import Entity.NguoiQuanTri;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class Admin_Dao {
    public Admin_Dao() {

    }

    public ArrayList<NguoiQuanTri> getInFor() throws SQLException {
        ArrayList<NguoiQuanTri> admin= new ArrayList<>();
        Database.getInstance();
        Connection con = Database.getConnection();
        String sql= "select * from NguoiQuanTri";

        Statement statement= con.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()){
            String tenDangNhap= rs.getString(1);
            String matKhau= rs.getString(2);
            admin.add(new NguoiQuanTri(tenDangNhap,matKhau));
        }
        return admin;
    }
}
