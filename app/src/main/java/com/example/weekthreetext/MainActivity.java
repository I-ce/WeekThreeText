package com.example.weekthreetext;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;

import com.example.weekthreetext.adapter.MyRecyAdapter;
import com.example.weekthreetext.bean.NewsBean;
import com.example.weekthreetext.presenter.DataPresenterImpl;
import com.example.weekthreetext.view.DataView;
import com.scwang.smartrefresh.header.MaterialHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshLoadmoreListener;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements DataView {
    List<NewsBean.DataBean> beans = new ArrayList<>();
    RefreshLayout smart_fresh;
    private Button btn_paixu;
    private Button btn_xiaoliang;
    private RecyclerView rech_list;
    private DataPresenterImpl presenter;
    private int page = 1;
    private MyRecyAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();

        presenter = new DataPresenterImpl(this);
        rech_list.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        presenter.getData(page);
        smart_fresh.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //  refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                page = 1;
                beans.clear();
                presenter.getData(page);

            }
        });
        smart_fresh.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                presenter.getData(page);

            }
        });
        adapter = new MyRecyAdapter(MainActivity.this, beans);
        rech_list.setAdapter(adapter);
//        adapter.setCallBakc(new MyRecyAdapter.DataCallBakc() {
//            @Override
//            public void getId(String string) {
//
//            }
//        });
    }

    private void initViews() {
        btn_paixu = findViewById(R.id.btn_paixu);
        btn_xiaoliang = findViewById(R.id.btn_xiaoliang);
        smart_fresh = findViewById(R.id.smart_refresh);
        rech_list = findViewById(R.id.recy_view);
//        smart_fresh.setRefreshHeader(new MaterialHeader(MainActivity.this).setShowBezierWave(true));
        smart_fresh.setRefreshFooter(new BallPulseFooter(MainActivity.this).setSpinnerStyle(SpinnerStyle.Scale));
    }

    @Override
    public void onSuccess(final List<NewsBean.DataBean> data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                beans.addAll(data);
                adapter.notifyDataSetChanged();
                smart_fresh.finishRefresh();
                smart_fresh.finishLoadmore();
            }
        });

    }

    @Override
    public void onError() {

    }
}
