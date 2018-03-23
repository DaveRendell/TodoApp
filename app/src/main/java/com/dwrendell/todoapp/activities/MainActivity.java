package com.dwrendell.todoapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;

import com.dwrendell.todoapp.R;
import com.dwrendell.todoapp.adapters.TodoItemAdapter;
import com.dwrendell.todoapp.services.EditTodoActivity;
import com.dwrendell.todoapp.services.HardcodedToDoService;
import com.dwrendell.todoapp.services.ToDoService;
import com.woxthebox.draglistview.DragListView;

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
                Intent intent = new Intent(view.getContext(), EditTodoActivity.class);
                startActivity(intent);
            }
        });

        ToDoService toDoService = new HardcodedToDoService();

        DragListView dragListView = findViewById(R.id.drag_list_view);
        dragListView.setLayoutManager(new LinearLayoutManager(this));
        TodoItemAdapter adapter = new TodoItemAdapter(
                toDoService, R.layout.todo_item, R.id.todo_item, true);
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
