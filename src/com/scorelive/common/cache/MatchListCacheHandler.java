package com.scorelive.common.cache;

import java.util.ArrayList;

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
	}

}
