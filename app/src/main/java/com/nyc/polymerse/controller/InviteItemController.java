package com.nyc.polymerse.controller;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nyc.polymerse.Constants;
import com.nyc.polymerse.HomeActivity;
import com.nyc.polymerse.Invites.Invite_Schema;
import com.nyc.polymerse.R;
import com.nyc.polymerse.User;
import com.nyc.polymerse.UserSingleton;
import com.nyc.polymerse.fragments.InviteDialogFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Shant on 3/24/2018.
 */

public class InviteItemController extends RecyclerView.Adapter<InviteItemController.InviteItemViewHolder> {

    List<Invite_Schema> inviteList = new ArrayList<>();
    FragmentManager fragmentManager;
    Context context;

    public InviteItemController(List<Invite_Schema> inviteList, FragmentManager fragmentManager, Context context) {
        this.inviteList = inviteList;
        this.fragmentManager = fragmentManager;
        this.context = context;
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

    public class InviteItemViewHolder extends RecyclerView.ViewHolder {

        private TextView otherUserName;
        private TextView acceptStatus;
        private ImageView accepted;
        private TextView date;
        private TextView details;
        private CircleImageView otherUserImg;
        private String status;
        private Invite_Schema inviteSchema;
        private final String ID = UserSingleton.getInstance().getUser().getuID();
        private final String TAG = "InviteItemViewHolder";

        public InviteItemViewHolder(View itemView) {
            super(itemView);
            otherUserName = itemView.findViewById(R.id.user_notification_user_name);
            acceptStatus = itemView.findViewById(R.id.status_update);
            accepted = itemView.findViewById(R.id.accepted);
            date = itemView.findViewById(R.id.date);
            details = itemView.findViewById(R.id.go_to_details);
            otherUserImg = itemView.findViewById(R.id.user_avatar_user_notification);
        }

        public void onBind(Invite_Schema invite) {
            inviteSchema = invite;

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    inviteDialog();
                }
            });
            details.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    inviteDialog();
                }
            });

            //get img of the other user through there profile
            String otherUser = "";
            if (ID.equals(invite.getSender_ID())) {
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

            status = invite.getAcceptStatus();
            date.setText(invite.getDate());

            if (ID.equals(invite.getSender_ID())) {
                switch (invite.getAcceptStatus()) {
                    case "accepted":
                        //NEED TO GET OTHER USER'S NAME AND IMAGE
                        otherUserName.setText(invite.getReceiverName());
                        acceptStatus.setText("accepted!");
                        accepted.setImageResource(R.drawable.ic_check_circle_green_a700_18dp);
                        accepted.setVisibility(View.VISIBLE);
                        //otherUserImg.setImageResource(invite.get);
                        break;
                    case "pending":
                        otherUserName.setText(invite.getReceiverName());
                        acceptStatus.setText("has not responded");
                        accepted.setImageResource(R.mipmap.hourglass);
                        accepted.setVisibility(View.VISIBLE);
                        otherUserImg.setImageResource(R.mipmap.man);
                        break;
                    case "rejected":
                        otherUserName.setText(invite.getReceiverName());
                        acceptStatus.setText("cannot meet");
                        accepted.setImageResource(R.drawable.ic_cancel_red_500_18dp);
                        accepted.setVisibility(View.VISIBLE);
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
                        otherUserName.setText(invite.getSenderName());
                        acceptStatus.setText("and you are meeting!");
                        accepted.setImageResource(R.drawable.ic_check_circle_green_a700_18dp);
                        accepted.setVisibility(View.VISIBLE);
                        otherUserImg.setImageResource(R.mipmap.man);
                        break;
                    case "pending":
                        otherUserName.setText(invite.getSenderName());
                        acceptStatus.setText("is waiting for a response");
                        accepted.setVisibility(View.GONE);
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
            acceptStatus.setVisibility(View.GONE);
            accepted.setImageResource(R.drawable.ic_cancel_red_500_18dp);
            accepted.setVisibility(View.VISIBLE);

        }

        public void inviteDialog() {
            InviteDialogFragment inviteDialogFragment = new InviteDialogFragment();
            inviteDialogFragment.setInvite(inviteSchema);
            android.support.v4.app.FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
            inviteDialogFragment.show(fragmentTransaction, "Invite Dialog");

        }

        private void fragmentJump(Fragment mFragment) {
            switchContent(R.id.fragment_container, mFragment);
        }

        public void switchContent(int id, Fragment fragment) {
            if (context == null)
                return;
            if (context instanceof HomeActivity) {
                HomeActivity homeActivity = (HomeActivity) context;
                homeActivity.switchContent(id, fragment);
            }

        }
    }
}
