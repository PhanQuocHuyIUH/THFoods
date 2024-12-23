package DAO;

import DB.Database;
import Entity.Ban;
import Entity.ChiTietDatMon;
import Entity.TrangThaiBan;

import java.sql.*;
import java.util.ArrayList;

public class Ban_Dao {
    public Ban_Dao() {
    }

    // lấy danh sách bàn
    public ArrayList<Ban> getAllBan() throws SQLException {
        ArrayList<Ban> dsBan = new ArrayList<>();
        Database.getInstance();
        Connection con = Database.getConnection();
        String sql = "select * from Ban";

        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {
            String maBan = rs.getString(1);
            TrangThaiBan trangThai = TrangThaiBan.valueOf(rs.getString(2));
            int soCho = rs.getInt(3);

            dsBan.add(new Ban(maBan, trangThai, soCho));
        }
        return dsBan;
    }

    // update trạng thái bàn
    public void updateTrangThaiBan(Ban ban) throws Exception {
        Database.getInstance();
        Connection con = Database.getConnection();
        String sql = "update Ban set trangThai = '" + ban.getTrangThai() + "' where MaBan = '" + ban.getMaBan() + "'";
        Statement statement = con.createStatement();
        statement.executeUpdate(sql);
    }

    // get số phiếu theo mã bàn
    public String getSoPhieu(String maBan) throws SQLException {
        Database.getInstance();
        Connection con = Database.getConnection();
        String sql = "select count(*) from PhieuDatMon where MaBan = '" + maBan + "'";
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        rs.next();
        return rs.getString(1);
    }

    // xóa phiếu theo mã bàn
    public void deletePhieu(String maBan) throws SQLException {
        Database.getInstance();
        Connection con = Database.getConnection();
        String sql = "delete from PhieuDatMon where MaBan = '" + maBan + "'";
        Statement statement = con.createStatement();
        statement.executeUpdate(sql);
    }

    // cập nhật trạng thái bàn theo mã bàn
    public void updateTrangThaiBan(String maBan, String trangThai) throws SQLException {
        Database.getInstance();
        Connection con = Database.getConnection();
        String sql = "update Ban set TrangThai = '" + trangThai + "' where MaBan = '" + maBan + "'";
        Statement statement = con.createStatement();
        statement.executeUpdate(sql);
    }

    // xóa chi tiết phiếu theo mã phiếu
    public void deleteCTPhieu(String maPhieu) throws SQLException {
        Database.getInstance();
        Connection con = Database.getConnection();
        String sql = "delete from CTDM where MaPDB = '" + maPhieu + "'";
        Statement statement = con.createStatement();
        statement.executeUpdate(sql);
    }

    public void deleteCTPhieuByMaMon(String maPhieu, String maMon) throws SQLException {
        Database.getInstance();
        Connection con = Database.getConnection();
        String sql = "delete from CTDM where MaPDB = '" + maPhieu + "' and MaMon = '" + maMon + "'";
        Statement statement = con.createStatement();
        statement.executeUpdate(sql);
    }

    // lấy danh sách phiếu theo mã bàn
    public ArrayList<String> getDSPhieu(String maBan) throws SQLException {
        ArrayList<String> dsPhieu = new ArrayList<>();
        Database.getInstance();
        Connection con = Database.getConnection();
        String sql = "select maPDB from PhieuDatMon where maBan = '" + maBan + "'";
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            dsPhieu.add(rs.getString(1));
        }
        return dsPhieu;
    }

    // lấy danh sách chi tiết phiếu theo mã phiếu
    public ArrayList<ChiTietDatMon> getDSCTPhieu(String maPhieu) throws SQLException {
        ArrayList<ChiTietDatMon> dsCTPhieu = new ArrayList<>();
        Database.getInstance();
        Connection con = Database.getConnection();
        String sql = "select * from CTDM where maPDB = '" + maPhieu + "'";
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            String maMon = rs.getString(2);
            int soLuong = rs.getInt(3);
            dsCTPhieu.add(new ChiTietDatMon(maPhieu, maMon, soLuong));
        }
        return dsCTPhieu;
    }

    // Lấy mã bàn theo mã phiếu
    public String getMaBanByMaPhieu(String maPhieu) throws SQLException {
        Database.getInstance();
        Connection con = Database.getConnection();
        String sql = "select MaBan from PhieuDatMon where MaPDB = '" + maPhieu + "'";
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        rs.next();
        return rs.getString(1);
    }

    // Thêm bàn
    public void themBan(Ban ban) throws SQLException {
        Database.getInstance();
        Connection con = Database.getConnection();
        String sql = "INSERT INTO Ban (MaBan, TrangThai, SoGhe) VALUES (?, ?, ?)";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1, ban.getMaBan());
        preparedStatement.setString(2, TrangThaiBan.Trong.name()); // Trạng thái mặc định là TRONG
        preparedStatement.setInt(3, ban.getSoGhe());
        preparedStatement.executeUpdate();
    }

    // Sửa bàn
    public void suaBan(Ban ban) throws SQLException {
        Connection con = Database.getConnection();
        try {
            // Kiểm tra xem bàn có đang được sử dụng không
            String checkUsageSQL = "SELECT COUNT(*) FROM PhieuDatMon WHERE maBan = ?";
            try (PreparedStatement pstmt = con.prepareStatement(checkUsageSQL)) {
                pstmt.setString(1, ban.getMaBan());
                ResultSet rs = pstmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    // Nếu có bản ghi tham chiếu, ném ngoại lệ
                    throw new SQLException("Không thể sửa bàn vì bàn đang được sử dụng.");
                }
            }

            // Nếu không có bản ghi tham chiếu, thực hiện sửa bàn
            String sql = "UPDATE Ban SET SoGhe = ? WHERE MaBan = ?";
            try (PreparedStatement preparedStatement = con.prepareStatement(sql)) {
                preparedStatement.setInt(1, ban.getSoGhe());
                preparedStatement.setString(2, ban.getMaBan());
                preparedStatement.executeUpdate();
            }
        } finally {
            if (con != null) {
                Database.getInstance().disconnect(); // Đảm bảo ngắt kết nối
            }
        }
    }

    // Xóa bàn
    public void xoaBan(String maBan) throws SQLException {
        Connection con = Database.getConnection();
        try {
            // Kiểm tra xem bàn có đang được sử dụng không
            String checkUsageSQL = "SELECT COUNT(*) FROM PhieuDatMon WHERE maBan = ?";
            try (PreparedStatement pstmt = con.prepareStatement(checkUsageSQL)) {
                pstmt.setString(1, maBan);
                ResultSet rs = pstmt.executeQuery();
                if (rs.next() && rs.getInt(1) > 0) {
                    // Nếu có bản ghi tham chiếu, ném ngoại lệ
                    throw new SQLException("Không thể xóa bàn vì bàn đang được sử dụng.");
                }
            }

            // Nếu không có bản ghi tham chiếu, thực hiện xóa bàn
            String deleteBanSQL = "DELETE FROM Ban WHERE maBan = ?";
            try (PreparedStatement pstmt = con.prepareStatement(deleteBanSQL)) {
                pstmt.setString(1, maBan);
                pstmt.executeUpdate();
            }
        } finally {
            if (con != null) {
                Database.getInstance().disconnect(); // Đảm bảo ngắt kết nối
            }
        }
    }

    // Lấy số lượng bàn
    public int getSoLuongBan() throws SQLException {
        Database.getInstance();
        Connection con = Database.getConnection();
        String sql = "SELECT COUNT(MaBan) FROM Ban";
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        rs.next();
        return rs.getInt(1);
    }

    // Lấy danh sach bàn theo trạng thái
    public ArrayList<Ban> getDanhSachBanTheoTrangThai(TrangThaiBan trangThai) throws SQLException {
        ArrayList<Ban> dsBan = new ArrayList<>();
        Database.getInstance();
        Connection con = Database.getConnection();
        String sql = "select * from Ban where TrangThai = '" + trangThai + "'";
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        while (rs.next()) {
            String maBan = rs.getString(1);
            TrangThaiBan trangThaiBan = TrangThaiBan.valueOf(rs.getString(2));
            int soGhe = rs.getInt(3);
            dsBan.add(new Ban(maBan, trangThaiBan, soGhe));
        }
        return dsBan;
    }

    public void updateCTPhieu(String maPhieu, String maMon, int soluong) throws SQLException {
        Connection con = null;
        PreparedStatement statement = null;

        try {
            System.out.println(soluong);
            // Kết nối tới cơ sở dữ liệu
            con = Database.getConnection();

            // Câu lệnh SQL sử dụng dấu ? để tránh SQL Injection
            String sql = "UPDATE CTDM SET SoLuong = ? WHERE MaPDB = ? AND MaMon = ?";

            // Sử dụng PreparedStatement để tránh SQL Injection
            statement = con.prepareStatement(sql);

            // Thiết lập các tham số cho câu lệnh
            statement.setInt(1, soluong);          // Set giá trị cho SoLuong
            statement.setString(2, maPhieu);      // Set giá trị cho MaPDB
            statement.setString(3, maMon);        // Set giá trị cho MaMon

            // Thực thi câu lệnh update
            int rowsAffected = statement.executeUpdate();

            if (rowsAffected == 0) {
                System.out.println("Không tìm thấy bản ghi để cập nhật");
            } else {
                System.out.println("Cập nhật thành công");
            }
        } catch (SQLException e) {
            // Xử lý ngoại lệ
            e.printStackTrace();
        } finally {
            // Đảm bảo đóng các tài nguyên khi không cần thiết nữa
            if (statement != null) {
                statement.close();
            }
            if (con != null) {
                con.close();
            }
        }
    }


    public ArrayList<Ban> getAllBan_Sort() throws SQLException {
        ArrayList<Ban> dsBan = new ArrayList<>();
        Database.getInstance();
        Connection con = Database.getConnection();
        String sql = "SELECT * FROM Ban ORDER BY CAST(SUBSTRING(maBan, 2, LEN(maBan) - 1) AS INT) ASC;\n";

        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {
            String maBan = rs.getString(1);
            TrangThaiBan trangThai = TrangThaiBan.valueOf(rs.getString(2));
            int soCho = rs.getInt(3);

            dsBan.add(new Ban(maBan, trangThai, soCho));
        }
        return dsBan;
    }

    public Ban searchBan(String maBan) throws SQLException {
        Ban dsBan = new Ban();
        Database.getInstance();
        Connection con = Database.getConnection();

        // Sử dụng toán tử '=' thay vì 'LIKE'
        String sql = "SELECT * FROM Ban WHERE MaBan = ?";

        // Sử dụng PreparedStatement
        PreparedStatement statement = con.prepareStatement(sql);
        statement.setString(1, maBan); // Gán giá trị cho tham số '?'

        // Thực hiện truy vấn
        ResultSet rs = statement.executeQuery();

        // Duyệt kết quả trả về
        while (rs.next()) {
            String maBan1 = rs.getString(1);
            TrangThaiBan trangThai = TrangThaiBan.valueOf(rs.getString(2)); // Lấy trạng thái
            int soCho = rs.getInt(3); // Lấy số chỗ

            // Thêm vào danh sách bàn
           dsBan = new Ban(maBan1, trangThai, soCho);
        }

        // Đóng ResultSet và Statement
        rs.close();
        statement.close();

        return dsBan;
    }


}
