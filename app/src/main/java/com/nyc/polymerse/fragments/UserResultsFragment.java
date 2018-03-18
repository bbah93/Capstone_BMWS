package com.nyc.polymerse.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseError;
import com.nyc.polymerse.GetUserList;
import com.nyc.polymerse.R;
import com.nyc.polymerse.User;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserResultsFragment extends Fragment {

    View rootView;
    private GetUserList.onDataChangedUserInterface onDataChangedUserInterface;
    private final String TAG = "UserResultsFragment";

    public UserResultsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_user_results, container, false);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        onDataChangedUserInterface = new GetUserList.onDataChangedUserInterface() {
            @Override
            public void onSuccess(User user) {
                //This is the test user only;
                Log.d(TAG, "onSuccess: " + user.getCity());
            }

            @Override
            public void ofFailure(DatabaseError databaseError) {

            }
        };
    }
}
