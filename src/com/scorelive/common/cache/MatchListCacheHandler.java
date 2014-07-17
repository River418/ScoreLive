package com.scorelive.common.cache;

import java.util.ArrayList;
import java.util.HashMap;

import com.scorelive.common.config.AppConstants;
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

	
	public void setAllList(ArrayList<Match> list) {
		mAllList = list;
		if (mAllList != null) {
			handleMatchList();
		}
	}

	/**
	 * 清除比赛的缓存
	 */
	public void clearMatchCache() {
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
	 * 处理比赛列表
	 */
	private void handleMatchList() {
		clearMatchCache();
		for (Match match : mAllList) {
			String typeList = match.matchBet;
			String[] typeArray = null;
			if (typeList.contains(",")) {
				typeArray = typeList.split(",");
				for (int i = 0; i < typeArray.length; i++) {
					addMatchToBetList(Integer.valueOf(typeArray[i]), match);
				}
			} else if (!typeList.equalsIgnoreCase("")) {
				addMatchToBetList(Integer.valueOf(typeList), match);
			}
			addMatchToAllList(match);
		}
	}

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
	 * @param type
	 * @return
	 */
	public HashMap<Integer,ArrayList<Match>> getMatchList(int type){
		HashMap<Integer,ArrayList<Match>> mMatchMap = new HashMap<Integer,ArrayList<Match>>();
		switch(type){
		case AppConstants.BetType.ALL:
			mMatchMap.put(AppConstants.MatchStatus.UNSTART, mAllUnstartList);
			mMatchMap.put(AppConstants.MatchStatus.MATCHING, mAllMatchingList);
			mMatchMap.put(AppConstants.MatchStatus.ENDED,mAllEndedList);
			break;
		case AppConstants.BetType.BJ:
			mMatchMap.put(AppConstants.MatchStatus.UNSTART, mBJUnstartList);
			mMatchMap.put(AppConstants.MatchStatus.MATCHING, mBJMatchingList);
			mMatchMap.put(AppConstants.MatchStatus.ENDED,mBJEndedList);
			break;
		case AppConstants.BetType.SMG:
			mMatchMap.put(AppConstants.MatchStatus.UNSTART, mSMGUnstartList);
			mMatchMap.put(AppConstants.MatchStatus.MATCHING, mSMGMatchingList);
			mMatchMap.put(AppConstants.MatchStatus.ENDED,mSMGEndedList);
			break;
		case AppConstants.BetType.ZC:
			mMatchMap.put(AppConstants.MatchStatus.UNSTART, mZCUnstartList);
			mMatchMap.put(AppConstants.MatchStatus.MATCHING, mZCMatchingList);
			mMatchMap.put(AppConstants.MatchStatus.ENDED,mZCEndedList);
			break;
		}
		return mMatchMap;
	}
}
