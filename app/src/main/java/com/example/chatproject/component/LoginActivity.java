package com.example.chatproject.component;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.lifecycle.ViewModelProvider;

import com.example.chatproject.R;
import com.example.chatproject.component.base.BaseActivity;
import com.example.chatproject.injection.ViewInjection;
import com.example.chatproject.injection.ViewModelInjection;
import com.example.chatproject.view.LoginView;
import com.example.chatproject.viewmodel.screen.LoginScreenViewModel;
import com.example.chatproject.viewmodelimpl.screen.LoginScreenViewModelImpl;

public class LoginActivity extends BaseActivity {

    // 클래스 변수여서 변수명에 m을 붙임.
    private LoginScreenViewModel mScreenViewModel;

    private ViewModelProvider.AndroidViewModelFactory viewModelFactory;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        if (viewModelFactory == null) {
            viewModelFactory = ViewModelProvider.AndroidViewModelFactory.getInstance(getApplication());
        }
        mScreenViewModel = new ViewModelProvider(this, viewModelFactory).get(LoginScreenViewModelImpl.class);
        mScreenViewModel.setParentContext(this);

        injectViewModel(mScreenViewModel);
        injectView(mScreenViewModel);
    }

    private void injectViewModel(LoginScreenViewModel viewModel) {
        viewModel.setLoginUsecaseExecutor(ViewModelInjection.provideLoginUsecaseExecutor(this));
    }

    private void injectView(LoginScreenViewModel viewModel) {
        viewModel.setToastView(ViewInjection.provideToastView());

        LoginView loginView = ViewInjection.provideLoginView(findViewById(R.id.login_trigger_container), this);
        viewModel.setLoginView(loginView);
    }

    @Override
    protected void onStart() {
        super.onStart();
        mScreenViewModel.loadUserData();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mScreenViewModel.onActivityResult(requestCode, resultCode, data);
    }
}