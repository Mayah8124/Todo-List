package todolist.service;

import todolist.entity.Todo;

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

}
