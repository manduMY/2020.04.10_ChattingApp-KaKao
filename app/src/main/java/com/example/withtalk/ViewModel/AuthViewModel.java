package com.example.withtalk.ViewModel;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;

import com.example.withtalk.Model.User;
import com.example.withtalk.Repository.AuthRepository;
import com.google.firebase.auth.AuthCredential;

public class AuthViewModel extends AndroidViewModel {
    public LiveData<Object> authenticatedUserLiveData;
    private AuthRepository authRepository;
    LiveData<User> authenticatedUserLiveData;

    public AuthViewModel(Application application) {
        super(application);
        authRepository = new AuthRepository();
    }

    void signInWithGoogle(AuthCredential googleAuthCredential) {
        authenticatedUserLiveData = authRepository.firebaseSignInWithGoogle(googleAuthCredential);
    }

    void createUser(User authenticatedUser) {
        createdUserLiveData = authRepository.createUserInFirestoreIfNotExists(authenticatedUser);
    }
}
