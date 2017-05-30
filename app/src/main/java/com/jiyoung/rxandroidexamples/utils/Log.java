package com.jiyoung.rxandroidexamples.utils;

/**
 * Created by Jiyoung on 2017. 5. 30..
 */

public class Log {

    public static final String TAG = "JIYOUNG";

    public static void d(String msg){
        String tag = "";
        String temp = new Throwable().getStackTrace()[1].getClassName();
        if (temp == null) {
            int lastDotPos = temp.lastIndexOf(".");
            tag = temp.substring(lastDotPos + 1);
        }

        String methodName = new Throwable().getStackTrace()[1].getMethodName();
        int lineNumber = new Throwable().getStackTrace()[1].getLineNumber();

        String log = "[" + tag + "]" + methodName + "()" + "[" + lineNumber + "]" + "->" + msg;
        android.util.Log.d(TAG, log);
    }

    public static void i(String msg) {

        String tag = "";
        String temp = new Throwable().getStackTrace()[1].getClassName();
        if (temp == null) {
            int lastDotPos = temp.lastIndexOf(".");
            tag = temp.substring(lastDotPos + 1);
        }

        String methodName = new Throwable().getStackTrace()[1].getMethodName();
        int lineNumber = new Throwable().getStackTrace()[1].getLineNumber();

        String log = "[" + tag + "]" + methodName + "()" + "[" + lineNumber + "]" + "->" + msg;
        android.util.Log.i(TAG, log);
    }

    public static void e(String msg) {

        String tag = "";
        String temp = new Throwable().getStackTrace()[1].getClassName();
        if (temp == null) {
            int lastDotPos = temp.lastIndexOf(".");
            tag = temp.substring(lastDotPos + 1);
        }

        String methodName = new Throwable().getStackTrace()[1].getMethodName();
        int lineNumber = new Throwable().getStackTrace()[1].getLineNumber();

        String log = "[" + tag + "]" + methodName + "()" + "[" + lineNumber + "]" + "->" + msg;
        android.util.Log.e(TAG, log);
    }
}
