package com.avit.apnamzp.models;

public class BannerData {
    private String imageURL;
    private String action;
    private String shopId;

    public BannerData(String imageURL, String action, String shopId) {
        this.imageURL = imageURL;
        this.action = action;
        this.shopId = shopId;
    }

    public BannerData(String imageURL) {
        this.imageURL = imageURL;
    }

    public String getAction() {
        return action;
    }

    public String getShopId() {
        return shopId;
    }

    public String getImageURL() {
        return imageURL;
    }
}
