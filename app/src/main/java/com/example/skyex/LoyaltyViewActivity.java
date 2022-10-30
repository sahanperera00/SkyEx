package com.example.skyex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class LoyaltyViewActivity extends AppCompatActivity {
    TextView tvLyltyName, tvLyltyNic, tvLyltyEmail, tvLyltyPhone, tvLyltyPoints;
    Button btnUpdateLoyalty, btnDeleteLoyalty;
    ImageView imageLoyalty;
    DatabaseReference databaseReference;
    FirebaseUser firebaseUser;
    LoyaltyModel loyaltyModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loyalty_view);


        tvLyltyName = findViewById(R.id.idTVLoyaltyName);
        tvLyltyNic = findViewById(R.id.idTVLoyaltyNic);
        tvLyltyEmail = findViewById(R.id.idTVLoyaltyEmail);
        tvLyltyPhone = findViewById(R.id.idTVLoyaltyPNo);
        tvLyltyPoints = findViewById(R.id.idTVLoyaltyPoints);
        btnUpdateLoyalty = findViewById(R.id.idBtnUpdateLoyalty);
        btnDeleteLoyalty = findViewById(R.id.idBtnDeleteLoyalty);
        imageLoyalty = findViewById(R.id.idIVLoyaltyStar);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference("Loyalty").child(firebaseUser.getUid().toString());
        loyaltyModel = getIntent().getParcelableExtra("loyaltyModel");

        if (loyaltyModel.getPoints()!=null){
            tvLyltyName.setText(loyaltyModel.getName());
            tvLyltyEmail.setText(loyaltyModel.getEmail());
            tvLyltyNic.setText(loyaltyModel.getNic());
            tvLyltyPhone.setText(loyaltyModel.getPhoneNo());
            tvLyltyPoints.setText(String.valueOf(loyaltyModel.getPoints()));
        }

        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/skyex-423ec.appspot.com/o/images%2FstarLoyalty.png?alt=media&token=a492f468-6ec0-4b5b-b844-25af7fc93ae9").into(imageLoyalty);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.shop:
                        startActivity(new Intent(getApplicationContext(),ShopActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.explore:
                        startActivity(new Intent(getApplicationContext(),ExploreActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.favorites:
                        startActivity(new Intent(getApplicationContext(),FavoritesActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        btnUpdateLoyalty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoyaltyViewActivity.this, EditLoyaltyActivity.class);
                intent.putExtra("loyaltyModel", loyaltyModel);
                startActivity(intent);
                overridePendingTransition(0, 0);
            }
        });

        btnDeleteLoyalty.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(LoyaltyViewActivity.this, "Membership Revoked", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(LoyaltyViewActivity.this, UserProfilePage.class));
                            overridePendingTransition(0, 0);
                        }
                    }
                });
            }
        });
    }
}