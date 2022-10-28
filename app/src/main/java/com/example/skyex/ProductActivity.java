package com.example.skyex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class ProductActivity extends AppCompatActivity {
    private TextView productName, productPrice, productDescription;
    private ImageView productImage;
    private ImageButton backProductButton;
    private String collectionName;
    private ProductModel productModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);

        productName = findViewById(R.id.idTVProductName);
        productPrice = findViewById(R.id.idTVProductPrice);
        productImage = findViewById(R.id.idIVProductImage);
        productDescription = findViewById(R.id.idTVProductDescription);
        backProductButton = findViewById(R.id.idIBBackProduct);
        collectionName = getIntent().getExtras().getString("collection");
        productModel = getIntent().getParcelableExtra("product");

        if (productModel!=null){
            productName.setText(productModel.getProductName());
            productPrice.setText(productModel.getProductPrice());
            Picasso.get().load(productModel.getProductImage()).into(productImage);
            productDescription.setText(productModel.getProductDescription());
        }

        backProductButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(ProductActivity.this, ProductViewActivity.class);
                intent.putExtra("collection", collectionName);
                startActivity(intent);
                overridePendingTransition(0,0);
            }
        });
    }
}