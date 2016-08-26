package com.zlove.app;

import android.app.Application;
import android.os.Handler;
import android.util.Log;

/**
 * Created by ZLOVE on 2016/8/26.
 */
public class MsgApplication extends Application {

    private static MsgApplication instance;
    private Handler handler;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        handler = new Handler();
    }

    public static MsgApplication getInstance() {
        if (instance == null) {
            Log.e("ZLOVE", "App is null");
        }
        return instance;
    }

    public void post(Runnable runnable) {
        handler.post(runnable);
    }
}
