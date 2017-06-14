package com.atguigu.mtime.web;

import com.atguigu.mtime.R;
import com.handmark.pulltorefresh.library.PullToRefreshWebView;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.RelativeLayout;

public class BrowserActivity extends Activity {

	private String URL;
	private PullToRefreshWebView mPullRefreshWebView;
	private WebView mWebView;
	private Context context;
	private RelativeLayout progress;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.webview);

		URL = getIntent().getStringExtra("url");
		context = this;
		getViews();
		setWebView();
		mWebView.loadUrl(URL);

	}

	private void setWebView() {
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.setWebViewClient(new SampleWebViewClient());
		mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT); // 设置

	}

	private void getViews() {

		mPullRefreshWebView = (PullToRefreshWebView) findViewById(R.id.pull_refresh_webview);
		mWebView = mPullRefreshWebView.getRefreshableView();
		progress = (RelativeLayout) findViewById(R.id.progress);

	}

	private void getDatas() {

	}

	// 设置回退
	// 覆盖Activity类的onKeyDown(int keyCoder,KeyEvent event)方法
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if ((keyCode == KeyEvent.KEYCODE_BACK) && mWebView.canGoBack()) {
			mWebView.goBack(); // goBack()表示返回WebView的上一页面
			return true;
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 对Web页面进行控制的类
	 * 
	 * @author DTT
	 *
	 */
	private class SampleWebViewClient extends WebViewClient {
		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {
			Intent intent = new Intent("com.dtt.goodsdetails");
			// intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
			intent.putExtra("url", url);
			context.startActivity(intent);
			return true;
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			progress.setVisibility(View.VISIBLE);
		}

		@Override
		public void onPageFinished(WebView view, String url) {
			progress.setVisibility(View.INVISIBLE);
		}

		@Override
		public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

		}
	}

}
