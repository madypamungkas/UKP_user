package com.komsi.user_interface.models;

import java.sql.Timestamp;

public class Klien {
    private int id, anak_ke, jumlah_saudara, user_id, kategori_id, parent_id;
    private String pendidikan_terakhir, hub_pendaftar;
    private String updated_at, created_at, deleted_at;

    public Klien(int idKlien, String pendidikan_terakhir, int anak_ke, int jumlah_saudara, int user_id, String updated_at, String created_at, String deleted_at, int kategori_id, int parent_id, String hub_pendaftar) {
        this.id = idKlien;
        this.pendidikan_terakhir = pendidikan_terakhir;
        this.anak_ke = anak_ke;
        this.jumlah_saudara = jumlah_saudara;
        this.user_id = user_id;
        this.updated_at = updated_at;
        this.created_at = created_at;
        this.deleted_at = deleted_at;
        this.kategori_id = kategori_id;
        this.parent_id = parent_id;
        this.hub_pendaftar = hub_pendaftar;

    }

    public int getKlienId() {
        return id;
    }

    public int getAnak_ke() {
        return anak_ke;
    }

    public int getJumlah_saudara() {
        return jumlah_saudara;
    }

    public int getUser_id() {
        return user_id;
    }

    public int getKategori_id() {
        return kategori_id;
    }

    public int getParent_id() {
        return parent_id;
    }

    public String getPendidikan_terakhir() {
        return pendidikan_terakhir;
    }

    public String getHub_pendaftar() {
        return hub_pendaftar;
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
