package com.jiyoung.rxandroidexamples;

import android.app.Activity;

/**
 * Created by Jiyoung on 2017. 5. 30..
 */

public class ExampleActivityAndName {

    public Class<? extends Activity> mActivityClass;
    public String mActivityName;

    public ExampleActivityAndName(Class<? extends Activity> mActivityClass, String mActivityName) {
        this.mActivityClass = mActivityClass;
        this.mActivityName = mActivityName;
    }
}
