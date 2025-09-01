package todolist.dao;

import todolist.entity.Todo;

import java.util.List;

public interface TodoDAO {
    List<Todo> findAll();
    Todo findById(int id);
    void save(Todo todo);
}
