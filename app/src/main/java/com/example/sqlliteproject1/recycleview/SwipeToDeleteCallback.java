package com.example.sqlliteproject1.recycleview;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {
    private final MyAdapter adapter;
    private final Paint deletePaint;
    private final Rect deleteBackground;

    public SwipeToDeleteCallback(MyAdapter adapter) {
        super(0, ItemTouchHelper.LEFT); // Only allow swiping to the left
        this.adapter = adapter;

        // Set background color for delete
        deletePaint = new Paint();
        deletePaint.setColor(Color.RED);

        // Set the background bounds for delete
        deleteBackground = new Rect();
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        // Not used for swipe-to-delete
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        int position = viewHolder.getAdapterPosition();
        adapter.onItemDismiss(position);
    }

    @Override
    public int getSwipeDirs(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder) {
        // Allow swiping only to the left
        return super.getSwipeDirs(recyclerView, viewHolder) & ItemTouchHelper.LEFT;
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        View itemView = viewHolder.itemView;
        int itemHeight = itemView.getHeight();

        // Draw the background
        deleteBackground.set(
                itemView.getRight() + (int) dX,
                itemView.getTop(),
                itemView.getRight(),
                itemView.getBottom()
        );
        c.drawRect(deleteBackground, deletePaint);

        // Draw the label
        Paint labelPaint = new Paint();
        labelPaint.setColor(Color.WHITE);
        labelPaint.setTextSize(59);
        String label = "Delete";
        float labelWidth = labelPaint.measureText(label);
        float labelX = itemView.getRight() - labelWidth - 50; // Adjust the X position as needed
        float labelY = itemView.getTop() + itemHeight / 2 + labelPaint.getTextSize() / 2;
        c.drawText(label, labelX, labelY, labelPaint);

        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
    }
}
