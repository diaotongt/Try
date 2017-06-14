package com.atguigu.mtime.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.atguigu.mtime.R;
import com.atguigu.mtime.discover.bean.Discover_Refresh_Bean.AdvWordList;

/**
 * 自定义下拉刷新的ListView(Copy自北京新闻)：添加详细注释
 * 
 * @author Wangnan
 */
public class RefreshListView extends ListView {

	private Context context;

	/**
	 * 整个头布局View（下拉刷新和头部图片） 以及他头布局中的视图组件
	 * 
	 * @author Wangnan
	 */

	public LinearLayout headView;
	// 下拉刷新控件的视图
	private RelativeLayout pullDownRefresh;

	// 头视图中除了刷新视图外ListView最上面的视图

	private RelativeLayout topview;

	/**
	 * 下拉刷新控件的视图的高
	 * 
	 * @author Wangnan
	 */
	private int pullDownRefreshHeight;
	/**
	 * 刷新视图的子视图以及数据
	 */
	// 下拉刷新的箭头
	private ImageView iv_refresh;
	// 正在刷新时的progressBar
	private ProgressBar pb_refresh;
	// 得到刷新时显示的“下拉刷新，释放刷新,正在加载”
	private TextView tv_refresh;
	// 刷新时显示的电影名称
	private TextView tv_refresh_title;
	// 刷新时显示的电影描述
	private TextView tv_refresh_describe;
	/**
	 * TopView的子视图
	 */
	// topview中的图片
	private ImageView iv_header;
	// topview中的文本
	private TextView tv_header_movies_describe;
	// 得到预告片中的topview中播放的按钮
	private ImageView iv_prevue_play;

	/**
	 * 尾布局(上拉加载更多),和他的高 以及其中的子视图
	 * 
	 * @author Wangnan
	 */
	private RelativeLayout footView;

	// 尾布局的高
	private int footViewHeight;

	// 尾布局的箭头
	private ImageView iv_loadmore;

	// 尾布局的ProgressBar
	private ProgressBar pb_loadmore;

	// 尾布局的文本
	private TextView tv_loading_more;

	/**
	 * listView在屏幕上Y轴的坐标（用于初始化定位 ）
	 * 
	 * @author Wangnan
	 */
	private int listViewOnScreenY = 0;
	/**
	 * 下拉刷新的状态值和各个刷新状态
	 */
	/**
	 * 标示是否有上拉加载更多的视图.默认是有的
	 */
	private boolean isfoodview = true;
	// 刷新的状态值
	private int currentrefreshstatus = PULL_DOWN_REFRESH;

	// 下拉刷新状态 ,松手刷新,正在刷新,正在刷新
	private static final int PULL_DOWN_REFRESH = 1;
	private static final int RELEASE_REFRESH = 2;
	private static final int REFRESHING = 3;
	/**
	 * 上拉加载更多的状态值和各个加载更多的状态
	 */
	// 加载更多的的状态值
	private int currentloadmorestatus = PULL_UP_REFRESH;

	// 加载更多的状态 ,上拉加载更多,释放加载,正在载入
	private static final int PULL_UP_REFRESH = 4;// 上拉加载更多
	private static final int LOADED_REFRESH = 5;// 释放加载
	private static final int LOADING = 6;// 正在载入

	/**
	 * 动画
	 */
	private Animation upAnimation;
	private Animation downAnimation;

	// 监听器对象
	private OnRefreshListener onRefreshListener;

	/**
	 * 监听刷新的接口
	 * 
	 * @author Wangnan
	 *
	 */
	public interface OnRefreshListener {

		void onPullDownRefresh(); // 下拉刷新

		void onLoadingMore(); // 加载更多
	}

	/**
	 * 设置刷新/加载更多的监听
	 * 
	 * @param onRefreshListener
	 */
	public void setOnRefreshListener(OnRefreshListener onRefreshListener) {
		this.onRefreshListener = onRefreshListener;
	}

	public RefreshListView(Context context, AttributeSet attrs) {
		super(context, attrs);

		this.context = context;

		// 初始化下拉刷新布局，并且把下拉刷新布局添加到ListView的头部 - addHeaderView();
		initHeaderView(context);

		// 根据需求初始化上拉加载更多布局，并且把上拉加载更多布局添加到ListView的尾部 - addHeaderView();
		if (isfoodview) {
			initFootView(context);
		}
		// 初始动画
		initAnimation(context);
	}

	/**
	 * 初始化下拉刷新布局，并且把下拉刷新布局添加到ListView的头部
	 * 
	 * @param context
	 */
	private void initHeaderView(Context context) {
		/**
		 * 加载ListView的头视图
		 */
		headView = (LinearLayout) View.inflate(context,
				R.layout.listview_header_refresh, null);

		/**
		 * 得到下拉刷新的视图以及他其中的子视图
		 */

		// 得到刷新的视图
		pullDownRefresh = (RelativeLayout) headView
				.findViewById(R.id.rl_refresh);

		// 测量并获取下拉刷新控件的高
		pullDownRefresh.measure(0, 0);
		pullDownRefreshHeight = pullDownRefresh.getMeasuredHeight();

		// 得到下拉刷新控件中电影名称的tv
		tv_refresh_title = (TextView) headView
				.findViewById(R.id.tv_refresh_title);

		// 得到下拉刷新控件中电影描述的tv
		tv_refresh_describe = (TextView) headView
				.findViewById(R.id.tv_refresh_describe);

		// 得到显示上下的箭头
		iv_refresh = (ImageView) headView.findViewById(R.id.iv_refresh);

		// 得到下拉刷新控件中的ProGressbar
		pb_refresh = (ProgressBar) headView.findViewById(R.id.pb_refresh);

		// 得到刷新视图中的“下拉刷新，释放刷新,正在加载
		tv_refresh = (TextView) headView.findViewById(R.id.tv_refresh);

		/**
		 * 得到下拉刷新下面的视图(TopView)以及他其中的子视图
		 */

		// 得到头视图中刷新视图下的视图
		topview = (RelativeLayout) headView.findViewById(R.id.rl_header);

		// 得到topview中的图片
		iv_header = (ImageView) headView.findViewById(R.id.iv_header);

		// 得到topview中的文本
		tv_header_movies_describe = (TextView) headView
				.findViewById(R.id.tv_header_movies_describe);
		// 得到预告片中的topview中播放的按钮
		iv_prevue_play = (ImageView) headView.findViewById(R.id.iv_prevue_play);
		/**
		 * 下拉刷新的原理 int pading = -控件高 +(endY - startY);
		 * view.setPading(0,pading,0,0);
		 */
		pullDownRefresh.setPadding(0, 0, 0, -pullDownRefreshHeight);

		addHeaderView(headView);
		/**
		 * 设置整个头文件的touch事件,防止头文件在ListView中的item的点击事件
		 */
		headView.setOnTouchListener(new OnTouchListener() {

			@Override
			public boolean onTouch(View v, MotionEvent event) {
				return true;
			}
		});
	}

	/**
	 * 拦截事件
	 */
	private int startx, starty;

	// @Override
	// public boolean onInterceptTouchEvent(MotionEvent ev) {
	// int evX = (int) ev.getRawX();
	// int evY = (int) ev.getRawY();
	// switch (ev.getAction()) {
	// case MotionEvent.ACTION_DOWN:
	// Log.e("TAG", "拦截ondown");
	// startx = evX;
	// starty = evY;
	// break;
	// case MotionEvent.ACTION_MOVE:
	// int totlex = Math.abs(evX - startx);
	// int totley = Math.abs(evY - starty);
	// Log.e("TAG", "不拦截");
	// if (totley > totlex && totley > 5) {
	// Log.e("TAG", "拦截");
	// return true;
	// }
	//
	// break;
	// case MotionEvent.ACTION_UP:
	//
	// break;
	//
	// default:
	// break;
	// }
	// return super.onInterceptTouchEvent(ev);
	// }

	/**
	 * 初始化上拉加载更多布局，并且把上拉加载更多布局添加到ListView的尾部
	 * 
	 * @param context
	 */
	private void initFootView(Context context) {
		footView = (RelativeLayout) View.inflate(context,
				R.layout.listview_footview_loadmore, null);
		// 在此这行setLayoutParams可以防止footView.measure(0, 0)空指针
		footView.setLayoutParams(new LayoutParams(LayoutParams.WRAP_CONTENT,
				LayoutParams.WRAP_CONTENT));
		// 测量并得到尾视图的高pullDownRefresh.measure(0, 0);
		footView.measure(0, 0);
		footViewHeight = footView.getMeasuredHeight();

		// 获取加载更多中的箭头
		iv_loadmore = (ImageView) footView.findViewById(R.id.iv_loadmore);

		// 获取加载更多中的ProgressBar
		pb_loadmore = (ProgressBar) footView.findViewById(R.id.pb_loadmore);

		// 获取加载更多的文本
		tv_loading_more = (TextView) footView
				.findViewById(R.id.tv_loading_more);

		// 默认隐藏并添加到ListView的后面
		footView.setPadding(0, 0, 0, -footViewHeight);
		addFooterView(footView);

	}

	/**
	 * 初始动画
	 * 
	 * @param context
	 */
	private void initAnimation(Context context) {
		upAnimation = new RotateAnimation(0, -180, Animation.RELATIVE_TO_SELF,
				0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
		upAnimation.setDuration(400);
		upAnimation.setFillAfter(true);

		downAnimation = new RotateAnimation(-180, -360,
				Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF,
				0.5f);
		downAnimation.setDuration(400);
		downAnimation.setFillAfter(true);
	}

	/**
	 * 记录下拉刷新的起始坐标
	 */
	private float startY = 0;

	@Override
	public boolean onTouchEvent(MotionEvent ev) {

		switch (ev.getAction()) {
		case MotionEvent.ACTION_DOWN:
			startY = ev.getY();
			break;
		case MotionEvent.ACTION_MOVE:

			if (startY == 0) {
				startY = ev.getY();
			}

			float endY = ev.getY();
			float distanceY = endY - startY;

			if (distanceY > 0) {
				// 如果是正在刷新就不处理
				if (currentrefreshstatus == REFRESHING) {
					break;
				}

				// 如果顶部轮播图没有完全显示，则没必要动态显示下拉刷新控件
				boolean isDispalySecondView = isDispalySecondView();
				if (!isDispalySecondView) {
					break;
				}

				int padding = (int) (-pullDownRefreshHeight + distanceY);

				/**
				 * view.setPading(0,-控件高,0,0);//完全隐藏状态
				 * view.setPading(0,0,0,0);//完全显示
				 * view.setPading(0,控件高,0,0);//完全两倍高方式显示
				 */
				if (padding >= 0 && currentrefreshstatus != RELEASE_REFRESH) {
					currentrefreshstatus = RELEASE_REFRESH;
					refreshHeadStatus();

				} else if (padding < 0
						&& currentrefreshstatus != PULL_DOWN_REFRESH) {
					currentrefreshstatus = PULL_DOWN_REFRESH;
					refreshHeadStatus();
				}
				pullDownRefresh.setPadding(0, padding, 0, 0);

				// return super.onTouchEvent(ev);
				return true;
			} else if (distanceY < 0 && isfoodview) {
				// 如果正在加载不处理
				if (currentloadmorestatus == LOADING) {
					break;
				}
				// 如果ListView没有到最后，不处理
				boolean islowest = islowest();
				if (!islowest) {
					break;
				}
				int padding = (int) (-footViewHeight - distanceY);

				if (padding >= 0 && currentloadmorestatus != LOADED_REFRESH) {
					currentloadmorestatus = LOADED_REFRESH;
					loadfootStatus();

				} else if (padding < 0
						&& currentloadmorestatus != PULL_UP_REFRESH) {
					currentloadmorestatus = PULL_UP_REFRESH;
					loadfootStatus();
				}

				footView.setPadding(0, 0, 0, padding);

				return super.onTouchEvent(ev);

			} else {
				return super.onTouchEvent(ev);
			}

		case MotionEvent.ACTION_UP:

			/**
			 * 下拉刷新的状态改变
			 */

			if (currentrefreshstatus == PULL_DOWN_REFRESH) {
				pullDownRefresh.setPadding(0, -pullDownRefreshHeight, 0, 0);
			} else if (currentrefreshstatus == RELEASE_REFRESH) {
				currentrefreshstatus = REFRESHING;
				refreshHeadStatus();
				pullDownRefresh.setPadding(0, 0, 0, 0);

				// 联网请求-接口
				if (onRefreshListener != null) {
					onRefreshListener.onPullDownRefresh();
				}
			}

			/**
			 * 加载更多的状态改变
			 */
			if (isfoodview) {
				if (currentloadmorestatus == PULL_UP_REFRESH) {
					footView.setPadding(0, 0, 0, -footViewHeight);
				} else if (currentloadmorestatus == LOADED_REFRESH) {
					currentloadmorestatus = LOADING;
					loadfootStatus();
					footView.setPadding(0, 0, 0, 0);

					// 联网请求-接口
					if (onRefreshListener != null) {
						onRefreshListener.onLoadingMore();
					}
				}
			}

			startY = 0;
			break;

		default:
			break;
		}
		return super.onTouchEvent(ev);
	}

	/**
	 * 根据需求设置是否添加脚视图
	 */
	public void setisfootview(boolean isfootview) {
		this.isfoodview = isfootview;
	}

	/**
	 * 根据需求设置是否显示播放的按钮
	 */
	public void setplayvisibility(int visibility) {
		iv_prevue_play.setVisibility(visibility);
	}

	/**
	 * 得到播放的按钮
	 */
	public ImageView getplayview() {
		return iv_prevue_play;
	}

	/**
	 * 修改headView刷新状态
	 */
	private void refreshHeadStatus() {

		switch (currentrefreshstatus) {
		case PULL_DOWN_REFRESH:
			iv_refresh.startAnimation(downAnimation);
			tv_refresh.setText("下拉刷新...");
			break;
		case RELEASE_REFRESH:
			iv_refresh.startAnimation(upAnimation);
			tv_refresh.setText("松手刷新...");
			break;
		case REFRESHING:
			iv_refresh.clearAnimation();
			iv_refresh.setVisibility(View.GONE);
			pb_refresh.setVisibility(View.VISIBLE);
			tv_refresh.setText("正在刷新...");
			break;

		default:
			break;
		}
	}

	/**
	 * 修改foodView的刷新状态
	 */
	private void loadfootStatus() {
		switch (currentloadmorestatus) {
		case PULL_UP_REFRESH:
			iv_loadmore.startAnimation(upAnimation);
			tv_loading_more.setText("上拉加载更多...");
			break;
		case LOADED_REFRESH:
			iv_loadmore.startAnimation(downAnimation);
			tv_loading_more.setText("释放加载...");
			break;
		case LOADING:
			iv_loadmore.clearAnimation();
			iv_loadmore.setVisibility(View.GONE);
			pb_loadmore.setVisibility(View.VISIBLE);
			tv_loading_more.setText("正在载入...");
			break;

		default:
			break;
		}
	}

	/**
	 * footView 判断TopView是否完全显示
	 * 
	 * @return
	 */
	private boolean isDispalySecondView() {

		int[] location = new int[2];
		// 得到listView在屏幕上Y轴的坐标
		if (listViewOnScreenY == 0) {
			this.getLocationOnScreen(location);
			listViewOnScreenY = location[1];
		}

		// 得到顶部轮播图在屏幕上Y轴的坐标

		topview.getLocationOnScreen(location);
		int topNewsViewOnScreenY = location[1];

		return listViewOnScreenY <= topNewsViewOnScreenY;
	}

	/**
	 * 判断ListView是否到最后了
	 * 
	 * @return
	 */
	public boolean islowest() {
		if (getAdapter() != null) {
			return getLastVisiblePosition() == getAdapter().getCount() - 1;
		}
		return true;
	}

	/**
	 * 在头文件中添加新的视图
	 * 
	 * @param topnewsView
	 */
	public void addSecondView(View topnewsView) {
		if (topnewsView != null) {
			headView.addView(topnewsView);
		}
	}

	/**
	 * 下拉刷新联网请求完成
	 * 
	 * @param b
	 */
	public void onFinishRefresh(AdvWordList advwordlist) {

		currentrefreshstatus = PULL_DOWN_REFRESH;
		tv_refresh.setText("下拉刷新...");
		iv_refresh.clearAnimation();
		iv_refresh.setVisibility(View.VISIBLE);
		pb_refresh.setVisibility(View.GONE);

		pullDownRefresh.setPadding(0, -pullDownRefreshHeight, 0, 0);
		// 重新设刷新视图的数据
		if (advwordlist != null) {
			setrefreshview(advwordlist.movieName, advwordlist.word);
		}
	}

	/**
	 * 上拉加载更多完成
	 */
	public void onLoadingFinish() {
		currentloadmorestatus = PULL_UP_REFRESH;
		tv_loading_more.setText("上拉加载更多...");
		iv_loadmore.clearAnimation();
		iv_loadmore.setVisibility(View.VISIBLE);
		pb_loadmore.setVisibility(View.GONE);

		footView.setPadding(0, 0, 0, -footViewHeight);
	}

	/**
	 * 设置刷新视图中的电影名称和电影描述
	 * 
	 * @param title
	 * @param describe
	 */
	public void setrefreshview(String title, String describe) {
		tv_refresh_title.setText(title);
		tv_refresh_describe.setText(describe);
	}

	/**
	 * 设置topview中的图片和文本描述
	 * 
	 * @param title
	 * @param describe
	 */
	public void settopview(Bitmap bitmap, String name) {
		iv_header.setImageBitmap(bitmap);
		tv_header_movies_describe.setText(name);

	}
}
