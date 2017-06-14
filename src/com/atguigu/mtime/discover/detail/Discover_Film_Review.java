package com.atguigu.mtime.discover.detail;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.AdapterView.OnItemClickListener;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.atguigu.mtime.Discover_film_item;
import com.atguigu.mtime.Discover_news_item;
import com.atguigu.mtime.R;
import com.atguigu.mtime.discover.base.DiscoverDetailBase;
import com.atguigu.mtime.discover.bean.DisCoverTopBean.Top_Review;
import com.atguigu.mtime.discover.bean.Discover_Film_Review_Bean;
import com.atguigu.mtime.discover.bean.Discover_Film_Review_Bean.RelatedObj;
import com.atguigu.mtime.discover.bean.Discover_News_Bean.NewsList;
import com.atguigu.mtime.discover.bean.Discover_Refresh_Bean.AdvWordList;
import com.atguigu.mtime.discover.bean.Discover_Refresh_Bean;
import com.atguigu.mtime.utils.CacheUtils;
import com.atguigu.mtime.utils.Constant;
import com.atguigu.mtime.view.RefreshListView.OnRefreshListener;

/**
 * 发现影评详情界面
 * 
 * @author lupeng
 *
 */
public class Discover_Film_Review extends DiscoverDetailBase {

	// 每条item的基本url,item的url=基本的url+item的id;
	private String itembaseurl = "http://api.m.mtime.cn/Review/Detail.api?reviewId=";

	// ListView头部数据的对象
	private Top_Review top_review;

	// ListView数据的url
	private String film_review_url = "http://api.m.mtime.cn/MobileMovie/Review.api?needTop=false";

	// ListView的数据集合和适配器
	private List<Discover_Film_Review_Bean> data = new ArrayList<Discover_Film_Review_Bean>();
	private Film_Adapter adapter;

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
	private String IS_READ_FILE_REVIEW = "is_read_file_review";

	/**
	 * 两个构造器
	 * 
	 */
	public Discover_Film_Review(Context context) {
		super(context);
	}

	public Discover_Film_Review(Context context, Top_Review top_review) {
		super(context);
		// 接受顶部数据
		this.top_review = top_review;
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
		if (top_review == null) {
			isrefreshtop = false;
			return;
		}
		RequestQueue queue = Volley.newRequestQueue(context);
		// 消息请求
		ImageRequest imageRequest = new ImageRequest(top_review.imageUrl,
				new Listener<Bitmap>() {

					@Override
					public void onResponse(Bitmap response) {

						// 设置ListView头部视图的数据
						discover_listview
								.settopview(response, top_review.title);
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
						discover_listview.settopview(null, top_review.title);

						Log.e("TAG", "加载影评顶部图片失败");
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
				film_review_url, new Response.Listener<String>() {
					// 获取数据成功后会调
					@Override
					public void onResponse(String arg0) {

						// 隐藏Loading动画
						animationDrawable.stop();
						fl__main_animation.setVisibility(View.GONE);
						// fl__main_refresh
						// .setOnClickListener(new OnClickListener() {
						//
						// @Override
						// public void onClick(View v) {
						// isenter = false;
						// initdata();
						// }
						// });

						Log.e("TAG", "获取影评ListView中的数据成功了");
						// 解析数据
						try {
							processdata(arg0);
							// 确保ListView的顶部视图的数据加载完了(或加载失败了);
							if (isrefreshlist && !isrefreshtop) {
								discover_listview
										.onFinishRefresh(getrandomAdvWord());
							}
							isrefreshlist = false;
						} catch (Exception e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
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
						Log.e("TAG", "获取影评ListView中的数据失败了");

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
	 * 解析数据
	 * 
	 * @param arg0
	 * @throws JSONException
	 */
	protected void processdata(String arg0) throws Exception {

		JSONArray jsonArray = new JSONArray(arg0);
		int length = jsonArray.length();
		/**
		 * 手动解析
		 */
		for (int i = 0; i < length; i++) {

			Discover_Film_Review_Bean discover_film_review_bean = new Discover_Film_Review_Bean();

			JSONObject jsonDiscover_Film_Review_Bean = jsonArray
					.optJSONObject(i);

			discover_film_review_bean.id = jsonDiscover_Film_Review_Bean
					.optInt("id");
			discover_film_review_bean.nickname = jsonDiscover_Film_Review_Bean
					.optString("nickname");
			discover_film_review_bean.rating = jsonDiscover_Film_Review_Bean
					.optString("rating");
			/**
			 * 这里需要注意一下
			 */
			JSONObject JsonRelatedObj = jsonDiscover_Film_Review_Bean
					.optJSONObject("relatedObj");
			RelatedObj relatedobj = discover_film_review_bean.new RelatedObj();
			relatedobj.id = JsonRelatedObj.optInt("id");
			relatedobj.image = JsonRelatedObj.optString("image");
			relatedobj.rating = JsonRelatedObj.optString("rating");
			relatedobj.title = JsonRelatedObj.optString("title");
			relatedobj.type = JsonRelatedObj.optInt("type");
			discover_film_review_bean.relatedObj = relatedobj;

			discover_film_review_bean.summary = jsonDiscover_Film_Review_Bean
					.optString("summary");
			discover_film_review_bean.title = jsonDiscover_Film_Review_Bean
					.optString("title");
			discover_film_review_bean.userImage = jsonDiscover_Film_Review_Bean
					.optString("userImage");

			data.add(discover_film_review_bean);
		}

		adapter = new Film_Adapter();
		discover_listview.setAdapter(adapter);
		// 设置刷新的监听
		discover_listview.setOnRefreshListener(new MyOnRefreshListener());

		// 设置ListViw的点击事件
		discover_listview.setOnItemClickListener(new MyOnItemClickListener());
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
		 * 没有上拉加载更多
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
			String readed_file_review = CacheUtils.getString(context,
					IS_READ_FILE_REVIEW);

			/**
			 * 此时的position包含下拉刷新视图，必须减一 判断此次点击的是否保存过，没有的话，保存
			 */
			if (!readed_file_review.contains(data.get(position - 1).id + ",")) {
				readed_file_review += data.get(position - 1).id + ",";
				CacheUtils.setString(context, IS_READ_FILE_REVIEW,
						readed_file_review);
				adapter.notifyDataSetChanged();
			}
			// 进入item的详情界面
			Discover_Film_Review_Bean discover_film_review_bean = data
					.get(position - 1);
			String itemurl = itembaseurl + discover_film_review_bean.id;
			Intent intent = new Intent(context, Discover_film_item.class);
			intent.setData(Uri.parse(itemurl));
			context.startActivity(intent);

		}
	}

	/**
	 * 自定义ListView的Adapter
	 * 
	 */
	class Film_Adapter extends BaseAdapter {
		// 在创建适配器的时候，创建内存的缓存集合LruCache
		public Film_Adapter() {
			// 得到运行时最大的内存
			int maxMemory = (int) Runtime.getRuntime().maxMemory();
			lruCache = new LruCache<String, Bitmap>(maxMemory / 4) {
				@Override
				protected int sizeOf(String key, Bitmap value) {

					return value.getRowBytes() * value.getHeight();
				}
			};
		}

		@Override
		public int getCount() {
			return data.size();
		}

		@Override
		public Object getItem(int position) {

			return data.get(position);
		}

		@Override
		public long getItemId(int position) {
			return 0;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			ViewHolder viewholder = null;
			if (convertView == null) {

				viewholder = new ViewHolder();
				convertView = View.inflate(context,
						R.layout.item_discover_film_review, null);

				viewholder.tvtitle = (TextView) convertView
						.findViewById(R.id.tv_item_title);
				viewholder.tvdescribe = (TextView) convertView
						.findViewById(R.id.tv_item_describe);
				viewholder.ivsmall = (ImageView) convertView
						.findViewById(R.id.iv_item_small);
				viewholder.tvnickname = (TextView) convertView
						.findViewById(R.id.tv_item_nickname);
				viewholder.tvmovewname = (TextView) convertView
						.findViewById(R.id.tv_item_movewname);
				viewholder.tvgrade = (TextView) convertView
						.findViewById(R.id.tv_item_grade);
				viewholder.ivicon = (ImageView) convertView
						.findViewById(R.id.iv_item_icon);

				convertView.setTag(viewholder);
			} else {
				viewholder = (ViewHolder) convertView.getTag();
			}

			Discover_Film_Review_Bean discover_Film_Review_Bean = data
					.get(position);

			viewholder.tvtitle.setText(discover_Film_Review_Bean.title);
			viewholder.tvdescribe.setText(discover_Film_Review_Bean.summary
					.trim());
			viewholder.tvnickname.setText(discover_Film_Review_Bean.nickname
					+ "-评");
			viewholder.tvmovewname
					.setText(discover_Film_Review_Bean.relatedObj.title);

			// 如果分数是0的话，不显示
			if ("0.0".equals(discover_Film_Review_Bean.rating)) {
				viewholder.tvgrade.setText(null);
			} else {
				viewholder.tvgrade.setText(discover_Film_Review_Bean.rating);
			}
			/**
			 * 这里暂时用了两个请求图片的网络，用一个有bug,闪图，不明白原因，
			 */
			// 根据内存中有无，选择设置bitmap的方式(1.直接内存中获取，2.联网获取)
			String smallurl = discover_Film_Review_Bean.userImage;
			Bitmap bitmap = lruCache.get(smallurl);
			if (bitmap != null) {
				Drawable drawable = new BitmapDrawable(bitmap);
				viewholder.ivsmall.setBackgroundDrawable(drawable);
			} else {
				// 设置tag值，防止闪图
				viewholder.ivsmall.setTag(smallurl);
				// 联网请求，设置图片
				setImageSmall(viewholder.ivsmall, smallurl);
				// setImageIcon(viewholder.ivsmall, smallurl);
			}

			String iconurl = discover_Film_Review_Bean.relatedObj.image;
			Bitmap bitmap1 = lruCache.get(iconurl);

			if (bitmap1 != null) {
				Drawable drawable = new BitmapDrawable(bitmap1);
				viewholder.ivicon.setBackgroundDrawable(drawable);
			} else {
				// 设置tag值，防止闪图
				viewholder.ivicon.setTag(iconurl);
				// 联网请求，设置图片
				setImageIcon(viewholder.ivicon, iconurl);
			}
			String readed_item_file_review = CacheUtils.getString(context,
					IS_READ_FILE_REVIEW);
			if (readed_item_file_review.contains(discover_Film_Review_Bean.id
					+ ",")) {
				viewholder.tvtitle.setTextColor(Color.GRAY);
			} else {
				viewholder.tvtitle.setTextColor(Color.BLACK);
			}
			return convertView;
		}
	}

	static class ViewHolder {
		TextView tvtitle;
		TextView tvdescribe;
		ImageView ivsmall;
		TextView tvnickname;
		TextView tvmovewname;
		TextView tvgrade;
		ImageView ivicon;
	}

	/**
	 * 联网请求item中的图片，并设置到对应的item中
	 * 
	 * @param iVicon
	 * @param imagepath
	 */
	public void setImageSmall(final ImageView iVicon, final String imagepath) {
		// 每次请求前把图片设置为默认的(不然复用的时候会显示上次加载过的图片)
		iVicon.setBackgroundDrawable(null);
		Log.e("TAG", "setImageSmall");
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
						Log.e("TAG", "获取影评小图片成功");
						// 在设置之前也判断一下
						String tagurl = (String) iVicon.getTag();
						if (tagurl != imagepath) {
							return;
						}
						// 保存到内存中
						lruCache.put(imagepath, response);
						// 设置图片到对应的ImageView中
						Drawable drawable = new BitmapDrawable(response);
						iVicon.setBackgroundDrawable(drawable);

					}
				}, 20, 20, Config.RGB_565, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("TAG", "获取预告片ListView的图片失败");
					}
				});
		queue.add(imageRequest);
	}

	/**
	 * 联网请求item中的图片，并设置到对应的item中
	 * 
	 * @param iVicon
	 * @param imagepath
	 */
	public void setImageIcon(final ImageView iVicon, final String imagepath) {
		// 每次请求前把图片设置为默认的(不然复用的时候会显示上次加载过的图片)
		iVicon.setBackgroundDrawable(null);

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
						// 保存到内存中
						lruCache.put(imagepath, response);
						// 设置图片到对应的ImageView中
						Drawable drawable = new BitmapDrawable(response);
						iVicon.setBackgroundDrawable(drawable);

					}
				}, 70, 100, Config.RGB_565, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("TAG", "获取预告片ListView的图片失败");
					}
				});
		queue.add(imageRequest);
	}
}
