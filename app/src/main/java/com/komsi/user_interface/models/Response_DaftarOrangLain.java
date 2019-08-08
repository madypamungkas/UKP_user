package com.komsi.user_interface.models;

import java.sql.Timestamp;

public class Response_DaftarOrangLain {
  private Klien_DaftarOrangLain Klien_DaftarOrangLain;

    public Response_DaftarOrangLain(com.komsi.user_interface.models.Klien_DaftarOrangLain klien_DaftarOrangLain) {
        Klien_DaftarOrangLain = klien_DaftarOrangLain;
    }

    public com.komsi.user_interface.models.Klien_DaftarOrangLain getKlien_DaftarOrangLain() {
        return Klien_DaftarOrangLain;
    }
}