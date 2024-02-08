package com.example.sqlliteproject1.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Color;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.example.sqlliteproject1.models.Note;

import java.util.ArrayList;
import java.util.List;

public class myDatapaseHelper extends SQLiteOpenHelper {
    private Context context;
    private static final String DATABASE_NAME = "Notes.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_NAME="my_notes";
    private static final String COULUMN_ID="_id";
    private static final String COULUMN_TITLE= "_tile";
    private static final String COULUMN_CONTENT="_content";
    private static final String COULUMN_TIMESTAMP="_timestamp";
    private static final String COULUMN_COLOR = "_color";


    public myDatapaseHelper(@Nullable Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
        this.context =context;

    }

    @Override

    public void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + TABLE_NAME + "(" +
                COULUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " +
                COULUMN_TITLE + " TEXT, " +
                COULUMN_CONTENT + " TEXT, " +
                COULUMN_COLOR + " INTEGER, " +  // Add the color column
                COULUMN_TIMESTAMP + " INTEGER " +
                ")";
        db.execSQL(sql);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop TABLE IF  EXISTS "+ TABLE_NAME);

    }

    ////////////////// Handel Note ////////////////////
    public void addNote(Note note){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COULUMN_TITLE, note.getTitle());
        contentValues.put(COULUMN_CONTENT, note.getContent());
        contentValues.put(COULUMN_COLOR, note.getColor() );
        contentValues.put(COULUMN_TIMESTAMP, note.getTimestamp());

        long result = db.insert(TABLE_NAME, null, contentValues);

        if (result == -1){
            System.out.println("Failed to add note");
        } else {
            System.out.println("Note added successfully");
        }

        db.close();
    }





    public ArrayList getAllNotesAsArrayList() {
        ArrayList<Note> notesList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {COULUMN_ID, COULUMN_TITLE, COULUMN_CONTENT, COULUMN_COLOR, COULUMN_TIMESTAMP};
        String orderBy = COULUMN_TIMESTAMP + " DESC";
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, orderBy);

        if (cursor != null) {
            int idColumnIndex = cursor.getColumnIndex(COULUMN_ID);
            int titleColumnIndex = cursor.getColumnIndex(COULUMN_TITLE);
            int contentColumnIndex = cursor.getColumnIndex(COULUMN_CONTENT);
            int colorColumnIndex = cursor.getColumnIndex(COULUMN_COLOR);
            int timestampColumnIndex = cursor.getColumnIndex(COULUMN_TIMESTAMP);

            while (cursor.moveToNext()) {
                int id = cursor.getInt(idColumnIndex);
                String title = cursor.getString(titleColumnIndex);
                String content = cursor.getString(contentColumnIndex);
                long color = cursor.getInt(colorColumnIndex);
                long timestamp = cursor.getLong(timestampColumnIndex);

                Note note = new Note(title, content, color);
                note.setId(id);
                note.setTimestamp(timestamp);

                notesList.add(note);
            }

            cursor.close();
        }

        db.close();
        return notesList;
    }

    public List<Note> getNotesByColor(int color) {
        List<Note> notesList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {COULUMN_ID, COULUMN_TITLE, COULUMN_CONTENT, COULUMN_COLOR, COULUMN_TIMESTAMP};
        String selection = COULUMN_COLOR + " = ?";
        String[] selectionArgs = {String.valueOf(color)};
        Cursor cursor = db.query(TABLE_NAME, columns, selection, selectionArgs, null, null, null);

        if (cursor != null) {
            // ... (Same as existing code)
        }

        db.close();
        return notesList;
    }


    public void deleteNote(int noteId) {
        SQLiteDatabase db = this.getWritableDatabase();
        String selection = COULUMN_ID + " = ?";
        String[] selectionArgs = {String.valueOf(noteId)};
        int deletedRows = db.delete(TABLE_NAME, selection, selectionArgs);

        if (deletedRows > 0) {
            System.out.println("Note deleted successfully");

        } else {
            System.out.println("Failed to delete note");

        }

        db.close();
    }

    public void updateNote(Note updatedNote) {
        SQLiteDatabase db = this.getWritableDatabase();
        updatedNote.setTimestamp(System.currentTimeMillis());

        ContentValues values = new ContentValues();
        values.put(COULUMN_TITLE, updatedNote.getTitle());
        values.put(COULUMN_CONTENT, updatedNote.getContent());
        values.put(COULUMN_TIMESTAMP, updatedNote.getTimestamp());
//        values.put(COULUMN_COLOR, updatedNote.getColor());

        String whereClause = COULUMN_ID + " = ?";
        String[] whereArgs = {String.valueOf(updatedNote.getId())};

        int rowsUpdated = db.update(TABLE_NAME, values, whereClause, whereArgs);

        if (rowsUpdated > 0) {
            System.out.println("Note updated successfully");
        } else {
            System.out.println("Failed to update note");
        }

        db.close();
    }



}
