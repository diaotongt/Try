package com.atguigu.mtime;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.Volley;
import com.atguigu.mtime.utils.CacheUtils;
import com.atguigu.mtime.utils.NetUtils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.Bitmap.Config;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.widget.RelativeLayout;
import android.widget.Toast;

/**
 * 用于展现欢迎界面的Activity
 * @author Wangnan
 */
public class WelcomeActivity extends Activity {

	/**
	 * 根布局的根标签
	 * @author Wangnan
	 */
	private RelativeLayout rl_welcome;
	
	/**
	 * Handler:页面跳转时，发送延迟消息
	 * @author Wangnan
	 */
	private Handler handler =new Handler();
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_welcome);
		
		//初始化视图，保存屏幕尺寸
		initView();
		
		//联网请求-加载广告图片
		getImageFromNetFromeNet();
			
	}

	/**
	 * 延迟跳转至下一个界面
	 * @author Wangnan
	 */
	private void postedToNext() {

		handler.postDelayed(new Runnable() {
			@Override
			public void run() {
				//前往下一个Activity
				toNextActivity();
			}
		}, 1000);
	}


	/**
	 * 初始化视图，保存屏幕尺寸
	 * @author Wangnan
	 */
	private void initView() {

	    rl_welcome = (RelativeLayout) findViewById(R.id.rl_welcome);
	    
	    //获取屏幕尺寸，保存到SP文件，用于后续屏幕适配
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int screenWidth = dm.widthPixels;
		int screenHeight = dm.heightPixels;
			 
		CacheUtils.setInt(this, "SCREEN_WIDTH", screenWidth);
		CacheUtils.setInt(this, "SCREEN_HEIGHT", screenHeight);
	}
	
	/**
	 * 联网请求-加载广告图片
	 * @author Wangnan
	 */
	private void getImageFromNetFromeNet() {
		
		//判断网络是否有链接
		boolean isConnected = NetUtils.isConnected(WelcomeActivity.this);
		if(!isConnected){
			Toast.makeText(this, "当前没有网络连接", Toast.LENGTH_LONG).show();
		}else{
			Toast.makeText(this, "数据加载中,请稍后...", 1).show();
		}
		//得到请求队列
		RequestQueue requestQueue = Volley.newRequestQueue(this);
		String url = "http://img31.mtime.cn/mg/2015/12/09/142746.65155750.jpg";	
		
		//创建图片请求
		ImageRequest imageRequest = new ImageRequest(url
				, new Listener<Bitmap>() {

			@Override
			public void onResponse(Bitmap response) {

				//得到广告图片后,进行内部文件存储
				File file = getFilesDir();
				File photoFile = new File(file, "Adv");
				FileOutputStream fos = null;
				try {
					fos = new FileOutputStream(photoFile);
					response.compress(CompressFormat.PNG, 100, fos);
				} catch (FileNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} finally{
					if(fos != null){
						try {
							fos.close();
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
					}
					Log.e("TAG", "onResponse");
					//延迟跳转至下一个界面
					postedToNext();
				}	
			}
		}, 0, 0, Config.ARGB_4444, new Response.ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				//延迟跳转至下一个界面
				postedToNext();	
				Log.e("TAG", "onErrorResponse");
			}
		});
		
		//向请求队列添加请求信息
		requestQueue.add(imageRequest);
		
	}
	

	/**
	 * 前往下一个Activity
	 * @author Wangnan
	 */
	private void toNextActivity() {
		boolean toAdv = CacheUtils.getBoolean(this, "IS_TO_ADV");//ADV:advertising(广告界面)
		if(toAdv){
			//去往广告界面  注：影视广告界面之后才是MainActivity
			startActivity(new Intent(WelcomeActivity.this,AdvActivity.class));
		}else{
			//去往引导界面
			startActivity(new Intent(WelcomeActivity.this, GuideActivity.class));
		}	
		//设置页面切换效果
		overridePendingTransition(R.anim.welcome_right_in, R.anim.welcome_left_out);
		//结束掉WelcomeActivity
		finish();	
	}
	
	@Override
	protected void onDestroy() {
		//注意：程序退出时要移除消息，否则用户在回退时，还会跳转至下一个Activity
		handler.removeCallbacksAndMessages(null);
		super.onDestroy();
	}
}
