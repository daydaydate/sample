package com.base.api.RestfulRequest;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyLog;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;

import static com.android.volley.toolbox.HttpHeaderParser.parseCacheHeaders;
import static com.android.volley.toolbox.HttpHeaderParser.parseCharset;

/**
 * Created by yuanjian on 2017/5/2.
 * post请求，无需缓存
 */

public class PostRequest<TRequest, TResponse> extends AuthenticatedRequestBase<TResponse> {
    private final static Gson gson = new Gson();
    private final Response.Listener<TResponse> listener;
    private final Class<TResponse> tResponseClass;
    private TRequest request;
    private static final String PROTOCOL_CHARSET = "utf-8";

    public PostRequest(String url, TRequest request, Class<TResponse> responseClass, boolean cache, NetCallback<TResponse> callback) {
        super(Request.Method.POST, url, cache, callback.getErrorListener());
        if (request == null) throw new IllegalArgumentException("request");
        this.request = request;
        this.listener = callback.getSuccessListener();
        this.tResponseClass = responseClass;
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
    protected Response<TResponse> parseNetworkResponse(NetworkResponse response) {
        try {
            String json = new String(response.data, parseCharset(response.headers));
            //see http://stackoverflow.com/questions/5554217/google-gson-deserialize-listclass-object-generic-type
            return Response.success(gson.fromJson(json, tResponseClass), parseCacheHeaders(response));
        } catch (UnsupportedEncodingException | JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(TResponse response) {
        listener.onResponse(response);
    }
}


