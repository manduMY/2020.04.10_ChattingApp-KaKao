package com.example.chattingapp;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

public class EditUserProfileActivity extends AppCompatActivity implements View.OnClickListener{
    private static int PICK_IMAGE = 123;
    private ImageButton profileImageButton;
    Uri imagePath;
    private FirebaseAuth firebaseAuth;
    private TextView textViewemailname;
    private DatabaseReference databaseReference;
    private EditText editTextNickName;
    private EditText editTextPhoneNum;
    private Button profileSaveButton;
    private FirebaseStorage firebaseStorage;
    private StorageReference storageReference;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_user_profile);

        firebaseAuth=FirebaseAuth.getInstance();
        if (firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(getApplicationContext(),SignInActivity.class));
        }
        databaseReference = FirebaseDatabase.getInstance().getReference();
        editTextNickName = (EditText)findViewById(R.id.EditText_NickName);
        editTextPhoneNum = (EditText)findViewById(R.id.EditText_PhoneNumber);
        profileSaveButton=(Button)findViewById(R.id.btnSaveButton);
        FirebaseUser user=firebaseAuth.getCurrentUser();
        profileSaveButton.setOnClickListener(this);
        textViewemailname=(TextView)findViewById(R.id.YourEmail);
        textViewemailname.setText(user.getEmail());
        profileImageButton = findViewById(R.id.profile_picture_imageBtn);
        firebaseStorage = FirebaseStorage.getInstance();
        storageReference = firebaseStorage.getReference();


        profileImageButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent();
                profileIntent.setType("image/*");
                profileIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(profileIntent, "Select Image."), PICK_IMAGE);
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null) {
            imagePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                profileImageButton.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
    private void userInformation(){
        String nickname = editTextNickName.getText().toString().trim();
        String phonenum = editTextPhoneNum.getText().toString().trim();
        Userinformation userinformation = new Userinformation(nickname, phonenum);
        FirebaseUser user = firebaseAuth.getCurrentUser();
        databaseReference.child(user.getUid()).setValue(userinformation);
        Toast.makeText(getApplicationContext(),"User information updated",Toast.LENGTH_LONG).show();
    }
    private void sendUserData() {
        FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
        // Get "User UID" from Firebase > Authentification > Users.
        databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
        StorageReference imageReference = storageReference.child(firebaseAuth.getUid()).child("Images").child("Profile Pic"); //User id/Images/Profile Pic.jpg
        UploadTask uploadTask = imageReference.putFile(imagePath);
        uploadTask.addOnFailureListener(new OnFailureListener() {

            @Override
            public void onFailure(@NonNull Exception e) {
                Toast.makeText(EditUserProfileActivity.this, "Error: Uploading profile picture", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(EditUserProfileActivity.this, "Profile picture uploaded", Toast.LENGTH_SHORT).show();
            }
        });
    }
    @Override
    public void onClick(View v) {
        if(v == profileSaveButton) {
            if (imagePath == null) {

                Drawable drawable = this.getResources().getDrawable(R.drawable.user_profile);
                Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.user_profile);
                // openSelectProfilePictureDialog();
                userInformation();
                // sendUserData();
                finish();
                startActivity(new Intent(EditUserProfileActivity.this, HomeActivity.class));
            }
            else {
                userInformation();
                sendUserData();
                finish();
                startActivity(new Intent(EditUserProfileActivity.this, HomeActivity.class));
            }
        }
    }
}
