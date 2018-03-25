package com.nyc.polymerse.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nyc.polymerse.Constants;
import com.nyc.polymerse.FilterUsersClass;
import com.nyc.polymerse.R;
import com.nyc.polymerse.User;
import com.nyc.polymerse.UserSingleton;
import com.nyc.polymerse.controller.UserResultAdapter;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserResultsFragment extends Fragment {

    final static String SHARED_PREFS_KEY = "filter_preferences";
    SharedPreferences sharedPreferences;

    View rootView;
    private final String TAG = "UserResultsFragment";
    private List<User> userList = new ArrayList<>();
    private Button filter;

    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseUser;

    private UserResultsFilterFragment userResultsFilterFragment;

    private UserResultAdapter adapter;

    public UserResultsFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_user_results, container, false);
        ButterKnife.bind(this, rootView);
        sharedPreferences = getActivity().getApplicationContext().getSharedPreferences(Constants.SHARED_PREFS_FILTER_KEY, MODE_PRIVATE);

        return rootView;

    }

    @OnClick(R.id.filter_button)
    public void showFilterFragment() {
        Toast.makeText(getActivity().getApplicationContext(), "CLICK", Toast.LENGTH_SHORT).show();
        userResultsFilterFragment = new UserResultsFilterFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.filter_container, userResultsFilterFragment).commit();
        FrameLayout layout = (FrameLayout) getActivity().findViewById(R.id.filter_container);
        layout.setVisibility(View.VISIBLE);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        Log.d(TAG, "ON VIEW CREATED");

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabaseUser = mDatabase.child(Constants.USERS);
        mDatabaseUser.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                Log.d(TAG, "count " + dataSnapshot.getChildrenCount());
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    User user = d.getValue(User.class);
                    userList.add(user);
                    Log.d(TAG, "onDataChange: user " + user.getUsername());

                    //This is the test user only;
                    Log.d(TAG, "onSuccess: " + user.getCity());
                }

                List<User> userListFilter = filterThroughSharedPrefs(userList, UserSingleton.getInstance().getUser());
                RecyclerView recyclerView = rootView.findViewById(R.id.user_results_rec_view);
                LinearLayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
                recyclerView.setLayoutManager(manager);
                adapter = new UserResultAdapter(userListFilter,rootView.getContext());
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("The read failed: ", databaseError.getMessage());

            }


        });

    }

    private List<User> filterThroughSharedPrefs(List<User> userList, User user) {
        boolean sharing = sharedPreferences.getBoolean("sharing",false);
        boolean learning = sharedPreferences.getBoolean("learning", false);
        List<User> filteredUsers = userList;
        if (sharing){
            filteredUsers = FilterUsersClass.filterUserBySharing(filteredUsers,user);
        }
        if (learning) {
            filteredUsers = FilterUsersClass.filterUserByLearning(filteredUsers,user);

        }

        return filteredUsers;
    }

    public void filterList(){
        List<User> userListFilter = filterThroughSharedPrefs(userList, UserSingleton.getInstance().getUser());

        adapter.setList(userListFilter);
    }


}
