package com.avit.apnamzp.ui.pickupanddrop;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avit.apnamzp.R;
import com.avit.apnamzp.models.cart.CartItemData;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;

public class PickAndDropAdapter extends RecyclerView.Adapter<PickAndDropAdapter.PickAndDropViewHolder>{

    ArrayList<CartItemData> cartItemDataList;
    private Context context;

    public PickAndDropAdapter(Context context) {
        cartItemDataList = new ArrayList<>();
        this.context = context;
    }

    @NonNull
    @Override
    public PickAndDropViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_pickanddroplistitem,parent,false);
        return new PickAndDropViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PickAndDropViewHolder holder, int position) {
        CartItemData currItem = cartItemDataList.get(position);

        holder.menuItemNameView.setText(currItem.getName());
        holder.quantityText.setText(String.valueOf(currItem.getQuantity()));

        holder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int pos = cartItemDataList.indexOf(currItem);
                cartItemDataList.remove(pos);
                notifyItemRangeRemoved(pos,1);
            }
        });

    }

    @Override
    public int getItemCount() {
        return cartItemDataList.size();
    }

    public void addItem(CartItemData cartItemData){
        cartItemDataList.add(cartItemData);
        notifyItemInserted(cartItemDataList.size()-1);
    }

    class PickAndDropViewHolder extends RecyclerView.ViewHolder{

        private TextInputEditText menuItemNameView;
        private TextView quantityText;
        private ImageView removeButton;

        public PickAndDropViewHolder(@NonNull View itemView) {
            super(itemView);
            menuItemNameView = itemView.findViewById(R.id.menuItemName);
            quantityText = itemView.findViewById(R.id.quantityText);
            removeButton = itemView.findViewById(R.id.removeButton);
        }
    }
}

