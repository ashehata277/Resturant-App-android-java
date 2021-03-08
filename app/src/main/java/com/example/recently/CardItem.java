package com.example.recently;

public class CardItem
{
    private String Name;
    private Long number;
    private String notes;
    private Long Total;
    private Long price;
    private String Description;
    public CardItem(String name, String des, Long price,Long number, String notes, Long total) {
        Name = name;
        this.number = number;
        this.notes = notes;
        Total = total;
        this.price = price;
        this.Description=des;
    }

    public String getName() {
        return Name;
    }

    public Long getNumber() {
        return number;
    }

    public String getNotes() {
        return notes;
    }

    public Long getTotal() {
        return Total;
    }

    public String getDescription() {
        return Description;
    }

    public Long getPrice() {
        return price;
    }

    public void setNumber(Long number) {
        this.number = number;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public void setTotal(Long total) {
        Total = total;
    }
}
