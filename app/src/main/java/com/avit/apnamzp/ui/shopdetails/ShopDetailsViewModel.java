package com.avit.apnamzp.ui.shopdetails;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avit.apnamzp.models.network.NetworkResponse;
import com.avit.apnamzp.models.shop.ShopCategoryData;
import com.avit.apnamzp.network.NetworkApi;
import com.avit.apnamzp.network.RetrofitClient;
import com.avit.apnamzp.utils.ErrorUtils;

import java.util.ArrayList;
import java.util.Calendar;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ShopDetailsViewModel extends ViewModel {
    public ArrayList<ShopCategoryData> shopCategoryDataArrayList;
    private MutableLiveData<ArrayList<ShopCategoryData>> mutableLiveData;
    private String TAG = "ShopDetailsViewModel";

    public ShopDetailsViewModel(){
        mutableLiveData = new MutableLiveData<>();
        shopCategoryDataArrayList = new ArrayList<>();
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
                    Toasty.error(context,errorResponse.getDesc(),Toasty.LENGTH_SHORT)
                            .show();
                    return;
                }

                shopCategoryDataArrayList = response.body();
                mutableLiveData.setValue(shopCategoryDataArrayList);
            }

            @Override
            public void onFailure(Call<ArrayList<ShopCategoryData>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
                Toasty.error(context,t.getMessage(),Toasty.LENGTH_SHORT)
                        .show();
            }
        });

    }

    public MutableLiveData<ArrayList<ShopCategoryData>> getMutableLiveData() {
        return mutableLiveData;
    }
}
