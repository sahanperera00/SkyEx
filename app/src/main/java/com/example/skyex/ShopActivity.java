package com.example.skyex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.Objects;

public class ShopActivity extends AppCompatActivity {
    TextView seemore1, seemore2, seemore3;
    ImageView hero, logo;
    private ImageButton cartButton;

    private RecyclerView recyclerView;
    private ProductHRVAdapter productHRVAdapter;
    private ArrayList<ProductModel> productModelArrayList;

    private RecyclerView recyclerView2;
    private ProductHRVAdapter productHRVAdapter2;
    private ArrayList<ProductModel> productModelArrayList2;

    private RecyclerView recyclerView3;
    private ProductHRVAdapter productHRVAdapter3;
    private ArrayList<ProductModel> productModelArrayList3;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.shop);

        seemore1 = findViewById(R.id.idTVSeeMoreTopic1);
        seemore2 = findViewById(R.id.idTVSeeMoreTopic2);
        seemore3 = findViewById(R.id.idTVSeeMoreTopic3);
        cartButton = findViewById(R.id.idIBCart);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Products");
        productModelArrayList = new ArrayList<>();
        productModelArrayList2 = new ArrayList<>();
        productModelArrayList3 = new ArrayList<>();
        hero = findViewById(R.id.idHomeImage);
        logo = findViewById(R.id.idIVLOGO);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/skyex-423ec.appspot.com/o/images%2Flogo.jpg?alt=media&token=fa65f58d-e2b0-4b66-a311-b0e6f548707c").into(logo);
        Picasso.get().load("https://firebasestorage.googleapis.com/v0/b/skyex-423ec.appspot.com/o/images%2Fhero1.jpg?alt=media&token=e4bf8d0d-9bd5-4951-89bb-162c41b8b4eb").into(hero);

        recyclerView = findViewById(R.id.idRVTopic1);
        productHRVAdapter = new ProductHRVAdapter(this,productModelArrayList);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                int count1 = 0;
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    if (dataSnapshot.exists()){
                        for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                            if (Objects.equals(dataSnapshot1.getKey(),"productCollection")&&Objects.equals(dataSnapshot1.getValue(), "Versatile")){
                                ProductModel productModel = dataSnapshot.getValue(ProductModel.class);
                                productModelArrayList.add(productModel);
                            }
                        }
                    }
//                    if (count1 == 6)
//                        break;
//                    else
//                        count1++;
                };
                productHRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        recyclerView.setAdapter(productHRVAdapter);

        recyclerView2 = findViewById(R.id.idRVTopic2);
        productHRVAdapter2 = new ProductHRVAdapter(this,productModelArrayList2);
        recyclerView2.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                int count2 = 0;
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    if (dataSnapshot.exists()){
                        for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                            if (Objects.equals(dataSnapshot1.getKey(),"productCollection")&&Objects.equals(dataSnapshot1.getValue(), "Crewneck")){
                                ProductModel productModel = dataSnapshot.getValue(ProductModel.class);
                                productModelArrayList2.add(productModel);
                            }
                        }
                    }
//                    if (count2 == 5)
//                        break;
//                    else
//                        count2++;
                };
                productHRVAdapter2.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        recyclerView2.setAdapter(productHRVAdapter2);

        recyclerView3 = findViewById(R.id.idRVTopic3);
        productHRVAdapter3 = new ProductHRVAdapter(this,productModelArrayList3);
        recyclerView3.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                int count3 = 0;
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    if (dataSnapshot.exists()){
                        for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                            if (Objects.equals(dataSnapshot1.getKey(),"productCollection")&&Objects.equals(dataSnapshot1.getValue(), "Oversize")){
                                ProductModel productModel = dataSnapshot.getValue(ProductModel.class);
                                productModelArrayList3.add(productModel);
                            }
                        }
                    }
                    if (count3 == 5)
                        break;
                    else
                        count3++;
                };
                productHRVAdapter3.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        recyclerView3.setAdapter(productHRVAdapter3);

        seemore1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShopActivity.this, ProductViewActivity.class);
                intent.putExtra("collection", "Versatile");
                startActivity(intent);
            }
        });

        seemore2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShopActivity.this, ProductViewActivity.class);
                intent.putExtra("collection", "Crewneck");
                startActivity(intent);
            }
        });

        seemore3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShopActivity.this, ProductViewActivity.class);
                intent.putExtra("collection", "Oversize");
                startActivity(intent);
            }
        });

        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ShopActivity.this, ShoppingCartActivity.class);
                intent.putExtra("cartcheck", "shop");
                startActivity(intent);
            }
        });

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                switch (item.getItemId()){
                    case R.id.shop:
                        return true;
                    case R.id.explore:
                        startActivity(new Intent(getApplicationContext(),ExploreActivity.class));
//                        overridePendingTransition(0,0);
                        return true;
                    case R.id.favorites:
                        startActivity(new Intent(getApplicationContext(),FavoritesActivity.class));
//                        overridePendingTransition(0,0);
                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(),UserDashboardActivity.class));
//                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
    }

}