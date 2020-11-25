package com.mhodges.finalproject;

import java.util.ArrayList;

public class ItemList {
    private String name;
    private ArrayList<Item> items;

    public ItemList(){
        items = new ArrayList<>();
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
}
