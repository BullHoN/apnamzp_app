package com.avit.apnamzp.models.shop;

public class ShopItemData {
    private String name;
    private String price;

    public ShopItemData(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public String getPrice() {
        return price;
    }
}
