package com.nyc.polymerse.Invites;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.nyc.polymerse.R;

/**
 * A simple {@link Fragment} subclass.
 */
public class Invite_Frag extends Fragment {


    public Invite_Frag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_invite_, container, false);
    }

}
