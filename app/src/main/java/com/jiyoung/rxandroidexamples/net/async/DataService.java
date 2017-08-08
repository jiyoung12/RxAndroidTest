package com.jiyoung.rxandroidexamples.net.async;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
import com.jiyoung.rxandroidexamples.net.Urls;
import com.jiyoung.rxandroidexamples.net.model.MainWeather;
import com.jiyoung.rxandroidexamples.utils.Log;
import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by jiyoung on 2017. 8. 1..
 */

public class DataService {

    private static final String TAG = "DataService";
    private static final int TIME_OUT = 15000;

    private static AsyncHttpClient client = new AsyncHttpClient();
    private Context mContext;
    protected EventListener eventListener;

    public DataService(Context ctx) {

        mContext = ctx;
        client.setTimeout(TIME_OUT); //타임아웃 초
        client.setMaxConnections(3); //재시도 횟수
        client.setEnableRedirects(false, false, true);

    }

    public DataService setEventListener(EventListener listener) {

        eventListener = listener;
        return this;

    }

    public void cancelRequest() {

        if (client != null)
            client.cancelRequests(mContext, true);

    }


//    public void addHeader(){
//        client.addHeader();
//    }


    public void requestWeatherInfo(String city) {
        RequestParams params = new RequestParams();
        params.add("appid", "14ab040dfc2cd23c5cd12d2362fd4981");
        params.add("q", city);

        client.get(Urls.getWatherInfoUrl(), params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                if (responseBody == null)
                    return;

                Log.d(new String(responseBody));
                if (eventListener != null){

                    try {
                        JSONObject object = new JSONObject(new String(responseBody));
                        JSONObject coord = object.getJSONObject("coord");
                        Log.d(coord.getString("lon"));

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                    MainWeather weather = new Gson().fromJson(new String(responseBody), MainWeather.class);
                    eventListener.requestWeatherInfo_Success(weather);

                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                Toast.makeText(mContext, responseBody.toString(), Toast.LENGTH_LONG).show();
                error.printStackTrace();
            }
        });


    }

    public static abstract class EventListener {

        public void requestWeatherInfo_Success(MainWeather weather) {
        }

        public void requestWeatherInfo_Fail() {
        }
    }
}
