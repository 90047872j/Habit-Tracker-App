package com.example.juan.habittackerapp.data;

import android.provider.BaseColumns;

public final class RoutineContract {

    private RoutineContract() {}

    public static final class RoutineEntry implements BaseColumns {

        public final static String TABLE_NAME = "routine";
        public final static String _ID = BaseColumns._ID;
        public final static String COLUMN_ROUTINE_DATE ="date";
        public final static String COLUMN_ROUTINE_EXERCISE = "exercise";
        public final static String COLUMN_ROUTINE_TYPE = "type";
        public final static String COLUMN_ROUTINE_TIME = "time";

        public static final int TYPE_RESTING_DAY = 0;
        public static final int TYPE_CARDIO_WO = 1;
        public static final int TYPE_MUSCLE_WO = 2;
    }
}

