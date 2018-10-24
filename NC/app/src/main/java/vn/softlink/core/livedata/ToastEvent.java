package vn.softlink.core.livedata;

import android.support.annotation.StringRes;
import android.text.TextUtils;

import vn.softlink.core.App;


/**
 * --------------------------------------------------------------------------------
 *
 * @Project: pimp
 * @Created: Huy QV 2018/09/27
 * @Description: ...
 * All Right Reserved.
 * --------------------------------------------------------------------------------
 */
public class ToastEvent extends EventLiveData<String> {

    private static ToastEvent sInstance;

    public static synchronized ToastEvent getInstance() {
        if (sInstance == null) {
            sInstance = new ToastEvent();
        }
        return sInstance;
    }

    public static void show(String string) {
        if (!TextUtils.isEmpty(string)){
            getInstance().set(string);
        }
    }

    public static void show(@StringRes int res) {
        show(App.getInstance().getApplicationContext().getResources().getString(res));
    }

}