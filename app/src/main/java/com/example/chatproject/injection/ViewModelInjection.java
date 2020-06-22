package com.example.chatproject.injection;

import android.app.Activity;

import com.example.chatproject.viewmodel.usecase.LoginUsecaseExecutor;
import com.example.chatproject.viewmodelimpl.usecase.LoginUsecaseExecutorImpl;

public class ViewModelInjection {

    private ViewModelInjection() {
    }
    public static LoginUsecaseExecutor provideLoginUsecaseExecutor(Activity activity) {
        return new LoginUsecaseExecutorImpl(activity);
    }
}
