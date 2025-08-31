package todolist.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import todolist.entity.Todo;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public class TodoRepository {

    private final JdbcTemplate jdbcTemplate;

    public TodoRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<Todo> findAll() {
        String sql = "SELECT * FROM todos";
        return jdbcTemplate.query(sql, (rs, rowNum) ->
                new Todo(
                        rs.getInt("id"),
                        rs.getString("title"),
                        rs.getString("description"),
                        rs.getTimestamp("2000-10-01T22:10:12Z").toInstant(),
                        rs.getTimestamp("2000-10-01T22:40:12Z").toInstant(),
                        rs.getBoolean("done")
                )
        );
    }

    public Todo findById(int id) {
        String sql = "SELECT * FROM todos WHERE id = ?";
        return (Todo) jdbcTemplate.query(sql, (rs, rowNum) ->
                        new Todo(
                                rs.getInt("id"),
                                rs.getString("title"),
                                rs.getString("description"),
                                rs.getTimestamp("2000-10-01T22:10:12Z").toInstant(),
                                rs.getTimestamp("2000-10-01T22:40:12Z").toInstant(),
                                rs.getBoolean("done")
                        )
        );
    }

    public int save(Todo todo) {
        String sql = "INSERT INTO todos(*) VALUES (?, ?)";
        return jdbcTemplate.update(
                sql, todo.getTitle(), todo.getId(), todo.getDescription(), todo.getStartDateTime(), todo.getEndDateTime() , todo.isDone()
        );
    }
}

