package com.nyc.polymerse.Invites;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TimePicker;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.nyc.polymerse.Constants;
import com.nyc.polymerse.R;
import com.nyc.polymerse.User;

import java.util.Calendar;

/**
 * A simple {@link Fragment} subclass.
 */
public class Invite_Frag extends Fragment implements View.OnClickListener {

    EditText time, date;
    Button dateButton, timeButton;

    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

    User otherUser;

    DatabaseReference databaseReference;


    public Invite_Frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View v = inflater.inflate(R.layout.fragment_invite_, container, false);

        Bundle bundle = getArguments();
        String jsonUser = bundle.getString(Constants.ITEM_SELECTED_KEY);
        otherUser = new Gson().fromJson(jsonUser,User.class);


        time = v.findViewById(R.id.time);
        date = v.findViewById(R.id.date);

        dateButton = v.findViewById(R.id.date_picker);
        timeButton = v.findViewById(R.id.time_picker);

        dateButton.setOnClickListener(this);
        timeButton.setOnClickListener(this);

        return v;


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.date_picker:

                Calendar calendar = Calendar.getInstance();
                int year = calendar.get(Calendar.YEAR);
                int month = calendar.get(Calendar.MONTH);
                int day = calendar.get(Calendar.DAY_OF_MONTH);

                datePickerDialog = new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        date.setText(dayOfMonth + " : " + (month + 1) + " : " + year);

//                        databaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.INVITES);
//                        databaseReference.push().setValue(new Invite_Schema());


                    }
                }, year, month, day);
                datePickerDialog.show();


                break;


            case R.id.time_picker:

                Calendar calendar1 = Calendar.getInstance();
                int hour = calendar1.get(Calendar.HOUR);
                final int mintue = calendar1.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {

                        time.setText(hourOfDay + " : " + minute);


                    }
                }, hour, mintue, false);
                timePickerDialog.show();


                break;


        }
    }
}
