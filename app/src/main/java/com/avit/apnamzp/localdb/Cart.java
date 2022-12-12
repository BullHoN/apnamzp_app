package com.avit.apnamzp.localdb;

import android.content.Context;
import android.content.SharedPreferences;
import android.view.accessibility.AccessibilityNodeInfo;

import androidx.annotation.NonNull;

import com.avit.apnamzp.models.offer.OfferItem;
import com.avit.apnamzp.models.shop.ShopData;
import com.avit.apnamzp.models.shop.ShopItemData;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private static Cart cart;
    private String shopName;
    private String shopID;
    private ShopData shopData;
    private List<ShopItemData> cartItems;
    private OfferItem appliedOffer;
    private List<String> itemsOnTheWay;

    public Cart(Context context,String shopName, String shopID, ShopData shopData, List<ShopItemData> cartItems) {
        this.shopName = shopName;
        this.shopID = shopID;
        this.shopData = shopData;
        this.cartItems = cartItems;
        this.itemsOnTheWay = new ArrayList<>();
        saveToSharedPref(context);
    }

    public void replaceCart(Context context, String shopName, String shopID,ShopData shopData ,List<ShopItemData> shopItemDataList){
        this.shopName = shopName;
        this.shopID = shopID;
        this.cartItems = shopItemDataList;
        this.shopData = shopData;
        this.itemsOnTheWay = new ArrayList<>();
        appliedOffer = null;
        saveToSharedPref(context);
    }

    public void replaceCart(Context context, String shopName, String shopID,ShopData shopData ){
        this.shopName = shopName;
        this.shopID = shopID;
        this.shopData = shopData;
        this.itemsOnTheWay = new ArrayList<>();
        appliedOffer = null;
        saveToSharedPref(context);
    }

    public void addItemsOnTheWay(Context context,List<String> itemsOnTheWay){
        Cart cart = Cart.getInstance(context);
        cart.itemsOnTheWay = itemsOnTheWay;
        saveToSharedPref(context);
    }

    public List<String> getItemsOnTheWay() {
        return itemsOnTheWay;
    }

    public Boolean insertToCart(Context context, String itemID, ShopItemData newCartItem){
        if(this.shopID != null && !itemID.equals(this.shopID)) return false;
        cartItems.add(newCartItem);
        saveToSharedPref(context);
        return true;
    }

    public void removeFromCart(Context context,String itemID){
        ArrayList<ShopItemData> newCartItems = new ArrayList<>();
        for(ShopItemData shopItemData : cartItems){
            if(!shopItemData.get_id().equals(itemID)) newCartItems.add(shopItemData);
        }

        this.cartItems = newCartItems;
        saveToSharedPref(context);
    }

    public void updateAnItem(Context context,String itemID,int val){
        for(ShopItemData shopItemData : cartItems){
            if(shopItemData.get_id().equals(itemID)){
                if(shopItemData.quantity == 1 && val == -1){
                    removeFromCart(context,itemID);
                    return;
                }
                shopItemData.quantity += val;
                break;
            }
        }
        saveToSharedPref(context);
    }

    public void justUpdate(Context context,String itemId,int val){
        for(ShopItemData shopItemData : cartItems){
            if(shopItemData.get_id().equals(itemId)){
                if(shopItemData.quantity == 1 && val == -1){
                    return;
                }
                shopItemData.quantity += val;
                break;
            }
        }
        saveToSharedPref(context);
    }

    public ShopItemData isPresentInCart(String itemID){
        for(ShopItemData shopItemData : cartItems){
            if(shopItemData.get_id().equals(itemID)){
                return shopItemData;
            }
        }
        return null;
    }

    public ShopData getShopData(){
        return shopData;
    }

    public void clearCart(Context context){
        cartItems.clear();
        shopID = null;
        appliedOffer = null;

        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPrefNames.SHAREDDB_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        editor.putString(SharedPrefNames.CART_NAME,null);
        editor.apply();
    }

    public int getTotalOfItems(){
        int total = 0;
        for(ShopItemData shopItemData : cartItems){
            total += shopItemData.getQuantity() * Integer.parseInt(shopItemData.getPricings().get(0).getPrice());
        }

        return total;
    }

    public int getTotalPackingCharge(){
        int total = 0;
        for(ShopItemData shopItemData : cartItems){
            total += shopItemData.getQuantity() * Integer.parseInt(shopItemData.getTaxOrPackigingPrice());
        }

        return total;
    }

    public int getTotalTaxDeduction(){
        return (int) Math.ceil(((getTotalOfItems() - getTotalDiscount()) * Float.parseFloat(getShopData().getTaxPercentage()))/100);
    }

    public int getTotalDiscount(){
        int total = 0;
        for(ShopItemData shopItemData : cartItems){
            total += shopItemData.getQuantity() * Integer.parseInt(shopItemData.getDiscount());
        }

        return total;
    }

    public void removeAppliedOffer(Context context){
        appliedOffer = null;
        saveToSharedPref(context);
    }

    public OfferItem getAppliedOffer(){
        return appliedOffer;
    }

    public void setAppliedOffer(Context context,OfferItem offer){
        this.appliedOffer = offer;
        saveToSharedPref(context);
    }

    public int getCartSize(){
        return cartItems.size();
    }


    public void saveToSharedPref(@NonNull Context context){
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPrefNames.SHAREDDB_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor  editor = sharedPreferences.edit();

        String cartString = gson.toJson(this);
        editor.putString(SharedPrefNames.CART_NAME,cartString);
        editor.apply();
    }

    public static Cart getInstance(Context context){
        if(cart == null){
            Gson gson = new Gson();
            SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPrefNames.SHAREDDB_NAME,Context.MODE_PRIVATE);

            String cartString = sharedPreferences.getString(SharedPrefNames.CART_NAME,"");
            if(cartString.length() == 0) return null;

            cart = gson.fromJson(cartString,Cart.class);
        }

        return cart;
    }

    public String getShopName() {
        return shopName;
    }

    public String getShopID() {
        return shopID;
    }

    public List<ShopItemData> getCartItems() {
        return cartItems;
    }

    public static Cart getCart() {
        return cart;
    }
}
