package vn.softlink.core;

import android.support.annotation.NonNull;
import android.util.Log;

/**
 * *******************************************************************
 *
 * @Project: Core
 * @Created: Huy QV 2018/10/09
 * @Description: ...
 * All Right Reserved.
 * *******************************************************************
 */
public final class Logger {

    private final String TAG;

    private Logger(Class<?> cls) {
        String tag = cls.getSimpleName();
        if (tag.length() > 23) {
            tag = tag.substring(0, 22);
        }
        TAG = tag;
    }

    private Logger(String tag) {
        if (tag.length() > 23) {
            tag = tag.substring(0, 22);
        }
        TAG = tag;
    }

    public void debug(String message) {
        if (Config.LOG_ENABLE) {
            Log.d(TAG, message);
        }
    }

    public void info(String message) {
        if (Config.LOG_ENABLE) {
            Log.i(TAG, message);
        }
    }

    public void error(String message) {
        if (Config.LOG_ENABLE) {
            Log.e(TAG, message);
        }
    }

    public void watch(String message) {
        if (Config.LOG_ENABLE) {
            Log.w(TAG, message);
        }
    }

    public void wtf(String message) {
        if (Config.LOG_ENABLE) {
            Log.wtf(TAG, message);
        }
    }

    public void breakpoint() {
    }

    @NonNull
    public static Logger get(Class<?> cls) {
        return new Logger(cls);
    }

    @NonNull
    public static Logger get(String tag) {
        return new Logger(tag);
    }
}


