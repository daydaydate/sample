package com.base.api.api;

import com.base.api.RestfulRequest.GetRequest;
import com.base.api.RestfulRequest.NetCallback;
import com.base.api.bean.News;
import com.base.api.constant.INetConstant;

public class NewsApi {

    public static void getInfo(NetCallback<News> callback) {
        new GetRequest<>(INetConstant.NEWS, News.class, true, callback);
    }
}
