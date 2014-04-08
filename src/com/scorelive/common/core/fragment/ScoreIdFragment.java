package com.scorelive.common.core.fragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;

import com.scorelive.R;

public class ScoreIdFragment extends ScoreBaseFragment{
	private ExpandableListView mListView;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.score_fragment, container,
				false);
		mListView = (ExpandableListView) rootView
				.findViewById(R.id.match_listview);
		View v = inflater.inflate(R.layout.score_match_header_id, null);
		mListView.addHeaderView(v);
		mListView.setAdapter(new ScoreListIdAdapter(this.getActivity()));
		return rootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);

	}
}
