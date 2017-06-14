package com.atguigu.mtime.discover.detail;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.net.Uri;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.atguigu.mtime.Chinese_Top_Activity;
import com.atguigu.mtime.Discover_Ranklist_item;
import com.atguigu.mtime.R;
import com.atguigu.mtime.Time_Top_Activity;
import com.atguigu.mtime.Total_Activity;
import com.atguigu.mtime.discover.base.DiscoverDetailBase;
import com.atguigu.mtime.discover.bean.DisCoverTopBean.TopList;
import com.atguigu.mtime.discover.bean.Discover_Ranklist_Bean;
import com.atguigu.mtime.discover.bean.Discover_Ranklist_Bean.TopLists;
import com.atguigu.mtime.discover.bean.Discover_Refresh_Bean;
import com.atguigu.mtime.discover.bean.Discover_Refresh_Bean.AdvWordList;
import com.atguigu.mtime.discover.bean.Discover_TOP_Bean;
import com.atguigu.mtime.utils.CacheUtils;
import com.atguigu.mtime.utils.Constant;
import com.atguigu.mtime.view.RefreshListView.OnRefreshListener;
import com.google.gson.Gson;

/**
 * 发现排行详情界面
 * 
 * @author lupeng
 *
 */
public class Discover_Ranklist extends DiscoverDetailBase {

	// 说明：通过上面的网址拿到每个排行榜的pageSubAreaID
	private String topranklisturl = "http://api.m.mtime.cn/TopList/TopListFixedNew.api";
	// 包含每个排行榜pageSubAreaID的对象
	private Discover_TOP_Bean Discover_TOP_Bean;

	// 每个item基本的url，item的url=基本的url+item的id;
	private String itembaseurl = "http://api.m.mtime.cn/TopList/TopListDetails.api?pageIndex=1&topListId=";

	// ListView头部数据的对象
	private TopList topList;

	// ListView数据的url
	// ListView数据的集合数据，page是页数，默认是1
	int page = 1;
	private String ranklist_url = "http://api.m.mtime.cn/TopList/TopListOfAll.api?pageIndex=";

	// ListView的数据集合和适配器
	private List<TopLists> topLists;
	private Ranklist_Adapter adapter;

	/**
	 * 是否进入过的标示
	 */
	private boolean isenter = false;
	/**
	 * 下拉刷新的标示
	 * 
	 */
	// 是否是下拉刷新获取顶部数据的标示,默认不是
	private boolean isrefreshtop = false;
	// 是否是下拉刷新获取ListView数据的标示，默认不是
	private boolean isrefreshlist = false;
	/**
	 * 上拉加载更多的标示
	 */
	private boolean isloadmore = false;
	// 保存点击过item数据的key值
	private String IS_READ_RANKLIST = "is_read_ranklist";

	/**
	 * 两个构造器
	 * 
	 */
	public Discover_Ranklist(Context context) {
		super(context);
	}

	public Discover_Ranklist(Context context, TopList topList) {
		super(context);
		// 接受顶部数据
		this.topList = topList;
	}

	@Override
	public void initdata() {
		super.initdata();
		Log.e("TAG", "initdata排行榜");
		// 如果加载过，不再加载
		if (isenter) {
			return;
		}
		/**
		 * 设置Loading界面
		 */
		fl__main_refresh.setVisibility(View.GONE);
		// 启动动画
		fl__main_animation.setVisibility(View.VISIBLE);
		animationDrawable.start();
		// 在ListView的头视图中添加视图
		int childCount = discover_listview.headView.getChildCount();
		if (childCount == 2) {
			View view = View
					.inflate(context, R.layout.ranklist_add_heard, null);
			View time_top = view.findViewById(R.id.time_top);
			View chinese_top = view.findViewById(R.id.chinese_top);
			View total = view.findViewById(R.id.total);

			time_top.setOnClickListener(new MyOnclickListener());
			chinese_top.setOnClickListener(new MyOnclickListener());
			total.setOnClickListener(new MyOnclickListener());
			discover_listview.addSecondView(view);

		}

		// 设置ListView头部的数据
		inittopdata();
		// 联网获取ListView的数据
		getdatafromnet();
		// 更改进入过的标示
		isenter = true;
	}

	/**
	 * 设置ListView头部的数据
	 */
	private void inittopdata() {
		// 如果为空，返回
		if (topList == null) {
			isrefreshtop = false;
			return;
		}
		RequestQueue queue = Volley.newRequestQueue(context);
		// 消息请求
		ImageRequest imageRequest = new ImageRequest(topList.imageUrl,
				new Listener<Bitmap>() {

					@Override
					public void onResponse(Bitmap response) {
						// 设置ListView头部视图的数据
						discover_listview.settopview(response, topList.title);
						// 确保ListView的数据也加载完了(或加载失败了);
						if (isrefreshtop && !isrefreshlist) {
							// 刷新完成
							discover_listview
									.onFinishRefresh(getrandomAdvWord());
						}
						isrefreshtop = false;
					}
				}, 0, 0, Config.ARGB_4444, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {

						// 如果图片失败的话，也应该设置文字
						discover_listview.settopview(null, topList.title);

						Log.e("TAG", "加载排行顶部图片失败");

						if (isrefreshtop && !isrefreshlist) {
							// 刷新完成
							discover_listview
									.onFinishRefresh(getrandomAdvWord());

							Toast.makeText(context, "刷新排行榜顶部数据失败",
									Toast.LENGTH_SHORT).show();
						}
						isrefreshtop = false;
					}
				});
		StringRequest stringRequest = new StringRequest(topranklisturl,
				new Listener<String>() {

					@Override
					public void onResponse(String response) {
						// 解析顶部排行榜的数据
						progresstoprank(response);
					}
				}, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("TAG", "加载排行顶部的排行榜失败");

					}
				});
		queue.add(imageRequest);
		queue.add(stringRequest);
	}

	/**
	 * 联网获取ListView的数据
	 */
	private void getdatafromnet() {
		RequestQueue queue = Volley.newRequestQueue(context);

		// 判断是不是加载更多
		if (!isloadmore) {
			// 消息请求
			page = 1;
			String listdata_url = ranklist_url + page;
			StringRequest request = new StringRequest(Request.Method.GET,
					listdata_url, new Response.Listener<String>() {
						// 获取数据成功后会调
						@Override
						public void onResponse(String arg0) {

							// 隐藏Loading动画
							animationDrawable.stop();
							fl__main_animation.setVisibility(View.GONE);

							Log.e("TAG", "获取排行榜ListView中的数据成功了");

							// 解析数据
							processdata(arg0);

							// 确保ListView的顶部视图的数据加载完了(或加载失败了);
							if (isrefreshlist && !isrefreshtop) {
								discover_listview
										.onFinishRefresh(getrandomAdvWord());
							}
							isrefreshlist = false;
						}
					}, new Response.ErrorListener() {
						// 获取数据失败后会调
						@Override
						public void onErrorResponse(VolleyError arg0) {

							// 隐藏Loading动画
							animationDrawable.stop();
							fl__main_animation.setVisibility(View.GONE);
							fl__main_refresh.setVisibility(View.VISIBLE);
							fl__main_refresh
									.setOnClickListener(new OnClickListener() {

										@Override
										public void onClick(View v) {
											isenter = false;
											initdata();
										}
									});
							Log.e("TAG", "获取排行榜ListView中的数据失败了");

							if (isrefreshlist && !isrefreshtop) {
								discover_listview
										.onFinishRefresh(getrandomAdvWord());

								Toast.makeText(context, "刷新排行榜ListView数据失败",
										Toast.LENGTH_SHORT).show();
							}
							isrefreshlist = false;
						}
					});
			queue.add(request);
		} else {// 加载更多的请求
			// 消息请求
			page += 1;
			String listdata_url = ranklist_url + page;
			StringRequest request = new StringRequest(Request.Method.GET,
					listdata_url, new Response.Listener<String>() {

						// 获取数据成功后会调
						@Override
						public void onResponse(String arg0) {
							Log.e("TAG", "获取排行榜ListView中的更多数据成功了");
							// 解析数据
							processdata(arg0);

							// 这里应该可以不加,但去了总担心bug，
							if (isrefreshlist && !isrefreshtop) {
								discover_listview
										.onFinishRefresh(getrandomAdvWord());
							}
							isrefreshlist = false;
						}
					}, new Response.ErrorListener() {
						// 获取数据失败后会调
						@Override
						public void onErrorResponse(VolleyError arg0) {
							Log.e("TAG", "没有更多数据了");

							if (isrefreshlist && !isrefreshtop) {
								AdvWordList advwordlist = getrandomAdvWord();
								discover_listview.onFinishRefresh(advwordlist);
								Toast.makeText(context, "刷新排行榜ListView数据失败",
										Toast.LENGTH_SHORT).show();
							}
							isrefreshlist = false;
						}
					});
			queue.add(request);
		}
	}

	/**
	 * 解析数据
	 * 
	 * @param arg0
	 */
	protected void processdata(String arg0) {
		Gson gson = new Gson();
		Discover_Ranklist_Bean discover_Ranklist_Bean = gson.fromJson(arg0,
				Discover_Ranklist_Bean.class);
		// 第一次加载
		if (!isloadmore) {
			// 获取ListView的数据
			topLists = discover_Ranklist_Bean.topLists;
			adapter = new Ranklist_Adapter();
			discover_listview.setAdapter(adapter);
			// 设置刷新和加载更多的监听
			discover_listview.setOnRefreshListener(new MyOnRefreshListener());
			// 设置ListViw的点击事件
			discover_listview
					.setOnItemClickListener(new MyOnItemClickListener());
		} else {
			topLists.addAll(discover_Ranklist_Bean.topLists);
			adapter.notifyDataSetChanged();
			// 更新标签值
			isloadmore = false;
			discover_listview.onLoadingFinish();
		}

	}

	/**
	 * 解析顶部排行榜的数据
	 * 
	 * @param response
	 */

	protected void progresstoprank(String response) {
		Gson gson = new Gson();
		Discover_TOP_Bean = gson.fromJson(response, Discover_TOP_Bean.class);
	}

	/**
	 * 返回一个随机的刷视图的数据对象
	 * 
	 * @return
	 */
	protected AdvWordList getrandomAdvWord() {
		/**
		 * 设置刷新界面的数据
		 */
		Discover_Refresh_Bean disCoverrefreshdata = Constant
				.getDisCoverTopBean();
		if (disCoverrefreshdata != null) {
			List<AdvWordList> advWordList = disCoverrefreshdata.advWordList;
			int location = (int) (Math.random() * advWordList.size());
			AdvWordList wordList = advWordList.get(location);
			return wordList;
		} else {
			return null;
		}
	}

	/**
	 * 自定义刷新的接口
	 *
	 */
	class MyOnRefreshListener implements OnRefreshListener {

		/**
		 * 下拉刷新
		 */
		@Override
		public void onPullDownRefresh() {
			// 设置ListView头部的数据
			isrefreshtop = true;
			inittopdata();
			// 联网获取ListView的数据
			isrefreshlist = true;
			getdatafromnet();
		}

		/**
		 * 上拉加载更多
		 */
		@Override
		public void onLoadingMore() {
			// 更新加载更多的标示
			isloadmore = true;
			// 请求更多的ListView的数据
			getdatafromnet();
		}

	}

	/**
	 * 自定义视图的点击事件
	 */
	class MyOnclickListener implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.time_top:
				Intent intent1 = new Intent(context, Time_Top_Activity.class);
				if (Discover_TOP_Bean != null) {
					intent1.putExtra("pageSubAreaID",
							Discover_TOP_Bean.topList.get(0).pageSubAreaId);
				}
				context.startActivity(intent1);
				break;
			case R.id.chinese_top:
				Intent intent2 = new Intent(context, Chinese_Top_Activity.class);
				if (Discover_TOP_Bean != null) {
					intent2.putExtra("pageSubAreaID",
							Discover_TOP_Bean.topList.get(1).pageSubAreaId);
				}
				context.startActivity(intent2);
				break;
			case R.id.total:
				Intent intent3 = new Intent(context, Total_Activity.class);
				if (Discover_TOP_Bean != null) {
					intent3.putExtra("pageSubAreaID",
							Discover_TOP_Bean.topList.get(2).subTopList);
					Log.e("TAG", "进入");
				}
				context.startActivity(intent3);
				break;

			default:
				break;
			}
		}

	}

	/**
	 * 自定义item的点击事件
	 */
	class MyOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// 获取保存的点击过的Item的ID
			String is_read_ranklist = CacheUtils.getString(context,
					IS_READ_RANKLIST);

			/**
			 * 此时的position包含下拉刷新视图，必须减一 判断此次点击的是否保存过，没有的话，保存
			 */
			if (!is_read_ranklist.contains(topLists.get(position - 1).id + ",")) {
				is_read_ranklist += topLists.get(position - 1).id + ",";
				CacheUtils.setString(context, IS_READ_RANKLIST,
						is_read_ranklist);
				adapter.notifyDataSetChanged();
			}

			Intent intent = new Intent(context, Discover_Ranklist_item.class);
			String itemurl = itembaseurl + topLists.get(position - 1).id;
			intent.setData(Uri.parse(itemurl));
			context.startActivity(intent);

		}
	}

	/**
	 * 自定义ListView的Adapter
	 * 
	 */
	class Ranklist_Adapter extends BaseAdapter {

		@Override
		public int getCount() {
			return topLists.size();
		}

		@Override
		public Object getItem(int position) {
			return topLists.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder holder = null;
			if (convertView == null) {
				holder = new ViewHolder();
				convertView = View.inflate(context,
						R.layout.item_discover_ranklist, null);
				holder.title = (TextView) convertView
						.findViewById(R.id.tv_item_title);
				holder.describe = (TextView) convertView
						.findViewById(R.id.tv_item_describe);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			TopLists lists = topLists.get(position);

			holder.title.setText(lists.topListNameCn);
			holder.describe.setText(lists.summary);

			String is_read_ranklist = CacheUtils.getString(context,
					IS_READ_RANKLIST);
			if (is_read_ranklist.contains(lists.id + ",")) {
				holder.title.setTextColor(Color.GRAY);
			} else {
				holder.title.setTextColor(Color.BLACK);
			}
			return convertView;
		}
	}

	static class ViewHolder {
		TextView title;
		TextView describe;
	}
}
