package com.example.skyex;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class AddProductActivity extends AppCompatActivity {
    private EditText productNameEdt, productDescriptionEdt, productTypeEdt, productCollectionEdt, productPriceEdt, productImageEdt;
    private Button addProductBtn;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String productId;
    private CollectionModel collectionModel;
    private String collectionName;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_product);

        productNameEdt = findViewById(R.id.edtProductName);
        productDescriptionEdt = findViewById(R.id.edtProductDescription);
        productTypeEdt = findViewById(R.id.edtProductType);
        productCollectionEdt = findViewById(R.id.edtProductCollection);
        productPriceEdt = findViewById(R.id.editProductPrice);
        productImageEdt = findViewById(R.id.editProductImage);
        addProductBtn = findViewById(R.id.btnAddProduct);

        collectionName = getIntent().getExtras().getString("collection");
        productCollectionEdt.setText(collectionName);

        addProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("Products");

                String productName = productNameEdt.getText().toString();
                String productDescription = productDescriptionEdt.getText().toString();
                String productType = productTypeEdt.getText().toString();
                String productCollection = productCollectionEdt.getText().toString();
                String productPrice = productPriceEdt.getText().toString();
                String productImage = productImageEdt.getText().toString();

                if (TextUtils.isEmpty(productName)) {
                    Toast.makeText(AddProductActivity.this, "Please enter a product name", Toast.LENGTH_SHORT).show();
                    productNameEdt.requestFocus();
                } else if (TextUtils.isEmpty(productDescription)) {
                    Toast.makeText(AddProductActivity.this, "Please enter a description", Toast.LENGTH_SHORT).show();
                    productDescriptionEdt.requestFocus();
                } else if (TextUtils.isEmpty(productType)) {
                    Toast.makeText(AddProductActivity.this, "Please enter a type", Toast.LENGTH_SHORT).show();
                    productTypeEdt.requestFocus();
                } else if (TextUtils.isEmpty(productCollection)) {
                    Toast.makeText(AddProductActivity.this, "Please enter a collection", Toast.LENGTH_SHORT).show();
                    productCollectionEdt.requestFocus();
                } else if (TextUtils.isEmpty(productPrice)) {
                    Toast.makeText(AddProductActivity.this, "Please enter a price", Toast.LENGTH_SHORT).show();
                    productPriceEdt.requestFocus();
                } else if (TextUtils.isEmpty(productImage)) {
                    Toast.makeText(AddProductActivity.this, "Please enter a URL for a image", Toast.LENGTH_SHORT).show();
                    productImageEdt.requestFocus();
                } else{


                    productId = productName;

                    ProductModel productModel = new ProductModel(
                            productId,
                            productName,
                            productDescription,
                            productType,
                            productCollection,
                            productPrice,
                            productImage);

                    databaseReference.child(productId).setValue(productModel);
                    Toast.makeText(AddProductActivity.this, "Product Added", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(AddProductActivity.this, ProductViewActivity.class);
                    intent.putExtra("collection", collectionName);
                    startActivity(intent);
            }
            }
        });
    }
}