package com.komsi.user_interface.models;

public class Response_BiodataUpdate {
    private boolean error;
    private String message;
    private Biodata biodata;

    public Response_BiodataUpdate(boolean error, String message, Biodata biodata) {
        this.error = error;
        this.message = message;
        this.biodata = biodata;
    }

    public boolean isError() {
        return error;
    }

    public String getMessage() {
        return message;
    }

    public Biodata getBiodata() {
        return biodata;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public void setBiodata(Biodata biodata) {
        this.biodata = biodata;
    }
}
