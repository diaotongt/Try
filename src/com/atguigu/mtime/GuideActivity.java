package com.atguigu.mtime;

import java.util.ArrayList;
import java.util.List;

import com.atguigu.mtime.utils.CacheUtils;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

/**
 * 向导指南Activity
 * @author Wangnan
 *
 */
public class GuideActivity extends Activity {

	/**
	 * ViewPager的声明（用于引用初始化数据）
	 * @author Wangnan
	 */
	private ViewPager vp_guide;
	
	/**
	 * 布局资源的装载集合
	 * @author Wangnan
	 */
	private List<RelativeLayout> rl_lead_pages = new ArrayList<RelativeLayout>();
	
	/**
	 * 最后一张引导页的跳转Button
	 * @author Wangnan
	 */
	private Button btn_lead_page4;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_guide);
		
		//初始化视图
		initView();
		
		//初始化数据（ImageView的数据）
		initData();
		
		//设置ViewPager的适配器
		vp_guide.setAdapter(new GuidePagerAdapter());
		
		//设置跳转Button的监听
		btn_lead_page4.setOnClickListener(new Page4BtnOnClickListener());
	}

	/**
	 * 初始化视图
	 * @author Wangnan
	 */
	private void initView() {
		vp_guide = (ViewPager) findViewById(R.id.vp_guide);
	}

	/**
	 * 初始化数据(ImageView的数据)
	 * @author Wangnan
	 */
	private void initData() {

		RelativeLayout rl = (RelativeLayout) View.inflate(this, R.layout.lead_page1, null);
		RelativeLayout r2 = (RelativeLayout) View.inflate(this, R.layout.lead_page2, null);
		RelativeLayout r3 = (RelativeLayout) View.inflate(this, R.layout.lead_page3, null);
		RelativeLayout r4 = (RelativeLayout) View.inflate(this, R.layout.lead_page4, null);
		btn_lead_page4 = (Button) r4.findViewById(R.id.btn_lead_page4);
		
		rl_lead_pages.add(rl);
		rl_lead_pages.add(r2);
		rl_lead_pages.add(r3);
		rl_lead_pages.add(r4);

	}

	
	/**
	 * ViewPager的适配器类
	 * @author Wangnan
	 */
	class GuidePagerAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return 4;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			RelativeLayout rl = rl_lead_pages.get(position);
			container.addView(rl);
			return rl;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View)object);
		}
	}
	
	/**
	 * 引导页跳转Button的监听类
	 * @author Wangnan
	 *
	 */
	class Page4BtnOnClickListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			// TODO Auto-generated method stub
			if(v == btn_lead_page4){
				
				//保存配置：引导界面已经加载过
				CacheUtils.setBoolean(GuideActivity.this, "IS_TO_ADV", true);
				
				////跳转至MainActivity,设置页面切换效果
				startActivity(new Intent(GuideActivity.this,MainActivity.class));				
				overridePendingTransition(R.anim.welcome_right_in, R.anim.welcome_left_out);
				finish();
			}
		}
		
	}
}
