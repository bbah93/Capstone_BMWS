package com.nyc.polymerse.fragments;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import static android.content.ContentValues.TAG;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {

    View rootView;
    List<Invite_Schema> invitesList = new ArrayList<>();
    List<String> invitesIDs = new ArrayList<>();
    RecyclerView recyclerView;

    private DatabaseReference db;

    public NotificationFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_notification, container, false);

        return rootView;
    }

    public class PopulateInvites extends AsyncTask<Void, Void, List<Invite_Schema>> {

        @Override
        protected List<Invite_Schema> doInBackground(Void... voids) {

            for (int i = 0; i < invitesIDs.size(); i++) {
                db.child(invitesIDs.get(i)).addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(DataSnapshot dataSnapshot) {

                        Invite_Schema invite = dataSnapshot.getValue(Invite_Schema.class);

                        if (invitesIDs.contains(dataSnapshot.getKey())) {
                            invitesList.add(invite);

                        }
                    }

                    @Override
                    public void onCancelled(DatabaseError databaseError) {

                    }
                });
            }
            return invitesList;
        }

        @Override
        protected void onPostExecute(List<Invite_Schema> list) {
            Log.d("ON POST EXC:", "" + list.size());
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = FirebaseDatabase.getInstance().getReference().child("Invites");
        User user = UserSingleton.getInstance().getUser();
        Log.d("userId", user.getuID());
        Map<String, String> inviteMap = user.getInvites();
        for (String s : inviteMap.values()) {
            invitesIDs.add(s);
        }
        recyclerView = rootView.findViewById(R.id.user_notification_rec_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        final InviteItemController adapter = new InviteItemController(invitesList, getActivity().getSupportFragmentManager());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        for (int i = 0; i < invitesIDs.size(); i++) {
            db.child(invitesIDs.get(i)).addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(DataSnapshot dataSnapshot) {

                    Invite_Schema invite = dataSnapshot.getValue(Invite_Schema.class);
                    invitesList.add(invite);
                    adapter.notifyDataSetChanged();

                }

                @Override
                public void onCancelled(DatabaseError databaseError) {

                }
            });
        }

    }

}
