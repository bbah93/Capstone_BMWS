package com.nyc.polymerse.profile_creation;


import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TextInputEditText;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
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
public class Name_Fragment extends Fragment {


    public static String TAG;
    View rootView;
    TextInputEditText first_name;
    TextInputEditText last_name;
    FloatingActionButton name_fab;
    private static final String PROF_CREATE_KEY = "profile_creator";
    SharedPreferences profile_creator;
    SharedPreferences.Editor editor;


    public Name_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         rootView = inflater.inflate(R.layout.name_frag, container, false);

         first_name = (TextInputEditText) rootView.findViewById(R.id.first_name);
         last_name = (TextInputEditText) rootView.findViewById(R.id.last_name);
         name_fab = (FloatingActionButton) rootView.findViewById(R.id.name_fab);


        profile_creator = getActivity().getApplicationContext().getSharedPreferences(PROF_CREATE_KEY,MODE_PRIVATE);

        moveToLocation_Frag();
        return rootView;

    }

    public void moveToLocation_Frag(){
        name_fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (first_name.getText().toString().isEmpty()){
                   toastFirst();
                }
                if (last_name.getText().toString().isEmpty()){
                   toastLast();
                }
                else if(!first_name.getText().toString().isEmpty()&& !last_name.getText().toString().isEmpty()){
                    editor = profile_creator.edit();

                    editor.putString("first_name", first_name.getText().toString());
                    editor.putString("last_name", last_name.getText().toString());
                    editor.apply();
                    UserSingleton.getInstance().getUser().setUsername(first_name.getText().toString() +"_"+ last_name.getText().toString());

                    Location_Fragment location_fragment = new Location_Fragment();
                    FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                    FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction()
                    //fragmentTransaction.add(R.id.fragment_container,location_fragment, Location_Fragment.TAG);
                    .replace(R.id.fragment_container,location_fragment, Location_Fragment.TAG);
                    fragmentTransaction.addToBackStack("Name Fragment");
                    fragmentTransaction.commit();

                }
            }
        });
    }

    public void toastFirst(){
        Toast.makeText(getActivity().getApplicationContext(), "Please enter your first name", Toast.LENGTH_LONG).show();
    }
    public void toastLast(){
        Toast.makeText(getActivity().getApplicationContext(), "Please enter your last name", Toast.LENGTH_LONG).show();
    }

}
