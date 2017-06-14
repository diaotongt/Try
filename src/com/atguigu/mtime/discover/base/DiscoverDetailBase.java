package com.atguigu.mtime.discover.base;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import com.atguigu.mtime.R;
import com.atguigu.mtime.view.RefreshListView;

/**
 * 发现详情界面的基类
 * 
 * @author Mr_Lu
 *
 */
public class DiscoverDetailBase {

	protected Context context;
	/**
	 * 发现详情界面的 基本视图
	 */
	public View rootview;
	/**
	 * 发现详情界面中的ListView
	 */
	protected RefreshListView discover_listview;
	/**
	 * 动画的视图和对象
	 * 
	 * @param context
	 */
	protected AnimationDrawable animationDrawable;
	protected FrameLayout fl__main_animation;

	/**
	 * 加载失败点击刷新的视图
	 */
	 protected FrameLayout fl__main_refresh;

	public DiscoverDetailBase(Context context) {
		this.context = context;
		rootview = initview();
	}

	/**
	 * 初始化视图
	 * 
	 * @return
	 */
	private View initview() {

		View view = View.inflate(context, R.layout.discover_datail_base, null);

		discover_listview = (RefreshListView) view
				.findViewById(R.id.discover_listview);
		discover_listview.setDividerHeight(0);
		fl__main_animation = (FrameLayout) view
				.findViewById(R.id.fl__main_animation);
		fl__main_refresh = (FrameLayout) view
		.findViewById(R.id.fl__main_refresh);

		// 初始化加载动画
		ImageView iv_main_loading = (ImageView) view
				.findViewById(R.id.iv_main_loading);
		iv_main_loading.setImageResource(R.drawable.loading_animation_list);
		animationDrawable = (AnimationDrawable) iv_main_loading.getDrawable();

		return view;
	}

	/**
	 * 让子类根据自己的情况实现自己的逻辑
	 */
	public void initdata() {
	}
}
