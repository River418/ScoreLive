package com.scorelive.common.config;

import java.io.File;

public class AppConstants {

	public static final String SERVER_URL = "http://203.195.200.238:8080/ScoreLive/";
	public static final String MATCH_INFO = SERVER_URL + "MatchInfoService?";
	public static final String MATCH_DETAIL = SERVER_URL
			+ "MatchEventDetailService?";
	public static final String LOGIN_URL = SERVER_URL + "SignInService?";

	public static final String SDCARD_PATH = android.os.Environment
			.getExternalStorageDirectory().getPath();
	public static final String SCORELIVE_FOLDER = SDCARD_PATH + File.separator
			+ "ScoreLive/";
	public static final String AVATOR_PATH = SCORELIVE_FOLDER + "avator.p";
	public static final String ASSET_PATH = "file:///android_asset/www/";
	public static final String INDEX = ASSET_PATH + "index.html";

	public static final String SINA_APP_KEY = "3456776882";

	public static final String Tencent_APP_ID = "1101484810";

	public static final String REDIRECT_URL = "https://api.weibo.com/oauth2/default.html";

	public static final String SCOPE = "email,direct_messages_read,direct_messages_write,"
			+ "friendships_groups_read,friendships_groups_write,statuses_to_me_read,"
			+ "follow_app_official_microblog," + "invitation_write";

	public class MsgType {
		public static final int GET_SCORE_LIST_SUCCESS = 0X0;
		public static final int GET_MATCH_DETAIL_SUCCESS = 0X1;
		public static final int LOGIN_SUCCESS = 0X2;
		public static final int LOGIN_ERROR = 0X3;
		public static final int QUERY_MATCH_BY_GROUP_SUCCESS = 0X4;
		public static final int ADD_MATCH_TO_GROUP = 0X5;
		public static final int DEL_MATCH_IN_GROUP = 0X6;
		public static final int ADD_GROUP = 0X7;
		public static final int DEL_GROUP = 0X8;
		public static final int CHANGE_MATCH_GROUP = 0X9;
		public static final int REFRESH_TIME = 0X10;
	}
	
	public class ActionType{
		public static final String UPDATE_MATCH_INFO = "com.scorelive.updatematchinfo";
	}

	public class BetType {
		public static final int ALL = 1;
		public static final int BJ = 2;
		public static final int SMG = 3;
		public static final int ZC = 4;
		public static final int CUSTOMIZE = 0;
	}

	public class MatchStatus {
		public static final int UNSTART = 0;// 未开始
		public static final int DELAY = -14;// 延期
		public static final int CANCEL = -10;// 取消
		public static final int MIDDLE = 2;// 中场休息
		public static final int ENDED = -1;// 结束
		public static final int UP = 1;// 上半场
		public static final int DOWN = 3;// 下半场
		public static final int ADDED = 4;// 加时

		public static final int UPADDED = 7;// 上半场补时
		public static final int DOWNADDED = 8;// 下半场补时
		public static final int PAUSEHURT = -13;// 受伤暂停
		public static final int PAUSEFOUL = -13;// 犯规暂停

		public static final int MATCHING = 10;
	}

	public class EventType {
		public static final int UP_START = 0;// 上半场比赛开始
		public static final int HOME_GOAL = 1;// 主队进球
		public static final int VISIT_GOAL = 2;// 客队进球
		public static final int HOME_POINT_GOAL = 3;// 主队点球
		public static final int VISIT_POINT_GOAL = 4;// 客队点球
		public static final int HOME_SUICIDE_GOAL = 5;// 主队乌龙球
		public static final int VISIT_SUICIDE_GOAL = 6;// 客队乌龙球
		public static final int HOME_RED = 7;// 主队红牌
		public static final int VISIT_RED = 8;// 客队红牌;
		public static final int HOME_YELLOW = 9;// 主队黄牌
		public static final int VISIT_YELLOW = 10;// 客队黄牌
		public static final int UP_OVER = 11;// 半场结束
		public static final int DOWN_START = 12;// 下半场开始
		public static final int ALL_OVER = 13;// 全场结束
		public static final int ADDED_START = 14;// 进入加时
		public static final int POINT_START = 15;// 进入点球大战
		public static final int MATCH_INTERUPT = 16;// 比赛中断
		public static final int MATCH_DELAY = 17;// 比赛延期
		public static final int MATCH_INTERUPTE= 18;//比赛腰斩
		public static final int MATCH_UNDECIDE= 19;//比赛待定
		public static final int MATCH_CANCEL= 20;//比赛取消
		public static final int MATCH_OTHER_SITUATION=21;//比赛其他变化
		public static final int HOME_YELLOW_TO_RED= 22;//主队两黄变一红
		public static final int VISIT_YELLOW_TO_RED=23;//客队两黄变一红
		public static final int HOME_CHANGE=24;//主队换人
		public static final int VISIT_CHANGE = 25;//客队换人
		public static final int ADDED_OVER = 41;//加时赛结束
		public static final int DOWN_OVER = 42;//下半场结束

	}
}
