package vn.softlink.core.util.android;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.annotation.RequiresPermission;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;

/**
 * *******************************************************************
 *
 * @Project: Core
 * @Created: Huy QV 2018/10/09
 * @Description: ...
 * All Right Reserved.
 * *******************************************************************
 */
public class PermissionUtil {

    public static final String[] PERMISSION;

    private PermissionUtil() {
        throw new UnsupportedOperationException("Not allow instantiating object.");
    }

    static {
        PERMISSION = new String[]{Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION};
    }

    public static boolean isGranted(Context context, @RequiresPermission String permission) {
        return ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED;
    }

    public static void request(Activity activity, int requestCode, String... permission) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activity.requestPermissions(permission, requestCode);
        } else {
            ActivityCompat.requestPermissions(activity, permission, requestCode);
        }

    }

    public static void request(Fragment fragment, int requestCode, String... permission) {
        fragment.requestPermissions(permission, requestCode);
    }

    /**
     * Location
     */
    public static boolean isGrantedLocation(Context context) {
        return isGranted(context,Manifest.permission.ACCESS_FINE_LOCATION)
                && isGranted(context,Manifest.permission.ACCESS_COARSE_LOCATION);
    }

    public static boolean isGrantedLocation(Fragment fragment) {
        return isGranted(fragment.getContext(),Manifest.permission.ACCESS_FINE_LOCATION)
                && isGranted(fragment.getContext(),Manifest.permission.ACCESS_COARSE_LOCATION);
    }

    public static void requestLocation(Fragment fragment, int requestCode) {
        request(fragment, requestCode, PERMISSION);
    }

    public static void requestLocation(Activity activity, int requestCode) {
        if (SdkUtil.has23()) {
            activity.requestPermissions(PERMISSION, requestCode);
        } else {
            ActivityCompat.requestPermissions(activity, PERMISSION, requestCode);
        }
    }

}
