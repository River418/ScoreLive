package com.scorelive.common.db;

import java.util.ArrayList;
import java.util.List;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

import com.scorelive.ScoreLiveApplication;
import com.scorelive.module.Group;
import com.scorelive.module.Match;

public class ScoreDBHandler {

	private Context mContext;
	private volatile static ScoreDBHandler mInstance;
	private SQLiteOpenHelper mDBHelper;
	public static final int SUCCESS = 0;
	public static final int FAIL = 1;
	public static final int ALREADY_HAVE = 2;

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
		mDBHelper = new ScoreLiveSQLiteHepler(mContext, DB_NAME, null,
				CURRENT_VERSION);
	}

	/**
	 * 根据分组id查找比赛
	 * 
	 * @param groupId
	 * @return
	 * @throws SQLiteException
	 */
	public synchronized ArrayList<Match> getMatchByGroupId(int groupId)
			throws SQLiteException {
		ArrayList<Match> matchList = new ArrayList<Match>();
		SQLiteDatabase db = mDBHelper.getReadableDatabase();
		Cursor cursor = db.query(SCORE_TABLE_NAME, null, MATCH_GROUP + "="
				+ groupId, null, null, null, MATCH_ID + " asc");
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			String match_id = cursor.getString(cursor.getColumnIndex(MATCH_ID));
			int group_id = cursor.getInt(cursor.getColumnIndex(MATCH_GROUP));
			String hostName = cursor.getString(cursor
					.getColumnIndex(HOST_TEAM_NAME));
			String visitName = cursor.getString(cursor
					.getColumnIndex(VISIT_TEAM_NAME));
			String hostIndex = cursor.getString(cursor
					.getColumnIndex(HOST_TEAM_INDEX));
			String visitIndex = cursor.getString(cursor
					.getColumnIndex(VISIT_TEAM_INDEX));
			String matchStartTime = cursor.getString(cursor
					.getColumnIndex(MATCH_STARTTIME));
			String match_bet = cursor.getString(cursor
					.getColumnIndex(MATCH_BET));
			String host_score = cursor.getString(cursor
					.getColumnIndex(HOST_TEAM_SCORE));
			String visit_score = cursor.getString(cursor
					.getColumnIndex(VISIT_TEAM_SCORE));
			String leagueColor = cursor.getString(cursor
					.getColumnIndex(LEAGUE_COLOR));
			int match_status = cursor
					.getInt(cursor.getColumnIndex(MATCH_STATE));
			String league_name = cursor.getString(cursor.getColumnIndex(LEAGUE_NAME));
			Match match = new Match();
			match.matchId = match_id;
			match.groupId = group_id;
			match.hostTeamName = hostName;
			match.hostTeamIndex = hostIndex;
			match.visitTeamName = visitName;
			match.visitTeamIndex = visitIndex;
			match.matchStartTime = matchStartTime;
			match.matchBet = match_bet;
			match.hostTeamScore = host_score;
			match.visitTeamScore = visit_score;
			match.matchState = match_status;
			match.leagueName = league_name;
			match.leagueColor = leagueColor;
			
			matchList.add(match);
			cursor.moveToNext();
		}
		db.close();
		return matchList;
	}

	/**
	 * 该分组中是否已经包含该比赛
	 * @param groupId
	 * @param matchId
	 * @return
	 * @throws SQLiteException
	 */
	public synchronized boolean isMatchInGroup(int groupId, String matchId)
			throws SQLiteException {
		SQLiteDatabase db = mDBHelper.getReadableDatabase();
		Cursor cursor = db.query(SCORE_TABLE_NAME, null, MATCH_GROUP + "="
				+ groupId + " and " + MATCH_ID + "=" + matchId, null, null,
				null, MATCH_ID + " asc");
		cursor.moveToFirst();
		if (cursor.isAfterLast()) {
			db.close();
			return false;
		} else {
			db.close();
			return true;
		}
	}

	/**
	 * 添加一场比赛到指定分组
	 * 
	 * @param groupId
	 * @param match
	 */
	public synchronized int addMatchToGroup(int groupId, Match match)
			throws SQLiteException {
		if (!isMatchInGroup(groupId, match.matchId)) {
			SQLiteDatabase db = mDBHelper.getReadableDatabase();
			ContentValues values = new ContentValues();
			values.put(MATCH_ID, match.matchId);
			values.put(MATCH_STARTTIME, match.matchStartTime);
			values.put(HOST_TEAM_NAME, match.hostTeamName);
			values.put(VISIT_TEAM_NAME, match.visitTeamName);
			values.put(LEAGUE_COLOR, match.leagueColor);
			values.put(MATCH_BET, match.matchBet);
			values.put(MATCH_STATE, match.matchState);
			values.put(HOST_TEAM_SCORE, match.hostTeamScore);
			values.put(HOST_TEAM_INDEX, match.hostTeamIndex);
			values.put(VISIT_TEAM_INDEX, match.visitTeamIndex);
			values.put(VISIT_TEAM_SCORE, match.visitTeamScore);
			values.put(MATCH_GROUP, groupId);
			values.put(HOST_TEAM_YELLOW, match.hostTeamYellow);
			values.put(HOST_TEAM_RED, match.hostTeamRed);
			values.put(VISIT_TEAM_YELLOW, match.visitTeamYellow);
			values.put(VISIT_TEAM_RED, match.visitTeamRed);
			values.put(LEAGUE_NAME, match.leagueName);
			values.put(LEAGUE_COLOR, match.leagueColor);
			db.insert(SCORE_TABLE_NAME, null, values);
			db.close();
			return SUCCESS;
		}else{
			return ALREADY_HAVE;
		}
	}
	
	/**
	 * 从一个分组中移除一场比赛
	 * @param match
	 * @return
	 * @throws SQLiteException
	 */
	public synchronized int delMatchInGroup(Match match) throws SQLiteException{
		SQLiteDatabase db = mDBHelper.getReadableDatabase();
		db.delete(SCORE_TABLE_NAME, MATCH_GROUP+"=? and "+MATCH_ID+"=?", new String[] { String.valueOf(match.groupId),String.valueOf(match.matchId) });
		return SUCCESS;
	}
	
	/**
	 * 将一场比赛从一个分组移动到其他分组
	 * @param match
	 * @param toGroupId
	 * @return
	 * @throws SQLiteException
	 */
	public synchronized int changeMatchGroup(Match match ,int toGroupId) throws SQLiteException{
		SQLiteDatabase db = mDBHelper.getReadableDatabase();
		ContentValues values = new ContentValues();
		values.put(MATCH_GROUP, toGroupId);
		db.update(SCORE_TABLE_NAME, values, MATCH_ID+"=?",
				new String[] { String.valueOf(match.matchId) });
		db.close();
		return SUCCESS;
	}

	/**
	 * 查询所有分组
	 * 
	 * @return
	 */
	public synchronized ArrayList<Group> getGroupList() throws SQLiteException {
		ArrayList<Group> list = new ArrayList<Group>();
		SQLiteDatabase db = mDBHelper.getReadableDatabase();
		Cursor cursor = db.query(GROUP_TABLE_NAME, null, null, null, null,
				null, GROUP_ID + " asc");
		cursor.moveToFirst();
		while (!cursor.isAfterLast()) {
			int id = cursor.getInt(cursor.getColumnIndex(GROUP_ID));
			int netId = cursor.getInt(cursor.getColumnIndex(GROUP_NETID));
			String name = cursor.getString(cursor.getColumnIndex(GROUP_NAME));
			Group group = new Group();
			group.id = id;
			group.netId = netId;
			group.grounName = name;
			list.add(group);
			cursor.moveToNext();
		}
		db.close();
		return list;
	}

	/**
	 * 添加一个分组
	 * 
	 * @param name
	 */
	public synchronized int addGroup(Group group) throws SQLiteException {
		SQLiteDatabase db = mDBHelper.getReadableDatabase();
		ContentValues values = new ContentValues();
		values.put(GROUP_NAME, group.grounName);
		values.put(GROUP_NETID, group.netId);
		db.insert(GROUP_TABLE_NAME, null, values);
		db.close();
		return SUCCESS;
	}

	/**
	 * 给一个分组重命名
	 * 
	 * @param name
	 * @param id
	 */
	public synchronized int renameGroup(String name, int id)
			throws SQLiteException {
		SQLiteDatabase db = mDBHelper.getReadableDatabase();
		ContentValues values = new ContentValues();
		values.put(GROUP_NAME, name);
		db.update(GROUP_TABLE_NAME, values, GROUP_ID+"=?",
				new String[] { String.valueOf(id) });
		db.close();
		return SUCCESS;
	}

	/**
	 * 删除一个分组
	 * 
	 * @param id
	 */
	public synchronized int deleteGroup(int id) throws SQLiteException {
		SQLiteDatabase db = mDBHelper.getReadableDatabase();
		db.delete(GROUP_TABLE_NAME, GROUP_ID+"=?", new String[] { String.valueOf(id) });
		return SUCCESS;
	}

	/*----------------------------------------------数据库的创建及升级代码-----------------------------------------*/

	private final static String DB_NAME = "SCORE_LIVE";
	private final static String SCORE_TABLE_NAME = "live_score";
	private final static String ID = "_id";
	private final static String MATCH_ID = "match_id";// 比赛id
	private final static String LEAGUE_NAME = "league_name";// 赛事名称
	private final static String MATCH_BET = "match_bet";// 所属彩票类型,包括竞彩、北单、足彩
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
	private final static String LEAGUE_COLOR = "league_color";//联赛颜色
	private final static String MATCH_GROUP = "groupid";
	private final static int CURRENT_VERSION = 1;

	private final static String GROUP_TABLE_NAME = "define_group";
	private final static String GROUP_ID = "_id";
	private final static String GROUP_NAME = "name";
	private final static String GROUP_NETID = "netid";

	public class ScoreLiveSQLiteHepler extends SQLiteOpenHelper {

		public ScoreLiveSQLiteHepler(Context context, String name,
				CursorFactory factory, int version) {
			super(context, name, factory, version);
		}

		@Override
		public void onCreate(SQLiteDatabase db) {
			db.execSQL("create table if not exists " + SCORE_TABLE_NAME + " ("
					+ ID + " integer primary key autoincrement," + MATCH_ID
					+ " long not null," + LEAGUE_NAME + " text," + MATCH_BET + " text," + MATCH_STATE
					+ " text," + MATCH_STARTTIME + " text," + MATCH_TIMEING
					+ " text," + HOST_TEAM_NAME
					+ " text," + HOST_TEAM_INDEX + " text," + HOST_TEAM_SCORE
					+ " integer," + HOST_TEAM_RED + " integer,"
					+ HOST_TEAM_YELLOW + " integer," + VISIT_TEAM_NAME
					+ " text," + VISIT_TEAM_INDEX + " text,"+ LEAGUE_COLOR+" text," + VISIT_TEAM_SCORE
					+ " integer," + VISIT_TEAM_RED + " integer," + MATCH_GROUP
					+ " integer," + VISIT_TEAM_YELLOW + " integer" + ");");
			db.execSQL("create table if not exists " + GROUP_TABLE_NAME + " ("
					+ GROUP_ID + " integer primary key autoincrement,"
					+ GROUP_NETID + " interger," + GROUP_NAME + " text" + ");");
		}

		@Override
		public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

		}

	}

}
