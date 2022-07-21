package com.avit.apnamzp.ui.shopdetails;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avit.apnamzp.R;
import com.avit.apnamzp.localdb.Cart;
import com.avit.apnamzp.models.shop.ShopItemData;
import com.bumptech.glide.Glide;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class ShopMenuItemsAdapter extends RecyclerView.Adapter<ShopMenuItemsAdapter.ShopMenuItemsViewHolder>{

    public interface onAddButtonInterface{
      Boolean addButtonPressed(ShopItemData shopItemData,int position);
      void removeTheBatch();
    };

    private Context context;
    private List<ShopItemData> shopItemDataList;
    private onAddButtonInterface onAddButtonInterface;
    private String TAG = "ShopDetailsFragment";
    private boolean showAddButton;

    public ShopMenuItemsAdapter(Context context, List<ShopItemData> shopItemDataList,onAddButtonInterface onAddButtonInterface,boolean showAddButton) {
        this.context = context;
        this.shopItemDataList = shopItemDataList;
        this.onAddButtonInterface = onAddButtonInterface;
        this.showAddButton = showAddButton;
    }

    @NonNull
    @Override
    public ShopMenuItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.item_menu,parent,false);
        return new ShopMenuItemsViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopMenuItemsViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ShopItemData curr = shopItemDataList.get(position);

        holder.itemNameView.setText(curr.getName() + "(" + curr.getPricings().get(0).getType() + ")");
        holder.itemPriceView.setText("₹" + curr.getPricings().get(0).getPrice());

        if(curr.getImageURL() != null && curr.getImageURL().length() > 0){
            Glide.with(context)
                    .load(curr.getImageURL())
                    .into(holder.itemImageView);
        }
        else {
            holder.itemImageView.setVisibility(View.GONE);
        }

        if(!curr.getVeg()){
            holder.isVegView.setImageResource(R.drawable.ic_nonveg);
        }

        if(!showAddButton){
            holder.addButton.setVisibility(View.GONE);
        }
        else {
            holder.addButton.setVisibility(View.VISIBLE);
        }

        Cart cart = Cart.getInstance(context);
        if(cart != null){
            ShopItemData cartItem =  cart.isPresentInCart(curr.get_id());
            if(cartItem != null && !curr.getAvailable()){
                holder.addButton.setText("Unavailable");
                holder.addButton.setCheckable(false);
                holder.addButton.setFocusable(false);

                cart.removeFromCart(context, curr.get_id());
                onAddButtonInterface.removeTheBatch();
                return;
            }
            else if(!curr.getAvailable()){
                holder.addButton.setText("Unavailable");
                holder.addButton.setCheckable(false);
                holder.addButton.setFocusable(false);

                return;
            }
            else if(cartItem != null){
                holder.addButtonView.setVisibility(View.GONE);
                holder.quantityView.setVisibility(View.VISIBLE);
                holder.quantityTextView.setText(String.valueOf(cartItem.getQuantity()));

                holder.itemNameView.setText(curr.getName() + "(" + curr.getPricings().get(0).getType() + ")");
                holder.itemPriceView.setText("₹" + curr.getPricings().get(0).getPrice());

            }
            else {
                holder.addButtonView.setVisibility(View.VISIBLE);
                holder.quantityView.setVisibility(View.GONE);
            }
        }
        else {
            holder.addButtonView.setVisibility(View.VISIBLE);
            holder.quantityView.setVisibility(View.GONE);
        }


        holder.increaseQuantityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cart.updateAnItem(context,curr.get_id(),1);
                notifyItemChanged(position);
            }
        });

        holder.decreaseQuantityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cart.updateAnItem(context,curr.get_id(),-1);
                onAddButtonInterface.removeTheBatch();
                notifyItemChanged(position);
            }
        });
        
        holder.addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(onAddButtonInterface.addButtonPressed(curr, holder.getAdapterPosition())){
                    
                }
            }
        });

    }

    public void replaceItems(List<ShopItemData> newItems){
        shopItemDataList = newItems;
        notifyDataSetChanged();
    }

    public void updateTheUI(int posi){
        notifyItemChanged(posi);
    }

    @Override
    public int getItemCount() {
        return shopItemDataList.size();
    }

    class ShopMenuItemsViewHolder extends RecyclerView.ViewHolder{

        public TextView itemNameView,itemPriceView,quantityTextView;
        public ImageView itemImageView,isVegView;
        public MaterialButton addButton;
        public LinearLayout addButtonView,quantityView, increaseQuantityButton, decreaseQuantityButton;

        public ShopMenuItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNameView = itemView.findViewById(R.id.itemName);
            itemPriceView = itemView.findViewById(R.id.itemPrice);
            itemImageView = itemView.findViewById(R.id.itemImage);
            addButton = itemView.findViewById(R.id.addButton);
            isVegView = itemView.findViewById(R.id.isVeg);

            addButtonView = itemView.findViewById(R.id.addButtonView);
            quantityView = itemView.findViewById(R.id.quantityView);
            increaseQuantityButton = itemView.findViewById(R.id.increaseQuantity);
            decreaseQuantityButton = itemView.findViewById(R.id.decreaseQuantity);
            quantityTextView = itemView.findViewById(R.id.quantityText);

        }
    }
}


