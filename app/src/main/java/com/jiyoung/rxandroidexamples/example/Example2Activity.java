package com.jiyoung.rxandroidexamples.example;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.jakewharton.rxbinding2.widget.RxTextView;
import com.jiyoung.rxandroidexamples.CitiesAdapter;
import com.jiyoung.rxandroidexamples.ClickListener;
import com.jiyoung.rxandroidexamples.R;
import com.jiyoung.rxandroidexamples.utils.Log;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.concurrent.TimeUnit;

import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class Example2Activity extends AppCompatActivity implements ClickListener {

    private EditText mSearchInput;
    private RecyclerView mSearchResult;
    private TextView mNoResult;
    private CitiesAdapter citiesAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example2);

        initLayout();
        createObservables();

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

    private void showSearchResult(List<String> cities) {
        if (cities != null) {
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
        try {
            Thread.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
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
        return toReturn;
    }

    @Override
    public void itemClicked(View view, int position) {
        Toast.makeText(this, "position" + position, Toast.LENGTH_SHORT).show();
    }
}
