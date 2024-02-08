package com.example.sqlliteproject1.recycleview;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.ItemTouchHelper;

import com.example.sqlliteproject1.R;
import com.example.sqlliteproject1.database.myDatapaseHelper;
import com.example.sqlliteproject1.models.Note;

import java.util.List;

public class MyAdapter extends RecyclerView.Adapter<MyViewHolder> {

    private myDatapaseHelper databaseHelper;
    private Context context;

    public void setItems(List<Note> items) {
        this.items = items;
    }

    private List<Note> items;
    private OnItemClickListener onItemClickListener;

    public MyAdapter(Context context, List<Note> items, myDatapaseHelper databaseHelper) {
        this.context = context;
        this.items = items;
        this.databaseHelper = databaseHelper;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_view, parent, false);
        return new MyViewHolder(itemView, this); // Pass the adapter reference
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder holder, int position) {
        Note currentNote = items.get(position);

        holder.title.setText(currentNote.getTitle());
        holder.content.setText(currentNote.getContent());
        holder.date.setText(currentNote.getTimeAsString());
        holder.colorIndicator.setBackgroundColor(ContextCompat.getColor(context, currentNote.getColor()));

        holder.noteFromViewItem = currentNote;
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        this.onItemClickListener = listener;
    }

    public OnItemClickListener getOnItemClickListener() {
        return onItemClickListener;
    }

    // Method to handle item dismissal (swipe-to-delete)
    public void onItemDismiss(int position) {

        // Remove from the list
        Note deletedNote = items.remove(position);
        // Notify RecyclerView about the removal
        notifyItemRemoved(position);

        // Delete from the database
        if (databaseHelper != null && deletedNote.getId() != -1) {
            databaseHelper.deleteNote(deletedNote.getId());

            System.out.println(position +"note deleteddddddddddddddddddddddd");
        }
        else System.out.println(position+"  NOTE HAVE NOT BEEN DELETED DKNFDKNFDKFNDKFNDKF");
    }

    public interface OnItemClickListener {
        void onItemClick(int position, Note note, MyViewHolder holder);
    }
}
