package com.atguigu.mtime.fragment;

import java.lang.reflect.Field;
import java.util.List;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.atguigu.mtime.MainActivity;
import com.atguigu.mtime.R;
import com.atguigu.mtime.discover.bean.Ticket_Film_hot_bean;
import com.atguigu.mtime.discover.bean.Ticket_Film_hot_bean.Hot_Info;
import com.atguigu.mtime.payticket.pager.PayTicketCinemaPager;
import com.atguigu.mtime.payticket.pager.PayTicketFilmPager;
import com.google.gson.Gson;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Bitmap.Config;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.util.LruCache;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.webkit.WebView.FindListener;
import android.widget.BaseAdapter;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.ImageView.ScaleType;
import android.widget.Toast;

/**
 * 首页Fragment
 * 
 * @author Diaotong and wangnan
 */
public class PayTicketFragment extends Fragment{


	/**
	 * 上下文对象
	 */
	private Context context;
	
	/**
	 * 购票界面的主布局
	 */
	private RelativeLayout rl_ticket_fragment;
	
	/**
	 * 标题栏电影选项
	 */
	private ImageView iv_ticket_fragment_top_film;
	
	/**
	 * 标题栏影院选项
	 */
	private ImageView iv_ticket_fragment_top_cinema;
	
	/**
	 * 购票标题栏下的内部内容1(注：1和2同时只会存在1个)
	 */
	private FrameLayout fl_ticket_fragment_content1;
	
	/**
	 * 购票标题栏下的内部内容2(注：1和2同时只会存在1个)
	 */
	private FrameLayout fl_ticket_fragment_content2;
	
	/**
	 * 电影详情页对象
	 */
	private PayTicketFilmPager payTicketFilmPager;
	
	/**
	 * 影院详情页对象
	 */
	private PayTicketCinemaPager payTicketCinemaPager;
	
	/**
	 * PayTicket视图的点击监听器
	 */
	private PayTicketOnClickListener payTicketOnClickListener = new PayTicketOnClickListener();

	@Override
	public void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		this.context = getActivity();	
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		//获取根布局
		rl_ticket_fragment = (RelativeLayout) View.inflate(context, R.layout.ticket_fm_diaotong, null);
		
		//初始化视图
		initView();
		
		//初始化数据
		initDate();
		
		//初始化监听器
		initListener();
		
		return rl_ticket_fragment;
	}
	

	/**
	 * 初始化视图
	 * @author Wangnan
	 */
	private void initView() {
		iv_ticket_fragment_top_film = (ImageView) rl_ticket_fragment.findViewById(R.id.iv_ticket_fragment_top_film);
		iv_ticket_fragment_top_cinema = (ImageView) rl_ticket_fragment.findViewById(R.id.iv_ticket_fragment_top_cinema);
		fl_ticket_fragment_content1 = (FrameLayout) rl_ticket_fragment.findViewById(R.id.fl_ticket_fragment_content1);
		fl_ticket_fragment_content2 = (FrameLayout) rl_ticket_fragment.findViewById(R.id.fl_ticket_fragment_content2);
	}
	
	
	/**
	 * 初始化数据
	 */
	private void initDate() {
		
		//填充两个界面
		payTicketFilmPager = new PayTicketFilmPager(context);
		payTicketFilmPager.initDate();
		
		payTicketCinemaPager = new PayTicketCinemaPager(context);
		payTicketCinemaPager.initDate();
		
		fl_ticket_fragment_content1.addView(payTicketFilmPager.root);
		fl_ticket_fragment_content2.addView(payTicketCinemaPager.root);
		
		//切换界面
		fl_ticket_fragment_content1.setVisibility(View.VISIBLE);
		fl_ticket_fragment_content2.setVisibility(View.GONE);

		//切换图标
		iv_ticket_fragment_top_film.setBackgroundResource(R.drawable.image_ticket_top_film_on);
		iv_ticket_fragment_top_cinema.setBackgroundResource(R.drawable.image_ticket_top_cinema_off);
	}
	
	/**
	 * 初始化监听器
	 * @author Wangnan
	 */
	private void initListener() {
		iv_ticket_fragment_top_film.setOnClickListener(payTicketOnClickListener);
		iv_ticket_fragment_top_cinema.setOnClickListener(payTicketOnClickListener);
	}

	/**
	 * 视图的点击监听器
	 * @author Wangnan
	 */
	class PayTicketOnClickListener implements View.OnClickListener{
				
		@Override
		public void onClick(View v) {
			
			
			if(v == iv_ticket_fragment_top_film){	
				
				//切换界面
				fl_ticket_fragment_content1.setVisibility(View.VISIBLE);
				fl_ticket_fragment_content2.setVisibility(View.GONE);
				
				//改变图标
				iv_ticket_fragment_top_film.setBackgroundResource(R.drawable.image_ticket_top_film_on);
				iv_ticket_fragment_top_cinema.setBackgroundResource(R.drawable.image_ticket_top_cinema_off);
			
			}else if(v == iv_ticket_fragment_top_cinema){
				
				//切换界面
				fl_ticket_fragment_content1.setVisibility(View.GONE);
				fl_ticket_fragment_content2.setVisibility(View.VISIBLE);
				
				//改变图标
				iv_ticket_fragment_top_film.setBackgroundResource(R.drawable.image_ticket_top_film_off);
				iv_ticket_fragment_top_cinema.setBackgroundResource(R.drawable.image_ticket_top_cinema_on);

			}

		}
		
	}


	
//
////	private void initLayout() {
////		iv_ticket_fm_top_film = (ImageView) view
////				.findViewById(R.id.iv_ticket_fm_top_film);
////		iv_ticket_fm_top_cinema = (ImageView) view
////				.findViewById(R.id.iv_ticket_fm_top_cinema);
////		ll_ticket_fm_cotent_ll = (LinearLayout) view
////				.findViewById(R.id.ll_ticket_fm_cotent_ll);
////	}
//
//	private void initData() {
//		childView = View.inflate(context, R.layout.ticket_fm_childview_film,
//				null);
//		lv_ticket_fm_film_cotent_lv = (ListView) childView
//				.findViewById(R.id.lv_ticket_fm_film_cotent_lv);
//		tv_ticket_fm_film_hot = (TextView) childView
//				.findViewById(R.id.tv_ticket_fm_film_hot);
//		tv_ticket_fm_film_comming = (TextView) childView
//				.findViewById(R.id.tv_ticket_fm_film_comming);
//		v_user_fm_act_message_left = childView
//				.findViewById(R.id.v_user_fm_act_message_left);
//		v_user_fm_act_message_right = childView
//				.findViewById(R.id.v_user_fm_act_message_right);
//		ll_fm_act_message_loading = (LinearLayout) childView
//				.findViewById(R.id.ll_fm_act_message_loading);
//		tv_ticket_fm_film_hot.setOnClickListener(PayTicketFragment.this);
//		tv_ticket_fm_film_comming.setOnClickListener(PayTicketFragment.this);
//		feedHotListInfo();
//	}
//
//	private void feedHotListInfo() {
//		getdatafromnet();
//
//	}
//
//	private void getdatafromnet() {
//		RequestQueue queue = Volley.newRequestQueue(context);
//		// // 消息请求
//		StringRequest request = new StringRequest(Request.Method.GET, news_url,
//				new Response.Listener<String>() {
//					// 获取数据成功后会调
//					@Override
//					public void onResponse(String arg0) {
//						Log.e("TAG", "获取新闻ListView中的数据成功了");
//						// 解析数据
//						processdata(arg0);
//					}
//				}, new Response.ErrorListener() {
//					// 获取数据失败后会调
//					@Override
//					public void onErrorResponse(VolleyError arg0) {
//						Log.e("TAG", "获取新闻ListView中的数据失败了");
//					}
//				});
//		queue.add(request);
//	}
//
//	protected void processdata(String arg0) {
//		Gson gson = new Gson();
//		Ticket_Film_hot_bean ticket_Film_hot_bean = gson.fromJson(arg0,
//				Ticket_Film_hot_bean.class);
//		if (ticket_Film_hot_bean == null) {
//			Log.e("TAG", "卧槽没解析成功么");
//		}
//		ms = ticket_Film_hot_bean.ms;
//		adapter = new MyListAdapter();
//		lv_ticket_fm_film_cotent_lv.setAdapter(adapter);
//	}
//
//	@Override
//	public void onClick(View v) {
//		switch (v.getId()) {
//		case R.id.tv_ticket_fm_film_hot:
//			tv_ticket_fm_film_hot.setTextColor(Color.parseColor("#0075c4"));
//			tv_ticket_fm_film_comming.setTextColor(Color.parseColor("#000000"));
//			v_user_fm_act_message_left.setVisibility(View.VISIBLE);
//			v_user_fm_act_message_right.setVisibility(View.INVISIBLE);
//			handler.sendEmptyMessage(LOAD_ANNOUNCE);
//
//			break;
//
//		case R.id.tv_ticket_fm_film_comming:
//
//			tv_ticket_fm_film_comming.setTextColor(Color.parseColor("#0075c4"));
//			tv_ticket_fm_film_hot.setTextColor(Color.parseColor("#000000"));
//			v_user_fm_act_message_left.setVisibility(View.INVISIBLE);
//			v_user_fm_act_message_right.setVisibility(View.VISIBLE);
//			handler.sendEmptyMessage(LOAD_BORADCAST);
//			break;
//
//		case R.id.iv_ticket_fm_top_film:
//			childView = View.inflate(context,
//					R.layout.ticket_fm_childview_film, null);
//			lv_ticket_fm_film_cotent_lv = (ListView) childView
//					.findViewById(R.id.lv_ticket_fm_film_cotent_lv);
//			tv_ticket_fm_film_hot = (TextView) childView
//					.findViewById(R.id.tv_ticket_fm_film_hot);
//			tv_ticket_fm_film_comming = (TextView) childView
//					.findViewById(R.id.tv_ticket_fm_film_comming);
//			tv_ticket_fm_film_hot.setOnClickListener(PayTicketFragment.this);
//			tv_ticket_fm_film_comming
//					.setOnClickListener(PayTicketFragment.this);
//			v_user_fm_act_message_left = childView
//					.findViewById(R.id.v_user_fm_act_message_left);
//			v_user_fm_act_message_right = childView
//					.findViewById(R.id.v_user_fm_act_message_right);
//			ll_fm_act_message_loading = (LinearLayout) childView
//					.findViewById(R.id.ll_fm_act_message_loading);
//			iv_ticket_fm_top_film
//					.setBackgroundResource(R.drawable.image_ticket_top_film_on);
//			iv_ticket_fm_top_cinema
//					.setBackgroundResource(R.drawable.image_ticket_top_cinema_off);
//			feedHotListInfo();
//			handler.sendEmptyMessage(LOAD_ANNOUNCE);
//			ll_ticket_fm_cotent_ll.removeAllViews();
//			ll_ticket_fm_cotent_ll.addView(childView);
//			break;
//
//		case R.id.iv_ticket_fm_top_cinema:
//			childView = View.inflate(context,
//					R.layout.ticket_fm_childview_cinema, null);
//			iv_ticket_fm_top_film
//					.setBackgroundResource(R.drawable.image_ticket_top_film_off);
//			iv_ticket_fm_top_cinema
//					.setBackgroundResource(R.drawable.image_ticket_top_cinema_on);
//			ll_ticket_fm_cotent_ll.removeAllViews();
//			ll_ticket_fm_cotent_ll.addView(childView);
//			break;
//
//		default:
//			break;
//		}
//	}
//
//	class MyListAdapter extends BaseAdapter {
//
//		public MyListAdapter() {
//			// 得到运行时最大的内存
//			int maxMemory = (int) Runtime.getRuntime().maxMemory();
//			lruCache = new LruCache<String, Bitmap>(maxMemory / 8) {
//				@Override
//				protected int sizeOf(String key, Bitmap value) {
//
//					return value.getRowBytes() * value.getHeight();
//				}
//			};
//		}
//
//		@Override
//		public int getCount() {
//			return ms.size();
//		}
//
//		@Override
//		public Object getItem(int position) {
//			return ms.get(position);
//		}
//
//		@Override
//		public long getItemId(int position) {
//			return 0;
//		}
//
//		@Override
//		public View getView(int position, View convertView, ViewGroup parent) {
//			hotViewHolder hViewHolder = new hotViewHolder();
//			if (convertView == null) {
//				convertView = View.inflate(context,
//						R.layout.item_ticket_film_hot, null);
//				hViewHolder.ivIcon = (ImageView) convertView
//						.findViewById(R.id.iv_item_ticket_film_hot_icon);
//				hViewHolder.tvTitle = (TextView) convertView
//						.findViewById(R.id.tv_item_ticket_film_hot_title);
//				hViewHolder.tvGrade = (TextView) convertView
//						.findViewById(R.id.tv_item_ticket_film_hot_grade);
//				hViewHolder.tvDes = (TextView) convertView
//						.findViewById(R.id.tv_item_ticket_film_hot_des);
//				hViewHolder.tvTime = (TextView) convertView
//						.findViewById(R.id.tv_item_ticket_film_hot_time);
//				hViewHolder.tvShow = (TextView) convertView
//						.findViewById(R.id.tv_item_ticket_film_hot_showinfo);
//
//				convertView.setTag(hViewHolder);
//			} else {
//				hViewHolder = (hotViewHolder) convertView.getTag();
//			}
//			Hot_Info hot_Info = ms.get(position);
//			String imagepath = hot_Info.img;
//			Bitmap bitmap = lruCache.get(imagepath);
//
//			if (bitmap != null) {
//				hViewHolder.ivIcon.setImageBitmap(bitmap);
//			} else {
//				// 设置tag值，防止闪图
//				hViewHolder.ivIcon.setTag(imagepath);
//				// 联网请求，设置图片
//				setImageView(hViewHolder.ivIcon, imagepath);
//			}
//
//			hViewHolder.tvTitle.setText(hot_Info.t);
//			hViewHolder.tvGrade.setText(hot_Info.r + "");
//			hViewHolder.tvDes.setText(hot_Info.commonSpecial);
//			hViewHolder.tvTime.setText(hot_Info.rd + "上映");
//			hViewHolder.tvShow.setText("今日" + hot_Info.NearestCinemaCount
//					+ "家影院" + hot_Info.NearestShowtimeCount + "场");
//
//			return convertView;
//		}
//	}
//
//	static class hotViewHolder {
//		ImageView ivIcon;
//		TextView tvTitle;
//		TextView tvGrade;
//		TextView tvDes;
//		TextView tvTime;
//		TextView tvShow;
//	}

//	public void setImageView(final ImageView iVicon, final String imagepath) {
//		// 每次请求前把图片设置为默认的(不然复用的时候会显示上次加载过的图片)
//		// iVicon.setImageResource(R.drawable.img_default_300x200);
//		iVicon.setScaleType(ScaleType.FIT_XY);
//		Resources res = context.getResources();
//
//		Bitmap bmp = BitmapFactory.decodeResource(res,
//				R.drawable.img_default_300x200);
//		Drawable drawablehead = new BitmapDrawable(bmp);
//		iVicon.setBackgroundDrawable(drawablehead);
//
//		// 说明已经不是最新的请求地址了，就没必要请求了
//		String tagurl = (String) iVicon.getTag();
//		if (tagurl != imagepath) {
//			return;
//		}
//		RequestQueue queue = Volley.newRequestQueue(context);
//		// 消息请求
//		ImageRequest imageRequest = new ImageRequest(imagepath,
//				new Listener<Bitmap>() {
//
//					@Override
//					public void onResponse(Bitmap response) {
//						Log.e("TAG", "获取预告片ListView的图片成功");
//						// 在设置之前也判断一下
//						String tagurl = (String) iVicon.getTag();
//						if (tagurl != imagepath) {
//							return;
//						}
//						// 设置图片到对应的ImageView中
//						lruCache.put(imagepath, response);
//						// 保存到内存中
//						// LinearLayout.LayoutParams param = new
//						// LinearLayout.LayoutParams(
//						// LinearLayout.LayoutParams.MATCH_PARENT,
//						// LinearLayout.LayoutParams.MATCH_PARENT);
//						// iVicon.setLayoutParams(param);
//						Drawable drawable = new BitmapDrawable(response);
//
//						iVicon.setBackgroundDrawable(drawable);
//						// iVicon.setImageBitmap(response);
//
//					}
//				}, 0, 0, Config.ARGB_4444, new ErrorListener() {
//
//					@Override
//					public void onErrorResponse(VolleyError error) {
//						Log.e("TAG", "获取预告片ListView的图片失败");
//					}
//				});
//		queue.add(imageRequest);
}
