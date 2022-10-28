package com.example.skyex;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.AbstractCollection;
import java.util.ArrayList;
import java.util.Objects;

public class ExploreActivity extends AppCompatActivity {
    private Button addCollecitionButton;
    private RecyclerView recyclerView;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<CollectionModel> collectionModelArrayList;
    private CollectionRVAdapter collectionRVAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_explore);

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Collections");
        addCollecitionButton = findViewById(R.id.idAddCollectionBtn);
        recyclerView = findViewById(R.id.ExploreRecyclerView);
        collectionModelArrayList = new ArrayList<>();

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.explore);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        collectionRVAdapter = new CollectionRVAdapter(this,collectionModelArrayList);
        recyclerView.setAdapter(collectionRVAdapter);
        getAllCollections();

        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.shop:
                        startActivity(new Intent(getApplicationContext(),ShopActivity.class));
                        overridePendingTransition(0,0);
                        return true;
                    case R.id.explore:
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

        addCollecitionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(ExploreActivity.this, AddCollectionActivity.class));
                overridePendingTransition(0,0);
                finish();
            }
        });
    }

    private void getAllCollections() {
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot dataSnapshot: snapshot.getChildren()){
                    CollectionModel collectionModel = dataSnapshot.getValue(CollectionModel.class);
                    collectionModelArrayList.add(collectionModel);
                    if (dataSnapshot.exists()){
                        for (DataSnapshot dataSnapshot1: dataSnapshot.getChildren()){
                            if (Objects.equals(dataSnapshot1.getValue(), "a")&&Objects.equals(dataSnapshot1.getKey(),"productDescription")){
//                                ExploreCardModel exploreCardModel = dataSnapshot.getValue(ExploreCardModel.class);
//                                exploreCardModelsArrayList.add(exploreCardModel);
                            }
                        }
                    }
                }
                collectionRVAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}