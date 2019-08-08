package com.komsi.user_interface.models;

import java.util.List;

public class Response_Child {
    private List<Model_Child> child;

    public Response_Child(List<Model_Child> child) {
        this.child = child;
    }

    public List<Model_Child> getChild() {
        return child;
    }
}
