package com.avit.apnamzp.models.offer;

public class OfferItem {
    private String code;
    private String offerType;
    private Boolean isApnaMzpDiscount;
    private String shopName;
    private String discountAbove;

    // Type 1: "percent" Discount
    private String discountPercentage;
    private String maxDiscount;

    // Type 2: "flat" Discount
    private String discountAmount;

    public String getCode() {
        return code;
    }

    public String getOfferType() {
        return offerType;
    }

    public Boolean getApnaMzpDiscount() {
        return isApnaMzpDiscount;
    }

    public String getShopName() {
        return shopName;
    }

    public String getDiscountAbove() {
        return discountAbove;
    }

    public String getDiscountPercentage() {
        return discountPercentage;
    }

    public String getMaxDiscount() {
        return maxDiscount;
    }

    public String getDiscountAmount() {
        return discountAmount;
    }
}
