package com.dwrendell.todoapp.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.dwrendell.todoapp.R;

public class EditTodoActivity extends AppCompatActivity {
    public static final String DESCRIPTION_EXTRA = "description";
    private EditText description;
    private Button button;
    private boolean canSubmit = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_todo);

        description = findViewById(R.id.edit_description);
        button = findViewById(R.id.edit_button);
        updateButtonEnabledState();

        description.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                updateButtonEnabledState();
            }
        });

        String defaultDescription = getIntent().getStringExtra("default_description");
        if (defaultDescription != null) {
            description.setText(defaultDescription);
            description.setSelection(defaultDescription.length());
        }

        description.requestFocus();
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_VISIBLE);

        description.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if (actionId == EditorInfo.IME_ACTION_DONE && canSubmit) {
                    submitTodo();
                    return true;
                }
                return false;
            }
        });
    }

    public void submit(View view) {
        submitTodo();
    }

    private void submitTodo() {
        Intent intent = new Intent();

        intent.putExtra(DESCRIPTION_EXTRA, description.getText().toString());

        int id = getIntent().getIntExtra("id", -1);
        if (id != -1) {
            intent.putExtra("id", id);
        }

        setResult(RESULT_OK, intent);
        finish();
    }

    private void updateButtonEnabledState() {
        canSubmit = description.getText().toString().length() > 0;
        button.setEnabled(canSubmit);
    }

    public static class RequestCodes {
        public static int CREATE = 1;
        public static int EDIT = 2;
    }
}
