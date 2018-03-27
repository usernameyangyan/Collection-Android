package com.youngmanster.collection.common;

import android.os.Environment;

/**
 * Created by yangyan
 * on 2018/3/25.
 */

public class AppConfig {
    public final static String STORAGE_DIR= Environment.getExternalStorageDirectory().getAbsolutePath()+"/Collection/";
    public final static String URL_CACHE=STORAGE_DIR+"url/cache/";
}
