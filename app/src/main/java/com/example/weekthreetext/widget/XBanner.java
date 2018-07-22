package com.example.weekthreetext.widget;

import android.content.Context;
import android.content.res.TypedArray;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.bumptech.glide.Glide;
import com.example.weekthreetext.R;

import java.util.ArrayList;
import java.util.List;

/**
 * 自定义轮播图
 *
 * @author libing
 * @date 2018-7-21
 */
public class XBanner extends RelativeLayout {

    private static long DELAY = 2000;
    private static final int CODE_BANNER = 1;
    public static final String TAG = XBanner.class.getSimpleName();
    private ViewPager mPager;
    private LinearLayout mlPoints;
    private List<ImageView> images;
    private List<ImageView> points;
    private int itemCount;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case CODE_BANNER:
                        int index = mPager.getCurrentItem();
                        ++index;
                        Log.i(TAG, "index:" + index);
                        mPager.setCurrentItem(index);
                        changePoint(index % itemCount);
                        sendEmptyMessageDelayed(CODE_BANNER, DELAY);
                    break;
            }
        }
    };
    private int POINT_WIDTH;
    private int POINT_HEIGHT;

    public XBanner(Context context) {
        this(context, null);
    }

    public XBanner(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public XBanner(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(context, attrs, defStyleAttr);

    }

    private void init(Context context, AttributeSet attrs, int defStyleAttr) {
        // 使用打气筒填充一个布局
        inflate(context, R.layout.x_banner, this);

        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.XBanner);
        DELAY = typedArray.getInt(R.styleable.XBanner_delay, 2000);
        POINT_WIDTH = typedArray.getInt(R.styleable.XBanner_point_width, 20);
        POINT_HEIGHT = typedArray.getInt(R.styleable.XBanner_point_height, 20);

        // 查找控件
        mPager = findViewById(R.id.view_pager);
        mlPoints = findViewById(R.id.ll_points);
    }

    /**
     * 设置轮播数据
     *
     * @param urls
     */
    public void setImages(List<String> urls) {
        images = new ArrayList<>();
        points = new ArrayList<>();
        itemCount = urls.size();

        for (String url : urls) {
            // 创建图片
            ImageView imageView = new ImageView(getContext());
            Glide.with(getContext()).load(url).into(imageView);
            images.add(imageView);

            // 创建小圆点
            ImageView point = new ImageView(getContext());
            point.setImageResource(R.drawable.ll_point_red);
            points.add(point);

            // 设置布局参数
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(POINT_WIDTH, POINT_HEIGHT);
            params.leftMargin = 10;
            params.rightMargin = 10;

            mlPoints.addView(point, params);
        }

        // 设置适配器
        mPager.setAdapter(new XBannerAdaper());
        changePoint(0);
    }

    /**
     * 改变小圆点颜色
     * @param i
     */
    private void changePoint(int i) {
        for (int j = 0; j < itemCount; j++) {
            if (i == j) {
                points.get(j).setImageResource(R.drawable.ll_point_green);
            } else {
                points.get(j).setImageResource(R.drawable.ll_point_red);
            }
        }
    }

    /**
     * 开始轮播
     */
    public void start() {
        mHandler.sendEmptyMessageDelayed(CODE_BANNER, DELAY);
    }

    /**
     * 停止轮播
     */
    public void stop() {
        mHandler.removeCallbacksAndMessages(null);
    }

    class XBannerAdaper extends PagerAdapter {

        @Override
        public int getCount() {
            return Integer.MAX_VALUE;
        }

        @Override
        public boolean isViewFromObject(@NonNull View view, @NonNull Object o) {
            return view == o;
        }

        @NonNull
        @Override
        public Object instantiateItem(@NonNull ViewGroup container, int position) {
            ImageView imageView = images.get(position % itemCount);
            ViewGroup group = (ViewGroup) imageView.getParent();
            if (group != null) {
                group.removeAllViews();
            }
            container.addView(imageView);
            return imageView;
        }

        @Override
        public void destroyItem(@NonNull ViewGroup container, int position, @NonNull Object object) {
            container.removeView((View) object);
        }
    }

}
