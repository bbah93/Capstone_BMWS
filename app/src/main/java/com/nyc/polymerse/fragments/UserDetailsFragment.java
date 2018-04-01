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

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.nyc.polymerse.Constants;
import com.nyc.polymerse.HomeActivity;
import com.nyc.polymerse.Invites.Invite_Frag;
import com.nyc.polymerse.R;
import com.nyc.polymerse.User;
import com.nyc.polymerse.UserSingleton;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * A simple {@link Fragment} subclass.
 */
public class UserDetailsFragment extends Fragment {

    private static final String TAG = "UserDetailsFragment";
    private View rootView;
    private User user;
    MessageFragment mMessageFragment;
    Invite_Frag mInviteFragment;
    UserResultsFragment mUserResultsFragment;
    private Context context;

    private CircleImageView profilePic, profileBlock, profileReviewerPic;
    private TextView profileUserName, aboutMe, profileReviewDate, profileReview, sharingLang, learningLang;
    private Button message, invite;
    private ProgressBar sharingFluency, learningFluency;
    private User currentUser;

    private DatabaseReference databaseReference;


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
        profileBlock = view.findViewById(R.id.profile_block);

        databaseReference = FirebaseDatabase.getInstance().getReference();

        Bundle bundle = getArguments();
        String userString = bundle.getString(Constants.ITEM_SELECTED_KEY);
        user = new Gson().fromJson(userString, User.class);
        Log.d(TAG, "onViewCreated: " + user.getuID());
        currentUser = UserSingleton.getInstance().getUser();

        message.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentJump(user, new MessageFragment());
            }
        });
        invite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                fragmentJump(user, new Invite_Frag());
            }
        });
        profileBlock.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //Accessing database to find a reference to the blocked child.
                DatabaseReference databaseReferenceBlocked = databaseReference.child(Constants.BLOCKED_USER_KEY);
                Map<String,Object> userGettingBlock = new HashMap<>();
                String currentTime = String.valueOf(System.currentTimeMillis());
                //Creating a map of the blocked user and the time they where blocked
                userGettingBlock.put(currentTime, currentUser.getuID());
                //Updating child so the new map gets added to FireBase.
                databaseReferenceBlocked.child(user.getuID()).updateChildren(userGettingBlock);

                //Getting the blocked map from the user
                Map<String,String> userBlockReference = new HashMap<>();
                if (currentUser.getBlocked() != null){
                    userBlockReference = currentUser.getBlocked();
                }
                //Adding the same blocked info as previously shown
                userBlockReference.put(currentTime,user.getuID());

                //Updated the current app users object
                currentUser.setBlocked(userBlockReference);
                UserSingleton.getInstance().setUser(currentUser);

                //Setting the updated user to the FireBase database
                databaseReference.child(Constants.USERS).child(currentUser.getuID()).setValue(currentUser);

                //Leaving profile to the home page
                switchContent(R.id.fragment_container, new UserResultsFragment());



            }
        });

        profileUserName = view.findViewById(R.id.user_detail_name);
        aboutMe = view.findViewById(R.id.about_value);
        learningLang = view.findViewById(R.id.detail_learning_value);
        sharingLang = view.findViewById(R.id.detail_sharing_value);
        learningFluency = view.findViewById(R.id.detail_learning_fluency);
        sharingFluency = view.findViewById(R.id.detail_sharing_fluency);
        profilePic = rootView.findViewById(R.id.user_profile_avatar);

        setUserFields();


    }

    private void setUserFields() {
        Picasso.get().load(user.getProfilePic()).placeholder(R.drawable.ic_account_circle_black_24dp).into(profilePic);
        profileUserName.setText(user.getUsername());
        Map<String, String> langLearn = user.getLangLearn();
        Map<String, String> langShare = user.getLangTeach();
        for (String s : langLearn.keySet()) {
            learningLang.setText(s);
        }
        for (String s : langShare.keySet()) {
            sharingLang.setText(s);
        }
        for (String s : langLearn.values()) {
            if (s.equals("Beginner")) {
                learningFluency.setProgress(25);
            } else if (s.equals("Intermediate")) {
                learningFluency.setProgress(50);
            } else if (s.equals("Advanced")) {
                learningFluency.setProgress(75);
            } else {
                learningFluency.setProgress(100);
            }
        }
        for (String s : langShare.values()) {

            if (s.equals("Beginner")) {
                sharingFluency.setProgress(25);
            } else if (s.equals("Intermediate")) {
                sharingFluency.setProgress(50);
            } else if (s.equals("Advanced")) {
                sharingFluency.setProgress(75);
            } else {
                sharingFluency.setProgress(100);
            }
        }

    }

    private void fragmentJump(Fragment fragment) {
        switchContent(R.id.fragment_container, fragment);
    }
    private void fragmentJump(User mItemSelected, Fragment fragment) {

        Bundle mBundle = new Bundle();
        String userString = new Gson().toJson(mItemSelected);
        mBundle.putString(Constants.ITEM_SELECTED_KEY, userString);
        fragment.setArguments(mBundle);
        switchContent(R.id.fragment_container, fragment);
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
