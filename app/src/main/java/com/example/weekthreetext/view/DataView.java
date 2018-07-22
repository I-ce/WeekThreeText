package com.example.weekthreetext.view;

import com.example.weekthreetext.bean.NewsBean;

import java.util.List;

public interface DataView {
    void onSuccess(List<NewsBean.DataBean> data);
    void onError();
}
