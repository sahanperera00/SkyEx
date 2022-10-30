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

import java.util.Objects;

public class UserDashboardActivity extends AppCompatActivity {

    private TextView tvWelcome,tvDashFName,tvDashEmail,tvViewFName,tvViewLName,tvViewEmail,tvViewPassword;
    private String fName,lName,email,password;
    private Button btnLogout,btnEditProfile,btnLoyalty;
//    private ImageView imgProfilePic;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_dashboard);

//        imgProfilePic = findViewById(R.id.imgProfileAdd);
        btnLogout = findViewById(R.id.btnLogout);
        btnEditProfile = findViewById(R.id.btnEditProfile);
        btnLoyalty = findViewById(R.id.btnLoyalty);
        tvDashFName = findViewById(R.id.tvDashFName);
//        tvDashEmail = findViewById(R.id.tvDashEmail);
        tvViewFName = findViewById(R.id.tvViewFName);
        tvViewLName = findViewById(R.id.tvViewLName);
        tvViewEmail = findViewById(R.id.tvViewEmail);
        tvViewPassword = findViewById(R.id.tvViewPassword);
//        tvWelcome = findViewById(R.id.tvDashWelcome);

        //switch to update profile activity
        btnEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent updateactivity = new Intent(UserDashboardActivity.this,UpdateProfileActivity.class);
                startActivity(updateactivity);
            }
        });

        btnLoyalty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                FirebaseAuth firebaseAuth = FirebaseAuth.getInstance();
                FirebaseUser firebaseUser = firebaseAuth.getCurrentUser();
                DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Loyalty");
                DatabaseReference loyMem = databaseReference.child(firebaseUser.getUid().toString());
                loyMem.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        LoyaltyModel loyaltyModel = new LoyaltyModel();

                        if (!snapshot.exists()){
                            Toast.makeText(UserDashboardActivity.this, "Not a Loyalty member", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(UserDashboardActivity.this, AddLoyaltyActivity.class));
                            overridePendingTransition(0, 0);
                            finish();
                        }else {
                            for (DataSnapshot dataSnapshot : snapshot.getChildren()){
                                if (Objects.equals(dataSnapshot.getKey(),"name")){
                                    loyaltyModel.setName(dataSnapshot.getValue().toString());
                                }
                                if (Objects.equals(dataSnapshot.getKey(),"nic")){
                                    loyaltyModel.setNic(dataSnapshot.getValue().toString());
                                }
                                if (Objects.equals(dataSnapshot.getKey(),"email")){
                                    loyaltyModel.setEmail(dataSnapshot.getValue().toString());
                                }
                                if (Objects.equals(dataSnapshot.getKey(),"phoneNo")){
                                    loyaltyModel.setPhoneNo(dataSnapshot.getValue().toString());
                                }
                                if (Objects.equals(dataSnapshot.getKey(),"points")){
                                    loyaltyModel.setPoints(dataSnapshot.getValue().toString());
                                    System.out.println(dataSnapshot.getValue().toString());
                                }
                            }

                            Intent intent = new Intent(UserDashboardActivity.this, LoyaltyViewActivity.class);
                            intent.putExtra("loyaltyModel", loyaltyModel);
                            startActivity(intent);
                            overridePendingTransition(0, 0);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {

                    }
                });
            }
        });



        mAuth = FirebaseAuth.getInstance();
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                mAuth.signOut();
                Toast.makeText(UserDashboardActivity.this,"Logout success",Toast.LENGTH_LONG).show();
                Intent logoutactivity = new Intent(UserDashboardActivity.this,LoginPage.class);
                logoutactivity.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | logoutactivity.FLAG_ACTIVITY_CLEAR_TASK| logoutactivity.FLAG_ACTIVITY_NEW_TASK);
                startActivity(logoutactivity);
                finish();
            }
        });

        FirebaseUser firebaseUser = mAuth.getCurrentUser();

        if(firebaseUser == null){
            Toast.makeText(UserDashboardActivity.this,"Error! User details not available",Toast.LENGTH_LONG).show();
        }else{
            displayUserProfile(firebaseUser);
        }
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
                    password = readUserData.password;

                    tvViewFName.setText(fName);
                    tvViewLName.setText(lName);
                    tvViewEmail.setText(email);
                    tvViewPassword.setText(password);

//                    tvWelcome.setText("Welcome"+tvViewFName);

                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(UserDashboardActivity.this,"Error!",Toast.LENGTH_LONG).show();
            }
        });
    }


}