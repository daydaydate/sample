package com.aiidayang.app;

import android.app.Application;
import android.content.Context;

import com.aiidayang.BuildConfig;
import com.base.api.YZ;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class BaseApp extends Application {

    public String TAG = this.getClass().getSimpleName();
    public static Context applicationContext;
    public static Executor threadPool;
    public static final int THREAD_POOL_SIZE = 3;
    public static final boolean isDebug = BuildConfig.BUILD_TYPE.equals("debug");

    @Override
    public void onCreate() {
        super.onCreate();
        applicationContext = getApplicationContext();
        threadPool = Executors.newFixedThreadPool(THREAD_POOL_SIZE);

        “还是那个冲突吗”
        initNet();
        initHyphenate();
    }

    private void initNet() {
        YZ.getInstance().init(this);
    }

    public Context getInstance() {
        return applicationContext;
    }

    public void initHyphenate() {
    }

}
