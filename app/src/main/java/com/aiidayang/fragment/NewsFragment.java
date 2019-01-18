package com.aiidayang.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aiidayang.R;
import com.aiidayang.adapter.NewsAdapter;
import com.aiidayang.base.BaseFragment;
import com.base.api.RestfulRequest.NetCallback;
import com.base.api.RestfulRequest.RestfulError;
import com.base.api.api.NewsApi;
import com.base.api.bean.News;

public class NewsFragment extends BaseFragment {
    private String TAG = this.getClass().getSimpleName();
    private View mView;
    private NewsAdapter mAdapter;

    public NewsFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.fragment_news, container, false);
        initView();
        initData();
        return mView;
    }

    private void initView() {
        RecyclerView recyclerView = mView.findViewById(R.id.recyclerview);
        recyclerView.setLayoutManager(new LinearLayoutManager(mContext));
        mAdapter = new NewsAdapter(getActivity());
        recyclerView.setAdapter(mAdapter);
    }

    private void initData() {
        NewsApi.getInfo(new NetCallback<News>() {
            @Override
            public void OnSuccess(final News result) {
                mAdapter.setData(result.getResult().getData());
            }

            @Override
            public void OnError(RestfulError error) {
            }
        });
    }


}
