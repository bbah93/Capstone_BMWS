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
    TextInputEditText fluent_level;
    TextInputEditText learning;
    TextInputEditText learning_Level;
    View rootView;


    public Language_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.language_frag, container, false);
        begin_Button = rootView.findViewById(R.id.begin_button);
        fluent = rootView.findViewById(R.id.known_input);
        fluent_level = rootView.findViewById(R.id.fluent_level_input);
        learning = rootView.findViewById(R.id.lang_learn_input);
        learning_Level = rootView.findViewById(R.id.fluency_learn_input);

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
