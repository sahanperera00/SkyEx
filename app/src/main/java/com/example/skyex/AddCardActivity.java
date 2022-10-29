package com.example.skyex;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class AddCardActivity extends AppCompatActivity {

    private TextInputEditText editCardNumber1, editCardNumber2, editCardNumber3, editCardNumber4, editCardMonth,editCardYear, editCardHolder;
    private Button addCard;
    private TextView textNumber1, textNumber2, textNumber3, textNumber4, textName, textYear, textMonth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;

    private String ID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_card);
        editCardNumber1 = findViewById(R.id.editCardNumber1);
        editCardNumber2 = findViewById(R.id.editCardNumber2);
        editCardNumber3 = findViewById(R.id.editCardNumber3);
        editCardNumber4 = findViewById(R.id.editCardNumber4);
        editCardMonth = findViewById(R.id.editCardMonth);
        editCardYear = findViewById(R.id.editCardYear);
        editCardHolder = findViewById(R.id.editCardHolder);
        textNumber1 = findViewById(R.id.textNumber1);
        textNumber2 = findViewById(R.id.textNumber2);
        textNumber3 = findViewById(R.id.textNumber3);
        textNumber4 = findViewById(R.id.textNumber4);
        textName = findViewById(R.id.textName);
        textMonth = findViewById(R.id.textMonth);
        textYear = findViewById(R.id.textYear);

        addCard = findViewById(R.id.button);

        firebaseDatabase = firebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Cards");

        editCardNumber1.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String number1 = editCardNumber1.getText().toString();
                textNumber1.setText(number1);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editCardNumber2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String number2 = editCardNumber2.getText().toString();
                textNumber2.setText(number2);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editCardNumber3.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String number3 = editCardNumber3.getText().toString();
                textNumber3.setText(number3);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editCardNumber4.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String number4 = editCardNumber4.getText().toString();
                textNumber4.setText(number4);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editCardHolder.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String name = editCardHolder.getText().toString();
                textName.setText(name);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editCardMonth.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String month = editCardMonth.getText().toString();
                textMonth.setText(month);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

        editCardYear.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                String year = editCardYear.getText().toString();
                textYear.setText(year);
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });





        addCard.setOnClickListener(new View.OnClickListener() {
                                       @Override
                                       public void onClick(View view) {

                                           String CardNumber = editCardNumber1.getText().toString() + editCardNumber2.getText().toString() + editCardNumber3.getText().toString() + editCardNumber4.getText().toString() ;
                                           String month = editCardMonth.getText().toString();
                                           String year = editCardYear.getText().toString();
                                           String name = editCardHolder.getText().toString();

                                           ID = CardNumber;



                                           CardModel cardModel = new CardModel(CardNumber, month, year, name, ID);

                                           databaseReference.addValueEventListener(new ValueEventListener() {
                                               @Override
                                               public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                                   databaseReference.child(ID).setValue(cardModel);
                                                   Toast.makeText(AddCardActivity.this, "Card successfully added", Toast.LENGTH_LONG).show();
                                                   startActivity(new Intent(AddCardActivity.this, PaymentActivity.class));
                                               }

                                               @Override
                                               public void onCancelled(@NonNull DatabaseError databaseError) {
                                                   Toast.makeText(AddCardActivity.this, "Error in adding the card: "+ databaseError.toString(), Toast.LENGTH_SHORT).show();

                                               }
                                           });


                                       }
                                   }

        );





    }
}