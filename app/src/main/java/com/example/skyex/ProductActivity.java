package com.example.skyex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

public class ProductActivity extends AppCompatActivity {
    private TextView productName, productPrice, productDescription, productSize;
    private ImageView productImage;
    private ImageButton backProductButton, cartButton;
    private Button addtocartButton;
    private TextInputEditText addQuantity = null;
    private String collectionName;
    private ProductModel productModel;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setSelectedItemId(R.id.shop);

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
        productName = findViewById(R.id.idTVProductName);
        productPrice = findViewById(R.id.idTVProductPrice);
        productSize = findViewById(R.id.idTVProductSize);
        productImage = findViewById(R.id.idIVProductImage);
        productDescription = findViewById(R.id.idTVProductDescription);
        backProductButton = findViewById(R.id.idIBBackProduct);
        cartButton = findViewById(R.id.idIBCart);
        addtocartButton = findViewById(R.id.idAddToCartBtn);
        addQuantity = findViewById(R.id.IDTIQuantity);
        collectionName = getIntent().getExtras().getString("collection");
        productModel = getIntent().getParcelableExtra("product");

        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Carts");

        if (productModel!=null){
            productName.setText(productModel.getProductName());
            productPrice.setText(productModel.getProductPrice());
            Picasso.get().load(productModel.getProductImage()).into(productImage);
            productDescription.setText(productModel.getProductDescription());
        }

        DatabaseReference specificdatabasereference;
        String ID = productName.getText().toString();

//        XXXNew methodXXX
//        String ID = FirebaseAuth.getInstance().getUid().toString();
//        FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        specificdatabasereference = firebaseDatabase.getReference("Carts").child(ID);

        specificdatabasereference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                Object dbIDobject = snapshot.child("id").getValue();

                if(dbIDobject == null){
                    addtocartButton.setClickable(true);
                }
                else{
                    String dbID = dbIDobject.toString();
                    if(dbID.equals(ID)){
//                        addtocartButton.setClickable(false);
                        addtocartButton.setVisibility(View.GONE);
                    }
                    else
                        addtocartButton.setClickable(true);
                }
            }

                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });


        addtocartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (String.valueOf(addQuantity.getText()).equals("")){
                    Toast.makeText(ProductActivity.this, "Add quantity", Toast.LENGTH_SHORT).show();
                }else {
                    String quantity = addQuantity.getText().toString();
                    String name = productName.getText().toString();
                    String price = productPrice.getText().toString();
                    String size = productSize.getText().toString();
                    String image = productModel.getProductImage().toString();
                    String ID = name;

                    CartModel cartModel = new CartModel(name, size, quantity,price, ID, image);

//                    XXXNew methodXXX
//                    databaseReference.child(FirebaseAuth.getInstance().getUid().toString()).child(ID).setValue(cartModel);
                    databaseReference.child(ID).setValue(cartModel);
                    Toast.makeText(ProductActivity.this, "Product Added to the cart", Toast.LENGTH_SHORT).show();
                }
            }
        });

        backProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductActivity.this, ProductViewActivity.class);
                intent.putExtra("collection", collectionName);
                startActivity(intent);
//                overridePendingTransition(0,0);
            }
        });

        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductActivity.this, ShoppingCartActivity.class);
                intent.putExtra("collection", collectionName);
                intent.putExtra("product", productModel);
                startActivity(intent);
            }
        });
    }
}