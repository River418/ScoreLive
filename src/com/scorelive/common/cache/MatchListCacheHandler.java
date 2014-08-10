package com.scorelive.common.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;

import android.widget.Toast;

import com.scorelive.ScoreLiveApplication;
import com.scorelive.common.config.AppConstants;
import com.scorelive.module.League;
import com.scorelive.module.Match;

public class MatchListCacheHandler {

	private static volatile MatchListCacheHandler mInstance;
	private ArrayList<Match> mAllList = new ArrayList<Match>(),
			mBJList = new ArrayList<Match>(),
			mSMGList = new ArrayList<Match>(),
			mZCList = new ArrayList<Match>();
	private ArrayList<Match> mAllUnstartList = new ArrayList<Match>(),
			mAllMatchingList = new ArrayList<Match>(),
			mAllEndedList = new ArrayList<Match>();
	private ArrayList<Match> mBJUnstartList = new ArrayList<Match>(),
			mBJMatchingList = new ArrayList<Match>(),
			mBJEndedList = new ArrayList<Match>();
	private ArrayList<Match> mSMGUnstartList = new ArrayList<Match>(),
			mSMGMatchingList = new ArrayList<Match>(),
			mSMGEndedList = new ArrayList<Match>();
	private ArrayList<Match> mZCUnstartList = new ArrayList<Match>(),
			mZCMatchingList = new ArrayList<Match>(),
			mZCEndedList = new ArrayList<Match>();
	private HashMap<String,ArrayList<Match>> mMatchLeagueMap = new HashMap<String,ArrayList<Match>>();
	private HashMap<String,League> mLeagueMap = new HashMap<String,League>();
	private ArrayList<League> mLeagueList = new ArrayList<League>();

	public static MatchListCacheHandler getInstance() {
		if (mInstance == null) {
			synchronized (MatchListCacheHandler.class) {
				if (mInstance == null) {
					mInstance = new MatchListCacheHandler();
				}
			}
		}
		return mInstance;
	}

	/**
	 * cache一份全部比赛
	 * 
	 * @param list
	 */
	public synchronized void setAllList(ArrayList<Match> list) {
		mAllList = list;
		if (mAllList != null) {
			handleMatchList();
		}
	}

	/**
	 * 清除比赛的缓存
	 */
	public synchronized void clearMatchCache() {
		mAllMatchingList.clear();
		mBJList.clear();
		mSMGList.clear();
		mZCList.clear();
		mAllUnstartList.clear();
		mAllMatchingList.clear();
		mAllEndedList.clear();
		mBJUnstartList.clear();
		mBJMatchingList.clear();
		mBJEndedList.clear();
		mSMGUnstartList.clear();
		mSMGMatchingList.clear();
		mSMGEndedList.clear();
		mZCUnstartList.clear();
		mZCMatchingList.clear();
		mZCEndedList.clear();

	}

	/**
	 * 初始化时处理比赛列表
	 */
	private void handleMatchList() {
		clearMatchCache();
		for (Match match : mAllList) {
			String typeList = match.matchBet;
			String[] typeArray = null;
			if (typeList.contains(",")) {// 有竞彩类型，则分类不同的竞彩类型和状态
				typeArray = typeList.split(",");
				for (int i = 0; i < typeArray.length; i++) {
					addMatchToBetList(Integer.valueOf(typeArray[i]), match);
				}
			} else if (!typeList.equalsIgnoreCase("")) {// 不属于任何竞彩类型，则只按状态分类
				addMatchToBetList(Integer.valueOf(typeList), match);
			}
			addMatchToAllList(match);
			handleMatchLeague(match);
		}
		changeLeagueMapToList();
	}

	/**
	 * 将全部比赛添加到不同的比赛状态中
	 * 
	 * @param match
	 */
	private void addMatchToAllList(Match match) {
		switch (match.matchState) {
		case AppConstants.MatchStatus.UNSTART:
			mAllUnstartList.add(match);
			break;
		case AppConstants.MatchStatus.MIDDLE:
		case AppConstants.MatchStatus.UP:
		case AppConstants.MatchStatus.DOWN:
		case AppConstants.MatchStatus.ADDED:
			mAllMatchingList.add(match);
			break;
		case AppConstants.MatchStatus.CANCEL:
		case AppConstants.MatchStatus.ENDED:
		case AppConstants.MatchStatus.DELAY:
			mAllEndedList.add(match);
			break;
		}
	}
	
	/**
	 * 添加比赛和联赛的映射关系
	 * @param match
	 */
	private void handleMatchLeague(Match match){
		int leagueId = match.leagueId;
		String leagueName = match.leagueName;
		String leagueColor = match.leagueColor;
		League league = new League();
		league.name = leagueName;
		league.color = leagueColor;
		league.id = leagueId;
		//比赛从服务器拉取，不再本地列表处理。注释掉以下
//		ArrayList<Match> list = mMatchLeagueMap.get(leagueId);
//		if(list == null){
//			list = new ArrayList<Match>();
//			mMatchLeagueMap.put(leagueName, list);
//		}
//		list.add(match);
		//注释掉以上
		mLeagueMap.put(leagueName, league);
	}
	
	private void changeLeagueMapToList(){
		mLeagueList.clear();
		Iterator<Entry<String, League>> it = mLeagueMap.entrySet().iterator();
		while(it.hasNext()){
			Map.Entry<String,League> entry = (Map.Entry<String,League>)it.next();
			mLeagueList.add(entry.getValue());
		}
	}

	/**
	 * 将全部比赛添加到不同的竞彩类型中
	 * 
	 * @param betType
	 * @param match
	 */
	private void addMatchToBetList(int betType, Match match) {
		switch (betType) {
		case AppConstants.BetType.BJ:
			switch (match.matchState) {
			case AppConstants.MatchStatus.UNSTART:
				mBJUnstartList.add(match);
				break;
			case AppConstants.MatchStatus.MIDDLE:
			case AppConstants.MatchStatus.UP:
			case AppConstants.MatchStatus.DOWN:
			case AppConstants.MatchStatus.ADDED:
				mBJMatchingList.add(match);
				break;
			case AppConstants.MatchStatus.CANCEL:
			case AppConstants.MatchStatus.ENDED:
			case AppConstants.MatchStatus.DELAY:
				mBJEndedList.add(match);
				break;
			}
			mBJList.add(match);
			break;
		case AppConstants.BetType.SMG:
			switch (match.matchState) {
			case AppConstants.MatchStatus.UNSTART:
				mSMGUnstartList.add(match);
				break;
			case AppConstants.MatchStatus.MIDDLE:
			case AppConstants.MatchStatus.UP:
			case AppConstants.MatchStatus.DOWN:
			case AppConstants.MatchStatus.ADDED:
				mSMGMatchingList.add(match);
				break;
			case AppConstants.MatchStatus.CANCEL:
			case AppConstants.MatchStatus.ENDED:
			case AppConstants.MatchStatus.DELAY:
				mSMGEndedList.add(match);
				break;
			}
			mSMGList.add(match);
			break;
		case AppConstants.BetType.ZC:
			switch (match.matchState) {
			case AppConstants.MatchStatus.UNSTART:
				mZCUnstartList.add(match);
				break;
			case AppConstants.MatchStatus.MIDDLE:
			case AppConstants.MatchStatus.UP:
			case AppConstants.MatchStatus.DOWN:
			case AppConstants.MatchStatus.ADDED:
				mZCMatchingList.add(match);
				break;
			case AppConstants.MatchStatus.CANCEL:
			case AppConstants.MatchStatus.ENDED:
			case AppConstants.MatchStatus.DELAY:
				mZCEndedList.add(match);
				break;
			}
			mZCList.add(match);
			break;
		}

	}

	/**
	 * 根据不同类型，取3组比赛数据
	 * 
	 * @param type
	 * @return
	 */
	public synchronized HashMap<Integer, ArrayList<Match>> getMatchList(int type) {
		HashMap<Integer, ArrayList<Match>> mMatchMap = new HashMap<Integer, ArrayList<Match>>();
		switch (type) {
		case AppConstants.BetType.ALL:
			mMatchMap.put(AppConstants.MatchStatus.UNSTART, mAllUnstartList);
			mMatchMap.put(AppConstants.MatchStatus.MATCHING, mAllMatchingList);
			mMatchMap.put(AppConstants.MatchStatus.ENDED, mAllEndedList);
			break;
		case AppConstants.BetType.BJ:
			mMatchMap.put(AppConstants.MatchStatus.UNSTART, mBJUnstartList);
			mMatchMap.put(AppConstants.MatchStatus.MATCHING, mBJMatchingList);
			mMatchMap.put(AppConstants.MatchStatus.ENDED, mBJEndedList);
			break;
		case AppConstants.BetType.SMG:
			mMatchMap.put(AppConstants.MatchStatus.UNSTART, mSMGUnstartList);
			mMatchMap.put(AppConstants.MatchStatus.MATCHING, mSMGMatchingList);
			mMatchMap.put(AppConstants.MatchStatus.ENDED, mSMGEndedList);
			break;
		case AppConstants.BetType.ZC:
			mMatchMap.put(AppConstants.MatchStatus.UNSTART, mZCUnstartList);
			mMatchMap.put(AppConstants.MatchStatus.MATCHING, mZCMatchingList);
			mMatchMap.put(AppConstants.MatchStatus.ENDED, mZCEndedList);
			break;
		}
		return mMatchMap;
	}

	/**
	 * 根据推送的消息，维护比赛状态
	 * 
	 * @param list
	 * @return
	 */
	public synchronized boolean updateMatchStatus(ArrayList<Match> list) {
		for (Match pushInfo : list) {
			int eventType = pushInfo.matchState;
			switch (eventType) {
			case AppConstants.EventType.UP_START:// 上半场开始，将比赛从未开始队列移动到进行中队列，并维护
				for (Match match : mAllUnstartList) {
					if (match.matchId.equalsIgnoreCase(pushInfo.matchId)) {
						match.matchState = AppConstants.MatchStatus.UP;
						match.matchStartTime = pushInfo.matchStartTime;
						String typeList = match.matchBet;
						String[] typeArray = null;
						if (typeList.contains(",")) {
							typeArray = typeList.split(",");
							for (int i = 0; i < typeArray.length; i++) {
								addMatchToBetList(
										Integer.valueOf(typeArray[i]), match);
							}
						} else if (!typeList.equalsIgnoreCase("")) {
							addMatchToBetList(Integer.valueOf(typeList), match);
						}
						removeFromUnstartList(match);
						addMatchToAllList(match);
						break;
					}
				}
				break;
			case AppConstants.EventType.UP_OVER:
				for (Match match : mAllMatchingList) {
					if (match.matchId.equalsIgnoreCase(pushInfo.matchId)) {
						match.matchState = AppConstants.MatchStatus.MIDDLE;
					}

				}
				break;
			case AppConstants.EventType.DOWN_START:
				for (Match match : mAllMatchingList) {
					if (match.matchId.equalsIgnoreCase(pushInfo.matchId)) {
						match.matchState = AppConstants.MatchStatus.DOWN;
						match.matchStartTime = pushInfo.matchStartTime;
					}

				}
				break;
			case AppConstants.EventType.ALL_OVER:
				for (Match match : mAllMatchingList) {
					if (match.matchId.equalsIgnoreCase(pushInfo.matchId)) {
						match.matchState = AppConstants.MatchStatus.ENDED;
						match.matchTime = "比赛结束";
						Toast.makeText(
								ScoreLiveApplication.getInstance()
										.getApplicationContext(),
								match.hostTeamName + ":比赛结束了",
								Toast.LENGTH_SHORT).show();
						match.matchState = AppConstants.MatchStatus.ENDED;
						String typeList = match.matchBet;
						String[] typeArray = null;
						if (typeList.contains(",")) {
							typeArray = typeList.split(",");
							for (int i = 0; i < typeArray.length; i++) {
								addMatchToBetList(
										Integer.valueOf(typeArray[i]), match);
							}
						} else if (!typeList.equalsIgnoreCase("")) {
							addMatchToBetList(Integer.valueOf(typeList), match);
						}
						removeFromMatchingList(match);
						addMatchToAllList(match);
						break;
					}

				}
				break;
			default:
				for (Match match : mAllMatchingList) {
					// removeFromMatchingList(match);
					if (match.matchId.equalsIgnoreCase(pushInfo.matchId)) {
						// match.matchState =
						// AppConstants.MatchStatus.ENDED;
						match.hostTeamScore = pushInfo.hostTeamScore;
						match.visitTeamScore = pushInfo.visitTeamScore;
						match.hostTeamYellow = pushInfo.hostTeamYellow;
						match.visitTeamYellow = pushInfo.visitTeamYellow;
						match.hostTeamRed = pushInfo.hostTeamRed;
						match.visitTeamRed = pushInfo.visitTeamRed;
						// String typeList = match.matchBet;
						// String[] typeArray = null;
						// if (typeList.contains(",")) {
						// typeArray = typeList.split(",");
						// for (int i = 0; i < typeArray.length; i++) {
						// addMatchToBetList(
						// Integer.valueOf(typeArray[i]),
						// match);
						// }
						// } else if (!typeList.equalsIgnoreCase("")) {
						// addMatchToBetList(Integer.valueOf(typeList),
						// match);
						// }
						// addMatchToAllList(match);
						// mAllMatchingList.remove(match);
						// break;
					}

				}
				break;
			}

		}
		return true;
	}

	private void removeFromUnstartList(Match match) {
		mBJUnstartList.remove(match);
		mZCUnstartList.remove(match);
		mSMGUnstartList.remove(match);
		mAllUnstartList.remove(match);
	}

	private void removeFromMatchingList(Match match) {
		mBJMatchingList.remove(match);
		mZCMatchingList.remove(match);
		mSMGMatchingList.remove(match);
		mAllMatchingList.remove(match);
	}
	
	public ArrayList<League> getLeagueList(){
		return mLeagueList;
	}
}
