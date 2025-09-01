package todolist.repository;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import todolist.entity.Todo;
import org.springframework.stereotype.Repository;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
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

    public Todo create(Todo todo) {
        String sql = "INSERT INTO todos (tittle ,description, start_date_time , end_date_time , is_done) VALUES (?, ? , ? , ? , ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();

        jdbcTemplate.update(connection -> {
            PreparedStatement ps = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, todo.getDescription());
            ps.setTimestamp(2, Timestamp.from(todo.getStartDateTime()));
            return ps;
        }, keyHolder);

        int id = Math.toIntExact(keyHolder.getKey().longValue());

        return new Todo(
                id,
                todo.getTitle(),
                todo.getDescription(),
                todo.getStartDateTime(),
                todo.getEndDateTime(),
                todo.isDone()
        );
    }
}

