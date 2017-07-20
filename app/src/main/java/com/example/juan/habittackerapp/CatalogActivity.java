package com.example.juan.habittackerapp;

import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.juan.habittackerapp.data.RoutineContract.RoutineEntry;
import com.example.juan.habittackerapp.data.RoutineDbHelper;

public class CatalogActivity extends AppCompatActivity {

    private RoutineDbHelper mDbHelper;

    public Cursor queryAllRoutines() {
        SQLiteDatabase db = mDbHelper.getReadableDatabase();

        String[] projection = {
                RoutineEntry._ID,
                RoutineEntry.COLUMN_ROUTINE_DATE,
                RoutineEntry.COLUMN_ROUTINE_EXERCISE,
                RoutineEntry.COLUMN_ROUTINE_TYPE,
                RoutineEntry.COLUMN_ROUTINE_TIME};

        Cursor allRoutinesCursor = db.query(
                RoutineEntry.TABLE_NAME,
                projection,
                null,
                null,
                null,
                null,
                null);

        return allRoutinesCursor;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_catalog);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(CatalogActivity.this, EditorActivity.class);
                startActivity(intent);
            }
        });

        mDbHelper = new RoutineDbHelper(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        displayAllRoutinesInDB(queryAllRoutines());
    }

    private void displayAllRoutinesInDB(Cursor cursor) {

        TextView displayView = (TextView) findViewById(R.id.text_view_routine);

        try {
            displayView.setText("The routine table contains " + cursor.getCount() + " routines.\n\n");
            displayView.append(RoutineEntry._ID + " - " +
                    RoutineEntry.COLUMN_ROUTINE_DATE + " - " +
                    RoutineEntry.COLUMN_ROUTINE_EXERCISE + " - " +
                    RoutineEntry.COLUMN_ROUTINE_TYPE + " - " +
                    RoutineEntry.COLUMN_ROUTINE_TIME + "\n");

            int idColumnIndex = cursor.getColumnIndex(RoutineEntry._ID);
            int dateColumnIndex = cursor.getColumnIndex(RoutineEntry.COLUMN_ROUTINE_DATE);
            int exerciseColumnIndex = cursor.getColumnIndex(RoutineEntry.COLUMN_ROUTINE_EXERCISE);
            int typeColumnIndex = cursor.getColumnIndex(RoutineEntry.COLUMN_ROUTINE_TYPE);
            int timeColumnIndex = cursor.getColumnIndex(RoutineEntry.COLUMN_ROUTINE_TIME);

            while (cursor.moveToNext()) {
                int currentID = cursor.getInt(idColumnIndex);
                String currentDate = cursor.getString(dateColumnIndex);
                String currentExercise = cursor.getString(exerciseColumnIndex);
                int currentType= cursor.getInt(typeColumnIndex);
                int currentTime = cursor.getInt(timeColumnIndex);
                displayView.append(("\n" + currentID + " - " +
                        currentDate + " - " +
                        currentExercise + " - " +
                        currentType + " - " +
                        currentTime));
            }
        } finally {
            cursor.close();
        }
    }

    private void insertRoutine() {
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(RoutineEntry.COLUMN_ROUTINE_DATE, getString(R.string.dummy_date));
        values.put(RoutineEntry.COLUMN_ROUTINE_EXERCISE, getString(R.string.dummy_exercise));
        values.put(RoutineEntry.COLUMN_ROUTINE_TYPE, RoutineEntry.TYPE_CARDIO_WO);
        values.put(RoutineEntry.COLUMN_ROUTINE_TIME, getString(R.string.dummy_time));
        db.insert(RoutineEntry.TABLE_NAME, null, values);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_catalog, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_insert_dummy_data:
                insertRoutine();
                displayAllRoutinesInDB(queryAllRoutines());
                return true;

        }
        return super.onOptionsItemSelected(item);
    }
}
