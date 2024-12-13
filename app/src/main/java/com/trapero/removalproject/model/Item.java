package com.trapero.removalproject.model;

import com.google.gson.annotations.SerializedName;

public class Item {

    @SerializedName("id") // Matches the JSON field name from your Laravel API
    private String id;

    @SerializedName("name")
    private String name;

    // Constructor, getters, and setters remain the same
    public Item(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public String getId() { return id; }
    public void setId(String id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

}