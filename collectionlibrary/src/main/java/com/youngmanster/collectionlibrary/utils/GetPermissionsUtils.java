package com.youngmanster.collectionlibrary.utils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PermissionGroupInfo;
import android.content.pm.PermissionInfo;

/**
 * Created by yangy
 * 2019-09-12
 * Describe:
 */
public class GetPermissionsUtils {
    public static String  getAllPermissons(Context context){

        StringBuffer stringBuffer=new StringBuffer();
        try {
            PackageManager pm = context.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(context.getPackageName(), 0);
            //得到自己的包名
            String pkgName = pi.packageName;

            PermissionGroupInfo pgi;
            PackageInfo pkgInfo = pm.getPackageInfo(pkgName, PackageManager.GET_PERMISSIONS);
            String sharedPkgList[] = pkgInfo.requestedPermissions;
            LogUtils.error("权限",sharedPkgList.length+"");

            for(int i=0;i<sharedPkgList.length;i++){
                String permName = sharedPkgList[i];

                PermissionInfo tmpPermInfo = pm.getPermissionInfo(permName, 0);

                stringBuffer.append("========"+permName+"========"+"\n");
                stringBuffer.append(tmpPermInfo.loadLabel(pm).toString()+"\n");
            }


        } catch (PackageManager.NameNotFoundException e) {
            LogUtils.error("权限","报错："+e);
        }

        return stringBuffer.toString();
    }
}
