package com.example.skyex;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class ShippingActivity extends AppCompatActivity implements AddressAdapter.CardClickInterface, AddressAdapter.ButtonClickInterface {

    private RecyclerView addressRV;
    private Button addAddress, next;
    private FirebaseDatabase firebaseDatabase;
    private TextView name, adl1, adl2, city;
    private DatabaseReference databaseReference;
    private ArrayList<AddressModel> addressModelArrayList;
    private AddressAdapter addressAdapter;
    private LinearLayoutManager linearLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shipping);
        addressRV = findViewById(R.id.RVaddress);
        addAddress = findViewById(R.id.button2);
        next  = findViewById(R.id.btnUpdateProfile);

        name = findViewById(R.id.name);
        adl1 = findViewById(R.id.textAddressLine1);
        adl2 = findViewById(R.id.textAddressLine2);
        city = findViewById(R.id.textCity);



        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Addresses");
        addressModelArrayList = new ArrayList<>();
        addressAdapter = new AddressAdapter(addressModelArrayList, this, this,this);

        linearLayoutManager = new LinearLayoutManager(ShippingActivity.this,linearLayoutManager.HORIZONTAL,false);

        addressRV.setLayoutManager(linearLayoutManager);
        addressRV.setAdapter(addressAdapter);

        addAddress.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShippingActivity.this,addAddressActivity.class));
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShippingActivity.this, PaymentActivity.class));
            }
        });



        getAllAddresses();


    }

    private void getAllAddresses() {
        addressModelArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                addressModelArrayList.add(dataSnapshot.getValue(AddressModel.class));
                addressAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                addressAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                addressAdapter.notifyDataSetChanged();

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                addressAdapter.notifyDataSetChanged();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {
                addressAdapter.notifyDataSetChanged();

            }
        });
    }

    @Override
    public void onCardClick(int position) {
        displaySelected(addressModelArrayList.get(position));
    }

    private void displaySelected(AddressModel addressModel) {
        name.setText(addressModel.getName());
        adl1.setText(addressModel.getAddressLine1());
        adl2.setText(addressModel.getAddressLine2());
        city.setText(addressModel.getCity());
    }

    public void onButtonClick(int position) {
        displayEditActivity(addressModelArrayList.get(position));
    }

    public void displayEditActivity(AddressModel addressModel) {
        Intent i = new Intent(ShippingActivity.this, EditAddressActivity.class);
        i.putExtra("address", addressModel);
        startActivity(i);

    }

    public void sendData(AddressModel addressModel){
        String name = addressModel.getName();
        String adl1 = addressModel.getAddressLine1();
        String adl2 = addressModel.getAddressLine2();
        String city = addressModel.getCity();

        Intent i = new Intent(ShippingActivity.this, CheckoutActivity.class);
        i.putExtra("name", name);
        i.putExtra("adl1", adl1);
        i.putExtra("adl2", adl2);
        i.putExtra("city", city);
    }


}