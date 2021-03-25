package com.example.lesson6_homework;

public class CardData {
    private String title;
    private String description;
    private String dateOfCreation;
    public CardData(String title, String description, String dateoOfCreation){
        this.title = title;
        this.description=description;
        this.dateOfCreation=dateoOfCreation;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public String getDateOfCreation() {
        return dateOfCreation;
    }
}
