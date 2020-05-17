package com.example.chocotalk.Splash;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.example.chocotalk.Model.User;

public class SplashViewModel extends ViewModel {
    private SplashRepository splashRepository;
    LiveData<User> isUserAuthenticatedLiveData;
    LiveData<User> userLiveData;

    SplashViewModel(SplashRepository splashRepository) {
        this.splashRepository = splashRepository;
    }

    void checkUserIsAuthenticated() {
        isUserAuthenticatedLiveData = splashRepository.checkUserIsAuthenticatedInFirebase();
    }
}
