package com.nyc.polymerse.fragments;


import android.content.Context;
import android.content.SharedPreferences;
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
import com.nyc.polymerse.Constants;
import com.nyc.polymerse.R;
import com.nyc.polymerse.User;
import com.nyc.polymerse.controller.MessageListAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessagingListFrag extends Fragment {

    private View rootView;
    private DatabaseReference databaseReference;
    public static final String TAG = "MessagingListFrag";
    private String uID;
    private List<User> userList;
    private List<String> msgKeys;
    private SharedPreferences sharedPreferences;

    public MessagingListFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_messaging_list, container, false);
        sharedPreferences = rootView.getContext().getSharedPreferences(Constants.FIREBASE_UID, Context.MODE_PRIVATE);
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        userList = new ArrayList<>();
        msgKeys = new ArrayList<>();
        //I needed the uID of the current user here but I only ever got it from auth so I put it in a sharedpreference.
        uID = sharedPreferences.getString(Constants.FIREBASE_UID_KEY, "");
        Log.d(TAG, "onViewCreated: uID " + uID);

        //Here I get a reference to the msgs stored for this user and the uID of the user he/she/it msg'd.
        databaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.MESSAGE).child(uID);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "onDataChange: msg " + dataSnapshot.getChildrenCount());
                Log.d(TAG, "onDataChange: msg " + dataSnapshot.getKey());
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    msgKeys.add(d.getKey());
                    Log.d(TAG, "onDataChange: msg " + d.getKey());
                    for (DataSnapshot ds : d.getChildren()) {
                        Log.d(TAG, "onDataChange: msg " + ds.getKey());
                    }
                }
                getUsers();


            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

    private void getUsers() {

        //Here I find the User msg'ing the current user by there ID.
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot ds : dataSnapshot.getChildren()) {
                    User user = ds.getValue(User.class);
                    for (String s : msgKeys) {
                        if (s.equals(user.getuID())) {
                            userList.add(user);
                            Log.d(TAG, "onDataChange: " + user.getUsername());
                        }
                    }

                    RecyclerView recyclerView = rootView.findViewById(R.id.msg_recview);
                    LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                    recyclerView.setLayoutManager(manager);
                    MessageListAdapter adapter = new MessageListAdapter(userList, rootView.getContext());
                    recyclerView.setAdapter(adapter);
                }
                Log.d(TAG, "onDataChange: user " + userList.size());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
    }
}
