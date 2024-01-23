package com.example.sqlliteproject1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;


import com.example.sqlliteproject1.database.myDatapaseHelper;
import com.example.sqlliteproject1.models.Note;
import com.google.android.material.button.MaterialButton;

public class CreateNoteActivity extends AppCompatActivity {
    EditText titleinput;
    EditText contentinput;
    ImageButton svbtn;
    ImageButton back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_note);
        titleinput = findViewById(R.id.titleinput);
        contentinput =findViewById(R.id.conentinput);
        svbtn =findViewById(R.id.svbtn);
        back =findViewById(R.id.back);




        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                back();

            }
        });

        svbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                titleinput = findViewById(R.id.titleinput);
                contentinput =findViewById(R.id.conentinput);
                if (titleinput.getText().toString().isEmpty() |contentinput.getText().toString().isEmpty()){
                    Toast.makeText(CreateNoteActivity.this,"Please Provide Title And Content",Toast.LENGTH_LONG).show();

                }
                else{
                    Note note =new Note(titleinput.getText().toString(),contentinput.getText().toString());
                    myDatapaseHelper myDb =new myDatapaseHelper(CreateNoteActivity.this);
                    myDb.addNote(note);

                    finish();
                    Toast.makeText(CreateNoteActivity.this,"Note Saved",Toast.LENGTH_LONG).show();

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
        back();
    }

    void back(){

        titleinput = findViewById(R.id.titleinput);
        contentinput =findViewById(R.id.conentinput);
        if (!titleinput.getText().toString().isEmpty() & !contentinput.getText().toString().isEmpty()){
            Note note =new Note(titleinput.getText().toString(),contentinput.getText().toString());
            myDatapaseHelper myDb =new myDatapaseHelper(CreateNoteActivity.this);
            myDb.addNote(note);

            finish();
            Toast.makeText(CreateNoteActivity.this,"Note Saved",Toast.LENGTH_LONG).show();

        }
        else{
            finish();


        }

    }

}