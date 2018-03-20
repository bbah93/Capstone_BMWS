package com.nyc.polymerse.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.nyc.polymerse.R;

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

    final static String SHARED_PREFS_KEY = "filter_preferences";

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

    public UserResultsFilterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_user_results_filter, container, false);
        ButterKnife.bind(this, rootView);
        populateCheckboxList();

        sharedPreferences = getActivity().getApplicationContext().getSharedPreferences(SHARED_PREFS_KEY, MODE_PRIVATE);
        checkedSavedFilters();
        return rootView;
    }

    public List<String> getSavedFilters() {
        Map<String,?> keys = sharedPreferences.getAll();
        List<String> checked = new ArrayList<>();
        for (Map.Entry<String, ?> entry : keys.entrySet()) {
            if((Boolean) entry.getValue()){
                checked.add(entry.getKey());
            }
        }
        return checked;
    }

    public void checkedSavedFilters(){
        for(CheckBox box:checkBoxList){
            String s = box.getText().toString();
            if(getSavedFilters().contains(s)){
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
    }

    @OnClick(R.id.close_filter)
    public void closeFilters() {
        closeFragment();
        clearFilters();
    }

    public boolean closeFragment() {
        FrameLayout layout = (FrameLayout) getActivity().findViewById(R.id.filter_container);
        layout.setVisibility(View.GONE);
        return true;
    }

    @OnClick(R.id.apply_filter)
    public void applyFilters() {
        SharedPreferences.Editor editor = sharedPreferences.edit();
        for (CheckBox checkBox : checkBoxList) {
            if (checkBox.isChecked()) {
                String preferenceKey = checkBox.getText().toString();
                boolean preferenceValue = true;
                editor.putBoolean(preferenceKey, preferenceValue);
                editor.commit();
            }
        }
        closeFragment();

    }

}
