package com.mhodges.finalproject;

import com.google.firebase.firestore.DocumentId;

import java.util.ArrayList;

//Model for an item in firebase
public class Item {
    @DocumentId
    private String documentId;

    private String userID;
    private String name;
    private double price;
    private String description;
    private String link;
    private Object timestamp;

    public Item(){ }

    public Item(String name, String userID)
    {
        this.name = name;
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public String getDocumentId() {
        return documentId;
    }

    public void setDocumentId(String documentId) {
        this.documentId = documentId;
    }

    public Object getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Object timestamp) {
        this.timestamp = timestamp;
    }
}
