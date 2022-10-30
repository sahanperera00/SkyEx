package com.example.skyex;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.Map;

public class EditCardActivity extends AppCompatActivity {

    private TextInputEditText editCardNumber1, editCardNumber2, editCardNumber3, editCardNumber4, editCardMonth,editCardYear, editCardHolder;
    private Button editCard, deleteCard;
    private TextView textNumber1, textNumber2, textNumber3, textNumber4, textName, textYear, textMonth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private CardModel cardModel;
    private AlertDialog.Builder builder;

    private String ID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_card);
        firebaseDatabase = firebaseDatabase.getInstance();
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

        builder = new AlertDialog.Builder(this);

        editCard = findViewById(R.id.button);
        deleteCard = findViewById(R.id.deletebutton);


        cardModel = getIntent().getParcelableExtra("card");

        if(cardModel != null){

            String wholeNumber = cardModel.getCardNumber();
            char[] a = wholeNumber.toCharArray();
            char[] n1 = new char[4];
            char[] n2 = new char[4];
            char[] n3 = new char[4];
            char[] n4 = new char[4];

//            for (int i = 0; i < 16; i++){
//                if(i < 4){
//                    n1[i] = a[i];
//                }
//
//                else if(i >= 4 && i < 8){
//                    n2[i-4] = a[i];
//                }
//
//                else if(i >= 8 && i < 12){
//                    n3[i-8] = a[i];
//                }
//                else if(i >= 12 && i < 16) {
//                    n4[i-12] = a[i];
//                }
//            }

            for(int i = 0; i < 4; i++){
                n1[i] = a[i];
            }

            for(int i = 4; i < 8; i++){
                n2[i-4] = a[i];
            }

            for(int i = 8; i < 12; i++){
                n3[i-8] = a[i];
            }

            for(int i = 12; i < 16; i++){
                n4[i-12] = a[i];
            }

            String CardNumber1 = new String(n1);
            String CardNumber2 = new String(n2);
            String CardNumber3 = new String(n3);
            String CardNumber4 = new String(n4);

            editCardNumber1.setText(CardNumber1);
            editCardNumber2.setText(CardNumber2);
            editCardNumber3.setText(CardNumber3);
            editCardNumber4.setText(CardNumber4);
            editCardMonth.setText(cardModel.getMonth());
            editCardYear.setText(cardModel.getYear());
            editCardHolder.setText(cardModel.getName());

            ID = cardModel.getID();

            textNumber1.setText(CardNumber1);
            textNumber2.setText(CardNumber2);
            textNumber3.setText(CardNumber3);
            textNumber4.setText(CardNumber4);
            textMonth.setText(cardModel.getMonth());
            textYear.setText(cardModel.getYear());
            textName.setText(cardModel.getName());
        }

        databaseReference = firebaseDatabase.getReference("Cards").child(ID);

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

        editCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String number1 = editCardNumber1.getText().toString();
                String number2 = editCardNumber2.getText().toString();
                String number3 = editCardNumber3.getText().toString();
                String number4 = editCardNumber4.getText().toString();
                String month = editCardMonth.getText().toString();
                String year = editCardYear.getText().toString();
                String name = editCardHolder.getText().toString();

                String finalNumber = number1 + number2 + number3 + number4;

                ID = finalNumber;

                Map<String, Object> map = new HashMap<>();
                map.put("CardNumber", finalNumber);
                map.put("month", month);
                map.put("year", year);
                map.put("name", name);
                map.put("ID", ID);

//                databaseReference.addValueEventListener(new ValueEventListener() {
//                    @Override
//                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
//                        databaseReference.updateChildren(map);
//                        Toast.makeText(EditCard.this, "Card successfully updated", Toast.LENGTH_LONG).show();
//                        startActivity(new Intent(EditCard.this, Payment_activity.class));
//                    }
//
//                    @Override
//                    public void onCancelled(@NonNull DatabaseError databaseError) {
//                        Toast.makeText(EditCard.this, "Error in adding the card: "+ databaseError.toString(), Toast.LENGTH_SHORT).show();
//
//                    }
//                });

                databaseReference.updateChildren(map).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isComplete()){
                            Toast.makeText(EditCardActivity.this, "Card successfully updated", Toast.LENGTH_LONG).show();
                            startActivity(new Intent(EditCardActivity.this, PaymentActivity.class));
                            finish();
                        }

                        else{
                            Toast.makeText(EditCardActivity.this, "Error in adding the card ", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

        deleteCard.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                builder.setTitle("Are you sure?")
                        .setMessage("Deleted data stay deleted")
                        .setCancelable(true)
                        .setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                deleteCard();
                            }
                        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                dialogInterface.cancel();
                            }
                        }).show();
            }
        });


    }

    private void deleteCard(){

        databaseReference.removeValue();
        Toast.makeText(EditCardActivity.this, "Card successfully deleted", Toast.LENGTH_SHORT).show();
        startActivity(new Intent(EditCardActivity.this, PaymentActivity.class));
    }

}