package com.atguigu.mtime;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.atguigu.mtime.discover.bean.News_Item_Bean;
import com.google.gson.Gson;

/**
 * 发现中新闻每个item对应的页面
 * 
 * @author Lupeng
 */
public class Discover_news_item extends Activity {
	TextView tv_item_title;
	TextView tv_item_time;
	TextView tv_item_source;
	private TextView tv_item_editor;

	private WebView web_view;
	private String pathuri;
	private WebSettings settings;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.discover_news_item);

		Intent intent = getIntent();
		Uri uri = intent.getData();
		pathuri = uri.toString();

		// 初始化视图
		initview();

		// 联网获取数据
		getdatafromnet();

	}

	/**
	 * 初始化视图
	 */
	private void initview() {
		tv_item_title = (TextView) findViewById(R.id.tv_item_title);
		tv_item_time = (TextView) findViewById(R.id.tv_item_time);
		tv_item_source = (TextView) findViewById(R.id.tv_item_source);
		tv_item_editor = (TextView) findViewById(R.id.tv_item_editor);
		web_view = (WebView) findViewById(R.id.web_view);

		// // 获取设置WebView属性的类
		// settings = web_view.getSettings(); //
		//
		// // 设置WebView支持javaScript
		// settings.setJavaScriptEnabled(true); //
		//
		// // 启动页面上放大缩小的按钮
		// // settings.setBuiltInZoomControls(true);
		// // 启动双击屏幕变大变小的功能
		// settings.setUseWideViewPort(true);

		// 网上找的代码
		// LinearLayout.LayoutParams mWebViewLP = new LinearLayout.LayoutParams(
		// LinearLayout.LayoutParams.MATCH_PARENT,
		// LinearLayout.LayoutParams.MATCH_PARENT);
		// web_view.setLayoutParams(mWebViewLP);
		// web_view.setInitialScale(25);
		// // 适应屏幕
		// settings.setUseWideViewPort(true);
		// settings.setSupportZoom(true);
		// settings.setLayoutAlgorithm(LayoutAlgorithm.SINGLE_COLUMN);
		// settings.setLoadWithOverviewMode(true);
		// settings.setBuiltInZoomControls(true);
	}

	/**
	 * 联网获取数据
	 */
	private void getdatafromnet() {
		RequestQueue queue = Volley.newRequestQueue(this);
		StringRequest request = new StringRequest(Request.Method.GET, pathuri,
				new Listener<String>() {

					@Override
					public void onResponse(String response) {
						Log.e("TAG", "获取新闻item数据成功");
						progressdata(response);
					}
				}, new Response.ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("TAG", "获取新闻item数据失败");
					}
				});
		queue.add(request);
	}

	protected void progressdata(String response) {
		Gson gson = new Gson();
		News_Item_Bean news_item_bean = gson.fromJson(response,
				News_Item_Bean.class);
		// web_view.loadUrl(news_Item_Bean.url);
		// webView.loadDataWithBaseURL("http://www.3dmgame.com/", html,
		// "text/html","UTF-8", null);
		tv_item_title.setText(news_item_bean.title);
		String html = news_item_bean.content;
		web_view.loadDataWithBaseURL(null, html, "text/html", "UTF-8", null);

	}
}
