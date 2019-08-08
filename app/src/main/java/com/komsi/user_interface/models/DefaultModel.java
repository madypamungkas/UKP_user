package com.komsi.user_interface.models;

public class DefaultModel {
    String status;
    String message;

    public DefaultModel(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }
}
