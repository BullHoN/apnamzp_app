package com.avit.apnamzp.models.shop;

public class ShopCostData {
    private int minOrderPrice;
    private int minFreeDeliveryPrice;

    public ShopCostData(int minOrderPrice, int minFreeDeliveryPrice) {
        this.minOrderPrice = minOrderPrice;
        this.minFreeDeliveryPrice = minFreeDeliveryPrice;
    }

    public int getMinOrderPrice() {
        return minOrderPrice;
    }

    public int getMinFreeDeliveryPrice() {
        return minFreeDeliveryPrice;
    }
}
