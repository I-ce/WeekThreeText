package com.example.weekthreetext.presenter;

import com.example.weekthreetext.bean.NewsBean;
import com.example.weekthreetext.model.DataModel;
import com.example.weekthreetext.model.DataModelImple;
import com.example.weekthreetext.view.DataView;

import java.util.List;

public class DataPresenterImpl implements DataPresenter {


    private DataView dataView;
    private DataModelImple dataModelImple;
    public DataPresenterImpl(DataView dataView) {
        this.dataView = dataView;
        dataModelImple = new DataModelImple();
    }


    @Override
    public void onDestory() {
        dataView = null;
    }

    public void getData(int page) {

        dataModelImple.getDatas(new DataModel() {
            @Override
            public void setOnSuccess(List<NewsBean.DataBean> data) {
                dataView.onSuccess(data);
            }

            @Override
            public void setOnError() {
                dataView.onError();
            }
        }, page);
    }
}
