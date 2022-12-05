package com.avit.apnamzp.ui.cart;

import java.util.List;

public class GetDistanceResponse {

    private String distance;
    private String actualDistance;
    private boolean edgeLocation;

    public GetDistanceResponse(String distance, String actualDistance, boolean edgeLocation) {
        this.distance = distance;
        this.actualDistance = actualDistance;
        this.edgeLocation = edgeLocation;
    }

    public boolean isEdgeLocation() {
        return edgeLocation;
    }

    public GetDistanceResponse(String distance) {
        this.distance = distance;
    }

    public String getActualDistance() {
        return actualDistance;
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
