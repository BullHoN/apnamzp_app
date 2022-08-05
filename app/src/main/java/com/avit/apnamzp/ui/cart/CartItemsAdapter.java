package com.avit.apnamzp.ui.cart;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Paint;
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
import com.avit.apnamzp.utils.PrettyStrings;

import java.util.List;

import es.dmoral.toasty.Toasty;

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
        holder.priceView.setText(PrettyStrings.getPriceInRupees(curr.getPricings().get(0).getPrice()));
        holder.quantityView.setText(String.valueOf(curr.getQuantity()));

        Cart cart = Cart.getInstance(context);

        holder.increasePriceTextView.setPaintFlags(holder.increasePriceTextView.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        float increasedPercentage = cart.getShopData().getIncreaseDisplayPricePercentage() * 0.01f;
        int increasedPrice = Integer.parseInt(curr.getPricings().get(0).getPrice()) + Math.round(Integer.parseInt(curr.getPricings().get(0).getPrice()) * increasedPercentage);
        holder.increasePriceTextView.setText(PrettyStrings.getPriceInRupees((increasedPrice)));

        holder.increaseQuantityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                cart.updateAnItem(context,curr.get_id(),1);
                updateBadgeInterface.updateBadge(cart.getCartSize());
                notifyItemChanged(position);
            }
        });

        holder.decreaseQuantityButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                int priceAboveOnOffer = 0;
                if(cart.getAppliedOffer() != null){
                    priceAboveOnOffer = Integer.parseInt(cart.getAppliedOffer().getDiscountAbove());
                }

                cart.justUpdate(context,curr.get_id(),-1);

                if(cart.getTotalOfItems() <= priceAboveOnOffer){
                    Toasty.warning(context,"Remove The Offer To Decrease Quantity",Toasty.LENGTH_SHORT)
                            .show();
                    cart.justUpdate(context,curr.get_id(),1);
                    return;
                }

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

        public TextView nameView,priceView,quantityView,increasePriceTextView;
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
            increasePriceTextView = itemView.findViewById(R.id.increasedPrice);

        }
    }

}
