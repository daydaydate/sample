package com.business.api.model;

import android.content.Context;

import com.android.volley.Response;
import com.business.api.base.BaseNetModel;
import com.business.api.constant.INetConstant;

import org.json.JSONObject;

public class NewsModel extends BaseNetModel {
    public NewsModel(Context context) {
        super(context);
    }

    public NewsModel(Context context, boolean isAsyn) {
        super(context, isAsyn);
    }

    public void getInfo(Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) throws Exception {
        JSONObject jsonObject = new JSONObject();
        addRequest(INetConstant.NEWS, jsonObject, listener, errorListener);
    }

}
