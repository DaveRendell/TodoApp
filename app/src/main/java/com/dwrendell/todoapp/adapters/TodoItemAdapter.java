package com.dwrendell.todoapp.adapters;

import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.dwrendell.todoapp.R;
import com.dwrendell.todoapp.models.ToDoItem;
import com.dwrendell.todoapp.services.ToDoService;
import com.woxthebox.draglistview.DragItemAdapter;


public class TodoItemAdapter extends DragItemAdapter<ToDoItem, TodoItemAdapter.TodoViewHolder> {
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

    @Override
    public void changeItemPosition(int fromPos, int toPos) {
        super.changeItemPosition(fromPos, toPos);
        toDoService.updateTodoPosition(fromPos, toPos);
    }

    class TodoViewHolder extends DragItemAdapter.ViewHolder {
        TextView description;


        TodoViewHolder(View itemView) {
            super(itemView, grabHandleId, dragOnLongPress);
            description = itemView.findViewById(R.id.todo_description);
        }
    }
}
