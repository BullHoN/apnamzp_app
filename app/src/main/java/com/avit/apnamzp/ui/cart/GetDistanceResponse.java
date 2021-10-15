package com.avit.apnamzp.ui.cart;

import java.util.List;

public class GetDistanceResponse {

    private String distance;

    public GetDistanceResponse(String distance) {
        this.distance = distance;
    }

    @Override
    public String toString() {
        return "GetDistanceResponse{" +
                "distance='" + distance + '\'' +
                '}';
    }

    public String getDistance() {
        return distance;
    }
}
