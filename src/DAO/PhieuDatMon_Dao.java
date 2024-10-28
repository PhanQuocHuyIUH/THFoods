package DAO;

import DB.Database;
import Entity.Ban;
import Entity.NhanVien;
import Entity.PhieuDatMon;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class PhieuDatMon_Dao{
    public PhieuDatMon_Dao(){
    }

    public ArrayList<PhieuDatMon> getAllPhieuDatMon() throws Exception{
        ArrayList<PhieuDatMon> dsPhieuDatMon = new ArrayList<>();
        Database.getInstance();
        Connection con = Database.getConnection();
        String sql = "select * from PhieuDatMon";

        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while(rs.next()){
            String maPDB = rs.getString(1);
            String ngayDat = rs.getString(2);
            String ghiChu = rs.getString(3);
            Ban maBan = new Ban(rs.getString(4), null, 0);
            NhanVien maNV = new NhanVien(rs.getString(5), null, null, null, null, null);

            dsPhieuDatMon.add(new PhieuDatMon(maPDB, ngayDat, ghiChu, maBan, maNV));
        }
        return dsPhieuDatMon;
    }

    //thêm phiếu đặt món vào dâtabase
    public void addPhieuDatMon(PhieuDatMon phieuDatMon) throws Exception{
        Database.getInstance();
        Connection con = Database.getConnection();
        String sql = "insert into PhieuDatMon values('"+phieuDatMon.getMaPDB()+"', '"+phieuDatMon.getNgayDat()+"', '"+phieuDatMon.getGhiChu()+"', '"+phieuDatMon.getMaBan()+"', '"+phieuDatMon.getMaNV()+"')";
        Statement statement = con.createStatement();
        statement.executeUpdate(sql);
    }

    //Lấy phiếu đặt món theo mã phiếu
    public PhieuDatMon getPhieuDatMonByMaPDB(String maPDB) throws SQLException {
        Database.getInstance();
        Connection con = Database.getConnection();
        String sql = "select * from PhieuDatMon where MaPDB = '"+maPDB+"'";
        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(sql);
        rs.next();
        String ngayDat = rs.getString(2);
        String ghiChu = rs.getString(3);
        Ban maBan = new Ban(rs.getString(4), null, 0);
        NhanVien maNV = new NhanVien(rs.getString(5), null, null, null, null, null);
        return new PhieuDatMon(maPDB, ngayDat, ghiChu, maBan, maNV);
    }

}
