package com.augmentis.ayp.myalarmclock;

import android.content.Context;
import android.content.Intent;
import android.support.v4.app.Fragment;

import java.util.UUID;

public class AlarmClockActivity extends SingleFragmentActivity implements AlarmClockFragment.Callbacks {

    private static final String ACLOCK_ID = "AlarmClockActivity.Id";
    private UUID aClockId;

    @Override
    protected Fragment onCreateFragment() {
        aClockId = (UUID) getIntent().getSerializableExtra(ACLOCK_ID);
        Fragment fragment = AlarmClockFragment.newInstance(aClockId);
        return fragment;
    }

    @Override
    public void onContactUpdated(AClock aClock) {

    }
}
