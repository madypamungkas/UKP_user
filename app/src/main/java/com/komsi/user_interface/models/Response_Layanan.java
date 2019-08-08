package com.komsi.user_interface.models;

import java.util.List;

public class Response_Layanan {
    private String status;
    private List<Model_Layanan> layanan;

    public Response_Layanan(String status, List<Model_Layanan> layanan) {
        this.status = status;
        this.layanan = layanan;
    }

    public String getStatus() {
        return status;
    }

    public List<Model_Layanan> getLayanan() {
        return layanan;
    }
}
