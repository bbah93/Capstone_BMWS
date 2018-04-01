package com.nyc.polymerse.My_Profile;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
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
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.nyc.polymerse.HomeActivity;
import com.nyc.polymerse.R;

import java.io.File;
import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;

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

    Fragment frag = this;


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
                frag.startActivityForResult(pictureActionIntent, GALLERY_PICTURE);
            }
        });

        cameraAlertDialogue.setNegativeButton("Camera", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                File file = new File(android.os.Environment.getExternalStorageDirectory(),"temp.jpg");
                intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(file));

                frag.startActivityForResult(intent, CAMERA_REQUEST);

            }
        });
        cameraAlertDialogue.show();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);

        bitmap = null;
        selectedImagePath = null;

        if(resultCode == RESULT_OK && requestCode == CAMERA_REQUEST){

            File f = new File(Environment.getExternalStorageDirectory()
            .toString());
            for (File temp : f.listFiles()){
                if(temp.getName().equals("temp.jpg")){
                    f = temp;
                    break;
                }
            }
            if (!f.exists()) {
                Toast.makeText(getActivity().getBaseContext(), "Error while capturing image", Toast.LENGTH_LONG).show();
                return;
            }
            try{
                bitmap = BitmapFactory.decodeFile(f.getAbsolutePath());

                bitmap = Bitmap.createScaledBitmap(bitmap, 400, 400, true);

                int rotate = 0;
                try {
                    ExifInterface exifInterface = new ExifInterface(f.getAbsolutePath());
                    int orientation = exifInterface
                            .getAttributeInt(ExifInterface.TAG_ORIENTATION,
                            ExifInterface.ORIENTATION_NORMAL);

                    switch (orientation){
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
                bitmap = Bitmap.createBitmap(bitmap, 0, 0,bitmap.getWidth(), bitmap.getHeight(), matrix, true);

                //setting profile image
                profileImage.setImageBitmap(bitmap);
            } catch (Exception e){
                e.printStackTrace();
            }
        }
        else if(resultCode == RESULT_OK && requestCode == GALLERY_PICTURE){
            if(data != null){

                Uri selectedImage = data.getData();
                String [] filePath = {MediaStore.Images.Media.DATA};
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
            }
            else {
                Toast.makeText(getActivity().getApplicationContext(), "Cancelled",
                        Toast.LENGTH_SHORT).show();
            }
        }
    }




    //TODO: Decide how name and user info will be stored in db
    public void grabUserInfo(){

    }
}
