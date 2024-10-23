package DAO;

import DB.Database;
import Entity.Ban;
import Entity.TrangThaiBan;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;


public class Ban_Dao {
    public Ban_Dao() {
    }

    //lấy danh sách bàn
    public ArrayList<Ban> getAllBan() throws Exception{
        ArrayList<Ban> dsBan = new ArrayList<>();
        Database.getInstance();
        Connection con = Database.getConnection();
        String sql = "select * from Ban";

        Statement statement = con.createStatement();
        ResultSet rs = statement.executeQuery(sql);

        while(rs.next()){
            String maBan = rs.getString(1);
            TrangThaiBan trangThai = TrangThaiBan.valueOf(rs.getString(2));
            int soCho = rs.getInt(3);

            dsBan.add(new Ban(maBan, trangThai, soCho));
        }
        return dsBan;
    }

    //update trạng thái bàn
    public void updateTrangThaiBan(Ban ban) throws Exception{
        Database.getInstance();
        Connection con = Database.getConnection();
        String sql = "update Ban set trangThai = '"+ban.getTrangThai()+"' where MaBan = '"+ban.getMaBan()+"'";
        Statement statement = con.createStatement();
        statement.executeUpdate(sql);
    }
}
