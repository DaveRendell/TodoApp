package com.dwrendell.todoapp.models;

public class TodoListBuilder {
    private String filename;

    public TodoListBuilder setFilename(String filename) {
        this.filename = filename;
        return this;
    }

    public TodoList createTodoList() {
        return new TodoList(filename);
    }
}