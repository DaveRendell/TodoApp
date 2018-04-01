package com.dwrendell.todoapp.services;


import com.dwrendell.todoapp.models.TodoList;

import java.util.List;

public interface ListService {
    public List<TodoList> getLists();
}
