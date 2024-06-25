package com.example.evenement_app;

public class InviteModel {

    private String id;
    private String name;

    public InviteModel() {
        // Required default constructor for Firestore serialization
    }

    public InviteModel(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}


