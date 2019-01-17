package com.base.api.api;

import com.base.api.RestfulRequest.NetCallback;
import com.base.api.RestfulRequest.GetRequest;
import com.base.api.bean.Laziji;

public class LazijiApi {

    public static void getInfo(NetCallback<Laziji> callback) {
        String url = "http://apis.juhe.cn/cook/query.php?menu=%e8%be%a3%e5%ad%90%e9%b8%a1&dtype=&pn=&rn=&key=972a7c986ed1aea801ff427cd9418260";
        new GetRequest<>(url, Laziji.class, true, callback);
    }

}
