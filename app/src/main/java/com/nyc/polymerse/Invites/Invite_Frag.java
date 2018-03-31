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
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.nyc.polymerse.Constants;
import com.nyc.polymerse.FilterUsersClass;
import com.nyc.polymerse.HomeActivity;
import com.nyc.polymerse.R;
import com.nyc.polymerse.User;
import com.nyc.polymerse.UserSingleton;
import com.nyc.polymerse.fragments.SuggestedLocationsFragment;
import com.nyc.polymerse.fragments.UserDetailsFragment;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class Invite_Frag extends Fragment implements View.OnClickListener {

    EditText time, date;
    Button dateButton, timeButton, send, location;
    TextView locationText;

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
        invite.setLocation("");
        invite.setTime("");
        invite.setDate("");
        currentUser = UserSingleton.getInstance().getUser();
        invite.setSender_ID(currentUser.getuID());
        invite.setReceiver_ID(otherUser.getuID());

        time = v.findViewById(R.id.time);
        date = v.findViewById(R.id.date);
        send = v.findViewById(R.id.send_button_invite);
        locationText = v.findViewById(R.id.location);

        //here I'm getting the time and date I sent to locations
        String timeString = bundle.getString("time_was_selected", "");

        if (!timeString.equals("")) {
            invite.setTime(timeString);
            time.setText(timeString);
        }
        String dateString = bundle.getString("date_was_selected", "");
        if (!dateString.equals("")) {
            invite.setDate(dateString);
            date.setText(dateString);
        }
        String locationString = bundle.getString("vicinity_selected", "");
        if (!locationString.equals("")) {
            invite.setLocation(locationString);
            locationText.setText(locationString);
        }

        time.setText(timeString);
        date.setText(dateString);

        dateButton = v.findViewById(R.id.date_picker);
        timeButton = v.findViewById(R.id.time_picker);
        location = v.findViewById(R.id.location_button);

        dateButton.setOnClickListener(this);
        timeButton.setOnClickListener(this);
        send.setOnClickListener(this);
        location.setOnClickListener(this);

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
                final int minute = calendar1.get(Calendar.MINUTE);

                timePickerDialog = new TimePickerDialog(getActivity(), new TimePickerDialog.OnTimeSetListener() {
                    @Override
                    public void onTimeSet(TimePicker view, int hourOfDay, int minute) {
                        String timeString = hourOfDay + " : " + minute;
                        time.setText(timeString);

                        invite.setTime(hourOfDay + " : " + minute);

                    }
                }, hour, minute, false);
                timePickerDialog.show();

                break;

            case R.id.send_button_invite:
                if (!invite.getDate().equals("") && !invite.getTime().equals("") && !invite.getLocation().equals("")) {
                    databaseReference = FirebaseDatabase.getInstance().getReference();
                    DatabaseReference newRef = databaseReference.child(Constants.INVITES).push();

                    updateInvites(newRef.getKey());
                    invite.setInvite_ID(newRef.getKey());
                    newRef.setValue(invite);

                    fragmentJump(otherUser, new UserDetailsFragment());
                } else {
                    Toast.makeText(getActivity(), "Please Fill all areas", Toast.LENGTH_SHORT).show();
                }
                break;

            case R.id.location_button:
                SuggestedLocationsFragment suggestedLocationsFragment = new SuggestedLocationsFragment();

                fragmentJump(otherUser, new SuggestedLocationsFragment());
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

    private void fragmentJump(User mItemSelected, Fragment mFragment) {
        Bundle mBundle = new Bundle();
        String userString = new Gson().toJson(mItemSelected);
        mBundle.putString("item_selected_key", userString);

        // I want to take the time and date and send it to the fragment,
        // then send it back so it doesn't disappear and I don't want to send it to shared pref s
        // so other invites to get it.
        if (mFragment instanceof SuggestedLocationsFragment) {
            if (!time.getText().toString().isEmpty()) {
                mBundle.putString("time_was_selected", time.getText().toString());
            }
            if (!date.getText().toString().isEmpty()) {
                mBundle.putString("date_was_selected", date.getText().toString());
            }
        }
        mFragment.setArguments(mBundle);
        switchContent(R.id.fragment_container, mFragment);
    }

    public void switchContent(int id, Fragment fragment) {
        if (context == null)
            return;
        if (context instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) context;
            homeActivity.switchContent(id, fragment);
        }

    }

}
