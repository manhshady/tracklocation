package vn.softlink.core.model.response;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.OnConflictStrategy;
import android.arch.persistence.room.Update;

import java.util.Collection;

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
public interface BaseDao<M> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(M m);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(Collection<M> collection);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    void insert(M... array);

    @Update(onConflict = OnConflictStrategy.REPLACE)
    void update(M t);

    @Delete
    void delete(M t);

}