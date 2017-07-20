package com.example.juan.habittackerapp;

import android.content.ContentValues;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.juan.habittackerapp.data.RoutineContract.RoutineEntry;
import com.example.juan.habittackerapp.data.RoutineDbHelper;

public class EditorActivity extends AppCompatActivity {

    private EditText etDate;
    private EditText etExercise;
    private EditText etTime;
    private Spinner sType;
    private int mType = RoutineEntry.TYPE_RESTING_DAY;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editor);

        etDate = (EditText) findViewById(R.id.editText_date);
        etExercise = (EditText) findViewById(R.id.editText_exercise);
        etTime = (EditText) findViewById(R.id.editText_time);
        sType = (Spinner) findViewById(R.id.spinner_type);
        setupSpinner();
    }

    private void setupSpinner() {
        ArrayAdapter typeSpinnerAdapter = ArrayAdapter.createFromResource(this,
                R.array.array_type_options, android.R.layout.simple_spinner_item);

        typeSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        sType.setAdapter(typeSpinnerAdapter);

        sType.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selection = (String) parent.getItemAtPosition(position);
                if (!TextUtils.isEmpty(selection)) {
                    if (selection.equals(getString(R.string.type_cardio_workout))) {
                        mType = RoutineEntry.TYPE_CARDIO_WO;
                    } else if (selection.equals(getString(R.string.type_muscle_workout))) {
                        mType = RoutineEntry.TYPE_MUSCLE_WO;
                    } else {
                        mType = RoutineEntry.TYPE_RESTING_DAY;
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                mType = RoutineEntry.TYPE_RESTING_DAY;
            }
        });
    }

    private void insertRoutine() {
        String dateString = etDate.getText().toString().trim();
        String exerciseString = etExercise.getText().toString().trim();
        String timeString = etTime.getText().toString().trim();
        int time = Integer.parseInt(timeString);

        RoutineDbHelper mDbHelper = new RoutineDbHelper(this);
        SQLiteDatabase db = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(RoutineEntry.COLUMN_ROUTINE_DATE, dateString);
        values.put(RoutineEntry.COLUMN_ROUTINE_EXERCISE, exerciseString);
        values.put(RoutineEntry.COLUMN_ROUTINE_TYPE, mType);
        values.put(RoutineEntry.COLUMN_ROUTINE_TIME, time);

        long newRowId = db.insert(RoutineEntry.TABLE_NAME, null, values);

        if (newRowId == -1) {
            Toast.makeText(this, "Error with saving routine", Toast.LENGTH_LONG).show();
        } else {
            Toast.makeText(this, "Routine saved with row id: " + newRowId, Toast.LENGTH_LONG).show();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_editor, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.action_save:
                insertRoutine();
                finish();
                return true;
            case android.R.id.home:
                NavUtils.navigateUpFromSameTask(this);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }
}