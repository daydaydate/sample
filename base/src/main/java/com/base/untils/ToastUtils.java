package com.base.untils;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.widget.Toast;


public class ToastUtils {

    private static Toast mToast;

    /**
     * 显示单条Toast
     */
    public static void showToast(Context context, CharSequence text) {
        if (mToast == null) {
            mToast = Toast.makeText(context, text, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(text);
            mToast.setDuration(Toast.LENGTH_SHORT);
        }
        mToast.show();
    }

    /**
     * 一定显示在主线程的toast
     */
    private static Handler sHandler = new Handler(Looper.getMainLooper());

    public static Handler getMainHandler() {

        return sHandler;
    }

    public static void safeShowToast(final Context context, final String text) {

        sHandler.post(new Runnable() {
            @Override
            public void run() {

                //Toast.makeText(BaseApp.getContext(), text, Toast.LENGTH_LONG).newIntent();
                showToast(context, text);
            }
        });
    }

}
