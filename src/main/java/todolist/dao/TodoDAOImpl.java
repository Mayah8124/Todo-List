package todolist.dao;

import java.sql.*;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
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
        }
      }
    } catch (SQLException e) {
      throw new RuntimeException("Error finding the todo with id of " + id);
    }
    return null;
  }

  @Override
  public void save(Todo todo) {
    String sql =
        "INSERT INTO todo (title ,description, start_datetime , end_datetime , done) VALUES (?, ?, ?, ?, ?)";
    try (Connection conn = DBConnection.getConnection();
        PreparedStatement stmt = conn.prepareStatement(sql)) {
      stmt.setString(1, todo.getTitle());
      stmt.setString(2, todo.getDescription());
      if (todo.getStartDatetime() != null) {
        stmt.setTimestamp(3, toTimestamp(todo.getStartDatetime()));
      } else {
        stmt.setTimestamp(3, null);
      }
      if (todo.getEndDatetime() != null) {
        stmt.setTimestamp(4, toTimestamp(todo.getEndDatetime()));
      } else {
        stmt.setTimestamp(4, null);
      }
      stmt.setBoolean(5, todo.isDone());
      stmt.executeUpdate();
    } catch (SQLException e) {
      throw new RuntimeException("Error creating new todo");
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
