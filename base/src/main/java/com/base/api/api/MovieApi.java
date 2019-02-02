package com.base.api.api;

import com.base.api.RestfulRequest.CommonRequest;
import com.base.api.RestfulRequest.NetCallback;
import com.base.api.bean.Movie;
import com.base.api.constant.INetConstant;

public class MovieApi {

    public static void getInfo(NetCallback<Movie> callback) {
        new CommonRequest<>(INetConstant.MOVIE, Movie.class, true, callback);
    }
}
