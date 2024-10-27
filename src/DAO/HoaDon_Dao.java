package DAO;

import DB.Database;
import Entity.ChiTietHoaDon;
import Entity.HoaDon;

import java.sql.*;
import java.util.*;
import java.util.Date;

public class HoaDon_Dao
{
    public List<HoaDon> getAllHD() throws SQLException
    {
        List<HoaDon> dsHD = new ArrayList<HoaDon>();
        Database.getInstance().connect();
        Connection con = Database.getConnection();
        String sql = "select * from HoaDon";

        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while (rs.next())
        {
            String maHD = rs.getString(1);
            Date ngayTao = rs.getDate(2);
            List<ChiTietHoaDon> dsCTHD = new ChiTietHoaDon_Dao().getCTHDByMaHD(maHD);

            dsHD.add(new HoaDon(maHD, ngayTao, dsCTHD));
        }

        return dsHD;
    }

    public double getTongTienHDByMAHD(String maHD) throws SQLException
    {
        double ThanhTien = 0;
        List<ChiTietHoaDon> dsCTHD = new ChiTietHoaDon_Dao().getCTHDByMaHD(maHD);
        for (ChiTietHoaDon cthd : dsCTHD)
        {
            ThanhTien += (new MonAn_Dao().getGiaMonAn(cthd.getMaMon()) * cthd.getSoLuong());
        }
        return ThanhTien;
    }

    public double getTongTienHD(HoaDon HD) throws SQLException
    {
        double ThanhTien = 0;
        List<ChiTietHoaDon> dsCTHD = HD.getChiTietHoaDon();
        for (ChiTietHoaDon cthd : dsCTHD)
        {
            ThanhTien += (new MonAn_Dao().getGiaMonAn(cthd.getMaMon()) * cthd.getSoLuong());
        }
        return ThanhTien;
    }

    // Hàm lấy doanh thu theo loại món ăn (PieChart)
    public Map<String, Double> getRevenueByDishType()  {
        Map<String, Double> revenueByDishType = new HashMap<>();
        List<HoaDon> dsHD = new ArrayList<HoaDon>();
        try {
            dsHD = getAllHD();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        for (HoaDon hd : dsHD) {
            List<ChiTietHoaDon> dsCTHD = hd.getChiTietHoaDon();
            for (ChiTietHoaDon cthd : dsCTHD) {
                String maMon = cthd.getMaMon();
                double giaMon = 0;
                try {
                    giaMon = new MonAn_Dao().getGiaMonAn(maMon);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                String loaiMon = "";
                try {
                    loaiMon = new MonAn_Dao().getLoaiMonAn(maMon);
                } catch (SQLException throwables) {
                    throwables.printStackTrace();
                }
                if (revenueByDishType.containsKey(loaiMon)) {
                    revenueByDishType.put(loaiMon, revenueByDishType.get(loaiMon) + giaMon * cthd.getSoLuong());
                } else {
                    revenueByDishType.put(loaiMon, giaMon * cthd.getSoLuong());
                }
            }
        }
        return revenueByDishType;
    }

    // Hàm lấy doanh thu trong 7 ngày gần nhất (LineChart)
    public Map<String, Double> getRevenueLast7Days() throws SQLException {
        Map<String, Double> revenueLast7Days = new HashMap<>();
        List<HoaDon> dsHD = new ArrayList<HoaDon>();
        try {
            dsHD = getAllHD();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        for (HoaDon hd : dsHD) {
            Date ngayTao = new Date(hd.getNgayTao().getYear()+1900, hd.getNgayTao().getMonth()+1, hd.getNgayTao().getDate());
            Date today = new Date(2024, 10, 16);
            long diff = today.getTime() - ngayTao.getTime();
            long diffDays = diff / (24 * 60 * 60 * 1000);
            if (diffDays <= 7) {
                String date = (ngayTao.getYear()) + "-" + (ngayTao.getMonth()) + "-" + ngayTao.getDate();
                if (revenueLast7Days.containsKey(date)) {
                    revenueLast7Days.put(date, revenueLast7Days.get(date) + getTongTienHD(hd));
                } else {
                    revenueLast7Days.put(date, getTongTienHD(hd));
                }
            }
        }

        return revenueLast7Days;
    }

    // Hàm lấy doanh thu theo tháng (BarChart)
    public Map<String, Double> getRevenueByMonth() throws SQLException {
        Map<String, Double> revenueByMonth = new HashMap<>();
        List<HoaDon> dsHD = new ArrayList<HoaDon>();
        try {
            dsHD = getAllHD();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        for (HoaDon hd : dsHD) {
            Date ngayTao = hd.getNgayTao();
            String month = (ngayTao.getYear()+1900) + "-" + (ngayTao.getMonth()+1);
            if (revenueByMonth.containsKey(month)) {
                revenueByMonth.put(month, revenueByMonth.get(month) + getTongTienHD(hd));
            } else {
                revenueByMonth.put(month, getTongTienHD(hd));
            }
        }

        return revenueByMonth;

        // Chỉ lấy 6 tháng gần nhất theo thời gian thực
//        Map<String, Double> revenueByMonthLast6 = new HashMap<>();
//        List<String> keys = new ArrayList<>(revenueByMonth.keySet());
//        Collections.sort(keys);
//        for (int i = keys.size() - 1; i >= keys.size() - 6; i--) {
//            revenueByMonthLast6.put(keys.get(i), revenueByMonth.get(keys.get(i)));
//        }
//
//        return revenueByMonthLast6;
    }

    public void createHD(HoaDon hd) throws SQLException
    {
        Database.getInstance().connect();
        Connection con = Database.getConnection();
        String sql = "insert into HoaDon values('" + hd.getMaHD() + "','" + hd.getNgayTao() + "')";
        Statement statement = con.createStatement();
        statement.executeUpdate(sql);
    }
    //tạo chi tiết hóa đơn
    public void createCTHD(ChiTietHoaDon cthd) throws SQLException
    {
        Database.getInstance().connect();
        Connection con = Database.getConnection();
        String sql = "insert into CTHD values('" + cthd.getMaHD() + "','"+ cthd.getMaMon() +"','" + cthd.getSoLuong() + "')";
        Statement statement = con.createStatement();
        statement.executeUpdate(sql);
    }

    // hàm getBestSellingDishes trả về danh sách 6 các món ăn bán chạy nhất trong 30 ngày qua lấy tên món
    public Map<String, Integer> getBestSellingDishes() throws SQLException {
        Map<String, Integer> bestSellingDishes = new HashMap<>();
        List<HoaDon> dsHD = new ArrayList<HoaDon>();
        try {
            dsHD = getAllHD();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        for (HoaDon hd : dsHD) {
            Date ngayTao = new Date(hd.getNgayTao().getYear()+1900, hd.getNgayTao().getMonth()+1, hd.getNgayTao().getDate());
            Date today = new Date(2024, 10, 16);
            long diff = today.getTime() - ngayTao.getTime();
            long diffDays = diff / (24 * 60 * 60 * 1000);
            if (diffDays <= 30) {
                List<ChiTietHoaDon> dsCTHD = hd.getChiTietHoaDon();
                for (ChiTietHoaDon cthd : dsCTHD) {
                    String maMon = cthd.getMaMon();
                    String tenMon = new MonAn_Dao().getTenMon(maMon);
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

    // hàm getWorstSellingDishes trả về danh sách 6 các món ăn bán chậm nhất trong 30 ngày qua lấy tên món
    public Map<String, Integer> getWorstSellingDishes() throws SQLException {
        Map<String, Integer> worstSellingDishes = new HashMap<>();
        List<HoaDon> dsHD = new ArrayList<HoaDon>();
        try {
            dsHD = getAllHD();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }

        for (HoaDon hd : dsHD) {
            Date ngayTao = new Date(hd.getNgayTao().getYear()+1900, hd.getNgayTao().getMonth()+1, hd.getNgayTao().getDate());
            Date today = new Date(2024, 10, 16);
            long diff = today.getTime() - ngayTao.getTime();
            long diffDays = diff / (24 * 60 * 60 * 1000);
            if (diffDays <= 30) {
                List<ChiTietHoaDon> dsCTHD = hd.getChiTietHoaDon();
                for (ChiTietHoaDon cthd : dsCTHD) {
                    String maMon = cthd.getMaMon();
                    String tenMon = new MonAn_Dao().getTenMon(maMon);
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
