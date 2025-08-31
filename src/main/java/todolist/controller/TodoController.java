package todolist.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import todolist.entity.Todo;
import todolist.service.TodoService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/todos")
public class TodoController {
    private final TodoService service;

    public TodoController(TodoService service) {
        this.service = service;
    }

    @GetMapping
    public List<Todo> getAllTodos() {
        return service.getAll();
    }

    @PostMapping
    public Todo create(@RequestBody Todo todo){
        return service.postTodo(todo);
    }
    @DeleteMapping("/{id}")
    public void delete(@PathVariable int id) {
        service.deleteTodo(id);
    }

}