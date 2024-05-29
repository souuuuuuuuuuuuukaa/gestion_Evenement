package com.example.evenement_app;

public class Model {
    String id,invites;

    public Model() {
    }

    public Model(String id, String invites) {
        this.id = id;
        this.invites = invites;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getInvite() {
        return invites;
    }

    public void setInvite(String invites) {
        this.invites = invites;
    }
}
