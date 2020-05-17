package com.example.chocotalk.Model;

import android.util.Patterns;

public class User {
    public String uid;
    public String nickname;
    public String phonenum;
    public boolean isAuthenticated;

    public User() {

    }
    public User(String uid, String nickname,String phonenum){
        this.uid = uid;
        this.nickname = nickname;
        this.phonenum = phonenum;
    }
    public String getUid() { return uid; }
    public String getNickname() {
        return nickname;
    }
    public String getPhonenum() {
        return phonenum;
    }
}
