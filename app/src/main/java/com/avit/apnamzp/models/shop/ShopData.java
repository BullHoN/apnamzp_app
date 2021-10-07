package com.avit.apnamzp.models.shop;

import com.avit.apnamzp.models.shop.ShopAddressData;
import com.avit.apnamzp.models.shop.ShopCostData;

public class ShopData {
    private String name;
    private String tagLine;
    private ShopCostData pricingDetails;
    private ShopAddressData addressData;
    private Boolean isOpen;
    private String averageDeliveryTime;
    private String bannerImage;
    private String averageRatings;
    private String shopType;
    private String reviews;
    private String menuItemsID;

    public ShopData(String shopName,Boolean isOpen) {
        this.name = shopName;
        this.isOpen = isOpen;
    }

    public ShopData(String shopName, String tagLine, ShopCostData pricingDetails, ShopAddressData addressData, Boolean isOpen, String averageDeliveryTime) {
        this.name = shopName;
        this.tagLine = tagLine;
        this.pricingDetails = pricingDetails;
        this.addressData = addressData;
        this.isOpen = isOpen;
        this.averageDeliveryTime = averageDeliveryTime;
    }

    public ShopData(String name, String tagLine, ShopCostData pricingDetails, ShopAddressData addressData, Boolean isOpen, String averageDeliveryTime, String bannerImage, String averageRatings, String shopType, String reviews, String menuItemsID) {
        this.name = name;
        this.tagLine = tagLine;
        this.pricingDetails = pricingDetails;
        this.addressData = addressData;
        this.isOpen = isOpen;
        this.averageDeliveryTime = averageDeliveryTime;
        this.bannerImage = bannerImage;
        this.averageRatings = averageRatings;
        this.shopType = shopType;
        this.reviews = reviews;
        this.menuItemsID = menuItemsID;
    }

    public String getMenuItemsID() {
        return menuItemsID;
    }

    public String getReviews() {
        return reviews;
    }

    public String getName() {
        return name;
    }

    public String getBannerImage() {
        return bannerImage;
    }

    public String getAverageRatings() {
        return averageRatings;
    }

    public String getShopType() {
        return shopType;
    }

    public String getShopName() {
        return name;
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
