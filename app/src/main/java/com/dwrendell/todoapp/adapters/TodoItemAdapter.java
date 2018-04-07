package com.dwrendell.todoapp.adapters;

import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dwrendell.todoapp.R;
import com.dwrendell.todoapp.models.TodoItem;
import com.dwrendell.todoapp.services.ToDoService;
import com.woxthebox.draglistview.DragItemAdapter;


public class TodoItemAdapter extends DragItemAdapter<TodoItem, TodoItemAdapter.TodoViewHolder> {
    private int layoutId;
    private int grabHandleId;
    private boolean dragOnLongPress;
    private ToDoService toDoService;

    public TodoItemAdapter(ToDoService toDoService, int layoutId, int grabHandleId, boolean dragOnLongPress) {
        setItemList(toDoService.getTodos());
        this.toDoService = toDoService;
        this.layoutId = layoutId;
        this.grabHandleId = grabHandleId;
        this.dragOnLongPress = dragOnLongPress;
    }

    @Override
    public long getUniqueItemId(int position) {
        TodoItem todo = getItemList().get(position);
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
        TodoItem todoItem = toDoService.getTodos().get(position);
        holder.bindToModel(todoItem);
        holder.itemView.setTag(todoItem);
    }

    @Override
    public void changeItemPosition(int fromPos, int toPos) {
        super.changeItemPosition(fromPos, toPos);
        toDoService.updateTodoPosition(fromPos, toPos);
    }

    @Override
    public int getPositionForItem(TodoItem item) {
        return toDoService.getPositionForId(item.getId());
    }

    class TodoViewHolder extends DragItemAdapter.ViewHolder {
        TextView description;
        int id;


        TodoViewHolder(View itemView) {
            super(itemView, grabHandleId, dragOnLongPress);
            description = itemView.findViewById(R.id.todo_description);
            id = -1;
        }

        public void bindToModel(TodoItem todoItem) {
            description.setText(String.format("• %s", todoItem.getDescription()));
            description.setTypeface(null, Typeface.BOLD);
            if (todoItem.isDone()) {
                description.setTextColor(Color.GRAY);
                description.setPaintFlags(
                        description.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
            } else {
                description.setTextColor(Color.BLACK);
                description.setPaintFlags(
                        description.getPaintFlags() & ~Paint.STRIKE_THRU_TEXT_FLAG);
            }
            id = todoItem.getId();
        }

        @Override
        public void onItemClicked(View view) {
            super.onItemClicked(view);
            toDoService.toggleDone(id);
            notifyDataSetChanged();
        }
    }
}
