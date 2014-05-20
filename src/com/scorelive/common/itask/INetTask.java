package com.scorelive.common.itask;

import java.io.IOException;
import java.io.InputStream;
import java.net.UnknownHostException;

import org.apache.http.HttpEntity;
import org.apache.http.message.BasicHeader;

import android.content.Context;

import com.scorelive.ScoreLiveApplication;
import com.scorelive.common.http.Http;
import com.scorelive.common.http.HttpResponseException;
import com.scorelive.common.utils.Utility;

public abstract class INetTask extends ITask implements Comparable<INetTask> {

	protected String mUrl;

	protected byte[] mRequest;

	protected String mContentType;

	protected BasicHeader[] mHeaders;

	protected Context mContext;

	protected String mRequestType;

	protected String mMethod;

	private INetTaskListener mListener;

	public static final int PRIORITY_HIGH = 0X3;// 高优先级
	public static final int PRIORITY_MID = 0X2;// 中等优先级
	public static final int PRIORITY_LOW = 0X1;// 低优先级
	private int mPriority = PRIORITY_MID;

	public INetTask(int type,long taskId) {
		super(type,taskId);
		mContext = ScoreLiveApplication.getInstance().getApplicationContext();
	}

	public void setPriority(int priority) {
		mPriority = priority;
	}

	@Override
	public void run() {
		try {
			mMethod = getRequestType();
			mRequest = getRequestBody();
			mHeaders = getHeader();
			mContentType = getContentType();
			mRequest = getRequestBody();
			if (Utility.isNetworkAvaiable(mContext)) {// 如果网络可用则发起请求
				try {
					HttpEntity response = Http.doRequest(mUrl, mRequest,
							mMethod, mHeaders, mContentType, mContext);
					onFinish(response);
				} catch (HttpResponseException e) {
					onError(e);
				} catch (IOException e) {
					onError(e);
				} catch (Exception e) {
					onError(e);
				}
			} else {// 否则抛出UnknownHostException
				onError(new UnknownHostException());
			}
		} catch (Exception e) {
			onError(e);
		}
	}

	@Override
	public void onFinish(Object object) {
		HttpEntity response = (HttpEntity) object;
		InputStream is = null;
		try {
			is = response.getContent();
			if (mListener != null) {
				mListener.onTaskFinish(this, is);
			}
		} catch (IllegalStateException e) {
			onError(e);
		} catch (IOException e) {
			onError(e);
		} catch (Exception e) {
			onError(e);
		} finally {
			if (is != null) {
				try {
					is.close();
				} catch (IOException e) {
					// 忽略
				}
				is = null;
			}
		}
	}

	@Override
	public void onError(Exception exception) {
		mListener.onTaskError(this, exception);
	}

	@Override
	public void setListener(ITaskListener listener) {
		// TODO Auto-generated method stub
		mListener = (INetTaskListener) listener;
	}

	/**
	 * 如果两个请求的URL相同，则认为它们是同样的任务
	 */
	@Override
	public boolean equals(Object o) {
		if (mUrl.equalsIgnoreCase(((INetTask) o).getUrl())) {
			return true;
		}
		return false;
	}

	public int getPriority() {
		return mPriority;
	}

	@Override
	public int compareTo(INetTask another) {
		if (this.getPriority() < another.getPriority()) {
			return 1;
		}
		if (this.getPriority() > another.getPriority()) {
			return -1;
		}
		return 0;
	}

	/**
	 * 请求类型 Http.GET/Http.POST
	 * 
	 * @return
	 */
	public abstract String getRequestType();

	/**
	 * 请求Url
	 * 
	 * @return
	 */
	public abstract String getUrl();

	/**
	 * 请求的头部
	 * 
	 * @return
	 */
	public abstract BasicHeader[] getHeader();

	/**
	 * 请求的ContentType
	 * 
	 * @return
	 */
	public abstract String getContentType();

	/**
	 * POST方法请求体的内容
	 * 
	 * @return
	 */
	public abstract byte[] getRequestBody();

}
