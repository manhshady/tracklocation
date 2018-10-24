package vn.softlink.core.ui;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.location.Criteria;
import android.location.Location;
import android.os.Bundle;
import android.os.IBinder;

import vn.softlink.core.Logger;
import vn.softlink.core.livedata.LocationLiveData;
import vn.softlink.core.model.response.LocationResponse;
import vn.softlink.core.util.android.AppUtil;
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
public class LocationService extends Service
        implements android.location.LocationListener {

    private Logger L = Logger.get(LocationService.class);

    private LocationHelper mHelper;

    public static boolean isRunning(Context context) {
        return AppUtil.isServiceRunning(context, LocationService.class);
    }

    @Override
    public void onCreate() {
        super.onCreate();
        PrefHelper.getInstance();
    }

        @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        mHelper = new LocationHelper("location.action")
                .gpsEnable(true).fusedEnable(true).altitudeRequired(true)
                .speedRequired(true).bearingRequired(true).costAllowed(false)
                .powerRequirement(Criteria.POWER_LOW)
                .requestInterval(10000).requestDistance(0)
                .horizontalAccuracy(Criteria.ACCURACY_FINE)
                .verticalAccuracy(Criteria.ACCURACY_FINE)
                .speedAccuracy(Criteria.ACCURACY_FINE)
                .bearingAccuracy(Criteria.ACCURACY_FINE);

        mHelper.addListener(this,getMainLooper());
        return START_STICKY;
    }

    private void updateService() {
        if (mHelper.getCurrentLocation() != null) {
            L.info("send broadcast location data");
            sendLocationBroadcast(mHelper.getCurrentLocation());
            sendCurrentLocationBroadCast(mHelper.getCurrentLocation());

        } else {
            L.info("send broadcast location permission had deny");
            sendPermissionHadDenyBroadCast();
        }
    }

    private void sendLocationBroadcast(Location location) {
        Intent fIntent = new Intent();
        fIntent.setAction(mHelper.mActionReceiver);
        fIntent.putExtra(LocationHelper.DATA, location);
        sendBroadcast(fIntent);
    }

    private void sendCurrentLocationBroadCast(Location location) {
        Intent fIntent = new Intent();
        fIntent.setAction(LocationHelper.CURRENT_BROADCAST);
        fIntent.putExtra(LocationHelper.DATA, location);
        sendBroadcast(fIntent);
    }

    private void sendPermissionHadDenyBroadCast() {
        Intent locationIntent = new Intent();
        locationIntent.setAction(LocationHelper.PERMISSION_DENY);
        sendBroadcast(locationIntent);
    }

    @Override
    public void onDestroy() {
        L.info("onDestroy");
        mHelper.removeListener(this);
    }

    @Override
    public void onTaskRemoved(Intent rootIntent) {
        //This is where we detect the app is being killed, thus stop service.
        L.info("onTaskRemoved");
        mHelper = null;
        stopSelf();
        super.onTaskRemoved(rootIntent);
    }

    @Override
    public IBinder onBind(Intent intent) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onLocationChanged(Location location) {
        mHelper.setCurrentLocation(location);
        System.out.println(location.getLatitude()+","+location.getLongitude());
        LocationLiveData.getInstance().set(new LocationResponse("123", location.getLatitude(), location.getLongitude(), System.currentTimeMillis()));
        updateService();
    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }
}
