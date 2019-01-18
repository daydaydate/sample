package com.aiidayang.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.aiidayang.R;
import com.base.api.bean.News;
import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends RecyclerView.Adapter<NewsAdapter.NewsHolder> {
    private List<News.ResultBean.DataBean> mData;
    private Context mContext;

    public NewsAdapter(Context mContext) {
        this.mContext = mContext;
        mData = new ArrayList<>();
    }

    public void setData(List<News.ResultBean.DataBean> data) {
        mData.clear();
        mData.addAll(data);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public NewsHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        return new NewsHolder(LayoutInflater.from(mContext).inflate(R.layout.view_news_holder, viewGroup, false));
    }

    @Override
    public void onBindViewHolder(@NonNull NewsHolder newsHolder, int i) {
        News.ResultBean.DataBean dataBean = mData.get(i);
        newsHolder.title.setText(dataBean.getTitle());
        newsHolder.category.setText(dataBean.getCategory());
        newsHolder.author.setText(dataBean.getAuthor_name());
        newsHolder.time.setText(dataBean.getDate());

        Glide.with(mContext).load(dataBean.getThumbnail_pic_s()).asBitmap().into(newsHolder.imageView1);
        Glide.with(mContext).load(dataBean.getThumbnail_pic_s02()).asBitmap().into(newsHolder.imageView2);
        Glide.with(mContext).load(dataBean.getThumbnail_pic_s03()).asBitmap().into(newsHolder.imageView3);
    }

    @Override
    public int getItemCount() {
        return mData == null ? 0 : mData.size();
    }


    public class NewsHolder extends RecyclerView.ViewHolder {
        TextView title, category, author, time;
        ImageView imageView1, imageView2, imageView3;

        public NewsHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.tv_title);
            category = itemView.findViewById(R.id.tv_category);
            author = itemView.findViewById(R.id.tv_author_name);
            time = itemView.findViewById(R.id.tv_time);
            imageView1 = itemView.findViewById(R.id.imageView1);
            imageView2 = itemView.findViewById(R.id.imageView2);
            imageView3 = itemView.findViewById(R.id.imageView3);
        }
    }
}
