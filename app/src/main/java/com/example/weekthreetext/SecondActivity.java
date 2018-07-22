package com.example.weekthreetext;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Button;
import android.widget.Toast;

import com.example.weekthreetext.adapter.MyRecyAdapter;
import com.example.weekthreetext.bean.NewsBean;
import com.example.weekthreetext.presenter.DataPresenterImpl;
import com.example.weekthreetext.view.DataView;
import com.example.weekthreetext.widget.XBanner;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.constant.SpinnerStyle;
import com.scwang.smartrefresh.layout.footer.BallPulseFooter;
import com.scwang.smartrefresh.layout.listener.OnLoadmoreListener;
import com.scwang.smartrefresh.layout.listener.OnRefreshListener;

import java.util.ArrayList;
import java.util.List;

public class SecondActivity extends AppCompatActivity implements DataView{

    List<NewsBean.DataBean> beans = new ArrayList<>();
    RefreshLayout smart_fresh1;
    private Button btn_paixu;
    private Button btn_xiaoliang;
    private RecyclerView rech_list1;
    private DataPresenterImpl presenter;
    private int page = 1;
    private MyRecyAdapter adapter;

    private XBanner xBanner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        initViews();
        presenter = new DataPresenterImpl(this);
        rech_list1.setLayoutManager(new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false));
        presenter.getData(page);
        smart_fresh1.setOnRefreshListener(new OnRefreshListener() {
            @Override
            public void onRefresh(RefreshLayout refreshlayout) {
                //  refreshlayout.finishRefresh(2000/*,false*/);//传入false表示刷新失败
                page = 1;
                beans.clear();
                presenter.getData(page);

            }
        });
        smart_fresh1.setOnLoadmoreListener(new OnLoadmoreListener() {
            @Override
            public void onLoadmore(RefreshLayout refreshlayout) {
                page++;
                presenter.getData(page);

            }
        });
        adapter = new MyRecyAdapter(SecondActivity.this, beans);
        rech_list1.setAdapter(adapter);

    }
    private void initViews() {
        btn_paixu = findViewById(R.id.btn_paixu);
        btn_xiaoliang = findViewById(R.id.btn_xiaoliang);
        smart_fresh1 = findViewById(R.id.smart_refresh1);

        xBanner = findViewById(R.id.x_banner);
        xBanner.start();

        ArrayList<String> urls = new ArrayList<>();
        urls.add("http://pic.616pic.com/ys_b_img/00/66/56/wDyzYR78cD.jpg");
        urls.add("http://pic.616pic.com/ys_b_img/00/66/59/wBtbUCeSx5.jpg");
        urls.add("http://pic.616pic.com/ys_b_img/00/08/85/j08h8TdKHq.jpg");
        urls.add("http://pic.616pic.com/ys_b_img/00/57/40/sYQzN9sJ2L.jpg");
        xBanner.setImages(urls);

        rech_list1 = findViewById(R.id.recy_view1);
//        smart_fresh.setRefreshHeader(new MaterialHeader(MainActivity.this).setShowBezierWave(true));
        smart_fresh1.setRefreshFooter(new BallPulseFooter(SecondActivity.this).setSpinnerStyle(SpinnerStyle.Scale));
    }

    @Override
    public void onSuccess(final List<NewsBean.DataBean> data) {
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                beans.addAll(data);
                adapter.notifyDataSetChanged();
                smart_fresh1.finishRefresh();
                smart_fresh1.finishLoadmore();
            }
        });
    }

    @Override
    public void onError() {

    }
}
