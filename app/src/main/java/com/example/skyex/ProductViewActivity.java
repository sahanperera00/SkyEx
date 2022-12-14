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
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Objects;

public class ProductViewActivity extends AppCompatActivity {
    private TextView topic;
    private ImageButton backProductviewButton, cartButton;
    private Button addProductButton;
    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private SearchView searchView;
    private DatabaseReference databaseReference;
    private ArrayList<ProductModel> productModelArrayList;
    private ProductRVAdapter productRVAdapter;
    private CollectionModel collectionModel;
    private String collectionName;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_view);

        cartButton = findViewById(R.id.idIBCart);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Products");
        addProductButton = findViewById(R.id.idProductBtn);
        recyclerView = findViewById(R.id.ProductRecyclerView);
        productModelArrayList = new ArrayList<>();
        backProductviewButton = findViewById(R.id.idIBBack);
        topic = findViewById(R.id.idTVExploreProduct);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        collectionName = getIntent().getExtras().getString("collection");
        topic.setText(collectionName);

        searchView = findViewById(R.id.searchViewProduct);
        searchView.clearFocus();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String s) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                filterList(newText);
                return false;
            }
        });

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.explore);

        productRVAdapter = new ProductRVAdapter(this,productModelArrayList);
        recyclerView.setLayoutManager(new GridLayoutManager(this,2));
        recyclerView.setAdapter(productRVAdapter);
        getAllProducts();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.shop:
                        startActivity(new Intent(getApplicationContext(),ShopActivity.class));
//                        overridePendingTransition(0,0);
                        return true;
                    case R.id.explore:
                        startActivity(new Intent(getApplicationContext(),ExploreActivity.class));
//                        overridePendingTransition(0,0);
                        return true;
//                    case R.id.favorites:
//                        startActivity(new Intent(getApplicationContext(),FavoritesActivity.class));
////                        overridePendingTransition(0,0);
//                        return true;
                    case R.id.profile:
                        startActivity(new Intent(getApplicationContext(),UserDashboardActivity.class));
//                        overridePendingTransition(0,0);
                        return true;
                }
                return false;
            }
        });
        FirebaseUser firebaseUser;
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        String Uid = firebaseUser.getUid();

        if (Uid.equals("jAyIlbUmERM6xoxmYqf1UWxlR7B2")){
            addProductButton.setVisibility(View.VISIBLE);
        }else {
            addProductButton.setVisibility(View.GONE);
        }

        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductViewActivity.this, ShoppingCartActivity.class);
                intent.putExtra("cartcheck", collectionName);
                startActivity(intent);
            }
        });

        addProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductViewActivity.this, AddProductActivity.class);
                intent.putExtra("collection", collectionName);
                startActivity(intent);
//                overridePendingTransition(0,0);
            }
        });

        backProductviewButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ProductViewActivity.this, ExploreActivity.class));
//                overridePendingTransition(0,0);
            }
        });
    }

    private void filterList(String text) {
        ArrayList<ProductModel> filteredList = new ArrayList<>();
        for (ProductModel model : productModelArrayList) {
            if (model.getProductName().toLowerCase().contains(text.toLowerCase())){
                filteredList.add(model);
            }
        }
        if (filteredList.isEmpty()){
            Toast.makeText(this, "No data found", Toast.LENGTH_SHORT).show();
        }else{
            productRVAdapter.setProductModelsArraylist(filteredList);
        }
    }

    private void getAllProducts() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    if (dataSnapshot.exists()){
                        for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                            if (Objects.equals(dataSnapshot1.getKey(),"productCollection")&&Objects.equals(dataSnapshot1.getValue(), collectionName)){
                                ProductModel productModel = dataSnapshot.getValue(ProductModel.class);
                                productModelArrayList.add(productModel);
                            }
                        }
                    }
                }
                productRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}