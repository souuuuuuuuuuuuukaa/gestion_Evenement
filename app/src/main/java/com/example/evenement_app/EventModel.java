package com.example.evenement_app;



public class EventModel {
    private String description;
    private String eventId;
    private String imageUrl;

    public EventModel(String description, String eventId, String imageUrl) {
        this.description = description;
        this.eventId = eventId;
        this.imageUrl = imageUrl;
    }

    public String getDescription() {
        return description;
    }

    public String getEventId() {
        return eventId;
    }

    public String getImageUrl() {
        return imageUrl;
    }
}

