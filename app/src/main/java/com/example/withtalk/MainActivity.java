package com.example.withtalk;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

//    private FirebaseAuth firebaseAuth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        firebaseAuth = FirebaseAuth.getInstance();
//        firebaseAuth.addAuthStateListener(authStateListener);
    }
//    FirebaseAuth.AuthStateListener authStateListener = new FirebaseAuth.AuthStateListener() {
//        @Override
//        public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
//            FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
//
//            if (firebaseUser == null) {
////                Intent in = new Intent(MainActivity.this, AuthActivity.class);
////                startActivity(in);
////                finish();
//            }
//            if(firebaseUser != null) {
////                Intent in = new Intent(MainActivity.this, HomeActivity.class);
////                startActivity(in);
////                finish();
//            }
//
//        }
//    };
}
