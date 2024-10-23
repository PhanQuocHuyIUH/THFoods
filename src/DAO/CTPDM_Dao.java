package DAO;

import DB.Database;
import Entity.ChiTietDatMon;
import java.sql.Connection;
import java.sql.Statement;



public class CTPDM_Dao {
    public CTPDM_Dao() {
    }

    // thêm chi tiết phiếu đặt
    public void addCTPDM(ChiTietDatMon ctpdm) throws Exception{
        Database.getInstance();
        Connection con = Database.getConnection();
        String sql = "insert into CTDM values('"+ctpdm.getMaPDB()+"', '"+ctpdm.getMaMon()+"', '"+ctpdm.getSoLuong()+"')";
        Statement statement = con.createStatement();
        statement.executeUpdate(sql);
    }
}
