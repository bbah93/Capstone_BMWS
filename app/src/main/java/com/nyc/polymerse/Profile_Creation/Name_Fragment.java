package com.nyc.polymerse.Profile_Creation;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nyc.polymerse.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Name_Fragment extends Fragment {


    public static String TAG;
    View rootView;

    public Name_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         rootView = inflater.inflate(R.layout.name_frag, container, false);

         return rootView;

    }

}
