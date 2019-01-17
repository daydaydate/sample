package com.base.api.RestfulRequest;

import android.support.annotation.UiThread;
import android.util.Log;

import com.android.volley.Response;
import com.android.volley.TimeoutError;
import com.android.volley.VolleyError;
import com.google.gson.Gson;

/*see https://developer.android.google.cn/training/volley/simple.html, You can add a request from any thread, but responses are always delivered on the main thread*/
@UiThread
public abstract class NetCallback<TResponse> {
    public ResponseCacheStatus fResponseCacheStatus = ResponseCacheStatus.NewFromServer;
    private String TAG = this.getClass().getSimpleName();
    public boolean enableAutomaticToastOnError = true;

    public NetCallback() {
    }

    public NetCallback(boolean enableAutomaticToastOnError) {
        this.enableAutomaticToastOnError = enableAutomaticToastOnError;
    }

    public abstract void OnSuccess(TResponse result);

    public abstract void OnError(RestfulError error);

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
                if (volleyError instanceof TimeoutError) {
                    Log.e(TAG, "networkResponse == null");
                    //volley TimeoutError
                    OnError(new RestfulError());
                }

                if (volleyError.networkResponse != null) {
                    int statusCode = volleyError.networkResponse.statusCode;
                    String errorMessage = new String(volleyError.networkResponse.data);
                    switch (statusCode) {
                        case 401:
                            //post a Permission authentication failed event
                            break;
                        default:
                            Log.d(TAG, "errorMessage =" + errorMessage);
                            try {
                                RestfulError error = new Gson().fromJson(errorMessage, RestfulError.class);
                                if (enableAutomaticToastOnError && error.getCode() != null) {
                                    //toast(error.ExceptionMessage); //toast it in main thread
                                }
                                OnError(error);
                            } catch (Exception e) {
                                OnError(new RestfulError());
                                Log.d(TAG, "e =" + e.toString());
                            }
                            break;
                    }
                }
            }
        };
    }
}
