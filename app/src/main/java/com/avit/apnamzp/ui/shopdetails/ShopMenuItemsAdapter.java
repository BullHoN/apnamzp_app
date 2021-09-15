package com.avit.apnamzp.ui.shopdetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avit.apnamzp.R;
import com.avit.apnamzp.models.shop.ShopItemData;

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

        holder.itemNameView.setText(curr.getName());
        holder.itemPriceView.setText("₹" + curr.getPrice());

    }

    @Override
    public int getItemCount() {
        return shopItemDataList.size();
    }

    class ShopMenuItemsViewHolder extends RecyclerView.ViewHolder{

        public TextView itemNameView,itemPriceView;

        public ShopMenuItemsViewHolder(@NonNull View itemView) {
            super(itemView);
            itemNameView = itemView.findViewById(R.id.itemName);
            itemPriceView = itemView.findViewById(R.id.itemPrice);
        }
    }
}


