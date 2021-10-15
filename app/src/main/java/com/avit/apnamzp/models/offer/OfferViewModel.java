package com.avit.apnamzp.models.offer;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

public class OfferViewModel extends ViewModel {
    MutableLiveData<List<OfferItem>> offersListMutableLiveData;

    public OfferViewModel(){
        offersListMutableLiveData = new MutableLiveData<>();

        // TODO: Get The data from server
        List<OfferItem> offerItemList = new ArrayList<>();
        offerItemList.add(new OfferItem());
        offerItemList.add(new OfferItem());
        offerItemList.add(new OfferItem());
        offerItemList.add(new OfferItem());

        offersListMutableLiveData.setValue(offerItemList);

    }

    public MutableLiveData<List<OfferItem>> getOffersListMutableLiveData() {
        return offersListMutableLiveData;
    }
}
