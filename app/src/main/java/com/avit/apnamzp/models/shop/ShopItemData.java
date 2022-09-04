package com.avit.apnamzp.models.shop;

import android.content.ClipData;
import android.util.Log;

import java.util.List;

public class ShopItemData {
    private String name;
    private String _id;
    private List<ShopPricingData> pricings;
    private String imageURL;
    public int quantity;
    public String taxOrPackigingPrice;
    public String discount;
    public Boolean available;
    private Boolean isVeg;
    private ItemAvailableTimings availableTimings;

    public ShopItemData(ShopItemData shopItemData){
        this.name = shopItemData.name;
        this._id = shopItemData._id;
        this.pricings = shopItemData.pricings;
        this.imageURL = shopItemData.imageURL;
        this.quantity = shopItemData.quantity;
        this.taxOrPackigingPrice = shopItemData.taxOrPackigingPrice;
        this.discount = shopItemData.discount;
        this.available = shopItemData.available;
        this.isVeg = shopItemData.isVeg;
        this.availableTimings = shopItemData.availableTimings;
    }

    public ShopItemData(String name, String _id, List<ShopPricingData> pricings, String imageURL, int quantity, String taxOrPackigingPrice, String discount, Boolean available, Boolean isVeg, ItemAvailableTimings availableTimings) {
        this.name = name;
        this._id = _id;
        this.pricings = pricings;
        this.imageURL = imageURL;
        this.quantity = quantity;
        this.taxOrPackigingPrice = taxOrPackigingPrice;
        this.discount = discount;
        this.available = available;
        this.isVeg = isVeg;
        this.availableTimings = availableTimings;
    }

    public ShopItemData(String name, List<ShopPricingData> pricings, String imageURL) {
        this.name = name;
        this.pricings = pricings;
        this.imageURL = imageURL;
    }

    public ItemAvailableTimings getAvailableTimings() {
        return availableTimings;
    }

    public Boolean getAvailable() {
        return available;
    }

    public Boolean isAvailable(String currTime){
        if(getAvailableTimings() == null) return available;
        if(!available) return available;

        if(convertTo24Hours(currTime).compareTo(convertTo24Hours(getAvailableTimings().getFrom())) >= 0
        && convertTo24Hours(currTime).compareTo(convertTo24Hours(getAvailableTimings().getTo())) <= 0){
            return true;
        }

        return false;
    }

    public String convertTo24Hours(String time){
        int hours = Integer.parseInt(time.split(":")[0]);
        String minutes = time.split(":")[1].split(" ")[0];
        String unit = time.split(":")[1].split(" ")[1].toUpperCase();

        if(unit.equals("PM")){
            hours += 12;
        }

        return String.format("%02d",hours) + "" + minutes;
    }

    public Boolean getVeg() {
        return isVeg;
    }

    public String getDiscount() {
        return discount;
    }

    public String getTaxOrPackigingPrice() {
        return taxOrPackigingPrice;
    }

    public String get_id() {
        return _id;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setPricings(List<ShopPricingData> pricings) {
        this.pricings = pricings;
    }

    public String getImageURL() {
        return imageURL;
    }

    public String getName() {
        return name;
    }

    public List<ShopPricingData> getPricings() {
        return pricings;
    }
}
