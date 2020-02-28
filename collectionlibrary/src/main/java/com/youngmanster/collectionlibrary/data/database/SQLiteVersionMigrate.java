package com.youngmanster.collectionlibrary.data.database;

import com.youngmanster.collectionlibrary.config.Config;
import com.youngmanster.collectionlibrary.data.DataManager;

/**
 * Created by yangy
 * 2020-02-27
 * Describe:
 */
public class SQLiteVersionMigrate {


    public interface MigrateListener {
        void onMigrate(int oldVersion, int newVersion);
    }

    public void setMigrateListener(MigrateListener migrate) {
        boolean isFirst= DataManager.DataForSharePreferences.getObject(SqlHelper.isFirstUseKey,true);

        if(isFirst){
            DataManager.DataForSharePreferences.saveObject(SqlHelper.isFirstUseKey,false);
            DataManager.DataForSharePreferences.saveObject(SqlHelper.PREFS_TABLE_VERSION_KEY, Config.SQLITE_DB_VERSION);
        }else{
            int tableVersion = DataManager.DataForSharePreferences.getObject(
                    SqlHelper.PREFS_TABLE_VERSION_KEY,
                    0
            );

            if (Config.SQLITE_DB_VERSION > tableVersion) {
                migrate.onMigrate(tableVersion, Config.SQLITE_DB_VERSION);
            }
        }


    }

}
