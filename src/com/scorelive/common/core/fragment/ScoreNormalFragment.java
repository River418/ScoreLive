package com.scorelive.common.core.fragment;

import java.util.ArrayList;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnLongClickListener;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.ExpandableListView.OnChildClickListener;

import com.scorelive.R;
import com.scorelive.ScoreDetailActivity;
import com.scorelive.module.AppConstants;
import com.scorelive.module.Match;

public class ScoreNormalFragment extends ScoreBaseFragment {
	private ExpandableListView mListView;

	private int mFragmentType;
	private ScoreListBaseAdapter mAdapter;
	private Context mContext;

	public ScoreNormalFragment(int type) {
		mFragmentType = type;
	}

	
	
	@Override
	public void setData(ArrayList<Match> unstart, ArrayList<Match> matching,
			ArrayList<Match> ended) {
		// TODO Auto-generated method stub
		super.setData(unstart, matching, ended);
		if (mAdapter != null) {
			mAdapter.setData(unstart, matching, ended);
		}
	}



	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		mContext = getActivity();
		View rootView = inflater.inflate(R.layout.score_fragment, container,
				false);
		mListView = (ExpandableListView) rootView
				.findViewById(R.id.match_listview);
		View v = null;
		switch (mFragmentType) {
		case AppConstants.BetType.ALL:
		case AppConstants.BetType.CUSTOMIZE:
			v = inflater.inflate(R.layout.score_match_header, null);
			break;
		case AppConstants.BetType.BJ:
		case AppConstants.BetType.SMG:
		case AppConstants.BetType.ZC:
			v = inflater.inflate(R.layout.score_match_header_id, null);
			break;
		}

		mListView.addHeaderView(v);
		mAdapter = new ScoreListBaseAdapter(this.getActivity(), mFragmentType);
		mListView.setAdapter(mAdapter);
		mListView.setOnChildClickListener(new OnChildClickListener() {

			@Override
			public boolean onChildClick(ExpandableListView parent, View v,
					int groupPosition, int childPosition, long id) {
				// TODO Auto-generated method stub
				Match match = (Match)mAdapter.getChild(groupPosition, childPosition);
				Intent intent= new Intent();
				intent.setClass(mContext, ScoreDetailActivity.class);
				intent.putExtra("match", match);
				mContext.startActivity(intent);
				return true;
			}

		});
		mListView.setOnLongClickListener(new OnLongClickListener() {
			
			@Override
			public boolean onLongClick(View v) {
				// TODO Auto-generated method stub
				return false;
			}
		});
		return rootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		mAdapter.setData(mUnstartList, mMatchingList, mEndedList);
	}
}
