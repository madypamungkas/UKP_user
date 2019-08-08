package com.komsi.user_interface.models;

public class Response_ForgotPassword {
    private String  message;
    private boolean status;

    public Response_ForgotPassword(String message, boolean status) {
        this.message = message;
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public boolean isStatus() {
        return status;
    }
}
