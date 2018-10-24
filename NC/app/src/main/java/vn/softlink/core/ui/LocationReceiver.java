package vn.softlink.core.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.location.Location;
import android.util.Log;

import vn.softlink.core.Config;

/**
 * *******************************************************************
 *
 * @Project: Core
 * @Created: Huy QV 2018/10/09
 * @Description: ...
 * All Right Reserved.
 * *******************************************************************
 */
public class LocationReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == intent || !intent.getAction().equals("location.action")) {
            return;
        }
        Location fLocation = intent.getParcelableExtra(LocationHelper.DATA);
        if (Config.LOG_ENABLE) {
            String s = "Lat: " + fLocation.getLatitude() + "Log:" + fLocation.getLongitude();
            Log.d("LocationReceiver", s);
        }
    }

}