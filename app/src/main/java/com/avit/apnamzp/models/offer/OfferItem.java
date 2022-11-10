package com.avit.apnamzp.models.offer;

public class OfferItem {
    private String code;
    private String offerType;
    private Boolean isApnaMzpDiscount;
    private String shopName;
    private String shopId;
    private String discountAbove;

    // Type 1: "percent" Discount
    private String discountPercentage;
    private String maxDiscount;

    // Type 2: "flat" Discount
    private String discountAmount;

    public static OfferItem getDiscountOffer(String discountAmount,String discountAbove,String code){
        OfferItem offerItem = new OfferItem();
        offerItem.setOfferType("delivery");
        offerItem.setCode(code);
        offerItem.setMaxDiscount(discountAmount);
        offerItem.setDiscountAbove(discountAbove);

        return offerItem;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setOfferType(String offerType) {
        this.offerType = offerType;
    }

    public void setApnaMzpDiscount(Boolean apnaMzpDiscount) {
        isApnaMzpDiscount = apnaMzpDiscount;
    }

    public void setShopName(String shopName) {
        this.shopName = shopName;
    }

    public void setShopId(String shopId) {
        this.shopId = shopId;
    }

    public void setDiscountAbove(String discountAbove) {
        this.discountAbove = discountAbove;
    }

    public void setDiscountPercentage(String discountPercentage) {
        this.discountPercentage = discountPercentage;
    }

    public void setMaxDiscount(String maxDiscount) {
        this.maxDiscount = maxDiscount;
    }

    public void setDiscountAmount(String discountAmount) {
        this.discountAmount = discountAmount;
    }

    public String getShopId() {
        return shopId;
    }


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
