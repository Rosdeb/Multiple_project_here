package com.example.daraz;

public class Movie {
    String image,category,title,description;
    Double price;

    public Movie(String image, String category,String title,String description, Double price) {
        this.image = image;
        this.category = category;
        this.price = price;
        this.title = title;
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public String getCategory() {
        return category;
    }
    public String getTitle() {
        return title;
    }
    public String getDescription() {
        return description;
    }
    public Double getPrice() {
        return price;
    }

}
