package com.atguigu.mtime.payticket.pager;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.Response.ErrorListener;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.atguigu.mtime.R;
import com.atguigu.mtime.payticket.bean.PayTicketFilmBean;
import com.atguigu.mtime.payticket.bean.PayTicketFilmBean.FilmDetail;
import com.atguigu.mtime.payticket.bean.PayTicketFilmBean.Version;
import com.atguigu.mtime.payticket.pager.PayTicketFilm_HotMoviePager.HotMovieAdapter;
import com.viewpagerindicator.TabPageIndicator;

import android.content.Context;
import android.graphics.Color;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.view.ViewPager.OnPageChangeListener;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * 电影详情页
 * @author Wangnan
 *
 */
public class PayTicketFilmPager {

	/**
	 * 上下文对象
	 */
	private Context context;

	/**
	 * 视图对象
	 */
	public View root;
	
	/**
	 * 滑动视图ViewPager的引用
	 */
	private ViewPager viewpager_payticket_film;
	
	/**
	 * ViewPager的适配器引用
	 */
	private PayTicketFilmAdapter payTicketFilmAdapter;
	
	/**
	 * ViewPager的页改变监听
	 */
	private PayTicketFilmOnPageChangeListener payTicketFilmOnPageChangeListener;
	
	/**
	 * 标题数据
	 */
	private String[] titles = { "正在热映", "即将上映" };
	
	/**
	 * "正在热映"Tab组合(自定义):整体，标题，下滑线
	 */
	private LinearLayout ll_payticketfilm_hotmovie;
	private TextView tv_payticketfilm_hotmovie_title;
	private View v_payticketfilm_hotmovie_line;
	
	/**
	 * "即将上映"Tab组合(自定义):整体，标题，下滑线
	 */
	private LinearLayout ll_payticketfilm_comingmovie;
	private TextView tv_payticketfilm_comingmovie_title;
	private View v_payticketfilm_comingmovie_line;
	
	/**
	 * Tab组合点击监听器引用
	 */
	private PayTicketFilmOnClickListener payTicketFilmOnClickListener;
	
	/**
	 * 数据集合
	 */
	private List<LinearLayout> ll_payTicketFilms = new ArrayList<LinearLayout>();
	
	/**
	 * “正在热映”的数据Bean对象
	 */
	public PayTicketFilmBean payTicketFilmBean;
	
	public PayTicketFilmPager(Context context){
		this.context = context;		
		root = initView();	
	}

	/**
	 * 初始化视图
	 * @return
	 */
	private View initView() {
		LinearLayout ll = (LinearLayout) View.inflate(context, R.layout.payticketfilm_pager_layout, null);
		ll_payticketfilm_hotmovie = (LinearLayout) ll.findViewById(R.id.ll_payticketfilm_hotmovie);
		tv_payticketfilm_hotmovie_title = (TextView) ll.findViewById(R.id.tv_payticketfilm_hotmovie_title);
		v_payticketfilm_hotmovie_line = ll.findViewById(R.id.v_payticketfilm_hotmovie_line);
		
		ll_payticketfilm_comingmovie = (LinearLayout) ll.findViewById(R.id.ll_payticketfilm_comingmovie);
		tv_payticketfilm_comingmovie_title = (TextView) ll.findViewById(R.id.tv_payticketfilm_comingmovie_title);
		v_payticketfilm_comingmovie_line = ll.findViewById(R.id.v_payticketfilm_comingmovie_line);
			
		viewpager_payticket_film = (ViewPager) ll.findViewById(R.id.viewpager_payticket_film);
		
		return ll;
	}
	
	/**
	 * 初始化数据
	 */
	public void initDate(){
		
		//给Tab组添加点击监听
		if(payTicketFilmOnClickListener == null){
			payTicketFilmOnClickListener = new PayTicketFilmOnClickListener();
		}
		ll_payticketfilm_hotmovie.setOnClickListener(payTicketFilmOnClickListener);
		ll_payticketfilm_comingmovie.setOnClickListener(payTicketFilmOnClickListener);
		
		
		//默认选中"热门电影"
		setSelectedToggle(true);
			
		//联网获取数据
		getDataFromNet();
	
	}
	
	/**
	 * 联网获取Json数据
	 */
	private void getDataFromNet() {

		RequestQueue mQueue = Volley.newRequestQueue(context);
		
		String url = "http://api.m.mtime.cn/Showtime/LocationMovies.api?locationId=290";
		StringRequest stringRequest = new StringRequest(url, new Listener<String>() {

			@Override
			public void onResponse(String response) {
				// TODO Auto-generated method stub
				processData(response);
			}
		}, new ErrorListener() {

			@Override
			public void onErrorResponse(VolleyError error) {
				// TODO Auto-generated method stub
				
			}
		});
		
		mQueue.add(stringRequest);	
	}
	
	/**
	 * 解析数据(手动解析)
	 * @param response
	 */
	private void processData(String response) {
		// TODO Auto-generated method stub
		payTicketFilmBean = new PayTicketFilmBean();
		try {
			JSONObject jsonObject = new JSONObject(response);
			payTicketFilmBean.bImg = jsonObject.optString("bImg");
			payTicketFilmBean.date = jsonObject.optString("date");
			payTicketFilmBean.lid = jsonObject.optInt("lid");
			payTicketFilmBean.newActivitiesTime = jsonObject.optInt("newActivitiesTime");
			payTicketFilmBean.totalComingMovie = jsonObject.optInt("totalComingMovie");
			payTicketFilmBean.voucherMsg = jsonObject.optString("voucherMsg");
			
			JSONArray jsonArray = jsonObject.optJSONArray("ms");
			if(jsonArray != null){
				List<FilmDetail> filmDetails = new ArrayList<FilmDetail>();
				payTicketFilmBean.ms = filmDetails;
				
				for(int i = 0; i < jsonArray.length(); i++){
					FilmDetail filmDetail = payTicketFilmBean.new FilmDetail();
					
					JSONObject jsonObject2 = jsonArray.optJSONObject(i);
					
					filmDetail.NearestCinemaCount = jsonObject2.optInt("NearestCinemaCount");
					filmDetail.NearestDay = jsonObject2.optLong("NearestDay");
					filmDetail.NearestShowtimeCount = jsonObject2.optInt("NearestShowtimeCount");		
					filmDetail.aN1 = jsonObject2.optString("aN1");
					filmDetail.aN2 = jsonObject2.optString("aN2");
					filmDetail.cC = jsonObject2.optInt("cC");
					filmDetail.commonSpecial = jsonObject2.optString("commonSpecial");
					filmDetail.d = jsonObject2.optString("d");
					filmDetail.dN = jsonObject2.optString("dN");
					filmDetail.id = jsonObject2.optInt("id");
					filmDetail.img = jsonObject2.optString("img");
					filmDetail.is3D = jsonObject2.optBoolean("is3D");
					filmDetail.isDMAX = jsonObject2.optBoolean("isDMAX");
					filmDetail.isFilter = jsonObject2.optBoolean("isFilter");
					filmDetail.isHot = jsonObject2.optBoolean("isHot");
					filmDetail.isIMAX = jsonObject2.optBoolean("isIMAX");
					filmDetail.isIMAX3D = jsonObject2.optBoolean("isIMAX3D");
					filmDetail.isNew = jsonObject2.optBoolean("isNew");
					filmDetail.isTicket = jsonObject2.optBoolean("isTicket");
					filmDetail.movieType = jsonObject2.optString("movieType");
					filmDetail.r = jsonObject2.optDouble("r");
					filmDetail.rc = jsonObject2.optInt("rc");
					filmDetail.rd = jsonObject2.optString("rd");
					filmDetail.rsC = jsonObject2.optInt("rsC");
					filmDetail.sC = jsonObject2.optInt("sC");
					filmDetail.t = jsonObject2.optString("t");
					filmDetail.tCn = jsonObject2.optString("tCn");
					filmDetail.tEn = jsonObject2.optString("tEn");
					filmDetail.ua = jsonObject2.optInt("ua");
					filmDetail.wantedCount = jsonObject2.optInt("wantedCount");
					
					//filmDetail.p=(手动解析：未解析成功)
//					JSONArray jsonArray2 = jsonObject2.optJSONArray("p");
//					if(jsonArray2 != null){
//						List<MovieType>  movieTypes = new ArrayList<MovieType>();
//						filmDetail.p = movieTypes;
//						
//						for(int j = 0; j < jsonArray2.length(); j++){
//							MovieType movieType = payTicketFilmBean.new MovieType();
//							
//							JSONObject jsonObject3 = jsonArray2.optJSONObject(j);
//							movieType.p0 = jsonObject3.optString("["+"0"+"]");
//							movieType.p1 = jsonObject3.optString("["+"1"+"]");
//							movieType.p2 = jsonObject3.optString("["+"2"+"]");
//							
//							movieTypes.add(movieType);
//						}
//					}
					
					//filmDetail.versions
					JSONArray jsonArray3 = jsonObject2.optJSONArray("versions");
					if(jsonArray3 != null){
						List<Version> versions = new ArrayList<Version>();
						filmDetail.versions = versions;
						
						for(int k = 0; k < jsonArray3.length(); k++){
							Version version = payTicketFilmBean.new Version();
							
							JSONObject jsonObject4 = jsonArray3.optJSONObject(k);
							
							version.version_enum = jsonObject4.optInt("enum");
							version.version = jsonObject4.optString("version");
							
							versions.add(version);
						}
					}
					
					filmDetails.add(filmDetail);
				}
			}
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//准备数据
		//电影-热门影片:创建对象，初始化数据，将视图添加至集合
		PayTicketFilm_HotMoviePager payTicketFilm_HotMoviePager = new PayTicketFilm_HotMoviePager(context,payTicketFilmBean);
		payTicketFilm_HotMoviePager.initDate();
		ll_payTicketFilms.add((LinearLayout) payTicketFilm_HotMoviePager.root);
		
		//电影-即将上映:创建对象，初始化数据，将视图添加至集合
		PayTicketFilm_ComingPager payTicketFilm_ComingPager = new PayTicketFilm_ComingPager(context);
		payTicketFilm_ComingPager.initData();
		ll_payTicketFilms.add((LinearLayout)payTicketFilm_ComingPager.root);
		
		//给ViewPager设置适配器
		if(payTicketFilmAdapter == null){
			payTicketFilmAdapter = new PayTicketFilmAdapter();
		}
		viewpager_payticket_film.setAdapter(payTicketFilmAdapter);
		
		//设置适配器滚动监听(页改变)
		if(payTicketFilmOnPageChangeListener == null){
			payTicketFilmOnPageChangeListener = new PayTicketFilmOnPageChangeListener();
		}
		viewpager_payticket_film.setOnPageChangeListener(payTicketFilmOnPageChangeListener);
		
	}
	
	/**
	 * Tab组合的点击事件监听器实现类
	 * @author Wangnan
	 *
	 */
	class PayTicketFilmOnClickListener implements View.OnClickListener{

		@Override
		public void onClick(View v) {
			if(v == ll_payticketfilm_hotmovie){//点击了"正在热映"
				setSelectedToggle(true);
			}else if(v == ll_payticketfilm_comingmovie){//点击了"即将上映"
				setSelectedToggle(false);
			}		
		}
		
	}
	
	/**
	 * "正在热映"开关
	 */
	private void setSelectedToggle(boolean isHotMovie){
		
		if(isHotMovie){
			tv_payticketfilm_hotmovie_title.setTextColor(context.getResources().getColor(R.color.color_blue));
			v_payticketfilm_hotmovie_line.setVisibility(View.VISIBLE);
			
			tv_payticketfilm_comingmovie_title.setTextColor(context.getResources().getColor(android.R.color.black));
			v_payticketfilm_comingmovie_line.setVisibility(View.INVISIBLE);
			
			viewpager_payticket_film.setCurrentItem(0);
		}else{
			tv_payticketfilm_comingmovie_title.setTextColor(context.getResources().getColor(R.color.color_blue));
			v_payticketfilm_comingmovie_line.setVisibility(View.VISIBLE);
		
			tv_payticketfilm_hotmovie_title.setTextColor(context.getResources().getColor(android.R.color.black));
			v_payticketfilm_hotmovie_line.setVisibility(View.INVISIBLE);
			
			viewpager_payticket_film.setCurrentItem(1);
		}
	}
	
	/**
	 * 电影ViewPager的Adapter
	 * @author Wangnan
	 *
	 */
	class PayTicketFilmAdapter extends PagerAdapter{

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return ll_payTicketFilms.size();
		}	
		
		@Override
		public Object instantiateItem(ViewGroup container, int position) {
			LinearLayout linearLayout = ll_payTicketFilms.get(position);
			container.addView(linearLayout);
			return linearLayout;
		}

		@Override
		public boolean isViewFromObject(View view, Object object) {
			// TODO Auto-generated method stub
			return view == object;
		}
		
		@Override
		public void destroyItem(ViewGroup container, int position, Object object) {
			// TODO Auto-generated method stub
			container.removeView((View)object);
		}
		
	}
	
	/**
	 * ViewPager页改变监听
	 * @author Wangnan
	 *
	 */
	class PayTicketFilmOnPageChangeListener implements OnPageChangeListener{

		@Override
		public void onPageScrolled(int position, float positionOffset,
				int positionOffsetPixels) {
			// TODO Auto-generated method stub
			
		}

		@Override
		public void onPageSelected(int position) {
			// TODO Auto-generated method stub
			if(position == 0){
				setSelectedToggle(true);
			}else if(position == 1){
				setSelectedToggle(false);
			}
		}

		@Override
		public void onPageScrollStateChanged(int state) {
			// TODO Auto-generated method stub
			
		}
		
	}
}
