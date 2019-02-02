package com.business.api;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.Response;
import com.android.volley.toolbox.JsonObjectRequest;
import com.business.utils.thread.ThreadUtils;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class CommonJsonObjectRequest extends JsonObjectRequest {
    private String TAG = this.getClass().getSimpleName();
    /*
     * code=1:处理成功；
     */
    public static final int CODE_SUCCESS = 100;
    private Context mContext;
    private JSONObject mJsonRequest;

    /**
     * @param context
     * @param url
     * @param jsonRequest
     * @param listener
     * @param errorListener
     */
    public CommonJsonObjectRequest(Context context, String url, JSONObject jsonRequest,
                                   Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        super(url, jsonRequest, listener, errorListener);
        if (jsonRequest != null) {
            Log.d(TAG, jsonRequest.toString());
        }
        init(context, jsonRequest);
    }

    /**
     * @param context
     * @param jsonRequest
     */
    private void init(Context context, JSONObject jsonRequest) {
        this.mContext = context.getApplicationContext();
        this.mJsonRequest = jsonRequest;
        setRetryPolicy(new DefaultRetryPolicy(10 * 1000, 0, 0));
    }

    @Override
    public Map<String, String> getHeaders() throws AuthFailureError {
        Map<String, String> headersMap = new HashMap<>();
        //do your business requirement
        return headersMap;
    }

}
