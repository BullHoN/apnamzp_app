package com.avit.apnamzp.ui.offers;

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
import com.google.android.material.button.MaterialButton;

import java.util.List;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.OffersViewHolder>{

    public interface applyOfferInterface{
        void applyOffer(OfferItem offerItem);
        void openShop(String shopId);
    }

    private Context context;
    private List<OfferItem> offerItemList;
    private Boolean displayApplyButton;
    private applyOfferInterface applyOfferInterface;

    public OffersAdapter(Context context,Boolean displayApplyButton ,List<OfferItem> offerItemList,applyOfferInterface applyOfferInterface) {
        this.context = context;
        this.offerItemList = offerItemList;
        this.displayApplyButton = displayApplyButton;
        this.applyOfferInterface = applyOfferInterface;
    }

    @NonNull
    @Override
    public OffersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_offer,parent,false);
        return  new OffersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OffersViewHolder holder, int position) {
        OfferItem offerItem = offerItemList.get(position);
        String offerType = offerItem.getOfferType();

        if(offerType.equals("percent")){
            holder.discountTypeView.setText(offerItem.getDiscountPercentage() + "% Discount");

            String descOfOffer = "Get " + offerItem.getDiscountPercentage() + "% Discount on your Orders above Rs. " + offerItem.getDiscountAbove() + "\n"
                    + "Maximum Discount Cap: Rs" + offerItem.getMaxDiscount();
            holder.offerConditionsView.setText(descOfOffer);

        }
        else if(offerType.equals("flat")){
            holder.discountTypeView.setText("Flat ₹" + offerItem.getDiscountAmount() + " Discount");

            String descOfOffer = "Get Flat " + offerItem.getDiscountAmount() + " Rupees Discount on orders above Rs." + offerItem.getDiscountAbove();
            holder.offerConditionsView.setText(descOfOffer);

        }

        holder.codeView.setText(offerItem.getCode());

        if(displayApplyButton){
            holder.applyButton.setVisibility(View.VISIBLE);
            holder.applyButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    applyOfferInterface.applyOffer(offerItem);
                }
            });
        }
        else {
            holder.offerViewContainer.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    applyOfferInterface.openShop(offerItem.getShopId());
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return offerItemList.size();
    }

    public void changeValues(List<OfferItem> newItems){
        offerItemList = newItems;
        notifyDataSetChanged();
    }

    class OffersViewHolder extends RecyclerView.ViewHolder{

        public TextView discountTypeView,offerConditionsView,codeView;
        public MaterialButton applyButton;
        public ImageView offerImageView;
        public CardView offerViewContainer;

        public OffersViewHolder(@NonNull View itemView) {
            super(itemView);
            discountTypeView = itemView.findViewById(R.id.discountType);
            offerConditionsView = itemView.findViewById(R.id.offerConditions);
            codeView = itemView.findViewById(R.id.code);

            applyButton = itemView.findViewById(R.id.applyButton);
            offerImageView = itemView.findViewById(R.id.offerImage);

            offerViewContainer = itemView.findViewById(R.id.offer_view);
        }
    }

}
