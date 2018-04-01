package com.nyc.polymerse.fragments;


import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseListAdapter;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.gson.Gson;
import com.nyc.polymerse.Constants;
import com.nyc.polymerse.HomeActivity;
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

    private DatabaseReference databaseReferenceUser;
    private DatabaseReference databaseReferenceOther;
    private static final String TAG = "MessageFragment";
    private User currentUser;
    private User otherUser;
    private String currentUID;
    private String otherUID;
    private SharedPreferences sharedPreferences;

    private ProgressBar mProgressBar;
    // Adapter to display messages
    private FirebaseListAdapter<Message> adapter;


    public MessageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_message, container, false);

        if (rootView.getContext() instanceof HomeActivity) {
            ((HomeActivity) rootView.getContext()).disableProgressBar();
        }
        return rootView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        sharedPreferences = view.getContext().getSharedPreferences(Constants.FIREBASE_UID, Context.MODE_PRIVATE);

        mProgressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        mProgressBar.setVisibility(ProgressBar.INVISIBLE);
        Bundle bundle = getArguments();
        //deserialize the other otherUser and store in a field.
        String userString = bundle.getString("item_selected_key");
        User otherUser = new Gson().fromJson(userString, User.class);
        this.otherUser = otherUser;
        Log.d(TAG, "onViewCreated: " + otherUser.getuID());


        //I needed the username of the current otherUser here but I only ever got it from auth so I put it in a singleton.
        currentUID = sharedPreferences.getString(Constants.FIREBASE_UID_KEY, "");
        otherUID = otherUser.getuID();

        //deserialize current otherUser from sharedpreference.
        String userGson = sharedPreferences.getString(Constants.FIREBASE_USER_KEY, "");
        currentUser = UserSingleton.getInstance().getUser();
        //Here I get a reference to the msgs stored for this otherUser and the username of the otherUser he/she/it msg'd.
        databaseReferenceUser = FirebaseDatabase.getInstance().getReference().child(Constants.MESSAGE).child(currentUID).child(otherUID);
        //Another reference to duplicate message for the other user to see
        databaseReferenceOther = FirebaseDatabase.getInstance().getReference().child(Constants.MESSAGE).child(otherUID).child(currentUID);
        mSendButton = (Button) view.findViewById(R.id.sendButton);
        mSendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Message message = new
                        Message(mMessageEditText.getText().toString(), currentUser.getUsername());
                databaseReferenceUser.push().setValue(message);
                databaseReferenceOther.push().setValue(message);
                mMessageEditText.setText("");
            }
        });

        //This is the adapter that's part of Firebase.

        ListView listOfMessages = view.findViewById(R.id.list_of_messages);
        adapter = new FirebaseListAdapter<Message>(this.getActivity(), Message.class,
                R.layout.message,
                FirebaseDatabase.getInstance().getReference().child(Constants.MESSAGE).child(currentUID).child(otherUID)) {
            @Override
            protected void populateView(View v, Message model, int position) {

                // Get references to the views of message.xml
                TextView messageText = (TextView) v.findViewById(R.id.message_text);
                TextView messageUser = (TextView) v.findViewById(R.id.message_user);
//                TextView messageTime = (TextView)v.findViewById(R.id.message_time);

                // Set their text
                messageText.setText(model.getMessageText());
                messageUser.setText(model.getMessageUser());
//
//                // Format the date before showing it
//                messageTime.setText(DateFormat.format("dd-MM-yyyy (HH:mm:ss)",
//                        model.getMessageTime()));
            }
        };

        listOfMessages.setAdapter(adapter);

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


