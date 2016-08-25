package com.augmentis.ayp.myalarmclock;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

public class AlarmClockListFragment extends Fragment {

    private static final String TAG = "AlarmClockListFragment";
    private RecyclerView _recyclerView;
    private AClockAdapter _adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.activity_alarm_clock_list_fragment, container, false);

        _recyclerView = (RecyclerView) v.findViewById(R.id.clock_recycler_view);
        _recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));

        updateUI();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }

    public void updateUI() {
        AClockLab aClockLab = AClockLab.getInstance(getActivity());
        List<AClock> contacts = aClockLab.getAClocks();

        if (_adapter == null) {
            _adapter = new AClockAdapter(this, contacts);
            _recyclerView.setAdapter(_adapter);
        } else {
            _adapter.setAClocks(aClockLab.getAClocks());
            _adapter.notifyDataSetChanged();

        }

    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);

        inflater.inflate(R.menu.list_menu, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){

            case R.id.menu_new_alarm_clock:
                //
                AClock aClock = new AClock();
                AClockLab.getInstance(getActivity()).addAClock(aClock);

                Intent intent = AlarmClockListActivity.newIntent(getActivity(),aClock.getId());
                startActivity(intent);

                updateUI();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }

    }


    private class AClockHolder extends RecyclerView.ViewHolder implements View.OnClickListener{
        public TextView _TextTime;
        public TextView _EditText;
        int _position;

        AClock _aClock;

        public AClockHolder(View itemView) {
            super(itemView);

            _TextTime = (TextView) itemView.findViewById(R.id.list_item_clock_time);
            _EditText = (TextView) itemView.findViewById(R.id.list_item_clock_title);

            itemView.setOnClickListener(this);
        }

        public void bind(final AClock aClock , int position) {
            _aClock = aClock;
            _position = position;

            _TextTime.setText( + _aClock.getHour() + ":" + _aClock.getMin());
            _EditText.setText(_aClock.getTitle());
        }

        @Override
        public void onClick(View view) {

            Intent intent = AlarmClockListActivity.newIntent(getActivity(), _aClock.getId());
            startActivity(intent);
        }
    }

    private class AClockAdapter extends RecyclerView.Adapter<AClockHolder>{
        private List<AClock> _aclocks;
        private Fragment _f;

        public AClockAdapter(Fragment f, List<AClock> aClocks){
            _aclocks = aClocks;
            _f = f;
        }

        @Override
        public AClockHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View v = layoutInflater.inflate(R.layout.list_item_alarm_clock, parent, false);

            return new AClockHolder(v);
        }

        @Override
        public void onBindViewHolder(AClockHolder holder, int position) {
            Log.d(TAG, "Bind view holder for CrimeList : position = " + position);

            AClock aClock = _aclocks.get(position);
            holder.bind(aClock, position);
        }

        @Override
        public int getItemCount() {
            return _aclocks.size();
        }

        public void setAClocks(List<AClock> aclocks) {
            _aclocks = aclocks;
        }
    }
}
