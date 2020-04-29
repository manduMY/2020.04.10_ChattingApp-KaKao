package com.example.chattingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class HomeActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    FriendListFragment menuFriendListFragment;
    ChatListFragment menuChatListFragment;
    MoreListFragment menuMoreListFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Fragment 생성
        menuFriendListFragment = new FriendListFragment();
        menuChatListFragment = new ChatListFragment();
        menuMoreListFragment = new MoreListFragment();

        //제일 처음 띄워줄 뷰를 세팅해줍니다. commit();까지 해줘야 합니다.
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,menuFriendListFragment).commitAllowingStateLoss();

        //bottomnavigationview의 아이콘을 선택 했을때 원하는 프래그먼트가 띄워질 수 있도록 리스너를 추가합니다.
        bottomNavigationView = (BottomNavigationView)findViewById(R.id.bottom_navigation);
        bottomNavigationView.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        menuFriendListFragment = new FriendListFragment();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, menuFriendListFragment).commit();


    }
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_friendList:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,menuFriendListFragment).commitAllowingStateLoss();
                    return true;
                case R.id.navigation_chatList:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,menuChatListFragment).commitAllowingStateLoss();
                    return true;
                case R.id.navigation_moreList:
                    getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,menuMoreListFragment).commitAllowingStateLoss();
                    return true;
                default: return false;
            }
        }
    };

}