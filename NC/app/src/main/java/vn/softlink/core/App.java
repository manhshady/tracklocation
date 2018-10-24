package vn.softlink.core;

import android.app.Application;
import android.content.Context;

import com.crashlytics.android.Crashlytics;

import io.fabric.sdk.android.Fabric;

/**
 * *******************************************************************
 *
 * @Project: Core
 * @Created: Huy QV 2018/10/09
 * @Description: ...
 * All Right Reserved.
 * *******************************************************************
 */
public class App extends Application {

    private static App sInstance;

    @Override
    public void onCreate() {
        super.onCreate();

        sInstance = this;

        if (Config.CRASH_REPORT_ENABLE) {
            Fabric.with(this, new Crashlytics());
        }
    }

    public static App getInstance() {
        return sInstance;
    }

    public static Context getAppContext() {
        return sInstance.getApplicationContext();
    }
}
