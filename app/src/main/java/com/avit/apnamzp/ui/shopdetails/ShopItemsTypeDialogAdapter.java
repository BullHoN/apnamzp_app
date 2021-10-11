package com.avit.apnamzp.ui.shopdetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avit.apnamzp.R;
import com.avit.apnamzp.models.shop.ShopPricingData;
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class ShopItemsTypeDialogAdapter extends RecyclerView.Adapter<ShopItemsTypeDialogAdapter.ShopItemsTypeDialogViewHolder>{

    public interface onPriceSelectedInterface {
        void selectedPrice(ShopPricingData shopPricingData);
    }

    private Context context;
    private List<ShopPricingData> shopPricingDataList;
    private onPriceSelectedInterface onPriceSelectedInterface;

    public ShopItemsTypeDialogAdapter(Context context, List<ShopPricingData> shopPricingDataList,onPriceSelectedInterface onPriceSelectedInterface) {
        this.context = context;
        this.shopPricingDataList = shopPricingDataList;
        this.onPriceSelectedInterface = onPriceSelectedInterface;
    }

    @NonNull
    @Override
    public ShopItemsTypeDialogViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shopipringitem,parent,false);
        return new ShopItemsTypeDialogViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ShopItemsTypeDialogViewHolder holder, int position) {
        ShopPricingData curr = shopPricingDataList.get(position);

        holder.typeView.setText(curr.getType());
        holder.priceView.setText("₹" + curr.getPrice());

        holder.chooseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onPriceSelectedInterface.selectedPrice(curr);
            }
        });

    }

    @Override
    public int getItemCount() {
        return shopPricingDataList.size();
    }

    public class ShopItemsTypeDialogViewHolder extends RecyclerView.ViewHolder{

        public TextView typeView,priceView;
        public MaterialButton chooseButton;

        public ShopItemsTypeDialogViewHolder(@NonNull View itemView) {
            super(itemView);
            typeView = itemView.findViewById(R.id.type);
            priceView = itemView.findViewById(R.id.price);
            chooseButton = itemView.findViewById(R.id.chooseButton);
        }
    }

}
