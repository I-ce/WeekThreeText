package com.example.weekthreetext.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.weekthreetext.MainActivity;
import com.example.weekthreetext.R;
import com.example.weekthreetext.SecondActivity;
import com.example.weekthreetext.bean.NewsBean;

import java.util.List;

public class MyRecyAdapter extends RecyclerView.Adapter<MyRecyAdapter.TextHolder>{

    public static final String TAG = MyRecyAdapter.class.getSimpleName();
    private List<NewsBean.DataBean> lists;
    private Context context;

    public MyRecyAdapter(Context context, List<NewsBean.DataBean> dataBeans) {
        this.context = context;
        this.lists = dataBeans;
    }

    @NonNull
    @Override
    public TextHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = View.inflate(context, R.layout.home_xlist_view_item, null);
        return new TextHolder(view);
    }

    @Override
    public int getItemCount() {
        int size = lists.size();
        Log.i(TAG, "size:" + size);
        return size;
    }

    @Override
    public void onBindViewHolder(@NonNull final TextHolder holder, final int position) {
        // Glide.with(context).load(lists.get(position).getPic_url().split("\\|")).into(holder.home_item_image);
        Glide.with(context).load(lists.get(position).getPic_url()).into(holder.home_item_image);
        holder.home_item_title.setText(lists.get(position).getName());
        holder.home_item_month_sales_tip.setText(lists.get(position).getMonth_sales_tip());
        holder.home_item_distance.setText(lists.get(position).getDistance());
        holder.home_item_min_price_tip.setText(lists.get(position).getMin_price_tip());
        holder.home_item_discounts2_imfo1.setText(lists.get(position).getDiscounts2().get(0).getInfo());
        holder.home_item_discounts2_imfo2.setText(lists.get(position).getDiscounts2().get(1).getInfo());
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String id = lists.get(position).get_id();
                Intent intent = new Intent(context, SecondActivity.class);
                intent.putExtra("id",id);
                context.startActivity(intent);

            }
        });

    }


    static class TextHolder extends RecyclerView.ViewHolder {


        private final ImageView home_item_image;
        private final TextView home_item_title;
        private final TextView home_item_month_sales_tip;
        private final TextView home_item_distance;
        private final TextView home_item_min_price_tip;
        private final TextView home_item_discounts2_imfo1;
        private final TextView home_item_discounts2_imfo2;

        public TextHolder(View itemView) {
            super(itemView);
            home_item_image = itemView.findViewById(R.id.home_item_image);
            home_item_title = itemView.findViewById(R.id.home_item_title);
            home_item_month_sales_tip = itemView.findViewById(R.id.home_item_month_sales_tip);
            home_item_distance = itemView.findViewById(R.id.home_item_distance);
            home_item_min_price_tip = itemView.findViewById(R.id.home_item_min_price_tip);
            home_item_discounts2_imfo1 = itemView.findViewById(R.id.home_item_discounts2_imfo1);
            home_item_discounts2_imfo2 = itemView.findViewById(R.id.home_item_discounts2_imfo2);
        }

    }

    private DataCallBakc callBakc;

    public void setCallBakc(DataCallBakc callBakc) {
        this.callBakc = callBakc;
    }

    public interface DataCallBakc{
        void getId(String string);
    }
}
