package com.nyc.polymerse.controller;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nyc.polymerse.R;
import com.nyc.polymerse.explore.ExploreItemModel;
import com.nyc.polymerse.explore.StaticMockExploreData;
import com.squareup.picasso.Picasso;

import org.json.JSONException;

import java.util.List;

/**
 * Created by Shant on 3/24/2018.
 */

public class ExploreItemAdapter extends RecyclerView.Adapter<ExploreItemAdapter.ExploreItemViewHolder> {

    List<ExploreItemModel> exploreList = StaticMockExploreData.populateList();

    public ExploreItemAdapter() throws JSONException {
    }

    @Override
    public ExploreItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.explore_item, parent, false);
        return new ExploreItemViewHolder(holder);
    }

    @Override
    public void onBindViewHolder(ExploreItemViewHolder holder, int position) {
        ExploreItemModel model = exploreList.get(position);
        holder.onBind(model);
    }

    @Override
    public int getItemCount() {
        return exploreList.size();
    }

    public class ExploreItemViewHolder extends RecyclerView.ViewHolder {

        ImageView img;
        ImageView type;
        TextView name;
        TextView goTo;

        public ExploreItemViewHolder(View itemView) {
            super(itemView);
            img = itemView.findViewById(R.id.explore_item_img);
            type = itemView.findViewById(R.id.explore_item_type);
            goTo = itemView.findViewById(R.id.explore_item_go_to);
            name = itemView.findViewById(R.id.explore_item_name);
        }

        public void onBind(final ExploreItemModel model) {

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String url = model.getItem_url();
                    Intent i = new Intent(Intent.ACTION_VIEW);
                    i.setData(Uri.parse(url));
                    itemView.getContext().startActivity(i);
                }
            });

            Picasso.get().load(model.getItem_img()).fit().into(img);
            name.setText(model.getItem_name());
            getItemTypeImg(model.getItem_type());
            goTo.setText(getGoToString(model.getItem_type()));

        }

        public void getItemTypeImg(String type) {
            switch (type) {
                case "film":
                    this.type.setImageResource(R.mipmap.show_round);
                    break;
                case "site":
                    this.type.setImageResource(R.mipmap.place_round);
                    break;
                case "food":
                    this.type.setImageResource(R.mipmap.food_icon_round);
                    break;
                case "music":
                    this.type.setImageResource(R.mipmap.song_round);
                    break;
            }
        }

        public String getGoToString(String type) {
            switch (type) {
                case "film":
                    return "Go to IMDB";
                case "site":
                    return "Go to Website";
                case "food":
                    return "Go to Website";
                case "music":
                    return "Listen on Youtube";
                default:
                    return "";
            }
        }

    }
}
