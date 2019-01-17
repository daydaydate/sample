package com.base.api.RestfulRequest;

import com.android.volley.AuthFailureError;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.Request;
import com.android.volley.Response;
import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;

import java.io.UnsupportedEncodingException;
import java.util.Map;

import static com.android.volley.toolbox.HttpHeaderParser.parseCacheHeaders;
import static com.android.volley.toolbox.HttpHeaderParser.parseCharset;

/**
 * Created by yz on 2017/6/3.
 */

public class TokenRequest<TResponse> extends AuthenticatedRequestBase<TResponse> {
    private final static Gson gson = new Gson();
    private Response.Listener<TResponse> mListener;
    private Map<String, String> params;
    private Class<TResponse> clazz;

    public TokenRequest(String url, Map<String, String> params, Class<TResponse> clazz, NetCallback<TResponse> callback) {
        super(Request.Method.POST, url, false, callback.getErrorListener());
        this.mListener = callback.getSuccessListener();
        this.params = params;
        this.clazz = clazz;
    }

    @Override
    protected Map<String, String> getParams() throws AuthFailureError {
        return params;
    }

    @Override
    protected Response<TResponse> parseNetworkResponse(NetworkResponse response) {
        //return Response.success(null, parseCacheHeaders(response));
        try {
            String json = new String(response.data, parseCharset(response.headers));
            return Response.success(gson.fromJson(json, clazz), parseCacheHeaders(response));
        } catch (UnsupportedEncodingException | JsonSyntaxException e) {
            return Response.error(new ParseError(e));
        }
    }

    @Override
    protected void deliverResponse(TResponse response) {
        mListener.onResponse(response);
    }

}
