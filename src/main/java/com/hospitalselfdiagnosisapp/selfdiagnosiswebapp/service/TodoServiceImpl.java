package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.service;

import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.Todo;
import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.repository.TodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TodoServiceImpl implements TodoService{
    @Autowired
    TodoRepository todoRepository;
    @Override
    public List<Todo> allTodo() {
        return todoRepository.findAll();
    }

    @Override
    public Todo searchTodo(String activity) {
        return todoRepository.findByActivity(activity);
    }

    @Override
    public Todo updateTodo(Long id, Todo todo) {
        return null;
    }

    @Override
    public void deleteTodo(Long id) {
        todoRepository.deleteById(id);

    }

    @Override
    public void save(Todo todo) {

        todoRepository.save(todo);
    }

    @Override
    public List<Todo> userTodos(String user) {
        return (List<Todo>) todoRepository.findByUser(user);
    }
}
