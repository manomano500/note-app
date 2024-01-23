package com.example.sqlliteproject1;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {
    TextView title ;
    TextView content ;
    TextView date;
    public MyViewHolder(@NonNull View itemView) {
        super(itemView);
        title = itemView.findViewById(R.id.title);
        content =itemView.findViewById(R.id.content);
        date =itemView.findViewById(R.id.date);
    }
}
