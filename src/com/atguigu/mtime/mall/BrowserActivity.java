package com.atguigu.mtime.mall;

import com.atguigu.mtime.R;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import cn.sharesdk.framework.ShareSDK;
import cn.sharesdk.onekeyshare.OnekeyShare;

public class BrowserActivity extends Activity implements OnClickListener {

	private String url;
	private String title;
	
	private WebView mWebView;
	private ImageView background;
	private RelativeLayout back;
	private ImageButton share;
	private ImageView cart;
	private Context context;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mall_product_view);
		context = BrowserActivity.this;

		getViews();
		setListeners();

		url = getIntent().getStringExtra("url");
		title = getIntent().getStringExtra("title");
		
		getViews();
		setWebView();
		mWebView.loadUrl(url);

	}

	private void setListeners() {
		back.setOnClickListener(this);
		share.setOnClickListener(this);
		cart.setOnClickListener(this);
	}

	private void getViews() {
		background = (ImageView) findViewById(R.id.background);
		background.getBackground().setAlpha(255);
		mWebView = (WebView) findViewById(R.id.home_content);
		back = (RelativeLayout) findViewById(R.id.back);
		share = (ImageButton) findViewById(R.id.share);
		cart = (ImageView) findViewById(R.id.cart);
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
			mWebView.loadUrl(url);
			return false;
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
		case R.id.share:// 把url分享
			showShare();
			break;
		case R.id.cart:
			startCartBrowser();
			break;

		default:
			break;
		}
	}

	private void showShare() {
		ShareSDK.initSDK(this);
		OnekeyShare oks = new OnekeyShare();
		// 关闭sso授权
		oks.disableSSOWhenAuthorize();

		// 分享时Notification的图标和文字 2.5.9以后的版本不调用此方法
		// oks.setNotification(R.drawable.ic_launcher,
		// getString(R.string.app_name));
		// title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间使用
		oks.setTitle("MTime");
		// titleUrl是标题的网络链接，仅在人人网和QQ空间使用
		oks.setTitleUrl(url);
		// text是分享文本，所有平台都需要这个字段
		oks.setText("我是分享文本");
		// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
		oks.setImagePath("");// 确保SDcard下面存在此张图片
		// url仅在微信（包括好友和朋友圈）中使用
		oks.setUrl(url);
		// comment是我对这条分享的评论，仅在人人网和QQ空间使用
		oks.setComment("我是测试评论文本");
		// site是分享此内容的网站名称，仅在QQ空间使用
		oks.setSite("MTime");
		// siteUrl是分享此内容的网站地址，仅在QQ空间使用
		oks.setSiteUrl(url);

		// 启动分享GUI
		oks.show(this);
	}

	/**
	 * 启动收藏夹
	 */
	private void startCartBrowser() {
		Intent intent = new Intent(BrowserActivity.this, BrowserCartActivity.class);
		intent.putExtra("url", "http://m.mtime.cn/#!/commerce/cart/");
		startActivity(intent);
	}

}
