package com.nyc.polymerse;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.nyc.polymerse.Invites.Invite_Frag;
import com.google.gson.Gson;
import com.nyc.polymerse.fragments.ExploreFragment;
import com.nyc.polymerse.fragments.NotificationFragment;
import com.nyc.polymerse.Profile_Creation.Prof_Create_Activity;
import com.nyc.polymerse.fragments.MessageFragment;
import com.nyc.polymerse.fragments.MessagingListFrag;
import com.nyc.polymerse.fragments.NotificationFragment;
import com.nyc.polymerse.fragments.UserDetailsFragment;
import com.nyc.polymerse.fragments.UserResultsFragment;

import java.io.IOException;

import butterknife.ButterKnife;

public class HomeActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "HomeActivity";
    private FirebaseAuth.AuthStateListener authListener;
    private FirebaseAuth auth;
    private FirebaseUser user;
    private BottomNavigationView bottomNavigationView;

    private DatabaseReference mDatabase;
    private DatabaseReference mDatabaseUser;
    private DatabaseReference mDatabaseUsers;

    private Fragment fragment;
    private ImageView imageView;
    private static final int PICK_IMAGE_REQUEST = 234;

    //a Uri object to store file path
    private Uri filePath;

    private String UserEmail;

    private Boolean isProfileNotCreated = true;
    private UserDetailsFragment frag;

    private SharedPreferences sharedPreferences;
    private boolean saveUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_navigation);
        ButterKnife.bind(this);
        // creating a shared preference to save the uID of the user.
        sharedPreferences = getSharedPreferences(Constants.FIREBASE_UID, MODE_PRIVATE);
        auth = FirebaseAuth.getInstance();
        authListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                Log.d(TAG, "onAuthStateChanged:  ran");
                user = firebaseAuth.getCurrentUser();
                if (user == null) {
                    // if user is null launch login activity
                    startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                    finish();
                } else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString(Constants.FIREBASE_UID_KEY, user.getUid());
                    editor.commit();
                    Log.d(TAG, "onAuthStateChanged: user isn't null");
                    Log.d(TAG, "onAuthStateChanged: " + user.getEmail());
                    Log.d(TAG, "onAuthStateChanged: " + user.getUid());
                    Toast.makeText(HomeActivity.this, user.getEmail() + " is logged in", Toast.LENGTH_SHORT).show();
                }
            }
        };

        mDatabase = FirebaseDatabase.getInstance().getReference();
        mDatabaseUsers = mDatabase.child(Constants.USERS);
        mDatabaseUsers.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                isProfileNotCreated = true;
                for (DataSnapshot d : dataSnapshot.getChildren()) {
                    String userKey = d.getKey();
                    Log.d(TAG, "onDataChange: user " + userKey);

                    profileNotCreated(userKey);
                    if (saveUser) {
                        UserSingleton.getInstance().setUser(d.getValue(User.class));
                   
                    }
                }
                if (isProfileNotCreated) {
                    Log.d(TAG, "onDataChange: uID " + user.getUid());
                    UserSingleton.getInstance().setUser(new User());
                    Log.d(TAG, "onDataChange: new user created in singleton");
                    UserSingleton.getInstance().getUser().setuID(user.getUid());
                    UserSingleton.getInstance().getUser().setEmail(user.getEmail());
                    startActivity(new Intent(HomeActivity.this, Prof_Create_Activity.class));
                    finish();
                }
                Log.d(TAG, "count " + dataSnapshot.getChildrenCount());
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.e("The read failed: ", databaseError.getMessage());

            }
        });

        fragment = new ExploreFragment();
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.fragment_container, fragment, "UserFrag");
        transaction.commit();

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("Polymerse");

        bottomNavigationView = findViewById(R.id.nav_tab);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
                switch (id) {
                    case R.id.nav_people:
                        fragment = new UserResultsFragment();
                        transaction.replace(R.id.fragment_container, fragment, "UserFrag");
                        transaction.commit();
                        toolbar.setTitle("Find Users");
                        Log.d(TAG, "onOptionsItemSelected: people clicked");
                        return true;
                    case R.id.nav_messages:
                        fragment = new MessagingListFrag();
                        Log.d(TAG, "onOptionsItemSelected: messages clicked");
                        transaction.replace(R.id.fragment_container, fragment, "msgFrag");
                        transaction.commit();
                        toolbar.setTitle("Messages");
                        return true;
                    case R.id.nav_notification:
                        fragment = new NotificationFragment();
                        transaction.replace(R.id.fragment_container, fragment, "notifrag");
                        Log.d(TAG, "onOptionsItemSelected: notification clicked");

                        transaction.commit();
                        toolbar.setTitle("Notifications");
                        return true;
                    case R.id.nav_explore:
                        fragment = new ExploreFragment();
                        transaction.replace(R.id.fragment_container, fragment, "explore_frag");
                        toolbar.setTitle("Polymerse");
                        transaction.commit();
                        return true;
                }
                return false;
            }
        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    private void profileNotCreated(String userKey) {
        String firebaseUid = sharedPreferences.getString(Constants.FIREBASE_UID_KEY, "");
        if (firebaseUid.equals(userKey)) {
            isProfileNotCreated = false;
            saveUser = true;
        } else {
            saveUser = false;
        }
    }

    //method to show file chooser
    private void showFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    //handling the image chooser activity result
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.navigation, menu);
        getMenuInflater().inflate(R.menu.menu_nav, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_profile) {
            Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
            // Handle the camera action
        } else if (id == R.id.nav_home) {

            Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();

        } else if (id == R.id.nav_history) {

        } else if (id == R.id.nav_settings) {
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);

        } else if (id == R.id.nav_sign_out) {
            signOutButton();
            Toast.makeText(this, "Sign out", Toast.LENGTH_SHORT).show();

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    //sign out method
    public void signOutButton() {
        auth.signOut();
        Log.d(TAG, "signOutButton: " + user.getEmail());
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onStart() {
        super.onStart();
        auth.addAuthStateListener(authListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (authListener != null) {
            auth.removeAuthStateListener(authListener);
        }
    }

    public void switchContent(int i, Fragment frag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, frag);
        ft.addToBackStack("");
        ft.commit();
    }
<<<<<<< HEAD

    public void switchContent(int i, MessageFragment frag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.replace(R.id.fragment_container, frag, "details_user_frag");
        ft.addToBackStack("user_detail_frag");
        ft.commit();
    }

    public void switchContent(int id, UserDetailsFragment frag) {
        FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        this.frag = frag;
        ft.replace(R.id.fragment_container, frag, "details_user_frag");
        ft.addToBackStack("user_detail_frag");
        ft.commit();
    }
=======
>>>>>>> master
}
