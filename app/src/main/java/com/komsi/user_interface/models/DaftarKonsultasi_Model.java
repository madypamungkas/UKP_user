package com.komsi.user_interface.models;

public class DaftarKonsultasi_Model {
    private int id,sesi_id, klien_id, layanan_id, tes_id, ruangan_id, psikolog_id, status_id;
    private String keluhan, tanggal;

    public DaftarKonsultasi_Model(int id, int sesi_id, int klien_id, int layanan_id, int tes_id, int ruangan_id, int psikolog_id, int status_id, String keluhan, String tanggal) {
        this.id = id;
        this.sesi_id = sesi_id;
        this.klien_id = klien_id;
        this.layanan_id = layanan_id;
        this.tes_id = tes_id;
        this.ruangan_id = ruangan_id;
        this.psikolog_id = psikolog_id;
        this.status_id = status_id;
        this.keluhan = keluhan;
        this.tanggal = tanggal;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSesi_id() {
        return sesi_id;
    }

    public void setSesi_id(int sesi_id) {
        this.sesi_id = sesi_id;
    }

    public int getKlien_id() {
        return klien_id;
    }

    public void setKlien_id(int klien_id) {
        this.klien_id = klien_id;
    }

    public int getLayanan_id() {
        return layanan_id;
    }

    public void setLayanan_id(int layanan_id) {
        this.layanan_id = layanan_id;
    }

    public int getTes_id() {
        return tes_id;
    }

    public void setTes_id(int tes_id) {
        this.tes_id = tes_id;
    }

    public int getRuangan_id() {
        return ruangan_id;
    }

    public void setRuangan_id(int ruangan_id) {
        this.ruangan_id = ruangan_id;
    }

    public int getPsikolog_id() {
        return psikolog_id;
    }

    public void setPsikolog_id(int psikolog_id) {
        this.psikolog_id = psikolog_id;
    }

    public int getStatus_id() {
        return status_id;
    }

    public void setStatus_id(int status_id) {
        this.status_id = status_id;
    }

    public String getKeluhan() {
        return keluhan;
    }

    public void setKeluhan(String keluhan) {
        this.keluhan = keluhan;
    }

    public String getTanggal() {
        return tanggal;
    }

    public void setTanggal(String tanggal) {
        this.tanggal = tanggal;
    }
}
