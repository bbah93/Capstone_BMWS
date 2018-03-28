package com.nyc.polymerse.controller;

import android.support.v4.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nyc.polymerse.Invites.Invite_Schema;
import com.nyc.polymerse.R;
import com.nyc.polymerse.fragments.InvitePopUpFragment;

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

            Log.d("INVITE ID", invite.getSender_ID() + "");

            date.setText(invite.getDate());
            time.setText(invite.getTime());
            location.setText(invite.getLocation());
            cancel.setOnClickListener(this);
            deny.setOnClickListener(this);
            delete.setOnClickListener(this);
            confirm.setOnClickListener(this);

            if ("tJqOPTGtMOPO4e3laJo7KrT3j272".equals(invite.getSender_ID())) {
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
            accepted.setImageResource(R.drawable.ic_cancel_red_500_18dp);
            accepted.setVisibility(View.VISIBLE);
            cancel.setVisibility(View.GONE);
            deny.setVisibility(View.GONE);
            confirm.setVisibility(View.GONE);
            delete.setVisibility(View.VISIBLE);
        }

        public void showPopUp() {
            Bundle bundle = new Bundle();
            bundle.putString("pop_up_type", popUpDesignator);

            DialogFragment showPopUp = new InvitePopUpFragment();
            FragmentManager transaction = fragmentManager;

            showPopUp.setArguments(bundle);
            transaction.beginTransaction().commit();

            showPopUp.show(fragmentManager, "popUp");
        }

        @Override
        public void onClick(View view) {
            int id = view.getId();
            switch (id) {
                case R.id.delete:
                    popUpDesignator = "Delete";
                    showPopUp();
                    break;
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
                    itemView.getContext().startActivity(mapIntent);
                    break;

            }
        }
    }
}
