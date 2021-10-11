package com.avit.apnamzp.localdb;

import android.content.Context;
import android.content.SharedPreferences;

import com.avit.apnamzp.models.shop.ShopItemData;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

public class Cart {

    private String shopName;
    private String shopID;
    private List<ShopItemData> cartItems;

    public Cart(Context context,String shopName,String shopID,List<ShopItemData> shopItemDataList){
        this.shopName = shopName;
        this.shopID = shopID;
        this.cartItems = shopItemDataList;
        saveToSharedPref(context);
    }

    public Boolean insertToCart(Context context,String itemID,ShopItemData newCartItem){
        if(!itemID.equals(this.shopID)) return false;
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

    public ShopItemData isPresentInCart(String itemID){
        for(ShopItemData shopItemData : cartItems){
            if(shopItemData.get_id().equals(itemID)){
                return shopItemData;
            }
        }
        return null;
    }

    public int getTotalOfItems(){
        int total = 0;
        for(ShopItemData shopItemData : cartItems){
            total += shopItemData.getQuantity() * Integer.parseInt(shopItemData.getPricings().get(0).getPrice());
        }

        return total;
    }

    public int getCartSize(){
        return cartItems.size();
    }

    public void saveToSharedPref(Context context){
        Gson gson = new Gson();
        SharedPreferences sharedPreferences = context.getSharedPreferences(SharedPrefNames.SHAREDDB_NAME,Context.MODE_PRIVATE);
        SharedPreferences.Editor  editor = sharedPreferences.edit();

        String cartString = gson.toJson(this);
        editor.putString(SharedPrefNames.CART_NAME,cartString);
        editor.apply();
    }

    private static Cart cart;

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
