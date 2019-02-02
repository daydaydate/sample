package com.business.api.base;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.business.api.CommonJsonObjectRequest;
import com.business.api.RequestQueueFactory;

import org.json.JSONObject;

/**
 * 网络处理基类
 */
public abstract class BaseNetModel {

    protected RequestQueue requestQueue;
    protected Context context;
    protected Object mTag;

    protected BaseNetModel(Context context) {
        this.context = context.getApplicationContext();
        requestQueue = RequestQueueFactory.getAsynRequeQueueRespond(this.context);
    }

    protected BaseNetModel(Context context, boolean isAsyn) {
        this.context = context.getApplicationContext();
        requestQueue = isAsyn ? RequestQueueFactory.getAsynRequeQueueRespond(this.context)
                : RequestQueueFactory.getRequestQueue(context);
    }

    /**
     * 推荐用页面ClassName+时间戳
     *
     * @param tag
     */
    public void setTag(Object tag) {
        this.mTag = tag;
    }

    public void destroy() {
        if (mTag != null) {
            cancelTaskByTag(mTag);
        }
        requestQueue = null;
        context = null;
    }

    public void cancelTaskByTag(Object tag) {
        if (requestQueue != null) {
            requestQueue.cancelAll(tag);
        }
    }


    public void addRequest(String path, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        addRequest(path, true, jsonRequest, listener, errorListener);
    }

    /**
     * @param path          不带域名的接口路径
     * @param withTag       是否带上页面的tag
     * @param jsonRequest
     * @param listener
     * @param errorListener
     */
    public void addRequest(String path, boolean withTag, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        addRequestUrl(path, withTag, jsonRequest, listener, errorListener);
    }

    /**
     * @param url           完整接口地址
     * @param withTag
     * @param jsonRequest
     * @param listener
     * @param errorListener
     */
    public void addRequestUrl(String url, boolean withTag, JSONObject jsonRequest, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        if (jsonRequest == null) {
            jsonRequest = new JSONObject();
        }
        CommonJsonObjectRequest request = new CommonJsonObjectRequest(context, url, jsonRequest, listener, errorListener);
        if (withTag && mTag != null) {
            request.setTag(mTag);
        }
        requestQueue.add(request);
    }

}
