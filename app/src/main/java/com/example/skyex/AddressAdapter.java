package com.example.skyex;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class AddressAdapter extends RecyclerView.Adapter<AddressAdapter.ViewHolder> {
    private ArrayList<AddressModel> addressModelArrayList;
    private Context context;
    int lastPosition = -1;
    private CardClickInterface cardClickInterface;
    private ButtonClickInterface buttonClickInterface;

    public AddressAdapter(ArrayList<AddressModel> addressModelArrayList, Context context, CardClickInterface cardClickInterface, ButtonClickInterface buttonClickInterface) {
        this.addressModelArrayList = addressModelArrayList;
        this.context = context;
        this.cardClickInterface = cardClickInterface;
        this.buttonClickInterface = buttonClickInterface;
    }

    @NonNull
    @Override
    public AddressAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.address_card,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull AddressAdapter.ViewHolder holder, @SuppressLint("RecyclerView") int position) {
        AddressModel addressModel = addressModelArrayList.get(position);

        holder.name.setText(addressModel.getName());
        holder.addressLine1.setText(addressModel.getAddressLine1());
        holder.addressLine2.setText(addressModel.getAddressLine2());
        holder.city.setText(addressModel.getCity());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cardClickInterface.onCardClick(position);
            }
        });

//        holder.delete.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                String ID = addressModel.getID();
//                FirebaseDatabase.getInstance().getReference().child("Addresses").
//                        child(ID).removeValue();
//            }
//        });

        holder.edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                buttonClickInterface.onButtonClick(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return addressModelArrayList.size();
    }

    public interface CardClickInterface{
        void onCardClick(int position);
    }

    public interface ButtonClickInterface{
        void onButtonClick(int position);
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private TextView name, addressLine1, addressLine2, city;
        private Button edit;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            addressLine1 = itemView.findViewById(R.id.textAddressLine1);
            addressLine2 = itemView.findViewById(R.id.textAddressLine2);
            city = itemView.findViewById(R.id.textCity);
            edit = itemView.findViewById(R.id.editAddressbtn);
//            delete = itemView.findViewById(R.id.deleteAddressbtn);
        }
    }
}

