package todolist.service;

import todolist.entity.Todo;

import java.time.Instant;
import java.util.List;

public class TodoService {
    public List<Todo> getAll(){
        var eat = new Todo(
                1,
                "eat",
                "go to the restaurant" ,
                Instant.parse("2000-10-01T22:10:12Z"),
                Instant.parse("2000-10-01T22:40:12Z"),
                false);
        var run = new Todo(
                2,
                "run",
                "go for a walk",
                Instant.parse("2020-10-01T22:10:12Z"),
                Instant.parse("2020-10-01T22:20:12Z"),
                true
        );
        return List.of(
                eat,run
        );
    }
}
