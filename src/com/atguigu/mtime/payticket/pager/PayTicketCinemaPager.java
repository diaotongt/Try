package com.atguigu.mtime.payticket.pager;

import android.content.Context;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.widget.FrameLayout;

/**
 * 影院详情页
 * @author Wangnan
 *
 */
public class PayTicketCinemaPager {
	
	/**
	 * 上下文对象
	 */
	private Context context;
	
	/**
	 * 视图对象
	 */
	public View root;
	
	public PayTicketCinemaPager(Context context){
		this.context = context;		
		root = initView();
		Log.e("TAG", "PayTicketCinemaPager");
	}
	
	/**
	 * 初始化视图
	 * @return
	 */
	private View initView() {
		FrameLayout frameLayout = new FrameLayout(context);
		frameLayout.setBackgroundColor(Color.GRAY);
		return frameLayout;
	}
	
	/**
	 * 初始化数据
	 */
	public void initDate(){
		
	}
}
