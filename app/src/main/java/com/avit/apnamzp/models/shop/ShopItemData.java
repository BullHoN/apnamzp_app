package com.avit.apnamzp.models.shop;

import java.util.List;

public class ShopItemData {
    private String name;
    private List<ShopPricingData> pricings;
    private String imageURL;

    public ShopItemData(String name, List<ShopPricingData> pricings, String imageURL) {
        this.name = name;
        this.pricings = pricings;
        this.imageURL = imageURL;
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
