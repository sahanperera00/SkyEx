package com.example.skyex;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ShoppingCartActivity extends AppCompatActivity {

    private ImageButton backProductButton;
    private RecyclerView cartRV;
    private Button next;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<CartModel> cartModelArrayList;
    private CartitemAdapter cartitemAdapter;
    private String collectionName;
    private ProductModel productModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_cart);

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
//                    case R.id.favorites:
//                        startActivity(new Intent(getApplicationContext(),FavoritesActivity.class));
//                        overridePendingTransition(0,0);
//                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(),UserDashboardActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });

        backProductButton = findViewById(R.id.idIBBackProduct);
        cartRV = findViewById(R.id.IDRVshoppingCart);
        next = findViewById(R.id.proceedCheckoutActivity);
//        temp = findViewById(R.id.temp);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Carts");
        String activity = getIntent().getExtras().getString("cartcheck");
        if (activity==null){
            collectionName = getIntent().getExtras().getString("collection");
            productModel = getIntent().getParcelableExtra("product");
        }

        cartModelArrayList = new ArrayList<>();
        cartitemAdapter = new CartitemAdapter(cartModelArrayList, this);
        cartRV.setLayoutManager(new LinearLayoutManager(this));
        cartRV.setAdapter(cartitemAdapter);

        backProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShoppingCartActivity.this, ProductActivity.class);
                if (productModel!=null&&collectionName!=null){
                    intent.putExtra("collection", collectionName);
                    intent.putExtra("product", productModel);
                    startActivity(intent);
                }else {
                    if (activity.equals("shop")){
                        startActivity(new Intent(ShoppingCartActivity.this, ShopActivity.class));
                    }else if (activity.equals("explore")){
                        startActivity(new Intent(ShoppingCartActivity.this,ExploreActivity.class));
                    }else{
                        Intent intent1 = new Intent(ShoppingCartActivity.this, ProductViewActivity.class);
                        intent1.putExtra("collection", activity);
                        startActivity(intent1);
                    }
                }
//                overridePendingTransition(0,0);
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ShoppingCartActivity.this, ShippingActivity.class));
            }
        });

//        temp.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String name = "Frock";
//                String size = "M";
//                String q = "2";
//                String price = "1500";
//                String ID = name;
//
//                CartModel cartModel = new CartModel(name, size, q, price,ID);
//
//                databaseReference.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        databaseReference.child(ID).setValue(cartModel);
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//
//                    }
//                });
//
//            }
//        });

        getAllCards();

        new ItemTouchHelper(new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
                cartitemAdapter.deleteItem(viewHolder.getAdapterPosition());
                getAllCards();

            }
        }).attachToRecyclerView(cartRV);
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