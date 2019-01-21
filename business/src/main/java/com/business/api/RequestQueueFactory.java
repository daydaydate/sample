package com.business.api;

import android.content.Context;
import android.os.AsyncTask;

import com.android.volley.ExecutorDelivery;
import com.android.volley.Network;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.Volley;

import java.io.File;

import okhttp3.OkHttpClient;

/**
 * 网络请求队列工厂类
 */
public class RequestQueueFactory {

    private static RequestQueue sRequestQueue;
    private static RequestQueue sAsynRequestQueue;

    private static int ASYN_QUEUE_THREAD_POOL_SIZE = 3;

    private RequestQueueFactory() {

    }

    /**
     * 获取默认RequestQueue，回调是同步到主线程的
     *
     * @param context
     * @return
     */
    public synchronized static RequestQueue getRequestQueue(Context context) {
        if (sRequestQueue == null) {
            OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
            OkHttpStack stack = new OkHttpStack(okHttpClient);
            sRequestQueue = Volley.newRequestQueue(context, stack);
        }
        return sRequestQueue;
    }

    /**
     * 获取异步RequestQueue，回调是在异步线程的
     *
     * @param context
     * @return
     */
    public synchronized static RequestQueue getRequeQueueRespondInAsyn(
            final Context context) {
        if (sAsynRequestQueue == null) {
            sAsynRequestQueue = getAsynRequeQueueRespond(context,
                    ASYN_QUEUE_THREAD_POOL_SIZE);
        }
        return sAsynRequestQueue;
    }

    public static RequestQueue getAsynRequeQueueRespond(final Context context,
                                                        int threadPoolSize) {
        File cacheDir = new File(context.getCacheDir(), "volley_asyn");
        OkHttpClient okHttpClient = new OkHttpClient.Builder().build();
        OkHttpStack stack = new OkHttpStack(okHttpClient);
        Network network = new BasicNetwork(stack);
        RequestQueue queue = new RequestQueue(new DiskBasedCache(cacheDir),
                network, threadPoolSize, new ExecutorDelivery(
                AsyncTask.SERIAL_EXECUTOR));
        queue.start();
        return queue;
    }

}
