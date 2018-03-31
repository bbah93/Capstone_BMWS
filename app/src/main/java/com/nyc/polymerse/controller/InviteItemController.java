package com.nyc.polymerse.controller;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.graphics.Color;
import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nyc.polymerse.Constants;
import com.nyc.polymerse.Invites.Invite_Schema;
import com.nyc.polymerse.R;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Shant on 3/24/2018.
 */

public class InviteItemController extends RecyclerView.Adapter<InviteItemController.InviteItemViewHolder> {

    List<Invite_Schema> inviteList = new ArrayList<>();
    FragmentManager fragmentManager;

    public InviteItemController(List<Invite_Schema> inviteList, FragmentManager fragmentManager) {
        this.inviteList = inviteList;
        this.fragmentManager = fragmentManager;
    }

    public void setData(List<Invite_Schema> list) {
        inviteList = list;
        notifyDataSetChanged();
    }

    @Override
    public InviteItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_notification_item, parent, false);
        return new InviteItemViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(InviteItemViewHolder holder, int position) {
        Invite_Schema invite_schema = inviteList.get(position);
        holder.onBind(invite_schema);
    }

    @Override
    public int getItemCount() {
        return inviteList.size();
    }

    public class InviteItemViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private Button confirm;
        private Button deny;
        private Button cancel;
        private TextView otherUserName;
        private TextView location;
        private ImageView accepted;
        private TextView date;
        private TextView time;
        private CircleImageView otherUserImg;
        private Button delete;
        private String popUpDesignator = "";
        private Invite_Schema invite_schema;
        private String question;
        private String status;
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.INVITES);

        public InviteItemViewHolder(View itemView) {
            super(itemView);
            confirm = itemView.findViewById(R.id.confirm);
            deny = itemView.findViewById(R.id.deny);
            cancel = itemView.findViewById(R.id.cancel);
            otherUserName = itemView.findViewById(R.id.user_notification_user_name);
            location = itemView.findViewById(R.id.invite_item_location);
            accepted = itemView.findViewById(R.id.accepted);
            date = itemView.findViewById(R.id.date);
            time = itemView.findViewById(R.id.time);
            otherUserImg = itemView.findViewById(R.id.user_avatar_user_notification);
            delete = itemView.findViewById(R.id.delete);

        }

        public void onBind(Invite_Schema invite) {
            invite_schema = invite;

            Log.d("INVITE ID", invite.getSender_ID() + "");

            date.setText(invite.getDate());
            time.setText(invite.getTime());
            location.setText(invite.getLocation());
            cancel.setOnClickListener(this);
            deny.setOnClickListener(this);
            delete.setOnClickListener(this);
            confirm.setOnClickListener(this);

            if ("tJqOPTGtMOPO4e3laJo7KrT3j272".equals(invite.getSender_ID())) {
                Toast.makeText(itemView.getContext(), invite.getAcceptStatus(), Toast.LENGTH_SHORT).show();

                switch (invite.getAcceptStatus()) {
                    case "accepted":
                        //NEED TO GET OTHER USER'S NAME AND IMAGE
                        otherUserName.setText(userResponse(invite.getReciever_ID(), "accepted!"));
                        accepted.setImageResource(R.drawable.ic_check_circle_green_a700_18dp);
                        accepted.setVisibility(View.VISIBLE);
                        cancel.setVisibility(View.VISIBLE);
                        deny.setVisibility(View.GONE);
                        confirm.setVisibility(View.GONE);
                        delete.setVisibility(View.GONE);
                        //otherUserImg.setImageResource(invite.getReciever_ID());
                        break;
                    case "pending":
                        otherUserName.setText(userResponse(invite.getReciever_ID(), "has not responded"));
                        accepted.setImageResource(R.mipmap.hourglass);
                        accepted.setVisibility(View.VISIBLE);
                        cancel.setVisibility(View.VISIBLE);
                        deny.setVisibility(View.GONE);
                        confirm.setVisibility(View.GONE);
                        delete.setVisibility(View.GONE);
                        otherUserImg.setImageResource(R.mipmap.man);
                        break;
                    case "rejected":
                        otherUserName.setText(userResponse(invite.getReciever_ID(), "cannot meet"));
                        accepted.setImageResource(R.drawable.ic_cancel_red_500_18dp);
                        accepted.setVisibility(View.VISIBLE);
                        cancel.setVisibility(View.GONE);
                        deny.setVisibility(View.GONE);
                        confirm.setVisibility(View.GONE);
                        delete.setVisibility(View.VISIBLE);
                        otherUserImg.setImageResource(R.mipmap.man);
                        break;
                    case "cancelled":

                        cancel();
                        otherUserImg.setImageResource(R.mipmap.man);
                        break;
                }
            } else {
                switch (invite.getAcceptStatus()) {
                    case "accepted":
                        otherUserName.setText(userResponse("You are meeting", invite.getSender_ID()));
                        accepted.setImageResource(R.drawable.ic_check_circle_green_a700_18dp);
                        accepted.setVisibility(View.VISIBLE);
                        cancel.setVisibility(View.VISIBLE);
                        deny.setVisibility(View.GONE);
                        confirm.setVisibility(View.GONE);
                        delete.setVisibility(View.GONE);
                        otherUserImg.setImageResource(R.mipmap.man);
                        break;
                    case "pending":
                        otherUserName.setText(userResponse("Respond to", invite.getSender_ID()));
                        accepted.setVisibility(View.GONE);
                        cancel.setVisibility(View.VISIBLE);
                        deny.setVisibility(View.VISIBLE);
                        confirm.setVisibility(View.VISIBLE);
                        delete.setVisibility(View.GONE);
                        otherUserImg.setImageResource(R.mipmap.man);
                        break;
                    case "rejected":
                        itemView.setVisibility(View.GONE);
                        break;
                    case "cancelled":
                        cancel();
                        otherUserImg.setImageResource(R.mipmap.man);
                        break;
                }
            }

        }

        public String userResponse(String first, String second) {
            StringBuilder userResponse = new StringBuilder();
            userResponse.append(first);
            userResponse.append(" " + second);
            return userResponse.toString();
        }

        public void cancel() {

            otherUserName.setText(userResponse("Invite", "cancelled"));
            otherUserName.setTextColor(Color.rgb(230,34,49));
            accepted.setImageResource(R.drawable.ic_cancel_red_500_18dp);
            accepted.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.GONE);
            deny.setVisibility(View.GONE);
            confirm.setVisibility(View.GONE);
            delete.setVisibility(View.VISIBLE);
        }


        public void buildDialog() {
            Toast.makeText(itemView.getContext(), "CLICKKK", Toast.LENGTH_SHORT).show();
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

            AlertDialog.Builder builder = new AlertDialog.Builder(itemView.getContext());

            builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    invite_schema.setAcceptStatus(status);
                    updateInviteStatus();

                }
            });
            builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
                public void onClick(DialogInterface dialog, int id) {
                    // User cancelled the dialog
                }
            });
            builder.setMessage(question);

            builder.create();
            builder.show();
        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id) {
                case R.id.delete:
                    popUpDesignator = "Delete";
                    buildDialog();
                    break;
                case R.id.cancel:

                    popUpDesignator = "Cancel";
                    buildDialog();
                    break;
                case R.id.deny:
                    popUpDesignator = "Deny";
                    buildDialog();
                    break;
                case R.id.confirm:
                    popUpDesignator = "Accept";
                    buildDialog();
                    break;
                case R.id.map_icon:
                    Uri gmmIntentUri = Uri.parse("google.streetview:cbll=46.414382,10.013988");
                    Intent mapIntent = new Intent(Intent.ACTION_VIEW, gmmIntentUri);
                    mapIntent.setPackage("com.google.android.apps.maps");
                    itemView.getContext().startActivity(mapIntent);
                    break;

            }
        }

        private void updateInviteStatus() {

            databaseReference.child(invite_schema.getInvite_ID())
                    .addListenerForSingleValueEvent(new ValueEventListener() {
                                                        @Override
                                                        public void onDataChange(DataSnapshot dataSnapshot) {

                                                            databaseReference.child(invite_schema.getInvite_ID()).setValue(invite_schema);
                                                        }

                                                        @Override
                                                        public void onCancelled(DatabaseError databaseError) {
                                                        }
                                                    }
                    );
        }
    }
}
