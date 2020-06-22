package com.example.chatproject.component;

import android.os.Bundle;

import androidx.annotation.Nullable;

import com.example.chatproject.R;
import com.example.chatproject.component.base.BaseActivity;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends BaseActivity {

    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mAuth = FirebaseAuth.getInstance();
    }
    private void signOut() {
        FirebaseAuth.getInstance().signOut();
    }
}

