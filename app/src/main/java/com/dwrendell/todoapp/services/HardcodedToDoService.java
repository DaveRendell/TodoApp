package com.dwrendell.todoapp.services;

import com.dwrendell.todoapp.adapters.TodoItemAdapter;
import com.dwrendell.todoapp.models.ToDoItem;
import com.dwrendell.todoapp.models.ToDoItemBuilder;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

/**
 * Just for initial dev
 */
public class HardcodedToDoService implements ToDoService {
    private List<ToDoItem> todos = new ArrayList<>(Arrays.asList(
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
    ));

    private int nextId = 5;

    @Override
    public List<ToDoItem> getTodos() {
        return new ArrayList<>(todos);
    }

    @Override
    public void updateTodoPosition(int from, int to) {
        List<ToDoItem> newTodos = new ArrayList<>(todos);
        ToDoItem item = newTodos.remove(from);
        newTodos.add(to, item);
        todos = newTodos;
    }

    @Override
    public void toggleDone(int id) {
        getTodo(id).setDone(!getTodo(id).isDone());
    }

    @Override
    public void createTodo(String description) {
        todos.add(new ToDoItemBuilder()
                .setDescription(description)
                .setId(nextId)
                .setDate(new Date())
                .createToDoItem());
        nextId += 1;
    }

    private ToDoItem getTodo(int id) {
        for (ToDoItem todo : todos) {
            if (todo.getId() == id) {
                return todo;
            }
        }
        throw new RuntimeException("No To Do found with id " + id);
    }
}