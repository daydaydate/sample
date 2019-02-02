package com.base.api;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.base.api.RestfulRequest.AppRequestQueue;
import com.base.api.RestfulRequest.AuthenticatedRequestBase;

public class YZ implements AppRequestQueue {
    private static final int DEFAULT_VOLLEY_CACHE_SIZE = 100 * 1024 * 1024;
    private Context context;
    private int cacheSize;

    private YZ() {
    }

    @Override
    public RequestQueue getRequestQueue() {
        return Volley.newRequestQueue(context, cacheSize);
    }

    public Context getContext() {
        return context;
    }

    private static class SingletonHolder {
        private static YZ instance = new YZ();
    }

    public static YZ getInstance() {
        return SingletonHolder.instance;
    }

    /**
     * need a app context
     *
     * @param appContext
     */
    public void init(final Context appContext) {
        init(appContext, DEFAULT_VOLLEY_CACHE_SIZE);
    }

    /**
     * @param appContext
     * @param cacheSize
     */
    public void init(final Context appContext, final int cacheSize) {
        this.context = appContext;
        this.cacheSize = cacheSize;
    }





}
