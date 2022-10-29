package com.example.skyex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class EditAddressActivity extends AppCompatActivity {

    private TextInputEditText name, addressLine1, addressLine2, city;
    private Button editAddress, deleteAddress;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    AddressModel addressModel;

    private String ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);

        firebaseDatabase = firebaseDatabase.getInstance();
        name = findViewById(R.id.editName);
        addressLine1 = findViewById(R.id.editAddressLine1);
        addressLine2 = findViewById(R.id.editAddressLine2);
        city = findViewById(R.id.editCity);
        editAddress = findViewById(R.id.editAddressbtn);
        deleteAddress = findViewById(R.id.deleteAddressbtn);

        addressModel = getIntent().getParcelableExtra("address");

        if (addressModel != null) {

            name.setText(addressModel.getName());
            addressLine1.setText(addressModel.getAddressLine1());
            addressLine2.setText(addressModel.getAddressLine2());
            city.setText(addressModel.getCity());

            ID = addressModel.getID();

            databaseReference = firebaseDatabase.getReference("Addresses").child(ID);

            editAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    String Name = name.getText().toString();
                    String AddressLine1 = addressLine1.getText().toString();
                    String AddressLine2 = addressLine2.getText().toString();
                    String City = city.getText().toString();

                    ID = Name;

                    Map<String, Object> map = new HashMap<>();
                    map.put("name", Name);
                    map.put("addressLine1", AddressLine1);
                    map.put("addressLine2", AddressLine2);
                    map.put("city", City);
                    map.put("ID", ID);

                    databaseReference.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isComplete()) {
                                Toast.makeText(EditAddressActivity.this, "Address successfully updated", Toast.LENGTH_LONG).show();
                                startActivity(new Intent(EditAddressActivity.this, ShippingActivity.class));
                                finish();
                            } else {
                                Toast.makeText(EditAddressActivity.this, "Error in adding the address: ", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

//                    databaseReference.addValueEventListener(new ValueEventListener() {
//                        @Override
//                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                            databaseReference.updateChildren(map);
//                            Toast.makeText(EditAddressActivity.this, "Address successfully updated", Toast.LENGTH_LONG).show();
//                            startActivity(new Intent(EditAddressActivity.this, ShippingActivity.class));
//                        }
//
//                        @Override
//                        public void onCancelled(@NonNull DatabaseError databaseError) {
//                            Toast.makeText(EditAddressActivity.this, "Error in adding the address: "+ databaseError.toString(), Toast.LENGTH_SHORT).show();
//
//                        }
//                    });
                }
            });

            deleteAddress.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    deleteAddress();
                }
            });


        }

    }

    private void deleteAddress() {

        databaseReference.removeValue();
        Toast.makeText(EditAddressActivity.this, "Card successfully deleted", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditAddressActivity.this, ShippingActivity.class));


    }
}