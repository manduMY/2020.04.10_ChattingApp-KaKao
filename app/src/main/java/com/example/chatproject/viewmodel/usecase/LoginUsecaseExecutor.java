package com.example.chatproject.viewmodel.usecase;

import android.app.Activity;
import android.content.Intent;

import androidx.lifecycle.LiveData;

import com.google.firebase.auth.FirebaseUser;

public interface LoginUsecaseExecutor {

    void loadUserData();

    LiveData<FirebaseUser> getUserLiveData();

    LiveData<Throwable> getThrowableLiveData();

    void firebaseAuthWithGoogle(Intent data, Activity activity);

    Intent getSignInIntent();
}