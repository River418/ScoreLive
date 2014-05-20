package com.scorelive.common.push;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.widget.Toast;

import com.scorelive.MainActivity;
import com.scorelive.R;
import com.scorelive.common.utils.JsonUtils;
import com.scorelive.module.PushInfo;
import com.tencent.android.tpush.XGPushBaseReceiver;
import com.tencent.android.tpush.XGPushClickedResult;
import com.tencent.android.tpush.XGPushRegisterResult;
import com.tencent.android.tpush.XGPushShowedResult;
import com.tencent.android.tpush.XGPushTextMessage;

/**
 * 本类主要用于接收消息和处理结果反馈<br>
 * APP可以参考本类，实现自己的Receiver<br>
 * 
 * 常见的错误码：<br>
 * 0：表示成功<br>
 * 1：系统错误，指针非法，内存错误等 <br>
 * 2：非法参数<br>
 * 其它：内部错误<br>
 * 
 * 
 * Copyright (c) 1998-2014 Tencent
 * 
 * @author foreachli Email: foreachli@tencent.com
 */
public class PushReceiver extends XGPushBaseReceiver {
	public static final String LogTag = "TPushReceiver";

	private void show(Context context, String text) {
		Toast.makeText(context, text, Toast.LENGTH_SHORT).show();
	}

	/**
	 * 注册结果
	 * 
	 * @param context
	 *            APP上下文对象
	 * @param errorCode
	 *            错误码，{@link XGPushBaseReceiver#SUCCESS}表示成功，其它表示失败
	 * @param registerMessage
	 *            注册结果返回
	 */
	@Override
	public void onRegisterResult(Context context, int errorCode,
			XGPushRegisterResult registerMessage) {
	}

	/**
	 * 反注册结果
	 * 
	 * @param context
	 *            APP上下文对象
	 * @param errorCode
	 *            错误码，{@link XGPushBaseReceiver#SUCCESS}表示成功，其它表示失败
	 */
	@Override
	public void onUnregisterResult(Context context, int errorCode) {
	}

	/**
	 * 设置标签操作结果
	 * 
	 * @param context
	 *            APP上下文对象
	 * @param errorCode
	 *            错误码，{@link XGPushBaseReceiver#SUCCESS}表示成功，其它表示失败
	 * @tagName 标签名称
	 */
	@Override
	public void onSetTagResult(Context context, int errorCode, String tagName) {
	}

	/**
	 * 删除标签操作结果
	 * 
	 * @param context
	 *            APP上下文对象
	 * @param errorCode
	 *            错误码，{@link XGPushBaseReceiver#SUCCESS}表示成功，其它表示失败
	 * @tagName 标签名称
	 */
	@Override
	public void onDeleteTagResult(Context context, int errorCode, String tagName) {
	}

	/**
	 * 收到消息<br>
	 * 
	 * @param context
	 *            APP上下文对象
	 * @param message
	 *            收到的消息
	 */
	@Override
	public void onTextMessage(Context context, XGPushTextMessage message) {
		if (context == null || message == null) {
			return;
		}
		String text = "收到消息:" + message.toString();
		String content = message.getContent();
		PushInfo info = JsonUtils.pushJson2Match(content);
		NotificationManager nm = (NotificationManager) context
				.getSystemService(Context.NOTIFICATION_SERVICE);
		Intent noticeIntent = new Intent();
		String tickerText = "";
		PendingIntent contentIntent = null;
		StringBuffer sb = new StringBuffer();
		String updateTitle = null;
		NotificationCompat.Builder builder = new NotificationCompat.Builder(
				context.getApplicationContext());
		builder.setWhen(System.currentTimeMillis());
		builder.setAutoCancel(true);

		tickerText = message.getTitle();
		builder.setTicker(tickerText);
		updateTitle = info.homeName + "vs" + info.visitName;
		builder.setSmallIcon(R.drawable.ic_launcher);
		builder.setContentTitle(updateTitle);
		builder.setContentText(info.liveScore);
		noticeIntent.setClass(context, MainActivity.class);
		noticeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
				| Intent.FLAG_ACTIVITY_NEW_TASK);
		// notification = new Notification(R.drawable.icon_notify,
		// tickerText, System.currentTimeMillis());
		contentIntent = PendingIntent.getActivity(context, 1001, noticeIntent,
				PendingIntent.FLAG_UPDATE_CURRENT);
		builder.setContentIntent(contentIntent);
		Notification notification = builder.build();
		nm.notify(1001, notification);
		Toast.makeText(context, updateTitle+" "+info.liveScore, Toast.LENGTH_LONG).show();
	}

	/**
	 * 通知被打开结果反馈
	 * 
	 * @param context
	 *            APP上下文对象
	 * @param message
	 *            被打开的消息对象
	 */
	@Override
	public void onNotifactionClickedResult(Context context,
			XGPushClickedResult message) {
	}

	@Override
	public void onNotifactionShowedResult(Context context,
			XGPushShowedResult notifiShowedRlt) {
	}
}
