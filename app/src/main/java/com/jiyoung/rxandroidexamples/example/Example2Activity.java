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

import com.jiyoung.rxandroidexamples.CitiesAdapter;
import com.jiyoung.rxandroidexamples.ClickListener;
import com.jiyoung.rxandroidexamples.R;
import com.jiyoung.rxandroidexamples.utils.Log;

import org.reactivestreams.Subscription;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import io.reactivex.subjects.PublishSubject;

public class Example2Activity extends AppCompatActivity implements ClickListener{

    private EditText mSearchInput;
    private RecyclerView mSearchResult;
    private TextView mNoResult;
    private CitiesAdapter citiesAdapter;
    
    private PublishSubject<String> mSearchResultSubject;
    private Subscription mTextWatchSubscription;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example2);

        initLayout();
        createObservables();
        listenToSearchInput();

    }

    private void initLayout() {
        mSearchInput = (EditText) findViewById(R.id.edit_search_input);
        mNoResult = (TextView) findViewById(R.id.text_no_result);
        Log.e("init RecyclerView");
        mSearchResult = (RecyclerView) findViewById(R.id.recycler_search_result);
        mSearchResult.setLayoutManager(new LinearLayoutManager(this));
        citiesAdapter = new CitiesAdapter(this);
        mSearchResult.setAdapter(citiesAdapter);
        citiesAdapter.setClickListener(this);

    }

    private void createObservables() {
        mSearchResultSubject = PublishSubject.create();
        mSearchResultSubject
                .debounce(400, TimeUnit.MILLISECONDS)
                .observeOn(Schedulers.io())
                .map(new Function<String, List<String>>(){

                    @Override
                    public List<String> apply(String s) throws Exception {
                        return searchForCity(s);
                    }
                });
//                .map(new Func1<String, List<String>>() {
//                    @Override
//                    public List<String> call(String s) {
//                        return searchForCity(s);
//                    }
//                })
//                .observeOn(AndroidSchedulers.mainThread())
//                .subscribe(new Observer<List<String>>() {
//                    @Override
//                    public void onCompleted() {
//
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//
//                    @Override
//                    public void onSubscribe(@NonNull Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(List<String> cities) {
//                        showSearchResult(cities);
//                    }
//                });
    }

    private void showSearchResult(List<String> cities){
        if (cities != null){
            mNoResult.setVisibility(View.GONE);
            mSearchResult.setVisibility(View.VISIBLE);
            citiesAdapter.setCities(cities);
            // // TODO: 2017. 5. 31. adapter cities set
            
        }else{
            mNoResult.setVisibility(View.VISIBLE);
            mSearchResult.setVisibility(View.GONE);
        }
    }

    private void listenToSearchInput(){
        mSearchInput.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                mSearchResultSubject.onNext(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    public List<String> searchForCity(String searchString) {
        try {
            // "Simulate" the delay of network.
            Thread.sleep(500);
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
