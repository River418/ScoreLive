package com.scorelive.common.login;

import org.json.JSONObject;

import android.app.Activity;

import com.scorelive.common.config.AppConstants;
import com.scorelive.common.config.Config;
import com.tencent.connect.UserInfo;
import com.tencent.connect.auth.QQAuth;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

public class ScoreLoginHelper {

	public static final int LOGIN_TYPE_QQ = 1;
	public static final int LOGIN_TYPE_SINA = 2;
	private QQAuth mQQAuth;
	private Tencent mTencent;
	private int mLoginType;
	private Activity mAct;
	private LoginListener mListener;
	private UserInfo mInfo;

	public ScoreLoginHelper(Activity act, int type) {
		mAct = act;
		switch (type) {
		case LOGIN_TYPE_QQ:
			mLoginType = type;
			break;
		case LOGIN_TYPE_SINA:
			break;
		}
	}

	public void login() {
		switch (mLoginType) {
		case LOGIN_TYPE_QQ:
			if (mTencent == null && mAct != null) {
				mTencent = Tencent.createInstance(AppConstants.Tencent_APP_ID,
						mAct);
			}
			if (mQQAuth == null && mAct != null) {
				mQQAuth = QQAuth.createInstance(AppConstants.Tencent_APP_ID,
						mAct);
			}
			mTencent.login(mAct, "all", new LoginUiListener());
			break;
		case LOGIN_TYPE_SINA:
			break;
		}
	}

	public void getUserInfo() {

	}

	public void setLoginListener(LoginListener listener) {
		mListener = listener;
	}

	public void isSessionValid(int type) {
		switch (type) {
		case LOGIN_TYPE_QQ:
			if (mQQAuth.isSessionValid())
				break;
		case LOGIN_TYPE_SINA:
			break;
		}
	}

	class LoginUiListener implements IUiListener {

		@Override
		public void onComplete(Object response) {
			JSONObject object = (JSONObject) response;
			String openId = object.optString("openid");
			String access_token = object.optString("access_token");
			Config.setQQAccessToken(mAct, access_token);
			Config.setQQOpenId(mAct, openId);
			mInfo = new UserInfo(mAct, mQQAuth.getQQToken());
			mInfo.getUserInfo(new UnpdateUserInfoListener());
		}

		@Override
		public void onError(UiError e) {
			mListener.onLoginError(mLoginType, e.errorMessage);
		}

		@Override
		public void onCancel() {
		}
	}

	class UnpdateUserInfoListener implements IUiListener {

		@Override
		public void onCancel() {
			// TODO Auto-generated method stub

		}

		@Override
		public void onComplete(Object arg0) {
			// TODO Auto-generated method stub
			if (mListener != null) {
				mListener.onLoginFinish(mLoginType,
						((JSONObject) arg0).toString());
			}
		}

		@Override
		public void onError(UiError arg0) {
			// TODO Auto-generated method stub

		}

	}

	public interface LoginListener {
		public void onLoginFinish(int type, String values);

		public void onLoginError(int type, String values);

		public void onUpdateUserInfo(int type, String values);
	}

}
