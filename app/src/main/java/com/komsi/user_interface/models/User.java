package com.komsi.user_interface.models;

public class User {

    private int id;
    private String name;
    private String username_id;
    private String email;
    private String token;


    public User(int id, String name, String username_id, String email, String token) {
        this.id = id;
        this.name = name;
        this.username_id = username_id;
        this.email = email;
        this.token = token;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getUsername_id() {
        return username_id;
    }

    public String getEmail() {
        return email;
    }

    public String getToken() {
        return token;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUsername(String username) {
        this.username_id = username_id;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
