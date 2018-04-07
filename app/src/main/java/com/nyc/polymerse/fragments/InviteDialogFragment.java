package com.nyc.polymerse.fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

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
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class InviteDialogFragment extends android.support.v4.app.DialogFragment {

    private String popUpDesignator = "";
    private String question;
    private String status;

    private final String TAG = "InviteDialogFragment";

    private Invite_Schema invite;
    private TextView statusView;
    private TextView user;
    private TextView date;
    private TextView time;
    private CircleImageView otherUserImg;
    private TextView location;
    private Button goTo;

    private DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.INVITES);
    private final String ID = UserSingleton.getInstance().getUser().getuID();

    public InviteDialogFragment() {
        // Required empty public constructor
    }

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = LayoutInflater.from(getActivity());
        final View v = inflater.inflate(R.layout.invite_dialog, null);
        statusView = v.findViewById(R.id.dialog_invite_status);
        user = v.findViewById(R.id.dialog_username);
        date = v.findViewById(R.id.dialog_date);
        time = v.findViewById(R.id.dialog_time);
        otherUserImg = v.findViewById(R.id.invite_dialog_avatar);
        location = v.findViewById(R.id.dialog_address);
        goTo = v. findViewById(R.id.dialog_loc_button);
        statusView.setText("Status: "+invite.getAcceptStatus());
        time.setText(invite.getTime());
        date.setText(invite.getDate());
        user.setText(invite.getSenderName());
        location.setText(invite.getLocation());
        dialogBuilder.setView(v);
        if (ID == invite.getSender_ID()) {
            setAsSenderButtons(dialogBuilder, invite.getAcceptStatus());
        } else {
            setAsReceiverButton(dialogBuilder, invite.getAcceptStatus());
        }

        //get img of the other user through there profile
        String otherUser = "";
        if (ID.equals(invite.getSender_ID())){
            otherUser = invite.getReceiver_ID();
        } else {
            otherUser = invite.getSender_ID();
        }
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.USERS).child(otherUser);
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (dataSnapshot != null) {
                    User otherUser = dataSnapshot.getValue(User.class);
                    if (otherUser != null) {
                        Log.d(TAG, "onDataChange: otherUser " + otherUser.getUsername());
                        if (otherUser.getProfilePic() != null) {
                            Picasso.get().load(otherUser.getProfilePic()).placeholder(R.drawable.ic_account_circle_black_24dp).into(otherUserImg);
                        } else {
                            otherUserImg.setImageResource(R.drawable.ic_account_circle_black_24dp);
                        }

                    } else {
                        otherUserImg.setImageResource(R.drawable.ic_account_circle_black_24dp);
                    }
                } else {
                    otherUserImg.setImageResource(R.drawable.ic_account_circle_black_24dp);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        });
        return dialogBuilder.create();

    }

    public void setInvite(Invite_Schema invite) {
        this.invite = invite;
    }

    public void setAsSenderButtons(AlertDialog.Builder builder, String status) {
        switch (status) {
            case "accepted":
                builder.setPositiveButton("Cancel Meet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        invite.setAcceptStatus("cancelled");
                        updateInviteStatus();
                    }
                });
                break;
            case "rejected":
                builder.setPositiveButton("Delete Invite", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        invite.setAcceptStatus("deleted");
                        updateInviteStatus();
                    }
                });
                break;
            case "pending":
                builder.setPositiveButton("Cancel Invite", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        invite.setAcceptStatus("cancelled");
                        confirmationDialog();
                    }
                });
                break;
            case "cancelled":
                builder.setPositiveButton("Delete Invite", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        invite.setAcceptStatus("cancelled");
                        confirmationDialog();
                    }
                });
                break;

        }

    }

    public void setAsReceiverButton(AlertDialog.Builder builder, String status) {
        switch (status) {
            case "accepted":
                builder.setPositiveButton("Cancel Meet", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        invite.setAcceptStatus("cancelled");
                        updateInviteStatus();
                    }
                });
                break;
            case "rejected":
                builder.setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        invite.setAcceptStatus("deleted");
                        updateInviteStatus();
                    }
                });
                break;
            case "pending":
                builder.setPositiveButton("Accept", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        invite.setAcceptStatus("accepted");
                        updateInviteStatus();
                    }
                });
                builder.setNegativeButton("Deny", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        invite.setAcceptStatus("rejected");
                        updateInviteStatus();
                    }
                });
                break;
            case "cancelled":
                builder.setPositiveButton("Delete Invite", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        invite.setAcceptStatus("cancelled");
                        confirmationDialog();
                    }
                });
                break;

        }

    }

    public void confirmationDialog() {

        switch (popUpDesignator) {
            case "Delete":
                status = "deleted";
                question = "Are you sure you want to delete this invite?";
                break;
            case "Cancel":
                status = "cancelled";
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setMessage(question);

        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                invite.setAcceptStatus(status);
                updateInviteStatus();
                //fragmentJump(new NotificationFragment());
            }
        });
        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                // User cancelled the dialog
            }
        });

        
        builder.show();

    }

    private void updateInviteStatus() {

        databaseReference.child(invite.getInvite_ID())
                .addListenerForSingleValueEvent(new ValueEventListener() {
                                                    @Override
                                                    public void onDataChange(DataSnapshot dataSnapshot) {

                                                        databaseReference.child(invite.getInvite_ID()).setValue(invite);
                                                    }

                                                    @Override
                                                    public void onCancelled(DatabaseError databaseError) {
                                                    }
                                                }
                );
    }

}


