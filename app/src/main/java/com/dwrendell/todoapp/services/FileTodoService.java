package com.dwrendell.todoapp.services;

import com.dwrendell.todoapp.adapters.TodoItemAdapter;
import com.dwrendell.todoapp.models.ToDoItem;
import com.dwrendell.todoapp.models.ToDoItemBuilder;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import static java.sql.DriverManager.println;

public class FileTodoService implements ToDoService {
    private File file;
    ArrayList<ToDoItem> todos= new ArrayList<>(Arrays.asList(
            new ToDoItemBuilder()
                    .setId(0)
                    .setDate(new Date())
                    .setDescription("Todo no 1")
                    .createToDoItem(),
            new ToDoItemBuilder()
                    .setId(1)
                    .setDate(new Date())
                    .setDescription("Todo no 2")
                    .setDone(true)
                    .createToDoItem(),
            new ToDoItemBuilder()
                    .setId(2)
                    .setDate(new Date())
                    .setDescription("Todo no 3")
                    .createToDoItem(),
            new ToDoItemBuilder()
                    .setId(3)
                    .setDate(new Date())
                    .setDescription("Todo no 4")
                    .createToDoItem(),
            new ToDoItemBuilder()
                    .setId(4)
                    .setDate(new Date())
                    .setDescription("Todo no 5")
                    .setDone(true)
                    .createToDoItem()
    ));;
    private ObjectMapper mapper = new ObjectMapper();

    public FileTodoService(File file) {
        this.file = file;
    }

    @Override
    public List<ToDoItem> getTodos() {
        readFromFile();
        return todos;
    }

    @Override
    public void updateTodoPosition(int from, int to) {
        readFromFile();
        ArrayList<ToDoItem> newTodos = new ArrayList<>(todos);
        ToDoItem item = newTodos.remove(from);
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
        todos.add(new ToDoItemBuilder()
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
        for (ToDoItem todo : todos) {
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
            todos = mapper.readValue(file, new TypeReference<List<ToDoItem>>(){});
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private ToDoItem getTodo(int id) {
        for (ToDoItem todo : todos) {
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
