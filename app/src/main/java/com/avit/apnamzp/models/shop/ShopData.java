package com.avit.apnamzp.models.shop;

import com.avit.apnamzp.models.shop.ShopAddressData;
import com.avit.apnamzp.models.shop.ShopCostData;

public class ShopData {
    private String shopName;
    private String tagLine;
    private ShopCostData pricingDetails;
    private ShopAddressData addressData;
    private Boolean isOpen;
    private String averageDeliveryTime;
    private String mainImageURL;
    private String averageRatings;
    private String shopCategory;

    public ShopData(String shopName,Boolean isOpen) {
        this.shopName = shopName;
        this.isOpen = isOpen;
    }

    public ShopData(String shopName, String tagLine, ShopCostData pricingDetails, ShopAddressData addressData, Boolean isOpen, String averageDeliveryTime) {
        this.shopName = shopName;
        this.tagLine = tagLine;
        this.pricingDetails = pricingDetails;
        this.addressData = addressData;
        this.isOpen = isOpen;
        this.averageDeliveryTime = averageDeliveryTime;
    }

    public String getShopName() {
        return shopName;
    }

    public String getTagLine() {
        return tagLine;
    }

    public ShopCostData getPricingDetails() {
        return pricingDetails;
    }

    public ShopAddressData getAddressData() {
        return addressData;
    }

    public Boolean getOpen() {
        return isOpen;
    }

    public String getAverageDeliveryTime() {
        return averageDeliveryTime;
    }
}
