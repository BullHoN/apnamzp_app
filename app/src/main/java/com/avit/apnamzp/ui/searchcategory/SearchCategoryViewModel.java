package com.avit.apnamzp.ui.searchcategory;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avit.apnamzp.models.network.NetworkResponse;
import com.avit.apnamzp.models.shop.ShopData;
import com.avit.apnamzp.network.NetworkApi;
import com.avit.apnamzp.network.RetrofitClient;
import com.avit.apnamzp.utils.ErrorUtils;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class SearchCategoryViewModel extends ViewModel {

    MutableLiveData<List<ShopData>> shopsData;
    List<ShopData> shopDataList;
    private String TAG = "SearchCategoryViewModel";

    public SearchCategoryViewModel(){
        shopsData = new MutableLiveData<>();
    }

    public void getDataFromServer(Context context, String shopType){
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkApi networkApi = retrofit.create(NetworkApi.class);

        Call<ArrayList<ShopData>> call = networkApi.getShopsFromCategories(shopType);
        call.enqueue(new Callback<ArrayList<ShopData>>() {
            @Override
            public void onResponse(Call<ArrayList<ShopData>> call, Response<ArrayList<ShopData>> response) {

                if(response.isSuccessful()){
                    shopDataList = response.body();
                    Log.i(TAG, "onResponse: " + response.body().size());
                    shopsData.setValue(shopDataList);
                }
                else {
                    NetworkResponse errorResponse = ErrorUtils.parseErrorResponse(response);
                    Toasty.error(context,errorResponse.getDesc(),Toasty.LENGTH_SHORT)
                            .show();
                }

            }

            @Override
            public void onFailure(Call<ArrayList<ShopData>> call, Throwable t) {
                Log.i(TAG, "onFailure: " + t.getMessage());
            }
        });

    }

    public MutableLiveData<List<ShopData>> getShopsData() {
        return shopsData;
    }


}
