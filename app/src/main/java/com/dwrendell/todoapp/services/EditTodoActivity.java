package com.dwrendell.todoapp.services;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.dwrendell.todoapp.R;
import com.dwrendell.todoapp.activities.MainActivity;

public class EditTodoActivity extends AppCompatActivity {
    public static final String DESCRIPTION_EXTRA = "description";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo);
    }

    public void submit(View view) {
        Intent intent = new Intent();

        TextView description = findViewById(R.id.edit_description);
        intent.putExtra(DESCRIPTION_EXTRA, description.getText().toString());

        setResult(RESULT_OK, intent);
        finish();
    }

    public static class RequestCodes {
        public static int CREATE = 1;
        public static int EDIT = 2;
    }
}
