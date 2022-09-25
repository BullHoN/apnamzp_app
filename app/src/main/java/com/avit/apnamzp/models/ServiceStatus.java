package com.avit.apnamzp.models;

public class ServiceStatus {
    private boolean serviceOpen;
    private String message;
    private String type;

    public ServiceStatus(boolean serviceOpen, String message, String type) {
        this.serviceOpen = serviceOpen;
        this.message = message;
        this.type = type;
    }

    public ServiceStatus(boolean serviceOpen, String message) {
        this.serviceOpen = serviceOpen;
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public boolean isServiceOpen() {
        return serviceOpen;
    }

    public String getMessage() {
        return message;
    }
}
