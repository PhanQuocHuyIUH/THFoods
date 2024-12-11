package DAO;

import DB.Database;
import Entity.ChiTietHoaDon;
import Entity.HoaDon;

import java.sql.*;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class HoaDon_Dao
{
    public List<HoaDon> getAllHD() throws SQLException {
        List<HoaDon> dsHD = new ArrayList<HoaDon>();
        Database.getInstance().connect();
        Connection con = Database.getConnection();
        String sql = "select * from HoaDon";

        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next()) {
            String maHD = rs.getString(1);

            // Lấy giá trị ngày tạo và chuyển đổi sang LocalDateTime
            Timestamp timestamp = rs.getTimestamp(2);
            LocalDateTime ngayTao = timestamp.toLocalDateTime();

            List<ChiTietHoaDon> dsCTHD = new ChiTietHoaDon_Dao().getCTHDByMaHD(maHD);

            dsHD.add(new HoaDon(maHD, ngayTao, dsCTHD));
        }

        return dsHD;
    }

    public double getTongTienHD(HoaDon HD) throws SQLException
    {
        double ThanhTien = 0;
        List<ChiTietHoaDon> dsCTHD = HD.getChiTietHoaDon();
        for (ChiTietHoaDon cthd : dsCTHD)
        {
            ThanhTien += (cthd.getDonGia()) * cthd.getSoLuong();
        }
        return ThanhTien;
    }

    // Hàm lấy doanh thu trong 7 ngày gần nhất (LineChart) update dung LocalDate de luu ngay
    public Map<String, Double> getRevenueLast7Days() throws SQLException {
        Map<String, Double> revenueLast7Days = new HashMap<>();
        List<HoaDon> dsHD = new ArrayList<HoaDon>();
        try {
            dsHD = getAllHD();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        for (HoaDon hd : dsHD) {
            LocalDateTime ngayTao = hd.getNgayTao();
            LocalDateTime today = LocalDateTime.now();
//            LocalDateTime today = LocalDateTime.of(2024, 10, 16, 0, 0, 0);
            long diffDays = ChronoUnit.DAYS.between(ngayTao.toLocalDate(), today.toLocalDate());
            System.out.println(diffDays);
            if (diffDays <= 7) {
                String date = ngayTao.getYear() + "-" + ngayTao.getMonthValue() + "-" + ngayTao.getDayOfMonth();
                if (revenueLast7Days.containsKey(date)) {
                    revenueLast7Days.put(date, revenueLast7Days.get(date) + getTongTienHD(hd));
                } else {
                    revenueLast7Days.put(date, getTongTienHD(hd));
                }
            }
        }

        return revenueLast7Days;
    }

    // Hàm lấy doanh thu theo tháng (BarChart) update dung LocalDate de luu ngay
    public Map<String, Double> getRevenueByMonth() throws SQLException {
        Map<String, Double> revenueByMonth = new HashMap<>();
        List<HoaDon> dsHD = new ArrayList<HoaDon>();
        try {
            dsHD = getAllHD();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        for (HoaDon hd : dsHD) {
            LocalDateTime ngayTao = hd.getNgayTao();
            String month = ngayTao.getYear() + "-" + ngayTao.getMonthValue();
            if (revenueByMonth.containsKey(month)) {
                revenueByMonth.put(month, revenueByMonth.get(month) + getTongTienHD(hd));
            } else {
                revenueByMonth.put(month, getTongTienHD(hd));
            }
        }

        // Chỉ lấy 8 tháng gần nhất theo thời gian thực
        Map<String, Double> sortedRevenueByMonth = new LinkedHashMap<>();
        revenueByMonth.entrySet().stream().sorted(Map.Entry.comparingByKey(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortedRevenueByMonth.put(x.getKey(), x.getValue()));

        int count = 0;
        Map<String, Double> revenueBy8Months = new LinkedHashMap<>();
        for (Map.Entry<String, Double> entry : sortedRevenueByMonth.entrySet()) {
            if (count == 8) {
                break;
            }
            revenueBy8Months.put(entry.getKey(), entry.getValue());
            count++;
        }

        return revenueBy8Months;
    }

    public void createHD(HoaDon hd) throws SQLException {
        Database.getInstance().connect();
        Connection con = Database.getConnection();

        // Chuyển đổi LocalDateTime thành Timestamp
        java.sql.Timestamp ngayTaoTimestamp = java.sql.Timestamp.valueOf(hd.getNgayTao());

        // Câu lệnh SQL để chèn hóa đơn vào cơ sở dữ liệu (sử dụng PreparedStatement để tránh lỗi SQL Injection và lỗi kiểu dữ liệu)
        String sql = "INSERT INTO HoaDon (MaHD, NgayTao) VALUES (?, ?)";

        PreparedStatement statement = con.prepareStatement(sql);
        statement.setString(1, hd.getMaHD());
        statement.setTimestamp(2, ngayTaoTimestamp);  // Chèn giá trị Timestamp trực tiếp vào câu lệnh SQL

        // Thực thi câu lệnh SQL
        statement.executeUpdate();
    }


    //tạo chi tiết hóa đơn
    public void createCTHD(ChiTietHoaDon cthd) throws SQLException {
        Database.getInstance().connect();
        Connection con = Database.getConnection();

        // Câu lệnh SQL chèn dữ liệu vào bảng CTHD (chỉ rõ tên cột)
        String sql = "INSERT INTO CTHD (MaHD, MonAn, SoLuong, DonGia) VALUES (?, ?, ?, ?)";

        PreparedStatement statement = con.prepareStatement(sql);

        // Gán giá trị cho các tham số trong câu lệnh SQL
        statement.setString(1, cthd.getMaHD());  // MaHD
        statement.setString(2, cthd.getMonAn()); // MonAn
        statement.setInt(3, cthd.getSoLuong());  // SoLuong
        statement.setDouble(4, cthd.getDonGia()); // DonGia

        // Thực thi câu lệnh SQL
        statement.executeUpdate();
    }


//     hàm getBestSellingDishes trả về danh sách 6 các món ăn bán chạy nhất trong 30 ngày qua lấy tên món
    public Map<String, Integer> getBestSellingDishes() throws SQLException {
        Map<String, Integer> bestSellingDishes = new HashMap<>();
        List<HoaDon> dsHD = new ArrayList<HoaDon>();
        try {
            dsHD = getAllHD();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        for (HoaDon hd : dsHD) {
            LocalDateTime ngayTao = hd.getNgayTao();
            LocalDateTime today = LocalDateTime.now();
            long diffDays = ChronoUnit.DAYS.between(ngayTao.toLocalDate(), today.toLocalDate());
            if (diffDays <= 30) {
                List<ChiTietHoaDon> dsCTHD = hd.getChiTietHoaDon();
                for (ChiTietHoaDon cthd : dsCTHD) {
                    String tenMon = cthd.getMonAn();
                    if (bestSellingDishes.containsKey(tenMon)) {
                        bestSellingDishes.put(tenMon, bestSellingDishes.get(tenMon) + cthd.getSoLuong());
                    } else {
                        bestSellingDishes.put(tenMon, cthd.getSoLuong());
                    }
                }
            }
        }

        Map<String, Integer> sortedBestSellingDishes = new LinkedHashMap<>();
        bestSellingDishes.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .forEachOrdered(x -> sortedBestSellingDishes.put(x.getKey(), x.getValue()));

        Map<String, Integer> best6SellingDishes = new LinkedHashMap<>();
        int count = 0;
        for (Map.Entry<String, Integer> entry : sortedBestSellingDishes.entrySet()) {
            if (count == 6) {
                break;
            }
            best6SellingDishes.put(entry.getKey(), entry.getValue());
            count++;
        }

        return best6SellingDishes;
    }

    //hàm getWorstSellingDishes trả về danh sách 6 các món ăn bán chậm nhất trong 30 ngày qua lấy tên món
    public Map<String, Integer> getWorstSellingDishes() throws SQLException {
        Map<String, Integer> worstSellingDishes = new HashMap<>();
        List<HoaDon> dsHD = new ArrayList<HoaDon>();
        try {
            dsHD = getAllHD();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        for (HoaDon hd : dsHD) {
            LocalDateTime ngayTao = hd.getNgayTao();
            LocalDateTime today = LocalDateTime.now();
            long diffDays = ChronoUnit.DAYS.between(ngayTao.toLocalDate(), today.toLocalDate());
            if (diffDays <= 30) {
                List<ChiTietHoaDon> dsCTHD = hd.getChiTietHoaDon();
                for (ChiTietHoaDon cthd : dsCTHD) {
                    String tenMon = cthd.getMonAn();
                    if (worstSellingDishes.containsKey(tenMon)) {
                        worstSellingDishes.put(tenMon, worstSellingDishes.get(tenMon) + cthd.getSoLuong());
                    } else {
                        worstSellingDishes.put(tenMon, cthd.getSoLuong());
                    }
                }
            }
        }

        Map<String, Integer> sortedWorstSellingDishes = new LinkedHashMap<>();
        worstSellingDishes.entrySet().stream().sorted(Map.Entry.comparingByValue())
                .forEachOrdered(x -> sortedWorstSellingDishes.put(x.getKey(), x.getValue()));

        Map<String, Integer> worst6SellingDishes = new LinkedHashMap<>();
        int count = 0;
        for (Map.Entry<String, Integer> entry : sortedWorstSellingDishes.entrySet()) {
            if (count == 6) {
                break;
            }
            worst6SellingDishes.put(entry.getKey(), entry.getValue());
            count++;
        }

        return worst6SellingDishes;
    }
}
