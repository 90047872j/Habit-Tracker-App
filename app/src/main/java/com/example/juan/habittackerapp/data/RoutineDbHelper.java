package com.example.juan.habittackerapp.data;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.juan.habittackerapp.data.RoutineContract.RoutineEntry;

public class RoutineDbHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "shelter.db";
    private static final int DATABASE_VERSION = 1;

    public RoutineDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String SQL_CREATE_ROUTINE_TABLE =  "CREATE TABLE " + RoutineEntry.TABLE_NAME + " ("
                + RoutineEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
                + RoutineEntry.COLUMN_ROUTINE_DATE + " TEXT NOT NULL, "
                + RoutineEntry.COLUMN_ROUTINE_EXERCISE + " TEXT, "
                + RoutineEntry.COLUMN_ROUTINE_TYPE + " INTEGER NOT NULL, "
                + RoutineEntry.COLUMN_ROUTINE_TIME + " INTEGER NOT NULL DEFAULT 0);";

        db.execSQL(SQL_CREATE_ROUTINE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }
}