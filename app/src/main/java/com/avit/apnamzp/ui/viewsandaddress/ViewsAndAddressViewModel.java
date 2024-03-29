package com.avit.apnamzp.ui.viewsandaddress;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avit.apnamzp.models.ReviewData;
import com.avit.apnamzp.models.network.NetworkResponse;
import com.avit.apnamzp.network.NetworkApi;
import com.avit.apnamzp.network.RetrofitClient;
import com.avit.apnamzp.utils.DisplayMessage;
import com.avit.apnamzp.utils.ErrorUtils;

import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class ViewsAndAddressViewModel extends ViewModel {
    MutableLiveData<List<ReviewData>> mutableLiveData;

    public ViewsAndAddressViewModel(){
        mutableLiveData = new MutableLiveData<>();
    }

    public void getReviewsFromServer(Context context, String shopName){
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkApi networkApi = retrofit.create(NetworkApi.class);

        Call<List<ReviewData>> call = networkApi.getReviews(shopName);
        call.enqueue(new Callback<List<ReviewData>>() {
            @Override
            public void onResponse(Call<List<ReviewData>> call, Response<List<ReviewData>> response) {

                if(!response.isSuccessful()){
                    NetworkResponse errorResponse = ErrorUtils.parseErrorResponse(response);
                    DisplayMessage.errorMessage(context,errorResponse.getDesc(),Toasty.LENGTH_SHORT);
                    return;
                }

                mutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<ReviewData>> call, Throwable t) {
                DisplayMessage.errorMessage(context,t.getMessage(),Toasty.LENGTH_SHORT);
            }
        });

    }

    public MutableLiveData<List<ReviewData>> getMutableLiveData() {
        return mutableLiveData;
    }
}
