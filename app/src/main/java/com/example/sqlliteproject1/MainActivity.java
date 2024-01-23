package com.example.sqlliteproject1;

import static com.example.sqlliteproject1.R.menu.menu;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.ViewCompat;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.example.sqlliteproject1.database.myDatapaseHelper;
import com.example.sqlliteproject1.models.Note;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
FloatingActionButton newnoteflb;
RecyclerView recyclerView ;
myDatapaseHelper myDatabaseHelper ;
    List<Note> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        myDatabaseHelper = new myDatapaseHelper(this); // Initialize myDatabaseHelper

        setContentView(R.layout.activity_main);
        newnoteflb = findViewById(R.id.newnoteflb);
        recyclerView = findViewById(R.id.recyclerView);
        Toolbar toolbar = findViewById(R.id.toolbar);
        items = new ArrayList<>();

        // Fetch updated list of notes
        items.clear();
        items.addAll(myDatabaseHelper.getAllNotes());

        setSupportActionBar(toolbar);
//        getSupportActionBar().setTitle("All Notes");





        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider));  // Create a divider drawable in your res/drawable folder
        recyclerView.addItemDecoration(itemDecoration);

        // Set up the RecyclerView with the updated list of notes
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new MyAdapter(getApplicationContext(), items));

        newnoteflb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CreateNoteActivity.class));
            }
        });
    }
    @Override
    protected void onResume() {
        super.onResume();
        updateNotesList();
    }

    private void updateNotesList() {
        // Fetch updated list of notes
        items.clear();
        items.addAll(myDatabaseHelper.getAllNotes());
        recyclerView.getAdapter().notifyDataSetChanged();
        // Notify the adapter about the dataset change

    }
}

