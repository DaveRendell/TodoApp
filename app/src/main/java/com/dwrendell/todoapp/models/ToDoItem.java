package com.dwrendell.todoapp.models;

import android.view.View;
import android.widget.TextView;

import com.dwrendell.todoapp.R;

import java.io.Serializable;
import java.util.Date;

public class ToDoItem implements Serializable {
    private int id;
    private String description;
    private Date date;
    private boolean done;

    public ToDoItem() {
        this(0, null, null, false);
    }

    public ToDoItem(int id, String description, Date date, boolean done) {
        this.id = id;
        this.description = description;
        this.date = date;
        this.done = done;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public boolean isDone() {
        return done;
    }

    public void setDone(boolean done) {
        this.done = done;
    }

    public void updateView(View todoItemView) {
        TextView text = todoItemView.findViewById(R.id.todo_description);
        text.setText("- " + (description));
    }
}
