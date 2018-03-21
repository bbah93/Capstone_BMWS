package com.nyc.polymerse;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.nyc.polymerse.Profile_Creation.Prof_Create_Activity;

public class SignupActivity extends AppCompatActivity {

    private static final String TAG = "SignupActivity";
    FirebaseAuth auth;
    private ProgressBar progressBar;
    private EditText signupInputEmail;
    private EditText signupInputPassword;
    private Button btnSignUp;
    private Button btnLinkToLogIn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        auth = FirebaseAuth.getInstance();
        progressBar = (ProgressBar) findViewById(R.id.progressBar);

        signupInputEmail = (EditText) findViewById(R.id.signup_input_email);
        signupInputPassword = (EditText) findViewById(R.id.signup_input_password);

        btnSignUp = (Button) findViewById(R.id.btn_signup);
        btnLinkToLogIn = (Button) findViewById(R.id.btn_link_login);


        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitForm();

            }
        });

        btnLinkToLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SignupActivity.this, LoginActivity.class);
                startActivity(intent);
            }
        });

    }
    private void submitForm() {

        String email = signupInputEmail.getText().toString().trim();
        String password = signupInputPassword.getText().toString().trim();

        if (email.isEmpty() || password.isEmpty()) {
            return;
        } else if (!isEmailValid(email)) {
            return;
        } else if (!isPasswordValid(password)) {
            return;
        }


        progressBar.setVisibility(View.VISIBLE);
        //create user
        auth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        Log.d(TAG,"createUserWithEmail:onComplete:" + task.isSuccessful());
                        progressBar.setVisibility(View.GONE);
                        // If sign in fails, Log the Message to the LogCat. If sign in succeeds
                        // the auth state listener will be notified and logic to handle the
                        // signed in user can be handled in the listener.
                        if (!task.isSuccessful()) {
                            Log.d(TAG,"Authentication failed." + task.getException());

                        } else {
                            startActivity(new Intent(SignupActivity.this, Prof_Create_Activity.class));
                            Toast.makeText(getApplicationContext(), "You are successfully Registered !!", Toast.LENGTH_SHORT).show();
                            finish();
                        }
                    }
                });
    }

    private static boolean isEmailValid(String email) {
        return !TextUtils.isEmpty(email) && android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private static boolean isPasswordValid(String password){
        return (password.length() >= 6);
    }

    @Override
    protected void onResume() {
        super.onResume();
        progressBar.setVisibility(View.GONE);
    }
}

