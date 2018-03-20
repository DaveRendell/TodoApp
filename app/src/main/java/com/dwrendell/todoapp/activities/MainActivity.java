package com.dwrendell.todoapp.activities;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.dwrendell.todoapp.R;
import com.dwrendell.todoapp.adapters.TodoItemAdapter;
import com.dwrendell.todoapp.models.ToDoItem;
import com.dwrendell.todoapp.models.ToDoItemBuilder;
import com.woxthebox.draglistview.DragListView;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

        List<ToDoItem> todos = new ArrayList<>(Arrays.asList(
            new ToDoItemBuilder()
                    .setId(0)
                    .setDate(new Date())
                    .setDescription("Todo no 1")
                    .createToDoItem(),
            new ToDoItemBuilder()
                    .setId(1)
                    .setDate(new Date())
                    .setDescription("Todo no 2")
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
                    .createToDoItem()
        ));

        DragListView dragListView = findViewById(R.id.drag_list_view);
        dragListView.setLayoutManager(new LinearLayoutManager(this));
        TodoItemAdapter adapter = new TodoItemAdapter(
                new ArrayList<>(todos), R.layout.todo_item, R.id.todo_item, true);
        dragListView.setAdapter(adapter, true);
        dragListView.setCanDragHorizontally(false);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
