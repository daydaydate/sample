package com.base.api.RestfulRequest;

import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;

import static com.android.volley.toolbox.HttpHeaderParser.parseCacheHeaders;
import static com.android.volley.toolbox.HttpHeaderParser.parseCharset;

/**
 * Created by yuanjian on 2017/5/2.
 * post请求，无需缓存
 */

public class PostWithoutRequestBody<TResponse> extends AuthenticatedRequestBase<TResponse> {
    private final static Gson gson = new Gson();
    private final Response.Listener<TResponse> listener;
    private final Class<TResponse> tResponseClass;

    public PostWithoutRequestBody(String url, Class<TResponse> responseClass, NetCallback<TResponse> callback) {
        super(Request.Method.POST, url, false, callback.getErrorListener());
        this.listener = callback.getSuccessListener();
        this.tResponseClass = responseClass;
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


