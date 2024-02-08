package com.example.sqlliteproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;


import com.example.sqlliteproject1.database.myDatapaseHelper;
import com.example.sqlliteproject1.models.Note;
import com.google.android.material.button.MaterialButton;

public class CreateNoteActivity extends AppCompatActivity {
    EditText titleinput;
    EditText contentinput;
    TextView dateinput;
    ImageButton svbtn;
    ImageButton back;
    Note clickedNote=null;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_create_note);
        titleinput = findViewById(R.id.titleinput);
        contentinput =findViewById(R.id.conentinput);
        dateinput =findViewById(R.id.dateinput);
        svbtn =findViewById(R.id.svbtn);
        back =findViewById(R.id.back);



        Intent intent = getIntent();
        if (intent.hasExtra("clicked_note")) {
            clickedNote = (Note) intent.getSerializableExtra("clicked_note");
            titleinput.setText(clickedNote.getTitle());
            contentinput.setText(clickedNote.getContent());
            dateinput.setText(clickedNote.getTimeAsString());

        }

        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveOrUpdateNote();

            }
        });

        svbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleinput = findViewById(R.id.titleinput);
                contentinput = findViewById(R.id.conentinput);

                if (titleinput.getText().toString().isEmpty() || contentinput.getText().toString().isEmpty()) {
                    Toast.makeText(CreateNoteActivity.this, "Please Provide Title And Content", Toast.LENGTH_LONG).show();
                } else {
                    myDatapaseHelper myDb = new myDatapaseHelper(CreateNoteActivity.this);

                    if (clickedNote == null) {
                        // If clickedNote is null, create a new note
                        Note note = new Note(titleinput.getText().toString(), contentinput.getText().toString());
                        myDb.addNote(note);
                    } else {
                        // If clickedNote is not null, update the existing note
                        clickedNote.setTitle(titleinput.getText().toString());
                        clickedNote.setContent(contentinput.getText().toString());
                        myDb.updateNote(clickedNote);
                    }

                    finish();
                    Toast.makeText(CreateNoteActivity.this, "Note Saved", Toast.LENGTH_LONG).show();
                }
            }
        });



    }

    @Override
    public void onBackPressed() {
        // Add your custom action here
        // For example, you might want to display a confirmation dialog before exiting the app

        // Call super.onBackPressed() to allow the default back button behavior
        super.onBackPressed();
        saveOrUpdateNote();

    }

    private void saveOrUpdateNote() {
        titleinput = findViewById(R.id.titleinput);
        contentinput = findViewById(R.id.conentinput);
        myDatapaseHelper myDb = new myDatapaseHelper(CreateNoteActivity.this);

        if (!titleinput.getText().toString().isEmpty() && !contentinput.getText().toString().isEmpty()) {
            if (clickedNote == null) {
                Note note = new Note(titleinput.getText().toString(), contentinput.getText().toString());
                myDb.addNote(note);
                Toast.makeText(CreateNoteActivity.this, "New Note Saved", Toast.LENGTH_LONG).show();
            } else {
                clickedNote.setTitle(titleinput.getText().toString());
                clickedNote.setContent(contentinput.getText().toString());
                myDb.updateNote(clickedNote);
                Toast.makeText(CreateNoteActivity.this, "Note Updated", Toast.LENGTH_LONG).show();
            }
        } else {
            Toast.makeText(CreateNoteActivity.this, "Please insert the note title and content", Toast.LENGTH_LONG).show();
        }

        finish();
    }
}



