package com.avit.apnamzp.ui.orderdetails;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avit.apnamzp.R;

public class OrderStatusAdapter extends RecyclerView.Adapter<OrderStatusAdapter.OrderStatusViewHolder>{

    private Context context;
    private int status;
    private String[] messages = {"Order Placed","Order Confirmed","Order In Preperation","Rider Assign","Rider Reached Shop","Rider On The Way","Order Delivered","Order Cancelled"};
    private String[] selfPickupMessages = {"Order Placed","Order Confirmed","Order In Preperation","Order Delivered","Order Cancelled"};
    private int size;
    private String cancelReason;
    private boolean isDeliveryService;

    public OrderStatusAdapter(Context context,int status){
        this.context = context;
        this.status = status;
        size = status == 7 ? 2 : 7;
    }

    public OrderStatusAdapter(Context context,int status,String cancelReason,boolean isDeliveryService){
        this.context = context;
        this.status = status;
        this.cancelReason = cancelReason;
        size = status == 7 ? 2 : 7;
        this.isDeliveryService = isDeliveryService;
        if(!isDeliveryService){
            size = 4;
        }
    }

    @NonNull
    @Override
    public OrderStatusViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_orderstatus,parent,false);
        return new OrderStatusViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OrderStatusViewHolder holder, int position) {

        holder.statusTitleView.setText(messages[position]);

        if(status == 7){

            if(position == 0){
                holder.statusImage.setImageResource(R.drawable.ic_check);
                holder.statusMessageView.setText("Completed");
            }
            else {
                holder.statusImage.setImageResource(R.drawable.ic_cancel);
                holder.statusMessageView.setText("Order Cancelled");
                holder.statusCancelMessage.setVisibility(View.VISIBLE);
                holder.statusCancelMessage.setText(cancelReason);
            }

            return;
        }



        if(position < status){
            holder.statusImage.setImageResource(R.drawable.ic_check);
            holder.statusMessageView.setText("Completed");
        }
        else if(position == status){
            if(status == 6){
                holder.statusImage.setImageResource(R.drawable.ic_check);
                holder.statusMessageView.setText("Completed");
            }
            else {
                holder.statusImage.setImageResource(R.drawable.ic_attention);
                holder.statusMessageView.setText("Working On It...");
            }
        }
        else {
            holder.statusImage.setImageResource(R.drawable.circle_back);
            holder.statusMessageView.setText("waiting...");
        }

        if(position == 3 && !isDeliveryService){
            holder.statusTitleView.setText("Order Received");
            if(status == 6){
                holder.statusImage.setImageResource(R.drawable.ic_check);
                holder.statusMessageView.setText("Completed");
            }
            else {
                holder.statusTitleView.setText("Order Received");
                holder.statusMessageView.setText("waiting...");
            }
        }

    }

    @Override
    public int getItemCount() {
        return size;
    }

    public class OrderStatusViewHolder extends RecyclerView.ViewHolder {

        public TextView statusTitleView, statusMessageView, statusCancelMessage;
        public ImageView statusImage;
        public RelativeLayout orderStatusContainer;

        public OrderStatusViewHolder(@NonNull View itemView) {
            super(itemView);
            statusTitleView = itemView.findViewById(R.id.statusTitle);
            statusMessageView = itemView.findViewById(R.id.statusMessage);
            statusImage = itemView.findViewById(R.id.statusImage);
            statusCancelMessage = itemView.findViewById(R.id.cancel_reason);
            orderStatusContainer = itemView.findViewById(R.id.order_status_container);
        }
    }
}
