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

public class AddCollectionActivity extends AppCompatActivity {
    private EditText collectionNameEdt, collectionImageEdt;
    private Button addCollectionBtn;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String collectionId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_collection);

        collectionNameEdt = findViewById(R.id.edtCollectionName);
        collectionImageEdt = findViewById(R.id.editCollectionImage);
        addCollectionBtn = findViewById(R.id.btnAddCollection);

        addCollectionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                firebaseDatabase = FirebaseDatabase.getInstance();
                databaseReference = firebaseDatabase.getReference("Collections");

                String collectionName = collectionNameEdt.getText().toString();
                String collectionImage = collectionImageEdt.getText().toString();

                if (TextUtils.isEmpty(collectionName)) {
                    Toast.makeText(AddCollectionActivity.this, "Please enter a name", Toast.LENGTH_SHORT).show();
                    collectionNameEdt.requestFocus();
                } else if (TextUtils.isEmpty(collectionImage)) {
                    Toast.makeText(AddCollectionActivity.this, "Please enter a URL of a Image", Toast.LENGTH_SHORT).show();
                    collectionImageEdt.requestFocus();
                } else{
                    collectionId = collectionName;

                    CollectionModel collectionModel = new CollectionModel(
                            collectionId,
                            collectionName,
                            collectionImage
                    );
                    databaseReference.child(collectionId).setValue(collectionModel).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(AddCollectionActivity.this, "Collection added", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(AddCollectionActivity.this, ExploreActivity.class));
                                finish();
                            } else {
                                Toast.makeText(AddCollectionActivity.this, "Add unsuccessful", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });

            }
            }
        });
    }
}