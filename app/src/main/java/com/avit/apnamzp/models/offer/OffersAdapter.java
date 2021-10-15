package com.avit.apnamzp.models.offer;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avit.apnamzp.R;

import java.util.List;

public class OffersAdapter extends RecyclerView.Adapter<OffersAdapter.OffersViewHolder>{

    private Context context;
    private List<OfferItem> offerItemList;

    public OffersAdapter(Context context, List<OfferItem> offerItemList) {
        this.context = context;
        this.offerItemList = offerItemList;
    }

    @NonNull
    @Override
    public OffersViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_offer,parent,false);
        return  new OffersViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull OffersViewHolder holder, int position) {

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

        public OffersViewHolder(@NonNull View itemView) {
            super(itemView);
        }
    }

}
