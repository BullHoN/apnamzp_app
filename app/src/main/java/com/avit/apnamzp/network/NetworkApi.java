package com.avit.apnamzp.network;

import com.avit.apnamzp.models.shop.ShopCategoryData;
import com.avit.apnamzp.models.shop.ShopData;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface NetworkApi {
     String SERVER_URL = "http://192.168.1.3:5000/";

     @GET("/category/{shopType}")
     Call<ArrayList<ShopData>> getShopsFromCategories(@Path("shopType") String shopType);

     @GET("/getShopItems/{itemsId}")
     Call<ArrayList<ShopCategoryData>> getShopData(@Path("itemsId") String itemsId);

}
