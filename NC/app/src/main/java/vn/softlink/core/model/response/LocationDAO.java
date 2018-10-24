package vn.softlink.core.model.response;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Query;

import java.util.List;

/**
 * *******************************************************************
 *
 * @Project: LocationNativeCore
 * @Created: Huy QV 2018/10/09
 * @Description: ...
 * All Right Reserved.
 * *******************************************************************
 */
@Dao
public interface LocationDAO extends BaseDao<LocationResponse> {

    @Query("SELECT * FROM location_response ORDER BY timestamp DESC")
    LiveData<LocationResponse> getLiveData();

    @Query("SELECT * FROM location_response ORDER BY timestamp DESC")
    List<LocationResponse> getData();

}