package com.avit.apnamzp.ui.cart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avit.apnamzp.R;
import com.avit.apnamzp.localdb.Cart;
import com.avit.apnamzp.models.shop.ShopItemData;
import com.avit.apnamzp.ui.shopdetails.ShopMenuItemsAdapter;

import java.util.List;

public class CartItemsAdapter extends RecyclerView.Adapter<CartItemsAdapter.CartItemsViewHolder>{

    public interface updateBadge{
        void updateBadge(int val);
    }

    private Context context;
    private List<ShopItemData> cartItems;
    private updateBadge updateBadgeInterface;

    public CartItemsAdapter(Context context, List<ShopItemData> cartItems,updateBadge updateBadgeInterface) {
        this.context = context;
        this.cartItems = cartItems;
        this.updateBadgeInterface = updateBadgeInterface;
    }

    @NonNull
    @Override
    public CartItemsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_cart,parent,false);
        return new CartItemsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CartItemsViewHolder holder, @SuppressLint("RecyclerView") int position) {
        ShopItemData curr = cartItems.get(position);

        holder.nameView.setText(curr.getName() + "(" + curr.getPricings().get(0).getType() + ")");
        holder.priceView.setText("₹" + curr.getPricings().get(0).getPrice());
        holder.quantityView.setText(String.valueOf(curr.getQuantity()));

        Cart cart = Cart.getInstance(context);

        holder.increaseQuantityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cart.updateAnItem(context,curr.get_id(),1);
                notifyItemChanged(position);
            }
        });

        holder.decreaseQuantityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cart.justUpdate(context,curr.get_id(),-1);
                updateBadgeInterface.updateBadge(cart.getCartSize());
                notifyItemChanged(position);
            }
        });

        holder.removeButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cart.removeFromCart(context,curr.get_id());
                cartItems.remove(curr);
                updateBadgeInterface.updateBadge(cart.getCartSize());
                notifyDataSetChanged();
            }
        });

    }

    @Override
    public int getItemCount() {
        return cartItems.size();
    }

    public class CartItemsViewHolder extends RecyclerView.ViewHolder{

        public TextView nameView,priceView,quantityView;
        public LinearLayout increaseQuantityButton,decreaseQuantityButton;
        public ImageView removeButton;

        public CartItemsViewHolder(@NonNull View itemView) {
            super(itemView);

            nameView = itemView.findViewById(R.id.itemName);
            priceView = itemView.findViewById(R.id.itemPrice);
            increaseQuantityButton = itemView.findViewById(R.id.increaseQuantity);
            decreaseQuantityButton = itemView.findViewById(R.id.decreaseQuantity);
            removeButton = itemView.findViewById(R.id.removeButton);
            quantityView = itemView.findViewById(R.id.quantityText);

        }
    }

}
