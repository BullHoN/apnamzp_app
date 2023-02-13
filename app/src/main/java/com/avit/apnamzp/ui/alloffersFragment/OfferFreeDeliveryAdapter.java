package com.avit.apnamzp.ui.alloffersFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.avit.apnamzp.R;
import com.avit.apnamzp.models.offer.OfferItem;
import com.avit.apnamzp.utils.PrettyStrings;
import com.bumptech.glide.Glide;

import java.util.List;

public class OfferFreeDeliveryAdapter extends RecyclerView.Adapter<OfferFreeDeliveryAdapter.OfferFreeDeliveryViewHolder>{

    public interface applyOfferInterface{
        void openShop(String shopId);
    }

    private List<OfferItem> offerItemList;
    private Context context;
    private applyOfferInterface interfaceA;

    public OfferFreeDeliveryAdapter(List<OfferItem> offerItemList, Context context, applyOfferInterface interfaceA) {
        this.offerItemList = offerItemList;
        this.context = context;
        this.interfaceA = interfaceA;
    }

    @NonNull
    @Override
    public OfferFreeDeliveryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category_offer,parent,false);
        return new OfferFreeDeliveryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OfferFreeDeliveryViewHolder holder, int position) {
        OfferItem curr = offerItemList.get(position);

        holder.shopNameView.setText(curr.getShopName());
        holder.abovePriceView.setText("Above " + PrettyStrings.getPriceInRupees(curr.getDiscountAbove()));

        Glide.with(context)
                .load(curr.getBannerImage())
                .into(holder.shopImageView);

        holder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                interfaceA.openShop(curr.getShopId());
            }
        });

    }

    @Override
    public int getItemCount() {
        return offerItemList.size();
    }

    public void replaceList(List<OfferItem> newList){
        offerItemList = newList;
        notifyDataSetChanged();
    }

    public class  OfferFreeDeliveryViewHolder extends RecyclerView.ViewHolder {

        public ImageView shopImageView;
        public TextView shopNameView, abovePriceView;
        public CardView cardView;

        public OfferFreeDeliveryViewHolder(@NonNull View itemView) {
            super(itemView);
            shopNameView = itemView.findViewById(R.id.shop_name);
            shopImageView = itemView.findViewById(R.id.shop_image);
            abovePriceView = itemView.findViewById(R.id.above_price);

            cardView = itemView.findViewById(R.id.cardView);

        }
    }
}
