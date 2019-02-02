package com.aiidayang.moudle.news;

import android.annotation.SuppressLint;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import com.aiidayang.R;
import com.aiidayang.base.BaseFragment;
import com.aiidayang.moudle.news.adapter.NewsAdapter;
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
    private int lastX = 0;
    private int lastY = 0;
    private int screenWidth;
    private int screenHeight;

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

        initButton();
    }

    @SuppressLint("ClickableViewAccessibility")
    private void initButton() {
        Button button = mView.findViewById(R.id.button);
        // 获取屏幕的宽度和高度
        WindowManager windowManager = (WindowManager) getActivity().getSystemService(Context.WINDOW_SERVICE);
        screenWidth = windowManager.getDefaultDisplay().getWidth();
        screenHeight = windowManager.getDefaultDisplay().getHeight();

        button.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();
                        break;
                    case MotionEvent.ACTION_MOVE:
                        int dx = (int) (event.getRawX() - lastX);
                        int dy = (int) (event.getRawY() - lastY);

                        int l = v.getLeft() + dx;
                        int r = v.getRight() + dx;
                        int t = v.getTop() + dy;
                        int b = v.getBottom() + dy;

                        if (l < 0) {
                            l = 0;
                            r = l + v.getWidth();
                        }

                        if (t < 0) {
                            t = 0;
                            b = t + v.getHeight();

                        }

                        if (r > screenWidth) {
                            r = screenWidth;
                            l = r - v.getWidth();
                        }

                        if (b > screenHeight) {
                            b = screenHeight;
                            l = b - v.getHeight();
                        }

                        v.layout(l, t, r, b);

                        //更新位置
                        lastX = (int) event.getRawX();
                        lastY = (int) event.getRawY();
                        v.postInvalidate();//重新绘制
                        break;
                    default:
                        break;
                }
                return false;
            }


        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    private void initData() {
        NewsApi.getInfo(new NetCallback<News>() {
            @Override
            public void OnSuccess(final News result) {
                Log.d(TAG, "news ==" + result.getResult().getData().toString());
            }

            @Override
            public void OnError(RestfulError error) {
            }
        });

        NewsApi.postInfo(new NetCallback<News>() {
            @Override
            public void OnSuccess(final News result) {
                mAdapter.setData(result.getResult().getData());
            }

            @Override
            public void OnError(RestfulError error) {
            }
        });

      /*  NewsModel newsModel = new NewsModel(getActivity());
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
        }*/
    }


}
