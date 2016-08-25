package com.augmentis.ayp.myalarmclock;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TimePicker;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.UUID;

/**
 * Created by Apinya on 8/24/2016.
 */
public class AlarmClockFragment extends Fragment {


    private static final int REQUEST_TIME = 12;
    private static final String DIALOG_TIME = "ACFragment.Dialog.time";
    private static final String TAG = "AlarmClockFragment";
    private static String ACLOCK_ID = "AlarmClockF.Id";
    private AClock aClock;

    private Callbacks callbacks;


    public interface Callbacks{
        void onContactUpdated(AClock aClock);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        callbacks = (Callbacks) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    public AlarmClockFragment() {
    }

    public static AlarmClockFragment newInstance(UUID aClockId) {
        Bundle args = new Bundle();
        args.putSerializable(ACLOCK_ID, aClockId);

        AlarmClockFragment fragment = new AlarmClockFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        AClockLab aClockLab = AClockLab.getInstance(getActivity());

        if (getArguments().get(ACLOCK_ID) != null) {
            UUID clock_id = (UUID) getArguments().getSerializable(ACLOCK_ID);
            aClock = aClockLab.getInstance(getActivity()).getAClockByID(clock_id);

        } else {
            AClock aClock = new AClock();
            aClockLab.addAClock(aClock);
            this.aClock = aClock;
        }

    }//end onCreate

    private TimePicker _time;
    private EditText _title;
    private Button _button_set;
    private TimePicker timePicker;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_alarm_clock, container, false);

        _time = (TimePicker) v.findViewById(R.id.time_picker);
        _time.setIs24HourView(true);
        _time.setOnTimeChangedListener(new TimePicker.OnTimeChangedListener() {
            @TargetApi(Build.VERSION_CODES.M)
            @Override
            public void onTimeChanged(TimePicker timePicker, int i, int i1) {
                aClock.setHour(i);
                aClock.setMin(i1);

                Log.d(TAG, "get time: " + aClock.getHour() + ":" + aClock.getMin());
                updateContact();
            }
        });
        updateContact();


        _title = (EditText) v.findViewById(R.id.title_view);
        _title.setText(aClock.getTitle());
        _title.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                aClock.setTitle(_title.getText().toString());
                updateContact();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        return v;
    }//end create view

    public void updateContact(){
        AClockLab.getInstance(getActivity()).updateAClock(aClock);// update crime in db

//        if (AlarmClockFragment.this.isResumed()) {
//            callbacks.onContactUpdated(aClock);
//        }

    }

}

