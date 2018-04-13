package com.nyc.polymerse.My_Profile;


import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.nyc.polymerse.R;
import com.nyc.polymerse.User;
import com.nyc.polymerse.UserSingleton;
import com.squareup.picasso.Picasso;

import java.io.File;
import java.io.IOException;
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
    private Intent pictureActionIntent = null;
    Bitmap bitmap;

    String selectedImagePath;

    Fragment frag = this;

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

    public void editProfileClick() {
        editProfileButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Edit_MyProfile_Fragment editFragment = new Edit_MyProfile_Fragment();
                MyProfile_Saved_Fragment profile_saved_fragment = new MyProfile_Saved_Fragment();
                FragmentManager fragmentManager = getActivity().getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .replace(R.id.my_prof_fragment_container, editFragment)
                        //.addToBackStack("Saved Profile Fragment")
                        .commit();
            }
        });
    }

    public void setAddProfileImage() {
        addProfileImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startDialogue();
            }
        });
    }

    private void startDialogue() {
        AlertDialog.Builder cameraAlertDialogue = new AlertDialog.Builder(getActivity());
        cameraAlertDialogue.setTitle("Upload Profile Pictures Option");
        cameraAlertDialogue.setMessage("How do you want to set your picture?");

        cameraAlertDialogue.setPositiveButton("Gallery", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent pictureActionIntent = null;

                pictureActionIntent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                getActivity().startActivityForResult(pictureActionIntent, GALLERY_PICTURE);
            }
        });

        cameraAlertDialogue.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));
                intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);

                getActivity().startActivityForResult(intent, CAMERA_REQUEST);

            }
        });
        cameraAlertDialogue.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        bitmap = null;
        selectedImagePath = null;

        if (resultCode == RESULT_OK && requestCode == CAMERA_REQUEST) {

            File f = new File(Environment.getExternalStorageDirectory()
                    .toString());
            for (File temp : f.listFiles()) {
                if (temp.getName().equals("temp.jpg")) {
                    f = temp;
                    break;
                }
            }
            if (!f.exists()) {
                Toast.makeText(getActivity().getBaseContext(), "Error while capturing image", Toast.LENGTH_LONG).show();
                return;
            }
            try {
                bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());

                bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, true);

                int rotate = 0;
                try {
                    ExifInterface exifInterface = new ExifInterface(f.getAbsolutePath());
                    int orientation = exifInterface
                            .getAttributeInt(ExifInterface.TAG_ORIENTATION,
                                    ExifInterface.ORIENTATION_NORMAL);

                    switch (orientation) {
                        case ExifInterface.ORIENTATION_ROTATE_270:
                            rotate = 270;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_180:
                            rotate = 180;
                            break;
                        case ExifInterface.ORIENTATION_ROTATE_90:
                            rotate = 90;
                            break;
                    }

                } catch (IOException e) {
                    e.printStackTrace();
                }
                Matrix matrix = new Matrix();
                matrix.postRotate(rotate);
                bitmap = Bitmap.createBitmap(bitmap, 0, 0, bitmap.getWidth(), bitmap.getHeight(), matrix, true);

                //setting profile image
                profileImage.setImageBitmap(bitmap);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else if (resultCode == RESULT_OK && requestCode == GALLERY_PICTURE) {
            if (data != null) {

                Uri selectedImage = data.getData();
                String[] filePath = {MediaStore.Images.Media.DATA};
                Cursor c = getActivity().getContentResolver().query(selectedImage, filePath,
                        null, null, null);
                c.moveToFirst();
                int columnIndex = c.getColumnIndex(filePath[0]);
                selectedImagePath = c.getString(columnIndex);
                c.close();

//                if (selectedImagePath != null) {
//                    // txt_image_path.setText(selectedImagePath);
//                }

                bitmap = BitmapFactory.decodeFile(selectedImagePath); // load
                // preview image
                bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, false);

                profileImage.setImageBitmap(bitmap);
            } else {
                Toast.makeText(getActivity().getApplicationContext(), "Cancelled",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }


    //TODO: Decide how name and user info will be stored in db
    public void grabUserInfo() {

        if (currentUser.getAboutMe() != null) {
            aboutMeInput.setText(currentUser.getAboutMe());
        }
        name_Input.setText(currentUser.getUsername());
        String locationString = currentUser.getCity() + ", " + currentUser.getState();
        location_Input.setText(locationString);
//        name_Input.setText(String.format("%s %s", profileDetails.getString("first_name", null), profileDetails.getString("last_name", null)));
//        location_Input.setText(String.format("%s %s", profileDetails.getString("city", null), profileDetails.getString("state", null)));

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

    public void grabProfileURL() {

        if (currentUser != null) {
            if (currentUser.getProfilePic() != null) {
                String imgUrl = currentUser.getProfilePic();
                Log.d(TAG, "grabProfileURL: " + imgUrl);
                Picasso.get().load(imgUrl).fit().placeholder(R.drawable.ic_account_circle_black_24dp).into(profileImage);
            }
        }
    }

}
