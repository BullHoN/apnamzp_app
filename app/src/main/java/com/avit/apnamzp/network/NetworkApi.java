package com.avit.apnamzp.network;

import com.avit.apnamzp.models.ReviewData;
import com.avit.apnamzp.models.offer.OfferItem;
import com.avit.apnamzp.models.shop.ShopCategoryData;
import com.avit.apnamzp.models.shop.ShopData;
import com.avit.apnamzp.ui.cart.GetDistanceResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NetworkApi {
     String SERVER_URL = "http://192.168.1.3:5000/";

     @GET("/category/{shopType}")
     Call<ArrayList<ShopData>> getShopsFromCategories(@Path("shopType") String shopType);

     @GET("/getShopItems/{itemsId}")
     Call<ArrayList<ShopCategoryData>> getShopData(@Path("itemsId") String itemsId);

     @GET("/getDistance")
     Call<GetDistanceResponse> getDistance(@Query("destinations") String destinations, @Query("origins") String origins, @Query("key") String key);

     @GET("/getOffers")
     Call<List<OfferItem>> getOffers(@Query("onlyAdmin") Boolean onlyAdmin,@Query("shopName") String shopName);

     @GET("/search")
     Call<List<ShopData>> getSearchResults(@Query("query") String query);

     @GET("/reviews")
     Call<List<ReviewData>> getReviews(@Query("shopName") String shopName);

}
