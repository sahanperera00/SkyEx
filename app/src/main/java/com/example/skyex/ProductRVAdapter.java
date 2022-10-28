package com.example.skyex;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class ProductRVAdapter extends RecyclerView.Adapter<ProductRVAdapter.MyViewHolder> {
    Context context;
    ArrayList<ProductModel> productModelsArraylist;

    public ProductRVAdapter(Context context, ArrayList<ProductModel> productModelsArraylist) {
        this.context = context;
        this.productModelsArraylist = productModelsArraylist;
    }

    @NonNull
    @Override
    public ProductRVAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.product_card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ProductRVAdapter.MyViewHolder holder, int position) {
        ProductModel productModel = productModelsArraylist.get(position);
        holder.prName.setText(productModel.getProductName());
        holder.prPrice.setText(productModel.getProductPrice());
        Picasso.get().load(productModel.getProductImage()).into(holder.prImage);

        holder.prEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditProductActivity.class);
                intent.putExtra("product", productModel);
                context.startActivity(intent);
            }
        });

        holder.prImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductActivity.class);
                intent.putExtra("product", productModel);
                intent.putExtra("collection", productModel.getProductCollection().toString());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return productModelsArraylist.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView prName,prPrice;
        private ImageView prImage;
        private Button prEditBtn;
        private ConstraintLayout prConstraintLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            prName = itemView.findViewById(R.id.etvProductName);
            prPrice = itemView.findViewById(R.id.etvProductPrice);
            prEditBtn = itemView.findViewById(R.id.idEditBtn2);
            prImage = itemView.findViewById(R.id.idIBProduct);
//            prRelativeLayout = itemView.findViewById(R.id.idRLCollection);
        }
    }
}