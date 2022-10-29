package com.example.skyex;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class UserProfilePage extends AppCompatActivity {

    private TextView tvFName,tvLName,tvEmail,tvPassword;
    private String fName,lName,email,password;
    private Button btnLogout;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile_page);

        tvFName = findViewById(R.id.fname);
        tvLName = findViewById(R.id.lname);
        tvEmail = findViewById(R.id.email);
//        tvPassword = findViewById(R.id.password);
        btnLogout = findViewById(R.id.btnLogout);

        mAuth = FirebaseAuth.getInstance();
        logout();

        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        if(firebaseUser == null){
            Toast.makeText(UserProfilePage.this,"Error! User details not available",Toast.LENGTH_LONG).show();
        }else{
            displayUserProfile(firebaseUser);
        }

    }

    private void logout() {
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Toast.makeText(UserProfilePage.this,"Logout success",Toast.LENGTH_LONG).show();
                Intent logoutactivity = new Intent(UserProfilePage.this,LandingPage.class);
                logoutactivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | logoutactivity.FLAG_ACTIVITY_CLEAR_TASK| logoutactivity.FLAG_ACTIVITY_NEW_TASK);
                startActivity(logoutactivity);
                finish();
            }
        });
    }

    private void displayUserProfile(FirebaseUser firebaseUser){
        String id = firebaseUser.getUid();

        DatabaseReference refUserProfile = FirebaseDatabase.getInstance().getReference("Registered User");
        refUserProfile.child(id).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User readUserData = snapshot.getValue(User.class);
                if(readUserData !=null){
                    fName = readUserData.firstName;
                    lName = readUserData.lastName;
                    email = firebaseUser.getEmail();

                    tvFName.setText(fName);
                    tvLName.setText(lName);
                    tvEmail.setText(email);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserProfilePage.this,"Error!",Toast.LENGTH_LONG).show();
            }
        });
    }
}