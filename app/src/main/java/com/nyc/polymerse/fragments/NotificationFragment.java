package com.nyc.polymerse.fragments;


import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.nyc.polymerse.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment implements View.OnClickListener {

    View rootView;
    Button cancel;
    Button accept;
    Button deny;
    Button directions;
    String popUpDesignator = "";


    public NotificationFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.user_notification_item, container, false);
        rootView = inflater.inflate(R.layout.user_notification_item, container, false);
        cancel = rootView.findViewById(R.id.cancel);
        accept = rootView.findViewById(R.id.confirm);
        deny = rootView.findViewById(R.id.deny);
        directions = rootView.findViewById(R.id.map_icon);
        directions.setOnClickListener(this);
        cancel.setOnClickListener(this);
        accept.setOnClickListener(this);
        deny.setOnClickListener(this);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch(id){
            case R.id.cancel:
                popUpDesignator = "Cancel";
                showPopUp();
                break;
            case R.id.deny:
                popUpDesignator = "Deny";
                showPopUp();
                break;
            case R.id.confirm:
                popUpDesignator = "Accept";
                showPopUp();
                break;
            case R.id.map_icon:
                Uri gmmIntentUri = Uri.parse("google.streetview:cbll=46.414382,10.013988");
                Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                mapIntent.setPackage("com.google.android.apps.maps");
                startActivity(mapIntent);
                break;
        }

    }

    public void showPopUp(){
        Bundle bundle = new Bundle();
        bundle.putString("pop_up_type",popUpDesignator);

        DialogFragment showPopUp = new InvitePopUpFragment();
        FragmentManager transaction = getActivity().getSupportFragmentManager();

        showPopUp.setArguments(bundle);
        transaction.beginTransaction().commit();

        showPopUp.show(getActivity().getSupportFragmentManager(),"popUp");
    }
}
