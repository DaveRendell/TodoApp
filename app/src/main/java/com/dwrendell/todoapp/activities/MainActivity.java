package com.dwrendell.todoapp.activities;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.dwrendell.todoapp.R;
import com.dwrendell.todoapp.adapters.TodoItemAdapter;
import com.dwrendell.todoapp.adapters.TodoItemSwipeListenerAdapter;
import com.dwrendell.todoapp.models.ToDoItem;
import com.dwrendell.todoapp.models.TodoList;
import com.dwrendell.todoapp.services.FileTodoService;
import com.dwrendell.todoapp.services.HardcodedListService;
import com.dwrendell.todoapp.services.ListService;
import com.dwrendell.todoapp.services.ToDoService;
import com.woxthebox.draglistview.DragListView;
import com.woxthebox.draglistview.swipe.ListSwipeHelper;
import com.woxthebox.draglistview.swipe.ListSwipeItem;

import java.io.File;
import java.io.IOException;

public class MainActivity extends AppCompatActivity {
    private ToDoService toDoService;
    private ListService listService;
    private TodoItemAdapter adapter;
    private DragListView dragListView;
    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        switchList("main");

        adapter = new TodoItemAdapter(
                toDoService, R.layout.todo_item, R.id.swipe_item, true);
        ListSwipeHelper.OnSwipeListener swipeListener =
                new TodoItemSwipeListenerAdapter(adapter, toDoService,this);

        listService = new HardcodedListService();

        drawerLayout = findViewById(R.id.drawer_layout);

        setupActionBar();
        setupFloatingAddButton();
        setupDragListView(swipeListener);
        setupMenuItems();

    }

    private void setupDragListView(ListSwipeHelper.OnSwipeListener swipeListener) {
        dragListView = findViewById(R.id.drag_list_view);
        dragListView.setSwipeListener(swipeListener);
        dragListView.setLayoutManager(new LinearLayoutManager(this));


        dragListView.setAdapter(adapter, true);
        dragListView.setCanDragHorizontally(false);
    }

    private void setupMenuItems() {
        ListView menuItems = findViewById(R.id.lst_menu_items);
        menuItems.setAdapter(new ArrayAdapter<>(
                this, R.layout.menu_list_item, listService.getLists()));

        menuItems.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TextView textView = (TextView) view;
                switchList(textView.getText().toString());
                adapter = new TodoItemAdapter(
                        toDoService, R.layout.todo_item, R.id.swipe_item, true);
                dragListView.setAdapter(adapter, true);
                drawerLayout.closeDrawer(GravityCompat.START);
            }
        });
    }

    private void setupActionBar() {
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            actionbar.setDisplayHomeAsUpEnabled(true);
            actionbar.setHomeAsUpIndicator(R.drawable.ic_menu);
        }
    }

    private void setupFloatingAddButton() {
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), EditTodoActivity.class);
                startActivityForResult(intent, EditTodoActivity.RequestCodes.CREATE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == RESULT_OK) {
            if (requestCode == EditTodoActivity.RequestCodes.CREATE) {
                String description = data.getStringExtra(EditTodoActivity.DESCRIPTION_EXTRA);
                toDoService.createTodo(description);
                adapter.setItemList(toDoService.getTodos());
                adapter.notifyDataSetChanged();
            }
            if (requestCode == EditTodoActivity.RequestCodes.EDIT) {
                String description = data.getStringExtra(EditTodoActivity.DESCRIPTION_EXTRA);
                int id = data.getIntExtra("id", -1);
                toDoService.editTodo(id, description);
                adapter.setItemList(toDoService.getTodos());
                adapter.notifyDataSetChanged();
            }
        }
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

        switch (item.getItemId()) {
            case R.id.action_delete_all:
                toDoService.deleteAll();
                adapter.setItemList(toDoService.getTodos());
                adapter.notifyItemRangeRemoved(0, adapter.getItemCount());
                return true;
            case android.R.id.home:
                drawerLayout.openDrawer(GravityCompat.START);
        }
        return super.onOptionsItemSelected(item);
    }

    private void switchList(String listName) {
        File directory = getFilesDir();
        File file = new File(directory, String.format("%s.todo", listName.toLowerCase()));
        if (!file.exists()) {
            try {
                boolean created = file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        ActionBar actionbar = getSupportActionBar();
        if (actionbar != null) {
            if (listName.equals("Main")) {
                actionbar.setTitle("TodoApp");
            } else {
                actionbar.setTitle(listName);
            }
        }

        toDoService = new FileTodoService(file);
    }
}
