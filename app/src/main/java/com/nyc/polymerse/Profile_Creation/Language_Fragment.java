package com.nyc.polymerse.Profile_Creation;


import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import com.nyc.polymerse.HomeActivity;
import com.nyc.polymerse.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Language_Fragment extends Fragment {

    public static String TAG;
    Button begin_Button;
    TextInputEditText fluent;
    TextInputEditText learning;
    View rootView;


    public Language_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.language_fragment, container, false);
        begin_Button = rootView.findViewById(R.id.begin_button);
        finishCreate();

        return rootView;
    }

    public void finishCreate(){
        begin_Button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getActivity(), HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
