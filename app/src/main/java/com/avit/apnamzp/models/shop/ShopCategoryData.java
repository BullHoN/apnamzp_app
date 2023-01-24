package com.avit.apnamzp.models.shop;

import java.util.List;

public class ShopCategoryData {
    private String categoryName;
    private List<ShopItemData> shopItemDataList;
    public Boolean expanded;

    public ShopCategoryData(String categoryName, List<ShopItemData> shopItemDataList,boolean expanded) {
        this.categoryName = categoryName;
        this.shopItemDataList = shopItemDataList;
        this.expanded = expanded;
    }

    public ShopCategoryData(String categoryName, List<ShopItemData> shopItemDataList) {
        this.categoryName = categoryName;
        this.shopItemDataList = shopItemDataList;
        this.expanded = true;
    }

    public Boolean getExpanded() {
        return expanded;
    }

    public void reverseExpended() {
        this.expanded = !expanded;
    }

    public ShopCategoryData(String categoryName) {
        this.categoryName = categoryName;
    }

    public String getCategoryName() {
        return categoryName;
    }

    public List<ShopItemData> getShopItemDataList() {
        return shopItemDataList;
    }

    public void setExpanded(Boolean expanded) {
        this.expanded = expanded;
    }

    @Override
    public String toString() {
        return "ShopCategoryData{" +
                "categoryName='" + categoryName + '\'' +
                ", expanded=" + expanded +
                '}';
    }
}

