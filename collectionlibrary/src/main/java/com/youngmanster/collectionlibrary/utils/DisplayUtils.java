package com.youngmanster.collectionlibrary.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Color;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.LinearLayout;

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

		setMIUIStatusBarDarkIcon(activity,true);
		setMeizuStatusBarDarkIcon(activity,true);

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP && Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {//5.0
			activity.getWindow().setStatusBarColor(ContextCompat.getColor(activity,bgColor));
		} else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			activity.getWindow().setStatusBarColor(ContextCompat.getColor(activity,bgColor));
			activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR);
		}else{
			setStatusBarColor(activity, R.color.black);
		}
	}


	/**
	 * 修改 MIUI V6  以上状态栏颜色
	 */
	private static void setMIUIStatusBarDarkIcon(@NonNull Activity activity, boolean darkIcon) {
		Class<? extends Window> clazz = activity.getWindow().getClass();
		try {
			Class<?> layoutParams = Class.forName("android.view.MiuiWindowManager$LayoutParams");
			Field field = layoutParams.getField("EXTRA_FLAG_STATUS_BAR_DARK_MODE");
			int darkModeFlag = field.getInt(layoutParams);
			Method extraFlagField = clazz.getMethod("setExtraFlags", int.class, int.class);
			extraFlagField.invoke(activity.getWindow(), darkIcon ? darkModeFlag : 0, darkModeFlag);
		} catch (Exception e) {
			//e.printStackTrace();
		}
	}

	/**
	 * 修改魅族状态栏字体颜色 Flyme 4.0
	 */
	private static void setMeizuStatusBarDarkIcon(@NonNull Activity activity, boolean darkIcon) {
		try {
			WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
			Field darkFlag = WindowManager.LayoutParams.class.getDeclaredField("MEIZU_FLAG_DARK_STATUS_BAR_ICON");
			Field meizuFlags = WindowManager.LayoutParams.class.getDeclaredField("meizuFlags");
			darkFlag.setAccessible(true);
			meizuFlags.setAccessible(true);
			int bit = darkFlag.getInt(null);
			int value = meizuFlags.getInt(lp);
			if (darkIcon) {
				value |= bit;
			} else {
				value &= ~bit;
			}
			meizuFlags.setInt(lp, value);
			activity.getWindow().setAttributes(lp);
		} catch (Exception e) {
			//e.printStackTrace();
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
	 * 设置状态栏全屏透明（状态栏字体颜色为默认）
	 * */
	public static void setStatusBarFullTranslucent(Activity act) {
		//设置全屏透明状态栏
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			act.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS |
					WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
			act.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN | View.SYSTEM_UI_FLAG_LAYOUT_STABLE);
			act.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			act.getWindow().setStatusBarColor(Color.TRANSPARENT);

		}else{
			setStatusBarColor(act, R.color.black);
		}
	}


	public static void setStatusBarFullTranslucentWithBlackFont(Activity activity){
		transparentStatusBar(activity);

		setMIUIStatusBarDarkIcon(activity, true);
		setMeizuStatusBarDarkIcon(activity, true);
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
			activity.getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR |  View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
		}
	}

	/**
	 * 使状态栏透明
	 */
	@TargetApi(Build.VERSION_CODES.KITKAT)
	private static void transparentStatusBar(Activity activity) {

		if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
			return;
		}

		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
			activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
			activity.getWindow().setStatusBarColor( Color.TRANSPARENT);
		} else {
			activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
		}
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
