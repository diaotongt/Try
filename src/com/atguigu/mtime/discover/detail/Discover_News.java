package com.atguigu.mtime.discover.detail;

import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
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
import com.atguigu.mtime.Discover_news_item;
import com.atguigu.mtime.R;
import com.atguigu.mtime.discover.base.DiscoverDetailBase;
import com.atguigu.mtime.discover.bean.DisCoverTopBean.Top_News;
import com.atguigu.mtime.discover.bean.Discover_News_Bean;
import com.atguigu.mtime.discover.bean.Discover_News_Bean.NewsList;
import com.atguigu.mtime.discover.bean.Discover_Refresh_Bean;
import com.atguigu.mtime.discover.bean.Discover_Refresh_Bean.AdvWordList;
import com.atguigu.mtime.utils.CacheUtils;
import com.atguigu.mtime.utils.Constant;
import com.atguigu.mtime.view.RefreshListView.OnRefreshListener;
import com.google.gson.Gson;

/**
 * 发现新闻详情界面
 * 
 * @author lupeng
 *
 */
public class Discover_News extends DiscoverDetailBase {

	// 每个item基本的url，item的url=基本的url+item的id;
	private String itembaseurl = "http://api.m.mtime.cn/News/Detail.api?newsId=";

	// ListView头部数据的对象
	private Top_News topnews;

	// ListView数据的url
	// ListView数据的集合数据，page是页数，默认是1
	int page = 1;
	private String news_url = "http://api.m.mtime.cn/News/NewsList.api?pageIndex=";

	// ListView的数据集合和适配器
	private List<NewsList> newsList;
	private NewsAdapter adapter;

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
	/**
	 * 上拉加载更多的标示
	 * 
	 * @param context
	 */
	private boolean isloadmore = false;

	// 保存点击过item数据的key值
	private String IS_READ_NEWS = "is_read_news";

	/**
	 * 两个构造器
	 * 
	 */
	public Discover_News(Context context) {
		super(context);
	}

	public Discover_News(Context context, Top_News top_news) {
		super(context);
		// 接受顶部数据
		this.topnews = top_news;
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

		// 在ListView的头视图中添加视图
		int childCount = discover_listview.headView.getChildCount();
		View view = null;
		if (childCount == 2) {
			view = View.inflate(context, R.layout.news_add_heard, null);
			discover_listview.addSecondView(view);
			/**
			 * 添加视图的点击事件
			 */
			// 內地票房榜
			TextView main_land = (TextView) view.findViewById(R.id.main_land);
			// 全球票房榜
			TextView globle = (TextView) view.findViewById(R.id.globle);
			main_land.setOnClickListener(new MyOnclick());
			globle.setOnClickListener(new MyOnclick());

		}

		// 设置ListView头部的数据
		inittopdata();

		// 联网获取新闻ListView的数据
		getdatafromnet();

		// 更改进入过的标示
		isenter = true;
	}

	/**
	 * 设置ListView头部的数据
	 */
	private void inittopdata() {
		// 如果为空，返回
		if (topnews == null) {
			Log.e("TAG", "topnews == null");
			isrefreshtop = false;
			return;
		}
		RequestQueue queue = Volley.newRequestQueue(context);
		// 消息请求
		ImageRequest imageRequest = new ImageRequest(topnews.imageUrl,
				new Listener<Bitmap>() {

					@Override
					public void onResponse(Bitmap response) {
						// 设置ListView头部视图的数据
						discover_listview.settopview(response, topnews.title);
						Log.e("TAG", "settopview");
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
						discover_listview.settopview(null, topnews.title);

						Log.e("TAG", "加载新闻顶部图片失败");

						if (isrefreshtop && !isrefreshlist) {
							// 刷新完成
							discover_listview
									.onFinishRefresh(getrandomAdvWord());

							Toast.makeText(context, "刷新新闻顶部数据失败",
									Toast.LENGTH_SHORT).show();
						}
						isrefreshtop = false;
					}
				});
		queue.add(imageRequest);
	}

	/**
	 * 联网获取新闻ListView的数据
	 */
	private void getdatafromnet() {
		RequestQueue queue = Volley.newRequestQueue(context);

		// 判断是不是加载更多
		if (!isloadmore) {
			page = 1;
			String listdata_url = news_url + page;
			StringRequest request = new StringRequest(Request.Method.GET,
					listdata_url, new Response.Listener<String>() {
						// 获取数据成功后会调
						@Override
						public void onResponse(String arg0) {

							// 隐藏Loading动画
							animationDrawable.stop();
							fl__main_animation.setVisibility(View.GONE);

							Log.e("TAG", "获取新闻ListView中的数据成功了");

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
							Log.e("TAG", "获取新闻ListView中的数据失败了");

							if (isrefreshlist && !isrefreshtop) {
								discover_listview
										.onFinishRefresh(getrandomAdvWord());

								Toast.makeText(context, "刷新新闻ListView数据失败",
										Toast.LENGTH_SHORT).show();
							}
							isrefreshlist = false;
						}
					});
			queue.add(request);
		} else {
			// 消息请求
			page += 1;
			final String listdata_url = news_url + page;

			StringRequest request = new StringRequest(Request.Method.GET,
					listdata_url, new Response.Listener<String>() {

						// 获取数据成功后会调
						@Override
						public void onResponse(String arg0) {

							Log.e("TAG", "获取新闻ListView中的更多数据成功了");
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
	 * 解析ListView的数据
	 * 
	 * @param arg0
	 */
	protected void processdata(String arg0) {
		Gson gson = new Gson();
		Discover_News_Bean discover_News_Bean = gson.fromJson(arg0,
				Discover_News_Bean.class);
		if (!isloadmore) {
			// 得到ListView的数据
			newsList = discover_News_Bean.newsList;
			adapter = new NewsAdapter();
			discover_listview.setAdapter(adapter);
			// 设置刷新和加载更多的监听
			discover_listview.setOnRefreshListener(new MyOnRefreshListener());
			// 设置item的点击事件
			discover_listview
					.setOnItemClickListener(new MyOnItemClickListener());
		} else {
			newsList.addAll(discover_News_Bean.newsList);
			adapter.notifyDataSetChanged();
			// 更新标签值
			isloadmore = false;
			discover_listview.onLoadingFinish();
		}
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
		List<AdvWordList> advWordList = disCoverrefreshdata.advWordList;
		if (disCoverrefreshdata != null) {
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
	 * 自定义item的点击事件
	 */
	class MyOnItemClickListener implements OnItemClickListener {

		@Override
		public void onItemClick(AdapterView<?> parent, View view, int position,
				long id) {
			// 获取保存的点击过的Item的ID
			String readed_news = CacheUtils.getString(context, IS_READ_NEWS);

			/**
			 * 此时的position包含下拉刷新视图，必须减一 判断此次点击的是否保存过，没有的话，保存
			 */
			if (!readed_news.contains(newsList.get(position - 1).id + ",")) {
				readed_news += newsList.get(position - 1).id + ",";
				CacheUtils.setString(context, IS_READ_NEWS, readed_news);
				adapter.notifyDataSetChanged();
			}

			// 进入item的详情界面
			NewsList list = newsList.get(position - 1);
			String itemurl = itembaseurl + list.id;
			Intent intent = new Intent(context, Discover_news_item.class);
			intent.setData(Uri.parse(itemurl));
			context.startActivity(intent);

		}
	}

	/**
	 * 自定义点击监听
	 */
	class MyOnclick implements OnClickListener {

		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.main_land:// 内地排行榜
				Toast.makeText(context, "内地排行榜", Toast.LENGTH_SHORT).show();
				break;
			case R.id.globle:// 全球排行榜
				Toast.makeText(context, "全球排行榜", Toast.LENGTH_SHORT).show();
				break;
			default:
				break;
			}
		}

	}

	/**
	 * 自定义ListView的Adapter
	 * 
	 */

	class NewsAdapter extends BaseAdapter {
		// 在创建适配器的时候，创建内存的缓存集合LruCache
		public NewsAdapter() {
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
			return newsList.size();
		}

		@Override
		public Object getItem(int position) {

			return newsList.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public int getItemViewType(int position) {
			return newsList.get(position).type;
		}

		@Override
		public int getViewTypeCount() {
			return 3;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			int type = getItemViewType(position);
			ImageViewHolder imageviewholder = null;
			ImagesViewHolder imagesviewholder = null;
			VideoViewHolder videoviewholder = null;
			if (convertView == null) {
				switch (type) {

				case 0:
					imageviewholder = new ImageViewHolder();

					convertView = View.inflate(context,
							R.layout.item_discover_news_type0_image, null);

					imageviewholder.tvSingle = (TextView) convertView
							.findViewById(R.id.tv_item_image_single);
					imageviewholder.ivIcon = (ImageView) convertView
							.findViewById(R.id.iv_item_image_icon);
					imageviewholder.tvTitle = (TextView) convertView
							.findViewById(R.id.tv_item_image_title);
					imageviewholder.tvContent = (TextView) convertView
							.findViewById(R.id.tv_item_image_content);
					imageviewholder.tvTime = (TextView) convertView
							.findViewById(R.id.tv_item_image_time);
					imageviewholder.tvComment = (TextView) convertView
							.findViewById(R.id.tv_item_image_comment);

					convertView.setTag(imageviewholder);
					break;

				case 1:
					imagesviewholder = new ImagesViewHolder();

					convertView = View.inflate(context,
							R.layout.item_discover_news_type1_images, null);

					imagesviewholder.tvTitle = (TextView) convertView
							.findViewById(R.id.tv_item_images_title);
					imagesviewholder.ivIcon1 = (ImageView) convertView
							.findViewById(R.id.iv_item_images_icon1);
					imagesviewholder.ivIcon2 = (ImageView) convertView
							.findViewById(R.id.iv_item_images_icon2);
					imagesviewholder.ivIcon3 = (ImageView) convertView
							.findViewById(R.id.iv_item_images_icon3);
					imagesviewholder.tvTime = (TextView) convertView
							.findViewById(R.id.tv_item_images_time);
					imagesviewholder.tvComment = (TextView) convertView
							.findViewById(R.id.tv_item_images_comment);

					convertView.setTag(imagesviewholder);
					break;

				case 2:
					videoviewholder = new VideoViewHolder();

					convertView = View.inflate(context,
							R.layout.item_discover_news_type2_video, null);

					videoviewholder.ivIcon = (ImageView) convertView
							.findViewById(R.id.iv_item_video_icon);
					videoviewholder.tvTitle = (TextView) convertView
							.findViewById(R.id.tv_item_video_title);
					videoviewholder.tvContent = (TextView) convertView
							.findViewById(R.id.tv_item_video_content);
					videoviewholder.tvTime = (TextView) convertView
							.findViewById(R.id.tv_item_video_time);
					videoviewholder.tvComment = (TextView) convertView
							.findViewById(R.id.tv_item_video_comment);

					convertView.setTag(videoviewholder);
					break;

				default:
					break;
				}
			} else {
				switch (type) {
				case 0:
					imageviewholder = (ImageViewHolder) convertView.getTag();
					break;
				case 1:
					imagesviewholder = (ImagesViewHolder) convertView.getTag();
					break;
				case 2:
					videoviewholder = (VideoViewHolder) convertView.getTag();
					break;
				default:
					break;
				}
			}
			// 获取每条item的数据
			NewsList list = newsList.get(position);

			switch (type) {
			case 0:
				String tag = list.tag;
				if ("无".equals(tag)) {
					imageviewholder.tvSingle.setVisibility(View.GONE);
				} else {
					imageviewholder.tvSingle.setVisibility(View.VISIBLE);
				}
				String imagepath = list.image;
				Bitmap bitmap = lruCache.get(imagepath);

				if (bitmap != null) {
					Drawable drawable = new BitmapDrawable(bitmap);
					imageviewholder.ivIcon.setBackgroundDrawable(drawable);
				} else {
					// 设置tag值，防止闪图
					imageviewholder.ivIcon.setTag(imagepath);
					// 联网请求，设置图片
					setImageView(imageviewholder.ivIcon, imagepath, 0);
				}
				imageviewholder.tvTitle.setText(list.title);
				imageviewholder.tvContent.setText(list.title2);
				long millis = list.publishTime;
				imageviewholder.tvTime.setText(gettime(millis));
				imageviewholder.tvComment.setText("评论 " + list.commentCount);
				break;
			case 1:

				imagesviewholder.tvTitle.setText(list.title);
				if (list.images.size() == 0) {
					break;
				}
				String images1 = list.images.get(0).url1;
				Bitmap bitmaps1 = lruCache.get(images1);
				if (bitmaps1 != null) {
					Drawable drawable1 = new BitmapDrawable(bitmaps1);
					imagesviewholder.ivIcon1.setBackgroundDrawable(drawable1);
				} else {
					// 设置tag值，防止闪图
					imagesviewholder.ivIcon1.setTag(images1);
					// 联网请求，设置图片
					setImageView(imagesviewholder.ivIcon1, images1, 1);
				}

				String images2 = list.images.get(1).url1;
				Bitmap bitmaps2 = lruCache.get(images2);
				if (bitmaps2 != null) {
					Drawable drawable2 = new BitmapDrawable(bitmaps2);
					imagesviewholder.ivIcon2.setBackgroundDrawable(drawable2);
				} else {
					// 设置tag值，防止闪图
					imagesviewholder.ivIcon2.setTag(images2);
					// 联网请求，设置图片
					setImageView(imagesviewholder.ivIcon2, images2, 1);
				}

				String images3 = list.images.get(2).url1;
				Bitmap bitmaps3 = lruCache.get(images3);
				if (bitmaps3 != null) {
					Drawable drawable3 = new BitmapDrawable(bitmaps3);
					imagesviewholder.ivIcon3.setBackgroundDrawable(drawable3);
				} else {
					// 设置tag值，防止闪图
					imagesviewholder.ivIcon3.setTag(images3);
					// 联网请求，设置图片
					setImageView(imagesviewholder.ivIcon3, images3, 1);
				}
				long time2 = list.publishTime;
				Log.e("TAG", gettime(time2));
				imagesviewholder.tvTime.setText(gettime(time2));
				imagesviewholder.tvComment.setText("评论 " + list.commentCount);

				break;
			case 2:
				String imagepath3 = list.image;
				Bitmap bitmap3 = lruCache.get(imagepath3);

				if (bitmap3 != null) {
					Drawable drawable3 = new BitmapDrawable(bitmap3);
					videoviewholder.ivIcon.setBackgroundDrawable(drawable3);
				} else {
					// 设置tag值，防止闪图
					videoviewholder.ivIcon.setTag(imagepath3);
					// 联网请求，设置图片
					setImageView(videoviewholder.ivIcon, imagepath3, 2);
				}
				videoviewholder.tvTitle.setText(list.title);
				videoviewholder.tvContent.setText(list.title2);
				long time3 = list.publishTime;
				Log.e("TAG", gettime(time3));
				videoviewholder.tvTime.setText(gettime(time3));
				Log.e("TAG", gettime(time3));
				videoviewholder.tvComment.setText("评论 " + list.commentCount);
				break;

			default:
				break;
			}
			String readed_item = CacheUtils.getString(context, IS_READ_NEWS);
			switch (type) {
			case 0:
				// 查看是否保存过，保存过的话就设置为灰色
				if (readed_item.contains(list.id + ",")) {
					imageviewholder.tvTitle.setTextColor(Color.GRAY);
				} else {
					imageviewholder.tvTitle.setTextColor(Color.BLACK);
				}
				break;
			case 1:
				// 查看是否保存过，保存过的话就设置为灰色
				if (readed_item.contains(list.id + ",")) {
					imagesviewholder.tvTitle.setTextColor(Color.GRAY);
				} else {
					imagesviewholder.tvTitle.setTextColor(Color.BLACK);
				}
				break;

			case 2:

				// 查看是否保存过，保存过的话就设置为灰色
				if (readed_item.contains(list.id + ",")) {
					videoviewholder.tvTitle.setTextColor(Color.GRAY);
				} else {
					videoviewholder.tvTitle.setTextColor(Color.BLACK);
				}
				break;

			default:
				break;
			}

			return convertView;
		}
	}

	/**
	 * 类型0
	 * 
	 * @author Administrator
	 *
	 */
	static class ImageViewHolder {
		TextView tvSingle;// 独家
		ImageView ivIcon;
		TextView tvTitle;
		TextView tvContent;
		TextView tvTime;
		TextView tvComment;

	}

	/**
	 * 类型1
	 * 
	 * @author Administrator
	 *
	 */
	static class ImagesViewHolder {
		TextView tvTitle;
		ImageView ivIcon1;
		ImageView ivIcon2;
		ImageView ivIcon3;
		TextView tvTime;
		TextView tvComment;
	}

	/**
	 * 类型2
	 * 
	 * @author Administrator
	 *
	 */
	static class VideoViewHolder {
		ImageView ivIcon;
		TextView tvTitle;
		TextView tvContent;
		TextView tvTime;
		TextView tvComment;
	}

	public void setImageView(final ImageView ivIcon, final String imagepath,
			int type) {
		// 每次请求前把图片设置为默认的(不然复用的时候会显示上次加载过的图片)

		switch (type) {
		case 0:
			ivIcon.setBackgroundResource(R.drawable.img_default_45x45);
			break;
		case 1:
			ivIcon.setBackgroundResource(R.drawable.img_default_300x200);
			break;
		case 2:
			ivIcon.setBackgroundResource(R.drawable.img_default_45x45);
			break;
		default:
			break;
		}

		// 说明已经不是最新的请求地址了，就没必要请求了
		String tagurl = (String) ivIcon.getTag();
		if (tagurl != imagepath) {
			return;
		}
		RequestQueue queue = Volley.newRequestQueue(context);
		// 消息请求
		ImageRequest imageRequest = new ImageRequest(imagepath,
				new Listener<Bitmap>() {

					@Override
					public void onResponse(Bitmap response) {
						Log.e("TAG", "获取新闻ListView的图片成功");
						// 在设置之前也判断一下
						String tagurl = (String) ivIcon.getTag();
						if (tagurl != imagepath) {
							return;
						}
						// 保存到内存中
						lruCache.put(imagepath, response);

						// 设置图片到对应的ImageView中
						Drawable drawable = new BitmapDrawable(response);
						ivIcon.setBackgroundDrawable(drawable);

					}
				}, 0, 0, Config.ARGB_4444, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("TAG", "获取新闻ListView的图片失败");
					}
				});
		queue.add(imageRequest);
	}

	/**
	 * 变long型数据为正常的时间格式
	 */
	public String gettime(long time) {

		long millis = System.currentTimeMillis();
		long resultTime = millis + 8 * 3600000 - time * 1000;

		int minute = (int) (resultTime / 1000 / 60);
		int hour = (int) (resultTime / 1000 / 3600);
		int day = (int) (resultTime / 1000 / 3600 / 24);
		if (day != 0) {
			return day + "天之前";
		} else if (hour != 0) {
			return hour + "小时之前";
		} else if (minute != 0) {
			return minute + "分钟之前";
		} else {
			return "刚刚发布";
		}
	}

}
