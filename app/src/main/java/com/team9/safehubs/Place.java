package com.team9.safehubs;

import com.google.firebase.database.Exclude;
import com.google.firebase.database.IgnoreExtraProperties;
import com.google.firebase.encoders.annotations.Encodable;

import java.util.HashMap;
import java.util.Map;

@IgnoreExtraProperties
public class Place {
    private double lat;
    private double lng;
    private String name;
    private int num_reviews;

    public Place() {

    }

    public double getLat() {
        return lat;
    }

    public double getLng() {
        return lng;
    }

    public String getName() {
        return name;
    }

    public int getNum_reviews() {
        return num_reviews;
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("lat", lat);
        result.put("lng", lng);
        result.put("name", name);
        result.put("num_reviews", num_reviews);

        return result;
    }
}
