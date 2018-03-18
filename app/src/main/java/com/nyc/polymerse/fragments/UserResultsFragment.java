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

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
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
    private final String TAG = "UserResultsFragment";
    private List<User> userList = new ArrayList<>();

    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseUser;

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
        Log.d(TAG,"ON VIEW CREATED");
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabaseUser = mDatabase.child("Users").child("Test");
        ValueEventListener userEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                User post = dataSnapshot.getValue(User.class);
                if (post != null) {
                    Log.d(TAG, "onDataChange: " + post.getUsername());
                    //This is an interface to put the data into a different activity.
                    for (int i = 0; i < 5; i++) {
                        userList.add(post);
                    }

                    RecyclerView recyclerView = rootView.findViewById(R.id.user_results_rec_view);
                    LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(manager);
                    UserResultAdapter adapter = new UserResultAdapter(userList);
                    recyclerView.setAdapter(adapter);
                    //This is the test user only;
                    Log.d(TAG, "onSuccess: " + post.getCity());
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            };

        };
        mDatabaseUser.addValueEventListener(userEventListener);

    }
}
