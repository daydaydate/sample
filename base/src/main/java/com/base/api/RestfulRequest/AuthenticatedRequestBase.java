package com.base.api.RestfulRequest;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.CallSuper;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.base.api.YZ;

import java.util.HashMap;
import java.util.Map;


/**
 * Created by yuanjian on 2017/5/2.
 */

public abstract class AuthenticatedRequestBase<T> extends Request<T> {
    private final static String TAG = "AuthenticatedRequestBase";
    private static final int TIME_OUT = 30000;
    private static final int MAX_RETRIES = 1;
    private static final float BACKOFF_MULT = 2f;
    protected Context mContext;
    protected RequestQueue mRequestQueue;

    @SuppressLint("LongLogTag")
    public AuthenticatedRequestBase(int method, String url, boolean cache, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        //this.setShouldCache(cache);
        this.setRetryPolicy(new DefaultRetryPolicy(
                TIME_OUT,
                MAX_RETRIES,
                BACKOFF_MULT));

        mRequestQueue = YZ.getInstance().getRequestQueue();
        if (mRequestQueue == null) {
            throw new IllegalArgumentException("mRequestQueue can't be null");
        }

        mContext = YZ.getInstance().getContext();
        if (mContext == null) {
            throw new IllegalArgumentException("mContext can't be null");
        }

        //如果重新发出服务器请求的时候，需要清除之前的缓存。
        if (!cache) {
            Cache volleyCache = mRequestQueue.getCache();
            Cache.Entry cacheEntry = volleyCache.get(url);

            if (cacheEntry != null) {
                volleyCache.remove(url);
                Log.d(TAG, "remove volley cache:" + url);
            }
        }
        mRequestQueue.add(this);
    }


    @CallSuper
    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<>();
        String token = "............";
        //headers.put("Authorization", "bearer " + token);
        //针对Get方法，申明接受的enum类型
        // headers.put("Accept", "charset=utf-8");
        return headers;
    }

    @CallSuper
    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        return super.parseNetworkError(volleyError);
    }
}