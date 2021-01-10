package com.team9.safehubs;

public class Place {
    int place_id;
    String place_name;
    double place_lat;
    double place_lng;

    public Place(int place_id, String place_name, double place_lat, double place_lng) {
        this.place_id = place_id;
        this.place_name = place_name;
        this.place_lat = place_lat;
        this.place_lng = place_lng;
    }

    public int getPlace_id() {
        return place_id;
    }

    public void setPlace_id(int place_id) {
        this.place_id = place_id;
    }

    public String getPlace_name() {
        return place_name;
    }

    public void setPlace_name(String place_name) {
        this.place_name = place_name;
    }

    public double getPlace_lat() {
        return place_lat;
    }

    public void setPlace_lat(double place_lat) {
        this.place_lat = place_lat;
    }

    public double getPlace_lng() {
        return place_lng;
    }

    public void setPlace_lng(double place_lng) {
        this.place_lng = place_lng;
    }
}
