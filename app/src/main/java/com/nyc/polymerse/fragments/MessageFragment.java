package com.nyc.polymerse.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.nyc.polymerse.Constants;
import com.nyc.polymerse.Message;
import com.nyc.polymerse.R;
import com.nyc.polymerse.User;
import com.nyc.polymerse.UserSingleton;

/**
 * A simple {@link Fragment} subclass.
 */
public class MessageFragment extends Fragment {

    private Button mSendButton;
    private EditText mMessageEditText;

    private DatabaseReference databaseReference;
    private static final String TAG = "MessageFragment";
    private String username;
    private String uID;

    private ProgressBar mProgressBar;
    public MessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_message, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mProgressBar.setVisibility(ProgressBar.INVISIBLE);
        Bundle bundle = getArguments();
        String userString = bundle.getString("item_selected_key");
        User user = new Gson().fromJson(userString,User.class);
        Log.d(TAG, "onViewCreated: " + user.getuID());
        //I needed the username of the current user here but I only ever got it from auth so I put it in a singleton.
        uID = UserSingleton.getInstance().getUser().getuID();

        username = UserSingleton.getInstance().getUser().getUsername();

        //Here I get a reference to the msgs stored for this user and the username of the user he/she/it msg'd.
        databaseReference = FirebaseDatabase.getInstance().getReference().child(Constants.MESSAGE).child(uID).child(user.getuID()).child("user");
        mSendButton = (Button) view.findViewById(R.id.sendButton);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message message = new
                        Message(mMessageEditText.getText().toString(), username);
                databaseReference.setValue(message);
                mMessageEditText.setText("");
            }
        });


        mMessageEditText = (EditText) view.findViewById(R.id.messageEditText);
        mMessageEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                if (charSequence.toString().trim().length() > 0) {
                    mSendButton.setEnabled(true);
                } else {
                    mSendButton.setEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable editable) {
            }
        });
    }
}
