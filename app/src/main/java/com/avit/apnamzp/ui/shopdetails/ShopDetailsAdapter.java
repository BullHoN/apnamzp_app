package com.avit.apnamzp.ui.shopdetails;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.avit.apnamzp.R;
import com.avit.apnamzp.models.shop.ShopCategoryData;
import com.avit.apnamzp.models.shop.ShopItemData;

import java.util.ArrayList;
import java.util.List;

public class ShopDetailsAdapter extends RecyclerView.Adapter<ShopDetailsAdapter.ShopDetailsViewHolder>{

    public interface onItemAdded{
      Boolean showDialog(ShopItemData shopItemData,int position);
      void removeTheBatch();
    };

    List<ShopCategoryData> shopCategoryDataList;
    Context context;
    private String TAG = "ShopDetailsAdapter";
    private onItemAdded onItemAddedInterface;
    public ShopMenuItemsAdapter adapter;

    public ShopDetailsAdapter(List<ShopCategoryData> shopCategoryDataList, Context context,onItemAdded onItemAddedInterface) {
        this.shopCategoryDataList = shopCategoryDataList;
        this.context = context;
        this.onItemAddedInterface = onItemAddedInterface;
    }

    @NonNull
    @Override
    public ShopDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_menu_list,parent,false);
        return new ShopDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopDetailsViewHolder holder, @SuppressLint("RecyclerView") int position) {

        ShopCategoryData curr = shopCategoryDataList.get(position);

        if(curr.expanded == null){
            curr.setExpanded(true);
        }

        holder.categoryNameView.setText(curr.getCategoryName());

        holder.menuItemsListView.setLayoutManager(new LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false));
        adapter = new ShopMenuItemsAdapter(context, shopCategoryDataList.get(position).getShopItemDataList(), new ShopMenuItemsAdapter.onAddButtonInterface() {
            @Override
            public Boolean addButtonPressed(ShopItemData shopItemData,int pos) {
                return onItemAddedInterface.showDialog(shopItemData,holder.getAdapterPosition());
            }

            @Override
            public void removeTheBatch() {
                onItemAddedInterface.removeTheBatch();
            }
        });
        holder.menuItemsListView.setAdapter(adapter);


        holder.menuItemsListView.setVisibility(curr.getExpanded() ? View.VISIBLE : View.GONE);

    }

    public ShopMenuItemsAdapter getMenuItemsAdapter(){
        return adapter;
    }

    @Override
    public int getItemCount() {
        return shopCategoryDataList.size();
    }

    public void setItems(ArrayList<ShopCategoryData> list){
        shopCategoryDataList = list;
        notifyDataSetChanged();
    }

    class ShopDetailsViewHolder extends RecyclerView.ViewHolder{

        public RecyclerView menuItemsListView;
        public LinearLayout headerTitleView;
        public TextView categoryNameView;

        public ShopDetailsViewHolder(@NonNull View itemView) {
            super(itemView);

            menuItemsListView = itemView.findViewById(R.id.menuItemsList);
            headerTitleView = itemView.findViewById(R.id.header_title);
            categoryNameView = itemView.findViewById(R.id.categoryName);

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

