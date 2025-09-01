package todolist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public static final String DB_URL = "jdbc:postgresql://localhost:5433/springboot_todolist_two";
    public static final String USER = "postgres";
    public static final String PASSWORD = "1234";

    public static Connection getConnection() throws SQLException{
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }
}
