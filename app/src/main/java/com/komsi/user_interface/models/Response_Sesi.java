package com.komsi.user_interface.models;

import java.util.List;

public class Response_Sesi {
    private String status;
    private List<SesiModel> sesi;

    public Response_Sesi(String status, List<SesiModel> sesi) {
        this.status = status;
        this.sesi = sesi;
    }

    public String getStatus() {
        return status;
    }

    public List<SesiModel> getSesi() {
        return sesi;
    }
}
