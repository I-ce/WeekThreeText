package com.example.weekthreetext.model;

import com.example.weekthreetext.bean.NewsBean;
import com.example.weekthreetext.net.OkHttpUtils;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class DataModelImple {

    private String url = "http://39.108.3.12:3000/v1/restaurants?offset=";
    private String url1 = "&limit=4&lng=116.29845&lat=39.95933";
    public void getDatas(final DataModel dataModel, int page) {

        OkHttpUtils.doGet(url + page + url1, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                dataModel.setOnError();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                Gson gson = new Gson();
                NewsBean newsBean = gson.fromJson(response.body().string(), NewsBean.class);
                dataModel.setOnSuccess(newsBean.getData());
            }
        });
    }
}
