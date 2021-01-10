package com.team9.safehubs;

public class ReviewBlock {
    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public float getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(float avgRating) {
        this.avgRating = avgRating;
    }

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public String getAdditionalComment() {
        return additionalComment;
    }

    public void setAdditionalComment(String additionalComment) {
        this.additionalComment = additionalComment;
    }

    public ReviewBlock(String place_name, float avgRating, String date_time, String additionalComment) {
        this.place_name = place_name;
        this.avgRating = avgRating;
        this.date_time = date_time;
        this.additionalComment = additionalComment;
    }

    String place_name;
    float avgRating;
    String date_time;
    String additionalComment;
}
