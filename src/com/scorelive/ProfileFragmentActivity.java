package com.scorelive;

import java.text.SimpleDateFormat;

import org.json.JSONObject;

import com.scorelive.common.utils.Constants;
import com.scorelive.common.utils.Utility;
import com.sina.weibo.sdk.auth.Oauth2AccessToken;
import com.sina.weibo.sdk.auth.WeiboAuth;
import com.sina.weibo.sdk.auth.WeiboAuthListener;
import com.sina.weibo.sdk.auth.WeiboAuth.AuthInfo;
import com.sina.weibo.sdk.auth.sso.SsoHandler;
import com.sina.weibo.sdk.exception.WeiboException;
import com.sina.weibo.sdk.utils.LogUtil;
import com.tencent.connect.auth.QQAuth;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

public class ProfileFragmentActivity extends Activity{
	 private static final String TAG = "WeiBo_Login";
	 /** 授权认证所需要的信息 */
    private AuthInfo mAuthInfo;
    /** 微博授权认证回调 */
    private WeiboAuthListener mAuthListener = new AuthListener();
    /** SSO 授权认证实例 */
    private SsoHandler mSsoHandler;
    /** 微博授权时，启动 SSO 界面的 Activity */
	private Context mContext;
	/**微博按钮*/
	private RelativeLayout weiboButton;
	/**QQ按钮*/
	private RelativeLayout qqButton;
	public static QQAuth mQQAuth;
    private Tencent mTencent;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		setContentView(R.layout.user_profile);
		mContext=ProfileFragmentActivity.this;
		//头像信息
		Bitmap bmBitmap = Utility.toRoundBitmap(BitmapFactory.decodeResource(getResources(), R.drawable.dashi));
		ImageView ivImageView = (ImageView) findViewById(R.id.splash_logo_image);
		ivImageView.setImageBitmap(bmBitmap);
		//微博
		initWeiboOauth();
		//QQ
		initQQOauth();
	}
	
	private class AuthOnClickListener implements OnClickListener{
		@Override
		public void onClick(View v) {
			if (null == mSsoHandler && mAuthInfo != null) {
				WeiboAuth weiboAuth = new WeiboAuth(mContext, mAuthInfo);
				mSsoHandler = new SsoHandler((Activity)mContext, weiboAuth);
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
            Oauth2AccessToken accessToken = Oauth2AccessToken.parseAccessToken(values);
            if (accessToken != null && accessToken.isSessionValid()) {
                String date = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss").format(
                        new java.util.Date(accessToken.getExpiresTime()));
                Toast.makeText(getApplicationContext(), "获得的accesstoken--->"+accessToken,
                	     Toast.LENGTH_SHORT).show();
               // AccessTokenKeeper.writeAccessToken(getApplicationContext(), accessToken);
            }
        }

        @Override
        public void onWeiboException(WeiboException e) {
            Toast.makeText(ProfileFragmentActivity.this, e.getMessage(), Toast.LENGTH_SHORT).show();
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
	
	private void initWeiboOauth(){
		mAuthInfo = new AuthInfo(this, Constants.SINA_APP_KEY, Constants.REDIRECT_URL, Constants.SCOPE);
		weiboButton=(RelativeLayout)findViewById(R.id.weibo_login_btn);
		weiboButton.setOnClickListener(new AuthOnClickListener());
	}
	private void initQQOauth(){
	    //mTencent.setAccessToken(arg0, arg1);
	    final Context context = ProfileFragmentActivity.this;
		final Context ctxContext = context.getApplicationContext();

	    mQQAuth = QQAuth.createInstance(Constants.Tencent_APP_ID, ctxContext);
		mTencent = Tencent.createInstance(Constants.Tencent_APP_ID, context);
		qqButton=(RelativeLayout)findViewById(R.id.qq_login_btn);
	    OnClickListener listener = new QQButtonOnClickListener();
	    qqButton.setOnClickListener(listener);
	}
	class QQButtonOnClickListener implements OnClickListener {
		@Override
		public void onClick(View v) {
				onClickLogin();

		}
	}
	
	private void onClickLogin() {
		if (!mQQAuth.isSessionValid()) {
			IUiListener listener = new BaseUiListener() {
				@Override
				protected void doComplete(JSONObject values) {
					
				}
			};
			//mQQAuth.login(this, "all", listener);
			//mTencent.loginWithOEM(this, "all", listener,"10000144","10000144","xxxx");
			mTencent.login(this, "all", listener);
		} else {
			mQQAuth.logout(this);
		}
	}
	
	private class BaseUiListener implements IUiListener {

		@Override
		public void onComplete(Object response) {
			//com.scorelive.common.utils.Util.showResultDialog(mContext, response.toString(), "登录成功");
			//这里做一些授权成功后的回调工作
			Toast.makeText(getApplicationContext(), "授权成功！",
              	     Toast.LENGTH_SHORT).show();
			doComplete((JSONObject)response);
		}

		protected void doComplete(JSONObject values) {

		}

		@Override
		public void onError(UiError e) {
			Utility.toastMessage(ProfileFragmentActivity.this, "onError: " + e.errorDetail);
		}

		@Override
		public void onCancel() {
			Utility.toastMessage(ProfileFragmentActivity.this, "onCancel: ");
		}
	}

}
