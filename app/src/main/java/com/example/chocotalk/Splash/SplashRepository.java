package com.example.chocotalk.Splash;

import androidx.lifecycle.MutableLiveData;

import com.example.chocotalk.Model.User;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SplashRepository {
    private FirebaseAuth firebaseAuth;
    private User user;

    public SplashRepository(FirebaseAuth firebaseAuth, User user) {
        this.firebaseAuth = firebaseAuth;
        this.user = user;
    }

    // 인증 문제는 결과값이 언제 반환되는지 알아야 하기 때문에 동기적 처리를 함.
    MutableLiveData<User> checkUserIsAuthenticatedInFirebase() {
        MutableLiveData<User> userIsAuthenticatedInFirebaseMutableLiveData = new MutableLiveData<>();
        FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
        if (firebaseUser == null) {
            user.isAuthenticated = false;
            userIsAuthenticatedInFirebaseMutableLiveData.setValue(user);
        } else {
            user.uid = firebaseUser.getUid();
            user.isAuthenticated = true;
            userIsAuthenticatedInFirebaseMutableLiveData.setValue(user);
        }
        return userIsAuthenticatedInFirebaseMutableLiveData;
    }

}
