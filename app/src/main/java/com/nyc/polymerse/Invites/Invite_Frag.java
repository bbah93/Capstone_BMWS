package com.nyc.polymerse.Invites;


import android.app.DatePickerDialog;
import android.app.TimePickerDialog;
import android.content.Context;
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
import com.nyc.polymerse.FilterUsersClass;
import com.nyc.polymerse.HomeActivity;
import com.nyc.polymerse.R;
import com.nyc.polymerse.User;
import com.nyc.polymerse.UserSingleton;
import com.nyc.polymerse.fragments.UserDetailsFragment;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class Invite_Frag extends Fragment implements View.OnClickListener {

    EditText time, date;
    Button dateButton, timeButton, send;

    DatePickerDialog datePickerDialog;
    TimePickerDialog timePickerDialog;

    User otherUser;
    User currentUser;

    DatabaseReference databaseReference;

    Invite_Schema invite;
    private Context context;


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
        otherUser = new Gson().fromJson(jsonUser, User.class);

        invite = new Invite_Schema();
        invite.setLocation("anywhere");
        currentUser = UserSingleton.getInstance().getUser();
        invite.setSender_ID(currentUser.getuID());
        invite.setReceiver_ID(otherUser.getuID());


        time = v.findViewById(R.id.time);
        date = v.findViewById(R.id.date);
        send = v.findViewById(R.id.send_button_invite);

        dateButton = v.findViewById(R.id.date_picker);
        timeButton = v.findViewById(R.id.time_picker);

        dateButton.setOnClickListener(this);
        timeButton.setOnClickListener(this);
        send.setOnClickListener(this);

        context = getActivity();

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
                        String dateString = dayOfMonth + " : " + (month + 1) + " : " + year;
                        date.setText(dateString);

                        invite.setDate(dayOfMonth + " : " + (month + 1) + " : " + year);

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

                        invite.setTime(hourOfDay + " : " + minute);

                    }
                }, hour, mintue, false);
                timePickerDialog.show();


                break;

            case R.id.send_button_invite:

                databaseReference = FirebaseDatabase.getInstance().getReference();
                DatabaseReference newRef = databaseReference.child(Constants.INVITES).push();

                updateInvites(newRef.getKey());
                newRef.setValue(invite);

                fragmentJump(otherUser);
                break;


        }
    }

    private void updateInvites(String key) {
        Map<String, String> otherUserMap = otherUser.getInvites();
        Map<String, String> currentUserMap = currentUser.getInvites();

        if (otherUserMap == null) {
            otherUserMap = new HashMap<>();
        }
        if (currentUserMap == null) {
            currentUserMap = new HashMap<>();
        }
        FilterUsersClass.incrementInvite(key, otherUserMap);
        otherUser.setInvites(otherUserMap);

        FilterUsersClass.incrementInvite(key, currentUserMap);
        currentUser.setInvites(currentUserMap);

        DatabaseReference databaseReferenceUsers = databaseReference.child(Constants.USERS);

        databaseReferenceUsers.child(currentUser.getuID()).setValue(currentUser);
        databaseReferenceUsers.child(otherUser.getuID()).setValue(otherUser);


    }

    UserDetailsFragment mFragment;

    private void fragmentJump(User mItemSelected) {
        mFragment = new UserDetailsFragment();
        Bundle mBundle = new Bundle();
        String userString = new Gson().toJson(mItemSelected);
        mBundle.putString("item_selected_key", userString);
        mFragment.setArguments(mBundle);
        switchContent(R.id.fragment_container, mFragment);
    }

    public void switchContent(int id, UserDetailsFragment fragment) {
        if (context == null)
            return;
        if (context instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) context;
            UserDetailsFragment frag = fragment;
            homeActivity.switchContent(id, frag);
        }

    }

}
