package com.jiyoung.rxandroidexamples;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.jiyoung.rxandroidexamples.example.Example1Activity;
import com.jiyoung.rxandroidexamples.example.Example2Activity;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ClickListener{

    private RecyclerView recyclerView;
    private ExampleAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setActionBar();
        setupExampleList();
    }

    private void setActionBar(){
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null){
            actionBar.setTitle("RxAndroid Examples");
        }
    }

    private void setupExampleList(){

        recyclerView = (RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setHasFixedSize(false);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ExampleAdapter(this, getList());
        recyclerView.setAdapter(adapter);
        adapter.setClickListener(this);

    }

    private List<ExampleActivityAndName> getList(){

        List<ExampleActivityAndName> exampleList = new ArrayList<>();
        exampleList.add(new ExampleActivityAndName(Example1Activity.class, "Button Counter"));
        exampleList.add(new ExampleActivityAndName(Example2Activity.class, "Retro Test"));

        return exampleList;

    }

    @Override
    public void itemClicked(View view, int position) {

        Intent intent = new Intent(MainActivity.this, getList().get(position).mActivityClass);
        startActivity(intent);
    }
}
