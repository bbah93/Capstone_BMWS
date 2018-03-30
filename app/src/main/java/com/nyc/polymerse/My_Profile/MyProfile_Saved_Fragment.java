package com.nyc.polymerse.My_Profile;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.nyc.polymerse.HomeActivity;
import com.nyc.polymerse.R;

import java.io.File;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyProfile_Saved_Fragment extends Fragment {

    public static String TAG;
    View rootView;
    Button homeButton;
    Button addProfileImage;
    FloatingActionButton editProfileButton;
    CircleImageView profileImage;
    TextView name_Input;
    TextView location_Input;
    TextView aboutMeInput;
    TextView learning_Input;
    TextView sharing_Input;

    protected static final int CAMERA_REQUEST = 0;
    protected static final int GALLERY_PICTURE = 1;
    private Intent pictureActionIntent = null;
    Bitmap bitmap;

    String selectedImagePath;


    public MyProfile_Saved_Fragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
         rootView = inflater.inflate(R.layout.fragment_my_profile__saved, container, false);
        homeButton = rootView.findViewById(R.id.back_home_button);
        addProfileImage = rootView.findViewById(R.id.ad_profile_image);
        editProfileButton = rootView.findViewById(R.id.save_profile_fab);
        profileImage = rootView.findViewById(R.id.user_profile_avatar);
        name_Input = rootView.findViewById(R.id.userName);
        location_Input = rootView.findViewById(R.id.user_location);
        aboutMeInput = rootView.findViewById(R.id.aboutMe_EditTexts);
        learning_Input = rootView.findViewById(R.id.prof_langLearn_spinner);
        sharing_Input = rootView.findViewById(R.id.sharing_lang_spinner);

        homeButtonClick();
        editProfileClick();
        setAddProfileImage();


        return rootView;
    }

    public void homeButtonClick(){
        homeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent homeIntent = new Intent(getActivity(), HomeActivity.class);
                startActivity(homeIntent);
            }
        });
    }

    public void editProfileClick(){
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit_MyProfile_Fragment editFragment = new Edit_MyProfile_Fragment();
                MyProfile_Saved_Fragment profile_saved_fragment = new MyProfile_Saved_Fragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .hide(profile_saved_fragment)
                        .show(editFragment)
                        .commit();
            }
        });
    }

    public void setAddProfileImage(){
        addProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDialogue();
            }
        });
    }

    private void startDialogue(){
        AlertDialog.Builder cameraAlertDialogue = new AlertDialog.Builder(getActivity());
        cameraAlertDialogue.setTitle("Upload Profile Pictures Option");
        cameraAlertDialogue.setMessage("How do you want to set your picture?");

        cameraAlertDialogue.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent pictureActionIntent = null;
                pictureActionIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                startActivityForResult(pictureActionIntent, GALLERY_PICTURE);
            }
        });

        cameraAlertDialogue.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = new File(android.os.Environment.getExternalStorageDirectory(),"temp.jpg");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));

                startActivityForResult(intent, CAMERA_REQUEST);

            }
        });
        cameraAlertDialogue.show();
    }
}
