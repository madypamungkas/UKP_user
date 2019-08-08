package com.komsi.user_interface.models;

public class Response_GantiPassword {
    private Model_GantiPassword password;

    public Response_GantiPassword(Model_GantiPassword password) {
        this.password = password;
    }

    public Model_GantiPassword getPassword() {
        return password;
    }

    public void setPassword(Model_GantiPassword password) {
        this.password = password;
    }
}
