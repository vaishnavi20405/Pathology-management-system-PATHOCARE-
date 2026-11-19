package patho;

import java.sql.Connection;
import java.sql.DriverManager;

public class DBConnection {

    // ✅ Database configuration for your user
    private static final String URL = "jdbc:mysql://localhost:3306/pathocare?useSSL=false&allowPublicKeyRetrieval=true&serverTimezone=UTC";
    private static final String USER = "root";       // same user
    private static final String PASS = "";     // same password

    public static Connection getConnection() throws Exception {
        // ✅ Updated driver for MySQL 8.x / Connector-J 9.x
        Class.forName("com.mysql.jdbc.Driver");

        // ✅ Establish connection
        return DriverManager.getConnection(URL, USER, PASS);
    }
}
