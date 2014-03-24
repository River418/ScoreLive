package com.scorelive.common.db;

import com.scorelive.ScoreLiveApplication;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

public class ScoreDBHandler {

	private Context mContext;
	private volatile static ScoreDBHandler mInstance;
	private SQLiteOpenHelper mDBHelper;

	public static ScoreDBHandler getInstance() {
		if (mInstance == null) {
			synchronized (ScoreDBHandler.class) {
				if (mInstance == null) {
					mInstance = new ScoreDBHandler();
				}
			}
		}
		return mInstance;
	}

	private ScoreDBHandler() {
		mContext = ScoreLiveApplication.getInstance().getApplicationContext();
		mDBHelper = new ScoreLiveSQLiteHepler(mContext,DB_NAME,null,CURRENT_VERSION);
	}

	/*----------------------------------------------数据库的创建及升级代码-----------------------------------------*/

	private final static String DB_NAME = "SCORE_LIVE";
	private final static String SCORE_TABLE_NAME = "live_score";
	private final static String ID = "_id";
	private final static String MATCH_ID = "match_id";// 比赛id
	private final static String MATCH_NAME = "match_name";// 所属赛事
	private final static String MATCH_BET = "match_bet";// 所属彩票类型,包括竞彩、北单、足彩
	private final static String MATCH_BET_ID = "match_bet_id";// 在所属彩票类型中的id
	private final static String MATCH_STATE = "match_state";// 比赛开始、结束及中场休息的状态
	private final static String MATCH_STARTTIME = "match_starttime";// 比赛开始时间
	private final static String MATCH_TIMEING = "match_timeing";// 比赛进行了多久
	private final static String HOST_TEAM_NAME = "host_teamname";// 主队名称
	private final static String HOST_TEAM_INDEX = "host_team_index";// 主队排名
	private final static String HOST_TEAM_SCORE = "host_team_score";// 主队积分
	private final static String HOST_TEAM_RED = "host_team_red";// 主队红牌数
	private final static String HOST_TEAM_YELLOW = "host_team_yellow";// 主队黄牌数
	private final static String VISIT_TEAM_NAME = "visit_teamname";// 客队名称
	private final static String VISIT_TEAM_INDEX = "visit_team_index";// 客队排名
	private final static String VISIT_TEAM_SCORE = "visit_team_score";// 客队积分
	private final static String VISIT_TEAM_RED = "visit_team_red";// 客队红牌数
	private final static String VISIT_TEAM_YELLOW = "visit_team_yellow";// 客队黄牌数
	private final static int CURRENT_VERSION = 1;

	public class ScoreLiveSQLiteHepler extends SQLiteOpenHelper {

		public ScoreLiveSQLiteHepler(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("create table if not exists " + SCORE_TABLE_NAME + " ("
					+ ID + " integer primary key autoincrement," + MATCH_ID
					+ " long not null," + MATCH_NAME + " text," + MATCH_BET
					+ " text," + MATCH_BET_ID + " long," + MATCH_STATE
					+ " text," + MATCH_STARTTIME + " text," + MATCH_TIMEING
					+ " text," + HOST_TEAM_NAME + " text," + HOST_TEAM_INDEX
					+ " long," + HOST_TEAM_SCORE + " integer," + HOST_TEAM_RED
					+ " integer," + HOST_TEAM_YELLOW + " integer,"
					+ VISIT_TEAM_NAME + " text," + VISIT_TEAM_INDEX + " long,"
					+ VISIT_TEAM_SCORE + " integer," + VISIT_TEAM_RED
					+ " integer," + VISIT_TEAM_YELLOW + " integer" + ");");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		}

	}

}
