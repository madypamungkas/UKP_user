package com.komsi.user_interface.models;

public class SendEmailVerif_Model {
    String status;
    String message;
    Details result;

    public SendEmailVerif_Model(String status, String message, Details result) {
        this.status = status;
        this.message = message;
        this.result = result;
    }

    public String getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Details getResult() {
        return result;
    }
}
