package com.example.chattingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;


public class MainActivity extends AppCompatActivity {
    BottomNavigationView bottomNavigationView;

    Fragment menuFriendListFragment;
    Fragment menuChatListFragment;

    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_friendList:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, menuFriendListFragment).commit();
                    return true;
                case R.id.navigation_chatList:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, menuChatListFragment).commit();
                    return true;
                case R.id.bottom_navi3:
                    return true;
            }
            return false;
        }
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);


        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        menuFriendListFragment = new FriendListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, menuFriendListFragment).commit();


    }



}