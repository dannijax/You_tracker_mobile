package com.oluwadara.youtrackmobile.app.data.model;

import org.joda.time.DateTime;

import java.util.Random;

public class Location extends Model {

    private double longitude;
    private double latitude;
    private String dateTime;

    public Location(double longitude, double latitude, String dateTime) {
        this.longitude = longitude;
        this.latitude = latitude;
        this.dateTime = dateTime;
        Random randomGenerator = new Random();
        int id = randomGenerator.nextInt(100000);
        setId(String.valueOf(id));
    }

    public Location() {
        Random randomGenerator = new Random();
        int id = randomGenerator.nextInt(100000);
        setId(String.valueOf(id));
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public void setId(String id) {
        this.id = id;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public String getDateTime() {
        return dateTime;
    }

    public void setDateTime(String dateTime) {
        this.dateTime = dateTime;
    }
}
