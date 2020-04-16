package com.example.chattingapp;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class RegisterActivity extends AppCompatActivity {
    private static final String TAG = "RegisterActivity";
    private FirebaseAuth firebaseAuth;

    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextName;
    private Button btnSubmit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        // Initialize Firebase Auth
        firebaseAuth = FirebaseAuth.getInstance();

        editTextEmail = (EditText)findViewById(R.id.editText_email);
        editTextPassword = (EditText)findViewById(R.id.editText_password);
        editTextName = (EditText)findViewById(R.id.editText_name);

        btnSubmit = (Button)findViewById(R.id.BtnSubmit);
        btnSubmit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strEmail = editTextEmail.getText().toString();
                String strPassword = editTextPassword.getText().toString();
                String strName = editTextName.getText().toString();
                if(strEmail.isEmpty()) {
                    Toast.makeText(RegisterActivity.this,"Email을 입력해 주십시오.",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(strPassword.isEmpty()) {
                    Toast.makeText(RegisterActivity.this,"Password를 입력해 주십시오.",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(strName.isEmpty()) {
                    Toast.makeText(RegisterActivity.this,"Name을 입력해 주십시오.",Toast.LENGTH_SHORT).show();
                    return;
                }
                createUser(strEmail, strPassword, strName);

            }
        });
    }
    private void createUser(String email, String password, String name) {
        firebaseAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // 회원가입 성공시
                            Toast.makeText(RegisterActivity.this, "회원가입 성공", Toast.LENGTH_SHORT).show();
                            finish();
                        } else {
                            // 계정이 중복된 경우
                            Toast.makeText(RegisterActivity.this, "이미 존재하는 계정입니다.", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }

}
