package todolist.controller;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import todolist.entity.Todo;
import todolist.service.TodoService;

import javax.management.openmbean.CompositeData;
import java.util.List;

@RestController
public class TodoController {
    private TodoService service = new TodoService();

    @GetMapping("/todos")
    public List<Todo> getAllTodos(){
        return service.getAll();
    }

}
