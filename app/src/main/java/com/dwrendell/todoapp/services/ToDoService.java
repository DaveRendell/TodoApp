package com.dwrendell.todoapp.services;

import com.dwrendell.todoapp.models.TodoItem;

import java.util.List;

public interface ToDoService {
    List<TodoItem> getTodos();
    void updateTodoPosition(int from, int to);

    void toggleDone(int id);

    void createTodo(String description);

    void editTodo(int id, String description);

    void removeTodo(int id);

    int getPositionForId(int id);

    void deleteAll();
}
