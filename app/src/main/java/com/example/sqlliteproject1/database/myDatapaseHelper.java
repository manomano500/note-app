package com.example.sqlliteproject1.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
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
                    COULUMN_TIMESTAMP + " INTEGER " + // Add the TIMESTAMP column
                    ")";
            db.execSQL(sql);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("Drop TABLE IF  EXISTS "+ TABLE_NAME);

    }
  public void addNote(Note note){
      SQLiteDatabase db = this.getWritableDatabase();
      ContentValues contentValues = new ContentValues();
      contentValues.put(COULUMN_TITLE, note.getTitle());
      contentValues.put(COULUMN_CONTENT, note.getContent());
      contentValues.put(COULUMN_TIMESTAMP, note.getTimestamp()); // Assuming COULUMN_TIMESTAMP is the column name for the timestamp field

      long result =db.insert(TABLE_NAME,null,contentValues);
      System.out.println(result);

      if (result==-1){

          Toast.makeText(context,"Faild",Toast.LENGTH_LONG).show();
      }else {
          Toast.makeText(context,"Success",Toast.LENGTH_LONG).show();
      }
      db.close();
  }


    public List<Note> getAllNotes() {
        List<Note> notesList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();

        String[] columns = {COULUMN_ID, COULUMN_TITLE, COULUMN_CONTENT,COULUMN_TIMESTAMP};
        Cursor cursor = db.query(TABLE_NAME, columns, null, null, null, null, null);

        if (cursor != null) {
            int idColumnIndex = cursor.getColumnIndex(COULUMN_ID);
            int titleColumnIndex = cursor.getColumnIndex(COULUMN_TITLE);
            int contentColumnIndex = cursor.getColumnIndex(COULUMN_CONTENT);
            int timestampColumnIndex = cursor.getColumnIndex(COULUMN_TIMESTAMP);

            while (cursor.moveToNext()) {
                int id = cursor.getInt(idColumnIndex);
                String title = cursor.getString(titleColumnIndex);
                String content = cursor.getString(contentColumnIndex);
                long timestamp = cursor.getLong(timestampColumnIndex);

                Note note = new Note(title,content);
                note.setId(id);
                note.setTimestamp(timestamp);

                notesList.add(note);
            }

            cursor.close();
        }

        db.close();
        return notesList;
    }

}
