package com.youngmanster.collectionlibrary.refreshrecyclerview.pulltorefresh;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.View;

import java.util.Date;

/**
 * Created by yangyan
 * on 2018/3/9.
 */

public class PullToRefreshRecyclerViewUtils {

	private static final String REFRESH_NAME = "REFRESH_KEY";
	private static final String REFRESH_TIME_KEY = "REFRESH_TIME_KEY";

	/**
	 * 获取上次刷新保存的时间
	 * @param context
	 * @return
	 */
	public static long getLastRefreshTime(Context context){
		@SuppressLint("WrongConstant")
		SharedPreferences s =context.getSharedPreferences(REFRESH_NAME, Context.MODE_APPEND);
		return s.getLong(REFRESH_TIME_KEY,new Date().getTime());
	}

	/**
	 * 保存本次刷新的时间
	 * @param refreshTime
	 */
	public static void saveLastRefreshTime(Context context,long refreshTime){
		@SuppressLint("WrongConstant")
		SharedPreferences s = context.getSharedPreferences(REFRESH_NAME,Context.MODE_APPEND);
		s.edit().putLong(REFRESH_TIME_KEY,refreshTime).commit();
	}


	/**
	 * 时间转换
	 * @param time
	 * @return
	 */
	public static String getTimeConvert(long time) {
		//获取time距离当前的秒数
		int ct = (int)((System.currentTimeMillis() - time)/1000);

		if(ct <=15) {
			return "刚刚";
		}

		if(ct >15 && ct < 60) {
			return ct + "秒前";
		}

		if(ct >= 60 && ct < 3600) {
			return Math.max(ct / 60,1) + "分钟前";
		}
		if(ct >= 3600 && ct < 86400)
			return ct / 3600 + "小时前";
		if(ct >= 86400 && ct < 2592000){ //86400 * 30
			int day = ct / 86400 ;
			return day + "天前";
		}
		if(ct >= 2592000 && ct < 31104000) { //86400 * 30
			return ct / 2592000 + "月前";
		}
		return ct / 31104000 + "年前";
	}

	/**
	 * 是否满屏
	 * @param recyclerView
	 * @return
	 */
	public static boolean isFullPage(PullToRefreshRecyclerView recyclerView, int lastPosition){
		//获取最后一个childView
		View lastChildView = recyclerView.getChildAt(lastPosition);
		if(lastChildView==null){
			return true;
		}else{
			//获取第一个childView
			View firstChildView = recyclerView.getChildAt(0);
			int top = firstChildView.getTop();
			int bottom = lastChildView.getBottom();
			//recycleView显示itemView的有效区域的bottom坐标Y
			int bottomEdge =recyclerView.getHeight() - recyclerView.getPaddingBottom();
			//recycleView显示itemView的有效区域的top坐标Y
			int topEdge =recyclerView.getPaddingTop();
			//第一个view的顶部小于top边界值,说明第一个view已经部分或者完全移出了界面
			//最后一个view的底部小于bottom边界值,说明最后一个view已经完全显示在界面
			//若满足这两个条件,说明所有子view已经填充满了recycleView,recycleView可以"真正地"滑动
			if (bottom <= bottomEdge && top < topEdge) {
				return true;
			}
		}

		return false;
	}




}
