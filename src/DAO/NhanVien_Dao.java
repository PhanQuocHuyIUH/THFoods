package DAO;

import DB.Database;
import Entity.NguoiQuanTri;
import Entity.NhanVien;
import Entity.TaiKhoan;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.*;

import java.time.LocalDate;
import java.util.ArrayList;

public class NhanVien_Dao {

    public void insert(NhanVien nhanVien) throws SQLException {
        String sql = "INSERT INTO NhanVien (maNV, tenNV, sdt, email, ngaySinh, tenDangNhap) VALUES (?, ?, ?, ?, ?, ?)";
        try (Connection con = Database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, nhanVien.getMaNV());
            pstmt.setString(2, nhanVien.getTenNV()); // Thêm tên nhân viên
            pstmt.setString(3, nhanVien.getSdt());
            pstmt.setString(4, nhanVien.getEmail());
            pstmt.setDate(5, java.sql.Date.valueOf(nhanVien.getNgaySinh()));
            pstmt.setString(6, nhanVien.getTenDangNhap().getTenDangNhap()); // Giả sử TaiKhoan có phương thức
                                                                            // getTenDangNhap()
            pstmt.executeUpdate();
        }
    }

    public boolean update(NhanVien nhanVien) throws SQLException {
        String sql = "UPDATE NhanVien SET tenNV = ?, sdt = ?, email = ?, ngaySinh = ?, tenDangNhap = ? WHERE maNV = ?";
        try (Connection con = Database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, nhanVien.getTenNV());
            pstmt.setString(2, nhanVien.getSdt());
            pstmt.setString(3, nhanVien.getEmail());
            pstmt.setDate(4, java.sql.Date.valueOf(nhanVien.getNgaySinh()));
            pstmt.setString(5, nhanVien.getTenDangNhap().getTenDangNhap()); // Giả sử TaiKhoan có phương thức
                                                                            // getTenDangNhap()
            pstmt.setString(6, nhanVien.getMaNV());

            int rowsAffected = pstmt.executeUpdate();
            return rowsAffected > 0; // Trả về true nếu có hàng bị cập nhật
        }
    }

    public boolean deleteEmployeeAndAccount(String maNV) throws SQLException {
        String getAccountSql = "SELECT tenDangNhap FROM NhanVien WHERE maNV = ?";
        String deleteEmployeeSql = "DELETE FROM NhanVien WHERE maNV = ?";
        String deleteAccountSql = "DELETE FROM TaiKhoan WHERE tenDangNhap = ?";
    
        try (Connection con = Database.getConnection()) {
            con.setAutoCommit(false); // Bắt đầu giao dịch
    
            String tenDangNhap = null;
    
            // Lấy tên đăng nhập của nhân viên
            try (PreparedStatement pstmtGetAccount = con.prepareStatement(getAccountSql)) {
                pstmtGetAccount.setString(1, maNV);
                ResultSet rs = pstmtGetAccount.executeQuery();
                if (rs.next()) {
                    tenDangNhap = rs.getString("tenDangNhap");
                }
            }
    
            // Nếu không tìm thấy tên đăng nhập, không thực hiện xóa
            if (tenDangNhap == null) {
                return false; // Không tìm thấy nhân viên
            }
    
            try (PreparedStatement pstmt2 = con.prepareStatement(deleteEmployeeSql);
                 PreparedStatement pstmt1 = con.prepareStatement(deleteAccountSql)) {
    
                // Xóa nhân viên
                pstmt2.setString(1, maNV);
                int rowsAffected = pstmt2.executeUpdate();
    
                // Xóa tài khoản
                pstmt1.setString(1, tenDangNhap);
                pstmt1.executeUpdate();
    
                con.commit(); // Cam kết giao dịch
                return rowsAffected > 0; // Trả về true nếu có hàng bị xóa
            } catch (SQLException e) {
                con.rollback(); // Rollback nếu có lỗi
                throw e; // Ném lại ngoại lệ để xử lý bên ngoài
            }
        }
    }

    public NhanVien select(String maNV) throws SQLException {
        String sql = "SELECT * FROM NhanVien WHERE maNV = ?";
        try (Connection con = Database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, maNV);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                String tenNV = rs.getString("tenNV"); // Lấy tên nhân viên
                String sdt = rs.getString("sdt");
                String email = rs.getString("email");
                LocalDate ngaySinh = rs.getDate("ngaySinh").toLocalDate();
                TaiKhoan tenDangNhap = new TaiKhoan(rs.getString("tenDangNhap")); // Giả sử TaiKhoan có constructor phù
                                                                                  // hợp
                return new NhanVien(maNV, tenNV, sdt, email, ngaySinh, tenDangNhap);
            }
        }
        return null; // Trả về null nếu không tìm thấy
    }

    public ArrayList<NhanVien> getAllNhanVienByMa(String maNV) throws SQLException {
        ArrayList<NhanVien> dsNV = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien WHERE maNV LIKE ?"; // Sử dụng LIKE để tìm kiếm linh hoạt

        try (Connection con = Database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, "%" + maNV + "%"); // Thêm ký tự % để tìm kiếm theo mã
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String maNVResult = rs.getString("maNV");
                String tenNV = rs.getString("tenNV");
                String sdt = rs.getString("sdt");
                String email = rs.getString("email");
                LocalDate ngaySinh = rs.getDate("ngaySinh").toLocalDate();
                TaiKhoan tenDangNhap = new TaiKhoan(rs.getString("tenDangNhap")); // Giả sử TaiKhoan có constructor phù
                                                                                  // hợp

                NhanVien nhanVien = new NhanVien(maNVResult, tenNV, sdt, email, ngaySinh, tenDangNhap);
                dsNV.add(nhanVien);
            }
        }

        return dsNV; // Trả về danh sách nhân viên tìm được
    }

    public ArrayList<NhanVien> getAllNhanVienByTen(String tenNV) throws SQLException {
        ArrayList<NhanVien> dsNV = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien WHERE tenNV LIKE ?"; // Sử dụng LIKE để tìm kiếm linh hoạt

        try (Connection con = Database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql)) {
            pstmt.setString(1, "%" + tenNV + "%"); // Thêm ký tự % để tìm kiếm theo tên
            ResultSet rs = pstmt.executeQuery();

            while (rs.next()) {
                String maNVResult = rs.getString("maNV");
                String tenNVResult = rs.getString("tenNV");
                String sdt = rs.getString("sdt");
                String email = rs.getString("email");
                LocalDate ngaySinh = rs.getDate("ngaySinh").toLocalDate();
                TaiKhoan tenDangNhap = new TaiKhoan(rs.getString("tenDangNhap")); 
                NhanVien nhanVien = new NhanVien(maNVResult, tenNVResult, sdt, email, ngaySinh, tenDangNhap);
                dsNV.add(nhanVien);
            }
        }

        return dsNV; // Trả về danh sách nhân viên tìm được
    }


    public ArrayList<NhanVien> getAllNhanVien() throws SQLException {
        ArrayList<NhanVien> dsNV = new ArrayList<>();
        String sql = "SELECT * FROM NhanVien";

        try (Connection con = Database.getConnection();
                PreparedStatement pstmt = con.prepareStatement(sql);
                ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                String maNV = rs.getString("maNV");
                String tenNV = rs.getString("tenNV");
                String sdt = rs.getString("sdt");
                String email = rs.getString("email");
                LocalDate ngaySinh = rs.getDate("ngaySinh").toLocalDate();
                TaiKhoan tenDangNhap = new TaiKhoan(rs.getString("tenDangNhap")); // Giả sử TaiKhoan có constructor phù
                                                                                  // hợp

                NhanVien nhanVien = new NhanVien(maNV, tenNV, sdt, email, ngaySinh, tenDangNhap);
                dsNV.add(nhanVien);
            }
        }

        return dsNV; // Trả về danh sách nhân viên
    }

    public ArrayList<NhanVien> getInForNV() throws SQLException {
        ArrayList<NhanVien> dsNV = new ArrayList<>();
        Database.getInstance();
        Connection con = Database.getConnection();
        String sql = "select * from NhanVien";

        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {
            String maNV = rs.getString(1);
            String matKhau = rs.getString(2);
            String sdt = rs.getString(3);
            String email = rs.getString(4);
            LocalDate ngaySinh = rs.getDate(5).toLocalDate();
            TaiKhoan tenDangNhap = new TaiKhoan(rs.getString(6));

            dsNV.add(new NhanVien(maNV, matKhau, sdt, email, ngaySinh, tenDangNhap));
        }
        return dsNV;
    }

    public String getIDMax() throws SQLException {
        Database.getInstance().connect(); // Đảm bảo kết nối cơ sở dữ liệu
        Connection con = Database.getConnection();
        String maNhanVienMoi = "NV01"; // Mã mặc định nếu chưa có nhân viên nào

        if (con != null) {
            Statement stmt = con.createStatement();
            String query = "SELECT maNV FROM NhanVien ORDER BY maNV DESC"; // Sắp xếp mã giảm dần để lấy mã lớn nhất
            ResultSet rs = stmt.executeQuery(query);

            if (rs.next()) {
                String lastMaNV = rs.getString("maNV"); // Lấy mã lớn nhất hiện tại, ví dụ: "NV05"
                int numberPart = Integer.parseInt(lastMaNV.substring(2)); // Lấy phần số, ví dụ: 5
                numberPart++; // Tăng giá trị lên 1, thành 6
                maNhanVienMoi = "NV" + String.format("%02d", numberPart); // Định dạng mã mới, thành "NV006"
            }

            rs.close();
            stmt.close();
        } else {
            System.out.println("Không kết nối được");
        }

        return maNhanVienMoi;
    }

    public void addTaiKhoanAndNhanVien(String tenDangNhap, String matKhau, String maNV, String tenNV, String sdt,
            String email, String ngaySinh) throws SQLException {
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

    public void updateNhanVien(String maNV, String tenNV, String sdt, String email, String ngaySinh, String matKhau) throws SQLException {
        Connection con = Database.getConnection();

        try {
            String updateNVSQL = "UPDATE NhanVien SET tenNV = ?, sdt = ?, email = ?, ngaySinh = ? WHERE maNV = ?";
            PreparedStatement updateNVStmt = con.prepareStatement(updateNVSQL);
            updateNVStmt.setString(1, tenNV);
            updateNVStmt.setString(2, sdt);
            updateNVStmt.setString(3, email);
            updateNVStmt.setString(4, ngaySinh);
            updateNVStmt.setString(5, maNV);
            updateNVStmt.executeUpdate();

            String updateTKSQL = "UPDATE TaiKhoan SET matKhau = ? WHERE tenDangNhap = ?";
            PreparedStatement updateTKStmt = con.prepareStatement(updateTKSQL);
            updateTKStmt.setString(1, matKhau);
            updateTKStmt.setString(2, maNV); // Sử dụng maNV làm tenDangNhap
            updateTKStmt.executeUpdate();

            // Đóng statement
            updateNVStmt.close();
            updateTKStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Lỗi khi cập nhật thông tin nhân viên: " + e.getMessage());
        }
    }

}
