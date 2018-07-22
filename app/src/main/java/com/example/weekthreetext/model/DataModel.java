package com.example.weekthreetext.model;

import com.example.weekthreetext.bean.NewsBean;

import java.util.List;

public interface DataModel {
    void setOnSuccess(List<NewsBean.DataBean> data);
    void setOnError();
}
