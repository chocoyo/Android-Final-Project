package com.mhodges.finalproject;

import com.google.firebase.firestore.DocumentId;

import java.util.ArrayList;

//Model for an list in firebase
public class ItemList {
    @DocumentId
    private String documentId;

    private String name;
    private String userID;
    private ArrayList<Item> items;
    private Object timestamp;

    public ItemList()
    {
        items = new ArrayList<>();
    }

    public ItemList(String name, String userID)
    {
        items = new ArrayList<>();
        this.name = name;
        this.userID = userID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> items) {
        this.items = items;
    }

    public void addItem(Item item) { this.items.add(item); }

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {this.userID = userID; }

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
