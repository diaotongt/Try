package com.atguigu.mtime.payticket.pager;

import java.util.ArrayList;
import java.util.List;

import com.android.volley.RequestQueue;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.atguigu.mtime.R;
import com.atguigu.mtime.payticket.bean.PayTicketComingBean;
import com.atguigu.mtime.payticket.bean.PayTicketComingBean.MovieInfo;
import com.google.gson.Gson;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Color;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.HorizontalScrollView;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 电影-即将上映详细页
 * @author Wangnan
 */
public class PayTicketFilm_ComingPager {

	/**
	 * 上下文
	 */
	private Context context;
	
	/**
	 * 根视图
	 */
	public View root;
	
	/**
	 * 水平滑动的ScrollView中的容器(LL)
	 */
	private LinearLayout ll_inhsv_payticketfilm_comingpager;
	
	/**
	 * 下部视图容器
	 */
	private LinearLayout ll_payticketfilm_comingpager_content_comingmovie;
	
	/**
	 * “即将上映”的数据Bean对象
	 */
	public PayTicketComingBean payTicketComingBean;
	
	/**
	 * “即将上映”的顶部滑动视图的数据集合
	 */
	private List<MovieInfo> attention = new ArrayList<MovieInfo>();
	
	/**
	 * “即将上映”的下方固定视图的数据集合
	 */
	private List<MovieInfo> moviecomings = new ArrayList<MovieInfo>();
	
	/**
	 * 广告条幅图片
	 */
	private ImageView iv_payticketfilm_comingpager_banner;
	
	/**
	 * 即将上映（多少部）
	 */
	private TextView tv_payticketfilm_comingpager_content_title;
	
	/**
	 * 请求队列声明
	 */
	private RequestQueue requestQueue;
	
	public PayTicketFilm_ComingPager(Context context){
		this.context = context;
		root = initView();
	}

	/**
	 * 初始化视图
	 * @return
	 */
	private View initView() {
		View view = View.inflate(context, R.layout.payticketfilm_comingpager_layout, null);
		ll_inhsv_payticketfilm_comingpager = (LinearLayout) view.findViewById(R.id.ll_inhsv_payticketfilm_comingpager);
		iv_payticketfilm_comingpager_banner = (ImageView) view.findViewById(R.id.iv_payticketfilm_comingpager_banner);
		tv_payticketfilm_comingpager_content_title = (TextView) view.findViewById(R.id.tv_payticketfilm_comingpager_content_title);
		ll_payticketfilm_comingpager_content_comingmovie = (LinearLayout) view.findViewById(R.id.ll_payticketfilm_comingpager_content_comingmovie);
		return view;
	}
	
	/**
	 * 初始化数据
	 */
	public void initData(){
		
		//联网获取数据
		getDataFromNet();
		
		//设置请求队列用于加载图片
		requestQueue = Volley.newRequestQueue(context);
		
		//广告Banner图片加载
		iv_payticketfilm_comingpager_banner.setImageResource(R.drawable.adv_banner);
		iv_payticketfilm_comingpager_banner.setScaleType(ScaleType.FIT_XY);
	}

	/**
	 * 联网获取JSON数据(Volley)
	 */
	private void getDataFromNet() {
		
		RequestQueue JsonQueue = Volley.newRequestQueue(context);
		String url = "http://api.m.mtime.cn/Movie/MovieComingNew.api?locationId=290";
		StringRequest stringRequest = new StringRequest(url, new Listener<String>() {

			@Override
			public void onResponse(String response) {
				progressData(response);
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				Toast.makeText(context, "联网加载失败", 0).show();
			}
		});
		
		//添加联网请求到消息队列
		JsonQueue.add(stringRequest);
	}

	/**
	 * 解析Json数据，并设置视图数据
	 * @param response
	 */
	protected void progressData(String response) {
		// TODO Auto-generated method stub
		payTicketComingBean = new Gson().fromJson(response, PayTicketComingBean.class);
	
		if(payTicketComingBean == null){
			return;
		}
		
		//填充上部水平滑动列表数据
		attention = payTicketComingBean.attention;
		for(int i = 0; i < attention.size(); i++){
			MovieInfo movieInfo = attention.get(i);
			View view = View.inflate(context, R.layout.item_hsv_payticketfilm_comingpager, null);
			
			//设置上映时间
			TextView tv_time = (TextView) view.findViewById(R.id.tv_item_hsv_payticket_comingpager_toptime);
			if(movieInfo.rYear == 2016){
				tv_time.setText(movieInfo.rYear+"年"+movieInfo.rMonth+"月"+movieInfo.rDay+"日");
			}else{
				tv_time.setText(movieInfo.releaseDate.substring(0, 6));
			}
			
			//设置标题
			TextView tv_title = (TextView) view.findViewById(R.id.tv_item_hsv_payticket_comingpager_title);
			tv_title.setText(movieInfo.title);
			
			//设置多少人想看
			TextView tv_people = (TextView) view.findViewById(R.id.tv_item_hsv_payticket_comingpager_people);
			tv_people.setText(movieInfo.wantedCount+"");
			
			//设置电影类型
			TextView tv_movietype = (TextView) view.findViewById(R.id.tv_item_hsv_payticket_comingpager_movietype);
			tv_movietype.setText(movieInfo.type);
			
			//设置导演
			TextView tv_director = (TextView) view.findViewById(R.id.tv_item_hsv_payticket_comingpager_director);	
			tv_director.setText("导演："+movieInfo.director);
			
			//设置演员
			TextView tv_actor = (TextView) view.findViewById(R.id.tv_item_hsv_payticket_comingpager_actor);
			tv_actor.setText("演员："+movieInfo.actor1+","+movieInfo.actor2);
			
			//设置图片
			ImageView iv_icon = (ImageView) view.findViewById(R.id.iv_item_hsv_payticket_comingpager_icon);
			setImageFromNet(iv_icon,movieInfo.image);
			
			//是否可以购票
			Button btn_payticket = (Button) view.findViewById(R.id.btn__item_hsv_payticket_comingpager_sell);
			if(!movieInfo.isTicket){
				btn_payticket.setVisibility(View.GONE);
			}
			
			//设置是否有预告片
			Button btn_pre = (Button) view.findViewById(R.id.btn__item_hsv_payticket_comingpager_pre);
			if(movieInfo.videos.size() > 0){
				btn_pre.setVisibility(View.VISIBLE);
			}
			
			//添加至滑动视图
			ll_inhsv_payticketfilm_comingpager.addView(view);
		}
		
		//填充下方固定滑动列表数据
		moviecomings = payTicketComingBean.moviecomings;
		tv_payticketfilm_comingpager_content_title.setText("即将上映("+moviecomings.size()+"部)");
		
		if(moviecomings.size()<=0){
			return;
		}
		
		//开始填充视图
		int month = 1;//记录月份(1~12)
		for(int i = 0;i < moviecomings.size(); i++){
			
			//添加时间：“月”时间视图
			if(i == 0){
				View view = View.inflate(context, R.layout.item_payticketfilm_comingpager_content_comingmovie_time, null);
				TextView tvTime = (TextView) view.findViewById(R.id.tv_payticketfilm_comingpager_content_comingmovie_time);
				month = moviecomings.get(i).rMonth;
				tvTime.setText(moviecomings.get(i).rMonth+"月");
				ll_payticketfilm_comingpager_content_comingmovie.addView(view);
			}
			
			if(month != moviecomings.get(i).rMonth){
				View view = View.inflate(context, R.layout.item_payticketfilm_comingpager_content_comingmovie_time, null);
				TextView tvTime = (TextView) view.findViewById(R.id.tv_payticketfilm_comingpager_content_comingmovie_time);
				month = moviecomings.get(i).rMonth;
				tvTime.setText(moviecomings.get(i).rMonth+"月");
				ll_payticketfilm_comingpager_content_comingmovie.addView(view);				
			}
			
			//添加具体item视图
			View v = View.inflate(context, R.layout.item_payticketfilm_comingpager_content_comingmovie_item, null);
			MovieInfo movieInfo = moviecomings.get(i);
			
			//设置item“日”时间
			TextView tv_time = (TextView) v.findViewById(R.id.item_payticketfilm_comingpager_content_comingmovie_item_time);
			tv_time.setText(movieInfo.rDay+"日");
			
			//设置标题
			TextView tv_title = (TextView) v.findViewById(R.id.item_payticketfilm_comingpager_content_comingmovie_item_title);
			tv_title.setText(movieInfo.title);
			
			//设置多少人想看
			TextView tv_people = (TextView) v.findViewById(R.id.item_payticketfilm_comingpager_content_comingmovie_item_people);
			tv_people.setText(movieInfo.wantedCount+"");
			
			//设置电影类型
			TextView tv_type = (TextView) v.findViewById(R.id.item_payticketfilm_comingpager_content_comingmovie_item_movietype);
			tv_type.setText(movieInfo.type);
			
			//设置导演
			TextView tv_director = (TextView) v.findViewById(R.id.item_payticketfilm_comingpager_content_comingmovie_item_director);
			tv_director.setText("导演 ：" + movieInfo.director);
			
			//设置第一个Button
			Button btn_alert = (Button) v.findViewById(R.id.btn_item_payticketfilm_comingpager_content_comingmovie_alert);
			if(movieInfo.isTicket){
				btn_alert.setText("超前预售");
				btn_alert.setTextColor(Color.WHITE);
				btn_alert.setBackgroundResource(R.drawable.home_fragment_btn_shape2);
			}
			
			//设置第二个Button是否显示
			Button btn_pre = (Button) v.findViewById(R.id.btn_item_payticketfilm_comingpager_content_comingmovie_pre);
			if(movieInfo.videos.size() > 0){
				btn_pre.setVisibility(View.VISIBLE);
			}
			
			//设置item图片
			ImageView iv_icon = (ImageView) v.findViewById(R.id.item_payticketfilm_comingpager_content_comingmovie_icon);
			setImageFromNet(iv_icon,movieInfo.image);
			
			ll_payticketfilm_comingpager_content_comingmovie.addView(v);
		}
	}

	/**
	 * 联网加载图片
	 * @param iv_icon
	 * @param img
	 */
	private void setImageFromNet(final ImageView iv_icon, String url) {
		
		ImageRequest imageRequest = new ImageRequest(url, new Listener<Bitmap>() {

			@Override
			public void onResponse(Bitmap response) {
				// TODO Auto-generated method stub
				iv_icon.setImageBitmap(response);
			}
		}, 270, 420, Config.RGB_565, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				
			}
		});
		
		requestQueue.add(imageRequest);
	}
}
