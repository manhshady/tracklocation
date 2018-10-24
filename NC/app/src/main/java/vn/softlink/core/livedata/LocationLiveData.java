package vn.softlink.core.livedata;

import vn.softlink.core.model.response.LocationResponse;

/**
 * *******************************************************************
 *
 * @Project: LocationNativeCore
 * @Created: Huy QV 2018/10/11
 * @Description: ...
 * All Right Reserved.
 * *******************************************************************
 */
public class LocationLiveData extends EventLiveData<LocationResponse> {

    private static LocationLiveData sInstance;

    public static LocationLiveData getInstance() {
        if (sInstance == null) {
            sInstance = new LocationLiveData();
        }
        return sInstance;
    }

}