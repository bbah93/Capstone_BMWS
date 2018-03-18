package com.nyc.polymerse;

import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;


/**
 * Created by Wayne Kellman on 3/18/18.
 */

public class GetUserList {

    private final String TAG = "UserDataBase";
    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseUser;
// ...


    public void getUserList() {
        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabaseUser = mDatabase.child("Users").child("Test");
        ValueEventListener postListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                User post = dataSnapshot.getValue(User.class);
                Log.d(TAG, "onDataChange: " + post.getUsername());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w(TAG, "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };
        mDatabaseUser.addValueEventListener(postListener);
    }
}
