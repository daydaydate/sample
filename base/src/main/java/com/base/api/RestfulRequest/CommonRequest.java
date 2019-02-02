package com.base.api.RestfulRequest;

import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.base.untils.NetUtils;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;

import static com.android.volley.toolbox.HttpHeaderParser.parseCacheHeaders;
import static com.android.volley.toolbox.HttpHeaderParser.parseCharset;

/**
 * Created by yz on 2019/2/2.
 */

public class CommonRequest<TRequest, TResponse> extends AuthenticatedRequestBase<TResponse> {
    private String TAG = this.getClass().getSimpleName();
    private final static Gson gson = new Gson();
    private final Response.Listener<TResponse> listener;
    private final Class<TResponse> tResponseClazz;
    private TRequest request;
    private static final String PROTOCOL_CHARSET = "utf-8";
    private boolean cacheHit;
    private String mUrl;
    private NetCallback<TResponse> cb;

    /**
     * @param url
     * @param callback
     */
    public CommonRequest(String url, NetCallback<TResponse> callback) {
        this(url, null, null, false, callback);
    }

    /**
     * @param url
     * @param request
     * @param callback
     */
    public CommonRequest(String url, TRequest request, NetCallback<TResponse> callback) {
        this(url, request, null, false, callback);
    }

    /**
     * @param url
     * @param responseClass
     * @param cache
     * @param callback
     */
    public CommonRequest(String url, Class<TResponse> responseClass, boolean cache, NetCallback<TResponse> callback) {
        this(url, null, responseClass, cache, callback);
    }

    /**
     * @param url
     * @param request
     * @param responseClass
     * @param cache
     * @param callback
     */
    public CommonRequest(String url, TRequest request, Class<TResponse> responseClass, boolean cache, NetCallback<TResponse> callback) {
        super(request == null ? Method.GET : Request.Method.POST, url, cache, callback.getErrorListener());
        this.request = request;
        this.tResponseClazz = responseClass;
        this.mUrl = url;
        this.listener = callback.getSuccessListener();
        this.cb = callback;
    }

    /**
     * get请求返回null，post请求返回byte【】
     *
     * @return
     */
    @Override
    public byte[] getBody() {
        if (request == null) return null;

        String mRequestBody = gson.toJson(this.request);
        try {
            return mRequestBody == null ? null : mRequestBody.getBytes(PROTOCOL_CHARSET);
        } catch (UnsupportedEncodingException uee) {
            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                    mRequestBody, PROTOCOL_CHARSET);
            return null;
        }
    }

    /**
     * 这个是缓存的标记，与本地缓存相关，返回response时才会使用此标志
     *
     * @param tag
     */
    @Override
    public void addMarker(String tag) {
        super.addMarker(tag);
        cacheHit = tag.equals("cache-hit");
    }

    /**
     * clazz为空时，返回null
     *
     * @param response
     * @return
     */
    @Override
    protected Response<TResponse> parseNetworkResponse(NetworkResponse response) {
        //无需返回数据
        if (tResponseClazz == null) {
            return Response.success(null, parseCacheHeaders(response));
        }

        Gson gson = new Gson();
        //无网络时，使用本地缓存,通过url去匹配缓存，volley sdk是通过url创建不同的文件来实现缓存的
        if (!NetUtils.isConnect(mContext) && mRequestQueue.getCache().get(mUrl) != null) {
            String json = new String(mRequestQueue.getCache().get(mUrl).data);
            Log.d(TAG, "url==" + mUrl + ",json" + json);
            cb.fResponseCacheStatus = ResponseCacheStatus.StaleFromCache;
            return Response.success(gson.fromJson(json, tResponseClazz), parseCacheHeaders(response));
        }

        //数据是否有更新
        try {
            if (response.statusCode == 304) {
                //服务端返回缓存数据
                cb.fResponseCacheStatus = ResponseCacheStatus.NotModifiedFromServer;
            } else if (response.statusCode == 200) {
                if (cacheHit) {
                    //使用本地缓存
                    cb.fResponseCacheStatus = ResponseCacheStatus.FreshFromCache;
                } else {
                    //使用服务端更新数据
                    cb.fResponseCacheStatus = ResponseCacheStatus.NewFromServer;
                }
            } else {
                cb.fResponseCacheStatus = ResponseCacheStatus.NewFromServer;
            }

            Log.d(TAG, "fResponseCacheStatus = " + cb.fResponseCacheStatus);
            String json = new String(response.data, parseCharset(response.headers));
            return Response.success(gson.fromJson(json, tResponseClazz), parseCacheHeaders(response));
        } catch (UnsupportedEncodingException | JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(TResponse response) {
        listener.onResponse(response);
    }

}


