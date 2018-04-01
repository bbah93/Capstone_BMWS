package com.nyc.polymerse.controller;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nyc.polymerse.DummyUser_Data;
import com.nyc.polymerse.HomeActivity;
import com.nyc.polymerse.R;
import com.nyc.polymerse.User;
import com.nyc.polymerse.fragments.UserDetailsFragment;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by Shant on 3/18/2018.
 */

public class UserResultAdapter extends RecyclerView.Adapter<UserResultAdapter.UserResultViewHolder> {

    ArrayList<User> userList = new ArrayList<>();
    Context context;
    private final String TAG = "UserResultsAdapter";
    DummyUser_Data dummyUser_data;

    public UserResultAdapter(ArrayList<User> userList, Context context){
        this.userList = userList;
        this.context = context;
    }

    @Override
    public UserResultViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.user_result_item,parent,false);
        return new UserResultViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final UserResultViewHolder holder, int position) {
        final User user = userList.get(position);
        holder.onBind(user);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentJump(user);
            }
        });

    }


    public void updateList(ArrayList<User> newList) {
        this.userList = newList;
        notifyDataSetChanged();
        Log.d(TAG, "updateList: ran");
    }

    @Override
    public int getItemCount() {
        return userList.size();
    }

    public class UserResultViewHolder extends RecyclerView.ViewHolder{

        TextView userName;
        TextView learningLang;
        ProgressBar learningFluency;
        TextView sharingLang;
        ProgressBar sharingFluency;
        //TextView location;
        RatingBar ratingBar;
        CircleImageView avatar;

        public UserResultViewHolder(View itemView) {
            super(itemView);
            userName = itemView.findViewById(R.id.user_results_user_name);
            learningLang = itemView.findViewById(R.id.learning_value);
            learningFluency = itemView.findViewById(R.id.learning_fluency);
            sharingLang =itemView.findViewById(R.id.sharing_value);
            sharingFluency = itemView.findViewById(R.id.sharing_fluency);
            //location = itemView.findViewById(R.id.located_value);
            ratingBar = itemView.findViewById(R.id.user_resuts_rating_bar);
            avatar = itemView.findViewById(R.id.user_avatar_user_results);
        }

        public void onBind(User user){

            String userDisplayName = user.getUsername();
            userName.setText(userDisplayName);
            if (user.getProfilePic() != null) {
                Picasso.get().load(user.getProfilePic()).into(avatar);
            }
            Map<String,String> learnMap = user.getLangLearn();
            String languageLearning = "";
            String languageLearningFluency = "";
            for(String s:learnMap.keySet()){
                languageLearning = s;
                languageLearningFluency = learnMap.get(s);

                if (learnMap.get(s).equals("Beginner")) {
                    learningFluency.setProgress(25);
                } else if (learnMap.get(s).equals("Intermediate")) {
                    learningFluency.setProgress(50);
                } else if (learnMap.get(s).equals("Advanced")) {
                    learningFluency.setProgress(75);
                } else {
                    learningFluency.setProgress(100);
                }
            }
            learningLang.setText(languageLearning);
            //learningFluency.setText(languageLearningFluency);

            Map<String,String> shareMap = user.getLangTeach();
            String languageSharing = "";
            String languageSharingFluency = "";
            for(String s:shareMap.keySet()){
                languageSharing = s;
                languageSharingFluency = shareMap.get(s);

                if (shareMap.get(s).equals("Beginner")) {
                    sharingFluency.setProgress(25);
                } else if (shareMap.get(s).equals("Intermediate")) {
                    sharingFluency.setProgress(50);
                } else if (shareMap.get(s).equals("Advanced")) {
                    sharingFluency.setProgress(75);
                } else {
                    sharingFluency.setProgress(100);
                }
            }
            sharingLang.setText(languageSharing);
            //sharingFluency.setText(languageSharingFluency);

            String locationValue = user.getCity();
            //location.setText(locationValue);


        }
    }


    UserDetailsFragment mFragment;

    private void fragmentJump(User mItemSelected) {
        mFragment = new UserDetailsFragment();
        Bundle mBundle = new Bundle();
        String userString = new Gson().toJson(mItemSelected);
        mBundle.putString("item_selected_key", userString);
        mFragment.setArguments(mBundle);
        switchContent(R.id.fragment_container, mFragment);
    }

    public void switchContent(int id, UserDetailsFragment fragment) {
        if (context == null)
            return;
        if (context instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) context;
            UserDetailsFragment frag = fragment;
            homeActivity.switchContent(id, frag);
        }

    }
}
