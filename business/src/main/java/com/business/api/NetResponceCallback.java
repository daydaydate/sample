package com.business.api;

import android.support.annotation.UiThread;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;

/*see https://developer.android.google.cn/training/volley/simple.html, You can add a request from any thread, but responses are always delivered on the main thread*/
@UiThread
public abstract class NetResponceCallback<TResponse> {
    private String TAG = this.getClass().getSimpleName();
    public boolean enableAutomaticToastOnError = true;

    public NetResponceCallback() {
    }

    public NetResponceCallback(boolean enableAutomaticToastOnError) {
        this.enableAutomaticToastOnError = enableAutomaticToastOnError;
    }

    public abstract void OnSuccess(TResponse result);

    public abstract void OnError(CommonServerError error);

    public void OnNetworkOff() {
        //do nothing ,use it according to requirement
    }

    public Response.Listener<TResponse> getSuccessListener() {
        return new Response.Listener<TResponse>() {
            @Override
            public void onResponse(TResponse result) {
                OnSuccess(result);
            }
        };
    }

    public Response.ErrorListener getErrorListener() {
        return new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {

            }
        };
    }
}
