package com.nyc.polymerse.My_Profile;

import android.content.Intent;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.nyc.polymerse.HomeActivity;
import com.nyc.polymerse.R;
import com.nyc.polymerse.SettingsActivity;
import com.nyc.polymerse.fragments.UserResultsFragment;

public class MyProfileActivity extends AppCompatActivity {
    private static final String TAG = null;
    private FirebaseAuth auth;
    private FirebaseUser user;

    //TODO: Add hide and show for fragments

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_profile);
        if (savedInstanceState == null) {
            MyProfile_Saved_Fragment profileSavedFragment = new MyProfile_Saved_Fragment();
            getSupportFragmentManager()
                    .beginTransaction()
                    .add(R.id.my_prof_fragment_container, profileSavedFragment, MyProfile_Saved_Fragment.TAG )
                    .commit();
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){
        super.onActivityResult(requestCode, resultCode, data);
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

        if (id == R.id.nav_sign_out) {
            signOutButton();
        }
        if (id == R.id.nav_home) {
            Intent homeIntent = new Intent(this, HomeActivity.class);
            startActivity(homeIntent);
            Log.d(TAG, "onOptionsItemSelected: people clicked");
            Toast.makeText(this, "Home", Toast.LENGTH_SHORT).show();
        }
        if (id == R.id.nav_settings) {
            Intent intent = new Intent(MyProfileActivity.this, SettingsActivity.class);
            startActivity(intent);
        }
        if (id == R.id.nav_profile) {
            Toast.makeText(this, "Profile", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(this, MyProfileActivity.class);
            startActivity(intent);


        }

        return super.onOptionsItemSelected(item);
    }

    //sign out method
    public void signOutButton() {
        auth.signOut();
        Log.d(TAG, "signOutButton: " + user.getEmail());
    }


}
