package com.scorelive;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.scorelive.common.core.fragment.ScoreBaseFragment;
import com.scorelive.common.core.fragment.ScoreNormalFragment;
import com.scorelive.common.utils.AppConstants;

/**
 * 即时比分的Adapter
 * 
 * @author River
 * 
 */
public class ScorePageAdapter extends FragmentPagerAdapter {

	private ScoreBaseFragment mAllFragment;// 所有
	private ScoreBaseFragment mCustomizeFragment;// 定制
	private ScoreBaseFragment mBJFragment;// 北单
	private ScoreBaseFragment mSMGFragment;// 竞彩
	private ScoreBaseFragment mZCFragment;// 足彩
	public static final int CUSTOMIZE = 0;
	public static final int ALL = 1;
	public static final int BJ = 2;
	public static final int SMG = 3;
	public static final int ZC = 4;

	public ScorePageAdapter(FragmentManager fm) {
		super(fm);
		// TODO Auto-generated constructor stub
	}

	public ScoreBaseFragment getFragment(int id) {
		switch (id) {
		case ALL:
			if (mAllFragment == null) {
				mAllFragment = new ScoreNormalFragment(AppConstants.BetType.ALL);
			}
			return mAllFragment;
		case BJ:
			if (mBJFragment == null) {
				mBJFragment = new ScoreNormalFragment(AppConstants.BetType.BJ);
			}
			return mBJFragment;
		case SMG:
			if (mSMGFragment == null) {
				mSMGFragment = new ScoreNormalFragment(AppConstants.BetType.SMG);
			}
			return mSMGFragment;
		case ZC:
			if (mZCFragment == null) {
				mZCFragment = new ScoreNormalFragment(AppConstants.BetType.ZC);
			}
			return mZCFragment;
		case CUSTOMIZE:
			if (mCustomizeFragment == null) {
				mCustomizeFragment = new ScoreNormalFragment(AppConstants.BetType.CUSTOMIZE);
			}
			return mCustomizeFragment;
		}
		return null;
	}

	@Override
	public Fragment getItem(int position) {
		// TODO Auto-generated method stub
		switch (position) {
		case ALL:
			if (mAllFragment == null) {
				mAllFragment = new ScoreNormalFragment(AppConstants.BetType.ALL);
			}
			return mAllFragment;
		case CUSTOMIZE:
			if (mCustomizeFragment == null) {
				mCustomizeFragment = new ScoreNormalFragment(AppConstants.BetType.CUSTOMIZE);
			}
			return mCustomizeFragment;
		case BJ:
			if (mBJFragment == null) {
				mBJFragment = new ScoreNormalFragment(AppConstants.BetType.BJ);
			}
			return mBJFragment;
		case SMG:
			if (mSMGFragment == null) {
				mSMGFragment = new ScoreNormalFragment(AppConstants.BetType.SMG);
			}
			return mSMGFragment;
		case ZC:
			if (mZCFragment == null) {
				mZCFragment = new ScoreNormalFragment(AppConstants.BetType.ZC);
			}
			return mZCFragment;
		}
		return new ScoreBaseFragment();
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return 5;
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
		case 4:
			return "足彩";
		}
		return super.getPageTitle(position);
	}
	
	public void refreshFragment(int position){
		((ScoreNormalFragment)getFragment(position)).refreshCustomFragment();
	}

}
