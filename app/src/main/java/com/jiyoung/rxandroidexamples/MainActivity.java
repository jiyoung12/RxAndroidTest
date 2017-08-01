package com.jiyoung.rxandroidexamples;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Base64;
import android.view.View;

import com.jiyoung.rxandroidexamples.example.Example1Activity;
import com.jiyoung.rxandroidexamples.example.Example2Activity;
import com.jiyoung.rxandroidexamples.example.Example3Activity;
import com.jiyoung.rxandroidexamples.example.Example4Activity;
import com.jiyoung.rxandroidexamples.example.Example5Activity;
import com.jiyoung.rxandroidexamples.utils.Log;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

import static com.kakao.util.helper.Utility.getPackageInfo;

public class MainActivity extends AppCompatActivity implements ClickListener{

    private RecyclerView recyclerView;
    private ExampleAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setActionBar();
        setupExampleList();
        Log.e(getKeyHash(this));
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
        exampleList.add(new ExampleActivityAndName(Example1Activity.class, "Login"));
        exampleList.add(new ExampleActivityAndName(Example2Activity.class, "Search"));
        exampleList.add(new ExampleActivityAndName(Example3Activity.class, "Retrofit"));
        exampleList.add(new ExampleActivityAndName(Example4Activity.class, "asyncHttp"));
        exampleList.add(new ExampleActivityAndName(Example5Activity.class, "SNS Login"));

        return exampleList;

    }

    @Override
    public void itemClicked(View view, int position) {

        Intent intent = new Intent(MainActivity.this, getList().get(position).mActivityClass);
        intent.putExtra("title", getList().get(position).mActivityName);
        startActivity(intent);
    }

    public static String getKeyHash(final Context context) {
        PackageInfo packageInfo = getPackageInfo(context, PackageManager.GET_SIGNATURES);
        if (packageInfo == null)
            return null;

        for (Signature signature : packageInfo.signatures) {
            try {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                return Base64.encodeToString(md.digest(), Base64.NO_WRAP);
            } catch (NoSuchAlgorithmException e) {
                Log.d( "Unable to get MessageDigest. signature=" + signature + e);
            }
        }
        return null;
    }
}
