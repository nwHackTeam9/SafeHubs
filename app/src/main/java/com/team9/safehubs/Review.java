package com.team9.safehubs;

public class Review {
    int place_id;
    int user_id;
    float avgRating;
    String additionalComment;
    String date_time;

    public int getPlace_id() {
        return place_id;
    }

    public void setPlace_id(int place_id) {
        this.place_id = place_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
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

    public String getDate_time() {
        return date_time;
    }

    public void setDate_time(String date_time) {
        this.date_time = date_time;
    }

    public Review(int place_id, int user_id, float avgRating, String additionalComment, String date_time) {
        this.place_id = place_id;
        this.user_id = user_id;
        this.avgRating = avgRating;
        this.additionalComment = additionalComment;
        this.date_time = date_time;
    }
}
