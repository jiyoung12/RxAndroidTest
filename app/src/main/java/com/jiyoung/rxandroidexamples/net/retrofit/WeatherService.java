package com.jiyoung.rxandroidexamples.net.retrofit;

import com.jiyoung.rxandroidexamples.net.model.MainWeather;
import com.jiyoung.rxandroidexamples.net.model.Response;


import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by Jiyoung on 2017-07-16.
 */

public interface WeatherService {
    String BASE_URL = "http://api.openweathermap.org/data/2.5/weather?";

    @GET()
    Observable<Response<MainWeather>> getWeather(@Query("lat") float lat, @Query("lon") float lon);
}