package com.nyc.polymerse.fragments;


import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nyc.polymerse.Constants;
import com.nyc.polymerse.HomeActivity;
import com.nyc.polymerse.Invites.Invite_Frag;
import com.nyc.polymerse.R;
import com.nyc.polymerse.User;

import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserDetailsFragment extends Fragment {

    private static final String TAG = "UserDetailsFragment";
    private View rootView;
    private User user;
    MessageFragment mFragment;
    Invite_Frag mInviteFragment;
    private Context context;

    private CircleImageView profilePic, profileBlock, profileReviewerPic;
    private TextView profileUserName, aboutMe, profileReviewDate, profileReview, sharingLang, learningLang;
    private Button message, invite;
    private ProgressBar sharingFluency, learningFluency;


    public UserDetailsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_user_details, container, false);
        context = rootView.getContext();
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        message = view.findViewById(R.id.profile_message);
        invite = view.findViewById(R.id.profile_invite);

        Bundle bundle = getArguments();
        String userString = bundle.getString(Constants.ITEM_SELECTED_KEY);
        user = new Gson().fromJson(userString, User.class);
        Log.d(TAG, "onViewCreated: " + user.getuID());

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentJumpMessage(user);
            }
        });
        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentJumpInvite(user);
            }
        });

        profileUserName = view.findViewById(R.id.user_detail_name);
        aboutMe = view.findViewById(R.id.about_value);
        learningLang = view.findViewById(R.id.detail_learning_value);
        sharingLang = view.findViewById(R.id.detail_sharing_value);
        learningFluency = view.findViewById(R.id.detail_learning_fluency);
        sharingFluency = view.findViewById(R.id.detail_sharing_fluency);
        setUserFields();


    }

    private void setUserFields() {
        profileUserName.setText(user.getUsername());
        Map<String, String > langLearn = user.getLangLearn();
        Map<String, String > langShare = user.getLangTeach();
        for (String s : langLearn.keySet()) {
            learningLang.setText(s);
        }
        for (String s : langShare.keySet()) {
            sharingLang.setText(s);
        }
        for (String s : langLearn.values()) {

        }
        for (String s : langShare.values()) {
            sharingLang.setText(s);
        }

    }

    private void fragmentJumpMessage(User mItemSelected) {
//        UserSingleton.getInstance().getUser().setUsername(mItemSelected.getUsername());
        mFragment = new MessageFragment();
        Bundle mBundle = new Bundle();
        String userString = new Gson().toJson(mItemSelected);
        mBundle.putString(Constants.ITEM_SELECTED_KEY, userString);
        mFragment.setArguments(mBundle);
        switchContent(R.id.fragment_container, mFragment);
    }

    private void fragmentJumpInvite(User mItemSelected) {

        mInviteFragment = new Invite_Frag();
        Bundle mBundle = new Bundle();
        String userString = new Gson().toJson(mItemSelected);
        mBundle.putString(Constants.ITEM_SELECTED_KEY, userString);
        mInviteFragment.setArguments(mBundle);
        switchContent(R.id.fragment_container, mInviteFragment);
    }


    public void switchContent(int id, Invite_Frag fragment) {

        if (context == null)
            return;
        if (context instanceof HomeActivity) {
            HomeActivity homeActivity = (HomeActivity) context;
            Invite_Frag frag = fragment;
            homeActivity.switchContent(id, frag);
        }
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
