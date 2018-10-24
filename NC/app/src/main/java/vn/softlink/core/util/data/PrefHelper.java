package vn.softlink.core.util.data;

import android.content.Context;
import android.content.SharedPreferences;
import android.support.annotation.Nullable;

import vn.softlink.core.App;
import vn.softlink.core.Config;

/**
 * *******************************************************************
 *
 * @Project: Core
 * @Created: Huy QV 2018/10/11
 * @Description: ...
 * All Right Reserved.
 * *******************************************************************
 */
public class PrefHelper {

    public interface Command {
        void edit(SharedPreferences.Editor editor);
    }

    private static SharedPreferences shared = App.getInstance().getSharedPreferences(Config.PREF_NAME, Context.MODE_PRIVATE);

    public void edit(Command command) {
        command.edit(shared.edit());
        commit();
    }

    public void clear() {
        shared.edit().clear();
        commit();
    }

    public void commit() {
        shared.edit().apply();
    }

    public void put(String key, @Nullable String value) {
        shared.edit().putString(key, value);
    }

    public void put(String key, int value) {
        shared.edit().putInt(key, value);
    }

    public void put(String key, long value) {
        shared.edit().putLong(key, value);
    }

    public void put(String key, boolean value) {
        shared.edit().putBoolean(key, value);
    }

    public static String getString(String key) {
        return getString(key, null);
    }

    public static String getString(String key, String defaultValue) {
        return shared.getString(key, defaultValue);
    }

    public static int getInt(String key) {
        return getInt(key, 0);
    }

    public static int getInt(String key, int defaultValue) {
        return getInstance().shared.getInt(key, defaultValue);
    }

    public static long getLong(String key) {
        return getLong(key, 0);
    }

    public static long getLong(String key, long defaultValue) {
        return getInstance().shared.getLong(key, defaultValue);
    }

    public static boolean getBoolean(String key) {
        return getBoolean(key, false);
    }

    public static boolean getBoolean(String key, boolean defaultValue) {
        return getInstance().shared.getBoolean(key, defaultValue);
    }

    // Singleton
    private static PrefHelper sInstance;

    public static synchronized PrefHelper getInstance() {
        if (sInstance == null) {
            sInstance = new PrefHelper();
        }
        return sInstance;
    }
}
