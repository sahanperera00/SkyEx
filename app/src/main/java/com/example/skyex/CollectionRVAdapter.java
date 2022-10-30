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

public class CollectionRVAdapter extends RecyclerView.Adapter<CollectionRVAdapter.MyViewHolder> {
    Context context;
    ArrayList<CollectionModel> collectionModelArrayList;

    public CollectionRVAdapter(Context context, ArrayList<CollectionModel> collectionModelArrayList) {
        this.context = context;
        this.collectionModelArrayList = collectionModelArrayList;
    }

    @NonNull
    @Override
    public CollectionRVAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.explore_card, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CollectionRVAdapter.MyViewHolder holder, int position) {
        CollectionModel collectionModel = collectionModelArrayList.get(position);
        holder.clName.setText(collectionModel.getCollectionName());
//        holder.clImage.setText(collectionModel.getCollectionImage());
        Picasso.get().load(collectionModel.getCollectionImage()).into(holder.clImage);

        holder.clEditBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, EditCollectionActivity.class);
                intent.putExtra("collection", collectionModel);
                context.startActivity(intent);
            }
        });

        holder.clImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, ProductViewActivity.class);
                intent.putExtra("collection", collectionModel.getCollectionName());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return collectionModelArrayList.size();
    }

    public static class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView clName;
        private ImageView clImage;
        private Button clEditBtn;
        private ConstraintLayout clConstraintLayout;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            clName = itemView.findViewById(R.id.etvCollectionName);
            clImage = itemView.findViewById(R.id.idIBCollection);
            clEditBtn = itemView.findViewById(R.id.idEditBtn);

            clEditBtn.setVisibility(View.GONE);
//            clConstraintLayout = itemView.findViewById(R.id.ecrvlayout);
        }
    }
}
