package com.example.skyex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddLoyaltyActivity extends AppCompatActivity {
    TextView tvLyltyName, tvLyltyNic, tvLyltyEmail, tvLyltyPhone;
    LoyaltyModel loyaltyModel;
    Button btnAddLoyalty;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_loyalty);

        tvLyltyName = findViewById(R.id.edtLoyaltyName);
        tvLyltyNic = findViewById(R.id.edtLoyaltyNIC);
        tvLyltyEmail = findViewById(R.id.edtLoyaltyEmail);
        tvLyltyPhone = findViewById(R.id.edtLoyaltyPhoneNo);
        btnAddLoyalty = findViewById(R.id.btnAddLoyalty);
        databaseReference = FirebaseDatabase.getInstance().getReference("Loyalty");
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        btnAddLoyalty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lName = tvLyltyName.getText().toString();
                String lNic = tvLyltyNic.getText().toString();
                String lEmail = tvLyltyEmail.getText().toString();
                String lPhoneNo = tvLyltyPhone.getText().toString();
                String lpoints = "0.00";

                loyaltyModel = new LoyaltyModel(lName, lNic, lEmail, lPhoneNo, lpoints);
                databaseReference.child(firebaseUser.getUid()).setValue(loyaltyModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        Toast.makeText(AddLoyaltyActivity.this, "Now you are a Loyalty Member", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(AddLoyaltyActivity.this, LoyaltyViewActivity.class);
                        intent.putExtra("loyaltyModel",loyaltyModel);
                        startActivity(intent);
                    }
                });
            }
        });
    }
}