package com.scorelive;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;

import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
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
import com.scorelive.common.utils.Utility;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.WeiboAuth.AuthInfo;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.utils.LogUtil;

public class ProfileFragmentActivity extends ScoreBaseActivity implements
		LoginListener, INetTaskListener {
	private static final String TAG = "WeiBo_Login";
	/** 授权认证所需要的信息 */
	private AuthInfo mAuthInfo;
	/** 微博授权认证回调 */
	private WeiboAuthListener mAuthListener = new AuthListener();
	/** SSO 授权认证实例 */
	private SsoHandler mSsoHandler;
	/** 微博授权时，启动 SSO 界面的 Activity */
	private Context mContext;
	/** 微博按钮 */
	private RelativeLayout weiboButton;
	/** QQ按钮 */
	private RelativeLayout mQQBtn;

	private Bitmap mAvator;
	private ImageView mAvatorIV;
	private TextView mNameTV, mTitleTV;
	private String mNickName;
	private LinearLayout mLoginLayout;

	@Override
	protected void onCreate(Bundle savedInstanceState) {

		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_profile);
		mContext = this;
		initUI();
		initWeiboOauth();
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
		mNameTV = (TextView)findViewById(R.id.nickname);
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
			OnClickListener listener = new QQButtonOnClickListener();
			mQQBtn.setOnClickListener(listener);
		}
	}

	private void setUserInfoLayout() {

	}

	private class AuthOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			if (null == mSsoHandler && mAuthInfo != null) {
				WeiboAuth weiboAuth = new WeiboAuth(mContext, mAuthInfo);
				mSsoHandler = new SsoHandler((Activity) mContext, weiboAuth);
				Toast.makeText(getApplicationContext(), "今日",
						Toast.LENGTH_SHORT).show();
			}
			if (mSsoHandler != null) {
				mSsoHandler.authorize(mAuthListener);
				Toast.makeText(getApplicationContext(), "今2日",
						Toast.LENGTH_SHORT).show();
			} else {
				LogUtil.e(TAG, "Please setWeiboAuthInfo(...) for first");
			}
		}
	}

	/**
	 * 登入按钮的监听器，接收授权结果。
	 */
	private class AuthListener implements WeiboAuthListener {
		@Override
		public void onComplete(Bundle values) {
			Oauth2AccessToken accessToken = Oauth2AccessToken
					.parseAccessToken(values);
			if (accessToken != null && accessToken.isSessionValid()) {
				String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss")
						.format(new java.util.Date(accessToken.getExpiresTime()));
				Toast.makeText(getApplicationContext(),
						"获得的accesstoken--->" + accessToken, Toast.LENGTH_SHORT)
						.show();
				// AccessTokenKeeper.writeAccessToken(getApplicationContext(),
				// accessToken);
			}
		}

		@Override
		public void onWeiboException(WeiboException e) {
			Toast.makeText(ProfileFragmentActivity.this, e.getMessage(),
					Toast.LENGTH_SHORT).show();
		}

		@Override
		public void onCancel() {
			Toast.makeText(ProfileFragmentActivity.this,
					R.string.weibo_auth_cancel, Toast.LENGTH_SHORT).show();
		}
	}

	public void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		if (mSsoHandler != null) {
			mSsoHandler.authorizeCallBack(requestCode, resultCode, data);
		}
	}

	public AuthInfo getmAuthInfo() {
		return mAuthInfo;
	}

	public void setmAuthInfo(AuthInfo mAuthInfo) {
		this.mAuthInfo = mAuthInfo;
	}

	public WeiboAuthListener getmAuthListener() {
		return mAuthListener;
	}

	public void setmAuthListener(WeiboAuthListener mAuthListener) {
		this.mAuthListener = mAuthListener;
	}

	private void initWeiboOauth() {
		mAuthInfo = new AuthInfo(this, AppConstants.SINA_APP_KEY,
				AppConstants.REDIRECT_URL, AppConstants.SCOPE);
		weiboButton = (RelativeLayout) findViewById(R.id.weibo_login_btn);
		weiboButton.setOnClickListener(new AuthOnClickListener());
	}

	class QQButtonOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
			showProgressDialog("正在登录");
			ScoreLoginHelper loginHelper = new ScoreLoginHelper(
					ProfileFragmentActivity.this,
					ScoreLoginHelper.LOGIN_TYPE_QQ);
			loginHelper.setLoginListener(ProfileFragmentActivity.this);
			loginHelper.login();

		}
	}

	private final MyHandler mHandler = new MyHandler();

	private class MyHandler extends Handler {
		@Override
		public void handleMessage(Message msg) {
			// TODO Auto-generated method stub
			// handleMessage(msg);
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
		}
	}

	// private void onClickLogin() {
	// if (!mQQAuth.isSessionValid()) {
	// IUiListener listener = new BaseUiListener() {
	// @Override
	// protected void doComplete(JSONObject values) {
	// Log.e("login qq", values.toString());
	// }
	// };
	// // mQQAuth.login(this, "all", listener);
	// // mTencent.loginWithOEM(this, "all",
	// // listener,"10000144","10000144","xxxx");
	// mTencent.login(this, "all", listener);
	// } else {
	// mQQAuth.logout(this);
	// }
	// }
	//
	// private class BaseUiListener implements IUiListener {
	//
	// @Override
	// public void onComplete(Object response) {
	// // com.scorelive.common.utils.Util.showResultDialog(mContext,
	// // response.toString(), "登录成功");
	// // 这里做一些授权成功后的回调工作
	// Toast.makeText(getApplicationContext(), "授权成功！", Toast.LENGTH_SHORT)
	// .show();
	// doComplete((JSONObject) response);
	// }
	//
	// protected void doComplete(JSONObject values) {
	//
	// }
	//
	// @Override
	// public void onError(UiError e) {
	// Utility.toastMessage(ProfileFragmentActivity.this, "onError: "
	// + e.errorDetail);
	// }
	//
	// @Override
	// public void onCancel() {
	// Utility.toastMessage(ProfileFragmentActivity.this, "onCancel: ");
	// }
	// }

	@Override
	public void onLoginFinish(int type, String values) {
		switch (type) {
		case ScoreLoginHelper.LOGIN_TYPE_QQ:
			JSONObject object = null;
			try {
				object = new JSONObject(values);
				mNickName = object.optString("nickname");
				Config.setNickName(mContext, mNickName);
				String userPhoto = object.optString("figureurl_qq_2");
				DownloadPicTask task = new DownloadPicTask(
						ITask.TYPE_DOWNLOAD_PIC, ThreadManager.getInstance()
								.getNewTaskId(), userPhoto);
				task.setListener(ProfileFragmentActivity.this);
				ThreadManager.getInstance().addTask(task);
			} catch (JSONException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			break;
		case ScoreLoginHelper.LOGIN_TYPE_SINA:
			break;
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
		// TODO Auto-generated method stub

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
