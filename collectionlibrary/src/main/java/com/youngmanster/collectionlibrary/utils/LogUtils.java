package com.youngmanster.collectionlibrary.utils;

import android.annotation.SuppressLint;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.youngmanster.collectionlibrary.config.Config;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 答应日记工具类
 */
public class LogUtils {

	public static boolean DISP;

	public static String DEFAULT_TAG = "Logutils";

	static {
		System.out.println("dLogStart:" + DISP);
		DISP = Config.DEBUG;
		System.out.println("dLogEnd:" + DISP);
	}

	/**
	 * 获取输出日志的类，方法，输出行数
	 */
	private static String generateTag(StackTraceElement caller) {
		String tag = "%s.%s(L:%d)";
		String callerClazzName = caller.getClassName();
		callerClazzName = callerClazzName.substring(callerClazzName.lastIndexOf(".") + 1);
		tag = String.format(tag, callerClazzName, caller.getMethodName(), caller.getLineNumber());
		tag = TextUtils.isEmpty(DEFAULT_TAG) ? tag : DEFAULT_TAG + ":" + tag;
		return tag;
	}

	public static StackTraceElement getCallerStackTraceElement() {
		return Thread.currentThread().getStackTrace()[4];
	}

	public static void debug(String TAG, String message) {
		if (DISP) {
			debug(TAG, TAG+message, null);
		}
	}

	public static void debug(String TAG, String message, Throwable t) {
		if (DISP) {
			if (message != null) {
				Log.d(TAG, "~~~~~  " + message);
			}
			if (t != null) {
				Log.d(TAG, t.toString());
			}
		}
	}

	/**
	 * @param message 要输出的日志
	 */
	public static void info(String message) {
		StackTraceElement caller = getCallerStackTraceElement();
		String tag = generateTag(caller);
		info(tag, message);
	}

	/**
	 * @param message 要输出的日志
	 */
	public static void info(String message, Object... args) {
		info(String.format(message, args));
	}

	public static void info(String TAG, String message) {
		info(TAG, message, null);
	}

	public static void info(String TAG, String message, Throwable t) {
		if (DISP) {
			if (message != null) {
				Log.i(TAG, message);
			}
			if (t != null) {
				Log.i(TAG, t.toString());
			}
		}
	}

	public static void infoF(String format, Object... args) {
		String message = String.format(format, args);
		StackTraceElement caller = getCallerStackTraceElement();
		String tag = generateTag(caller);
		info(tag, message);
	}

	public static void warn(String TAG, String message) {
		if (DISP) {
			warn(TAG, message, null);
		}
	}

	public static void warn(String TAG, String message, Throwable t) {
		if (DISP) {
			if (message != null) {
				Log.w(TAG, message);
			}
			if (t != null) {
				Log.w(TAG, t.toString());
			}
		}
	}

	public static void error(String TAG, String message) {
		if (DISP) {
			error(TAG, message, null);
		}
	}

	public static void error(String TAG, String message, Throwable t) {
		if (DISP) {
			if (message != null) {
				Log.e(TAG, "~~~~~  " + message);
			}
			if (t != null) {
				Log.e(TAG, t.toString());
			}
		}
	}

	// 测试日志
	public static void log(String TAG, String message) {
		if (DISP) {
			if (message != null) {
				Log.e(TAG, message);
			}
		}
	}

	/**
	 * 保存异常日志
	 *
	 * @param excp
	 */
	@SuppressLint("SimpleDateFormat")
	public static void saveErrorLog(Exception excp) {
		if (DISP) {
			SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String errorlog = "errorlog" + sDateFormat.format(new Date()) + ".txt";

			String savePath = "";
			String logFilePath = "";
			FileWriter fw = null;
			PrintWriter pw = null;
			try {
				// 判断是否挂载了SD卡
				String storageState = Environment.getExternalStorageState();
				if (storageState.equals(Environment.MEDIA_MOUNTED)) {
					savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/9KUSREADER/Log/";
					File file = new File(savePath);
					if (!file.exists()) {
						file.mkdirs();
					}
					logFilePath = savePath + errorlog;
				}
				// 没有挂载SD卡，无法写文件
				if (logFilePath == "") {
					return;
				}
				File logFile = new File(logFilePath);
				if (!logFile.exists()) {
					logFile.createNewFile();
				}
				fw = new FileWriter(logFile, true);
				pw = new PrintWriter(fw);
				pw.println("--------------------" + (new Date().toLocaleString()) + "---------------------");
				Log.d("saveErrorLog", (new Date().toLocaleString()));

				excp.printStackTrace(pw);
				pw.close();
				fw.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (pw != null) {
					pw.close();
				}
				if (fw != null) {
					try {
						fw.close();
					} catch (IOException e) {
                        e.printStackTrace();
					}
				}
			}
		}

	}

	/**
	 * 保存异常日志
	 *
	 * @param logString
	 */
	@SuppressLint("SimpleDateFormat")
	public static void saveLog(String logString) {
		if (DISP) {
			SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String errorlog = "Requestlog" + sDateFormat.format(new Date()) + ".txt";
			String savePath = "";
			String logFilePath = "";
			FileWriter fw = null;
			PrintWriter pw = null;
			try {
				// 判断是否挂载了SD卡
				String storageState = Environment.getExternalStorageState();
				if (storageState.equals(Environment.MEDIA_MOUNTED)) {
					savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/9KUSREADER/Log/";
					File file = new File(savePath);
					if (!file.exists()) {
						file.mkdirs();
					}
					logFilePath = savePath + errorlog;
				}
				// 没有挂载SD卡，无法写文件
				if (logFilePath == "") {
					return;
				}
				File logFile = new File(logFilePath);
				if (!logFile.exists()) {
					logFile.createNewFile();
				}
				fw = new FileWriter(logFile, true);
				pw = new PrintWriter(fw);
				pw.println("--------------------" + (new Date().toLocaleString()) + "---------------------");
				pw.println(logString);
				pw.close();
				fw.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (pw != null) {
					pw.close();
				}
				if (fw != null) {
					try {
						fw.close();
					} catch (IOException e) {
                        e.printStackTrace();
					}
				}
			}
		}

	}

	/**
	 * 保存异常日志
	 *
	 * @param logString
	 */
	@SuppressLint("SimpleDateFormat")
	public static void saveImagesLog(String logString) {
		if (DISP) {
			SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String errorlog = "ImagesRequestlog" + sDateFormat.format(new Date()) + ".txt";
			String savePath = "";
			String logFilePath = "";
			FileWriter fw = null;
			PrintWriter pw = null;
			try {
				// 判断是否挂载了SD卡
				String storageState = Environment.getExternalStorageState();
				if (storageState.equals(Environment.MEDIA_MOUNTED)) {
					savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/9KUSREADER/Log/";
					File file = new File(savePath);
					if (!file.exists()) {
						file.mkdirs();
					}
					logFilePath = savePath + errorlog;
				}
				// 没有挂载SD卡，无法写文件
				if (logFilePath == "") {
					return;
				}
				File logFile = new File(logFilePath);
				if (!logFile.exists()) {
					logFile.createNewFile();
				}
				fw = new FileWriter(logFile, true);
				pw = new PrintWriter(fw);
				pw.println("--------------------" + (new Date().toLocaleString()) + "---------------------");
				pw.println(logString);
				pw.close();
				fw.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (pw != null) {
					pw.close();
				}
				if (fw != null) {
					try {
						fw.close();
					} catch (IOException e) {
                        e.printStackTrace();
					}
				}
			}
		}

	}

	/**
	 * 保存异常日志
	 *
	 * @param logString
	 */
	@SuppressLint("SimpleDateFormat")
	public static void saveRequestLog(String logString) {
		if (DISP) {
			SimpleDateFormat sDateFormat = new SimpleDateFormat("yyyy-MM-dd");
			String errorlog = "saveRequestLog" + sDateFormat.format(new Date()) + ".txt";
			String savePath = "";
			String logFilePath = "";
			FileWriter fw = null;
			PrintWriter pw = null;
			try {
				// 判断是否挂载了SD卡
				String storageState = Environment.getExternalStorageState();
				if (storageState.equals(Environment.MEDIA_MOUNTED)) {
					savePath = Environment.getExternalStorageDirectory().getAbsolutePath() + "/9KUSREADER/Log/";
					File file = new File(savePath);
					if (!file.exists()) {
						file.mkdirs();
					}
					logFilePath = savePath + errorlog;
				}
				// 没有挂载SD卡，无法写文件
				if (logFilePath == "") {
					return;
				}
				File logFile = new File(logFilePath);
				if (!logFile.exists()) {
					logFile.createNewFile();
				}
				fw = new FileWriter(logFile, true);
				pw = new PrintWriter(fw);
				pw.println("--------------------" + (new Date().toLocaleString()) + "---------------------");
				pw.println(logString);
				pw.close();
				fw.close();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				if (pw != null) {
					pw.close();
				}
				if (fw != null) {
					try {
						fw.close();
					} catch (IOException e) {
                        e.printStackTrace();
					}
				}
			}
		}

	}
}
