package com.example.skyex;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class PaymentActivity extends AppCompatActivity implements CardAdapter.CardClickInterface, CardAdapter.ButtonClickInterface {

    private RecyclerView CardRV;
    private Button Add, next;
    private TextView cardName, cardNumber, cardMonth, cardYear;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference;
    private ArrayList<CardModel> cardModelArrayList;
    private ConstraintLayout bottomSheetRL;
    private CardAdapter cardAdapter;
    private LinearLayoutManager linearLayoutManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_payment);
        CardRV = findViewById(R.id.RVCards);
        Add = findViewById(R.id.button2);
        next = findViewById(R.id.button);
        cardNumber = findViewById(R.id.textNumber1);
        cardName = findViewById(R.id.textName);
        cardMonth = findViewById(R.id.textMonth);
        cardYear = findViewById(R.id.textYear);
        firebaseDatabase = FirebaseDatabase.getInstance();
        databaseReference = firebaseDatabase.getReference("Cards");
        cardModelArrayList = new ArrayList<>();
//        cardAdapter = new CardAdapter(cardModelArrayList,this,this, this);
        cardAdapter = new CardAdapter(cardModelArrayList, this, this, this);


        linearLayoutManager = new LinearLayoutManager(PaymentActivity.this, linearLayoutManager.HORIZONTAL, false);

        CardRV.setLayoutManager(linearLayoutManager);
        CardRV.setAdapter(cardAdapter);

        Add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PaymentActivity.this, AddCardActivity.class));
            }
        });

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(PaymentActivity.this, CheckoutActivity.class));
            }
        });

        getAllCards();

    }

    private void getAllCards() {

        cardModelArrayList.clear();
        databaseReference.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                cardModelArrayList.add(dataSnapshot.getValue(CardModel.class));
                cardAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                cardAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                cardAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                cardAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }

    public void onButtonClick(int position) {
        displayEditActivity(cardModelArrayList.get(position));
    }


    @Override
    public void onCardClick(int position) {
//        displayBottom(cardModelArrayList.get(position));
        displaySelected(cardModelArrayList.get(position));
    }

    public void displayEditActivity(CardModel cardModel) {
        Intent i = new Intent(PaymentActivity.this, EditCardActivity.class);
        i.putExtra("card", cardModel);
        startActivity(i);

    }

    public void displaySelected(CardModel cardModel){

        cardNumber.setText(cardModel.getCardNumber());
        cardName.setText(cardModel.getName());
        cardMonth.setText(cardModel.getMonth());
        cardYear.setText(cardModel.getYear());

    }

//    private void displayBottom(CardModel cardModel) {
//        final BottomSheetDialog bottomSheetDialog = new BottomSheetDialog(this);
//        View layout = LayoutInflater.from(this).inflate(R.layout.bottom_dialog_cards,bottomSheetRL);
//        bottomSheetDialog.setContentView(layout);
//        bottomSheetDialog.setCancelable(false);
//        bottomSheetDialog.setCancelable(true);
//        bottomSheetDialog.show();
//
//        TextView cardNumbertxt = layout.findViewById(R.id.tvNumber);
//        TextView cardNametxt = layout.findViewById(R.id.tvName);
//        TextView cardMonthtxt = layout.findViewById(R.id.tvMonth);
//        TextView cardYeartxt = layout.findViewById(R.id.tvYear);
//        Button editButton = layout.findViewById(R.id.editCardbtn);
//        Button deleteButton = layout.findViewById(R.id.deleteCardbtn);
//        Button selectButton = layout.findViewById(R.id.selectCardbtn);
//        DatabaseReference databaseReference = firebaseDatabase.getReference();
//
//        cardNumbertxt.setText(cardModel.getCardNumber());
//        cardNametxt.setText(cardModel.getName());
//        cardMonthtxt.setText(cardModel.getMonth());
//        cardYeartxt.setText(cardModel.getYear());
//
//        editButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent i = new Intent(MainActivity.this, EditCard.class);
//                i.putExtra("card", cardModel);
//                startActivity(i);
//            }
//        });
//
//        deleteButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                databaseReference.removeValue();
//                Toast.makeText(MainActivity.this, "Card successfully deleted", Toast.LENGTH_SHORT).show();
//                startActivity(new Intent(MainActivity.this, MainActivity.class));
//                bottomSheetDialog.cancel();
//            }
//        });
//
//    }
}