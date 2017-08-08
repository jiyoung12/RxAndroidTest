package com.jiyoung.rxandroidexamples.net.retrofit;

import com.jiyoung.rxandroidexamples.net.model.MainWeather;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Jiyoung on 2017-07-16.
 */

public interface WeatherService {
    String BASE_URL = "http://api.openweathermap.org";

    @GET("/data/2.5/weather")
    Call<MainWeather> getWeather(@Query("appid") String appid, @Query("q") String city);
}