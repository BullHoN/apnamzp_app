package com.avit.apnamzp.ui.orderdetails;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avit.apnamzp.models.network.NetworkResponse;
import com.avit.apnamzp.models.order.OrderItem;
import com.avit.apnamzp.network.NetworkApi;
import com.avit.apnamzp.network.RetrofitClient;
import com.avit.apnamzp.utils.DisplayMessage;
import com.avit.apnamzp.utils.ErrorUtils;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OrderDetailsViewModel extends ViewModel {
    private String TAG = "OrderDetailsViewModel";
    private MutableLiveData<OrderItem> orderItemMutableLiveData;

    public OrderDetailsViewModel(){
        orderItemMutableLiveData = new MutableLiveData<>();
    }

    public MutableLiveData<OrderItem> getOrderItemMutableLiveData() {
        return orderItemMutableLiveData;
    }

    public void setOrderItem(OrderItem orderItem){
        orderItemMutableLiveData.setValue(orderItem);
    }

    public void getOrderDataFromServer(Context context,String orderId){
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkApi networkApi = retrofit.create(NetworkApi.class);

        Call<OrderItem> call = networkApi.getOrderById(orderId);
        call.enqueue(new Callback<OrderItem>() {
            @Override
            public void onResponse(Call<OrderItem> call, Response<OrderItem> response) {
                if(response.isSuccessful()){
                    setOrderItem(response.body());
                }
                else {
                    NetworkResponse errorResponse = ErrorUtils.parseErrorResponse(response);
                    DisplayMessage.errorMessage(context,errorResponse.getDesc(),Toasty.LENGTH_SHORT);
                }
            }

            @Override
            public void onFailure(Call<OrderItem> call, Throwable t) {
                DisplayMessage.errorMessage(context,t.getMessage(),Toasty.LENGTH_SHORT);
            }
        });
    }

}
