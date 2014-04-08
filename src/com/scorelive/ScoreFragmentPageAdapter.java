package com.scorelive;

import com.scorelive.common.core.fragment.ScoreBaseFragment;
import com.scorelive.common.core.fragment.ScoreIdFragment;
import com.scorelive.common.core.fragment.ScoreNoIdFragment;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * 即时比分的Adapter
 * 
 * @author River
 * 
 */
public class ScoreFragmentPageAdapter extends FragmentPagerAdapter {

	private ScoreBaseFragment mAllFragment;// 所有
	private ScoreBaseFragment mCustomizeFragment;// 定制
	private ScoreBaseFragment mBJFragment;// 北单
	private ScoreBaseFragment mSMGFragment;// 竞彩
	private static final int ALL = 0;
	private static final int CUSTOMIZE = 1;
	private static final int BJ = 2;
	private static final int SMG = 3;

	public ScoreFragmentPageAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		switch(position){
		case ALL:
			if(mAllFragment == null){
				mAllFragment = new ScoreNoIdFragment();
			}
			return mAllFragment;
		case CUSTOMIZE:
			if(mCustomizeFragment == null){
				mCustomizeFragment = new ScoreNoIdFragment();
			}
			return mCustomizeFragment;
		case BJ:
			if(mBJFragment == null){
				mBJFragment = new ScoreIdFragment();
			}
			return mBJFragment;
		case SMG:
			if(mSMGFragment == null){
				mSMGFragment = new ScoreIdFragment();
			}
			return mSMGFragment;
		
		}
		return new ScoreBaseFragment();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 4;
	}

	@Override
	public CharSequence getPageTitle(int position) {
		// TODO Auto-generated method stub
		switch (position) {
		case 0:
			return "定制";
		case 1:
			return "全部";
		case 2:
			return "北单";
		case 3:
			return "竞彩";
		}
		return super.getPageTitle(position);
	}

}
