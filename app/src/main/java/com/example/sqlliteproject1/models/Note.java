package com.example.sqlliteproject1.models;

import android.text.format.DateUtils;

import com.example.sqlliteproject1.R;

import java.io.Serializable;

public class Note implements Serializable {



    private int id;
    private String title;
    private String content;
    private int color;
    private static final int DEFAULT_COLOR_RES_ID = NoteColor.YELLOW.getColorResId();








    private long timestamp;

    public Note(String title, String content) {

        this.title = title;
        this.content = content;
        this.timestamp = System.currentTimeMillis(); // Set the current time as the timestamp

    }
    public Note(String title, String content,Long timestamp) {

        this.title = title;
        this.content = content;
        this.timestamp = timestamp;
        this.color = DEFAULT_COLOR_RES_ID;// Set the current time as the timestamp

    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }
    public long getTimestamp() {
        return timestamp;
    }
    public String getTimeAsString() {
        long currentTime = System.currentTimeMillis();
        long timeDifference = currentTime - timestamp;

        // Use DateUtils.getRelativeTimeSpanString to get a user-friendly time format
        CharSequence timeAgo = DateUtils.getRelativeTimeSpanString(timestamp, currentTime, DateUtils.SECOND_IN_MILLIS);

        return timeAgo.toString();
    }
    public int getColor() {
        return color;
    }
    public void setTitle(String title) {
        this.title = title;
    }

    public void setContent(String content) {
        this.content = content;
    }
    public void setId(int id) {
        this.id = id;
    }
    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }
    public void setColor(int color) {
        this.color = color;
    }

}
