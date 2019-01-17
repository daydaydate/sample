package com.base.api.RestfulRequest;

import android.annotation.SuppressLint;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.support.annotation.CallSuper;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;

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
    private static AppRequestQueue appRequestQueue;

    @SuppressLint("LongLogTag")
    public AuthenticatedRequestBase(int method, String url, boolean cache, Response.ErrorListener errorListener) {
        super(method, url, errorListener);
        //this.setShouldCache(cache);
        this.setRetryPolicy(new DefaultRetryPolicy(
                TIME_OUT,
                MAX_RETRIES,
                BACKOFF_MULT));

        if (appRequestQueue == null)
            throw new IllegalArgumentException("appRequestQueue can't be null");

        //如果重新发出服务器请求的时候，需要清除之前的缓存。
        if (!cache) {
            Cache volleyCache = appRequestQueue.getRequestQueue().getCache();
            Cache.Entry cacheEntry = volleyCache.get(url);

            if (cacheEntry != null) {
                volleyCache.remove(url);
                Log.d(TAG, "remove volley cache:" + url);
            }
        }

        appRequestQueue.getRequestQueue().add(this);
    }

    public static void setAppRequestQueue(AppRequestQueue appRequestQueue) {
        AuthenticatedRequestBase.appRequestQueue = appRequestQueue;
    }

    /**
     * PUT,POST method is need
     *
     * @return
     */
    @Override
    public String getBodyContentType() {
        return "application/vnd.fht360.enumAsString+json";
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