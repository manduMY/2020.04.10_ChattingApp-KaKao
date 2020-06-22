package com.example.chatproject.viewmodel.screen;

import android.app.Activity;
import android.content.Intent;

import com.example.chatproject.view.LoginView;
import com.example.chatproject.view.ToastView;
import com.example.chatproject.viewmodel.usecase.LoginUsecaseExecutor;

public interface LoginScreenViewModel extends LoginView.ActionListener{
    void setParentContext(Activity parentContext);

    void setLoginUsecaseExecutor(LoginUsecaseExecutor executor);

    void setToastView(ToastView view);

    void setLoginView(LoginView view);

    void loadUserData();

    boolean onActivityResult(int requestCode, int resultCode, Intent data);
}
