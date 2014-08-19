package com.scorelive.ui.widget.dialog;

import java.util.ArrayList;

import android.app.Activity;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.GridView;
import android.widget.TextView;

import com.scorelive.R;
import com.scorelive.common.cache.MatchListCacheHandler;
import com.scorelive.module.League;

public class LeagueFilterDialog extends BaseDialog {

	private GridView mGridView;
	private TextView mConfirmBtn, mCancelBtn;
	private OnLeagueSelectedListener mListener;

	public LeagueFilterDialog(Activity act) {
		initDialog(act, R.layout.league_filter, TYPE_CENTER, false);
		mGridView = (GridView) mDialog.findViewById(R.id.leagueName);
		mGridView.setAdapter(new LeagueAdapter());
		mConfirmBtn = (TextView) mDialog.findViewById(R.id.confirm);
		mCancelBtn = (TextView) mDialog.findViewById(R.id.cancel);
		mCancelBtn.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				mDialog.dismiss();
			}

		});
		mConfirmBtn.setOnClickListener(new OnClickListener(){

			@Override
			public void onClick(View v) {
				mListener.onLeagueSelected(MatchListCacheHandler.getInstance().getLeagueList());
				mDialog.dismiss();
			}
			
		});

	}
	
	public void setOnLeagueSelectedListener(OnLeagueSelectedListener listener){
		mListener = listener;
	}

	class LeagueAdapter extends BaseAdapter {

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return MatchListCacheHandler.getInstance().getLeagueList().size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return MatchListCacheHandler.getInstance().getLeagueList()
					.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			// TODO Auto-generated method stub
			if (convertView == null) {
				convertView = new CheckBox(act);
			}
			final int index = position;
			League league = MatchListCacheHandler.getInstance().getLeagueList()
					.get(index);
			CheckBox box = (CheckBox) convertView;
			box.setText(league.name);
			box.setChecked(league.isSelected);
			box.setOnCheckedChangeListener(new OnCheckedChangeListener() {

				@Override
				public void onCheckedChanged(CompoundButton buttonView,
						boolean isChecked) {
					League league = (League) getItem(index);
					if (buttonView.getText().toString()
							.equalsIgnoreCase(league.name)) {
						MatchListCacheHandler.getInstance().getLeagueList()
								.get(index).isSelected = isChecked;
					}
				}

			});
			return box;
		}

	}

	public interface OnLeagueSelectedListener {
		public void onLeagueSelected(ArrayList<League> list);
	}

}
