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

import java.util.HashMap;
import java.util.Map;

public class EditLoyaltyActivity extends AppCompatActivity {
    TextView tvLyltyName, tvLyltyNic, tvLyltyEmail, tvLyltyPhone, tvLyltyPoints;
    LoyaltyModel loyaltyModel;
    Button btnUpdateLoyalty;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_loyalty);

        tvLyltyName = findViewById(R.id.edtLoyaltyName);
        tvLyltyNic = findViewById(R.id.edtLoyaltyNIC);
        tvLyltyEmail = findViewById(R.id.edtLoyaltyEmail);
        tvLyltyPhone = findViewById(R.id.edtLoyaltyPhoneNo);
        btnUpdateLoyalty = findViewById(R.id.btnUpdateLoyalty);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Loyalty").child(firebaseUser.getUid().toString());
        loyaltyModel = getIntent().getParcelableExtra("loyaltyModel");

        if (loyaltyModel!=null) {
            tvLyltyName.setText(loyaltyModel.getName());
            tvLyltyNic.setText(loyaltyModel.getNic());
            tvLyltyEmail.setText(loyaltyModel.getEmail());
            tvLyltyPhone.setText(loyaltyModel.getPhoneNo());
        }

        btnUpdateLoyalty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String lName = tvLyltyName.getText().toString();
                String lNic = tvLyltyNic.getText().toString();
                String lEmail = tvLyltyEmail.getText().toString();
                String lPhoneNo = tvLyltyPhone.getText().toString();

                Map<String,Object> map = new HashMap<>();
                map.put("name",lName);
                map.put("nic",lNic);
                map.put("email",lEmail);
                map.put("phoneNo", lPhoneNo);

                databaseReference.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            loyaltyModel.setName(lName);
                            loyaltyModel.setEmail(lEmail);
                            loyaltyModel.setNic(lNic);
                            loyaltyModel.setPhoneNo(lPhoneNo);

                            Toast.makeText(EditLoyaltyActivity.this, "Loyalty details updated", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(EditLoyaltyActivity.this, LoyaltyViewActivity.class);
                            intent.putExtra("loyaltyModel", loyaltyModel);
                            startActivity(intent);
                        }
                    }
                });
            }
        });
    }
}