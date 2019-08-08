package com.komsi.user_interface.models;

import java.util.List;

public class Response_Jadwal {

    private List<Model_Jadwal> jadwal;

    public Response_Jadwal(List<Model_Jadwal> jadwal) {
        this.jadwal = jadwal;
    }

    public List<Model_Jadwal> getJadwal() {
        return jadwal;
    }
}
