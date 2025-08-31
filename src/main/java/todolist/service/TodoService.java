package todolist.service;

import todolist.entity.Todo;
import

import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import todolist.repository.TodoRepository;

@Service
public class TodoService {

    private final TodoRepository repository;

    public TodoService(TodoRepository repository) {
        this.repository = repository;
    }
    public List<Todo> getAll() {
        return repository.findAll();
    }

    public Todo postTodo(Todo todo) {
        return repository.save(todo);
    }

    public void deleteTodo(int id) {
        Todo todo = repository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Todo not found"));
        repository.delete(todo);
    }
}

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
