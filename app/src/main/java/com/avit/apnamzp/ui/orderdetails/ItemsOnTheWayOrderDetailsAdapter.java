package com.avit.apnamzp.ui.orderdetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avit.apnamzp.R;
import com.google.android.gms.maps.model.LatLng;

import java.util.List;

public class ItemsOnTheWayOrderDetailsAdapter extends RecyclerView.Adapter<ItemsOnTheWayOrderDetailsAdapter.ItemsOnTheWayOrderDetailsViewHolder>{

    private List<String> itemsOnTheWay;
    private Context context;

    public ItemsOnTheWayOrderDetailsAdapter(List<String> itemsOnTheWay, Context context) {
        this.itemsOnTheWay = itemsOnTheWay;
        this.context = context;
    }

    @NonNull
    @Override
    public ItemsOnTheWayOrderDetailsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_ion_the_way_order_details,parent,false);
        return new ItemsOnTheWayOrderDetailsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemsOnTheWayOrderDetailsViewHolder holder, int position) {
        String curr = itemsOnTheWay.get(position);
        holder.itemOnTheWayTextView.setText(curr);
    }

    @Override
    public int getItemCount() {
        return itemsOnTheWay.size();
    }

    public class ItemsOnTheWayOrderDetailsViewHolder extends RecyclerView.ViewHolder {

        public TextView itemOnTheWayTextView;

        public ItemsOnTheWayOrderDetailsViewHolder(@NonNull View itemView) {
            super(itemView);
            itemOnTheWayTextView = itemView.findViewById(R.id.item_on_the_way);
        }
    }
}
