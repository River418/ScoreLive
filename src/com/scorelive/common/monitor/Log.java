package com.scorelive.common.monitor;

public class Log {

	private static boolean isPrintLog = false;

	public static void isPrintLog() {
		if (Debug.isDebug) {
			isPrintLog = true;
		}
	}

	public static void e(String tag, String msg) {
		if (isPrintLog)
			android.util.Log.e(tag, msg);
	}

	public static void i(String tag, String msg) {
		if (isPrintLog)
			android.util.Log.i(tag, msg);
	}

	public static void w(String tag, String msg) {
		if (isPrintLog)
			android.util.Log.w(tag, msg);

	}

	public static void d(String tag, String msg) {
		if (isPrintLog)
			android.util.Log.d(tag, msg);
	}

	public static void v(String tag, String msg) {
		if (isPrintLog)
			android.util.Log.v(tag, msg);
	}

	public static void println(String msg) {
		if (isPrintLog)
			System.out.println(msg);
	}
}
