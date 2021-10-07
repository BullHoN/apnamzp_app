package com.avit.apnamzp.ui.shopdetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avit.apnamzp.R;
import com.avit.apnamzp.models.shop.ShopItemData;
import com.bumptech.glide.Glide;

import java.util.List;

public class ShopMenuItemsAdapter extends RecyclerView.Adapter<ShopMenuItemsAdapter.ShopMenuItemsViewHolder>{

    private Context context;
    private List<ShopItemData> shopItemDataList;

    public ShopMenuItemsAdapter(Context context, List<ShopItemData> shopItemDataList) {
        this.context = context;
        this.shopItemDataList = shopItemDataList;
    }

    @NonNull
    @Override
    public ShopMenuItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.item_menu,parent,false);
        return new ShopMenuItemsViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopMenuItemsViewHolder holder, int position) {
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

    }

    @Override
    public int getItemCount() {
        return shopItemDataList.size();
    }

    class ShopMenuItemsViewHolder extends RecyclerView.ViewHolder{

        public TextView itemNameView,itemPriceView;
        public ImageView itemImageView;

        public ShopMenuItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNameView = itemView.findViewById(R.id.itemName);
            itemPriceView = itemView.findViewById(R.id.itemPrice);
            itemImageView = itemView.findViewById(R.id.itemImage);
        }
    }
}


