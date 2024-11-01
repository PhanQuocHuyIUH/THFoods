package DAO;

import DB.Database;
import Entity.TaiKhoan;

import javax.swing.table.DefaultTableModel;
import java.sql.*;
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

    public void loadNhanVienData(DefaultTableModel tableModel) throws SQLException {
        // Xóa dữ liệu cũ trong tableModel nếu có
        tableModel.setRowCount(0);

        // Khởi tạo kết nối
        Database.getInstance().connect();
        Connection con = Database.getConnection();

        if (con != null) {
            CallableStatement stmt = con.prepareCall("{CALL getInforTkN}");
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                String maNV = rs.getString("maNV");
                String tenNV = rs.getString("tenNV");
                String sdt = rs.getString("sdt");
                String email = rs.getString("email");
                LocalDate ngaySinh = rs.getDate("ngaySinh").toLocalDate();
                String tenDangNhap = rs.getString("tenDangNhap");
                String matKhau = rs.getString("matKhau");

                tableModel.addRow(new Object[]{maNV, tenNV, sdt, email, ngaySinh, tenDangNhap, matKhau});
            }
            rs.close();
            stmt.close();
        } else {
            System.out.println("Không thể kết nối đến cơ sở dữ liệu.");
        }
    }


}
