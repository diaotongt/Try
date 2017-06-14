package com.atguigu.mtime;

import java.util.List;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.atguigu.mtime.NeiDi.MyAdapter;
import com.atguigu.mtime.NeiDi.ViewHolder;
import com.atguigu.mtime.discover.base.RankList_Top_Base;
import com.atguigu.mtime.discover.bean.Rank_Top_Bean;
import com.atguigu.mtime.discover.bean.Discover_TOP_Bean.SubTopList;
import com.atguigu.mtime.discover.bean.Rank_Top_Bean.Top_Movies;
import com.google.gson.Gson;

public class XiangGang extends RankList_Top_Base {
	private String pageSubAreaID;
	private String baseurl = "http://api.m.mtime.cn/TopList/TopListDetailsByRecommend.api?pageIndex=1&pageSubAreaID="
			+ pageSubAreaID + "&locationId={2}";

	private SubTopList subtoplist;

	// 内存缓存集合
	private LruCache<String, Bitmap> lruCache;

	// ListView的数据
	private List<Top_Movies> movies;

	public XiangGang(Context context, SubTopList subtoplist) {
		super(context);
		this.subtoplist = subtoplist;
	}

	@Override
	public void initdata() {
		super.initdata();
		if (subtoplist == null) {
			return;
		}
		getdatafromnet();
	}

	/**
	 * 联网获取数据
	 */
	private void getdatafromnet() {

		fl__main_animation.setVisibility(View.VISIBLE);
		animationDrawable.start();

		pageSubAreaID = subtoplist.pageSubAreaId;
		baseurl = "http://api.m.mtime.cn/TopList/TopListDetailsByRecommend.api?pageIndex=1&pageSubAreaID="
				+ pageSubAreaID + "&locationId={2}";
		// 消息队列
		RequestQueue queue = Volley.newRequestQueue(context);
		// 消息请求
		StringRequest request = new StringRequest(Request.Method.GET, baseurl,
				new Response.Listener<String>() {
					@Override
					public void onResponse(String arg0) {
						Log.e("TAG", "获取各个地区的排行榜成功");
						// 解析数据
						processtopdata(arg0);
					}
				}, new Response.ErrorListener() {
					@Override
					public void onErrorResponse(VolleyError arg0) {
						Log.e("TAG", "获取各个地区的排行榜失败");
					}
				});
		queue.add(request);
	}

	/**
	 * 解析数据
	 * 
	 * @param arg0
	 */
	protected void processtopdata(String arg0) {

		animationDrawable.stop();
		fl__main_animation.setVisibility(View.GONE);

		Gson gson = new Gson();
		Rank_Top_Bean rank_top_bean = gson.fromJson(arg0, Rank_Top_Bean.class);
		movies = rank_top_bean.movies;

		tv_titleSmall.setText(rank_top_bean.topList.summary);

		lv_ranklist_top.setAdapter(new MyAdapter());
	}

	class MyAdapter extends BaseAdapter {

		public MyAdapter() {
			// 在创建适配器的时候，创建内存的缓存集合LruCache
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
			return movies.size();
		}

		@Override
		public Object getItem(int position) {

			return movies.get(position);
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
						R.layout.ranklist_top_base_item, null);

				viewholder.tvposition = (TextView) convertView
						.findViewById(R.id.tv_item_position);

				viewholder.ivicon = (ImageView) convertView
						.findViewById(R.id.iv_item_icon);

				viewholder.tvname = (TextView) convertView
						.findViewById(R.id.tv_item_name);

				viewholder.tvrating = (TextView) convertView
						.findViewById(R.id.tv_item_rating);

				viewholder.tvnameEn = (TextView) convertView
						.findViewById(R.id.tv_item_nameEn);

				viewholder.tvweekBoxOffice = (TextView) convertView
						.findViewById(R.id.tv_item_weekBoxOffice);

				viewholder.tvtotalBoxOffice = (TextView) convertView
						.findViewById(R.id.tv_item_totalBoxOffice);

				convertView.setTag(viewholder);
			} else {
				viewholder = (ViewHolder) convertView.getTag();
			}
			Top_Movies top_movies = movies.get(position);

			viewholder.tvposition.setText(top_movies.rankNum + "");

			// 获取图片的地址
			String movieimageurl = top_movies.posterUrl;
			// 根据内存中有无，选择设置bitmap的方式(1.直接内存中获取，2.联网获取)
			Bitmap bitmap = lruCache.get(movieimageurl);
			if (bitmap != null) {
				Drawable drawable = new BitmapDrawable(bitmap);
				viewholder.ivicon.setBackgroundDrawable(drawable);
			} else {
				// 设置tag值，防止闪图
				viewholder.ivicon.setTag(movieimageurl);
				// 联网请求，设置图片
				setImageView(viewholder.ivicon, movieimageurl);
			}

			// viewholder.ivicon;
			viewholder.tvname.setText(top_movies.name);
			if (top_movies.rating < 5) {
				viewholder.tvrating.setText(null);
			} else {
				viewholder.tvrating.setText(top_movies.rating + "");
			}

			viewholder.tvnameEn.setText(top_movies.nameEn);
			viewholder.tvweekBoxOffice.setText(top_movies.weekBoxOffice);
			viewholder.tvtotalBoxOffice.setText(top_movies.totalBoxOffice);
			switch (position) {
			case 0:
				viewholder.tvposition
						.setBackgroundResource(R.drawable.topmovie_list_numa);
				break;
			case 1:
				viewholder.tvposition
						.setBackgroundResource(R.drawable.topmovie_list_numb);
				break;
			case 2:
				viewholder.tvposition
						.setBackgroundResource(R.drawable.topmovie_list_numc);
				break;

			default:
				viewholder.tvposition
						.setBackgroundResource(R.drawable.topmovie_list_numd);
				break;
			}
			return convertView;
		}
	}

	static class ViewHolder {
		TextView tvposition;
		ImageView ivicon;
		TextView tvname;
		TextView tvrating;
		TextView tvnameEn;
		TextView tvweekBoxOffice;
		TextView tvtotalBoxOffice;
	}

	public void setImageView(final ImageView ivIcon, final String imagepath) {
		// 每次请求前把图片设置为默认的(不然复用的时候会显示上次加载过的图片)
		ivIcon.setBackgroundResource(R.drawable.img_default);
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
						Log.e("TAG", "获取排行榜item图片成功");
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
				}, 80, 120, Config.ARGB_4444, new ErrorListener() {

					@Override
					public void onErrorResponse(VolleyError error) {
						Log.e("TAG", "获取排行榜item图片失败");
					}
				});
		queue.add(imageRequest);
	}
}
