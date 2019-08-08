package com.komsi.user_interface.models;

public class Response_DetailKlien {
    private Details details;

    public Response_DetailKlien(Details details) {
        this.details = details;
    }

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }
}
