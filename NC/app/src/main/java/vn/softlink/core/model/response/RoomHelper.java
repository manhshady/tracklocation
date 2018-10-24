package vn.softlink.core.model.response;

import android.arch.persistence.db.SupportSQLiteDatabase;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;
import android.support.annotation.NonNull;

import vn.softlink.core.App;
import vn.softlink.core.Config;

/**
 * *******************************************************************
 *
 * @Project: LocationNativeCore
 * @Created: Huy QV 2018/10/09
 * @Description: ...
 * All Right Reserved.
 * *******************************************************************
 */
@Database(entities = {LocationResponse.class},
        version = Config.DATABASE_VERSION,
        exportSchema = false)
public abstract class RoomHelper extends RoomDatabase {

    private static RoomHelper sInstance;

    // Data access object
    public abstract LocationDAO locationDAO();

    public static RoomHelper getInstance() {
        if (sInstance == null) {
            synchronized (RoomHelper.class) {
                if (null == sInstance) {
                    sInstance = init();
                }
            }
        }
        return sInstance;
    }


    private static RoomHelper init() {

        Context context = App.getInstance().getApplicationContext();
        return Room.databaseBuilder(context, RoomHelper.class, Config.DATABASE_NAME)
                .allowMainThreadQueries()
                // Recreate the database if necessary
                .fallbackToDestructiveMigration()
                // Prepopulate the database after onCreate was called
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        super.onCreate(db);

                    }
                })
                .build();

    }


}
