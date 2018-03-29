package com.nyc.polymerse.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nyc.polymerse.Constants;

import java.util.HashMap;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class InvitePopUpFragment extends DialogFragment {

    String typeOfPopUp = "";
    String inviteID = "";
    String question = "";
    String status = "";
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.INVITES);

    public InvitePopUpFragment() {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        Bundle bundle = getArguments();
        typeOfPopUp = bundle.getString("pop_up_type");
        inviteID = bundle.getString("invite_id");

        switch (typeOfPopUp) {
            case "Delete":
                status = "deleted";
                question = "Are you sure you want to delete this invite?";
                break;
            case "Cancel":
                status = "canceled";
                question = "Are you sure you want to cancel this event?";
                break;
            case "Deny":
                status = "denied";
                question = "Are you sure you want to reject this invite?";
                break;
            case "Accept":
                status = "accepted";
                question = "Are you sure you want to accept this invite?";
                break;

        }

        return changeStatus(inviteID, status);
    }

    public AlertDialog changeStatus(String id, String newStatus) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(question)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        updateInviteStatus();
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();

    }

    private void updateInviteStatus() {
        databaseReference.child(inviteID)
                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {
                                                        Map<String, Object> postValues = new HashMap<String, Object>();
                                                        for (DataSnapshot snapshot : dataSnapshot.getChildren()) {
                                                            postValues.put(snapshot.getKey(), snapshot.getValue());
                                                            Log.d("DB_KEYS", snapshot.getKey());

                                                        }

                                                        postValues.put("acceptStatus", status);
                                                        databaseReference.child(inviteID).updateChildren(postValues);
                                                    }

                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {
                                                    }
                                                }
                );
    }

}
