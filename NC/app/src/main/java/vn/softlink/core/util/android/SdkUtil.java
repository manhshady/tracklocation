package vn.softlink.core.util.android;

import android.os.Build;

/**
 * --------------------------------------------------------------------------------
 * @Project: Pimp
 * @Created: Huy QV 2018/09/15
 * @Description:
 * All Right Reserved
 * --------------------------------------------------------------------------------
 */
public final class SdkUtil {

    private SdkUtil() {
        throw new UnsupportedOperationException("Not allow instantiating object.");
    }

    public static boolean has8() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.FROYO;
    }

    public static boolean has9() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD;
    }

    public static boolean has11() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB;
    }

    public static boolean has12() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR1;
    }

    public static boolean has16() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN;
    }

    public static boolean has19() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT;
    }

    public static boolean has20() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT_WATCH;
    }

    public static boolean has21() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP;
    }

    public static boolean has22() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP_MR1;
    }

    public static boolean has23() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M;
    }

    public static boolean has24() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.N;
    }

    public static boolean has26() {
        return Build.VERSION.SDK_INT >= Build.VERSION_CODES.O;
    }

}

