package DBConnect;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    String dbURL;
    String dbUser;
    String dbPass;
    Connection con;
   public  Connection connect() throws ClassNotFoundException, SQLException {
        Class.forName("com.mysql.cj.jdbc.Driver");
        dbURL = "jdbc:mysql://localhost:3306/bms";
        dbUser = "root";
        dbPass = "";
        con = DriverManager.getConnection(dbURL, dbUser, dbPass);
        return con;
    }
}