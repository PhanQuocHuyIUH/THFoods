package DAO;

import DB.Database;
import Entity.NguoiQuanTri;
import Entity.NhanVien;
import Entity.TaiKhoan;

import java.sql.*;
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

    public  String getIDMax() throws SQLException {
        Database.getInstance().connect(); // Đảm bảo kết nối cơ sở dữ liệu
        Connection con = Database.getConnection();
        String maNhanVienMoi = "NV01";  // Mã mặc định nếu chưa có nhân viên nào

        if (con != null) {
            Statement stmt = con.createStatement();
            String query = "SELECT maNV FROM NhanVien ORDER BY maNV DESC";  // Sắp xếp mã giảm dần để lấy mã lớn nhất
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                String lastMaNV = rs.getString("maNV");  // Lấy mã lớn nhất hiện tại, ví dụ: "NV05"
                int numberPart = Integer.parseInt(lastMaNV.substring(2));  // Lấy phần số, ví dụ: 5
                numberPart++;  // Tăng giá trị lên 1, thành 6
                maNhanVienMoi = "NV" + String.format("%02d", numberPart);  // Định dạng mã mới, thành "NV006"
            }

            rs.close();
            stmt.close();
        }else {
            System.out.println("Không kết nối được");
        }

        return maNhanVienMoi;
    }

    public void addTaiKhoanAndNhanVien(String tenDangNhap, String matKhau, String maNV, String tenNV, String sdt, String email, String ngaySinh) throws SQLException {
        String insertTaiKhoanSQL = "INSERT INTO TaiKhoan (tenDangNhap, matKhau) VALUES (?, ?)";
        String insertNhanVienSQL = "INSERT INTO NhanVien (maNV, tenNV, sdt, email, ngaySinh, tenDangNhap) VALUES (?, ?, ?, ?, ?, ?)";
        Database.getInstance().connect(); // Đảm bảo kết nối cơ sở dữ liệu
        Connection con = Database.getConnection();
        // Sử dụng try-with-resources để tự động đóng các tài nguyên
        try (PreparedStatement pstmtTaiKhoan = con.prepareStatement(insertTaiKhoanSQL);
             PreparedStatement pstmtNhanVien = con.prepareStatement(insertNhanVienSQL)) {

            // Thêm tài khoản
            pstmtTaiKhoan.setString(1, tenDangNhap);
            pstmtTaiKhoan.setString(2, matKhau);
            pstmtTaiKhoan.executeUpdate();

            // Thêm nhân viên
            pstmtNhanVien.setString(1, maNV);
            pstmtNhanVien.setString(2, tenNV);
            pstmtNhanVien.setString(3, sdt);
            pstmtNhanVien.setString(4, email);
            pstmtNhanVien.setString(5, ngaySinh);
            pstmtNhanVien.setString(6, tenDangNhap);
            pstmtNhanVien.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Lỗi: " + e.getMessage());
            throw e; // Ném lại ngoại lệ để xử lý bên ngoài nếu cần
        }
    }
}
