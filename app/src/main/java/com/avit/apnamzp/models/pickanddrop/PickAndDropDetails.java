package com.avit.apnamzp.models.pickanddrop;

import com.avit.apnamzp.models.BannerData;

import java.util.List;

public class PickAndDropDetails {
    private String pricings;
    private List<BannerData> carriablesImage;

    public PickAndDropDetails(String pricings, List<BannerData> carriablesImage) {
        this.pricings = pricings;
        this.carriablesImage = carriablesImage;
    }

    public String getPricings() {
        return pricings;
    }

    public List<BannerData> getCarriablesImage() {
        return carriablesImage;
    }
}
