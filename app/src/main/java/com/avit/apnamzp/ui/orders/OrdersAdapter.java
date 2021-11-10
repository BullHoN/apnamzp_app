package com.avit.apnamzp.ui.orders;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.avit.apnamzp.R;
import com.avit.apnamzp.models.order.OrderItem;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class OrdersAdapter extends RecyclerView.Adapter<OrdersAdapter.OrdersViewHolder>{

    public interface openOrderDetailsInterface{
        void openOrderDetails(OrderItem orderItem);
    }

    private Context context;
    private List<OrderItem> orderItemsList;
    private String TAG = "OrdersFragment";
    private openOrderDetailsInterface openOrderDetailsInterface;

    public OrdersAdapter(Context context, List<OrderItem> orderItemsList,openOrderDetailsInterface openOrderDetailsInterface) {
        this.context = context;
        this.orderItemsList = orderItemsList;
        this.openOrderDetailsInterface = openOrderDetailsInterface;
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

//        Log.i(TAG, "onBindViewHolder: " + curr.getShopData());
        holder.shopNameView.setText(curr.getShopData().getShopName());
        holder.priceView.setText("₹" + curr.getBillingDetails().getTotalPay() + ".00");
        holder.orderNoView.setText("Order No- " + curr.get_id());

        Glide.with(context)
                .load(curr.getShopData().getBannerImage())
                .into(holder.shopImage);

        String messages[] = {"Order Placed","Order Confirmed","Order In Preparetion","Rider Assign",
        "Rider Reached Shop","Rider On The Way","Order Arrived","Order Delivered","Order Cancelled"};

        holder.orderStatusView.setText(messages[curr.getOrderStatus()]);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openOrderDetailsInterface.openOrderDetails(curr);
            }
        });

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
        public CardView cardView;

        public OrdersViewHolder(@NonNull View itemView) {
            super(itemView);
            shopNameView = itemView.findViewById(R.id.shopName);
            orderNoView = itemView.findViewById(R.id.orderId);
            priceView = itemView.findViewById(R.id.totalPrice);
            orderStatusView = itemView.findViewById(R.id.orderStatus);
            shopImage = itemView.findViewById(R.id.shopImage);
            cardView = itemView.findViewById(R.id.cardView);
        }
    }
}
