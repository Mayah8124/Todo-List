package todolist.service;

import todolist.entity.Todo;
import java.util.List;
import org.springframework.stereotype.Service;
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

    public Todo getTodoById(int id) {
        return repository.findById(id);
    }

    public Todo postTodo(Todo todo) {
        return repository.save(todo);
    }

}
