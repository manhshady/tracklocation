package vn.softlink.core.mvvm;

import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.annotation.StringRes;

import vn.softlink.core.App;
import vn.softlink.core.Logger;
import vn.softlink.core.model.response.RoomHelper;
import vn.softlink.core.util.android.AppUtil;


/**
 * *******************************************************************
 *
 * @Project: LocationNativeCore
 * @Created: Huy QV 2018/10/11
 * @Description: ...
 * All Right Reserved.
 * *******************************************************************
 */
public abstract class MVVMViewModel extends ViewModel {

    public Logger mLogger;

    /**
     * M.V.VM configuration
     */
    protected abstract void onStart();

    protected abstract void onDestroy();


    /**
     * Utilities methods
     */
    public Context getContext() {
        return App.getInstance().getApplicationContext();
    }

    public RoomHelper getRoom() {
        return RoomHelper.getInstance();
    }

    public String getString(@StringRes int stringRes) {
        return AppUtil.getString(stringRes);
    }


    /**
     * Logger enable by Config.LOG_ENABLE
     */
    public void debug(String string) {
        mLogger.debug(string);
    }

    public void info(String string) {
        mLogger.info(string);
    }

    public void error(String string) {
        mLogger.error(string);
    }

    public void watch(String string) {
        mLogger.watch(string);
    }

    public void wtf(String string) {
        mLogger.wtf(string);
    }

}
