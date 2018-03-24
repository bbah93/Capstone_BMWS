package com.nyc.polymerse.Invites;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.nyc.polymerse.R;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class Invite_Frag extends Fragment{

    TextView date, time, location;
    Calendar mCurrentDate;

    int day, month, year;

    public Invite_Frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_invite_, container, false);
          date= (TextView)v.findViewById(R.id.date);
          time = (TextView)v.findViewById(R.id.time);
          location = (TextView)v.findViewById(R.id.location);

          mCurrentDate = Calendar.getInstance();
          day = mCurrentDate.get(Calendar.DAY_OF_MONTH);
          month = mCurrentDate.get(Calendar.MONTH);
          year = mCurrentDate.get(Calendar.YEAR);
          month = month+1;

          date.setText(day+"/"+month+"/"+year);
        return v;



    }



}
