package com.example.chattingapp;

public class Userinformation {

    public String nickname;
    public String phonenum;

    public Userinformation() {

    }
    public Userinformation(String nickname,String phonenum){
        this.nickname = nickname;
        this.phonenum = phonenum;
    }
    public String getNickname() {
        return nickname;
    }
    public String getPhonenum() {
        return phonenum;
    }
}
