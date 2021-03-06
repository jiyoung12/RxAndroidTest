package com.jiyoung.rxandroidexamples.example;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jiyoung.rxandroidexamples.BaseActivity;
import com.jiyoung.rxandroidexamples.CitiesAdapter;
import com.jiyoung.rxandroidexamples.ClickListener;
import com.jiyoung.rxandroidexamples.R;
import com.jiyoung.rxandroidexamples.net.async.DataService;
import com.jiyoung.rxandroidexamples.net.model.MainWeather;
import com.jiyoung.rxandroidexamples.utils.Log;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class Example2Activity extends BaseActivity implements ClickListener {

    private EditText mSearchInput;
    private RecyclerView mSearchResult;
    private TextView mNoResult;
    private List<String> mSearchList;
    private CitiesAdapter citiesAdapter;
    private DataService dataService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example2);

        initLayout();
        initService();
        createObservables();

    }

    private void initService() {

        dataService = new DataService(this);
        dataService.setEventListener(new DataService.EventListener() {
            @Override
            public void requestWeatherInfo_Success(MainWeather weather) {
                super.requestWeatherInfo_Success(weather);
                Log.d(weather.getName());
            }

            @Override
            public void requestWeatherInfo_Fail() {
                super.requestWeatherInfo_Fail();
            }
        });

    }

    private void initLayout() {
        mSearchInput = (EditText) findViewById(R.id.edit_search_input);
        mNoResult = (TextView) findViewById(R.id.text_no_result);
        mSearchResult = (RecyclerView) findViewById(R.id.recycler_search_result);
        mSearchResult.setLayoutManager(new LinearLayoutManager(this));
        citiesAdapter = new CitiesAdapter(this);
        mSearchResult.setAdapter(citiesAdapter);
        citiesAdapter.setClickListener(this);

    }

    private void createObservables() {

        RxTextView.textChanges(mSearchInput)
                .observeOn(Schedulers.io())
                .map(charSequence -> searchForCity(charSequence.toString()))
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<List<String>>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.e("onSubscribe");
                    }

                    @Override
                    public void onNext(@NonNull List<String> strings) {
                        Log.e("onNext : " + strings);
                        showSearchResult(strings);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e("onError");
                    }

                    @Override
                    public void onComplete() {
                        Log.e("onComplete");
                    }
                });
    }

    /**
     * 도시 리스트에 따른 뷰 변경
     *
     * @param cities 도시 리스트
     */
    private void showSearchResult(List<String> cities) {
        if (cities != null && cities.size() != 0) {
            mNoResult.setVisibility(View.GONE);
            mSearchResult.setVisibility(View.VISIBLE);
            citiesAdapter.setCities(cities);
            citiesAdapter.notifyDataSetChanged();

        } else {
            mNoResult.setVisibility(View.VISIBLE);
            mSearchResult.setVisibility(View.GONE);
            citiesAdapter.notifyDataSetChanged();
        }
    }

    public List<String> searchForCity(String searchString) {
//        try {
//            Thread.sleep(300);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        return getMatchingCities(searchString);
    }

    private List<String> getMatchingCities(String searchString) {
        if (searchString.isEmpty()) {
            return new ArrayList<>();
        }

        String[] cities = getResources().getStringArray(R.array.city_list);
        List<String> toReturn = new ArrayList<>();
        for (String city : cities) {
            if (city.toLowerCase().startsWith(searchString.toLowerCase())) {
                toReturn.add(city);
            }
        }
        mSearchList = toReturn;
        return toReturn;
    }

    @Override
    public void itemClicked(View view, int position) {
        if (mSearchList != null) {
            Log.e(mSearchList.get(position));
            Intent intent = new Intent(Example2Activity.this, Example3Activity.class);
            intent.putExtra("city", mSearchList.get(position));
            startActivity(intent);
        }
    }
}
