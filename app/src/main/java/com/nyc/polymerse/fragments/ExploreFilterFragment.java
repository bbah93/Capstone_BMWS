package com.nyc.polymerse.fragments;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.FrameLayout;

import com.nyc.polymerse.Constants;
import com.nyc.polymerse.R;
import com.nyc.polymerse.controller.ExploreItemAdapter;
import com.nyc.polymerse.explore.ExploreItemModel;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExploreFilterFragment extends Fragment {

    @BindView(R.id.explore_film_filter)
    CheckBox film;

    @BindView(R.id.explore_music_filter)
    CheckBox music;

    @BindView(R.id.explore_food_filter)
    CheckBox food;

    @BindView(R.id.explore_place_filter)
    CheckBox place;

    @BindView(R.id.explore_close_filter)
    Button close;

    @BindView(R.id.explore_clear_filter)
    Button clear;

    @BindView(R.id.explore_apply_filter)
    Button apply;

    View rootView;
    SharedPreferences sharedPreferences;

    List<CheckBox> checkBoxList = new ArrayList<>();

    ArrayList<ExploreItemModel> List;
    ExploreItemAdapter adapter;

    private static final String TAG = "ExploreFilterFrag";

    private boolean clearAndApply = false;


    public ExploreFilterFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_explore_filter, container, false);
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
        checkBoxList.add(music);
        checkBoxList.add(film);
        checkBoxList.add(place);
        checkBoxList.add(food);
        return true;
    }

    @OnClick(R.id.explore_clear_filter)
    public void clearFilters() {
        for (CheckBox box : checkBoxList) {
            box.setChecked(false);
        }
        clearAndApply = true;
    }

    @OnClick(R.id.explore_close_filter)
    public void closeFilters() {
        closeFragment();
        clearFilters();
    }

    public boolean closeFragment() {
        FrameLayout layout = (FrameLayout) getActivity().findViewById(R.id.explore_filter_container);
        layout.setVisibility(View.GONE);
        return true;
    }

    @OnClick(R.id.explore_apply_filter)
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
        clearAndApply = false;
        closeFragment();
    }


}
