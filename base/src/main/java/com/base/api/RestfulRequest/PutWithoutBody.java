package com.base.api.RestfulRequest;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;

import static com.android.volley.toolbox.HttpHeaderParser.parseCacheHeaders;

/**
 * Created by yuanjian on 2017/5/3.
 */
public class PutWithoutBody extends AuthenticatedRequestBase{
    private Response.Listener listener;

    public PutWithoutBody(String url, NetCallback callback) {
        super(Method.PUT, url, false , callback.getErrorListener());
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
