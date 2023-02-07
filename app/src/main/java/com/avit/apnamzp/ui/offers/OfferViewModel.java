package com.avit.apnamzp.ui.offers;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avit.apnamzp.models.network.NetworkResponse;
import com.avit.apnamzp.models.offer.OfferItem;
import com.avit.apnamzp.network.NetworkApi;
import com.avit.apnamzp.network.RetrofitClient;
import com.avit.apnamzp.utils.DisplayMessage;
import com.avit.apnamzp.utils.ErrorUtils;

import java.util.ArrayList;
import java.util.List;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class OfferViewModel extends ViewModel {
    MutableLiveData<List<OfferItem>> offersListMutableLiveData;

    public OfferViewModel(){
        offersListMutableLiveData = new MutableLiveData<>();

    }

    public void getDataFromServer(Context context,Boolean onlyAdmin,String shopName,boolean allOffers){
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkApi networkApi = retrofit.create(NetworkApi.class);

        Call<List<OfferItem>> call = networkApi.getOffers(onlyAdmin,shopName,allOffers);
        call.enqueue(new Callback<List<OfferItem>>() {
            @Override
            public void onResponse(Call<List<OfferItem>> call, Response<List<OfferItem>> response) {

                if(!response.isSuccessful()){
                    NetworkResponse errorResponse = ErrorUtils.parseErrorResponse(response);
                    DisplayMessage.errorMessage(context,errorResponse.getDesc(),Toasty.LENGTH_SHORT);
                    return;
                }

                offersListMutableLiveData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<List<OfferItem>> call, Throwable t) {
                DisplayMessage.errorMessage(context,t.getMessage(),Toasty.LENGTH_SHORT);
            }
        });
    }

    public MutableLiveData<List<OfferItem>> getOffersListMutableLiveData() {
        return offersListMutableLiveData;
    }
}
