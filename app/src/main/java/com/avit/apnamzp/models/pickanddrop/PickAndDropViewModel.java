package com.avit.apnamzp.models.pickanddrop;

import android.content.Context;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avit.apnamzp.models.network.NetworkResponse;
import com.avit.apnamzp.network.NetworkApi;
import com.avit.apnamzp.network.RetrofitClient;
import com.avit.apnamzp.utils.ErrorUtils;

import es.dmoral.toasty.Toasty;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PickAndDropViewModel extends ViewModel {
    private MutableLiveData<PickAndDropDetails> mutableLivePickAndDropDetailsData;

    public PickAndDropViewModel(){
        mutableLivePickAndDropDetailsData = new MutableLiveData<>();
    }

    public MutableLiveData<PickAndDropDetails> getMutableLivePickAndDropDetailsData() {
        return mutableLivePickAndDropDetailsData;
    }

    public void getPickAndDropDetailsFromServer(Context context){
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkApi networkApi = retrofit.create(NetworkApi.class);

        Call<PickAndDropDetails> call = networkApi.getPickAndDropDetails();
        call.enqueue(new Callback<PickAndDropDetails>() {
            @Override
            public void onResponse(Call<PickAndDropDetails> call, Response<PickAndDropDetails> response) {

                if(!response.isSuccessful()){
                    NetworkResponse errorResponse = ErrorUtils.parseErrorResponse(response);
                    Toasty.error(context,errorResponse.getDesc(),Toasty.LENGTH_SHORT)
                            .show();
                    return;
                }

                mutableLivePickAndDropDetailsData.setValue(response.body());
            }

            @Override
            public void onFailure(Call<PickAndDropDetails> call, Throwable t) {
                Toasty.error(context,t.getMessage(),Toasty.LENGTH_SHORT)
                        .show();
            }
        });

    }

}
