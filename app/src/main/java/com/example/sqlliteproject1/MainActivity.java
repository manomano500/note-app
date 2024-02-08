package com.example.sqlliteproject1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;


import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;


import com.example.sqlliteproject1.database.myDatapaseHelper;
import com.example.sqlliteproject1.models.Note;
import com.example.sqlliteproject1.recycleview.MyAdapter;
import com.example.sqlliteproject1.recycleview.MyViewHolder;
import com.example.sqlliteproject1.recycleview.SwipeToDeleteCallback;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;


public class MainActivity extends AppCompatActivity implements  SearchView.OnQueryTextListener{
    MyAdapter adapter;
FloatingActionButton newnoteflb;
RecyclerView recyclerView ;
myDatapaseHelper myDatabaseHelper ;
List<Note> items;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        recyclerView = findViewById(R.id.recyclerView);
        newnoteflb = findViewById(R.id.newnoteflb);

        SearchView searchView = findViewById(R.id.searchView);
        searchView.setOnQueryTextListener(MainActivity.this);
        // cary list of all notes
        items = new ArrayList<>();

        myDatabaseHelper = new myDatapaseHelper(this);
        adapter = new MyAdapter(this, items,myDatabaseHelper);

        // divider between list items
        DividerItemDecoration itemDecoration = new DividerItemDecoration(this, LinearLayoutManager.VERTICAL);
        itemDecoration.setDrawable(getResources().getDrawable(R.drawable.divider));
        recyclerView.addItemDecoration(itemDecoration);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));


        // Swipe to delete
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new SwipeToDeleteCallback(adapter));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        // on item click listener
        adapter.setOnItemClickListener(new MyAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(int position, Note note, MyViewHolder holder) {

                Intent intent = new Intent(MainActivity.this, CreateNoteActivity.class);
                intent.putExtra("clicked_note", note);
                startActivity(intent);
            }
        });



        // add new note btn
        newnoteflb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, CreateNoteActivity.class));
            }
        });
    }
    @Override
    protected void onResume() {
        // to add the just created note to the view when screen reload
        super.onResume();
        updateNotesList();
    }



    private void updateNotesList() {
        // Fetch updated list of notes
        items.clear();
        items.addAll(myDatabaseHelper.getAllNotesAsArrayList());
        recyclerView.getAdapter().notifyDataSetChanged();
        // Notify the adapter about the dataset change

    }

    @Override
    public boolean onQueryTextSubmit(String query) {

        return false;
    }

    @Override
    public boolean onQueryTextChange(String newText) {
        myDatapaseHelper myDatapaseHelper=MainActivity.this.myDatabaseHelper;
        newText = newText.toLowerCase(Locale.getDefault());

        // Create a filtered list to hold the filtered notes
        List<Note> filteredNotes = new ArrayList<>();

        // Iterate through all notes
        for (Note note :MainActivity.this.items ) {

            // Check if the note's title contains the search query

            System.out.println("from content:  "+note.getContent());
            if (note.getTitle().toLowerCase(Locale.getDefault()).contains(newText)| note.getContent().toLowerCase(Locale.getDefault()).contains(newText ) ){
                // If it does, add it to the filtered list
                System.out.println("notes with answer:  "+note.getContent());
                filteredNotes.add(note);
            }
        }
        adapter.setItems(filteredNotes);
        return true;
    }
}

