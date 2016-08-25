package com.augmentis.ayp.myalarmclock;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.augmentis.ayp.myalarmclock.Model.AClockBaseHelper;
import com.augmentis.ayp.myalarmclock.Model.AClockCursorWrapper;
import com.augmentis.ayp.myalarmclock.Model.AClockDbSchema.AClockTable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Created by Apinya on 8/24/2016.
 */
public class AClockLab {

    private static final String TAG = "AClockLab";
    /////////////////////////STATIC ZONE/////////////////////////////////
    private static AClockLab instance;

    public static AClockLab getInstance(Context context){

        if(instance == null){
            instance = new AClockLab(context);
        }
        return instance;
    }

    public static ContentValues getContentValues(AClock aClock){
        ContentValues contentValues = new ContentValues();
        contentValues.put(AClockTable.Cols.UUID, aClock.getId().toString());
        contentValues.put(AClockTable.Cols.TITLE, aClock.getTitle());
        contentValues.put(AClockTable.Cols.CHOUR, aClock.getHour());
        contentValues.put(AClockTable.Cols.CMIN, aClock.getMin());
        return contentValues;
    }
////////////////////////////////////////////////////////////////////

    private Context context;
    private SQLiteDatabase database;


    private AClockLab(Context context){
        this.context = context;
        AClockBaseHelper aClockBaseHelper = new AClockBaseHelper(context);
        database = aClockBaseHelper.getWritableDatabase();
    }

    public  AClock getAClockByID(UUID uuid){
        AClockCursorWrapper cursor = queryAClock(AClockTable.Cols.UUID
                + " = ? ", new String[] { uuid.toString()});

        try{
            if(cursor.getCount() == 0){
                return null;
            }

            cursor.moveToFirst();
            return cursor.getAClock();
        }finally {
            cursor.close();
        }
    }

    public AClockCursorWrapper queryAClock(String whereCause, String[] whereArgs){
        Cursor cursor = database.query(AClockTable.NAME,
                null,
                whereCause,
                whereArgs,
                null,
                null,
                null);

        return new AClockCursorWrapper(cursor);
    }

    public List<AClock> getAClocks(){
        List<AClock> aClocks = new ArrayList<>();

        AClockCursorWrapper cursorWrapper = queryAClock(null, null);
        try {
            cursorWrapper.moveToFirst();
            while ( !cursorWrapper.isAfterLast()){ // cursor until after the last row
                aClocks.add(cursorWrapper.getAClock());// add crime to crimelist

                cursorWrapper.moveToNext();//move to next crime
            }
        }finally {
            cursorWrapper.close(); //move until the last then close cursor
        }

        return aClocks;
    }

    public static void main(String [] args){
        AClockLab aClockLab = AClockLab.getInstance(null);
        List<AClock> aClockList = aClockLab.getAClocks();
        int size = aClockList.size();
        for(int i = 0; i < size; i++){
            System.out.println(aClockList.get(i));
        }
    }

    public void addAClock(AClock aClock) {
        Log.d(TAG, "Add clock " + aClock.toString());
        ContentValues contentValues = getContentValues(aClock);

        database.insert(AClockTable.NAME, null, contentValues);
    }

    public void deleteAClock(UUID uuid) {
        database.delete(AClockTable.NAME, AClockTable.Cols.UUID
                + " = ? ", new String[] {uuid.toString() });
    }

    public void updateAClock(AClock aClock){
        String uuidStr = aClock.getId().toString();
        ContentValues contentValues = getContentValues(aClock);

        database.update(AClockTable.NAME, contentValues, AClockTable.Cols.UUID
                + " = ?", new String[] { uuidStr});
    }



}
