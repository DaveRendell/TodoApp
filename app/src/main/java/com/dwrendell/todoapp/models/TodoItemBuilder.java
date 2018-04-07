package com.dwrendell.todoapp.models;

import java.util.Date;

public class TodoItemBuilder {
    private int id;
    private String description;
    private Date date;
    private boolean done = false;

    public TodoItemBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public TodoItemBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public TodoItemBuilder setDate(Date date) {
        this.date = date;
        return this;
    }

    public TodoItemBuilder setDone(boolean done) {
        this.done = done;
        return this;
    }

    public TodoItem createToDoItem() {
        return new TodoItem(id, description, date, done);
    }
}