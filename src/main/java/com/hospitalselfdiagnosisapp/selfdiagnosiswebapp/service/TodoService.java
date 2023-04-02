package com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.service;

import com.hospitalselfdiagnosisapp.selfdiagnosiswebapp.model.Todo;

import java.util.List;

public interface TodoService {
    List<Todo> allTodo();
    Todo searchTodo(String activity);
    Todo updateTodo(Long id, Todo todo);
    void deleteTodo(Long id);
    void save(Todo todo);

    List<Todo> userTodos(String user);
}
