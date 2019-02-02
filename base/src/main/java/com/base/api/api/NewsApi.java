package com.base.api.api;

import com.base.api.RestfulRequest.CommonRequest;
import com.base.api.RestfulRequest.NetCallback;
import com.base.api.bean.News;
import com.base.api.bean.RequestBody;
import com.base.api.constant.INetConstant;

public class NewsApi {

    public static void getInfo(NetCallback<News> callback) {
        new CommonRequest<>(INetConstant.NEWS, News.class, true, callback);
    }

    public static void postInfo(NetCallback<News> callback) {
        RequestBody body = new RequestBody("top", "b2f8e4aeacfa310cabfadd5189bbe4d5");
        new CommonRequest<>(INetConstant.NEWS, body, News.class, true, callback);
    }


    public static void cancelGetInfo() {
        //how to do
    }
}
