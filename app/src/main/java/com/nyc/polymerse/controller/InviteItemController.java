package com.nyc.polymerse.controller;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.nyc.polymerse.Invites.Invite_Schema;
import com.nyc.polymerse.R;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Shant on 3/24/2018.
 */

public class InviteItemController extends RecyclerView.Adapter {
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return null;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }

    public class InviteItemViewHolder extends RecyclerView.ViewHolder{

        Button confirm;
        Button deny;
        Button cancel;
        TextView otherUserName;
        TextView location;
        ImageView accepted;
        TextView date;
        TextView time;
        CircleImageView otherUserImg;
        Button delete;

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

        public void onBind(Invite_Schema invite){

            date.setText(invite.getDate());
            time.setText(invite.getTime());
            location.setText(invite.getLocation());

            if("currentId" == invite.getSender_ID()){
                switch(invite.getAcceptStatus()){
                    case "accepted":
                        //NEED TO GET OTHER USER'S NAME AND IMAGE
                        otherUserName.setText(userResponse(invite.getReceiver_ID(),"accepted!"));
                        accepted.setImageResource(R.drawable.ic_check_circle_green_a700_18dp);
                        accepted.setVisibility(View.VISIBLE);
                        cancel.setVisibility(View.VISIBLE);
                        deny.setVisibility(View.GONE);
                        confirm.setVisibility(View.GONE);
                        delete.setVisibility(View.GONE);
                        //otherUserImg.setImageResource(invite.getReceiver_ID());
                        break;
                    case "pending":
                        otherUserName.setText(userResponse(invite.getReceiver_ID(),"has not responded"));
                        accepted.setImageResource(R.mipmap.hourglass);
                        accepted.setVisibility(View.VISIBLE);
                        cancel.setVisibility(View.VISIBLE);
                        deny.setVisibility(View.GONE);
                        confirm.setVisibility(View.GONE);
                        delete.setVisibility(View.GONE);
                        //otherUserImg.setImageResource(invite.getReceiver_ID());
                        break;
                    case "rejected":
                        otherUserName.setText(userResponse(invite.getReceiver_ID(),"cannot meet"));
                        accepted.setImageResource(R.drawable.ic_cancel_red_500_18dp);
                        accepted.setVisibility(View.VISIBLE);
                        cancel.setVisibility(View.GONE);
                        deny.setVisibility(View.GONE);
                        confirm.setVisibility(View.GONE);
                        delete.setVisibility(View.VISIBLE);
                        //otherUserImg.setImageResource(invite.getReceiver_ID());
                        break;
                    case "cancelled":
                        otherUserName.setText(userResponse(invite.getReceiver_ID(),"cancelled"));
                        accepted.setImageResource(R.drawable.ic_cancel_red_500_18dp);
                        accepted.setVisibility(View.VISIBLE);
                        cancel.setVisibility(View.GONE);
                        deny.setVisibility(View.GONE);
                        confirm.setVisibility(View.GONE);
                        delete.setVisibility(View.VISIBLE);
                        //otherUserImg.setImageResource(invite.getReceiver_ID());
                        break;
                }
            } else {
                switch(invite.getAcceptStatus()){
                    case "accepted":
                        break;
                    case "pending":
                        break;
                    case "rejected":
                        break;
                    case "cancelled":
                        break;
                }
            }

        }

        public String userResponse(String name,String response){
            StringBuilder userResponse = new StringBuilder();
            userResponse.append(name);
            userResponse.append(" "+response);
            return userResponse.toString();
        }



    }
}
