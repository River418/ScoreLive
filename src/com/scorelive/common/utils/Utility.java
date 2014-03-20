package com.scorelive.common.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

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
}
