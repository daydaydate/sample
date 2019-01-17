package com.base.api.RestfulRequest;

import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.google.gson.Gson;

import java.io.UnsupportedEncodingException;

import static com.android.volley.toolbox.HttpHeaderParser.parseCacheHeaders;

/**
 * Created by yuanjian on 2017/5/3.
 */
public class PutRequest<TRequest> extends AuthenticatedRequestBase {
    private TRequest request;
    private Response.Listener listener;
    private final static Gson gson = new Gson();
    protected static final String PROTOCOL_CHARSET = "utf-8";

    public PutRequest(String url, TRequest request, NetCallback callback) {
        super(Method.PUT, url, false, callback.getErrorListener());
        this.request = request;
        this.listener = callback.getSuccessListener();
    }

    @Override
    public byte[] getBody() {
        String mRequestBody = gson.toJson(this.request);
        try {
            return mRequestBody == null ? null : mRequestBody.getBytes(PROTOCOL_CHARSET);
        } catch (UnsupportedEncodingException uee) {
            VolleyLog.wtf("Unsupported Encoding while trying to get the bytes of %s using %s",
                    mRequestBody, PROTOCOL_CHARSET);
            return null;
        }
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
