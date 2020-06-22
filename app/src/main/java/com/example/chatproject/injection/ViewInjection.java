package com.example.chatproject.injection;

import android.view.View;

import androidx.lifecycle.LifecycleOwner;

import com.example.chatproject.view.LoginView;
import com.example.chatproject.view.ToastView;
import com.example.chatproject.viewimpl.LoginViewImpl;
import com.example.chatproject.viewimpl.ToastViewImpl;

public class ViewInjection {

    private ViewInjection() {

    }

    public static LoginView provideLoginView(View view, LifecycleOwner lifecycleOwner) {
        return new LoginViewImpl(view, lifecycleOwner);
    }

    public static ToastView provideToastView() {
        return new ToastViewImpl();
    }
}
