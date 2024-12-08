package DAO;

import DB.Database;
import Entity.NguoiQuanLy;
import Entity.NhanVien;
import Entity.TaiKhoan;

import javax.swing.*;
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

    public void deleteTaiKhoanAndQuanLy(String idQL) throws SQLException {
        Connection con = Database.getConnection();
        con.setAutoCommit(false); // Tắt auto-commit để xử lý giao dịch

        try (
                // Truy vấn để lấy tenDangNhap của người quản lý
                PreparedStatement getStmt = con.prepareStatement("SELECT tenDangNhap FROM NguoiQuanLy WHERE maQL = ?");
                PreparedStatement deleteAccountStmt = con.prepareStatement("DELETE FROM TaiKhoan WHERE tenDangNhap = ?");
                PreparedStatement deleteManagerStmt = con.prepareStatement("DELETE FROM NguoiQuanLy WHERE maQL = ?")
        ) {
            getStmt.setString(1, idQL);
            try (ResultSet rs = getStmt.executeQuery()) {
                if (rs.next()) {
                    String tenDangNhap = rs.getString("tenDangNhap");

                    // Xóa quản lý từ bảng NguoiQuanLy
                    deleteManagerStmt.setString(1, idQL);
                    deleteManagerStmt.executeUpdate();

                    // Xóa tài khoản từ bảng TaiKhoan
                    deleteAccountStmt.setString(1, tenDangNhap);
                    deleteAccountStmt.executeUpdate();

                    // Commit giao dịch sau khi thực hiện thành công
                    con.commit();
                } else {
                    JOptionPane.showMessageDialog(null, "Không tìm thấy quản lý với mã ID này.", "Lỗi", JOptionPane.ERROR_MESSAGE);
                }
            }
        } catch (SQLException e) {
            // Rollback giao dịch nếu có lỗi
            if (con != null) {
                con.rollback();
            }
            JOptionPane.showMessageDialog(null, "Lỗi khi xóa dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            // Đảm bảo bật lại auto-commit và đóng kết nối
            if (con != null) {
                con.setAutoCommit(true);
                con.close();
            }
        }
    }

    public void updateTaiKhoanAndQuanLy(String maQL, String tenNV, String soDT, String email, String tenDangNhapMoi, String matKhauMoi) throws SQLException {
        Connection con = Database.getConnection();
        con.setAutoCommit(false); // Tắt auto-commit để xử lý theo giao dịch

        try (
                // Cập nhật bảng NguoiQuanLy
                PreparedStatement updateManagerStmt = con.prepareStatement(
                        "UPDATE NguoiQuanLy SET tenNV = ?, soDT = ?, email = ?, tenDangNhap = ? WHERE maQL = ?"
                );

                // Cập nhật bảng TaiKhoan
                PreparedStatement updateAccountStmt = con.prepareStatement(
                        "UPDATE TaiKhoan SET matKhau = ?, tenDangNhap = ? WHERE tenDangNhap = (SELECT tenDangNhap FROM NguoiQuanLy WHERE maQL = ?)"
                )
        ) {
            // Cập nhật thông tin trong bảng NguoiQuanLy
            updateManagerStmt.setString(1, tenNV);
            updateManagerStmt.setString(2, soDT);
            updateManagerStmt.setString(3, email);
            updateManagerStmt.setString(4, tenDangNhapMoi);
            updateManagerStmt.setString(5, maQL);
            int managerUpdated = updateManagerStmt.executeUpdate();

            if (managerUpdated == 0) {
                throw new SQLException("Không tìm thấy người quản lý với mã: " + maQL);
            }

            // Cập nhật thông tin trong bảng TaiKhoan
            updateAccountStmt.setString(1, matKhauMoi);
            updateAccountStmt.setString(2, tenDangNhapMoi);
            updateAccountStmt.setString(3, maQL);
            int accountUpdated = updateAccountStmt.executeUpdate();

            if (accountUpdated == 0) {
                throw new SQLException("Không tìm thấy tài khoản tương ứng với quản lý mã: " + maQL);
            }

            // Commit giao dịch sau khi cả hai cập nhật thành công
            con.commit();
            JOptionPane.showMessageDialog(null, "Cập nhật thông tin thành công!", "Thành công", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            // Rollback giao dịch nếu xảy ra lỗi
            if (con != null) {
                con.rollback();
            }
            JOptionPane.showMessageDialog(null, "Lỗi khi cập nhật dữ liệu: " + e.getMessage(), "Lỗi", JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        } finally {
            // Đảm bảo bật lại auto-commit và đóng kết nối
            if (con != null) {
                con.setAutoCommit(true);
                con.close();
            }
        }
    }

    public void updateQuanLy(String maQL, String tenNV, String sdt, String email, String matKhau) throws SQLException {
        Connection con = Database.getConnection();

        try {
            String updateQLSQL = "UPDATE NguoiQuanLy SET tenQL = ?, sdt = ?, email = ? WHERE maQL = ?";
            PreparedStatement updateQLStmt = con.prepareStatement(updateQLSQL);
            updateQLStmt.setString(1, tenNV);
            updateQLStmt.setString(2, sdt);
            updateQLStmt.setString(3, email);
            updateQLStmt.setString(4, maQL);
            updateQLStmt.executeUpdate();

            String updateTKSQL = "UPDATE TaiKhoan SET matKhau = ? WHERE tenDangNhap = ?";
            PreparedStatement updateTKStmt = con.prepareStatement(updateTKSQL);
            updateTKStmt.setString(1, matKhau);
            updateTKStmt.setString(2, maQL); // Sử dụng maQL làm tenDangNhap
            updateTKStmt.executeUpdate();

            // Đóng statement
            updateQLStmt.close();
            updateTKStmt.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new SQLException("Lỗi khi cập nhật thông tin quản lý: " + e.getMessage());
        }
    }



}