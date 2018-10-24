package vn.softlink.core.model.response;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import vn.softlink.core.model.anotation.Network;
import vn.softlink.core.model.anotation.Signal;

/**
 * *******************************************************************
 *
 * @Project: Core
 * @Created: Huy QV 2018/10/22
 * @Description: ...
 * All Right Reserved.
 * *******************************************************************
 */

@Entity(tableName = "location_response")
public class LocationResponse {

    private String userId;                                  // User ID
    @PrimaryKey
    @NonNull
    @ColumnInfo(name = "device_id")
    private String deviceId;                                // IMEI, MEID, ESN or IMSI
    private String placeId;                                 // Exp: 34975984632564294652654

    /**
     * Network information
     */
    private String phoneNumber;
    private transient @Network int networkProvider;
    private transient @Signal int networkStrength = 0;
    private transient @Signal int dataNetworkStrength = 0;

    /**
     * Wireless information
     */
    private String wirelessProviderName;
    private String ipAddress;                               // IP android   - exp: 192.168.0.123
    private String macAddress;                              // Mac address  - exp:  02:03:04:05:06:07
    private transient @Signal int wirelessStrength = 0;

    /**
     * {@link android.location.Location}
     */
    private String locationProvider;                        // GPS, NETWORK, FUSED, PASSED..
    private double latitude, longitude, altitude;           // WGS-84 Coordinate
    private float horizontalAccuracy, verticalAccuracy;
    private float bearing, bearingAccuracy;
    private float speed, speedAccuracyMetersPerSecond;
    @NonNull
    @ColumnInfo(name = "timestamp")
    private long timeStamp = 0;                             // Long time millisecond
    private long elapsedRealtimeNanos = 0;

    public LocationResponse(@NonNull String deviceId, double latitude, double longitude, @NonNull long timeStamp) {
        this.deviceId = deviceId;
        this.latitude = latitude;
        this.longitude = longitude;
        this.timeStamp = timeStamp;
    }

    /**
     * {@link android.location.GnssStatus}
     */

    //TODO: error: Cannot figure out how to save this field into database. You can consider adding a type converter for it.
//    private String[] blueToothPossibleDeviceIds;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getPlaceId() {
        return placeId;
    }

    public void setPlaceId(String placeId) {
        this.placeId = placeId;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public int getNetworkProvider() {
        return networkProvider;
    }

    public void setNetworkProvider(int networkProvider) {
        this.networkProvider = networkProvider;
    }

    public int getNetworkStrength() {
        return networkStrength;
    }

    public void setNetworkStrength(int networkStrength) {
        this.networkStrength = networkStrength;
    }

    public int getDataNetworkStrength() {
        return dataNetworkStrength;
    }

    public void setDataNetworkStrength(int dataNetworkStrength) {
        this.dataNetworkStrength = dataNetworkStrength;
    }

    public String getWirelessProviderName() {
        return wirelessProviderName;
    }

    public void setWirelessProviderName(String wirelessProviderName) {
        this.wirelessProviderName = wirelessProviderName;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getMacAddress() {
        return macAddress;
    }

    public void setMacAddress(String macAddress) {
        this.macAddress = macAddress;
    }

    public int getWirelessStrength() {
        return wirelessStrength;
    }

    public void setWirelessStrength(int wirelessStrength) {
        this.wirelessStrength = wirelessStrength;
    }

    public String getLocationProvider() {
        return locationProvider;
    }

    public void setLocationProvider(String locationProvider) {
        this.locationProvider = locationProvider;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public double getAltitude() {
        return altitude;
    }

    public void setAltitude(double altitude) {
        this.altitude = altitude;
    }

    public float getHorizontalAccuracy() {
        return horizontalAccuracy;
    }

    public void setHorizontalAccuracy(float horizontalAccuracy) {
        this.horizontalAccuracy = horizontalAccuracy;
    }

    public float getVerticalAccuracy() {
        return verticalAccuracy;
    }

    public void setVerticalAccuracy(float verticalAccuracy) {
        this.verticalAccuracy = verticalAccuracy;
    }

    public float getBearing() {
        return bearing;
    }

    public void setBearing(float bearing) {
        this.bearing = bearing;
    }

    public float getBearingAccuracy() {
        return bearingAccuracy;
    }

    public void setBearingAccuracy(float bearingAccuracy) {
        this.bearingAccuracy = bearingAccuracy;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    public float getSpeedAccuracyMetersPerSecond() {
        return speedAccuracyMetersPerSecond;
    }

    public void setSpeedAccuracyMetersPerSecond(float speedAccuracyMetersPerSecond) {
        this.speedAccuracyMetersPerSecond = speedAccuracyMetersPerSecond;
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(long timeStamp) {
        this.timeStamp = timeStamp;
    }

    public long getElapsedRealtimeNanos() {
        return elapsedRealtimeNanos;
    }

    public void setElapsedRealtimeNanos(long elapsedRealtimeNanos) {
        this.elapsedRealtimeNanos = elapsedRealtimeNanos;
    }

//    public String[] getBlueToothPossibleDeviceIds() {
//        return blueToothPossibleDeviceIds;
//    }
//
//    public void setBlueToothPossibleDeviceIds(String[] blueToothPossibleDeviceIds) {
//        this.blueToothPossibleDeviceIds = blueToothPossibleDeviceIds;
//    }
}
