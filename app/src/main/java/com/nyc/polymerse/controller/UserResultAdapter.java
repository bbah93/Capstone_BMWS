package com.nyc.polymerse.controller;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import com.nyc.polymerse.R;
import com.nyc.polymerse.User;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by Shant on 3/18/2018.
 */

public class UserResultAdapter extends RecyclerView.Adapter<UserResultAdapter.UserResultViewHolder> {

    List<User> userList = new ArrayList<>();

    public UserResultAdapter(List<User> userList){
        this.userList = userList;
    }

    @Override
    public UserResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_result_item,parent,false);
        return new UserResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(UserResultViewHolder holder, int position) {
        User user = userList.get(position);
        holder.onBind(user);

    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class UserResultViewHolder extends RecyclerView.ViewHolder{

        TextView userName;
        TextView learningLang;
        TextView learningFluency;
        TextView sharingLang;
        TextView sharingFluency;
        TextView location;
        RatingBar ratingBar;
        CircleImageView avatar;

        public UserResultViewHolder(View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_results_user_name);
            learningLang = itemView.findViewById(R.id.learning_value);
            learningFluency = itemView.findViewById(R.id.learning_fluency);
            sharingLang =itemView.findViewById(R.id.sharing_value);
            sharingFluency = itemView.findViewById(R.id.sharing_fluency);
            location = itemView.findViewById(R.id.located_value);
            ratingBar = itemView.findViewById(R.id.user_resuts_rating_bar);
            avatar = itemView.findViewById(R.id.user_avatar_user_results);
        }

        public void onBind(User user){

            String userDisplayName = user.getUsername();
            userName.setText(userDisplayName);

            Map<String,String> learnMap = user.getLangLearn();
            String languageLearning = "";
            String languageLearningFluency = "";
            for(String s:learnMap.keySet()){
                languageLearning = s;
                languageLearningFluency = learnMap.get(s);
            }
            learningLang.setText(languageLearning);
            learningFluency.setText(languageLearningFluency);

            Map<String,String> shareMap = user.getLangTeach();
            String languageSharing = "";
            String languageSharingFluency = "";
            for(String s:learnMap.keySet()){
                languageSharing = s;
                languageSharingFluency = learnMap.get(s);
            }
            sharingLang.setText(languageSharing);
            sharingFluency.setText(languageSharingFluency);

            String locationValue = user.getCity();
            location.setText(locationValue);

        }
    }


}
