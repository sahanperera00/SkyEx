package com.example.skyex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.HashMap;
import java.util.Map;

public class EditProductActivity extends AppCompatActivity {
//    private EditText productNameEdt, productDescriptionEdt, productPriceEdt;
    private EditText productNameEdt, productDescriptionEdt, productTypeEdt, productCollectionEdt, productPriceEdt, productImageEdt;
    private Button updateProductBtn, deleteProductBtn;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String productId;
    private ProductModel productModel;
    private String productCollection;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_product);

        productNameEdt = findViewById(R.id.edtProductName);
        productDescriptionEdt = findViewById(R.id.edtProductDescription);
        productTypeEdt = findViewById(R.id.edtProductType);
        productCollectionEdt = findViewById(R.id.edtProductCollection);
        productPriceEdt = findViewById(R.id.editProductPrice);
        productImageEdt = findViewById(R.id.editProductImage);
        updateProductBtn = findViewById(R.id.btnUpdateProduct);
        deleteProductBtn = findViewById(R.id.btnDeleteProduct);
        firebaseDatabase = FirebaseDatabase.getInstance();

        productModel = getIntent().getParcelableExtra("product");
        if (productModel !=null){
            productNameEdt.setText(productModel.getProductName());
            productDescriptionEdt.setText(productModel.getProductDescription());
            productTypeEdt.setText(productModel.getProductType());
            productCollectionEdt.setText(productModel.getProductCollection());
            productPriceEdt.setText(productModel.getProductPrice());
            productImageEdt.setText(productModel.getProductImage());
            productId = productModel.getProductId();
        }

        databaseReference = firebaseDatabase.getReference("Products").child(productId);

        updateProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String productName = productNameEdt.getText().toString();
                String productDescription = productDescriptionEdt.getText().toString();
                String productType = productTypeEdt.getText().toString();
                productCollection = productCollectionEdt.getText().toString();
                String productPrice = productPriceEdt.getText().toString();
                String productImage = productImageEdt.getText().toString();

                if (TextUtils.isEmpty(productName)) {
                    Toast.makeText(EditProductActivity.this, "Please enter a product name", Toast.LENGTH_SHORT).show();
                    productNameEdt.requestFocus();
                } else if (TextUtils.isEmpty(productDescription)) {
                    Toast.makeText(EditProductActivity.this, "Please enter a description", Toast.LENGTH_SHORT).show();
                    productDescriptionEdt.requestFocus();
                } else if (TextUtils.isEmpty(productType)) {
                    Toast.makeText(EditProductActivity.this, "Please enter a type", Toast.LENGTH_SHORT).show();
                    productTypeEdt.requestFocus();
                } else if (TextUtils.isEmpty(productCollection)) {
                    Toast.makeText(EditProductActivity.this, "Please enter a collection", Toast.LENGTH_SHORT).show();
                    productCollectionEdt.requestFocus();
                } else if (TextUtils.isEmpty(productPrice)) {
                    Toast.makeText(EditProductActivity.this, "Please enter a price", Toast.LENGTH_SHORT).show();
                    productPriceEdt.requestFocus();
                } else if (TextUtils.isEmpty(productImage)) {
                    Toast.makeText(EditProductActivity.this, "Please enter a URL for a image", Toast.LENGTH_SHORT).show();
                    productImageEdt.requestFocus();
                } else{

                        Map<String, Object> map = new HashMap<>();
                    map.put("productId", productId);
                    map.put("productName", productName);
                    map.put("productDescription", productDescription);
                    map.put("productType", productType);
                    map.put("productCollection", productCollection);
                    map.put("productPrice", productPrice);
                    map.put("productImage", productImage);

                    databaseReference.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(EditProductActivity.this, "Product updated", Toast.LENGTH_SHORT).show();
                                Intent intent = new Intent(EditProductActivity.this, ProductViewActivity.class);
                                intent.putExtra("collection", productCollection);
                                startActivity(intent);
                                finish();
                            } else {
                                Toast.makeText(EditProductActivity.this, "Update unsuccessful", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            }
            }
        });
//System.out.println(productCollection);
        deleteProductBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                productCollection = productCollectionEdt.getText().toString();
                databaseReference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(EditProductActivity.this, "Product Deleted", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(EditProductActivity.this,ProductViewActivity.class);
                            intent.putExtra("collection", productCollection);
                            startActivity(intent);
                            finish();
                        }else {
                            Toast.makeText(EditProductActivity.this, "Delete unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}