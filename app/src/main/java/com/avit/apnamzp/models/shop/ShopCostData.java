package com.avit.apnamzp.models.shop;

public class ShopCostData {
    private String minOrderPrice;
    private String minFreeDeliveryPrice;

    public ShopCostData(String minOrderPrice, String minFreeDeliveryPrice) {
        this.minOrderPrice = minOrderPrice;
        this.minFreeDeliveryPrice = minFreeDeliveryPrice;
    }

    public String getMinOrderPrice() {
        return minOrderPrice;
    }

    public String getMinFreeDeliveryPrice() {
        return minFreeDeliveryPrice;
    }
}
