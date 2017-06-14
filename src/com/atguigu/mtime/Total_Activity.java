package com.atguigu.mtime;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.atguigu.mtime.discover.base.RankList_Top_Base;
import com.atguigu.mtime.discover.bean.Discover_TOP_Bean.SubTopList;
import com.viewpagerindicator.TabPageIndicator;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;

/**
 * 全球Top100
 * 
 * @author Mr lu
 *
 */
public class Total_Activity extends Activity {

	// 说明：pageIndex为页码数，pageSubAreaID为每个排行榜的编号
	private String pageSubAreaID;
	private String baseurl = "http://api.m.mtime.cn/TopList/TopListDetailsByRecommend.api?pageIndex=1&pageSubAreaID="
			+ pageSubAreaID + "&locationId={2}";

	// 所有排行对象的集合
	private ArrayList<SubTopList> subTopList;

	// TabPageIndicator(开元框架：ViewPager顶部滑动定位器)
	private TabPageIndicator tabPageIndicator;
	// ViewPager
	private ViewPager viewpager_discover;

	// ViewPager的集合
	private List<RankList_Top_Base> ranklist_top_totals = new ArrayList<RankList_Top_Base>();
	// title的数据
	private String[] titles = { "北美", "内地", "香港", "台湾", "日本", "韩国" };

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.discover_layout);
		// 初始化视图
		initview();
		// 获取数据
		Intent intent = getIntent();
		subTopList = (ArrayList<SubTopList>) intent
				.getSerializableExtra("pageSubAreaID");
		Log.e("TAG", "进入了吗");
		// 添加ViewPager的数据
		ranklist_top_totals.add(new BeiMei(this, subTopList.get(0)));
		ranklist_top_totals.add(new NeiDi(this, subTopList.get(1)));
		ranklist_top_totals.add(new XiangGang(this, subTopList.get(2)));
		ranklist_top_totals.add(new TaiWan(this, subTopList.get(3)));
		ranklist_top_totals.add(new RiBen(this, subTopList.get(4)));
		ranklist_top_totals.add(new HanGuo(this, subTopList.get(5)));

		// 设置viewpager_discover和tabPageIndicator可见
		viewpager_discover.setVisibility(View.VISIBLE);
		tabPageIndicator.setVisibility(View.VISIBLE);

		MyPagerAdapter adapter = new MyPagerAdapter();
		viewpager_discover.setAdapter(adapter);
		// 关联ViewPager
		tabPageIndicator.setViewPager(viewpager_discover);
		tabPageIndicator.setOnPageChangeListener(new MyOnPageChangeListener());

		// 默认执行第0个的initdata();
		RankList_Top_Base ranklist_top_base = ranklist_top_totals.get(0);
		ranklist_top_base.initdata();

	}

	private void initview() {
		// 初始化ViewPager以及ViewPager顶部滑动定位器
		viewpager_discover = (ViewPager) findViewById(R.id.viewpager_discover);
		tabPageIndicator = (TabPageIndicator) findViewById(R.id.tabPageIndicator);

	}

	class MyOnPageChangeListener implements OnPageChangeListener {

		@Override
		public void onPageScrollStateChanged(int arg0) {

		}

		@Override
		public void onPageScrolled(int arg0, float arg1, int arg2) {

		}

		@Override
		public void onPageSelected(int arg0) {
			RankList_Top_Base ranklist_top_base = ranklist_top_totals.get(arg0);
			ranklist_top_base.initdata();
		}

	}

	class MyPagerAdapter extends PagerAdapter {

		@Override
		public int getCount() {
			return ranklist_top_totals.size();
		}

		@Override
		public boolean isViewFromObject(View arg0, Object arg1) {
			return arg0 == arg1;
		}

		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// super.destroyItem(container, position, object);
			container.removeView((View) object);
		}

		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			RankList_Top_Base ranklist_top_base = ranklist_top_totals
					.get(position);
			View view = ranklist_top_base.rootview;
			/**
			 * 在这执行inid()
			 */
			// ranklist_top_base.initdata();
			container.addView(view);

			return view;
		}

		@Override
		public CharSequence getPageTitle(int position) {
			return titles[position];
		}
	}
}
