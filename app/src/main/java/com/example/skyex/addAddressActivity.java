package com.example.skyex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class addAddressActivity extends AppCompatActivity {

    private EditText name, addressLine1, addressLine2, city;
    private Button addAddress;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private String ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_address);
        name = findViewById(R.id.editName);
        addressLine1 = findViewById(R.id.editAddressLine1);
        addressLine2 = findViewById(R.id.editAddressLine2);
        city = findViewById(R.id.editCity);
        addAddress = findViewById(R.id.addAddress);

        firebaseDatabase = firebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Addresses");

        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String Name = name.getText().toString();
                String AddressLine1 = addressLine1.getText().toString();
                String AddressLine2 = addressLine2.getText().toString();
                String City = city.getText().toString();

                ID = Name;

                AddressModel addressModel = new AddressModel(Name, AddressLine1, AddressLine2, City, ID);

                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        databaseReference.child(ID).setValue(addressModel);
                        Toast.makeText(addAddressActivity.this, "Address successfully added", Toast.LENGTH_LONG).show();
                        startActivity(new Intent(addAddressActivity.this, ShippingActivity.class));

                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                        Toast.makeText(addAddressActivity.this, "Error in adding the address: "+ databaseError.toString(), Toast.LENGTH_SHORT).show();

                    }
                });
            }
        });


    }
}