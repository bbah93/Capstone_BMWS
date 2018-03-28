package com.nyc.polymerse.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nyc.polymerse.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class InvitePopUpFragment extends DialogFragment {

    String typeOfPopUp = "";

    public InvitePopUpFragment() {

    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        // Use the Builder class for convenient dialog construction
        Bundle bundle = getArguments();
        typeOfPopUp = bundle.getString("pop_up_type");
        String question = "";

        switch (typeOfPopUp) {
            case "Delete":
                question = "Are you sur you want to delete this invite?";
                break;
            case "Cancel":
                question = "Are you sure you want to cancel this event?";
                break;
            case "Deny":
                question = "Are you sure you want to reject this invite?";
                break;
            case "Accept":
                question = "Are you sure you want to accept this invite?";
                break;

        }

        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        builder.setMessage(question)
                .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                    }
                })
                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                    }
                });
        // Create the AlertDialog object and return it
        return builder.create();
    }

}
