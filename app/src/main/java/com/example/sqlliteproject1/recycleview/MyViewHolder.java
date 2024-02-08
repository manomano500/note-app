package com.example.sqlliteproject1.recycleview;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.sqlliteproject1.R;
import com.example.sqlliteproject1.models.Note;

public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView title;
    TextView content;
    TextView date;
    View colorIndicator;
    Note noteFromViewItem;
    MyAdapter adapter;

    public MyViewHolder(@NonNull View itemView, MyAdapter adapter) {
        super(itemView);
        this.adapter = adapter;

        title = itemView.findViewById(R.id.title);
        content = itemView.findViewById(R.id.content);
        date = itemView.findViewById(R.id.date);
        colorIndicator = itemView.findViewById(R.id.colorIndicator);


        itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (noteFromViewItem != null && adapter != null) {
                    int adapterPosition = getAdapterPosition();
                    if (adapterPosition != RecyclerView.NO_POSITION) {
                        adapter.getOnItemClickListener().onItemClick(adapterPosition, noteFromViewItem, MyViewHolder.this);
                    }
                }
            }
        });
    }
}
