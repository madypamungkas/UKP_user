package com.komsi.user_interface.models;

import java.util.List;

public class PsikologModel {
    private int id;
    private String name,  email, email_verified_at, level, username, nik, tanggal_lahir, jenis_kelamin, alamat, no_telepon, foto;
    private String updated_at, created_at, deleted_at;
   // private Model_DetailPsikolog detailPsikologModel;
    private PsikologDetail_list psikolog;

    public PsikologModel(int id, String name, String email, String email_verified_at, String level, String username, String nik, String tanggal_lahir, String jenis_kelamin, String alamat, String no_telepon, String foto, String updated_at, String created_at, String deleted_at, PsikologDetail_list psikolog) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.email_verified_at = email_verified_at;
        this.level = level;
        this.username = username;
        this.nik = nik;
        this.tanggal_lahir = tanggal_lahir;
        this.jenis_kelamin = jenis_kelamin;
        this.alamat = alamat;
        this.no_telepon = no_telepon;
        this.foto = foto;
        this.updated_at = updated_at;
        this.created_at = created_at;
        this.deleted_at = deleted_at;
        this.psikolog = psikolog;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getEmail_verified_at() {
        return email_verified_at;
    }

    public void setEmail_verified_at(String email_verified_at) {
        this.email_verified_at = email_verified_at;
    }

    public String getLevel() {
        return level;
    }

    public void setLevel(String level) {
        this.level = level;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNik() {
        return nik;
    }

    public void setNik(String nik) {
        this.nik = nik;
    }

    public String getTanggal_lahir() {
        return tanggal_lahir;
    }

    public void setTanggal_lahir(String tanggal_lahir) {
        this.tanggal_lahir = tanggal_lahir;
    }

    public String getJenis_kelamin() {
        return jenis_kelamin;
    }

    public void setJenis_kelamin(String jenis_kelamin) {
        this.jenis_kelamin = jenis_kelamin;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getNo_telepon() {
        return no_telepon;
    }

    public void setNo_telepon(String no_telepon) {
        this.no_telepon = no_telepon;
    }

    public String getFoto() {
        return foto;
    }

    public void setFoto(String foto) {
        this.foto = foto;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getDeleted_at() {
        return deleted_at;
    }

    public void setDeleted_at(String deleted_at) {
        this.deleted_at = deleted_at;
    }

    public PsikologDetail_list getPsikolog() {
        return psikolog;
    }
    //    public Model_DetailPsikolog getDetailPsikologModel() {
//        return detailPsikologModel;
//    }
//
//    public void setDetailPsikologModel(Model_DetailPsikolog detailPsikologModel) {
//        this.detailPsikologModel = detailPsikologModel;
//    }
}
