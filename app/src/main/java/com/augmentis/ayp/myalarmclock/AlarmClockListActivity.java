package com.augmentis.ayp.myalarmclock;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.UUID;

public class AlarmClockListActivity extends SingleFragmentActivity {


    private static final String ACLOCK_ID = "AlarmClockActivity.Id";

    @Override
    protected Fragment onCreateFragment() {
        return new AlarmClockListFragment();
    }


    public static Intent newIntent(Context activity, UUID id) {
        Intent intent = new Intent(activity, AlarmClockActivity.class);
        intent.putExtra(ACLOCK_ID, id);
        return intent;
    }

}
