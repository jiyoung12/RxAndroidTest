package com.jiyoung.rxandroidexamples.example;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

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

    private String strCity;
    private TextView tv_city, tv_date, tv_temp, tv_des, tv_min, tv_max;
    private ImageView iv_icon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example3);

        initView();
        getCityInfo();
        getCityWeatherInfo();

    }


    private void initView() {

        tv_city = (TextView) findViewById(R.id.tv_city);
        tv_date = (TextView) findViewById(R.id.tv_date);
        tv_temp = (TextView) findViewById(R.id.tv_temp);
        tv_des = (TextView) findViewById(R.id.tv_des);
        tv_min = (TextView) findViewById(R.id.tv_min);
        tv_max = (TextView) findViewById(R.id.tv_max);

        iv_icon = (ImageView) findViewById(R.id.iv_icon);

    }

    private void getCityInfo() {

        strCity = getIntent().hasExtra("city") ? getIntent().getStringExtra("city") : "seoul";
        Log.d("City : " + strCity);
    }

    private void getCityWeatherInfo() {

        Retrofit client = new Retrofit.Builder().baseUrl("http://api.openweathermap.org").addConverterFactory(GsonConverterFactory.create()).build();
        WeatherService service = client.create(WeatherService.class);

        Call<MainWeather> call = service.getWeather(getString(R.string.weather_app_key), strCity);
        call.enqueue(new Callback<MainWeather>() {
            @Override
            public void onResponse(Call<MainWeather> call, Response<MainWeather> response) {
                if (response.isSuccessful()) {

                    MainWeather mainWeather = response.body();
                    Log.d(mainWeather.getName());
                    tv_city.setText(mainWeather.getName());
                    tv_date.setText(mainWeather.getDt() + "");
                    tv_temp.setText(setTempFormatter(mainWeather.getMain().getTemp()) + "C");
                    tv_des.setText(mainWeather.getWeather()[0].getMain());
                    tv_min.setText(setTempFormatter(mainWeather.getMain().getTemp_min()) + "C");
                    tv_max.setText(setTempFormatter(mainWeather.getMain().getTemp_max()) + "C");
                }
            }

            @Override
            public void onFailure(Call<MainWeather> call, Throwable t) {

            }
        });
    }

    private String setTempFormatter(float temp) {

        return String.format("%.2f", temp - 273.15);

    }
}
