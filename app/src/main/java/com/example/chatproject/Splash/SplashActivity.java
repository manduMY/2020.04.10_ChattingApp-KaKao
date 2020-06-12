package com.example.chatproject.Splash;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

import com.example.chatproject.Login.SiginInActivity;
import com.example.chatproject.R;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Handler handler = new Handler();
        handler.postDelayed(new splashHandler(), 2000); // 2초 뒤에 handler 실행 2000ms = 2초
    }

    private class splashHandler implements Runnable{
        @Override
        public void run() {
            startActivity(new Intent(getApplication(), SiginInActivity.class));
            SplashActivity.this.finish(); // 로딩 페이지 Activity Stack에서 제거
        }
    }

    @Override
    public void onBackPressed() {
        //초반 플래시 화면에서 넘어갈때 뒤로가기 버튼 못누르게함
    }
}