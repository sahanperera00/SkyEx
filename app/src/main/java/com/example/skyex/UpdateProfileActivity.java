package com.example.skyex;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UpdateProfileActivity extends AppCompatActivity {

    private EditText etupdatefname,etupdatelname,etupdateemail,etupdatepassword;
    private String textFname,textLname,textEmail,textPassword;
    private Button btnConfirmUpdate,btnDeleteProfile;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_update_profile);

        etupdatefname = findViewById(R.id.etUpdateFName);
        etupdatelname = findViewById(R.id.etUpdateLName);
        etupdateemail = findViewById(R.id.etUpdateEmail);
        etupdatepassword = findViewById(R.id.etUpdatePw);
        btnConfirmUpdate = findViewById(R.id.btnUpdateProfile);
        btnDeleteProfile = findViewById(R.id.btnDeleteProfiile);

        mAuth = FirebaseAuth.getInstance();
        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        //show user retrieved data
        showProfile(firebaseUser);


        btnConfirmUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateProfile(firebaseUser);
            }
        });

        //delete button
        btnDeleteProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent deleteactivity = new Intent(UpdateProfileActivity.this,DeleteProfileActivity.class);
                startActivity(deleteactivity);
            }
        });
    }

    private void showProfile(FirebaseUser firebaseUser){
        String regUID = firebaseUser.getUid();
        DatabaseReference refprofile = FirebaseDatabase.getInstance().getReference("Registered User");
        refprofile.child(regUID).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User readUserData = snapshot.getValue(User.class);
                if(readUserData != null){
                    textFname = readUserData.firstName;
                    textLname = readUserData.lastName;
                    textEmail = readUserData.email;
                    textPassword = readUserData.password;

                    etupdatefname.setText(textFname);
                    etupdatelname.setText(textLname);
                    etupdateemail.setText(textEmail);
                    etupdatepassword.setText(textPassword);
                }else{
                    Toast.makeText(UpdateProfileActivity.this,"Error!",Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UpdateProfileActivity.this,"Error!",Toast.LENGTH_LONG).show();
            }
        });



    }

    private void updateProfile(FirebaseUser firebaseUser) {
        //check if fields are empty
        if (TextUtils.isEmpty(textFname)) {
            Toast.makeText(UpdateProfileActivity.this, "Please enter first name", Toast.LENGTH_SHORT).show();
            etupdatefname.requestFocus();
        } else if (TextUtils.isEmpty(textLname)) {
            Toast.makeText(UpdateProfileActivity.this, "Please enter last name", Toast.LENGTH_SHORT).show();
            etupdatelname.requestFocus();
        } else if (TextUtils.isEmpty(textEmail)) {
            Toast.makeText(UpdateProfileActivity.this, "Please enter Email", Toast.LENGTH_SHORT).show();
            etupdateemail.requestFocus();
        } else if (!Patterns.EMAIL_ADDRESS.matcher(textEmail).matches()) {
            Toast.makeText(UpdateProfileActivity.this, "Invalid email format!", Toast.LENGTH_SHORT).show();
            etupdateemail.requestFocus();
        }else{
            String textFname = etupdatefname.getText().toString();
            String textLname = etupdatelname.getText().toString();
            String textEmail = etupdateemail.getText().toString();
            String textPassword = etupdatepassword.getText().toString();

            User writeUserData = new User(textFname,textLname,textEmail,textPassword);

            DatabaseReference refprofile = FirebaseDatabase.getInstance().getReference("Registered User");

            String userID = firebaseUser.getUid();

            refprofile.child(userID).setValue(writeUserData).addOnCompleteListener(new OnCompleteListener<Void>() {
                @Override
                public void onComplete(@NonNull Task<Void> task) {

                    if (task.isSuccessful()) {
                        //setting new values
                        Toast.makeText(UpdateProfileActivity.this, "Profile successfully updated", Toast.LENGTH_SHORT).show();
                        Intent updateActivity = new Intent(UpdateProfileActivity.this, UserProfilePage.class);
                        updateActivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | updateActivity.FLAG_ACTIVITY_CLEAR_TASK | updateActivity.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(updateActivity);
                        finish();
                    } else {
                        try{
                            throw task.getException();
                        }catch(Exception e){
                            Toast.makeText(UpdateProfileActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                }
            });
        }
    }
}