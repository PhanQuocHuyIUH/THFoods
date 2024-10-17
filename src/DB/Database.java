package DB;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Database {
    public static Connection con = null;
    public static Database instance = new Database();

    public static Database getInstance() {
        return instance;
    }

    public void connect() throws SQLException {
        String url = "jdbc:sqlserver://localhost:1433;databasename=THFoods;trustServerCertificate=true";
        String user = "sa";
        String pw = "sapassword";
        con =  DriverManager.getConnection(url,user,pw);
    }

    public void disconnect() {
        if(con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static Connection getConnection() {
        return con;
    }
}
