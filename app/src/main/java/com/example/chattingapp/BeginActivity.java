package com.example.chattingapp;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class BeginActivity extends AppCompatActivity {

    private FirebaseAuth mAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin);

        mAuth = FirebaseAuth.getInstance();
        mAuth.addAuthStateListener(authStateListener);
    }
    FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
        @Override
        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();

            if (firebaseUser == null) {
                Intent in = new Intent(BeginActivity.this, SignInActivity.class);
                startActivity(in);
                finish();
            } else if(firebaseUser != null) {
                Intent in = new Intent(BeginActivity.this, MainActivity.class);
                startActivity(in);
                finish();
            } else {

            }

        }
    };
}
