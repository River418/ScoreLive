package com.scorelive.common.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.http.impl.cookie.DateParseException;
import org.apache.http.impl.cookie.DateUtils;

import android.content.Context;
import android.content.res.Resources;
import android.content.res.TypedArray;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.scorelive.R;

public class Utility {

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
}
