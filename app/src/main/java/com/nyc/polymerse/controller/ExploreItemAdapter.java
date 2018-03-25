package com.nyc.polymerse.controller;

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
        View holder = LayoutInflater.from(parent.getContext()).inflate(R.layout.explore_item,parent,false);
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

        ImageView movieImg;
        ImageView songImg;
        ImageView placeImg;
        TextView movie;
        TextView place;
        TextView song;

        public ExploreItemViewHolder(View itemView) {
            super(itemView);
            movieImg = itemView.findViewById(R.id.explore_item_show);
            songImg = itemView.findViewById(R.id.explore_item_song);
            placeImg = itemView.findViewById(R.id.explore_item_place);
            movie = itemView.findViewById(R.id.show_name);
            place = itemView.findViewById(R.id.place_name);
            song = itemView.findViewById(R.id.song_name);
        }

        public void onBind(ExploreItemModel model){
            Picasso.get().load(model.getPlace_img()).fit().into(placeImg);
            Picasso.get().load(model.getSong_img()).fit().into(songImg);
            Picasso.get().load(model.getShow_movie_img()).fit().into(movieImg);
            movie.setText(model.getShow_movie());
            song.setText(model.getSong());
            place.setText(model.getPlace());

        }
    }
}
