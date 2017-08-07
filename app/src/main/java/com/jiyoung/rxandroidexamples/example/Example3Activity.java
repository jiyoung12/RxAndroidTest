package com.jiyoung.rxandroidexamples.example;

import android.os.Bundle;

import com.jiyoung.rxandroidexamples.BaseActivity;
import com.jiyoung.rxandroidexamples.R;
import com.jiyoung.rxandroidexamples.net.model.MainWeather;
import com.jiyoung.rxandroidexamples.net.retrofit.WeatherService;
import com.jiyoung.rxandroidexamples.utils.Log;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Example3Activity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example3);

        float lat = 37;
        float lon  = 126;

        Retrofit client = new Retrofit.Builder().baseUrl("http://api.openweathermap.org").addConverterFactory(GsonConverterFactory.create()).build();
        WeatherService service = client.create(WeatherService.class);

        Call<MainWeather> call = service.getWeather(getString(R.string.weather_app_key), lat, lon);
        call.enqueue(new Callback<MainWeather>() {
            @Override
            public void onResponse(Call<MainWeather> call, Response<MainWeather> response) {
                if (response.isSuccessful()){

                    MainWeather mainWeather = response.body();
                    Log.d(mainWeather.getName());
                }
            }

            @Override
            public void onFailure(Call<MainWeather> call, Throwable t) {

            }
        });
    }
}
