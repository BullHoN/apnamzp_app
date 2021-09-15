package com.avit.apnamzp.ui.shopdetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avit.apnamzp.R;
import com.avit.apnamzp.models.shop.ShopItemData;

import java.util.List;

public class ShopMenuItemsAdapter extends RecyclerView.Adapter<ShopMenuItemsViewHolder>{

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

    }

    @Override
    public int getItemCount() {
        return shopItemDataList.size();
    }
}

class ShopMenuItemsViewHolder extends RecyclerView.ViewHolder{

    public ShopMenuItemsViewHolder(@NonNull View itemView) {
        super(itemView);
    }
}
