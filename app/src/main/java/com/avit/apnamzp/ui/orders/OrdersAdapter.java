package com.avit.apnamzp.ui.orders;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avit.apnamzp.R;
import com.avit.apnamzp.models.order.OrderItem;

import java.util.ArrayList;
import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder>{

    private Context context;
    private List<OrderItem> orderItemsList;

    public OrdersAdapter(Context context, List<OrderItem> orderItemsList) {
        this.context = context;
        this.orderItemsList = orderItemsList;
    }

    @NonNull
    @Override
    public OrdersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_orderdisplay,parent,false);
        return new OrdersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrdersViewHolder holder, int position) {
        OrderItem curr = orderItemsList.get(position);



    }

    @Override
    public int getItemCount() {
        return orderItemsList.size();
    }

    public void replaceItems(List<OrderItem> newItems){
        orderItemsList = newItems;
        notifyDataSetChanged();
    }

    public class OrdersViewHolder extends RecyclerView.ViewHolder {

        public TextView shopNameView,orderNoView,priceView,orderStatusView;
        public ImageView shopImage;

        public OrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            shopNameView = itemView.findViewById(R.id.shopName);
            orderNoView = itemView.findViewById(R.id.orderId);
            priceView = itemView.findViewById(R.id.totalPrice);
            orderStatusView = itemView.findViewById(R.id.orderStatus);
            shopImage = itemView.findViewById(R.id.shopImage);

        }
    }
}
