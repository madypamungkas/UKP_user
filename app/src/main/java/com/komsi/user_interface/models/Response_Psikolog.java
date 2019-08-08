package com.komsi.user_interface.models;

import java.util.List;

public class Response_Psikolog {
    private List<PsikologModel> psikolog;

    public Response_Psikolog(List<PsikologModel> psikolog) {
        this.psikolog = psikolog;
    }

    public List<PsikologModel> getPsikolog() {
        return psikolog;
    }
}
