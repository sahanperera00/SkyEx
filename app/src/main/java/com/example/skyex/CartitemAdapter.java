package com.example.skyex;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class CartitemAdapter extends RecyclerView.Adapter<CartitemAdapter.ViewHolder> {

    private ArrayList <CartModel> cartModelArrayList;
    private Context context;
    int lastPosition = -1;

    public CartitemAdapter(ArrayList<CartModel> cartModelArrayList, Context context) {
        this.cartModelArrayList = cartModelArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public CartitemAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.cart_item,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartitemAdapter.ViewHolder holder, int position) {
        CartModel cartModel = cartModelArrayList.get(position);
        int total = Integer.parseInt(cartModel.getPrice())*Integer.parseInt(cartModel.getQuantity());
        holder.name.setText(cartModel.getName());
//        holder.price.setText(cartModel.getPrice());
        holder.price.setText("Rs. " + String.valueOf(total));
        holder.quantity.setText(cartModel.getQuantity());
        holder.size.setText(cartModel.getSize());

    }

    @Override
    public int getItemCount() {
        return cartModelArrayList.size();
    }

    public void deleteItem(int position){
        CartModel cartModel = cartModelArrayList.get(position);
        String ID = cartModel.getID();
        FirebaseDatabase.getInstance().getReference().child("Carts").child(ID).removeValue();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name, size, quantity, price;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.item_name);
            size = itemView.findViewById(R.id.item_size);
            quantity = itemView.findViewById(R.id.item_quantity);
            price = itemView.findViewById(R.id.item_price);



        }
    }
}
