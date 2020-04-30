package com.example.chattingapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.io.IOException;

public class UserProfileActivity extends AppCompatActivity {
    private static int PICK_IMAGE = 123;
    Uri imagePath;
    private DatabaseReference databaseReference;
    private TextView profileNickNameTextView, profilePhonenoTextView;
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private ImageView profileImageButton;
    private FirebaseStorage firebaseStorage;
    private TextView textViewemailname;
    private EditText editTextName;
    private StorageReference storageReference;
    private static final String TAG = "UserProfileActivity";

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        StorageReference storageReference = firebaseStorage.getReference();
        if (requestCode == PICK_IMAGE && resultCode == RESULT_OK && data.getData() != null) {
            imagePath = data.getData();
            Log.d(TAG, storageReference.child(firebaseAuth.getUid()).child("Images").child("Profile Pic").getDownloadUrl().toString());
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), imagePath);
                profileImageButton.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);
        databaseReference = FirebaseDatabase.getInstance().getReference();
        editTextName = (EditText)findViewById(R.id.et_username);
        profileImageButton = findViewById(R.id.profile_picture_imageBtn);
        profileNickNameTextView = findViewById(R.id.profile_nickname_textView);
        profilePhonenoTextView = findViewById(R.id.profile_phoneno_textView);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseStorage = FirebaseStorage.getInstance();
        DatabaseReference databaseReference = firebaseDatabase.getReference(firebaseAuth.getUid());
        storageReference = firebaseStorage.getReference();
        // Get the image stored on Firebase via "User id/Images/Profile Pic.jpg".
        storageReference.child(firebaseAuth.getUid()).child("Images").child("Profile Pic").getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
            @Override
            public void onSuccess(Uri uri) {
                // Using "Picasso" (http://square.github.io/picasso/) after adding the dependency in the Gradle.
                // ".fit().centerInside()" fits the entire image into the specified area.
                // Finally, add "READ" and "WRITE" external storage permissions in the Manifest.
                Picasso.get().load(uri).fit().centerInside().into(profileImageButton);
            }
        });

        profileImageButton.setOnClickListener(new Button.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent profileIntent = new Intent();
                profileIntent.setType("image/*");
                profileIntent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(profileIntent, "Select Image."), PICK_IMAGE);
            }
        });

        if (firebaseAuth.getCurrentUser() == null){
            finish();
            startActivity(new Intent(getApplicationContext(),SignInActivity.class));
        }
        final FirebaseUser user=firebaseAuth.getCurrentUser();
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange( DataSnapshot dataSnapshot) {
                Userinformation userProfile = dataSnapshot.getValue(Userinformation.class);
                profileNickNameTextView.setText(userProfile.getNickname());
                profilePhonenoTextView.setText(userProfile.getPhonenum());
                textViewemailname=(TextView)findViewById(R.id.textViewEmailAdress);
                textViewemailname.setText(user.getEmail());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                Toast.makeText(UserProfileActivity.this, databaseError.getCode(), Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void buttonClickedEditName(View view) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.layout_custom_dialog_edit_name, null);
        final EditText etUsername = alertLayout.findViewById(R.id.et_username);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Name Edit");
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nickname = etUsername.getText().toString();
                String phoneno =  profilePhonenoTextView.getText().toString();
                Userinformation userinformation = new Userinformation(nickname, phoneno);
                FirebaseUser user = firebaseAuth.getCurrentUser();
                databaseReference.child(user.getUid()).setValue(userinformation);
                databaseReference.child(user.getUid()).setValue(userinformation);
                etUsername.onEditorAction(EditorInfo.IME_ACTION_DONE);
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
    }

    public void buttonClickedEditPhoneNo(View view) {
        LayoutInflater inflater = getLayoutInflater();
        View alertLayout = inflater.inflate(R.layout.layout_custom_dialog_edit_phoneno, null);
        final EditText etUserPhoneno = alertLayout.findViewById(R.id.et_phoneno);
        AlertDialog.Builder alert = new AlertDialog.Builder(this);
        alert.setTitle("Phone No Edit");
        // this is set the view from XML inside AlertDialog
        alert.setView(alertLayout);
        // disallow cancel of AlertDialog on click of back button and outside touch
        alert.setCancelable(false);
        alert.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                String nickname = profileNickNameTextView.getText().toString();
                String phoneno =  etUserPhoneno.getText().toString();
                Userinformation userinformation = new Userinformation(nickname, phoneno);
                FirebaseUser user = firebaseAuth.getCurrentUser();
                databaseReference.child(user.getUid()).setValue(userinformation);
                databaseReference.child(user.getUid()).setValue(userinformation);
                etUserPhoneno.onEditorAction(EditorInfo.IME_ACTION_DONE);
            }
        });
        AlertDialog dialog = alert.create();
        dialog.show();
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
                Toast.makeText(UserProfileActivity.this, "Error: Uploading profile picture", Toast.LENGTH_SHORT).show();
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                Toast.makeText(UserProfileActivity.this, "Profile picture uploaded", Toast.LENGTH_SHORT).show();
            }
        });
    }
    public void navigateSave(View v){
        if (imagePath == null) {

            Drawable drawable = this.getResources().getDrawable(R.drawable.user_profile);
            Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.user_profile);
            // openSelectProfilePictureDialog();
            // sendUserData();
            finish();
        }
        else {
            sendUserData();
            finish();
        }
    }
}
