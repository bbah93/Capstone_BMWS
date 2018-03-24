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
import android.widget.TextView;

import com.google.gson.Gson;
import com.nyc.polymerse.HomeActivity;
import com.nyc.polymerse.R;
import com.nyc.polymerse.User;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserDetailsFragment extends Fragment {

    private static final String TAG = "UserDetailsFragment";
    private View rootView;
    private User user;
    MessageFragment mFragment;
    private Context context;

    private CircleImageView profilePic, profileBlock, profileReviewerPic;
    private TextView profileUserName, aboutMe, profileReviewDate, profileReview;
    private Button message, invite;



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

        Bundle bundle = getArguments();
        String userString = bundle.getString("item_selected_key");
        user = new Gson().fromJson(userString,User.class);
        Log.d(TAG, "onViewCreated: " + user.getuID());

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentJump(user);
            }
        });



    }
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
