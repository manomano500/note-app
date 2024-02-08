package com.example.sqlliteproject1.models;

import com.example.sqlliteproject1.R;

public enum NoteColor {
    RED(R.color.red),
    BLUE(R.color.sky),
    GREEN(R.color.green),
    YELLOW(R.color.defaultNoteColorForNotes),
    ORANGE(R.color.orange);


    private final int colorResId;

    NoteColor(int colorResId) {
        this.colorResId = colorResId;
    }

    public int getColorResId() {
        return colorResId;
    }
}