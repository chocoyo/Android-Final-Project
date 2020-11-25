package com.mhodges.finalproject;

import java.util.ArrayList;

public class ItemList {
    private String name;
    private String userID;
    private ArrayList<Item> items;

    public ItemList()
    {
        items = new ArrayList<>();
    }

    public ItemList(String userID)
    {
        items = new ArrayList<>();
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

    public String getUserID() {
        return userID;
    }

    public void setUserID(String userID) {this.userID = userID; }
}
