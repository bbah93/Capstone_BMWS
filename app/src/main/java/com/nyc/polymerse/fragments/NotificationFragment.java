package com.nyc.polymerse.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nyc.polymerse.Constants;
import com.nyc.polymerse.Invites.Invite_Schema;
import com.nyc.polymerse.R;
import com.nyc.polymerse.User;
import com.nyc.polymerse.UserSingleton;
import com.nyc.polymerse.controller.InviteItemController;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class NotificationFragment extends Fragment {

    private final String TAG = "NotificationFragment";
    View rootView;
    List<Invite_Schema> invitesList = new ArrayList<>();
    List<String> invitesIDs = new ArrayList<>();
    RecyclerView recyclerView;
    InviteItemController adapter;

    private LinearLayout linearLayout;

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

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        db = FirebaseDatabase.getInstance().getReference();
        User user = UserSingleton.getInstance().getUser();

        linearLayout = view.findViewById(R.id.nothing_here_notification);

        recyclerView = rootView.findViewById(R.id.user_notification_rec_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
        adapter = new InviteItemController(invitesList, getActivity().getSupportFragmentManager(), getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);
        recyclerView.setAdapter(adapter);

        db.child(Constants.USERS).child(user.getuID()).child(Constants.USER_CHILD_INVITES).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot.getChildrenCount() > 0) {
                    for (DataSnapshot d : dataSnapshot.getChildren()) {
                        if (d.getValue() != null) {
                            invitesIDs.add(d.getValue().toString());
                        }
                    }

                    for (int i = 0; i < invitesIDs.size(); i++) {

                        db.child(Constants.INVITES).child(invitesIDs.get(i)).addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {

                                Invite_Schema invite = dataSnapshot.getValue(Invite_Schema.class);
                                linearLayout.setVisibility(View.GONE);

                                invitesList.add(invite);
                                adapter.notifyDataSetChanged();

                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                    }

                } else {

                    linearLayout.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });

    }

}
