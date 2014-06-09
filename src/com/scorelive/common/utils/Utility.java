package com.scorelive.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.impl.cookie.DateParseException;
import org.apache.http.impl.cookie.DateUtils;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.Bitmap.Config;
import android.graphics.PorterDuff.Mode;
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

	public static int caculateMatchingTime(String startTime) {
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		try {
			long millStartTime = format.parse(startTime).getTime();
			long time = Calendar.getInstance().getTimeInMillis()
					- millStartTime;
			if (time > 0) {//比赛进行时间
				int minuteTime = (int) (time/(60*1000));
				if(minuteTime>60){
					return minuteTime-15;
				}else if(minuteTime == 0){
					return 1;
				}else{
					return minuteTime;
				}
			}
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return -1;
	}
	
	public static String parseTimeToDate(String startTime){
		return startTime.substring(0, 10);
	}
	
	/**
	 * 转换图片成圆形
	 * 
	 * @param bitmap
	 *            传入Bitmap对象
	 * @return
	 */
	public static Bitmap toRoundBitmap(Bitmap bitmap) {
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
	
	/**
	 * 打印消息并且用Toast显示消息
	 * 
	 * @param activity
	 * @param message
	 * @param logLevel
	 *            填d, w, e分别代表debug, warn, error; 默认是debug
	 */
	public static final void toastMessage(final Activity activity,
			final String message, String logLevel) {
		if ("w".equals(logLevel)) {
			Log.w("sdkDemo", message);
		} else if ("e".equals(logLevel)) {
			Log.e("sdkDemo", message);
		} else {
			Log.d("sdkDemo", message);
		}
		activity.runOnUiThread(new Runnable() {
			@Override
			public void run() {
				// TODO Auto-generated method stub
				if (mToast != null) {
					mToast.cancel();
					mToast = null;
				}
				mToast = Toast.makeText(activity, message, Toast.LENGTH_SHORT);
				mToast.show();
			}
		});
	}

	/**
	 * 打印消息并且用Toast显示消息
	 * 
	 * @param activity
	 * @param message
	 * @param logLevel
	 *            填d, w, e分别代表debug, warn, error; 默认是debug
	 */
	public static final void toastMessage(final Activity activity,
			final String message) {
		toastMessage(activity, message, null);
	}

}
