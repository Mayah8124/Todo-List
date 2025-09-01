package todolist.service;

import java.util.List;
import org.springframework.stereotype.Service;
import todolist.dao.TodoDAOImpl;
import todolist.entity.Todo;

@Service
public class TodoService {
  private TodoDAOImpl todoDAO;

  public TodoService() {
    this.todoDAO = new TodoDAOImpl();
  }

  public List<Todo> getAll() {
    return todoDAO.findAll();
  }

  public Todo getTodoById(int id) {
    return todoDAO.findById(id);
  }

  public void postTodo(Todo todo) {
    if (todo.getTitle() == null || todo.getTitle().isEmpty()) {
      throw new IllegalArgumentException("Title field cannot be empty");
    }
    todoDAO.save(todo);
  }
}
