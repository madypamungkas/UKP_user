package com.komsi.user_interface.models;

public class Model_Jadwal {
    private int id;
    private String tanggal;
    private int sesi_id;
    private int klien_id;
    private String keluhan;
    private int layanan_id;
    private int tes_id;
    private int ruangan_id;
    private int psikolog_id;
    private int status_id;
    private Model_Child klien;
    private String  created_at, updated_at, deleted_at;
    private StatusModel status;
    private Model_Layanan layanan;
    private PsikologDetails psikolog;
    private SesiModel sesi;
    private Model_Ruangan ruangan;

    public Model_Jadwal(int id, String tanggal, int sesi_id, int klien_id, String keluhan, int layanan_id, int tes_id, int ruangan_id, int psikolog_id, int status_id, Model_Child klien, String created_at, String updated_at, String deleted_at, StatusModel status, Model_Layanan layanan, PsikologDetails psikolog, SesiModel sesi, Model_Ruangan ruangan) {
        this.id = id;
        this.tanggal = tanggal;
        this.sesi_id = sesi_id;
        this.klien_id = klien_id;
        this.keluhan = keluhan;
        this.layanan_id = layanan_id;
        this.tes_id = tes_id;
        this.ruangan_id = ruangan_id;
        this.psikolog_id = psikolog_id;
        this.status_id = status_id;
        this.klien = klien;
        this.created_at = created_at;
        this.updated_at = updated_at;
        this.deleted_at = deleted_at;
        this.status = status;
        this.layanan = layanan;
        this.psikolog = psikolog;
        this.sesi = sesi;
        this.ruangan = ruangan;
    }

    public int getId() {
        return id;
    }

    public String getTanggal() {
        return tanggal;
    }

    public int getSesi_id() {
        return sesi_id;
    }

    public int getKlien_id() {
        return klien_id;
    }

    public String getKeluhan() {
        return keluhan;
    }

    public int getLayanan_id() {
        return layanan_id;
    }

    public int getTes_id() {
        return tes_id;
    }

    public int getRuangan_id() {
        return ruangan_id;
    }

    public int getPsikolog_id() {
        return psikolog_id;
    }

    public int getStatus_id() {
        return status_id;
    }

    public Model_Child getKlien() {
        return klien;
    }

    public String getCreated_at() {
        return created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public StatusModel getStatus() {
        return status;
    }

    public Model_Layanan getLayanan() {
        return layanan;
    }

    public PsikologDetails getPsikolog() {
        return psikolog;
    }

    public SesiModel getSesi() {
        return sesi;
    }

    public Model_Ruangan getRuangan() {
        return ruangan;
    }
}