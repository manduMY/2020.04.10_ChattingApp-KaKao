package com.example.withtalk.Model;

import java.io.Serializable;

public class User implements Serializable {

    private String email;
    private String name;
    private String phoneno;

    public User() {}

    public User(String email, String name, String phoneno) {
        this.email = email;
        this.name = name;
        this.phoneno = phoneno;
    }
//    @NonNull
//    public String getEmail() {
//        return email;
//    }
//
//    public void setEmail(@NonNull String email) {
//        this.email = email;
//    }
//
//    @NonNull
//    public String getPassword() {
//        return password;
//    }
//
//    public void setPassword(@NonNull String password) {
//        this.password = password;
//    }
//    public boolean isValidData() {
//        return !TextUtils.isEmpty(getEmail()) &&
//                Patterns.EMAIL_ADDRESS.matcher(getEmail()).matches() &&
//                getPassword().length() > 7;
//    }
}
