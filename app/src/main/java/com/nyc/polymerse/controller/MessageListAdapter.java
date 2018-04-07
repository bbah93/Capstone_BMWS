package com.nyc.polymerse.controller;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nyc.polymerse.HomeActivity;
import com.nyc.polymerse.R;
import com.nyc.polymerse.User;
import com.nyc.polymerse.fragments.MessageFragment;
import com.squareup.picasso.Picasso;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Wayne Kellman on 3/21/18.
 */

public class  MessageListAdapter extends RecyclerView.Adapter<MessageListAdapter.ViewHoler>{

    private List<User> userList;
    private Context context;
    private ProgressBar progressBar;

    public MessageListAdapter(List<User> userList, Context context, ProgressBar progressBar) {
        this.userList = userList;
        this.context = context;
        this.progressBar = progressBar;
        if (userList.size() < 1) {
            progressBar.setVisibility(View.INVISIBLE);
        }
    }

    @Override
    public ViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.msg_list_itemview,parent,false);
        return new ViewHoler(view);
    }

    @Override
    public void onBindViewHolder(ViewHoler holder, final int position) {

        progressBar.setVisibility(View.INVISIBLE);
        holder.textView.setText(userList.get(position).getUsername());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentJump(userList.get(position));
            }
        });
        Picasso.get().load(userList.get(position).getProfilePic()).placeholder(R.drawable.ic_account_circle_black_24dp).into(holder.avatar);
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class ViewHoler extends RecyclerView.ViewHolder {

        TextView textView;
        CircleImageView avatar;
        public ViewHoler(View itemView) {
            super(itemView);

            textView = itemView.findViewById(R.id.msg_user_name);
            avatar = itemView.findViewById(R.id.message_avatar);
        }
    }
    MessageFragment mFragment;

    private void fragmentJump(User mItemSelected) {
//        UserSingleton.getInstance().getUser().setUsername(mItemSelected.getUsername());
        mFragment = new MessageFragment();
        Bundle mBundle = new Bundle();
        String userString = new Gson().toJson(mItemSelected);
        mBundle.putString("item_selected_key", userString);
        mFragment.setArguments(mBundle);
        switchContent(R.id.fragment_container, mFragment);
    }

    public void switchContent(int id, MessageFragment fragment) {
        if (context == null)
            return;
        if (context instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) context;
            MessageFragment frag = fragment;
            homeActivity.switchContent(id, frag);
        }

    }
}
