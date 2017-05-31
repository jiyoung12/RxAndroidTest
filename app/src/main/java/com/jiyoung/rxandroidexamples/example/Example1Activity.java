package com.jiyoung.rxandroidexamples.example;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jiyoung.rxandroidexamples.R;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class Example1Activity extends AppCompatActivity {

    EditText editText1, editText2;
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example1);
        setActionTitle();
        initView();
        initEvent();
    }

    private void setActionTitle() {

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getIntent()!= null? getIntent().getStringExtra("title") : "RxAndroid Examples");
    }

    private void initView() {

        editText1 = (EditText) findViewById(R.id.editText1);
        editText2 = (EditText) findViewById(R.id.editText2);
        button = (Button) findViewById(R.id.button);

    }

    private void initEvent() {

        button.setOnClickListener(view -> {
            Observable.combineLatest(Observable.just(editText1.getText().toString())
            , Observable.just(editText2.getText().toString())
            , (a, b) -> !TextUtils.isEmpty(a) && !TextUtils.isEmpty(b))
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.computation())
                    .subscribe( o -> {
                        if (o){
                            Toast.makeText(Example1Activity.this, "로그인", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(Example1Activity.this, "실패", Toast.LENGTH_SHORT).show();
                        }
                    });
        });
    }

}
