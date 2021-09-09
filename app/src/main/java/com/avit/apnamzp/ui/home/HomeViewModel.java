package com.avit.apnamzp.ui.home;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avit.apnamzp.models.BannerData;

import java.util.ArrayList;
import java.util.List;

public class HomeViewModel extends ViewModel {
    MutableLiveData<List<BannerData>> bannerImages;

    public HomeViewModel() {
        // TODO: GET THE BANNER DATA FROM THE SERVER
        List<BannerData> bannerDataList = new ArrayList<>();
        bannerDataList.add(new BannerData("https://caportal.saginfotech.com/wp-content/uploads/2019/06/Discount-Offers.jpg"));
        bannerDataList.add(new BannerData("https://www.arisimart.com/wp-content/uploads/2020/01/OFFER4.jpg"));
        bannerDataList.add(new BannerData("https://www.mysmartprice.com/gear/wp-content/uploads/2018/08/flipkart.png"));

        bannerImages = new MutableLiveData<>();
        bannerImages.setValue(bannerDataList);
    }

    public MutableLiveData<List<BannerData>> getBannerImages() {
        return bannerImages;
    }
}
