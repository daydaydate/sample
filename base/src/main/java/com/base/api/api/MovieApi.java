package com.base.api.api;

import com.base.api.RestfulRequest.GetRequest;
import com.base.api.RestfulRequest.NetCallback;
import com.base.api.bean.Movie;
import com.base.api.constant.INetConstant;

public class MovieApi {

    public static void getInfo(NetCallback<Movie> callback) {
        new GetRequest<>(INetConstant.MOVIE, Movie.class, true, callback);
    }
}
