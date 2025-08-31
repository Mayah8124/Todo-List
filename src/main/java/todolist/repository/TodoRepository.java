package todolist.repository;

import todolist.entity.Todo;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Repository
public class TodoRepository {
    private final String url = "jdbc:postgresql://localhost/5432/todo";
    private final String user = "user";
    private final String password = "password";

    public Connection connect() throws SQLException {
        return DriverManager.getConnection(url, user, password);
    }
}
