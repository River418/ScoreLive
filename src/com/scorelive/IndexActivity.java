package com.scorelive;

import java.io.File;

import org.apache.cordova.DroidGap;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebSettings.RenderPriority;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.scorelive.common.utils.AppConstants;

public class IndexActivity extends DroidGap {

	private WebView mWebPage;
	private WebSettings mWebSettings;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.index_activity);
//		mWebPage = (WebView) findViewById(R.id.webview);
//		initWebView();
//		File file = new File(AppConstants.INDEX);
//		if (file.exists()) {
//			mWebPage.loadUrl("file:///"+AppConstants.INDEX);
//		}
		super.loadUrl(AppConstants.INDEX);

	}

	private void initWebView() {
		mWebSettings = mWebPage.getSettings();
		mWebSettings.setRenderPriority(RenderPriority.HIGH);
		mWebSettings.setJavaScriptEnabled(true);
		setWebChromeClient();
		setWebViewClient();
	}

	private void setWebChromeClient() {
		mWebPage.setWebChromeClient(new WebChromeClient() {

			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				// TODO Auto-generated method stub
				super.onProgressChanged(view, newProgress);
			}

			@Override
			public void onReceivedTitle(WebView view, String title) {
				// TODO Auto-generated method stub
				super.onReceivedTitle(view, title);
			}

		});
	}

	private void setWebViewClient() {
		mWebPage.setWebViewClient(new WebViewClient() {

			@Override
			public void onPageFinished(WebView view, String url) {
				// TODO Auto-generated method stub
				super.onPageFinished(view, url);
			}

			@Override
			public void onPageStarted(WebView view, String url, Bitmap favicon) {
				// TODO Auto-generated method stub
				super.onPageStarted(view, url, favicon);
			}

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				// TODO Auto-generated method stub
				return super.shouldOverrideUrlLoading(view, url);
			}

		});
	}

}
