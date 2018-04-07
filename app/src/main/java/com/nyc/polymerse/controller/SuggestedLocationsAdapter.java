package com.nyc.polymerse.controller;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nyc.polymerse.HomeActivity;
import com.nyc.polymerse.Invites.Invite_Frag;
import com.nyc.polymerse.R;
import com.nyc.polymerse.models.SuggestedLocationModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Wayne Kellman on 3/31/18.
 */

public class SuggestedLocationsAdapter extends RecyclerView.Adapter<SuggestedLocationsAdapter.ViewHolder> {
    private List<SuggestedLocationModel> suggestedLocationModels;
    private String date, time, user;
    private Context context;

    public SuggestedLocationsAdapter(String user, String date, String time, Context context) {
        this.suggestedLocationModels = new ArrayList<>();
        this.user = user;
        this.date = date;
        this.time = time;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.suggested_locations_itemview, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {

        if (suggestedLocationModels.get(position).getVicinity() != null) {
            String vicinity = suggestedLocationModels.get(position).getVicinity().replace("<br/>", " ");

            holder.vicinity.setText(vicinity);
            holder.title.setText(suggestedLocationModels.get(position).getTitle());
            Picasso.get().load("http://www.realdetroitweekly.com/wp-content/uploads/2017/06/Restaurants.jpg").fit().into(holder.img);
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    fragmentJump(new Invite_Frag(), suggestedLocationModels.get(position).getVicinity().replace("<br/>", " "));
                }
            });
        }
    }

    @Override
    public int getItemCount() {
        return suggestedLocationModels.size();
    }

    private void fragmentJump(Fragment mFragment, String vicinity) {
        Bundle mBundle = new Bundle();
        mBundle.putString("item_selected_key", user);

        mBundle.putString("vicinity_selected", vicinity);

        if (!time.isEmpty()) {
            mBundle.putString("time_was_selected", time);
        }
        if (!date.isEmpty()) {
            mBundle.putString("date_was_selected", date);
        }
        mFragment.setArguments(mBundle);
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

    public void updateList(List<SuggestedLocationModel> suggestedLocationModels) {
        this.suggestedLocationModels = suggestedLocationModels;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        private TextView title, vicinity;
        private ImageView img;

        public ViewHolder(View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.suggestions_title);
            vicinity = itemView.findViewById(R.id.suggestions_vicinity);
            img = itemView.findViewById(R.id.suggested_img);
        }


    }
}
