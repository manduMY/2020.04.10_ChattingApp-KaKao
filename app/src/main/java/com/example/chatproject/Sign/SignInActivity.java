package com.example.chatproject.Sign;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.chatproject.R;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

public class SignInActivity extends AppCompatActivity {
    private EditText editTextEmail, editTextPassword;
    private SignInButton googleSignIn;
    private GoogleSignInClient mGoogleSignInClient;
    private static final String TAG = "SignInActivity";
    private FirebaseAuth firebaseAuth;
    private Button btnSignIn, btnSignUp;
    private int RC_SIGN_IN = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);

        editTextEmail = (EditText) findViewById(R.id.SignIn_EditEmail);
        editTextPassword = (EditText) findViewById(R.id.SignIn_EditPassword);

        googleSignIn = (SignInButton) findViewById(R.id.Btn_Google_SignIn);
        firebaseAuth = FirebaseAuth.getInstance();
        btnSignIn = (Button)findViewById(R.id.SignIn_BtnSignIn);


        // Configure Google Sign In
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
//                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);

        googleSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                signIn();
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String email = editTextEmail.getText().toString();
                final String password = editTextPassword.getText().toString();
                if(email.isEmpty()) {
                    Toast.makeText(SignInActivity.this,"Email을 입력해 주십시오.",Toast.LENGTH_SHORT).show();
                    return;
                }
                if(password.isEmpty()) {
                    Toast.makeText(SignInActivity.this,"Password를 입력해 주십시오." + password,Toast.LENGTH_SHORT).show();
                    return;
                }

                firebaseAuth.signInWithEmailAndPassword(email, password)
                        .addOnCompleteListener(SignInActivity.this, new OnCompleteListener<AuthResult>() {
                            @Override
                            public void onComplete(@NonNull Task<AuthResult> task) {
                                if (task.isSuccessful()) {
                                    // Sign in success, update UI with the signed-in user's information
                                    Log.d(TAG, "signInWithEmail:success");
                                    Toast.makeText(SignInActivity.this, "환영합니다", Toast.LENGTH_SHORT).show();
                                    FirebaseUser user = firebaseAuth.getCurrentUser();
//                                    Intent in = new Intent(SignInActivity.this, EditUserProfileActivity.class);
//                                    startActivity(in);
//                                    updateUI(user);
                                } else {
                                    // If sign in fails, display a message to the user.
                                    Log.w(TAG, "signInWithEmail:failure", task.getException());
                                    if (password.length() < 8)
                                    {
                                        Toast.makeText(SignInActivity.this, "비밀번호를 8자 이상 입력 해주십시오.", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(SignInActivity.this, "인증 실패", Toast.LENGTH_SHORT).show();
                                    }
                                    updateUI(null);
                                }
                            }
                        });
            }
        });
        btnSignUp = (Button)findViewById(R.id.SignIn_BtnSignUp);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(SignInActivity.this, "회원가입 창 이동", Toast.LENGTH_SHORT).show();
                Intent in = new Intent(getApplication(),SignUpActivity.class);
                startActivity(in);
                SignInActivity.this.finish();
            }
        });
    }

    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            handleSignInResult(task);
        }
    }

    private void handleSignInResult(Task<GoogleSignInAccount> completedTask) {
        try{
            GoogleSignInAccount acc = completedTask.getResult(ApiException.class);
            Toast.makeText(SignInActivity.this, "Signed In Successfully", Toast.LENGTH_SHORT).show();
            firebaseAuthWithGoogle(acc);
        }catch(ApiException e) {
            Toast.makeText(SignInActivity.this, "Signed In Failed", Toast.LENGTH_SHORT).show();
            firebaseAuthWithGoogle(null);
        }
    }
    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d(TAG, "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        firebaseAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = firebaseAuth.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(SignInActivity.this, "Failed", Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }

                        // ...
                    }
                });
    }

    private void updateUI(FirebaseUser fUser) {
        GoogleSignInAccount account = GoogleSignIn.getLastSignedInAccount(getApplicationContext());
        if (account != null) {
            String personName = account.getDisplayName();
            String personGivenName = account.getGivenName();
            String personFamilyName = account.getFamilyName();
            String personEmail = account.getEmail();
            String personId = account.getId();
            Uri personPhoto = account.getPhotoUrl();



            Log.d(TAG, "Debug personName: " + personName);
            Log.d(TAG, "Debug personGivenName: " + personGivenName);
            Log.d(TAG, "Debug personFamilyName: " + personFamilyName);
            Log.d(TAG, "Debug personEmail: " + personEmail);
            Log.d(TAG, "Debug personId: " + personId);
            Log.d(TAG, "Debug personPhoto: " + personPhoto);
            Toast.makeText(SignInActivity.this, personName + personEmail, Toast.LENGTH_SHORT).show();
        }
    }
}