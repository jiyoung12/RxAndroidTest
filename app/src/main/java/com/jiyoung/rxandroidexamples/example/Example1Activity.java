package com.jiyoung.rxandroidexamples.example;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.jiyoung.rxandroidexamples.BaseActivity;
import com.jiyoung.rxandroidexamples.R;
import com.kakao.auth.ISessionCallback;
import com.kakao.auth.Session;
import com.kakao.util.exception.KakaoException;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

public class Example1Activity extends BaseActivity {

    private EditText editText1, editText2;
    private Button button;
    private SessionCallback callback;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example1);

        initKakao();
        initView();
        initEvent();

    }

    private void initKakao() {
        callback = new SessionCallback();
        Session.getCurrentSession().addCallback(callback);
        Session.getCurrentSession().checkAndImplicitOpen();
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

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (Session.getCurrentSession().handleActivityResult(requestCode, resultCode, data)){
            return;
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Session.getCurrentSession().removeCallback(callback);
    }

    private class SessionCallback implements ISessionCallback {

        @Override
        public void onSessionOpened() {
            redirectActivity();
        }

        @Override
        public void onSessionOpenFailed(KakaoException exception) {
            if (exception != null){
                exception.printStackTrace();
            }
        }
    }

    private void redirectActivity(){
        Intent intent = new Intent(this, Example5Activity.class);
        startActivity(intent);
        finish();
    }
}
