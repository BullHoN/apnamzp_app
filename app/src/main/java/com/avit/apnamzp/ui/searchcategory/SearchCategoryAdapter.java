package com.avit.apnamzp.ui.searchcategory;

import android.annotation.SuppressLint;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.avit.apnamzp.R;
import com.avit.apnamzp.models.shop.ShopData;
import com.avit.apnamzp.utils.PrettyStrings;
import com.bumptech.glide.Glide;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.card.MaterialCardView;
import com.google.android.material.imageview.ShapeableImageView;

import java.util.List;

public class SearchCategoryAdapter extends RecyclerView.Adapter<SearchCategoryViewHolder>{

    public interface openShopDetails{
        void openShopDetails(ShopData shopData);
    }

    private List<ShopData> shopDataList;
    private Context context;
    private openShopDetails openShopDetailsInterface;
    private String TAG = "SearchCategoryAdapter";

    public SearchCategoryAdapter(Context context,List<ShopData> shopDataList,openShopDetails openShopDetailsInterface) {
        this.shopDataList = shopDataList;
        this.context = context;
        this.openShopDetailsInterface = openShopDetailsInterface;
    }

    @NonNull
    @Override
    public SearchCategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_shopdetails,parent,false);
        return new SearchCategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull SearchCategoryViewHolder holder, int position) {
        ShopData curr = shopDataList.get(position);

        Log.i(TAG, "onBindViewHolder: " + curr.getOpen() + " " + curr.getName() + " " + curr.isAllowSelfPickup());
        if(!curr.getOpen()){
            holder.shopCardView.setAlpha(0.7f);
        }else {
            holder.shopCardView.setAlpha(1f);
        }

        holder.nameView.setText(curr.getShopName().trim());
        holder.tagLineView.setText(curr.getTagLine());
        holder.averageDeliveryTimeView.setText(curr.getAverageDeliveryTime());
//        holder.minOrderView.setText("₹" + curr.getPricingDetails().getMinOrderPrice());

//        if(curr.getShopTimings() != null && curr.getShopTimings().length() > 1){
//            holder.shopTimingsView.setVisibility(View.VISIBLE);
//            holder.shopTimingsView.setText("Shop Timings: " + curr.getShopTimings());
//        }

//        if(curr.getPricingDetails().getMinOrderPrice().equals("0")){
//            holder.minOrderContainer.setVisibility(View.GONE);
//            holder.minOrderDotView.setVisibility(View.GONE);
//        }

        Glide.with(context)
                .load(curr.getBannerImage())
                .into(holder.shopImageView);

        int total_reviews = Integer.parseInt(curr.getReviews());
        float rating = Float.parseFloat(curr.getAverageRatings());
        holder.noOfRatingView.setText(String.valueOf(rating));

        if(total_reviews < 10){
            holder.noOfRatingView.setText("- /");
            holder.ratingBackground.setBackgroundResource(R.drawable.no_rating_back);
        }
        else if(rating >= 4){
            holder.ratingBackground.setBackgroundResource(R.drawable.rating_background_good);
        }
        else if(rating >= 2){
            holder.ratingBackground.setBackgroundResource(R.drawable.rating_back_average);
        }
        else {
            holder.ratingBackground.setBackgroundResource(R.drawable.rating_back_bad);
        }

        int freeDeliveryPrice = Integer.parseInt(curr.getPricingDetails().getMinFreeDeliveryPrice());
        if(freeDeliveryPrice < 2000){
            holder.freeDeliveryChargeText.setVisibility(View.VISIBLE);
            if(freeDeliveryPrice == 0){
                holder.freeDeliveryChargeText.setText("FREE Delivery !!");
            }
            else {
                holder.freeDeliveryChargeText.setText("FREE Delivery On " + PrettyStrings.getPriceInRupees(freeDeliveryPrice));
            }
        }
        else {
            holder.freeDeliveryChargeText.setVisibility(View.GONE);
            holder.freeDeliveryChargeContainer.setVisibility(View.GONE);
        }
//            curr.getPricingDetails().getMinFreeDeliveryPrice()
//        holder.reviewsView.setText(curr.getReviews());

        if(!curr.getOpen()){
            holder.nameView.setTextColor(context.getResources().getColor(R.color.secondaryTextColor));
            holder.closedBackView.setVisibility(View.VISIBLE);
            holder.closedTextView.setVisibility(View.VISIBLE);
        }else {
            holder.nameView.setTextColor(context.getResources().getColor(R.color.black));
            holder.closedBackView.setVisibility(View.INVISIBLE);
            holder.closedTextView.setVisibility(View.INVISIBLE);
        }

        holder.shopCardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openShopDetailsInterface.openShopDetails(new ShopData(curr));
            }
        });

        if(curr.isNewShop() && curr.getOpen()){
            holder.newTagView.setVisibility(View.VISIBLE);
            holder.newTagShimmerLayout.startShimmer();
        }
        else if(curr.getShopDiscountTag() != null && !curr.getShopDiscountTag().equals("0") && curr.getOpen()){
            holder.offerContainer.setVisibility(View.VISIBLE);
            holder.offerTagView.setText(curr.getShopDiscountTag());
            holder.offerShimmerLayout.startShimmer();
        }
        else {
            holder.newTagView.setVisibility(View.GONE);
            holder.offerTagView.setVisibility(View.GONE);
        }

    }

    @Override
    public int getItemCount() {
        if(shopDataList == null) return 0;
        return shopDataList.size();
    }

    public void addToList(List<ShopData> newData){
        shopDataList.addAll(newData);
        notifyDataSetChanged();
    }

    public void replaceList(List<ShopData> newData){
        shopDataList = newData;
        notifyDataSetChanged();
    }

}

class SearchCategoryViewHolder extends RecyclerView.ViewHolder{

    public TextView nameView,closedTextView,tagLineView,averageDeliveryTimeView,
            noOfRatingView,reviewsView,notAcceptingOrders, shopTimingsView, offerTagView, freeDeliveryChargeText;
    public ShapeableImageView shopImageView,closedBackView;
    public LinearLayout shopCardView, newTagView;
    public ShimmerFrameLayout newTagShimmerLayout, offerShimmerLayout;
    public LinearLayout ratingBackground,reviewsBackground, offerContainer, freeDeliveryChargeContainer;

    public SearchCategoryViewHolder(@NonNull View itemView) {
        super(itemView);
        nameView = itemView.findViewById(R.id.shopName);
        closedBackView = itemView.findViewById(R.id.closed_back);
        closedTextView = itemView.findViewById(R.id.closed_text);
        shopCardView = itemView.findViewById(R.id.shop_card);
        tagLineView = itemView.findViewById(R.id.tagLine);
        averageDeliveryTimeView = itemView.findViewById(R.id.averageDeliveryTime);
//        minOrderView = itemView.findViewById(R.id.minOrder);
        noOfRatingView = itemView.findViewById(R.id.rating);
//        reviewsView = itemView.findViewById(R.id.reviews);
        ratingBackground = itemView.findViewById(R.id.ratingBackground);
//        reviewsBackground = itemView.findViewById(R.id.reviewsBackground);
        shopImageView = itemView.findViewById(R.id.shopImage);
        shopTimingsView = itemView.findViewById(R.id.shopTimings);

        notAcceptingOrders = itemView.findViewById(R.id.not_accepting_orders);
//        minOrderContainer = itemView.findViewById(R.id.minOrderContainer);
//        minOrderDotView = itemView.findViewById(R.id.minOrderDot);

        newTagView = itemView.findViewById(R.id.shop_new_tag);
        offerTagView = itemView.findViewById(R.id.offer_tag);
        newTagShimmerLayout = itemView.findViewById(R.id.new_tag_shimmer_view);

        offerContainer = itemView.findViewById(R.id.offer_container);
        offerShimmerLayout = itemView.findViewById(R.id.offer_shimmer_view);

        freeDeliveryChargeText = itemView.findViewById(R.id.free_delivery_Charge_text);
        freeDeliveryChargeContainer = itemView.findViewById(R.id.free_delivery_charge_container);
    }
}
