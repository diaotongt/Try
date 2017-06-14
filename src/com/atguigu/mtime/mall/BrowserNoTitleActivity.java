package com.atguigu.mtime.mall;

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
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class BrowserNoTitleActivity extends Activity {

	private String url;
	private WebView mWebView;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.browser_notitle_activity);
		
		url = getIntent().getStringExtra("url");

		getViews();
		setWebView();
		mWebView.loadUrl(url);

	}

	private void getViews() {
		mWebView = (WebView) findViewById(R.id.home_content);
	}

	private void setWebView() {
		mWebView.getSettings().setJavaScriptEnabled(true);
		mWebView.setWebViewClient(new SampleWebViewClient());
		mWebView.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT); // 设置

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
			Intent intent = new Intent(BrowserNoTitleActivity.this, BrowserActivity.class);
			intent.putExtra("url", url);
			startActivity(intent);
			return true;
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
		}

		@Override
		public void onPageFinished(WebView view, String url) {
		}

		@Override
		public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {

		}
	}

}
