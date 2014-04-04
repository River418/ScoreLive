package com.scorelive.fragment;

import com.scorelive.R;
import com.scorelive.R.id;
import com.scorelive.R.layout;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ExpandableListView;
import android.widget.TextView;

public class ScoreFragment extends Fragment{

	private ExpandableListView mListView;
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View rootView = inflater.inflate(R.layout.score_fragment,
				container, false);
		mListView = (ExpandableListView)rootView.findViewById(R.id.match_listview);
		View v = inflater.inflate(R.layout.score_match_header,null);
		mListView.addHeaderView(v);
		mListView.setAdapter(new ScoreListViewAdapter(this.getActivity()));
		return rootView;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onViewCreated(view, savedInstanceState);
		
	}

	
	
}
