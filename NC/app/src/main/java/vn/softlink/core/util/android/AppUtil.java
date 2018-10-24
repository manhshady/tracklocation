package vn.softlink.core.util.android;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.graphics.Point;
import android.net.Uri;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.support.annotation.StringRes;
import android.text.TextUtils;
import android.view.WindowManager;
import android.widget.Toast;
import java.io.IOException;
import java.io.InputStream;

import vn.softlink.core.App;

/**
 * *******************************************************************
 *
 * @Project: Core
 * @Created: Huy QV 2018/10/09
 * @Description: ...
 * All Right Reserved.
 * *******************************************************************
 */
public class AppUtil {

    private static String sDeviceId = null;

    public static float sDensity;

    private static final Point sDisplaySize = new Point(0, 0);

    private static final String sPackageName;

    private static final Handler sUIHandler = new Handler(Looper.getMainLooper());

    private AppUtil() {
        throw new UnsupportedOperationException("Not allow instantiating object.");
    }

    static {
        sDensity = App.getInstance().getResources().getDisplayMetrics().density;
        sPackageName = App.getInstance().getPackageName();
    }

    public static int dp(float dp) {
        if (dp == 0) {
            return 0;
        }
        return (int) Math.ceil(sDensity * dp);
    }

    public static int dp2(float dp) {
        if (dp == 0) {
            return 0;
        }
        return (int) Math.floor(sDensity * dp);
    }

    public static float dp2px(float dp) {
        return sDensity * dp;
    }

    public static String getDeviceId() {

        Context context = App.getInstance();
        if (TextUtils.isEmpty(sDeviceId)) {
            try {
                sDeviceId = Settings.Secure.getString(context.getContentResolver(), Settings.Secure.ANDROID_ID);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return sDeviceId;
    }

    public static String currentOsVersion() {
        return Build.VERSION.RELEASE;
    }

    public static int currentOsVersionCode() {
        return Build.VERSION.SDK_INT;
    }

    public static String getDeviceName() {
        String manufacturer = Build.MANUFACTURER;
        String model = Build.MODEL;
        if (model.startsWith(manufacturer)) {
            return capitalize(model);
        }
        return capitalize(manufacturer) + " " + model;
    }

    private static String capitalize(String str) {
        if (TextUtils.isEmpty(str)) {
            return str;
        }
        char[] arr = str.toCharArray();
        boolean capitalizeNext = true;
        String phrase = "";
        for (char c : arr) {
            if (capitalizeNext && Character.isLetter(c)) {
                phrase += Character.toUpperCase(c);
                capitalizeNext = false;
                continue;
            } else if (Character.isWhitespace(c)) {
                capitalizeNext = true;
            }
            phrase += c;
        }
        return phrase;
    }

    public static Point getDisplaySize(Context context) {
        if (sDisplaySize.x == 0 && sDisplaySize.y == 0) {
            WindowManager wm = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
            wm.getDefaultDisplay().getSize(sDisplaySize);
        }
        return new Point(sDisplaySize);
    }

    public static void showToast(final String message) {
        if (TextUtils.isEmpty(message)) {
            return;
        }
        if (isOnMainThread()) {
            Toast.makeText(App.getInstance(), message, Toast.LENGTH_SHORT).show();
        } else {
            sUIHandler.post(() -> Toast.makeText(App.getInstance(), message, Toast.LENGTH_SHORT).show());
        }
    }

    public static void showToast(@StringRes int stringRes, Object... arguments) {
        final Context context = App.getInstance().getApplicationContext();
        try {
            final String message = context.getString(stringRes, arguments);
            if (isOnMainThread()) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            } else {
                sUIHandler.post(() -> Toast.makeText(context, message, Toast.LENGTH_SHORT).show());
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void showToast(@StringRes int stringRes) {
        final Context context = App.getInstance().getApplicationContext();
        try {
            final String message = context.getString(stringRes);
            if (isOnMainThread()) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            } else {
                sUIHandler.post(() -> Toast.makeText(context, message, Toast.LENGTH_SHORT).show());
            }
        } catch (Resources.NotFoundException e) {
            e.printStackTrace();
        }
    }

    public static boolean isTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout & Configuration.SCREENLAYOUT_SIZE_MASK)
                >= Configuration.SCREENLAYOUT_SIZE_LARGE;
    }

    public static String getPackageName() {
        return sPackageName;
    }

    public static String getString(@StringRes int stringRes) {
        try {
            return App.getInstance().getString(stringRes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static boolean isOnMainThread() {
        return Looper.myLooper() == Looper.getMainLooper();
    }

    public static Uri resourceToUri(Context context, int resID) {
        if (context == null) {
            return Uri.parse("");
        }
        try {
            return Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE + "://" +
                    context.getResources().getResourcePackageName(resID) + '/' +
                    context.getResources().getResourceTypeName(resID) + '/' +
                    context.getResources().getResourceEntryName(resID));
        } catch (Exception ignored) {
            return Uri.parse("");
        }
    }

    public static void openAppSettings(Activity activity, int requestCode) {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        Uri uri = Uri.fromParts("package", activity.getPackageName(), null);
        intent.setData(uri);
        activity.startActivityForResult(intent, requestCode);
    }

    public static String readAsset(Context context, String filename) {
        StringBuilder sb = new StringBuilder();
        try {
            InputStream is = context.getAssets().open(filename);
            int size = is.available();
            byte[] buffer = new byte[size];
            is.read(buffer);
            is.close();
            return new String(buffer, "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    public static boolean isServiceRunning(Context context, Class serviceClass) {

        ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (manager == null) {
            return false;
        }
        for (ActivityManager.RunningServiceInfo service : manager.getRunningServices(Integer.MAX_VALUE)) {
            if (serviceClass.getName().equals(service.service.getClassName())) {
                return true;
            }
        }
        return false;
    }

}

