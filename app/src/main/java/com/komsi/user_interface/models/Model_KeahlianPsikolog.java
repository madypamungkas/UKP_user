package com.komsi.user_interface.models;

public class Model_KeahlianPsikolog {
    private int id;
    private String nama;
    private String updated_at, created_at, deleted_at;

    public Model_KeahlianPsikolog(int id, String nama, String updated_at, String created_at, String deleted_at) {
        this.id = id;
        this.nama = nama;
        this.updated_at = updated_at;
        this.created_at = created_at;
        this.deleted_at = deleted_at;
    }

    public int getId() {
        return id;
    }

    public String getNama() {
        return nama;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getDeleted_at() {
        return deleted_at;
    }
}
