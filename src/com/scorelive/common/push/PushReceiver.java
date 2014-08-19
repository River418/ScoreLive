package com.scorelive.common.push;

import java.util.ArrayList;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.Toast;

import com.scorelive.MainActivity;
import com.scorelive.R;
import com.scorelive.common.config.AppConstants;
import com.scorelive.common.utils.JsonUtils;
import com.scorelive.common.utils.Utility;
import com.scorelive.module.Match;
import com.scorelive.ui.widget.ScoreToast;
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
		if (errorCode != 0) {
			Toast.makeText(context, errorCode + ":注册错误", Toast.LENGTH_SHORT)
					.show();
		}
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
		if (errorCode != 0) {
			Toast.makeText(context, errorCode + ":反注册错误", Toast.LENGTH_SHORT)
					.show();
		}
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
		Utility.saveFile(message.getContent(), AppConstants.SCORELIVE_FOLDER
				+ "push/" + message.getTitle());
		String text = "收到消息:" + message.toString();
		Log.e("push", text);
		String content = message.getContent();
		ArrayList<Match> list = JsonUtils.pushJson2Match(content);
		Intent intent = new Intent(AppConstants.ActionType.UPDATE_MATCH_INFO);
		for (Match info : list) {
			// intent.putExtra("type", info.type);
			// intent.putExtra("stime", info.sTime);
			// intent.putExtra("matchId", info.id);
			switch (info.matchState) {
			case AppConstants.EventType.UP_START:
			case AppConstants.EventType.DOWN_START:
			case AppConstants.EventType.UP_OVER:
			case AppConstants.EventType.DOWN_OVER:
			case AppConstants.EventType.ADDED_START:
			case AppConstants.EventType.ADDED_OVER:
			case AppConstants.EventType.ALL_OVER:
				break;
			default:
				NotificationManager nm = (NotificationManager) context
						.getSystemService(Context.NOTIFICATION_SERVICE);
				Intent noticeIntent = new Intent();
				String tickerText = "";
				PendingIntent contentIntent = null;
				StringBuffer sb = new StringBuffer();
				String updateTitle = null;
				NotificationCompat.Builder builder = new NotificationCompat.Builder(
						context.getApplicationContext());
				// RemoteViews view = new
				// RemoteViews(context.getPackageName(),R.layout.score_match_item);
				// builder.setContent(view);
				builder.setWhen(System.currentTimeMillis());
				builder.setAutoCancel(true);

				updateTitle = info.hostTeamName + " VS " + info.visitTeamName;
				builder.setSmallIcon(R.drawable.ic_launcher);
				builder.setContentTitle(updateTitle);
				String contentText = null;
				switch (info.matchState) {
				case AppConstants.EventType.HOME_GOAL:
					contentText = info.hostTeamName + "进球了！";
					break;
				case AppConstants.EventType.VISIT_GOAL:
					contentText = info.visitTeamName + "进球了！";
					break;
				case AppConstants.EventType.HOME_YELLOW:
					contentText = info.hostTeamName + "黄牌了！";
					break;
				case AppConstants.EventType.VISIT_YELLOW:
					contentText = info.visitTeamName + "黄牌了！";
					break;
				case AppConstants.EventType.HOME_RED:
					contentText = info.hostTeamName + "红牌了！";
					break;
				case AppConstants.EventType.VISIT_RED:
					contentText = info.visitTeamName + "红牌了！";
					break;

				}
				builder.setContentText(contentText);
				noticeIntent.setClass(context, MainActivity.class);
				noticeIntent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP
						| Intent.FLAG_ACTIVITY_NEW_TASK);
				// notification = new Notification(R.drawable.icon_notify,
				// tickerText, System.currentTimeMillis());
				contentIntent = PendingIntent.getActivity(context,
						Integer.valueOf(info.hostTeamScore), noticeIntent,
						PendingIntent.FLAG_UPDATE_CURRENT);
				builder.setContentIntent(contentIntent);
				builder.setTicker(contentText);
				Notification notification = builder.build();
				nm.notify(Integer.valueOf(info.matchId), notification);
				ScoreToast.makeText(
						context,
						updateTitle + " " + info.hostTeamScore + ":"
								+ info.visitTeamScore, Toast.LENGTH_LONG)
						.show();
				break;
			}

		}
		Bundle bundle = new Bundle();
		bundle.putParcelableArrayList("push_info", list);
		// intent.putExtra("push_info", list);
		intent.putExtras(bundle);
		context.sendBroadcast(intent);
	}

	private void setRemoteViewContent(RemoteViews view, Match match) {
		// view.
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
