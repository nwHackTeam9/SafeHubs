package com.team9.safehubs;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.encoders.annotations.Encodable;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class PlaceReviews {
    private Double lat;
    private Double lng;
    private Double avg_rating;
    private String name;
    private Long num_reviews;

    public PlaceReviews() {

    }

    public Double getLat() {
        return lat;
    }

    public Double getLng() {
        return lng;
    }

    public String getName() {
        return name;
    }

    public Long getNum_reviews() {
        return num_reviews;
    }

    public Double getAvg_rating() {
        return avg_rating;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("lat", lat);
        result.put("lng", lng);
        result.put("name", name);
        result.put("num_reviews", num_reviews);
        result.put("avg_review", avg_rating);
        return result;
    }
}
