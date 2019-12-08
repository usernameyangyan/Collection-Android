package com.youngmanster.collectionlibrary.permission;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;

import java.util.ArrayList;
import java.util.List;

/**
 * 权限检测
 * Created by yangyan
 * on 2018/3/22.
 */

public class PermissionManager {

	public static final int EXIST_NECESSARY_PERMISSIONS_PROHIBTED = 10001;//存在必要权限被禁止
	public static final int EXIST_NECESSARY_PERMISSIONS_PROHIBTED_NOT_REMIND = 1002;//存在必要权限永远不提示禁止
	public static final int PERMISSION_REQUEST_CODE = 0; // 系统权限管理页面的参数
	private static final String PACKAGE_URL_SCHEME = "package:";
	private Activity mActivity;
	private List<String> necessaryPermissions = new ArrayList<>();
	private List<String> deniedPermissions = new ArrayList<>();


	private PermissionManager() {
		necessaryPermissions.clear();
		deniedPermissions.clear();

	}


	public static PermissionManager with(Activity activity) {
		PermissionManager permissionBuilder = new PermissionManager();
		permissionBuilder.mActivity = activity;
		return permissionBuilder;
	}

	/**
	 * 项目必要的权限
	 *
	 * @param permissions
	 * @return
	 */
	public PermissionManager setNecessaryPermissions(String... permissions) {
		necessaryPermissions.clear();
		for (String permission : permissions) {
			necessaryPermissions.add(permission);
		}
		return this;
	}

	/**
	 * 检查是否缺少权限
	 *
	 * @return
	 */
	public boolean isLackPermission(String permission) {
		if (ActivityCompat.checkSelfPermission(mActivity, permission) == PackageManager.PERMISSION_DENIED) {
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 检查是否缺少权限
	 *
	 * @return
	 */
	public boolean isLackPermission() {

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			for(String permission:necessaryPermissions){
				if(ActivityCompat.checkSelfPermission(mActivity, permission) == PackageManager.PERMISSION_DENIED){
					return true;
				}
			}

			return false;
		}else {
			return false;
		}
	}

	/**
	 * 得到还没有处理的权限
	 */
	public String[] getDeniedPermissions() {
		String[] permissions;
		for (String permission : necessaryPermissions) {
			if (isLackPermission(permission)) {
				deniedPermissions.add(permission);
			}
		}
		if (deniedPermissions.size() == 0) {
			return null;
		} else {
			permissions = deniedPermissions.toArray(new String[deniedPermissions.size()]);
			return permissions;
		}
	}

	/**
	 * 权限请求
	 */
	public void requestPermissions() {
		if (getDeniedPermissions() != null) {
			ActivityCompat.requestPermissions(mActivity, getDeniedPermissions(), PERMISSION_REQUEST_CODE);
		}
	}

	private boolean isNotRemind;
	private boolean isRemind;

	/**
	 * 是否存在必要权限没有允许
	 */
	public int getShouldShowRequestPermissionsCode() {
		isNotRemind = false;
		isRemind = false;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

			for (int i = 0; i < deniedPermissions.size(); i++) {
				boolean isTip = ActivityCompat.shouldShowRequestPermissionRationale(mActivity, deniedPermissions.get(i));
				if (isLackPermission(deniedPermissions.get(i))) {
					if (isTip) {//表明用户没有彻底禁止弹出权限请求
						isRemind = true;
					} else {//表明用户已经彻底禁止弹出权限请求
						isNotRemind = true;
					}
				}
			}

			if (isRemind) {
				return EXIST_NECESSARY_PERMISSIONS_PROHIBTED;
			} else if (isNotRemind) {
				return EXIST_NECESSARY_PERMISSIONS_PROHIBTED_NOT_REMIND;

			}

		} else {
			return 0;
		}

		return 0;
	}

	/**
	 * 装转到应用设置页面
	 */
	public void startAppSettings() {
		Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
		intent.setData(Uri.parse(PACKAGE_URL_SCHEME + mActivity.getPackageName()));
		mActivity.startActivity(intent);
	}

}
