package com.example.chatproject.viewimpl;

import android.view.View;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;

import com.example.chatproject.R;
import com.example.chatproject.common.Logger;
import com.example.chatproject.view.LoginView;
import com.google.android.gms.common.SignInButton;
import com.google.firebase.auth.FirebaseUser;

public class LoginViewImpl implements LoginView {

    private static final String TAG = LoginViewImpl.class.getSimpleName();

    private View mMainView;
    private LifecycleOwner mLifecycleOwner;
    private ActionListener mActionListener;

    public LoginViewImpl(View view, LifecycleOwner lifecycleOwner) {
        mMainView = view;
        mLifecycleOwner = lifecycleOwner;

        SignInButton signInButton = mMainView.findViewById(R.id.Btn_Google_SignIn);
        signInButton.setSize(SignInButton.SIZE_STANDARD);
        signInButton.setOnClickListener(v -> mActionListener.onRequestedSignIn());
    }


    @Override
    public void setFirebaseUserLiveData(LiveData<FirebaseUser> liveData) {
        liveData.observe(mLifecycleOwner, data -> {
            if (liveData.getValue() == null) {
                showLoginButton();
                return;
            }
            mActionListener.onNotifySignInSuccess();
        });
    }

    @Override
    public void setThrowableUserLiveData(LiveData<Throwable> liveData) {
        liveData.observe(mLifecycleOwner, data -> {
            Logger.e(TAG, data.getMessage());
            mActionListener.onRenderToast(data.getMessage());
            showLoginButton();
        });
    }

    @Override
    public void setActionListener(ActionListener actionListener) {
        mActionListener = actionListener;
    }
    private void showLoginButton() {
        mMainView.setVisibility(View.VISIBLE);
    }
}
