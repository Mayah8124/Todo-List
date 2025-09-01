package todolist.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {

    public final String DB_URL = "jdbc:postgresql://localhost:5433/todolist";
    public final String USER = "postgres";
    public final String PASSWORD = "1234";

    public Connection getConnetion() throws SQLException{
        return DriverManager.getConnection(DB_URL, USER, PASSWORD);
    }
}
