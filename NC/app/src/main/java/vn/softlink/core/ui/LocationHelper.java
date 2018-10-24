package vn.softlink.core.ui;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Looper;
import android.support.v4.app.Fragment;

import java.util.HashMap;
import java.util.Map;

import vn.softlink.core.App;
import vn.softlink.core.Logger;
import vn.softlink.core.util.android.PermissionUtil;
import vn.softlink.core.util.android.SdkUtil;
import vn.softlink.core.util.data.PrefHelper;

/**
 * *******************************************************************
 *
 * @Project: Core
 * @Created: Huy QV 2018/10/09
 * @Description: ...
 * All Right Reserved.
 * *******************************************************************
 */
@SuppressLint("MissingPermission")
public class LocationHelper {

    private Logger L = Logger.get(LocationHelper.class);

    /**
     * Static constants
     */
    public static final int PERMISSION_REQUEST_CODE = 76835;
    public static final String ACTION = "location.action";
    public static final String DATA = "location.data";
    public static final String CURRENT_BROADCAST = "location.broadcast";
    public static final String PERMISSION_DENY = "location.deny";


    /**
     * Location request update settings
     */
    public String mActionReceiver;
    private long mInterval;
    private int mDistance;
    private boolean mGpsEnable;
    private boolean mFusedEnable;
    private Criteria mCriteria;


    public LocationManager mManager;
    private Location currentLocation;
    private volatile boolean isListening = false;
    private BroadcastReceiver mBroadcastReceiver;

    /**
     * name of action to send mGpsEnable data for your broadcast receiver
     */


    public LocationHelper(String actionReceiver) {

        mManager = (LocationManager) App.getAppContext().getSystemService(Context.LOCATION_SERVICE);
        mCriteria = new Criteria();
        mActionReceiver = actionReceiver;
        getPref();
    }

    public void getPref() {
        mInterval = PrefHelper.getLong("interval", 10000L);
        mDistance = PrefHelper.getInt("distance", 0);
        mGpsEnable = PrefHelper.getBoolean("gps", true);
        mFusedEnable = PrefHelper.getBoolean("fused", true);
    }

    public void savePref() {
        PrefHelper.getInstance().edit(editor -> {
            editor.putLong("interval", mInterval);
            editor.putInt("distance", mDistance);
            editor.putBoolean("gps", mGpsEnable);
            editor.putBoolean("fused", mFusedEnable);
        });
    }

    public Location getCurrentLocation() {
        return currentLocation;
    }

    public void setCurrentLocation(Location currentLocation) {
        this.currentLocation = currentLocation;
    }

    public void addListener(LocationListener listener, Looper looper) {
        isListening = true;
        mManager.requestLocationUpdates(mInterval, mDistance, mCriteria, listener, looper);
    }

    public void removeListener(LocationListener listener) {
        isListening = false;
        try {
            mManager.removeUpdates(listener);
        } catch (NullPointerException e) {
        }
    }

    public void getSingleLocation(LocationListener listener, Looper looper) {
        mManager.requestSingleUpdate(mCriteria, listener, looper);
    }

    public boolean isListeningUpdate() {
        return isListening;
    }

    public String getBestProvider() {
        return mManager.getBestProvider(mCriteria, true);
    }

    public static void fireBaseLog(Location location) {
        Map<String, Double> map = new HashMap<>();
        map.put("lat", location.getLatitude());
        map.put("lng", location.getLongitude());
      /*  FirebaseDatabase.getInstance().getReference()
                .child(AppUtil.getDeviceId() + " - " + AppUtil.getDeviceName())
                .child(TimeHelper.format(location.getTime(), new SimpleDateFormat("yyyy/MM/dd/hh:mm:ss")))
                .setValue(map);*/
    }


    /**
     * Service
     */
    public void start(Activity activity) {

        if (SdkUtil.has23() && !LocationHelper.isGrantedPermission(activity)) {
            LocationHelper.requestPermission(activity);
        } else {
            startLocationService(activity.getBaseContext());
            if (mBroadcastReceiver != null) {
                IntentFilter fIntent = new IntentFilter(LocationHelper.CURRENT_BROADCAST);
                fIntent.addAction(LocationHelper.PERMISSION_DENY);
                activity.getBaseContext().registerReceiver(mBroadcastReceiver, fIntent);
            }
        }


    }

    public void startService(Context context) {
        startLocationService(context);
        if (mBroadcastReceiver != null) {
            IntentFilter fIntent = new IntentFilter(LocationHelper.CURRENT_BROADCAST);
            fIntent.addAction(LocationHelper.PERMISSION_DENY);
            context.registerReceiver(mBroadcastReceiver, fIntent);
        }
    }

    public void startLocationService(Context context) {
        Intent fIntent = new Intent(context, LocationService.class);
        savePref();
        context.startService(fIntent);
    }

    public void stopLocationService(Context context) {
        if (LocationService.isRunning(context)) {
            if (mBroadcastReceiver != null) {
                context.unregisterReceiver(mBroadcastReceiver);
            }
            Intent serviceIntent = new Intent(context, LocationService.class);
            context.stopService(serviceIntent);
        }
    }


    /**
     * Location Permission:
     */
    public static boolean isGrantedPermission(Context context) {
        return PermissionUtil.isGrantedLocation(context);
    }

    public static boolean isGrantedPermission(Fragment fragment) {
        return PermissionUtil.isGrantedLocation(fragment);
    }

    public static void requestPermission(Activity activity) {
        PermissionUtil.requestLocation(activity, PERMISSION_REQUEST_CODE);
    }

    public static void requestPermission(Fragment fragment) {
        PermissionUtil.requestLocation(fragment, PERMISSION_REQUEST_CODE);
    }


    /**
     * Criteria Builder
     */
    public LocationHelper currentLocation(BroadcastReceiver broadcastReceiver) {
        mBroadcastReceiver = broadcastReceiver;
        return this;
    }

    public LocationHelper gpsEnable(boolean enable) {
        mGpsEnable = enable;
        return this;
    }

    public LocationHelper fusedEnable(boolean enable) {
        mFusedEnable = enable;
        return this;
    }

    public LocationHelper powerRequirement(int powerLevel) {
        mCriteria.setPowerRequirement(powerLevel);
        return this;
    }

    public LocationHelper requestInterval(long interval) {
        this.mInterval = interval;
        return this;
    }

    public LocationHelper requestDistance(int interval) {
        mDistance = interval;
        return this;
    }

    public LocationHelper horizontalAccuracy(int accuracy) {
        mCriteria.setHorizontalAccuracy(accuracy);
        return this;
    }

    public LocationHelper verticalAccuracy(int accuracy) {
        mCriteria.setVerticalAccuracy(accuracy);
        return this;
    }

    public LocationHelper altitudeRequired(boolean value) {
        mCriteria.setAltitudeRequired(value);
        return this;
    }

    public LocationHelper speedRequired(boolean value) {
        mCriteria.setSpeedRequired(value);
        return this;
    }

    public LocationHelper speedAccuracy(int accuracy) {
        mCriteria.setSpeedAccuracy(accuracy);
        return this;
    }

    public LocationHelper bearingRequired(boolean value) {
        mCriteria.setBearingRequired(value);
        return this;
    }

    public LocationHelper bearingAccuracy(int accuracy) {
        mCriteria.setBearingAccuracy(accuracy);
        return this;
    }

    public LocationHelper costAllowed(boolean value) {
        mCriteria.setCostAllowed(value);
        return this;
    }

}
