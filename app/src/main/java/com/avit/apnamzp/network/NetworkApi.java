package com.avit.apnamzp.network;

import com.avit.apnamzp.localdb.Cart;
import com.avit.apnamzp.models.ReviewData;
import com.avit.apnamzp.models.User;
import com.avit.apnamzp.models.cart.CartItemData;
import com.avit.apnamzp.models.offer.OfferItem;
import com.avit.apnamzp.models.order.OrderItem;
import com.avit.apnamzp.models.shop.ShopCategoryData;
import com.avit.apnamzp.models.shop.ShopData;
import com.avit.apnamzp.ui.cart.GetDistanceResponse;

import java.util.ArrayList;
import java.util.List;
import java.util.Queue;

import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NetworkApi {
     String SERVER_URL = "http://192.168.1.3:5000/";
//     String SERVER_URL = "http://da22-2401-4900-1f3b-392b-189b-5688-d508-410c.ngrok.io";

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

     @GET("/checkUserExists")
     Call<Boolean> doesUsersExists(@Query("phoneNo") String phoneNo);

     @GET("/verifyOtp")
     Call<Boolean> verifyOtp(@Query("phoneNo") String phoneNo,@Query("otp") String otp);

     @GET("/sendOtp")
     Call<ResponseBody> sendOtp(@Query("phoneNo") String phoneNo);

     @POST("/registerUser")
     Call<Boolean> registerUser(@Body User userData);

     @GET("/login")
     Call<Boolean> login(@Query("phoneNo") String phoneNo,@Query("password") String password);

     @POST("/checkout")
     Call<Boolean> checkout(@Body OrderItem orderItem);

     @GET("/user/getOrders")
     Call<List<OrderItem>> getOrders(@Query("userId") String userId);

     @POST("/user_routes/updateFCM")
     Call<ResponseBody> updateFCMToken(@Body User user,@Query("user_type") String user_type);

     @GET("/user/getOrder")
     Call<OrderItem> getOrderById(@Query("order_id") String orderId);

}
