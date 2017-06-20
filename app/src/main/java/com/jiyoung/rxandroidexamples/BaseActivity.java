package com.jiyoung.rxandroidexamples;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;

/**
 * Created by Jiyoung on 2017-06-20.
 */

public class BaseActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setActionTitle();
    }

    private void setActionTitle() {

        ActionBar actionBar = getSupportActionBar();
        actionBar.setTitle(getIntent()!= null? getIntent().getStringExtra("title") : "RxAndroid Examples");
    }
}
