package com.atguigu.mtime.mall;

import com.atguigu.mtime.R;
import com.atguigu.mtime.utils.ToastUtil;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;
import android.widget.RelativeLayout;

public class BrowserScrollActivity extends Activity implements OnClickListener {

	private Context context = null;
	private String url;
	private WebView mWebView;
	private ImageView background;
	private ImageView back;
	private RelativeLayout pre;
	private RelativeLayout next;
	private RelativeLayout open;
	private RelativeLayout refresh;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mall_scroll_product_view);
		context = BrowserScrollActivity.this;

		getViews();
		setListeners();
		setWebView();

		url = getIntent().getStringExtra("url");
		mWebView.loadUrl(url);

	}

	private void setListeners() {
		back.setOnClickListener(this);
		pre.setOnClickListener(this);
		next.setOnClickListener(this);
		open.setOnClickListener(this);
		refresh.setOnClickListener(this);
	}

	private void getViews() {
		background = (ImageView) findViewById(R.id.background);
		background.getBackground().setAlpha(255);
		mWebView = (WebView) findViewById(R.id.home_content);
		back = (ImageView) findViewById(R.id.back);
		pre = (RelativeLayout) findViewById(R.id.pre);
		next = (RelativeLayout) findViewById(R.id.next);
		open = (RelativeLayout) findViewById(R.id.open);
		refresh = (RelativeLayout) findViewById(R.id.refresh);
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
			Intent intent = new Intent(BrowserScrollActivity.this, BrowserActivity.class);
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

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.back:
			finish();
			break;
		case R.id.pre:
			ToastUtil.showToast(context, "已经没有上一页了");
			break;
		case R.id.next:
			ToastUtil.showToast(context, "已经没有下一页了");
			break;
		case R.id.open:
			startSystemBrowser();
			break;
		case R.id.refresh:
			mWebView.reload();
			break;
		default:
			break;
		}
	}

	private void startSystemBrowser() {
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		Uri content_url = Uri.parse(url);
		intent.setData(content_url);
		startActivity(intent);
	}

}
