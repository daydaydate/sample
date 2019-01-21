package com.aiidayang.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.aiidayang.R;
import com.aiidayang.adapter.NewsAdapter;
import com.aiidayang.base.BaseFragment;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.base.api.RestfulRequest.NetCallback;
import com.base.api.RestfulRequest.RestfulError;
import com.base.api.api.NewsApi;
import com.base.api.bean.News;
import com.business.api.model.NewsModel;
import com.business.utils.thread.ThreadUtils;
import com.google.gson.Gson;

import org.json.JSONObject;

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
       /* NewsApi.getInfo(new NetCallback<News>() {
            @Override
            public void OnSuccess(final News result) {
                mAdapter.setData(result.getResult().getData());
            }

            @Override
            public void OnError(RestfulError error) {
            }
        });*/

        NewsModel newsModel = new NewsModel(getActivity());
        try {
            newsModel.getInfo(new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(final JSONObject response) {
                    ThreadUtils.runInUIThread(new Runnable() {
                        @Override
                        public void run() {
                            News news = new Gson().fromJson(response.toString(), News.class);
                            mAdapter.setData(news.getResult().getData());
                        }
                    });
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
