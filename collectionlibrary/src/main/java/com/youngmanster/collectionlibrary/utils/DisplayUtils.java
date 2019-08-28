package com.youngmanster.collectionlibrary.utils;

import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;

import com.youngmanster.collectionlibrary.R;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.Locale;

/**
 * dp、sp 转换为 px 的工具类
 */
public class DisplayUtils {

	/**
	 * 将px值转换为dip或dp值，保证尺寸大小不变（有精度损失）
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);
	}

	/**
	 * 将px值转换为dip或dp值，保证尺寸大小不变（无精度损失）
	 */
	public static float px2dipByFloat(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (pxValue / scale);
	}

	/**
	 * 将dip或dp值转换为px值，保证尺寸大小不变（有精度损失），类似Context.getDimensionPixelSize方法（四舍五入）
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 * 将dip或dp值转换为px值，保证尺寸大小不变（无精度损失），类似Context.getDimension方法
	 */
	public static float dip2pxByFloat(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (dipValue * scale);
	}

	/**
	 * 将px值转换为sp值，保证文字大小不变
	 */
	public static int px2sp(Context context, float pxValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (pxValue / fontScale + 0.5f);
	}

	/**
	 * 将sp值转换为px值，保证文字大小不变
	 */
	public static int sp2px(Context context, float spValue) {
		final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
		return (int) (spValue * fontScale + 0.5f);
	}

	/**
	 * 屏幕宽度
	 */
	public static int getScreenWidthPixels(Context context) {
		return context.getResources().getDisplayMetrics().widthPixels;
	}

	/**
	 * 屏幕高度
	 */
	public static int getScreenHeightPixels(Context context) {
		return context.getResources().getDisplayMetrics().heightPixels;
	}

	public static String getDisplayInfo(Context context) {
		String infoFormat = "宽:%s,高:%s 宽Dip:%s,高Dip:%s\ndpi:%s,1dip=%sPixels";
		int screenWidthPixels = DisplayUtils.getScreenWidthPixels(context);
		int screenHeightPixels = DisplayUtils.getScreenHeightPixels(context);
		float density = context.getResources().getDisplayMetrics().density;
		Object[] infoFormatValue = {
				screenWidthPixels, screenHeightPixels,
				((int) (screenWidthPixels / density)), ((int) (screenHeightPixels / density)),
				context.getResources().getDisplayMetrics().densityDpi, density
		};
		return String.format(Locale.getDefault(), infoFormat, infoFormatValue);
	}


	//白色可以替换成其他浅色系
	public static void setStatusBarBlackFontBgColor(Activity activity,int bgColor) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
			if (MIUISetStatusBarLightMode(activity.getWindow(), true)) {//MIUI
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP&& Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {//5.0
					activity.getWindow().setStatusBarColor(activity.getResources().getColor(bgColor));
				}else if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
					activity.getWindow().setStatusBarColor(activity.getResources().getColor(bgColor));
					activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
				}
			} else if (FlymeSetStatusBarLightMode(activity.getWindow(), true)) {//Flyme
				if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0
					activity.getWindow().setStatusBarColor(activity.getResources().getColor(bgColor));
				}
			} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//6.0
				activity.getWindow().setStatusBarColor(activity.getResources().getColor(bgColor));
				activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
			}else{
				setStatusBarColor(activity,R.color.black);
			}
		}
	}

	/**
	 * 设置状态栏颜色
	 * */
	public static void setStatusBarColor(Activity activity, int colorResId) {
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			Window window = activity.getWindow();
			window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			window.setStatusBarColor(activity.getResources().getColor(colorResId));
		}

	}

	/**
	 * 设置状态栏字体图标为深色，需要MIUIV6以上
	 *
	 * @param window 需要设置的窗口
	 * @param dark   是否把状态栏字体及图标颜色设置为深色
	 * @return boolean 成功执行返回true
	 */
	public static boolean MIUISetStatusBarLightMode(Window window, boolean dark) {
		boolean result = false;

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0
			if (window != null) {
				Class clazz = window.getClass();
				try {
					int darkModeFlag = 0;
					Class layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
					Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
					darkModeFlag = field.getInt(layoutParams);
					Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
					if (dark) {
						extraFlagField.invoke(window, darkModeFlag, darkModeFlag);//状态栏透明且黑色字体
					} else {
						extraFlagField.invoke(window, 0, darkModeFlag);//清除黑色字体
					}
					result = true;
				} catch (Exception e) {

				}
			}
		}
		return result;
	}


	/**
	 * 设置状态栏图标为深色和魅族特定的文字风格
	 * 可以用来判断是否为Flyme用户
	 *
	 * @param window 需要设置的窗口
	 * @param dark   是否把状态栏字体及图标颜色设置为深色
	 * @return boolean 成功执行返回true
	 */
	public static boolean FlymeSetStatusBarLightMode(Window window, boolean dark) {
		boolean result = false;
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0
			if (window != null) {
				try {
					WindowManager.LayoutParams lp = window.getAttributes();
					Field darkFlag = WindowManager.LayoutParams.class
							.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
					Field meizuFlags = WindowManager.LayoutParams.class
							.getDeclaredField("meizuFlags");
					darkFlag.setAccessible(true);
					meizuFlags.setAccessible(true);
					int bit = darkFlag.getInt(null);
					int value = meizuFlags.getInt(lp);
					if (dark) {
						value |= bit;
					} else {
						value &= ~bit;
					}
					meizuFlags.setInt(lp, value);
					window.setAttributes(lp);
					result = true;
				} catch (Exception e) {

				}
			}
		}
		return result;
	}

	/**
	 * 设置状态栏全屏透明（状态栏字体颜色为默认）
	 * */
	public static boolean setStatusBarFullTranslucent(Activity act) {
		//设置全屏透明状态栏
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			act.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS |
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
			act.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
			act.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			act.getWindow().setStatusBarColor(Color.TRANSPARENT);
			return true;
		}else{
			setStatusBarColor(act,R.color.black);
			return false;
		}
	}

	/**
	 * 设置状态栏全屏透明（状态栏字体颜色为默认黑色）
	 * */

	public static boolean setStatusBarFullTranslucentWithBlackFont(Activity act) {
		//设置全屏透明状态栏
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP&&isCanSetStatusBarBlackFontLightMode(act)) {

			act.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			ViewGroup rootView = (ViewGroup) ((ViewGroup) act.findViewById(android.R.id.content)).getChildAt(0);
			ViewCompat.setFitsSystemWindows(rootView, false);
			rootView.setClipToPadding(true);
			act.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS |
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
			act.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
			act.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			act.getWindow().setStatusBarColor(Color.TRANSPARENT);
			act.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
			return true;
		}else{
			return false;
		}
	}

	/**
	 * 判断状态栏是否可以设置为黑字
	 * */
	public static boolean isCanSetStatusBarBlackFontLightMode(Activity activity) {

		if (MIUISetStatusBarLightMode(activity.getWindow(), true)) {//MIUI
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0
				return true;
			}
		} else if (FlymeSetStatusBarLightMode(activity.getWindow(), true)) {//Flyme
			if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {//5.0
				return true;
			}
		} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {//6.0
			return true;
		}

		return false;
	}


	/**
	 * 获取状态栏的高度
	 * @param context
	 * @return
	 */
	public static int getStatusBarHeight(Context context){
		return context.getResources().getDimensionPixelSize(context.getResources().getIdentifier("status_bar_height", "dimen", "android"));
	}
	/**
	 * 获取ActionBar的高度
	 * @param context
	 * @return
	 */
	public static int getActionBarHeight(Context context){
		TypedArray actionbarSizeTypedArray = context.obtainStyledAttributes(new int[] {
				android.R.attr.actionBarSize
		});

		return (int) actionbarSizeTypedArray.getDimension(0, 0);
	}

}
