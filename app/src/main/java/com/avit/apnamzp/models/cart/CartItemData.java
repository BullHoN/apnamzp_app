package com.avit.apnamzp.models.cart;

public class CartItemData {
    private String name;
    private int price;
    private boolean isPickAndDrop;
    private int quantity;

    public CartItemData(){
        this.quantity = 1;
    }

    public CartItemData(String name,int quantity){
        this.name = name;
        this.quantity = quantity;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setPickAndDrop(boolean pickAndDrop) {
        isPickAndDrop = pickAndDrop;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getName() {
        return name;
    }

    public int getPrice() {
        return price;
    }

    public boolean isPickAndDrop() {
        return isPickAndDrop;
    }

    public int getQuantity() {
        return quantity;
    }

    @Override
    public String toString() {
        return "CartItemData{" +
                "name='" + name + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
