package com.avit.apnamzp.ui.orders;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avit.apnamzp.models.order.OrderItem;
import com.avit.apnamzp.network.NetworkApi;
import com.avit.apnamzp.network.RetrofitClient;
import com.google.gson.Gson;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OrdersViewModel extends ViewModel {
    private MutableLiveData<List<OrderItem>> mutableLiveData;
    private String TAG = "OrdersViewModel";

    public OrdersViewModel(){
        mutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<List<OrderItem>> getMutableLiveData() {
        return mutableLiveData;
    }

    public void getDataFromServer(Context context, String userId){
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkApi networkApi = retrofit.create(NetworkApi.class);

        Call<List<OrderItem>> call = networkApi.getOrders(userId);
        call.enqueue(new Callback<List<OrderItem>>() {
            @Override
            public void onResponse(Call<List<OrderItem>> call, Response<List<OrderItem>> response) {
                mutableLiveData.setValue(response.body());
//                Log.i(TAG, "onResponse: " + response.body());

            }

            @Override
            public void onFailure(Call<List<OrderItem>> call, Throwable t) {
                Toasty.error(context,t.getMessage(),Toasty.LENGTH_SHORT)
                        .show();
            }
        });
    }

}
