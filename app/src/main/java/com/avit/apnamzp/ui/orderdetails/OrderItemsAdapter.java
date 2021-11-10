package com.avit.apnamzp.ui.orderdetails;

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

public class OrderItemsAdapter extends RecyclerView.Adapter<OrderItemsAdapter.OrderItemsViewHolder>{

    private Context context;
    private List<ShopItemData> orderItems;

    public OrderItemsAdapter(Context context, List<ShopItemData> orderItems) {
        this.context = context;
        this.orderItems = orderItems;
    }

    @NonNull
    @Override
    public OrderItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_orderdetailsorderitems,parent,false);
        return new OrderItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderItemsViewHolder holder, int position) {
        ShopItemData curr = orderItems.get(position);

        holder.itemNameView.setText(curr.getName());
        holder.itemQuantity.setText(String.valueOf(curr.getQuantity()));
        holder.itemPriceView.setText("₹" + (Integer.valueOf(curr.getPricings().get(0).getPrice()) * curr.getQuantity()) + ".00");

    }

    @Override
    public int getItemCount() {
        return orderItems.size();
    }

    public class OrderItemsViewHolder extends RecyclerView.ViewHolder {

        public TextView itemNameView,itemQuantity,itemPriceView;

        public OrderItemsViewHolder(@NonNull View itemView) {
            super(itemView);

            itemNameView = itemView.findViewById(R.id.itemName);
            itemQuantity = itemView.findViewById(R.id.itemQuantity);
            itemPriceView = itemView.findViewById(R.id.itemPrice);

        }
    }
}
