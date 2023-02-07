package com.avit.apnamzp.ui.shopdetails;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avit.apnamzp.models.network.NetworkResponse;
import com.avit.apnamzp.models.offer.OfferItem;
import com.avit.apnamzp.models.shop.ShopCategoryData;
import com.avit.apnamzp.models.shop.ShopData;
import com.avit.apnamzp.network.NetworkApi;
import com.avit.apnamzp.network.RetrofitClient;
import com.avit.apnamzp.utils.DisplayMessage;
import com.avit.apnamzp.utils.ErrorUtils;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ShopDetailsViewModel extends ViewModel {
    public ArrayList<ShopCategoryData> shopCategoryDataArrayList;
    private MutableLiveData<ArrayList<ShopCategoryData>> mutableLiveData;
    private MutableLiveData<ShopData> mutableShopLiveData;
    private MutableLiveData<List<OfferItem>> offersMutableLiveData;
    private String TAG = "ShopDetailsViewModel";

    public ShopDetailsViewModel(){
        mutableLiveData = new MutableLiveData<>();
        shopCategoryDataArrayList = new ArrayList<>();
        mutableShopLiveData = new MutableLiveData<>();
        offersMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<OfferItem>> getOffersMutableLiveData() {
        return offersMutableLiveData;
    }

    public void getOffersOfShop(Context context, String shopId){
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkApi networkApi = retrofit.create(NetworkApi.class);

        Call<List<OfferItem>> call = networkApi.getOffersOfShop(shopId);
        call.enqueue(new Callback<List<OfferItem>>() {
            @Override
            public void onResponse(Call<List<OfferItem>> call, Response<List<OfferItem>> response) {
                if(!response.isSuccessful()){
                    NetworkResponse errorResponse = ErrorUtils.parseErrorResponse(response);

                    DisplayMessage.errorMessage(context,errorResponse.getDesc(),Toasty.LENGTH_SHORT);
                    return;
                }

                offersMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<OfferItem>> call, Throwable t) {
                DisplayMessage.errorMessage(context,t.getMessage(),Toasty.LENGTH_SHORT);
            }
        });

    }

    public void setShopData(ShopData shopData){
        mutableShopLiveData.setValue(shopData);
    }

    public void getShopData(Context context,String shopId){
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkApi networkApi = retrofit.create(NetworkApi.class);

        Call<ShopData> call = networkApi.getShop(shopId);
        call.enqueue(new Callback<ShopData>() {
            @Override
            public void onResponse(Call<ShopData> call, Response<ShopData> response) {
                if(!response.isSuccessful()){
                    NetworkResponse errorResponse = ErrorUtils.parseErrorResponse(response);
                    DisplayMessage.errorMessage(context,errorResponse.getDesc(),Toasty.LENGTH_SHORT);
                    return;
                }

                mutableShopLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<ShopData> call, Throwable t) {
                DisplayMessage.errorMessage(context,t.getMessage(),Toasty.LENGTH_SHORT);
            }
        });

    }

    public MutableLiveData<ShopData> getMutableShopLiveData() {
        return mutableShopLiveData;
    }

    public void getDataFromServer(Context context, String itemsID){
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkApi networkApi = retrofit.create(NetworkApi.class);

        Call<ArrayList<ShopCategoryData>> call = networkApi.getShopData(itemsID);
        call.enqueue(new Callback<ArrayList<ShopCategoryData>>() {
            @Override
            public void onResponse(Call<ArrayList<ShopCategoryData>> call, Response<ArrayList<ShopCategoryData>> response) {

                if(!response.isSuccessful()){
                    NetworkResponse errorResponse = ErrorUtils.parseErrorResponse(response);
                    DisplayMessage.errorMessage(context,errorResponse.getDesc(),Toasty.LENGTH_SHORT);
                    return;
                }

                shopCategoryDataArrayList = response.body();
                mutableLiveData.setValue(shopCategoryDataArrayList);
            }

            @Override
            public void onFailure(Call<ArrayList<ShopCategoryData>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                DisplayMessage.errorMessage(context,t.getMessage(),Toasty.LENGTH_SHORT);
            }
        });

    }

    public MutableLiveData<ArrayList<ShopCategoryData>> getMutableLiveData() {
        return mutableLiveData;
    }
}
