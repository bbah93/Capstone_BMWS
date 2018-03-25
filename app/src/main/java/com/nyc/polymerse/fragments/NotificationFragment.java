package com.nyc.polymerse.fragments;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nyc.polymerse.Invites.Invite_Schema;
import com.nyc.polymerse.R;
import com.nyc.polymerse.User;
import com.nyc.polymerse.UserSingleton;
import com.nyc.polymerse.controller.InviteItemController;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment implements View.OnClickListener {

    View rootView;
    String popUpDesignator = "";
    List<Invite_Schema> invitesList = new ArrayList<>();
    List<String> invitesIDs = new ArrayList<>();

    private DatabaseReference db;

    public NotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_notification, container, false);

        db = FirebaseDatabase.getInstance().getReference().child("Invites");
        User user = UserSingleton.getInstance().getUser();
        Log.d("userId",user.getuID());
        Map<String, String> inviteMap = user.getInvites();
        for (String s : inviteMap.values()) {
            invitesIDs.add(s);
        }

        for (int i = 0; i < invitesIDs.size(); i++) {
            db.child(invitesIDs.get(i)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {
                    Invite_Schema invite = dataSnapshot.getValue(Invite_Schema.class);
                    invitesList.add(invite);

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }
        Toast.makeText(getActivity().getApplicationContext(), "SIZE = "+invitesList.size(), Toast.LENGTH_SHORT).show();

        RecyclerView recyclerView = rootView.findViewById(R.id.user_notification_rec_view);
        InviteItemController adapter = new InviteItemController(invitesList);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(),LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.user_notification_item, container, false);


        return rootView;
    }

    @Override
    public void onClick(View view) {
        int id = view.getId();
        switch (id) {
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

    public void showPopUp() {
        Bundle bundle = new Bundle();
        bundle.putString("pop_up_type", popUpDesignator);

        DialogFragment showPopUp = new InvitePopUpFragment();
        FragmentManager transaction = getActivity().getSupportFragmentManager();

        showPopUp.setArguments(bundle);
        transaction.beginTransaction().commit();

        showPopUp.show(getActivity().getSupportFragmentManager(), "popUp");
    }
}
