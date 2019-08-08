package com.komsi.user_interface.models;

public class PsikologDetails {
    private int id;
    private String gelar;
    private String no_sipp;
    private String user_id;
    private String updated_at, created_at, deleted_at;
    private Details user;

    public PsikologDetails(int id, String gelar, String no_sipp, String user_id, String updated_at, String created_at, String deleted_at, Details user) {
        this.id = id;
        this.gelar = gelar;
        this.no_sipp = no_sipp;
        this.user_id = user_id;
        this.updated_at = updated_at;
        this.created_at = created_at;
        this.deleted_at = deleted_at;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public String getGelar() {
        return gelar;
    }

    public String getNo_sipp() {
        return no_sipp;
    }

    public String getUser_id() {
        return user_id;
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

    public Details getUser() {
        return user;
    }
}
