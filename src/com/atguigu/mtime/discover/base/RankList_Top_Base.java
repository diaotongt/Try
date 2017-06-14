package com.atguigu.mtime.discover.base;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.atguigu.mtime.R;

/**
 * 全球排行旁的基类
 * 
 * @author Mr lu
 *
 */
public class RankList_Top_Base {

	protected Context context;
	public View rootview;

	protected TextView tv_titleSmall;
	protected ListView lv_ranklist_top;
	protected FrameLayout fl__main_animation;
	protected AnimationDrawable animationDrawable;

	public RankList_Top_Base(Context context) {
		this.context = context;

		rootview = getview();
	}

	private View getview() {
		View view = View.inflate(context, R.layout.ranklist_top_base, null);
		tv_titleSmall = (TextView) view.findViewById(R.id.tv_titleSmall);
		lv_ranklist_top = (ListView) view.findViewById(R.id.lv_ranklist_top);
		fl__main_animation = (FrameLayout) view
				.findViewById(R.id.fl__main_animation);
		ImageView iv_main_loading = (ImageView) view
				.findViewById(R.id.iv_main_loading);
		iv_main_loading.setImageResource(R.drawable.loading_animation_list);
		animationDrawable = (AnimationDrawable) iv_main_loading.getDrawable();
		return view;
	}

	public void initdata() {
	}
}
