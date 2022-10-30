package com.example.skyex;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
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

public class UserProfilePage extends AppCompatActivity {

    private TextView tvFName,tvLName,tvEmail,tvPassword;
    private String fName,lName,email,password;
    private Button btnLogout, btnLoyalty;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        tvFName = findViewById(R.id.fname);
        tvLName = findViewById(R.id.lname);
        tvEmail = findViewById(R.id.email);
        btnLogout = findViewById(R.id.btnLogout);
//        btnLoyalty = findViewById(R.id.idBtnViewLoyalty);
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Loyalty");
        DatabaseReference loyMem = databaseReference.child(firebaseUser.getUid().toString());
//        tvPassword = findViewById(R.id.password);
//        logout();
//        if(firebaseUser == null){
//            Toast.makeText(UserProfilePage.this,"Error! User details not available",Toast.LENGTH_LONG).show();
//        }else{
//            displayUserProfile(firebaseUser);
//        }


        btnLoyalty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                loyMem.addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        LoyaltyModel loyaltyModel = new LoyaltyModel();

                        if (!snapshot.exists()){
                            Toast.makeText(UserProfilePage.this, "Not a Loyalty member", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(UserProfilePage.this, AddLoyaltyActivity.class));
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

                            Intent intent = new Intent(UserProfilePage.this, LoyaltyViewActivity.class);
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

        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseAuth.signOut();

                Toast.makeText(UserProfilePage.this,"Logout success",Toast.LENGTH_LONG).show();
                Intent intent = new Intent(UserProfilePage.this,LoginPage.class);
//                i.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | logoutactivity.FLAG_ACTIVITY_CLEAR_TASK| logoutactivity.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                finish();
            }
        });

    }

//    private void logout() {

//    }

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