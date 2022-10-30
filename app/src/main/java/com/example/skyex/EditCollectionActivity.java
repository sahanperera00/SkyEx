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

public class EditCollectionActivity extends AppCompatActivity {
    private EditText collectionNameEdt, collectionImageEdt;
    private Button updateCollectionBtn, deleteCollectionBtn;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private String collectionId;
    private CollectionModel collectionModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_collection);

        collectionNameEdt = findViewById(R.id.edtCollectionName);
        collectionImageEdt = findViewById(R.id.editCollectionImage);
        updateCollectionBtn = findViewById(R.id.btnUpdateCollection);
        deleteCollectionBtn = findViewById(R.id.btnDeleteCollection);
        firebaseDatabase = FirebaseDatabase.getInstance();
        collectionModel = getIntent().getParcelableExtra("collection");

        if (collectionModel!=null) {
            collectionNameEdt.setText(collectionModel.getCollectionName());
            collectionImageEdt.setText(collectionModel.getCollectionImage());
            collectionId = collectionModel.getCollectionId();
        }

        databaseReference = firebaseDatabase.getReference("Collections").child(collectionId);

        updateCollectionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String collectionName = collectionNameEdt.getText().toString();
                String collectionImage = collectionImageEdt.getText().toString();

                if (TextUtils.isEmpty(collectionName)) {
                    Toast.makeText(EditCollectionActivity.this, "Please enter a name", Toast.LENGTH_SHORT).show();
                    collectionNameEdt.requestFocus();
                } else if (TextUtils.isEmpty(collectionImage)) {
                    Toast.makeText(EditCollectionActivity.this, "Please enter a URL of a Image", Toast.LENGTH_SHORT).show();
                    collectionImageEdt.requestFocus();
                } else{

                    Map<String, Object> map = new HashMap<>();
                    map.put("collectionId", collectionId);
                    map.put("collectionName", collectionName);
                    map.put("collectionImage", collectionImage);

                    databaseReference.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Toast.makeText(EditCollectionActivity.this, "Collection updated", Toast.LENGTH_SHORT).show();
                                startActivity(new Intent(EditCollectionActivity.this, ExploreActivity.class));
                                finish();
                            } else {
                                Toast.makeText(EditCollectionActivity.this, "Update unsuccessful", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
            }
            }
        });

        deleteCollectionBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                databaseReference.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()){
                            Toast.makeText(EditCollectionActivity.this, "Collection deleted", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(EditCollectionActivity.this, ExploreActivity.class));
                            finish();
                        }else {
                            Toast.makeText(EditCollectionActivity.this, "Delete unsuccessful", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}