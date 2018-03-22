package com.nyc.polymerse.Profile_Creation;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.nyc.polymerse.R;
import com.nyc.polymerse.UserSingleton;

import static android.content.Context.MODE_PRIVATE;

/**
 * A simple {@link Fragment} subclass.
 */
public class Location_Fragment extends Fragment {

    public static String TAG;
    TextInputEditText city;
    TextInputEditText state;
    FloatingActionButton location_fab;
    View rootView;
    private static final String PROF_CREATE_KEY = "profile_creator";
    SharedPreferences profile_creator;
    SharedPreferences.Editor editor;
    public Location_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.location_frag, container, false);

        city = (TextInputEditText) rootView.findViewById(R.id.city_input);
        state = (TextInputEditText) rootView.findViewById(R.id.state_input);
        location_fab = (FloatingActionButton) rootView.findViewById(R.id.location_fab);

        profile_creator = getActivity().getApplicationContext().getSharedPreferences(PROF_CREATE_KEY,MODE_PRIVATE);

       moveToLanguage_Frag();

        return rootView;
    }

    public void moveToLanguage_Frag(){
        location_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (city.getText().toString().isEmpty()){
                    toastCity();
                }
                if (state.getText().toString().isEmpty()){
                    toastState();
                }
                else if(!city.getText().toString().isEmpty()&& !state.getText().toString().isEmpty()){

                    editor = profile_creator.edit();
                    editor.putString("city", city.getText().toString());
                    editor.putString("state", state.getText().toString());
                    editor.apply();

                    UserSingleton.getInstance().getUser().setCity(city.getText().toString());
                    UserSingleton.getInstance().getUser().setState(state.getText().toString());
                    Language_Fragment language_fragment = new Language_Fragment();
                    getActivity().getSupportFragmentManager()
                            .beginTransaction()
                            .add(R.id.fragment_container,language_fragment, Language_Fragment.TAG)
                            .commit();

                }
            }
        });
    }

    public void toastCity(){
        Toast.makeText(getActivity().getApplicationContext(), "Please enter your city", Toast.LENGTH_LONG).show();
    }
    public void toastState(){
        Toast.makeText(getActivity().getApplicationContext(), "Please enter your state", Toast.LENGTH_LONG).show();
    }

}
