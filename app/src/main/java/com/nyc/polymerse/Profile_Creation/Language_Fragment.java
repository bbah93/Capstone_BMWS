package com.nyc.polymerse.Profile_Creation;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.nyc.polymerse.HomeActivity;
import com.nyc.polymerse.R;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class Language_Fragment extends Fragment {

    public static String TAG;
    TextInputEditText fluent;
    TextInputEditText fluent_level;
    TextInputEditText learning;
    TextInputEditText learning_Level;
    FloatingActionButton language_fab;
    private static final String PROF_CREATE_KEY = "profile_creator";
    SharedPreferences profile_creator;
    SharedPreferences.Editor editor;
    View rootView;


    public Language_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.language_frag, container, false);

        fluent = rootView.findViewById(R.id.known_input);
        fluent_level = rootView.findViewById(R.id.fluent_level_input);
        learning = rootView.findViewById(R.id.lang_learn_input);
        learning_Level = rootView.findViewById(R.id.fluency_learn_input);
        language_fab = rootView.findViewById(R.id.language_fab);

        profile_creator = getActivity().getApplicationContext().getSharedPreferences(PROF_CREATE_KEY,MODE_PRIVATE);

        finishCreate();
        return rootView;
    }

    public void finishCreate(){
        language_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fluent.getText().toString().isEmpty()){
                    toastFluent();
                }
                if (fluent_level.getText().toString().isEmpty()){
                    toastLevel();
                }
                if (learning.getText().toString().isEmpty()){
                    toastLearning();
                }
                if(learning_Level.getText().toString().isEmpty()){
                    toastLevel();
                }
                else if (!fluent.getText().toString().isEmpty()&& !learning.getText().toString().isEmpty()){
                    editor = profile_creator.edit();
                    editor.putString("fluent", fluent.getText().toString());
                    editor.putString("fluent_level", fluent_level.getText().toString());

                    editor.putString("learning_lang", learning.getText().toString());
                    editor.putString("learning_level", learning_Level.getText().toString());
                    editor.apply();

                    Intent intent = new Intent(getActivity(), HomeActivity.class);
                    startActivity(intent);
                }

            }
        });
    }


    public void toastFluent(){
        Toast.makeText(getActivity().getApplicationContext(), "Please enter a language you know", Toast.LENGTH_LONG).show();
    }
    public void toastLearning(){
        Toast.makeText(getActivity().getApplicationContext(), "Please enter a language you are learning", Toast.LENGTH_LONG).show();
    }
    public void toastLevel(){
        Toast.makeText(getActivity().getApplicationContext(), "Please enter your fluency level", Toast.LENGTH_LONG).show();
    }

}
