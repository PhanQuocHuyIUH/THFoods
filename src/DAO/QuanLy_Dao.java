package DAO;

import DB.Database;
import Entity.NguoiQuanLy;
import Entity.NhanVien;
import Entity.TaiKhoan;

import java.sql.*;
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

    public String getIDMax() throws SQLException {
        Database.getInstance().connect(); // Đảm bảo kết nối cơ sở dữ liệu
        Connection con = Database.getConnection();
        String maQLMoi = "QL01"; // Mã mặc định nếu chưa có nhân viên nào

        if (con != null) {
            Statement stmt = con.createStatement();
            String query = "SELECT maQL FROM NguoiQuanLy ORDER BY maQL DESC"; // Sắp xếp mã giảm dần để lấy mã lớn nhất
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                String lastMaQL = rs.getString("maQL"); // Lấy mã lớn nhất hiện tại, ví dụ: "QL05"
                int numberPart = Integer.parseInt(lastMaQL.substring(2)); // Lấy phần số, ví dụ: 5
                numberPart++; // Tăng giá trị lên 1, thành 6
                maQLMoi = "QL" + String.format("%02d", numberPart); // Định dạng mã mới, thành "QL06"
            }

            rs.close();
            stmt.close();
        } else {
            System.out.println("Không kết nối được");
        }

        return maQLMoi;
    }

    public void addTaiKhoanAndQuanLy(String tenDangNhap, String matKhau, String maQL, String tenQL, String sdt,
                                       String email) throws SQLException {
        String insertTaiKhoanSQL = "INSERT INTO TaiKhoan (tenDangNhap, matKhau) VALUES (?, ?)";
        String insertNguoiQuanLySQL = "INSERT INTO NguoiQuanLy (maQL, tenQL, sdt, email, tenDangNhap) VALUES (?, ?, ?, ?, ?)";
        Database.getInstance().connect(); // Đảm bảo kết nối cơ sở dữ liệu
        Connection con = Database.getConnection();
        // Sử dụng try-with-resources để tự động đóng các tài nguyên
        try (PreparedStatement pstmtTaiKhoan = con.prepareStatement(insertTaiKhoanSQL);
             PreparedStatement pstmtQuanLy = con.prepareStatement(insertNguoiQuanLySQL)) {

            // Thêm tài khoản
            pstmtTaiKhoan.setString(1, tenDangNhap);
            pstmtTaiKhoan.setString(2, matKhau);
            pstmtTaiKhoan.executeUpdate();

            // Thêm Quản lý
            pstmtQuanLy.setString(1, maQL);
            pstmtQuanLy.setString(2, tenQL);
            pstmtQuanLy.setString(3, sdt);
            pstmtQuanLy.setString(4, email);
            pstmtQuanLy.setString(5, tenDangNhap);
            pstmtQuanLy.executeUpdate();

        } catch (SQLException e) {
            System.out.println("Lỗi: " + e.getMessage());
            throw e; // Ném lại ngoại lệ để xử lý bên ngoài nếu cần
        }
    }
}