package com.augmentis.ayp.myalarmclock.Model;

import android.database.Cursor;
import android.database.CursorWrapper;

import java.util.UUID;

import com.augmentis.ayp.myalarmclock.AClock;
import com.augmentis.ayp.myalarmclock.Model.AClockDbSchema.AClockTable;

/**
 * Created by Apinya on 8/24/2016.
 */
public class AClockCursorWrapper extends CursorWrapper {

    public AClockCursorWrapper(Cursor cursor) {
        super(cursor);
    }

    public AClock getAClock(){

        String uuidString = getString(getColumnIndex(AClockTable.Cols.UUID));
        String title = getString(getColumnIndex(AClockTable.Cols.TITLE));
        int hour = getInt(getColumnIndex(AClockTable.Cols.CHOUR));
        int min = getInt(getColumnIndex(AClockTable.Cols.CMIN));

        AClock alarmClock = new AClock(UUID.fromString(uuidString));
        alarmClock.setTitle(title);
        alarmClock.setHour(hour);
        alarmClock.setMin(min);

        return alarmClock;
    }
}
