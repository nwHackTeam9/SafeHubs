package com.team9.safehubs;

public class ReviewBlock {
    private String place_name;
    private double avgRating;
    private String date_time;
    private String additionalComment;

    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public double getAvgRating() {
        return avgRating;
    }

    public void setAvgRating(double avgRating) {
        this.avgRating = avgRating;
    }

    public String getAdditionalComment() {
        return additionalComment;
    }

    public void setAdditionalComment(String additionalComment) {
        this.additionalComment = additionalComment;
    }

    public ReviewBlock(double avgRating, String additionalComment) {
        this.place_name = place_name;
        this.avgRating = avgRating;
        this.additionalComment = additionalComment;
    }
}
