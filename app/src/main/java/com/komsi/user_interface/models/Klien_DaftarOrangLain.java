package com.komsi.user_interface.models;

public class Klien_DaftarOrangLain {
    private int idKlien;
    private int parent_id;

    public Klien_DaftarOrangLain(int idKlien, int parent_id) {
        this.idKlien = idKlien;
        this.parent_id = parent_id;
    }

    public int getIdKlien() {
        return idKlien;
    }

    public int getParent_id() {
        return parent_id;
    }
}
