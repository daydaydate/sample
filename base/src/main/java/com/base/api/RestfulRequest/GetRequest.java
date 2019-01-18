package com.base.api.RestfulRequest;

import android.os.Handler;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.base.untils.NetUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;

import static com.android.volley.toolbox.HttpHeaderParser.parseCacheHeaders;
import static com.android.volley.toolbox.HttpHeaderParser.parseCharset;

public class GetRequest<TResponse> extends AuthenticatedRequestBase<TResponse> {

    private final Response.Listener<TResponse> listener;
    private final Class<TResponse> clazz;
    private final static String TAG = "GetRequest";
    private String mUrl;
    private NetCallback<TResponse> cb;
    private boolean cacheHit;

    public GetRequest(String url, Class<TResponse> clazz, boolean cache, NetCallback<TResponse> callback) {
        super(Request.Method.GET, url, cache, callback.getErrorListener());
        this.listener = callback.getSuccessListener();
        this.clazz = clazz;
        this.mUrl = url;
        this.cb = callback;

        //无网络时300ms后返回callback
        if (!NetUtils.isConnect(mContext) && mRequestQueue.getCache().get(url) == null) {
            Handler handler = new Handler();
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    cb.OnNetworkOff();
                }
            }, 300);
        }
    }

    @Override
    public void addMarker(String tag) {
        super.addMarker(tag);
        cacheHit = tag.equals("cache-hit");
    }

    @Override
    protected Response<TResponse> parseNetworkResponse(NetworkResponse response) {
        Gson gson = new Gson();

        //无网络时，使用本地缓存
        if (!NetUtils.isConnect(mContext) && mRequestQueue.getCache().get(mUrl) != null) {
            String json = new String(mRequestQueue.getCache().get(mUrl).data);
            Log.d(TAG, "url==" + mUrl + ",json" + json);
            cb.fResponseCacheStatus = ResponseCacheStatus.StaleFromCache;
            return Response.success(gson.fromJson(json, clazz), parseCacheHeaders(response));
        }
        //数据是否有更新
        try {
            if (response.statusCode == 304) {
                cb.fResponseCacheStatus = ResponseCacheStatus.NotModifiedFromServer;
            } else if (response.statusCode == 200) {
                if (cacheHit) {
                    cb.fResponseCacheStatus = ResponseCacheStatus.FreshFromCache;
                } else {
                    cb.fResponseCacheStatus = ResponseCacheStatus.NewFromServer;
                }
            } else {
                cb.fResponseCacheStatus = ResponseCacheStatus.NewFromServer;
            }

            Log.d(TAG, "fResponseCacheStatus = " + cb.fResponseCacheStatus);
            String json = new String(response.data, parseCharset(response.headers));
            return Response.success(gson.fromJson(json, clazz), parseCacheHeaders(response));
        } catch (UnsupportedEncodingException | JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(TResponse response) {
        listener.onResponse(response);
    }

    @Override
    protected VolleyError parseNetworkError(VolleyError volleyError) {
        return super.parseNetworkError(volleyError);
    }
}
