package com.jiyoung.rxandroidexamples.net.model;

/**
 * Created by Jiyoung on 2017-07-16.
 */

public class Sys {

    private float message;
    private String country;
    private long sunrise;
    private long sunset;

    public float getMessage() {
        return message;
    }

    public void setMessage(float message) {
        this.message = message;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public long getSunrise() {
        return sunrise;
    }

    public void setSunrise(long sunrise) {
        this.sunrise = sunrise;
    }

    public long getSunset() {
        return sunset;
    }

    public void setSunset(long sunset) {
        this.sunset = sunset;
    }
}
