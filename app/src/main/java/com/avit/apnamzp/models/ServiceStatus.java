package com.avit.apnamzp.models;

public class ServiceStatus {
    private boolean serviceOpen;
    private String message;

    public ServiceStatus(boolean serviceOpen, String message) {
        this.serviceOpen = serviceOpen;
        this.message = message;
    }

    public boolean isServiceOpen() {
        return serviceOpen;
    }

    public String getMessage() {
        return message;
    }
}
