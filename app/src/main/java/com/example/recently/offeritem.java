package com.example.recently;

public class offeritem
{
    private String link;
    private String name;
    private int type;
    private String Description;
    private long price;

    public offeritem(String link, String name,String des,long price, int type) {
        this.link = link;
        this.name = name;
        this.type = type;
        this.Description=des;
        this.price=price;
    }

    public String getLink() {
        return link;
    }

    public String getName() {
        return name;
    }

    public int getType() {
        return type;
    }

    public String getDescription() {
        return Description;
    }

    public long getPrice() {
        return price;
    }
}
