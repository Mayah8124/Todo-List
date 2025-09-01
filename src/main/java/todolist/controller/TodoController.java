package todolist.controller;

import java.util.List;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import todolist.entity.Todo;
import todolist.service.TodoService;

@RestController
@RequestMapping("/todos")
public class TodoController {
  private final TodoService service = new TodoService();

  @GetMapping
  public List<Todo> getAllTodos() {
    return service.getAll();
  }

  @GetMapping("/{id}")
  public Todo getTodoById(@PathVariable int id) {
    return service.getTodoById(id);
  }

  @PostMapping
  public void create(@RequestBody Todo todo) {
    service.postTodo(todo);
  }
}
