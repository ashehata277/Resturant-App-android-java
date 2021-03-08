package com.example.recently;

public class MenuItem {
    private String Name;
    private String description;
    private  Long price;
    public MenuItem(String name, String description, Long price)
    {
        Name = name;
        this.description = description;
        this.price = price;
    }
    public String getName() {
        return Name;
    }
    public String getDescription() {
        return description;
    }

    public Long getPrice() {
        return price;
    }
}
