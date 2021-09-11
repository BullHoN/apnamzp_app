package com.avit.apnamzp.ui.searchcategory;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.avit.apnamzp.models.shop.ShopData;

import java.util.ArrayList;
import java.util.List;

public class SearchCategoryViewModel extends ViewModel {

    MutableLiveData<List<ShopData>> shopsData;
    List<ShopData> shopDataList;

    public SearchCategoryViewModel(){
        shopsData = new MutableLiveData<>();

        // TODO: Get shop data from server
        shopDataList = new ArrayList<>();
        shopDataList.add(new ShopData("Vaibhav KI Dukaan hai",true));
        shopDataList.add(new ShopData("Kathir Sweets & Backery",true));
        shopDataList.add(new ShopData("Zeher hi Zeher hai Yha Pe",false));
        shopDataList.add(new ShopData("Up63 Cafe",true));

        shopsData.setValue(shopDataList);
    }

    public MutableLiveData<List<ShopData>> getShopsData() {
        return shopsData;
    }


}
