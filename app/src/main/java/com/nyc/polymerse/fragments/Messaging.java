package com.nyc.polymerse.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nyc.polymerse.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Messaging extends Fragment {

    View rootView;

    public Messaging() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_messaging, container, false);
        return rootView;
    }

}
