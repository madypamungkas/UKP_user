package com.komsi.user_interface.models;

public class Model_GantiPassword {
    private String status;
    private String sukses;

    public Model_GantiPassword(String status, String sukses) {
        this.status = status;
        this.sukses = sukses;
    }

    public String getStatus() {
        return status;
    }

    public String getSukses() {
        return sukses;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSukses(String sukses) {
        this.sukses = sukses;
    }
}
