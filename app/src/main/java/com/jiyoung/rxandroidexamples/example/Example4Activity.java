package com.jiyoung.rxandroidexamples.example;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.jiyoung.rxandroidexamples.BaseActivity;
import com.jiyoung.rxandroidexamples.R;
import com.jiyoung.rxandroidexamples.net.async.DataService;
import com.jiyoung.rxandroidexamples.net.model.MainWeather;

import java.util.ArrayList;

public class Example4Activity extends BaseActivity {

    private ArrayList<String> citis;
    private ArrayList<MainWeather> weathers;
    private RecyclerView recyclerView;
    private WeatherAdapter adapter;
    private DataService dataService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example4);
        initArray();
        initRecyclerView();
        initDataService();
    }

    private void initDataService() {
        dataService = new DataService(this);
        dataService.setEventListener(new DataService.EventListener() {
            @Override
            public void requestWeatherInfo_Success(MainWeather weather) {
                super.requestWeatherInfo_Success(weather);
                if (weathers == null || weathers.size() > 3)
                    weathers = new ArrayList<MainWeather>();

                weathers.add(weather);

                if (weathers.size() < 3){

                    dataService.requestWeatherInfo(citis.get(weathers.size()-1));
                }

            }

            @Override
            public void requestWeatherInfo_Fail() {
                super.requestWeatherInfo_Fail();
            }
        });
    }

    private void initRecyclerView() {

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new WeatherAdapter(this);
        recyclerView.setAdapter(adapter);

    }

    private void initArray() {
        citis = new ArrayList<>();
        citis.add("seoul");
        citis.add("paris");
        citis.add("osaka");
    }

    private class WeatherAdapter extends RecyclerView.Adapter<WeatherAdapter.ViewHolder> {

        private Context context;
        private ArrayList<MainWeather> weatherList;

        public void setWeatherList(ArrayList<MainWeather> weatherList) {
            this.weatherList = weatherList;
        }

        public WeatherAdapter(Context context) {
            this.context = context;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View v = LayoutInflater.from(context).inflate(R.layout.itme_weather_info, parent, false);
            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            holder.tv_city.setText(position + "");
        }


        @Override
        public int getItemCount() {
            return citis.size();
        }

        /**
         * recyclerView item에 들어간 view들을 관리하는 ViewHolder
         */
        public class ViewHolder extends RecyclerView.ViewHolder {

            private TextView tv_city;
            private TextView tv_temp;
            private ImageView iv_icon;

            public ViewHolder(View itemView) {
                super(itemView);

                tv_city = (TextView) itemView.findViewById(R.id.tv_city);
                tv_temp = (TextView) itemView.findViewById(R.id.tv_temp);
                iv_icon = (ImageView) itemView.findViewById(R.id.iv_icon);

            }
        }
    }
}
