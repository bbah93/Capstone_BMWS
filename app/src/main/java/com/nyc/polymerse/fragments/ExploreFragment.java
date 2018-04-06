package com.nyc.polymerse.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;

import com.nyc.polymerse.HomeActivity;
import com.nyc.polymerse.R;
import com.nyc.polymerse.controller.ExploreItemAdapter;

import org.json.JSONException;

import butterknife.ButterKnife;
import butterknife.OnClick;

/**
 * A simple {@link Fragment} subclass.
 */
public class ExploreFragment extends Fragment {

    View rootView;
    Button filter;

    public ExploreFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_explore, container, false);

        ButterKnife.bind(this, rootView);

        RecyclerView recyclerView = rootView.findViewById(R.id.explore_rec_view);
        try {
            ExploreItemAdapter adapter = new ExploreItemAdapter();
            LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity().getApplicationContext(), LinearLayoutManager.VERTICAL, false);
            recyclerView.setLayoutManager(layoutManager);
            recyclerView.setAdapter(adapter);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        // Inflate the layout for this fragment
        return rootView;
    }

    @OnClick(R.id.explore_filter_button)
    public void showFilterFragment() {
        ExploreFilterFragment exploreFilterFragment = new ExploreFilterFragment();
        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.explore_filter_container, exploreFilterFragment).commit();
        FrameLayout layout = (FrameLayout) getActivity().findViewById(R.id.explore_filter_container);
        layout.setVisibility(View.VISIBLE);
    }

}
