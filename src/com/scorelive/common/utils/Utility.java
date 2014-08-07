package com.scorelive.common.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.List;

import android.app.ActivityManager;
import android.app.ActivityManager.RunningAppProcessInfo;
import android.app.KeyguardManager;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;
import android.util.Log;
import android.widget.Toast;

import com.scorelive.R;

public class Utility {
	private static Toast mToast;

	/**
	 * 网络是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isNetworkAvaiable(Context context) {
		if (context == null) {
			return false;
		}
		ConnectivityManager cm = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo info = cm.getActiveNetworkInfo();
		if (info != null && info.isConnected()) {
			NetworkInfo.State state = info.getState();
			if (state == NetworkInfo.State.CONNECTED) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 是否为2G和2.5G网络
	 * 
	 * @param context
	 * @return
	 */
	public static boolean isBadNetwork(Context context) {
		if (isNetworkAvaiable(context)) {
			ConnectivityManager connectivityManager = (ConnectivityManager) context
					.getSystemService(Context.CONNECTIVITY_SERVICE);
			if (connectivityManager != null) {
				NetworkInfo activeInfo = connectivityManager
						.getActiveNetworkInfo();
				if (activeInfo != null
						&& activeInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
					if (activeInfo.getSubtype() == TelephonyManager.NETWORK_TYPE_EDGE
							|| activeInfo.getSubtype() == TelephonyManager.NETWORK_TYPE_CDMA
							|| activeInfo.getSubtype() == TelephonyManager.NETWORK_TYPE_GPRS) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static int pickLeagueColor(Context context, int index) {
		Resources res = context.getResources();
		TypedArray bks = res.obtainTypedArray(R.array.league_items);
		return bks.getColor(index, R.color.league_color_default);
	}

	public static boolean hasMatchStarted(String startTime) {
		SimpleDateFormat format = new SimpleDateFormat("YYYY-MM-DD HH:MM:SS");
		try {
			long millStartTime = format.parse(startTime).getTime();
			if (Calendar.getInstance().getTimeInMillis() > millStartTime) {
				return true;
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return false;
	}

	public static String getDateOfToday(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(Calendar.getInstance().getTime());
//		int year = Calendar.getInstance().get(Calendar.YEAR);
//		int month = Calendar.getInstance().get(Calendar.MONTH) + 1;
//		int day = Calendar.getInstance().get(Calendar.DATE);
//		String mm = null;
//		if (month < 10) {
//			mm = "0" + month;
//		} else {
//			mm = String.valueOf(month);
//		}
//		if (format.equalsIgnoreCase("yyyy-MM-dd")) {
//			return year + "-" + mm + "-" + day;
//		} else {
//			Log.e("date", year + "" + mm + "" + day);
//			return year + "" + mm + "" + day;
//		}
	}

	public static int caculateMatchingTime(String startTime) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		// SimpleDateFormat format = new SimpleDateFormat("hh:mm")
		// int year = Calendar.getInstance().get(Calendar.YEAR);
		// int month = Calendar.getInstance().get(Calendar.MONTH)+1;
		// int day = Calendar.getInstance().get(Calendar.DATE)-1;
		try {
			// long millStartTime = format.parse(year +"-"+
			// month+"-"+day+" "+startTime+":00").getTime();
			long millStartTime = format.parse(startTime).getTime();
			long time = Calendar.getInstance().getTimeInMillis()
					- millStartTime;
			if (time > 0) {// 比赛进行时间
				int minuteTime = (int) (time / (60 * 1000));
				if (minuteTime > 60) {
					return minuteTime - 15;
				} else if (minuteTime == 0) {
					return 1;
				} else {
					return minuteTime;
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}

	// public static String updateMatchingTime(String startTime){
	// SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
	// // SimpleDateFormat format = new SimpleDateFormat("hh:mm")
	// int year = Calendar.getInstance().get(Calendar.YEAR);
	// int month = Calendar.getInstance().get(Calendar.MONTH)+1;
	// int day = Calendar.getInstance().get(Calendar.DATE);
	// }

	public static String parseTimeToDate(String startTime) {
		return startTime.substring(5, 16);
	}

	/**
	 * 转换图片成圆形
	 * 
	 * @param bitmap
	 *            传入Bitmap对象
	 * @return
	 */
	public static Bitmap toRoundBitmap(Bitmap bitmap) {
		if (bitmap == null) {
			return null;
		}
		int w = bitmap.getWidth();
		int h = bitmap.getHeight();
		int x = Math.max(w, h);
		Bitmap output = Bitmap.createBitmap(x, x, Bitmap.Config.ARGB_8888);
		Canvas canvas = new Canvas(output);

		final int color = 0xffffffff;
		final Paint paint = new Paint();
		// 根据原来图片大小画一个矩形
		final Rect rect = new Rect(0, 0, x, x);
		paint.setAntiAlias(true);
		paint.setColor(color);
		// 画出一个圆
		canvas.drawCircle(x / 2, x / 2, x / 2, paint);
		// 取两层绘制交集,显示上层
		paint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));
		// 将图片画上去
		canvas.drawBitmap(bitmap, rect, rect, paint);
		// 返回Bitmap对象
		return output;
	}

	public static boolean saveFile(InputStream is, String path) {
		File file = new File(path);
		if (!file.exists()) {
			if (!file.getParentFile().exists()) {
				file.mkdirs();
			}
			try {
				file.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
		FileOutputStream fos = null;
		try {
			fos = new FileOutputStream(file);
			int len = 0;
			byte[] buf = new byte[1024];
			while ((len = is.read(buf)) != -1) {
				fos.write(buf, 0, len);
			}
			fos.flush();
			fos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			if (file != null) {
				file.delete();
			}
			return false;
		}
		return true;
	}

	public static Bitmap getBitmapFromFile(String path) {
		return BitmapFactory.decodeFile(path);
	}

	
	/**
	 * 判断App是否在后台
	 * @param context
	 * @return
	 */
	public static boolean isBackgroundRunning(Context context) {
		ActivityManager activityManager = (ActivityManager) context
				.getSystemService(context.ACTIVITY_SERVICE);
		KeyguardManager keyguardManager = (KeyguardManager) context
				.getSystemService(context.KEYGUARD_SERVICE);

		if (activityManager == null)
			return false;
		// get running application processes
		List<ActivityManager.RunningAppProcessInfo> processList = activityManager
				.getRunningAppProcesses();
		for (ActivityManager.RunningAppProcessInfo process : processList) {
			if (process.processName.equals(context.getPackageName())) {
				boolean isBackground = process.importance != RunningAppProcessInfo.IMPORTANCE_FOREGROUND
						&& process.importance != RunningAppProcessInfo.IMPORTANCE_VISIBLE;
				boolean isLockedState = keyguardManager
						.inKeyguardRestrictedInputMode();
				if (isBackground || isLockedState)
					return true;
				else
					return false;
			}
		}
		return false;
	}

}
