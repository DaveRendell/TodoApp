package com.dwrendell.todoapp.services;

import com.dwrendell.todoapp.models.TodoList;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HardcodedListService implements ListService {
    @Override
    public List<TodoList> getLists() {
        return new ArrayList<>(Arrays.asList(
                new TodoList("Main"),
                new TodoList("TodoApp Development"),
                new TodoList("Books to Read"),
                new TodoList("Movies to Watch"),
                new TodoList("Shopping List")
        ));
    }
}
