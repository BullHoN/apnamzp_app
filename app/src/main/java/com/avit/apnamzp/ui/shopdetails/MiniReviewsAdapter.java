package com.avit.apnamzp.ui.shopdetails;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avit.apnamzp.R;
import com.avit.apnamzp.models.offer.OfferItem;
import com.avit.apnamzp.utils.PrettyStrings;

import java.util.List;

public class MiniReviewsAdapter extends RecyclerView.Adapter<MiniReviewsAdapter.MiniReviewsViewHolder>{

    private List<OfferItem> offerItemList;
    private Context context;

    public MiniReviewsAdapter(List<OfferItem> offerItemList, Context context) {
        this.offerItemList = offerItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public MiniReviewsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shop_details_offerts,parent,false);
        return new MiniReviewsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MiniReviewsViewHolder holder, int position) {
        OfferItem curr = offerItemList.get(position);

        if(curr.getOfferType().equals("flat")){
            holder.maxDiscountView.setText("Flat " + PrettyStrings.getPriceInRupees(curr.getDiscountAmount()) + " OFF");
            holder.offerCodeView.setText("Use code " + curr.getCode());
        }
        else if(curr.getOfferType().equals("percent")){
            holder.maxDiscountView.setText("UPTO " + PrettyStrings.getPriceInRupees(curr.getMaxDiscount()) + " OFF");
            holder.offerCodeView.setText("Use code " + curr.getCode());
        }
        else if(curr.getOfferType().equals("delivery")){
            holder.maxDiscountView.setText("FREE DELIVERY");
            holder.offerCodeView.setText("On Order Above " + PrettyStrings.getPriceInRupees(curr.getDiscountAbove()));
        }

    }

    @Override
    public int getItemCount() {
        return offerItemList.size();
    }

    public void replaceItems(List<OfferItem> newOfferItems){
        this.offerItemList.addAll(newOfferItems);
        notifyDataSetChanged();
    }

    public class MiniReviewsViewHolder extends RecyclerView.ViewHolder {

        public TextView maxDiscountView, offerCodeView;

        public MiniReviewsViewHolder(@NonNull View itemView) {
            super(itemView);

            maxDiscountView = itemView.findViewById(R.id.max_discount);
            offerCodeView = itemView.findViewById(R.id.offer_code);

        }
    }

}
