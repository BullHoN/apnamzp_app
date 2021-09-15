package com.avit.apnamzp.ui.shopdetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.avit.apnamzp.R;
import com.avit.apnamzp.models.shop.ShopCategoryData;

import java.util.List;

public class ShopDetailsAdapter extends RecyclerView.Adapter<ShopDetailsAdapter.ShopDetailsViewHolder>{

    List<ShopCategoryData> shopCategoryDataList;
    Context context;

    public ShopDetailsAdapter(List<ShopCategoryData> shopCategoryDataList, Context context) {
        this.shopCategoryDataList = shopCategoryDataList;
        this.context = context;
    }

    @NonNull
    @Override
    public ShopDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_menu_list,parent,false);
        return new ShopDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopDetailsViewHolder holder, int position) {

        ShopCategoryData curr = shopCategoryDataList.get(position);

        holder.menuItemsListView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        ShopMenuItemsAdapter adapter = new ShopMenuItemsAdapter(context,shopCategoryDataList.get(position).getShopItemDataList());
        holder.menuItemsListView.setAdapter(adapter);

        holder.menuItemsListView.setVisibility(curr.getExpanded() ? View.VISIBLE : View.GONE);

    }

    @Override
    public int getItemCount() {
        return shopCategoryDataList.size();
    }

    class ShopDetailsViewHolder extends RecyclerView.ViewHolder{

        public RecyclerView menuItemsListView;
        public LinearLayout headerTitleView;

        public ShopDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            menuItemsListView = itemView.findViewById(R.id.menuItemsList);
            headerTitleView = itemView.findViewById(R.id.header_title);

            headerTitleView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    ShopCategoryData curr = shopCategoryDataList.get(getAdapterPosition());
                    curr.reverseExpended();
                    notifyItemChanged(getAdapterPosition());
                }
            });

        }
    }
}

