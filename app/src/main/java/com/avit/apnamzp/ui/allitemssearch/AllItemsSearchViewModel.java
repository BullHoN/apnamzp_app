package com.avit.apnamzp.ui.allitemssearch;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avit.apnamzp.models.network.NetworkResponse;
import com.avit.apnamzp.models.shop.ShopData;
import com.avit.apnamzp.network.NetworkApi;
import com.avit.apnamzp.network.RetrofitClient;
import com.avit.apnamzp.utils.ErrorUtils;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class AllItemsSearchViewModel extends ViewModel {
    MutableLiveData<List<ShopData>> mutableLiveShopData;

    public AllItemsSearchViewModel(){
        mutableLiveShopData = new MutableLiveData<>();
    }

    public void getSearchDataFromServer(Context context, String query){
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkApi networkApi = retrofit.create(NetworkApi.class);

        Call<List<ShopData>> call = networkApi.getSearchResults(query);
        call.enqueue(new Callback<List<ShopData>>() {
            @Override
            public void onResponse(Call<List<ShopData>> call, Response<List<ShopData>> response) {

                if(!response.isSuccessful()){
                    NetworkResponse errorResponse = ErrorUtils.parseErrorResponse(response);
                    Toasty.error(context,errorResponse.getDesc(),Toasty.LENGTH_SHORT)
                            .show();
                    return;
                }

                mutableLiveShopData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<ShopData>> call, Throwable t) {
                Toasty.error(context,t.getMessage(),Toasty.LENGTH_SHORT)
                        .show();
            }
        });
    }

    public MutableLiveData<List<ShopData>> getMutableLiveShopData() {
        return mutableLiveShopData;
    }
}
