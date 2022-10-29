package com.example.skyex;

import android.annotation.SuppressLint;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CardAdapter extends RecyclerView.Adapter<CardAdapter.ViewHolder>{

    private ArrayList<CardModel> cardModelArrayList;
    private Context context;
    int lastPosition = -1;
    private CardClickInterface cardClickInterface;
    private ButtonClickInterface buttonClickInterface;

    public CardAdapter(ArrayList<CardModel> cardModelArrayList, Context context, CardClickInterface cardClickInterface, ButtonClickInterface buttonClickInterface) {
        this.cardModelArrayList = cardModelArrayList;
        this.context = context;
        this.cardClickInterface = cardClickInterface;
        this.buttonClickInterface = buttonClickInterface;
    }


    @NonNull
    @Override
    public CardAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.card_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CardAdapter.ViewHolder holder, @SuppressLint("RecyclerView") final int position) {
        CardModel cardModel = cardModelArrayList.get(position);

//        holder.wholeNumber = cardModel.getCardNumber();
//        char[] a = holder.wholeNumber.toCharArray();
//        char[] n1 = new char[4];
//        char[] n2 = new char[4];
//        char[] n3 = new char[4];
//        char[] n4 = new char[4];
//
//        for (int i = 0; i < 16; i++){
//            if(i < 4){
//                n1[i] = a[i];
//            }
//
//            else if(i >= 4 && i < 8){
//                n2[i-4] = a[i];
//            }
//
//            else if(i >= 8 && i < 12){
//                n3[i-8] = a[i];
//            }
//            else if(i >= 12 && i < 16) {
//                n4[i-12] = a[i];
//            }
//        }
//
//        String CardNumber1 = new String(n1);
//        String CardNumber2 = new String(n2);
//        String CardNumber3 = new String(n3);
//        String CardNumber4 = new String(n4);

        holder.cardNumber1.setText(cardModel.getCardNumber());
        holder.cardName.setText(cardModel.getName());
        holder.cardMonth.setText(cardModel.getMonth());
        holder.cardYear.setText(cardModel.getYear());
//        setAnimation(holder.itemView, position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                cardClickInterface.onCardClick(position);


            }
        });

        holder.editbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonClickInterface.onButtonClick(position);
            }
        });


    }

//    private void setAnimation(View itemView, int position){
////        if(position>lastPosition){
////            Animation animation = AnimationUtils.loadAnimation(context, android.R.anim.slide_out_right);
////            itemView.setAnimation(animation);
////            lastPosition = position;
////        }
////    }

    @Override
    public int getItemCount() {
        return cardModelArrayList.size();
    }

    public interface CardClickInterface{
        void onCardClick(int position);
    }

    public interface  ButtonClickInterface{
        void onButtonClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        private TextView cardNumber1, cardName, cardMonth, cardYear;
        private Button editbtn;
//        private String wholeNumber;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            cardNumber1 = itemView.findViewById(R.id.textNumber1);
//            cardNumber2 = itemView.findViewById(R.id.textNumber2);
//            cardNumber3 = itemView.findViewById(R.id.textNumber3);
//            cardNumber4 = itemView.findViewById(R.id.textNumber4);
            cardMonth = itemView.findViewById(R.id.textMonth);
            cardYear = itemView.findViewById(R.id.textYear);
            cardName = itemView.findViewById(R.id.textName);
            editbtn = itemView.findViewById((R.id.editCardbtn));
        }
    }



}
