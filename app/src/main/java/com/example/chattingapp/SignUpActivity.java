package com.example.chattingapp;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignUpActivity extends AppCompatActivity {

    private static final String TAG = "SignUpActivity";

    private EditText editTextEmail;
    private EditText editTextPassword;
    private EditText editTextName;
    private Button btnSignUp;

    private FirebaseAuth mAuth;

    @Override
    public void onStart() {
        super.onStart();
        // Check if user is signed in (non-null) and update UI accordingly.
        FirebaseUser currentUser = mAuth.getCurrentUser();
//        updateUI(currentUser);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        // Initialize Firebase Auth
        mAuth = FirebaseAuth.getInstance();

        editTextEmail = (EditText)findViewById(R.id.SignUp_editText_email);
        editTextPassword = (EditText)findViewById(R.id.SignUp_editText_password);
        editTextName = (EditText)findViewById(R.id.SignUp_editText_name);

        btnSignUp = (Button)findViewById(R.id.SignUp_BtnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strEmail = editTextEmail.getText().toString();
                final String strPassword = editTextPassword.getText().toString();
                String strName = editTextName.getText().toString();
                if(strEmail.isEmpty()) {
                    Toast.makeText(SignUpActivity.this,"Sign Up: Email을 입력해 주십시오.",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(strPassword.isEmpty()) {
                    Toast.makeText(SignUpActivity.this,"Sign Up: Password를 입력해 주십시오.",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(strName.isEmpty()) {
                    Toast.makeText(SignUpActivity.this,"Sign Up: Name을 입력해 주십시오.",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(strPassword.length() < 8) {
                    Toast.makeText(SignUpActivity.this,"Sign Up: 비밀번호를 8자 이상 입력해 주십시오.",Toast.LENGTH_SHORT).show();
                    return;
                }
                mAuth.createUserWithEmailAndPassword(strEmail, strPassword)
                        .addOnCompleteListener(SignUpActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "createUserWithEmail:success");
                                    finish();
//                                    FirebaseUser user = mAuth.getCurrentUser();
//                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "createUserWithEmail:failure", task.getException());
                                    Toast.makeText(SignUpActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
//                                    updateUI(null);
                                }

                                // ...
                            }
                        });
            }
        });
    }
}
