package com.dwrendell.todoapp.adapters;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dwrendell.todoapp.R;
import com.dwrendell.todoapp.models.ToDoItem;
import com.woxthebox.draglistview.DragItemAdapter;

import java.util.List;


public class TodoItemAdapter extends DragItemAdapter<ToDoItem, TodoItemAdapter.TodoViewHolder> {
    private int layoutId;
    private int grabHandleId;
    private boolean dragOnLongPress;

    public TodoItemAdapter(List<ToDoItem> todos, int layoutId, int grabHandleId, boolean dragOnLongPress) {
        setItemList(todos);
        this.layoutId = layoutId;
        this.grabHandleId = grabHandleId;
        this.dragOnLongPress = dragOnLongPress;
    }

    @Override
    public long getUniqueItemId(int position) {
        ToDoItem todo = getItemList().get(position);
        return todo.getId();
    }

    @NonNull
    @Override
    public TodoItemAdapter.TodoViewHolder onCreateViewHolder(
            @NonNull ViewGroup parent,
            int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(layoutId, parent, false);
        return new TodoViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TodoViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        String text = mItemList.get(position).getDescription();
        holder.description.setText(text);
        holder.itemView.setTag(mItemList.get(position));
    }

    class TodoViewHolder extends DragItemAdapter.ViewHolder {
        TextView description;


        TodoViewHolder(View itemView) {
            super(itemView, grabHandleId, dragOnLongPress);
            description = itemView.findViewById(R.id.todo_description);
        }
    }
}
