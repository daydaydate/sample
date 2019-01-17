package com.base.api.RestfulRequest;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;

import static com.android.volley.toolbox.HttpHeaderParser.parseCacheHeaders;

/**
 * Created by yuanjian on 2017/5/3.
 * post请求无返回结果的
 */
public class PostWithoutBody extends AuthenticatedRequestBase {
    private Response.Listener listener;

    public PostWithoutBody(String url , NetCallback callback) {
        super(Method.POST, url, false, callback.getErrorListener());
        this.listener = callback.getSuccessListener();
    }

    @Override
    protected Response parseNetworkResponse(NetworkResponse response) {
        return Response.success(null, parseCacheHeaders(response));
    }

    @Override
    protected void deliverResponse(Object response) {
        listener.onResponse(response);
    }
}