//package com.example.skyex;
//
//import androidx.appcompat.app.AppCompatActivity;
//
//import android.content.Intent;
//import android.os.Bundle;
//import android.view.MenuItem;
//
//import com.google.android.material.bottomnavigation.BottomNavigationView;
//import com.google.android.material.navigation.NavigationBarView;
//
//public class FavoritesActivity extends AppCompatActivity {
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.activity_favorites);
//
//        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
//        bottomNavigationView.setSelectedItemId(R.id.favorites);
//
//        bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
//            @Override
//            public boolean onNavigationItemSelected(MenuItem item) {
//                switch (item.getItemId()){
//                    case R.id.shop:
//                        startActivity(new Intent(getApplicationContext(),ShopActivity.class));
//                        overridePendingTransition(0,0);
//                        return true;
//                    case R.id.explore:
//                        startActivity(new Intent(getApplicationContext(),ExploreActivity.class));
//                        overridePendingTransition(0,0);
//                        return true;
//                    case R.id.favorites:
//                        return true;
//                    case R.id.profile:
//                        startActivity(new Intent(getApplicationContext(),ProfileActivity.class));
//                        overridePendingTransition(0,0);
//                        return true;
//                }
//                return false;
//            }
//        });
//    }
//}