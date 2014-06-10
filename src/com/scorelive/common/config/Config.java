package com.scorelive.common.config;

import android.content.Context;
import android.content.SharedPreferences;

public class Config {

	private static final String SP_NAME="score_sharedpreference";
	private static final String QQ_OPEN_ID="qq_openid";
	private static final String QQ_ACCESS_TOKEN="qq_token_access";
	
	public static void setQQOpenId(Context context,String openid){
		SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		sp.edit().putString(QQ_OPEN_ID, openid).commit();
	}
	
	public static String getQQOpenId(Context context){
		SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		return sp.getString(QQ_OPEN_ID, null);
	}
	
	public static void setQQAccessToken(Context context,String token){
		SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		sp.edit().putString(QQ_ACCESS_TOKEN, token).commit();
	}
	
	public static String getQQAccessToken(Context context){
		SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		return sp.getString(QQ_ACCESS_TOKEN, null);
	}
	
}
