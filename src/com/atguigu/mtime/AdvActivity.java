package com.atguigu.mtime;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import com.atguigu.mtime.utils.CacheUtils;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.widget.ImageView;
import android.widget.TextView;

/**
 * 广告界面Activity
 * @author Wangnan
 */
public class AdvActivity extends Activity {

	/**
	 * 倒计时Msg标识
	 * @author Wangnan
	 */
	private static final int TIMECOUNT = 1;
	
	/**
	 * 用于显示的倒计时TextView,及背景广告图片（动态加载）
	 * @author Wangnan
	 */
	private TextView tv_adv_count;
	private ImageView iv_adv_photo;
	
	/**
	 * Handler倒计时处理
	 */
	private Handler handler = new Handler(){
	
		public void handleMessage(android.os.Message msg) {
			if(msg.what == TIMECOUNT){
				
				int time = msg.arg1;
				
				//移除原有的消息
				removeMessages(TIMECOUNT);
				
				if(time >= 0){
					//显示时间
					tv_adv_count.setText(time+"");
					//发送新的延迟消息
					Message newMsg = Message.obtain();
					newMsg.what = TIMECOUNT;
					newMsg.arg1 = --time;
					sendMessageDelayed(newMsg, 1000);
				}
			}
		}
	};
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_adv);
			
		//倒计时的显示View,及背景广告图片（动态加载）
		tv_adv_count = (TextView) findViewById(R.id.tv_adv_count);
		iv_adv_photo = (ImageView) findViewById(R.id.iv_adv_photo);
		
		//加载背景广告图片，如果内部文件没有,则使用默认图片
		loadAdvPhoto();
		
		//Time倒计时
		timeCount();
		
		//跳转至MainActivity
		toMainActivity();
	}


	/**
	 * 倒计时时间
	 * @author Wangnan
	 */
	private void timeCount() {
		
		Message msg = Message.obtain();
		msg.what = TIMECOUNT;
		msg.arg1 = 3;
		handler.sendMessage(msg);
	}
	
	/**
	 * 加载背景广告图片，如果内部文件没有,则使用默认图片
	 * @author Wangnan
	 */
	private void loadAdvPhoto() {
		
		//定位要读取的图片文件
		File file = new File(getFilesDir(), "Adv");
		
		//获取屏幕的宽度，用于确定读取的图片是否需要压缩
		int screenWidth = CacheUtils.getInt(this, "SCREEN_WIDTH");
		int srceenHeight = CacheUtils.getInt(this, "SCREEN_HEIGHT");
		
		FileInputStream fis = null;
		Bitmap bitmap = null;
		try {
	        fis = new FileInputStream(file);
	        
	        //根据手机分辨率动态设置Bitmap的读取方式，避免内存溢出
	        if(screenWidth < 480 || srceenHeight < 800){
		        BitmapFactory.Options options = new BitmapFactory.Options();
		        
		        //获取原文件图片的1/2宽高，像素点的个数为原来的1/4	(注：为保证图片的清晰度可以根据手机分辨率进行细分，设置不同的inSampleSize大小，但必须是整数)	        
		        options.inSampleSize = 2;
				bitmap = BitmapFactory.decodeStream(fis,null,options);
				
	        }else{
	        	bitmap = BitmapFactory.decodeStream(fis);
	        }
			
			if(bitmap != null){
				iv_adv_photo.setImageBitmap(bitmap);
			}
			
		}catch(Exception e){
			e.printStackTrace();
		} finally{
			
			//关闭输入流
			if(fis != null){
				try {
					fis.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * 跳转至MainActivity
	 * @author Wangnan
	 */
	private void toMainActivity() {
		handler.postDelayed(new Runnable() {
			
			@Override
			public void run() {
				
				//跳转至MainActivity,设置页面切换效果
				startActivity(new Intent(AdvActivity.this,MainActivity.class));				
				overridePendingTransition(R.anim.welcome_right_in, R.anim.welcome_left_out);
				finish();
			}
		}, 3000);
		
	}
	
	@Override
	protected void onDestroy() {
		
		//Activity退出时，移除消息
		handler.removeCallbacksAndMessages(null);
		super.onDestroy();
	}
}
