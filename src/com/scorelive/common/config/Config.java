package com.scorelive.common.config;

import android.content.Context;
import android.content.SharedPreferences;

public class Config {

	private static final String SP_NAME="score_sharedpreference";
	private static final String QQ_OPEN_ID="qq_openid";
	private static final String QQ_ACCESS_TOKEN="qq_token_access";
	private static final String IS_LOGIN="is_login";
	private static final String NICK_NAME="nickname";
	private static final String EXPIRES_TIME="expires_time";
	
	public static void setOpenId(Context context,String openid){
		SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		sp.edit().putString(QQ_OPEN_ID, openid).commit();
	}
	
	public static String getOpenId(Context context){
		SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		return sp.getString(QQ_OPEN_ID, null);
	}
	
	public static void setAccessToken(Context context,String token){
		SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		sp.edit().putString(QQ_ACCESS_TOKEN, token).commit();
	}
	
	public static String getAccessToken(Context context){
		SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		return sp.getString(QQ_ACCESS_TOKEN, null);
	}
	
	public static void setExpiresTime(Context context,long time){
		SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		sp.edit().putLong(EXPIRES_TIME, time).commit();
	}
	
	public static long getExpiresTime(Context context){
		SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		return sp.getLong(EXPIRES_TIME, 0);
	}
	
	public static void setIsLogin(Context context,boolean isLogin){
		SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		sp.edit().putBoolean(IS_LOGIN, isLogin).commit();
	}
	
	public static boolean getIsLogin(Context context){
		SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		return sp.getBoolean(IS_LOGIN, false);
	}
	
	
	public static void setNickName(Context context,String nickname){
		SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		sp.edit().putString(NICK_NAME, nickname).commit();
	}
	
	public static String getNickName(Context context){
		SharedPreferences sp = context.getSharedPreferences(SP_NAME, Context.MODE_PRIVATE);
		return sp.getString(NICK_NAME,null);
	}
	
}
