package com.team9.safehubs;

public class ReviewBlock {
    private double rating;
    private String text;

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public ReviewBlock(double rating, String text) {
        this.rating = rating;
        this.text = text;
    }
}
