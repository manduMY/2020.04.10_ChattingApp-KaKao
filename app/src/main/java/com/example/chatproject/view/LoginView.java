package com.example.chatproject.view;

import androidx.lifecycle.LiveData;

import com.example.chatproject.view.action.OnNotifySignInSuccessAction;
import com.example.chatproject.view.action.OnRenderToastAction;
import com.example.chatproject.view.action.OnRequestedSignInAction;
import com.google.firebase.auth.FirebaseUser;

public interface LoginView {

    void setFirebaseUserLiveData(LiveData<FirebaseUser> liveData);

    void setThrowableUserLiveData(LiveData<Throwable> liveData);

    void setActionListener(ActionListener actionListener);

    interface ActionListener extends OnNotifySignInSuccessAction, OnRequestedSignInAction, OnRenderToastAction {
    }
}