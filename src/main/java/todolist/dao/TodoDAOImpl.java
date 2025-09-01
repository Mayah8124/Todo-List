package todolist.dao;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;
import todolist.entity.Todo;

public class TodoDAOImpl implements TodoDAO {

  @Override
  public List<Todo> findAll() {
    String sql = "SELECT * FROM todo";
    List<Todo> todolist = new ArrayList<>();
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql);
        ResultSet rs = stmt.executeQuery()) {
      while (rs.next()) {
        todolist.add(mapRow(rs));
      }
    } catch (SQLException e) {
      throw new RuntimeException("Error fetching todos");
    }
    return todolist;
  }

  @Override
  public Todo findById(int id) {
    String sql = "SELECT * FROM todo WHERE id = ?";
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setInt(1, id);
      try (ResultSet rs = stmt.executeQuery()) {
        if (rs.next()) {
          return mapRow(rs);
        } else {
          throw new ResponseStatusException(
              HttpStatus.NOT_FOUND, "Todo with id " + id + " not found");
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException("Error finding the todo with id of " + id);
    }
  }

  @Override
  public void save(Todo todo) {
    String sql;
    boolean hasStart = todo.getStartDatetime() != null;
    boolean hasEnd = todo.getEndDatetime() != null;

    if (hasStart && hasEnd) {
      sql =
          "INSERT INTO todo (title, description, start_datetime, end_datetime, done) VALUES (?, ?, ?, ?, ?)";
    } else if (hasStart) {
      sql = "INSERT INTO todo (title, description, start_datetime, done) VALUES (?, ?, ?, ?)";
    } else if (hasEnd) {
      sql = "INSERT INTO todo (title, description, end_datetime, done) VALUES (?, ?, ?, ?)";
    } else {
      sql = "INSERT INTO todo (title, description, done) VALUES (?, ?, ?)";
    }

    try (Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {

      int index = 1;
      stmt.setString(index++, todo.getTitle());
      stmt.setString(index++, todo.getDescription());

      if (hasStart) {
        stmt.setTimestamp(index++, toTimestamp(todo.getStartDatetime()));
      }
      if (hasEnd) {
        stmt.setTimestamp(index++, toTimestamp(todo.getEndDatetime()));
      }

      stmt.setBoolean(index, todo.isDone());
      stmt.executeUpdate();

    } catch (SQLException e) {
      throw new RuntimeException("Error creating new todo", e);
    }
  }

  public Todo mapRow(ResultSet rs) throws SQLException {
    return new Todo(
        rs.getInt("id"),
        rs.getString("title"),
        rs.getString("description"),
        toInstant(rs.getTimestamp("start_datetime")),
        toInstant(rs.getTimestamp("end_datetime")),
        rs.getBoolean("done"));
  }

  private Instant toInstant(Timestamp ts) {
    return ts == null ? null : ts.toInstant();
  }

  private Timestamp toTimestamp(Instant instant) {
    return instant == null ? null : Timestamp.from(instant);
  }
}
