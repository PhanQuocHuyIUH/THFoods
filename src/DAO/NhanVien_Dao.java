package DAO;

import DB.Database;
import Entity.NguoiQuanTri;
import Entity.NhanVien;
import Entity.TaiKhoan;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;

public class NhanVien_Dao {
    public void insert() {
        System.out.println("Insert NhanVien");
    }

    public void update() {
        System.out.println("Update NhanVien");
    }

    public void delete() {
        System.out.println("Delete NhanVien");
    }

    public void select() {
        System.out.println("Select NhanVien");
    }

    public ArrayList<NhanVien> getInForNV() throws SQLException {
        ArrayList<NhanVien> dsNV= new ArrayList<>();
        Database.getInstance();
        Connection con = Database.getConnection();
        String sql= "select * from NhanVien";

        Statement statement= con.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()){
            String maNV= rs.getString(1);
            String matKhau= rs.getString(2);
            String sdt= rs.getString(3);
            String email= rs.getString(4);
            LocalDate ngaySinh = rs.getDate(5).toLocalDate();
            TaiKhoan tenDangNhap = new TaiKhoan(rs.getString(6));

            dsNV.add(new NhanVien(maNV,matKhau,sdt,email,ngaySinh,tenDangNhap));
        }
        return dsNV;
    }
}
