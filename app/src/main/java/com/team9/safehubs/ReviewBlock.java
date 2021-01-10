package com.team9.safehubs;

public class ReviewBlock {
    private String place_name;
    private float avgRating;
    private String date_time;
    private String additionalComment;

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

    public String getAdditionalComment() {
        return additionalComment;
    }

    public void setAdditionalComment(String additionalComment) {
        this.additionalComment = additionalComment;
    }

    public ReviewBlock(String place_name, float avgRating, String additionalComment) {
        this.place_name = place_name;
        this.avgRating = avgRating;
        this.additionalComment = additionalComment;
    }
}
