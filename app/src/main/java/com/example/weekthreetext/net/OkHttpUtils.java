package com.example.weekthreetext.net;

import javax.security.auth.callback.Callback;

import okhttp3.OkHttpClient;
import okhttp3.Request;

public class OkHttpUtils {
    private static OkHttpClient client = null;
    public static OkHttpClient getInstance(){
        if (client == null){
            synchronized (OkHttpUtils.class){
                if (client == null){
                    client = new OkHttpClient();
                }
            }
        }
        return client;
    }
    public static void doGet(String url, okhttp3.Callback callback){
        Request request = new Request.Builder()
                .url(url)
                .build();
        getInstance().newCall(request).enqueue(callback);


    }
}
