package com.nyc.polymerse.controller;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;

import com.nyc.polymerse.Invites.Invite_Schema;

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

        public InviteItemViewHolder(View itemView) {
            super(itemView);
        }

        public void onBind(Invite_Schema invite){


        }
    }
}
