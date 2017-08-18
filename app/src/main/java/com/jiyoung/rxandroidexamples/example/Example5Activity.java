package com.jiyoung.rxandroidexamples.example;

import android.os.Bundle;
import android.text.TextUtils;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import com.jakewharton.rxbinding2.widget.RxCompoundButton;
import com.jiyoung.rxandroidexamples.BaseActivity;
import com.jiyoung.rxandroidexamples.R;
import com.jiyoung.rxandroidexamples.utils.Log;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Consumer;

public class Example5Activity extends BaseActivity {

    private EditText et1, et2, et3;
    private CheckBox cb1, cb2, cb3;
    private Button btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_example5);

        et1 = (EditText) findViewById(R.id.editText1);
        et2 = (EditText) findViewById(R.id.editText2);
        et3 = (EditText) findViewById(R.id.editText3);

        cb1 = (CheckBox) findViewById(R.id.checkBox1);
        cb2 = (CheckBox) findViewById(R.id.checkBox2);
        cb3 = (CheckBox) findViewById(R.id.checkBox3);

        btn = (Button) findViewById(R.id.button);

        Observable<Boolean> check1 = RxCompoundButton.checkedChanges(cb1);
        check1.subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(@NonNull Boolean aBoolean) throws Exception {
                et1.setEnabled(aBoolean);
            }
        });

        Observable<Boolean> exist1 = Observable.just(et1.getText().toString())
                .map(text -> TextUtils.isEmpty(text));

        Observable<Boolean> check2 = RxCompoundButton.checkedChanges(cb2);
        check2.subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(@NonNull Boolean aBoolean) throws Exception {
                et2.setEnabled(aBoolean);
            }
        });

        Observable<Boolean> exist2 = Observable.just(et2.getText().toString())
                .map(text -> TextUtils.isEmpty(text));

        Observable<Boolean> check3 = RxCompoundButton.checkedChanges(cb3);
        check3.subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(@NonNull Boolean aBoolean) throws Exception {
                et3.setEnabled(aBoolean);
            }
        });

        Observable<Boolean> exist3 = Observable.just(et3.getText().toString())
                .map(text -> TextUtils.isEmpty(text));


        Observable.combineLatest(exist1, exist2, exist3, (textExist1, textExist2, textExist3) -> textExist1 && textExist2 && textExist3)
                .subscribe(new Observer<Boolean>() {
                    @Override
                    public void onSubscribe(@NonNull Disposable d) {
                        Log.e("onSubscribe");
                    }

                    @Override
                    public void onNext(@NonNull Boolean aBoolean) {
                        btn.setEnabled(aBoolean);
                        Log.e("onNext " + aBoolean);
                    }

                    @Override
                    public void onError(@NonNull Throwable e) {
                        Log.e(e.getMessage());
                    }

                    @Override
                    public void onComplete() {
                        Log.e("onComplete");
                    }
                });
    }
}
