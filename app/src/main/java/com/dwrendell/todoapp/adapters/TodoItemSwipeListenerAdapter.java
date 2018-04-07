package com.dwrendell.todoapp.adapters;

import android.content.ContextWrapper;
import android.content.Intent;
import android.graphics.Color;
import android.widget.TextView;

import com.dwrendell.todoapp.R;
import com.dwrendell.todoapp.activities.EditTodoActivity;
import com.dwrendell.todoapp.activities.MainActivity;
import com.dwrendell.todoapp.models.ToDoItem;
import com.dwrendell.todoapp.services.ToDoService;
import com.woxthebox.draglistview.swipe.ListSwipeHelper;
import com.woxthebox.draglistview.swipe.ListSwipeItem;

public class TodoItemSwipeListenerAdapter implements ListSwipeHelper.OnSwipeListener {
    private TodoItemAdapter adapter;
    private ToDoService toDoService;
    private MainActivity mainActivity;

    public TodoItemSwipeListenerAdapter(
            TodoItemAdapter adapter,
            ToDoService toDoService,
            MainActivity mainActivity) {
        this.adapter = adapter;
        this.toDoService = toDoService;
        this.mainActivity = mainActivity;
    }

    @Override
    public void onItemSwipeStarted(ListSwipeItem item) {
    }

    @Override
    public void onItemSwipeEnded(ListSwipeItem item, ListSwipeItem.SwipeDirection swipedDirection) {
        if (swipedDirection == ListSwipeItem.SwipeDirection.LEFT) {
            ToDoItem toDoItem = (ToDoItem) item.getTag();
            int position = adapter.getPositionForItem(toDoItem);
            adapter.removeItem(position);
            toDoService.removeTodo(toDoItem.getId());
        }
        if (swipedDirection == ListSwipeItem.SwipeDirection.RIGHT) {
            ToDoItem toDoItem = (ToDoItem) item.getTag();
            Intent intent = new Intent(mainActivity.getBaseContext(), EditTodoActivity.class);
            intent.putExtra("id", toDoItem.getId());
            intent.putExtra("default_description", toDoItem.getDescription());
            mainActivity.startActivityForResult(intent, EditTodoActivity.RequestCodes.EDIT);
        }
    }

    @Override
    public void onItemSwiping(ListSwipeItem item, float swipedDistanceX) {
        TextView left = item.findViewById(R.id.item_left);
        TextView right = item.findViewById(R.id.item_right);
        int alpha = 255;
        if (Math.abs(swipedDistanceX) < 600f) {
            alpha = Float.valueOf(255 * (Math.abs(swipedDistanceX) / 600)).intValue();
        }
        left.setBackgroundColor(Color.argb(alpha, 46, 125, 50));
        right.setBackgroundColor(Color.argb(alpha, 183, 28, 28));
    }
}
