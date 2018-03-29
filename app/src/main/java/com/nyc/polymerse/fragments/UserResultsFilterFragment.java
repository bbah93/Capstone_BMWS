package com.nyc.polymerse.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nyc.polymerse.Constants;
import com.nyc.polymerse.FilterUsersClass;
import com.nyc.polymerse.R;
import com.nyc.polymerse.User;
import com.nyc.polymerse.UserSingleton;
import com.nyc.polymerse.controller.UserResultAdapter;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserResultsFilterFragment extends Fragment {

    @BindView(R.id.advanced_fluency_filter)
    CheckBox advanced;

    @BindView(R.id.intermediate_fluency_filter)
    CheckBox intermediate;

    @BindView(R.id.beginner_fluency_filter)
    CheckBox beginner;

    @BindView(R.id.fluent_fluency_filter)
    CheckBox fluent;

    @BindView(R.id.learning_filter)
    CheckBox learning;

    @BindView(R.id.sharing_filter)
    CheckBox sharing;

    @BindView(R.id.close_filter)
    Button close;

    @BindView(R.id.clear_filter)
    Button clear;

    @BindView(R.id.apply_filter)
    Button apply;

    View rootView;
    SharedPreferences sharedPreferences;

    List<CheckBox> checkBoxList = new ArrayList<>();

    ArrayList<User> userList;
    UserResultAdapter adapter;

    private static final String TAG = "UserResultsFilterFrag";

    private boolean clearAndApply = false;

    public UserResultsFilterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_user_results_filter, container, false);
        ButterKnife.bind(this, rootView);
        populateCheckboxList();

        sharedPreferences = getActivity().getApplicationContext().getSharedPreferences(Constants.SHARED_PREFS_FILTER_KEY, MODE_PRIVATE);
        checkedSavedFilters();
        return rootView;
    }

    public List<String> getSavedFilters() {
        Map<String, ?> keys = sharedPreferences.getAll();
        List<String> checked = new ArrayList<>();
        for (Map.Entry<String, ?> entry : keys.entrySet()) {
            if ((Boolean) entry.getValue()) {
                checked.add(entry.getKey());
            }
        }
        return checked;
    }

    public void checkedSavedFilters() {
        for (CheckBox box : checkBoxList) {
            String s = box.getText().toString();
            if (getSavedFilters().contains(s)) {
                box.setChecked(true);
            }
        }
    }

    public boolean populateCheckboxList() {
        checkBoxList.add(advanced);
        checkBoxList.add(beginner);
        checkBoxList.add(intermediate);
        checkBoxList.add(learning);
        checkBoxList.add(sharing);
        checkBoxList.add(fluent);
        return true;
    }

    @OnClick(R.id.clear_filter)
    public void clearFilters() {
        for (CheckBox box : checkBoxList) {
            box.setChecked(false);
        }
        clearAndApply = true;
    }

    @OnClick(R.id.close_filter)
    public void closeFilters() {
        closeFragment();
        clearFilters();
    }

    public boolean closeFragment() {
        FrameLayout layout = (FrameLayout) getActivity().findViewById(R.id.filter_container);
        layout.setVisibility(View.GONE);
        filterList();
        return true;
    }

    @OnClick(R.id.apply_filter)
    public void applyFilters() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (CheckBox checkBox : checkBoxList) {
            if (checkBox.isChecked()) {
                String preferenceKey = checkBox.getText().toString();
                Log.d(TAG, "applyFilters: prefKey " + preferenceKey);
                boolean preferenceValue = true;
                editor.putBoolean(preferenceKey, preferenceValue);
                editor.commit();
            }
        }
        if (clearAndApply) {
            editor.clear().commit();
        }

        closeFragment();
        clearAndApply = false;

    }
    public void filterList(){
        ArrayList<User> userListFilter = filterThroughSharedPrefs(userList, UserSingleton.getInstance().getUser());
        adapter.updateList(userListFilter);
    }

    private ArrayList<User> filterThroughSharedPrefs(ArrayList<User> userList, User user) {
        boolean sharing = sharedPreferences.getBoolean("Sharing",false);
        boolean learning = sharedPreferences.getBoolean("Learning", false);
        boolean beginner = sharedPreferences.getBoolean("Beginner", false);
        boolean intermediate = sharedPreferences.getBoolean("Intermediate", false);
        boolean advance = sharedPreferences.getBoolean("Advanced", false);
        boolean fluent = sharedPreferences.getBoolean("Fluent", false);

        Log.d(TAG, "filterThroughSharedPrefs: sharing " + sharing);
        Log.d(TAG, "filterThroughSharedPrefs: learning " + learning);
        Log.d(TAG, "filterThroughSharedPrefs: beginner " + beginner);
        Log.d(TAG, "filterThroughSharedPrefs: intermediate " + intermediate);
        Log.d(TAG, "filterThroughSharedPrefs: advance " + advance);
        Log.d(TAG, "filterThroughSharedPrefs: fluent " + fluent);
        ArrayList<User> filteredUsers = userList;
        for (int i = 0; i < userList.size(); i++) {
            Log.d(TAG, "filterThroughSharedPrefs: pre " + userList.get(i).getUsername() + " " + i);
        }
        if (sharing){
            filteredUsers = FilterUsersClass.filterUserBySharing(filteredUsers,user);

            for (int i = 0; i < filteredUsers.size(); i++) {
                Log.d(TAG, "filterThroughSharedPrefs: post sharing " + filteredUsers.get(i).getUsername() + " " + i);
            }
        }
        if (learning) {
            filteredUsers = FilterUsersClass.filterUserByLearning(filteredUsers,user);

            for (int i = 0; i < filteredUsers.size(); i++) {
                Log.d(TAG, "filterThroughSharedPrefs: post learning " + filteredUsers.get(i).getUsername() + " " + i);
            }
        }

        //These should filter the fluency but needs work
        if (beginner) {
            filteredUsers = FilterUsersClass.filterUserByBeginner(filteredUsers);

            for (int i = 0; i < filteredUsers.size(); i++) {
                Log.d(TAG, "filterThroughSharedPrefs: post beginner " + filteredUsers.get(i).getUsername() + " " + i);
            }
        }
        if (intermediate) {
            filteredUsers = FilterUsersClass.filterUserByIntermediate(filteredUsers);

            for (int i = 0; i < filteredUsers.size(); i++) {
                Log.d(TAG, "filterThroughSharedPrefs: post intermediate " + filteredUsers.get(i).getUsername() + " " + i);
            }
        }
        if (advance) {
            filteredUsers = FilterUsersClass.filterUserByAdvanced(filteredUsers);

            for (int i = 0; i < filteredUsers.size(); i++) {
                Log.d(TAG, "filterThroughSharedPrefs: post advance " + filteredUsers.get(i).getUsername() + " " + i);
            }
        }
        if (fluent) {
            filteredUsers = FilterUsersClass.filterUserByFluent(filteredUsers);

            for (int i = 0; i < filteredUsers.size(); i++) {
                Log.d(TAG, "filterThroughSharedPrefs: post fluent " + filteredUsers.get(i).getUsername() + " " + i);
            }
        }

        return filteredUsers;
    }

    public void setAdapter(UserResultAdapter adapter, ArrayList<User> userList) {
        this.adapter = adapter;
        this.userList = userList;
        Log.d(TAG, "setAdapter: ran");
    }
}
