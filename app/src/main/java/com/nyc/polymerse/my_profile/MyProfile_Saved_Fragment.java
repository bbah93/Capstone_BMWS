package com.nyc.polymerse.my_profile;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.icu.text.SimpleDateFormat;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.FileProvider;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.nyc.polymerse.BuildConfig;
import com.nyc.polymerse.R;
import com.nyc.polymerse.models.User;
import com.nyc.polymerse.UserSingleton;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Date;
import java.util.Map;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;
import static com.nyc.polymerse.Constants.PROF_CREATE_KEY;

/**
 * A simple {@link Fragment} subclass.
 */
public class MyProfile_Saved_Fragment extends Fragment {

    public static String TAG = "MyProfile_Saved_Fragment";
    View rootView;
    Button addProfileImage;
    FloatingActionButton editProfileButton;
    CircleImageView profileImage;
    TextView name_Input;
    TextView location_Input;
    TextView aboutMeInput;
    TextView learning_Input;
    TextView sharing_Input;
    ProgressBar learningLevel;
    ProgressBar sharingLevel;
    SharedPreferences profileDetails;
    User currentUser;

    protected static final int CAMERA_REQUEST = 0;
    protected static final int GALLERY_PICTURE = 1;
    private Intent pictureActionIntentI;
    Bitmap bitmap;
    String currentPhotoPath;
    private static final String AUTHORITY = BuildConfig.APPLICATION_ID+".fileprovider";

    //TODO: Alert Dialogue for langauges and fluency levels

    public MyProfile_Saved_Fragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_my_profile__saved, container, false);
        addProfileImage = rootView.findViewById(R.id.ad_profile_image);
        editProfileButton = rootView.findViewById(R.id.save_profile_fab);
        profileImage = rootView.findViewById(R.id.user_profile_avatar);
        name_Input = rootView.findViewById(R.id.userName);
        location_Input = rootView.findViewById(R.id.user_location);
        aboutMeInput = rootView.findViewById(R.id.aboutMe_EditTexts);
        learning_Input = rootView.findViewById(R.id.prof_langLearn_spinner);
        sharing_Input = rootView.findViewById(R.id.sharing_lang_spinner);
        learningLevel = rootView.findViewById(R.id.myProfile_learning_level);
        sharingLevel = rootView.findViewById(R.id.myprof_sharing_level);
        currentUser = UserSingleton.getInstance().getUser();

        profileDetails = getActivity().getSharedPreferences(PROF_CREATE_KEY, Context.MODE_PRIVATE);

        if (currentUser != null) {
            grabProfileURL();
            editProfileClick();
            setAddProfileImage();
            grabUserInfo();
        }

        return rootView;
    }

    //TODO: Seperate camera logic into a presenter class
    //TODO:Add File Provider to solve UriExposed exception
    /**
     * Beginning of Camera Logic
     */
    public void setAddProfileImage() {
        addProfileImage.setOnClickListener(v -> startDialogue());
    }

    private void startDialogue() {
       android.support.v7.app.AlertDialog cameraAlertDialogue = new android.support.v7.app.AlertDialog.Builder(getActivity())
               .setIcon(android.R.drawable.ic_dialog_alert)
               .setTitle("Upload Profile Pictures Option")
               .setMessage("How do you want to set your picture?")
               .setPositiveButton("Gallery", (dialog, which) -> {
                   //Gallery Option
                   Intent galleryIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                   galleryIntent.setType("image/*");
                   startActivityForResult(galleryIntent, GALLERY_PICTURE);
               }).setNegativeButton("Camera", (dialog, which) -> {
                   //Camera Option
                   Intent cameraIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                   if (cameraIntent.resolveActivity(getActivity().getPackageManager()) != null){
                       File photoFile = null;
                       try{
                           photoFile = createImageFile();
                       } catch (IOException e) {
                           // Error occurred while creating the File
                           e.printStackTrace();
                       }
                       // Continue only if the File was successfully created
                       if (photoFile != null){
                           Uri photoURI = FileProvider.getUriForFile(getActivity(),
                                   AUTHORITY,
                                   photoFile);
                           cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                           startActivityForResult(cameraIntent, CAMERA_REQUEST);
                       }
                   }
               }).show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST){
            Glide.with(this).load(currentPhotoPath).into(profileImage);
        }
        else if (resultCode == RESULT_OK && requestCode == GALLERY_PICTURE){
            try {
                final Uri imageUri = data.getData();
                final InputStream imageStream = getActivity().getContentResolver().openInputStream(imageUri);
                final Bitmap selectedImage = BitmapFactory.decodeStream(imageStream);
                profileImage.setImageBitmap(selectedImage);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                Toast.makeText(getActivity(), "Something went wrong", Toast.LENGTH_LONG).show();
            }

        }else {
            Toast.makeText(getActivity(), "You haven't picked Image",Toast.LENGTH_LONG).show();
        }
    }
    /**
     * End of Camera Logic
     */
    public void grabProfileURL() {
        if (currentUser != null) {
            if (currentUser.getProfilePic() != null) {
                String imgUrl = currentUser.getProfilePic();
                Log.d(TAG, "grabProfileURL: " + imgUrl);
                Picasso.get().load(imgUrl).fit().placeholder(R.drawable.ic_account_circle_black_24dp).into(profileImage);
            }
        }
    }

    //FAB click to go to Edit_MyProfile_Fragment so user can edit their info
    public void editProfileClick() {
        editProfileButton.setOnClickListener(v -> {
            Edit_MyProfile_Fragment editFragment = new Edit_MyProfile_Fragment();
            FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.my_prof_fragment_container, editFragment)
                    //.addToBackStack("Saved Profile Fragment")
                    .commit();
        });
    }


    //TODO: Decide how name and user info will be stored in db
    //Takes all user info which includes: about me, known and learning languages, fluency levels, and location
    public void grabUserInfo() {

        if (currentUser.getAboutMe() != null) {
            aboutMeInput.setText(currentUser.getAboutMe());
        }
        name_Input.setText(currentUser.getUsername());
        String locationString = currentUser.getCity() + ", " + currentUser.getState();
        location_Input.setText(locationString);


        //TODO:Put about me in if statement
        //aboutMeInput
        Map<String, String> langLearn = currentUser.getLangLearn();
        Map<String, String> langTeach = currentUser.getLangTeach();

        String learnLevel = profileDetails.getString("learning_level", null);
        String sharingLevelString = profileDetails.getString("fluent_level", null);
        if (langLearn != null) {
            for (String s : langLearn.keySet()) {
                learning_Input.setText(s);
                learnLevel = langLearn.get(s);
            }
        } else {
            learnLevel = "";
        }
        if (langTeach != null) {
            for (String s : langTeach.keySet()) {
                sharing_Input.setText(s);
                sharingLevelString = langTeach.get(s);
            }
        } else {
            sharingLevelString = "";
        }

        if (learnLevel == null){
            learnLevel = "";
        }
        switch (learnLevel) {
            case "Beginner":
                learningLevel.setProgress(25);
                break;
            case "Intermediate":
                learningLevel.setProgress(50);
                break;
            case "Advanced":
                learningLevel.setProgress(75);
                break;
            case "Fluent":
                Log.d(TAG, "grabUserInfo: user learn language level " + learnLevel);
                learningLevel.setProgress(100);
                break;
            default:
                learningLevel.setProgress(0);
                break;
        }

        if (sharingLevelString == null) {
            sharingLevelString = "";
        }
        switch (sharingLevelString) {
            case "Beginner":
                sharingLevel.setProgress(25);
                break;
            case "Intermediate":
                sharingLevel.setProgress(50);
                break;
            case "Advanced":
                sharingLevel.setProgress(75);
                break;
            case "Fluent":
                sharingLevel.setProgress(100);
                break;
            default:
                sharingLevel.setProgress(0);
                break;
        }
    }

    private File createImageFile() throws IOException{
        //create an image file name using timestamp
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",  /* suffix */
                storageDir      /* directory */
        );
        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;
    }

}
