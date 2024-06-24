package com.example.evenement_app;

import com.google.type.DateTime;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Event {

        private String name;
        private Date date;
        private Date heure;
        private String Id;
        private String budget;
        private String imageUrl;



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Date getHeure() {
        return heure;
    }

    public void setHeure(Date heure) {
        this.heure = heure;
    }

    public String getBudget() {
        return budget;
    }

    public void setBudget(String budget) {
        this.budget = budget;
    }

    public Event(String name, Date date, Date heure, String budget) {
        this.name = name;
        this.date = date;
        this.heure = heure;
        this.budget = budget;
    }

    public Event(String name, Date date, Date heure, String id, String budget) {
        this.name = name;
        this.date = date;
        this.heure = heure;
        Id = id;
        this.budget = budget;
    }

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    @Override
    public String toString() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm", Locale.getDefault());
        return
                "Nom d'événement : " + name + "\n" +
                " Date : " + dateFormat.format(date) +"\n"+
                " Heure : " + timeFormat.format(heure)+"\n"+
                "Budget : "+ budget;
    }
}

