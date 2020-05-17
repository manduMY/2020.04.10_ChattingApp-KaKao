package com.example.chocotalk.Splash;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import android.content.Intent;
import android.os.Bundle;

import com.example.chocotalk.Auth.AuthActivity;
import com.example.chocotalk.MainActivity;
import com.example.chocotalk.Model.User;
import com.example.chocotalk.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashActivity extends AppCompatActivity {
    public SplashViewModel splashViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        checkIfUserIsAuthenticated();
    }
    private void checkIfUserIsAuthenticated() {
        splashViewModel.checkUserIsAuthenticated();
        splashViewModel.isUserAuthenticatedLiveData.observe(this, new Observer<User>() {
            @Override
            public void onChanged(User user) {
                if(!user.isAuthenticated) {
//                    Intent intent = new Intent(SplashActivity.this, AuthActivity.class);
//                    startActivity(intent);
                } else {
                    return;
                }
            }
        });
    }
}
