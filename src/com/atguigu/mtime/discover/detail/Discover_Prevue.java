package com.atguigu.mtime.discover.detail;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.net.Uri;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.ImageView;
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
import com.atguigu.mtime.R;
import com.atguigu.mtime.discover.base.DiscoverDetailBase;
import com.atguigu.mtime.discover.bean.DisCoverTopBean.Top_Trailer;
import com.atguigu.mtime.discover.bean.Discover_Prevue_Bean;
import com.atguigu.mtime.discover.bean.Discover_Prevue_Bean.Prevue_trailers;
import com.atguigu.mtime.discover.bean.Discover_Refresh_Bean;
import com.atguigu.mtime.discover.bean.Discover_Refresh_Bean.AdvWordList;
import com.atguigu.mtime.utils.CacheUtils;
import com.atguigu.mtime.utils.Constant;
import com.atguigu.mtime.view.RefreshListView.OnRefreshListener;
import com.google.gson.Gson;

/**
 * 发现预告详情界面
 * 
 * @author lupeng
 *
 */
public class Discover_Prevue extends DiscoverDetailBase {

	// ListView头部数据的对象
	private Top_Trailer top_trailer;

	// 预告片详情界面ListView数据的Url
	private String prevue_rul = "http://api.m.mtime.cn/PageSubArea/TrailerList.api";

	// ListView的数据集合和适配器
	List<Prevue_trailers> trailers;
	private Prevue_Adapter adapter;

	// 内存缓存集合
	private LruCache<String, Bitmap> lruCache;

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
	// 保存点击过item数据的key值
	private String IS_READ_PREVUE = "is_read_prevue";

	/**
	 * 两个构造器
	 * 
	 */
	public Discover_Prevue(Context context) {
		super(context);
	}

	public Discover_Prevue(Context context, Top_Trailer top_trailer) {
		super(context);
		// 接受顶部数据
		this.top_trailer = top_trailer;
	}

	@Override
	public void initdata() {
		super.initdata();
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
		/**
		 * 设置ListView是否有脚视图
		 */
		discover_listview.setisfootview(false);
		/**
		 * 设置是否显示播放的按钮,并设置监听
		 */
		discover_listview.setplayvisibility(View.VISIBLE);
		ImageView playview = discover_listview.getplayview();
		// 设置头部播放按钮的点击监听
		playview.setOnClickListener(new OnClickListener() {

			@Override
			public void onClick(View v) {
				// 调用系统的安装了的播放器来播放视频
				Intent intent = new Intent();
				intent.setAction(Intent.ACTION_VIEW);
				intent.setDataAndType(Uri.parse(top_trailer.hightUrl),
						"video/*");
				context.startActivity(intent);
			}
		});
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
		if (top_trailer == null) {
			isrefreshtop = false;
			return;
		}
		RequestQueue queue = Volley.newRequestQueue(context);
		// 消息请求
		ImageRequest imageRequest = new ImageRequest(top_trailer.imageUrl,
				new Listener<Bitmap>() {

					@Override
					public void onResponse(Bitmap response) {
						// 设置ListView头部视图的数据
						discover_listview.settopview(response,
								top_trailer.title);

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
						discover_listview.settopview(null, top_trailer.title);

						Log.e("TAG", "加载预告片顶部图片失败");

						if (isrefreshtop && !isrefreshlist) {
							// 刷新完成
							discover_listview
									.onFinishRefresh(getrandomAdvWord());

							Toast.makeText(context, "刷新预告片顶部数据失败",
									Toast.LENGTH_SHORT).show();
						}
						isrefreshtop = false;
					}
				});
		queue.add(imageRequest);
	}

	/**
	 * 联网获取ListView的数据
	 */
	private void getdatafromnet() {
		RequestQueue queue = Volley.newRequestQueue(context);
		StringRequest request = new StringRequest(Request.Method.GET,
				prevue_rul, new Response.Listener<String>() {
					// 获取数据成功后会调
					@Override
					public void onResponse(String arg0) {
						// 隐藏Loading动画
						animationDrawable.stop();
						fl__main_animation.setVisibility(View.GONE);

						Log.e("TAG", "获取预告片ListView的数据成功了");
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
						Log.e("TAG", "获取预告片ListView的数据失败了");

						if (isrefreshlist && !isrefreshtop) {
							discover_listview
									.onFinishRefresh(getrandomAdvWord());

							Toast.makeText(context, "刷新预告片ListView数据失败",
									Toast.LENGTH_SHORT).show();
						}
						isrefreshlist = false;
					}
				});
		queue.add(request);
	}

	/**
	 * 解析ListView的json数据
	 * 
	 * @param arg0
	 */
	protected void processdata(String arg0) {
		Gson gson = new Gson();
		Discover_Prevue_Bean prevue_Bean = gson.fromJson(arg0,
				Discover_Prevue_Bean.class);
		// 得到ListView数据的集合
		trailers = prevue_Bean.trailers;
		adapter = new Prevue_Adapter();
		discover_listview.setAdapter(adapter);
		// 设置刷新的监听
		discover_listview.setOnRefreshListener(new MyOnRefreshListener());
		// 设置ListViw的点击事件
		discover_listview.setOnItemClickListener(new MyOnItemClickListener());
	}

	/**
	 * 返回一个随机的刷视图的数据对象
	 * 
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
		 * 沒有上拉加载更多
		 */
		@Override
		public void onLoadingMore() {
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
			String is_read_prevue = CacheUtils.getString(context,
					IS_READ_PREVUE);

			/**
			 * 此时的position包含下拉刷新视图，必须减一 判断此次点击的是否保存过，没有的话，保存
			 */
			if (!is_read_prevue.contains(trailers.get(position - 1).id + ",")) {
				is_read_prevue += trailers.get(position - 1).id + ",";
				CacheUtils.setString(context, IS_READ_PREVUE, is_read_prevue);
				adapter.notifyDataSetChanged();
			}
			// 调用系统的安装了的播放器来播放视频
			Intent intent = new Intent();
			intent.setAction(Intent.ACTION_VIEW);
			intent.setDataAndType(
					Uri.parse(trailers.get(position - 1).hightUrl), "video/*");
			context.startActivity(intent);

		}
	}

	/**
	 * 自定义ListView的Adapter
	 * 
	 */
	class Prevue_Adapter extends BaseAdapter {
		// 在创建适配器的时候，创建内存的缓存集合LruCache
		public Prevue_Adapter() {
			// 得到运行时最大的内存
			int maxMemory = (int) Runtime.getRuntime().maxMemory();
			lruCache = new LruCache<String, Bitmap>(maxMemory / 8) {
				@Override
				protected int sizeOf(String key, Bitmap value) {

					return value.getRowBytes() * value.getHeight();
				}
			};
		}

		@Override
		public int getCount() {
			return trailers.size();
		}

		@Override
		public Object getItem(int position) {
			return trailers.get(position);
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
						R.layout.item_discover_prevue, null);
				holder.IVicon = (ImageView) convertView
						.findViewById(R.id.iv_item_icon);
				holder.prevue_title = (TextView) convertView
						.findViewById(R.id.tv_item_title);
				holder.privue_describe = (TextView) convertView
						.findViewById(R.id.tv_item_describe);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			Prevue_trailers prevue_trailers = trailers.get(position);
			// 获取图片的地址
			String imagepath = prevue_trailers.coverImg;
			// 根据内存中有无，选择设置bitmap的方式(1.直接内存中获取，2.联网获取)
			Bitmap bitmap = lruCache.get(imagepath);
			if (bitmap != null) {
				holder.IVicon.setImageBitmap(bitmap);
			} else {
				// 设置tag值，防止闪图
				holder.IVicon.setTag(imagepath);
				// 联网请求，设置图片
				setImageView(holder.IVicon, imagepath);
			}

			holder.prevue_title.setText(prevue_trailers.movieName);
			holder.privue_describe.setText(prevue_trailers.summary);

			String is_read_prevue = CacheUtils.getString(context,
					IS_READ_PREVUE);
			if (is_read_prevue.contains(prevue_trailers.id + ",")) {
				holder.prevue_title.setTextColor(Color.GRAY);
			} else {
				holder.prevue_title.setTextColor(Color.BLACK);
			}
			return convertView;
		}
	}

	static class ViewHolder {
		ImageView IVicon;
		TextView prevue_title;
		TextView privue_describe;
	}

	/**
	 * 联网请求item中的图片，并设置到对应的item中
	 * 
	 * @param iVicon
	 * @param imagepath
	 */
	public void setImageView(final ImageView iVicon, final String imagepath) {
		// 每次请求前把图片设置为默认的(不然复用的时候会显示上次加载过的图片)
		iVicon.setImageResource(R.drawable.img_default_300x200);

		// 说明已经不是最新的请求地址了，就没必要请求了
		String tagurl = (String) iVicon.getTag();
		if (tagurl != imagepath) {
			return;
		}
		RequestQueue queue = Volley.newRequestQueue(context);
		// 消息请求
		ImageRequest imageRequest = new ImageRequest(imagepath,
				new Listener<Bitmap>() {

					@Override
					public void onResponse(Bitmap response) {
						Log.e("TAG", "获取预告片ListView的图片成功");
						// 在设置之前也判断一下
						String tagurl = (String) iVicon.getTag();
						if (tagurl != imagepath) {
							return;
						}
						// 设置图片到对应的ImageView中
						lruCache.put(imagepath, response);
						// 保存到内存中
						iVicon.setImageBitmap(response);

					}
				}, 0, 0, Config.ARGB_4444, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("TAG", "获取预告片ListView的图片失败");
					}
				});
		queue.add(imageRequest);
	}
}
