package com.example.chatproject.viewmodelimpl.screen;

import android.app.Activity;
import android.content.Intent;
import android.widget.Toast;

import androidx.lifecycle.ViewModel;

import com.example.chatproject.component.MainActivity;
import com.example.chatproject.view.LoginView;
import com.example.chatproject.view.ToastView;
import com.example.chatproject.viewmodel.screen.LoginScreenViewModel;
import com.example.chatproject.viewmodel.usecase.LoginUsecaseExecutor;
import com.example.chatproject.viewmodelimpl.usecase.LoginUsecaseExecutorImpl;

import java.lang.ref.WeakReference;

public class LoginScreenViewModelImpl extends ViewModel implements LoginScreenViewModel {

    private static final int REQ_CODE_SIGN_IN = 1;
    private WeakReference<Activity> mActivityRef;

    // View
    private ToastView mToastView;

    // LiveData
    private LoginUsecaseExecutor mLoginUsercaseExecutor;

    public LoginScreenViewModelImpl() {
    }

    @Override
    public void setParentContext(Activity parentContext) {
        mActivityRef = new WeakReference<>(parentContext);
    }

    @Override
    public void setLoginUsecaseExecutor(LoginUsecaseExecutor executor) {
        mLoginUsercaseExecutor = executor;
    }

    @Override
    public void setToastView(ToastView view) {
        mToastView = view;
    }

    @Override
    public void setLoginView(LoginView view) {
        view.setActionListener(this);
        view.setFirebaseUserLiveData(mLoginUsercaseExecutor.getUserLiveData());
        view.setThrowableUserLiveData(mLoginUsercaseExecutor.getThrowableLiveData());
    }

    @Override
    public void loadUserData() {
        mLoginUsercaseExecutor.loadUserData();
    }

    @Override
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE_SIGN_IN) {
            mLoginUsercaseExecutor.firebaseAuthWithGoogle(data, mActivityRef.get());
            return true;
        }
        return false;
    }

    @Override
    public void onNotifySignInSuccess() {
        if (mActivityRef.get() == null) {
            return;
        }
        Intent intent = new Intent(mActivityRef.get(), MainActivity.class);
        mActivityRef.get().startActivity(intent);
        finishActivity();
    }

    @Override
    public void onRenderToast(String msg) {
        if (mActivityRef.get() != null) {
            mToastView.render(mActivityRef.get().getApplicationContext(), msg);
        }
    }

    @Override
    public void onRequestedSignIn() {
        Intent signIntent = mLoginUsercaseExecutor.getSignInIntent();
        if (mActivityRef.get() != null) {
            mActivityRef.get().startActivityForResult(signIntent, REQ_CODE_SIGN_IN);
        }
    }
    private void finishActivity() {
        if (mActivityRef.get() != null) {
            mActivityRef.get().finish();
        }
    }
}