package com.komsi.user_interface.models;

public class Response_Login {
    private User user;


    public Response_Login(User user) {
        this.user = user;

    }

    public User getUser() {
        return user;
    }


    public void setUser(User user) {
        this.user = user;
    }

}
