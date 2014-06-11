package com.scorelive;

import java.io.File;
import java.io.InputStream;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.scorelive.common.config.AppConstants;
import com.scorelive.common.config.Config;
import com.scorelive.common.itask.INetTaskListener;
import com.scorelive.common.itask.ITask;
import com.scorelive.common.itask.net.task.DownloadPicTask;
import com.scorelive.common.itask.pool.ThreadManager;
import com.scorelive.common.login.ScoreLoginHelper;
import com.scorelive.common.login.ScoreLoginHelper.LoginListener;
import com.scorelive.common.login.User;
import com.scorelive.common.utils.Utility;
import com.scorelive.ui.widget.ScoreToast;

public class ProfileFragmentActivity extends ScoreBaseActivity implements
		LoginListener, INetTaskListener {
	private static final String TAG = "WeiBo_Login";
	private Context mContext;
	/** 微博按钮 */
	private RelativeLayout mWeiboButton;
	/** QQ按钮 */
	private RelativeLayout mQQBtn;

	private Bitmap mAvator;
	private ImageView mAvatorIV;
	private TextView mNameTV, mTitleTV;
	private String mNickName;
	private LinearLayout mLoginLayout;
	private ScoreLoginHelper mLoginHelper;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_profile);
		mContext = this;
		initUI();
	}

	private void initUI() {
		mAvatorIV = (ImageView) findViewById(R.id.splash_logo_image);
		mAvatorIV.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				if (Config.getIsLogin(mContext)) {// 如果是登录状态中则点击退出登录
					Config.setIsLogin(mContext, false);
					Config.setNickName(mContext, null);
					File file = new File(AppConstants.AVATOR_PATH);
					file.deleteOnExit();
					mAvatorIV.setImageResource(R.drawable.login_logo);
					mNameTV.setText("");
					setLoginLayout();
				}
			}

		});
		mTitleTV = (TextView) findViewById(R.id.middle_title);
		mTitleTV.setText("用户信息");
		mNameTV = (TextView) findViewById(R.id.nickname);
		if (Config.getIsLogin(mContext)) {
			mAvator = Utility.toRoundBitmap(BitmapFactory
					.decodeFile(AppConstants.AVATOR_PATH));
			mAvatorIV.setImageBitmap(mAvator);
			mNameTV.setText(Config.getNickName(mContext));
		}

		setLoginLayout();

	}

	private void setLoginLayout() {
		mLoginLayout = (LinearLayout) findViewById(R.id.login_layout);
		if (Config.getIsLogin(mContext)) {
			mLoginLayout.setVisibility(View.GONE);
		} else {
			mLoginLayout.setVisibility(View.VISIBLE);
			mQQBtn = (RelativeLayout) findViewById(R.id.qq_login_btn);
			mQQBtn.setOnClickListener(new QQButtonOnClickListener());
			mWeiboButton = (RelativeLayout) findViewById(R.id.weibo_login_btn);
			mWeiboButton.setOnClickListener(new AuthOnClickListener());
		}
	}

	class AuthOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			showProgressDialog("正在登录");
			mLoginHelper = new ScoreLoginHelper(ProfileFragmentActivity.this,
					ScoreLoginHelper.LOGIN_TYPE_SINA);
			mLoginHelper.setLoginListener(ProfileFragmentActivity.this);
			mLoginHelper.login();
		}
	}

	class QQButtonOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			showProgressDialog("正在登录");
			mLoginHelper = new ScoreLoginHelper(ProfileFragmentActivity.this,
					ScoreLoginHelper.LOGIN_TYPE_QQ);
			mLoginHelper.setLoginListener(ProfileFragmentActivity.this);
			mLoginHelper.login();

		}
	}

	@Override
	protected void onActivityResult(int arg0, int arg1, Intent arg2) {
		// TODO Auto-generated method stub
		if (mLoginHelper != null) {
			mLoginHelper.onActivityResult(arg0, arg1, arg2);
		}
	}

	private final MyHandler mHandler = new MyHandler();

	private class MyHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			handlerMessage(msg);
		}

	}

	protected void handlerMessage(Message msg) {
		switch (msg.what) {
		case AppConstants.MsgType.LOGIN_SUCCESS:
			if (mAvatorIV != null && mAvator != null) {
				mAvatorIV.setImageBitmap(mAvator);
			}
			if (mNameTV != null && mNickName != null) {
				mNameTV.setText(mNickName);
			}

			Config.setIsLogin(mContext, true);
			setLoginLayout();
			dismissProgressDialog();
			break;
		case AppConstants.MsgType.LOGIN_ERROR:
			ScoreToast.makeText(mContext, "网络异常，请稍后重试", Toast.LENGTH_SHORT).show();
			dismissProgressDialog();
		}
	}

	@Override
	public void onLoginFinish(int type, String values) {
		String userPhoto = null;

		switch (type) {
		case ScoreLoginHelper.LOGIN_TYPE_QQ:
			JSONObject object = null;
			try {
				object = new JSONObject(values);
				mNickName = object.optString("nickname");
				Config.setNickName(mContext, mNickName);
				userPhoto = object.optString("figureurl_qq_2");
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case ScoreLoginHelper.LOGIN_TYPE_SINA:
			User user = User.parse(values);
			mNickName = user.name;
			Config.setNickName(mContext, mNickName);
			userPhoto = user.avatar_large;
			break;
		}
		if (userPhoto != null) {
			DownloadPicTask task = new DownloadPicTask(ITask.TYPE_DOWNLOAD_PIC,
					ThreadManager.getInstance().getNewTaskId(), userPhoto);
			task.setListener(ProfileFragmentActivity.this);
			ThreadManager.getInstance().addTask(task);
		}
	}

	@Override
	public void onLoginError(int type, String values) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onUpdateUserInfo(int type, String values) {
		// TODO Auto-generated method stub

	}

	@Override
	public void onTaskError(ITask task, Exception exception) {
		mHandler.sendEmptyMessage(AppConstants.MsgType.LOGIN_ERROR);

	}

	@Override
	public void onTaskFinish(ITask task, InputStream is) {
		// TODO Auto-generated method stub
		Utility.saveFile(is, AppConstants.AVATOR_PATH);
		Bitmap temp = BitmapFactory.decodeFile(AppConstants.AVATOR_PATH);
		mAvator = Utility.toRoundBitmap(temp);
		temp.recycle();
		temp = null;
		mHandler.sendEmptyMessage(AppConstants.MsgType.LOGIN_SUCCESS);
	}

}
