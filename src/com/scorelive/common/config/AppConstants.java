package com.scorelive.common.config;

import java.io.File;

public class AppConstants {

	public static final String SERVER_URL = "http://203.195.200.238:8080/ScoreLive/";
	public static final String MATCH_INFO = SERVER_URL + "MatchInfoService?";
	public static final String MATCH_DETAIL = SERVER_URL
			+ "MatchDetailService?";

	public static final String SDCARD_PATH = android.os.Environment
			.getExternalStorageDirectory().getPath();
	public static final String SCORELIVE_FOLDER = SDCARD_PATH + File.separator
			+ "ScoreLive/";
	public static final String ASSET_PATH = "file:///android_asset/www/";
	public static final String INDEX = ASSET_PATH + "index.html";

	public static final String SINA_APP_KEY = "3456776882";

	public static final String Tencent_APP_ID = "1101484810";

	public static final String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";

	public static final String SCOPE = "email,direct_messages_read,direct_messages_write,"
			+ "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
			+ "follow_app_official_microblog," + "invitation_write";

	public class MsgType {
		public static final int QUERY_MATCH_BY_GROUP_SUCCESS = 0X5;
		public static final int GET_SCORE_LIST_SUCCESS = 0X0;
		public static final int GET_MATCH_DETAIL_SUCCESS = 0X1;
		public static final int LOGIN_SUCCESS = 0X2;
		public static final int LOGIN_ERROR = 0X3;
	}

	public class BetType {
		public static final int ALL = 0;
		public static final int NORMAL = 1;
		public static final int BJ = 3;
		public static final int SMG = 2;
		public static final int ZC = 4;
		public static final int CUSTOMIZE = -1;
	}

	public class MatchStatus {
		public static final int UNSTART = 1;// 未开始
		public static final int DELAY = 2;// 延期
		public static final int CANCEL = 3;// 取消
		public static final int MATCHING = 4;// 比赛中
		public static final int MIDDLE = 5;// 中场休息
		public static final int ENDED = 6;// 结束
		public static final int UPADDED = 7;// 上半场补时
		public static final int DOWNADDED = 8;// 下半场补时
		public static final int PAUSEHURT = 9;// 受伤暂停
		public static final int PAUSEFOUL = 10;// 犯规暂停
	}
}