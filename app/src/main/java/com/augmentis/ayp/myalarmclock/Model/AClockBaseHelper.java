package com.augmentis.ayp.myalarmclock.Model;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.augmentis.ayp.myalarmclock.Model.AClockDbSchema.AClockTable;

/**
 * Created by Apinya on 8/24/2016.
 */
public class AClockBaseHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "alarmClock.db";
    private static final int VERSION = 1;
    private static final String TAG = "AClockBaseHelper";

    public AClockBaseHelper(Context context){
        super(context, DATABASE_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        Log.d(TAG, "Create Database");

        db.execSQL("create table " + AClockTable.NAME
                + "("
                + "_id integer primary key autoincrement, "
                + AClockTable.Cols.UUID + ","
                + AClockTable.Cols.TITLE + ","
                + AClockTable.Cols.CHOUR + ","
                + AClockTable.Cols.CMIN + ")"
        );
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {

    }
}
