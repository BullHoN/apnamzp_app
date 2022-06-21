package com.avit.apnamzp.ui.cart;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avit.apnamzp.R;

import java.util.List;

public class CartItemsOnTheWayAdapter extends RecyclerView.Adapter<CartItemsOnTheWayAdapter.CartItemsOnTheWayViewHolder>{

    public interface ActionsOnTheWayInterface {
        void removeItemOnTheWay(String text);
    }

    private Context context;
    private List<String> itemsOnTheWay;
    private ActionsOnTheWayInterface actionsOnTheWayInterface;
    public static final String TAG = "CartItemsOnTheWayAdapter";

    public CartItemsOnTheWayAdapter(Context context, List<String> itemsOnTheWay, ActionsOnTheWayInterface actionsOnTheWayInterface) {
        this.context = context;
        this.itemsOnTheWay = itemsOnTheWay;
        this.actionsOnTheWayInterface = actionsOnTheWayInterface;
    }

    @NonNull
    @Override
    public CartItemsOnTheWayViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View root = LayoutInflater.from(context).inflate(R.layout.item_on_the_way,parent,false);
        return new CartItemsOnTheWayViewHolder(root);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemsOnTheWayViewHolder holder, int position) {
        String curr = itemsOnTheWay.get(position);

        holder.itemOnTheWayTextView.setText(curr);
        holder.removeItemOnTheWay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionsOnTheWayInterface.removeItemOnTheWay(curr);
            }
        });

    }

    @Override
    public int getItemCount() {
        return itemsOnTheWay.size();
    }

    public void updateItemsOnTheWay(List<String> newItemsOnTheWay){
        this.itemsOnTheWay = newItemsOnTheWay;
        notifyDataSetChanged();
    }

    public class CartItemsOnTheWayViewHolder extends RecyclerView.ViewHolder {

        public TextView itemOnTheWayTextView;
        public ImageButton removeItemOnTheWay;

        public CartItemsOnTheWayViewHolder(@NonNull View itemView) {
            super(itemView);
            itemOnTheWayTextView = itemView.findViewById(R.id.item_on_the_way_text);
            removeItemOnTheWay = itemView.findViewById(R.id.delete_items_on_the_way);
        }
    }
}
