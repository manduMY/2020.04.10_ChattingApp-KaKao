package com.example.chatproject.viewmodelimpl.usecase;

import android.app.Activity;
import android.content.Intent;
import android.nfc.Tag;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.chatproject.R;
import com.example.chatproject.viewmodel.usecase.LoginUsecaseExecutor;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class LoginUsecaseExecutorImpl implements LoginUsecaseExecutor {

    private static final String TAG = LoginUsecaseExecutorImpl.class.getSimpleName();

    private MutableLiveData<FirebaseUser> mFirebaseUserLiveData = new MutableLiveData<>();
    private MutableLiveData<Throwable> mThrowableLiveData = new MutableLiveData<>();

    private GoogleSignInClient mGoogleSignInClient;
    private FirebaseAuth mAuth;

    public LoginUsecaseExecutorImpl(Activity activity) {
        mAuth = FirebaseAuth.getInstance();

        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(activity.getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleSignInClient = GoogleSignIn.getClient(activity, gso);
    }

    @Override
    public void loadUserData() {
        FirebaseUser user = mAuth.getCurrentUser();
        mFirebaseUserLiveData.setValue(user);
    }

    @Override
    public LiveData<FirebaseUser> getUserLiveData() {
        return mFirebaseUserLiveData;
    }

    @Override
    public LiveData<Throwable> getThrowableLiveData() {
        return mThrowableLiveData;
    }

    @Override
    public void firebaseAuthWithGoogle(Intent data, Activity activity) {
        Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
        GoogleSignInAccount account;
        try {
            account = task.getResult(ApiException.class);
        } catch (ApiException e) {
            e.printStackTrace();
            mThrowableLiveData.setValue(e);
            return;
        }
        if (account == null) {
            mThrowableLiveData.setValue(new NullPointerException("Failed to retrieve a GoogleSignInAccount"));
            return;
        }

        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential).addOnCompleteListener(activity, paramTask -> {
           if (paramTask.isSuccessful()) {
               FirebaseUser user = mAuth.getCurrentUser();
               mFirebaseUserLiveData.setValue(user);
               return;
           }
           mThrowableLiveData.setValue(task.getException());
        });
    }

    @Override
    public Intent getSignInIntent() {
        return mGoogleSignInClient.getSignInIntent();
    }
}