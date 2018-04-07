package com.dwrendell.todoapp.services;

import com.dwrendell.todoapp.models.TodoItem;
import com.dwrendell.todoapp.models.TodoItemBuilder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class FileTodoService implements ToDoService {
    private File file;
    private ArrayList<TodoItem> todos = new ArrayList<>();
    private ObjectMapper mapper = new ObjectMapper();

    public FileTodoService(File file) {
        this.file = file;
        readFromFile();
    }

    @Override
    public List<TodoItem> getTodos() {
        readFromFile();
        return todos;
    }

    @Override
    public void updateTodoPosition(int from, int to) {
        readFromFile();
        ArrayList<TodoItem> newTodos = new ArrayList<>(todos);
        TodoItem item = newTodos.remove(from);
        newTodos.add(to, item);
        todos = newTodos;
        writeToFile();
    }

    @Override
    public void toggleDone(int id) {
        readFromFile();
        getTodo(id).setDone(!getTodo(id).isDone());
        writeToFile();
    }

    @Override
    public void createTodo(String description) {
        readFromFile();
        todos.add(new TodoItemBuilder()
                .setDescription(description)
                .setId(getNextId())
                .setDate(new Date())
                .createToDoItem());
        writeToFile();
    }

    @Override
    public void editTodo(int id, String description) {
        readFromFile();
        getTodo(id).setDescription(description);
        writeToFile();
    }

    @Override
    public void removeTodo(int id) {
        readFromFile();
        todos.remove(getTodo(id));
        writeToFile();
    }

    private int getNextId() {
        int id = 0;
        for (TodoItem todo : todos) {
            if (todo.getId() > id) {
                id = todo.getId();
            }
        }
        return id + 1;
    }

    private void writeToFile() {
        //TODO delete file if exists?
        try {
            mapper.writeValue(file, todos);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void readFromFile() {
        try {
            todos = mapper.readValue(file, new TypeReference<List<TodoItem>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private TodoItem getTodo(int id) {
        for (TodoItem todo : todos) {
            if (todo.getId() == id) {
                return todo;
            }
        }
        throw new RuntimeException("No To Do found with id " + id);
    }

    @Override
    public int getPositionForId(int id) {
        for (int i = 0; i < todos.size(); i++ ) {
            if (todos.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    @Override
    public void deleteAll() {
        todos = new ArrayList<>();
        writeToFile();
    }
}
