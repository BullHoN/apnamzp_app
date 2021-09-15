package com.avit.apnamzp.ui.searchcategory;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avit.apnamzp.R;
import com.avit.apnamzp.models.shop.ShopData;
import com.google.android.material.card.MaterialCardView;

import java.util.List;

public class SearchCategoryAdapter extends RecyclerView.Adapter<SearchCategoryViewHolder>{

    public interface openShopDetails{
        void openShopDetails(String shopName,String shopCategory);
    }

    private List<ShopData> shopDataList;
    private Context context;
    private openShopDetails openShopDetailsInterface;

    public SearchCategoryAdapter(Context context,List<ShopData> shopDataList,openShopDetails openShopDetailsInterface) {
        this.shopDataList = shopDataList;
        this.context = context;
        this.openShopDetailsInterface = openShopDetailsInterface;
    }

    @NonNull
    @Override
    public SearchCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shopdetails,parent,false);
        return new SearchCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchCategoryViewHolder holder, int position) {
        ShopData curr = shopDataList.get(position);

        holder.nameView.setText(curr.getShopName());

        if(!curr.getOpen()){
            holder.nameView.setTextColor(context.getResources().getColor(R.color.secondaryTextColor));
            holder.closedBackView.setVisibility(View.VISIBLE);
            holder.closedTextView.setVisibility(View.VISIBLE);
        }

        holder.shopCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openShopDetailsInterface.openShopDetails(curr.getShopName(),"Zeher");
            }
        });

    }

    @Override
    public int getItemCount() {
        return shopDataList.size();
    }

    public void addToList(List<ShopData> newData){
        shopDataList.addAll(newData);
        notifyDataSetChanged();
    }

    public void replaceList(List<ShopData> newData){
        shopDataList = newData;
        notifyDataSetChanged();
    }

}

class SearchCategoryViewHolder extends RecyclerView.ViewHolder{

    public TextView nameView,closedTextView;
    public ImageView closedBackView;
    public MaterialCardView shopCardView;

    public SearchCategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        nameView = itemView.findViewById(R.id.shopName);
        closedBackView = itemView.findViewById(R.id.closed_back);
        closedTextView = itemView.findViewById(R.id.closed_text);
        shopCardView = itemView.findViewById(R.id.shop_card);
    }
}
