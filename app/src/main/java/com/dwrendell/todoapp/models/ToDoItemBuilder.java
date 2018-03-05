package com.dwrendell.todoapp.models;

import java.util.Date;

public class ToDoItemBuilder {
    private int id;
    private String description;
    private Date date;
    private boolean done = false;

    public ToDoItemBuilder setId(int id) {
        this.id = id;
        return this;
    }

    public ToDoItemBuilder setDescription(String description) {
        this.description = description;
        return this;
    }

    public ToDoItemBuilder setDate(Date date) {
        this.date = date;
        return this;
    }

    public ToDoItemBuilder setDone(boolean done) {
        this.done = done;
        return this;
    }

    public ToDoItem createToDoItem() {
        return new ToDoItem(id, description, date, done);
    }
}