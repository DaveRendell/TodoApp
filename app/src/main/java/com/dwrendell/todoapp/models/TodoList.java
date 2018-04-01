package com.dwrendell.todoapp.models;


public class TodoList {
    private String filename;

    public TodoList(String filename) {
        this.filename = filename;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public String toString() {
        return filename;
    }
}
