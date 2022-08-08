package com.avit.apnamzp.ui.home;

import android.content.Context;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avit.apnamzp.models.BannerData;
import com.avit.apnamzp.models.network.NetworkResponse;
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

public class HomeViewModel extends ViewModel {
    MutableLiveData<List<BannerData>> bannerImages;
    private String TAG = "HomeFragment";

    public HomeViewModel() {
        List<BannerData> bannerDataList = new ArrayList<>();
        bannerDataList.add(new BannerData("https://caportal.saginfotech.com/wp-content/uploads/2019/06/Discount-Offers.jpg"));
        bannerDataList.add(new BannerData("https://www.arisimart.com/wp-content/uploads/2020/01/OFFER4.jpg"));
        bannerDataList.add(new BannerData("https://www.mysmartprice.com/gear/wp-content/uploads/2018/08/flipkart.png"));

        bannerImages = new MutableLiveData<>();
//        bannerImages.setValue(bannerDataList);
    }

    public void getImages(Context context){
        Retrofit retrofit = RetrofitClient.getInstance();
        NetworkApi networkApi = retrofit.create(NetworkApi.class);

        Call<List<BannerData>> call = networkApi.getBannerImages();
        call.enqueue(new Callback<List<BannerData>>() {
            @Override
            public void onResponse(Call<List<BannerData>> call, Response<List<BannerData>> response) {
                if(!response.isSuccessful()){
                    NetworkResponse errorResponse = ErrorUtils.parseErrorResponse(response);
                    Toasty.error(context,errorResponse.getDesc(),Toasty.LENGTH_SHORT)
                            .show();
                    return;
                }

                Log.i(TAG, "onResponse: " + response.body().size());
                bannerImages.setValue(response.body());

            }

            @Override
            public void onFailure(Call<List<BannerData>> call, Throwable t) {
                Log.e(TAG, "onFailure: ", t);
            }
        });

    }

    public MutableLiveData<List<BannerData>> getBannerImages() {
        return bannerImages;
    }
}
