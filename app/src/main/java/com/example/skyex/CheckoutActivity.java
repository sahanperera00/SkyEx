package com.example.skyex;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;

public class CheckoutActivity extends AppCompatActivity {

    private RecyclerView checkout_cartRV;
    private Button finish;
    private TextView subTotal, shipping_cost,grand_total;
    private Spinner spinner;
    private AlertDialog.Builder builder;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<CartModel> cartModelArrayList;
    private CartitemAdapter cartitemAdapter;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);
        checkout_cartRV = findViewById(R.id.checkoutRV);
        spinner = findViewById(R.id.spinShipping);
        finish = findViewById(R.id.button);
        subTotal = findViewById(R.id.textView6);
        shipping_cost = findViewById(R.id.textView8);
        grand_total = findViewById(R.id.textView10);
        builder = new AlertDialog.Builder(this);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Carts");

        cartModelArrayList = new ArrayList<>();

        cartitemAdapter = new CartitemAdapter(cartModelArrayList, this);


        checkout_cartRV.setLayoutManager(new LinearLayoutManager(this));
        checkout_cartRV.setAdapter(cartitemAdapter);

        finish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setTitle("Payment Successful!")
                        .setMessage("Thank You for shopping with us. We will be processing your order soon")
                        .setCancelable(false)
                        .setPositiveButton("Back to Shopping", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                startActivity(new Intent(CheckoutActivity.this, ShoppingCartActivity.class));
                            }
                        }).show();
            }
        });


        getAllCards();


        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String shipping_method = adapterView.getItemAtPosition(i).toString();

                String s1 = "500";
                String s2 = "1000";
                String s3 = "2000";
                String s4 = "3000";

                int Grand_Total = 0;
                String sub_total = String.valueOf(subTotal.getText());


                if(i == 0){
                    shipping_cost.setText(s1);
                    Grand_Total = Integer.parseInt(s1) + Integer.parseInt(sub_total);
                }
                else if (i == 1){
                    shipping_cost.setText(s2);
                    Grand_Total = Integer.parseInt(s2) + Integer.parseInt(sub_total);
                }
                else if (i == 2){
                    shipping_cost.setText(s3);
                    Grand_Total = Integer.parseInt(s3) + Integer.parseInt(sub_total);
                }
                else if (i == 3){
                    shipping_cost.setText(s4);
                    Grand_Total = Integer.parseInt(s4) + Integer.parseInt(sub_total);
                }

                grand_total.setText("Rs. " + String.valueOf(Grand_Total));
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }


        });


        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                int sum = 0;

                for (DataSnapshot ds: dataSnapshot.getChildren()){

                    Map<String, Object> map = (Map<String, Object>) ds.getValue();
                    Object price = map.get("price");
                    Object quantity = map.get("quantity");
                    int pValue = Integer.parseInt(String.valueOf(price));
                    int qValue = Integer.parseInt(String.valueOf(quantity));

                    int total = pValue*qValue;
                    sum = sum + total;

                    subTotal.setText(String.valueOf(sum));

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });

//        String st = String.valueOf(subTotal.getText());

    }

    private void getAllCards(){

        cartModelArrayList.clear();

        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                cartModelArrayList.add(dataSnapshot.getValue(CartModel.class));
                cartitemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                cartitemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                cartitemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                cartitemAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}