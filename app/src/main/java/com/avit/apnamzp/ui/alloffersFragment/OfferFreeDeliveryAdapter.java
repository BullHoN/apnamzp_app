package com.avit.apnamzp.ui.alloffersFragment;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avit.apnamzp.R;
import com.avit.apnamzp.models.offer.OfferItem;

import java.util.List;

public class OfferFreeDeliveryAdapter extends RecyclerView.Adapter<OfferFreeDeliveryAdapter.OfferFreeDeliveryViewHolder>{

    private List<OfferItem> offerItemList;
    private Context context;

    public OfferFreeDeliveryAdapter(List<OfferItem> offerItemList, Context context) {
        this.offerItemList = offerItemList;
        this.context = context;
    }

    @NonNull
    @Override
    public OfferFreeDeliveryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_category_offer,parent,false);
        return new OfferFreeDeliveryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OfferFreeDeliveryViewHolder holder, int position) {

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

        public OfferFreeDeliveryViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }
}
