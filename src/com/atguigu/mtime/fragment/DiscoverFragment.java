package com.atguigu.mtime.fragment;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.atguigu.mtime.R;
import com.atguigu.mtime.discover.base.DiscoverDetailBase;
import com.atguigu.mtime.discover.bean.DisCoverTopBean;
import com.atguigu.mtime.discover.bean.Discover_Refresh_Bean;
import com.atguigu.mtime.discover.detail.Discover_Film_Review;
import com.atguigu.mtime.discover.detail.Discover_News;
import com.atguigu.mtime.discover.detail.Discover_Prevue;
import com.atguigu.mtime.discover.detail.Discover_Ranklist;
import com.atguigu.mtime.utils.Constant;
import com.google.gson.Gson;
import com.viewpagerindicator.TabPageIndicator;

/**
 * 发现界面Fragment
 * 
 * @author lupeng
 */
public class DiscoverFragment extends Fragment {
	// 上下文对象声明(onCreate时创建)
	private Context context;

	// TabPageIndicator(开元框架：ViewPager顶部滑动定位器)
	private TabPageIndicator tabPageIndicator;
	// ViewPager
	private ViewPager viewpager_discover;

	// Loading界面和他的动画
	private FrameLayout fl__main_animation;
	private AnimationDrawable animationDrawable;

	// 每个详情界面中顶部视图(topview)数据的url
	private String topurl = "http://api.m.mtime.cn/PageSubArea/GetRecommendationIndexInfo.api";
	// 每个详情界面中顶部视图(topview)的数据
	private DisCoverTopBean disCoverTopdata;

	// 每个详情界面中刷新视图数据的url
	private String refreshurl = "http://api.m.mtime.cn/PageSubArea/PullMovieAdvWordAndCouponActivities.api?locationId=290";
	// 每个详情界面中刷新视图的数据
	private Discover_Refresh_Bean disCoverrefreshdata;

	// ViewPager的视图容器 包含所有详情界面所对应的类的集合
	private List<DiscoverDetailBase> discoverdetaildatas = new ArrayList<DiscoverDetailBase>();

	// 执行ViewPager哪个界面的initdata()方法的页数
	private int currpage = 0;

	/**
	 * 标示获取顶部视图的数据是否成功
	 */
	private boolean issuccess = false;

	// 标题数据
	private String[] titles = { "新闻", "预告片", "排行榜", "影评" };

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.context = getActivity();
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		// 初始化发现界面视图容器及View
		FrameLayout ll_discover = (FrameLayout) View.inflate(context,
				R.layout.discover_layout, null);

		// 初始化ViewPager以及ViewPager顶部滑动定位器
		viewpager_discover = (ViewPager) ll_discover
				.findViewById(R.id.viewpager_discover);
		tabPageIndicator = (TabPageIndicator) ll_discover
				.findViewById(R.id.tabPageIndicator);

		// Loading的视图和动画
		fl__main_animation = (FrameLayout) ll_discover
				.findViewById(R.id.fl__main_animation);
		ImageView iv_main_loading = (ImageView) ll_discover
				.findViewById(R.id.iv_main_loading);
		iv_main_loading.setImageResource(R.drawable.loading_animation_list);
		animationDrawable = (AnimationDrawable) iv_main_loading.getDrawable();

		return ll_discover;
	}

	/**
	 * 当activity创建好后
	 */
	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		super.onActivityCreated(savedInstanceState);
		// 联网请求每个详情界面顶部和下拉刷新界面的数据
		getdatafromnet();
	}

	/**
	 * 联网请求每个详情界面顶部(topview)和下拉刷新界面的数据
	 */
	private void getdatafromnet() {

		/**
		 * 隐藏ViewPager和tabPageIndicator
		 */

		tabPageIndicator.setVisibility(View.GONE);
		viewpager_discover.setVisibility(View.GONE);

		// 显示动画
		fl__main_animation.setVisibility(View.VISIBLE);
		animationDrawable.start();

		// 消息队列
		RequestQueue queue = Volley.newRequestQueue(context);
		// 消息请求
		StringRequest request = new StringRequest(Request.Method.GET, topurl,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String arg0) {
						Log.e("TAG", "获取发现每个详情界面顶部视图的数据成功了");
						// 更新完成标示
						issuccess = true;

						processtopdata(arg0);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError arg0) {
						Log.e("TAG", "获取发现每个详情界面顶部视图的数据失败了");
						// 更新完成标示
						issuccess = false;

						fail();
					}
				});
		// 消息请求
		StringRequest request1 = new StringRequest(Request.Method.GET,
				refreshurl, new Response.Listener<String>() {
					@Override
					public void onResponse(String arg0) {
						processrefreshdata(arg0);

					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError arg0) {
						Log.e("TAG", "获取发现每个详情界面下拉刷新视图的数据失败了");
					}
				});

		queue.add(request);
		queue.add(request1);
	}

	/**
	 * 解析顶部视图的数据，并设置ViewPager
	 */

	/**
	 * 解析每个详情界面顶部的数据
	 *
	 * @param arg0
	 */
	protected void processtopdata(String arg0) {

		// 隐藏动画
		animationDrawable.stop();
		fl__main_animation.setVisibility(View.GONE);

		Gson gson = new Gson();
		disCoverTopdata = gson.fromJson(arg0, DisCoverTopBean.class);

		// 向集合中添加数据
		discoverdetaildatas
				.add(new Discover_News(context, disCoverTopdata.news));// 新闻
		discoverdetaildatas.add(new Discover_Prevue(context,
				disCoverTopdata.trailer));// 预告
		discoverdetaildatas.add(new Discover_Ranklist(context,
				disCoverTopdata.topList));// 排行review
		discoverdetaildatas.add(new Discover_Film_Review(context,
				disCoverTopdata.review));// 影评

		/**
		 * 设置ViewPager并设置监听
		 */

		// 设置viewpager_discover和tabPageIndicator可见
		viewpager_discover.setVisibility(View.VISIBLE);
		tabPageIndicator.setVisibility(View.VISIBLE);

		DiscoverPagerAdapter adapter = new DiscoverPagerAdapter();
		viewpager_discover.setAdapter(adapter);
		// 关联ViewPager
		tabPageIndicator.setViewPager(viewpager_discover);
		tabPageIndicator.setOnPageChangeListener(new MyOnPageChangeListener());

		/**
		 * 滑动到当前页
		 */
		viewpager_discover.setCurrentItem(currpage);

	}

	/**
	 * 解析每个详情界面刷新界面的数据，并保存
	 *
	 * @param arg0
	 */
	public void processrefreshdata(String arg0) {

		Gson gson = new Gson();
		disCoverrefreshdata = gson.fromJson(arg0, Discover_Refresh_Bean.class);
		// 保存到常量类中
		Constant.setDisCoverTopBean(disCoverrefreshdata);
	}

	/**
	 * 请求都失败后调用的方法
	 */
	private void fail() {

		// 隐藏动画
		animationDrawable.stop();
		fl__main_animation.setVisibility(View.GONE);

		/**
		 * 设置ViewPager并设置监听
		 */

		// 设置viewpager_discover和tabPageIndicator可见
		viewpager_discover.setVisibility(View.VISIBLE);
		tabPageIndicator.setVisibility(View.VISIBLE);

		viewpager_discover.removeAllViews();
		viewpager_discover.setAdapter(null);

		viewpager_discover.setAdapter(new DiscoverPagerAdapter());
		// 关联ViewPager
		tabPageIndicator.setViewPager(viewpager_discover);
		tabPageIndicator.setOnPageChangeListener(new MyOnPageChangeListener());
	}

	/**
	 * 自定义OnPageChangeListener
	 */
	class MyOnPageChangeListener implements OnPageChangeListener {
		/**
		 * 滑动到那页执行那页的initdata()方法
		 */
		@Override
		public void onPageSelected(int arg0) {
			currpage = arg0;
		}

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

	}

	/**
	 * 正文ViewPager的适配器
	 */
	class DiscoverPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return 4;
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			View view = null;
			if (issuccess) {// 成功
				Log.e("TAG", "pagertype == 1");
				DiscoverDetailBase detailFragment = discoverdetaildatas
						.get(position);
				view = detailFragment.rootview;
				/**
				 * 创建的事就就执行initdata();
				 */
				discoverdetaildatas.get(position).initdata();

			} else {// 失败
				Log.e("TAG", "pagertype == 2");
				view = View.inflate(context, R.layout.discover_fail, null);
				view.setOnClickListener(new OnClickListener() {

					@Override
					public void onClick(View v) {
						getdatafromnet();
					}
				});
			}
			container.addView(view);
			return view;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			return view == object;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			container.removeView((View) object);
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return titles[position];
		}
	}

}
