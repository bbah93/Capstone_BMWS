package com.nyc.polymerse.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseError;
import com.nyc.polymerse.GetUserList;
import com.nyc.polymerse.R;
import com.nyc.polymerse.User;
import com.nyc.polymerse.controller.UserResultAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserResultsFragment extends Fragment {

    View rootView;
    private GetUserList.onDataChangedUserInterface onDataChangedUserInterface;
    private final String TAG = "UserResultsFragment";
    private List<User> userList = new ArrayList<>();

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
                for(int i = 0; i < 5; i++){
                    userList.add(user);
                }
                RecyclerView recyclerView = rootView.findViewById(R.id.user_results_rec_view);
                LinearLayoutManager manager = new LinearLayoutManager(getContext(),LinearLayoutManager.VERTICAL,false);
                recyclerView.setLayoutManager(manager);
                UserResultAdapter adapter = new UserResultAdapter(userList);
                //This is the test user only;
                Log.d(TAG, "onSuccess: " + user.getCity());
            }

            @Override
            public void ofFailure(DatabaseError databaseError) {

            }
        };
    }
}
