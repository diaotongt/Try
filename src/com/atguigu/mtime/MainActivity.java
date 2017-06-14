package com.atguigu.mtime;

import com.atguigu.mtime.fragment.DiscoverFragment;
import com.atguigu.mtime.fragment.HomeFragment;
import com.atguigu.mtime.fragment.PayTicketFragment;
import com.atguigu.mtime.fragment.ShopFragment;
import com.atguigu.mtime.fragment.UserFragment;
import com.atguigu.mtime.utils.CacheUtils;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Window;
import android.widget.RadioGroup;
import android.widget.Toast;

/**
 * 主界面Activity（注意：继承至FragmentActivity）
 * 
 * @author Wangnan
 *
 */
public class MainActivity extends FragmentActivity {

	private static final int REFRESH = 255;
	
	/**
	 * 用于连点两次返回键，时间超过2秒，还原isExit的消息标识
	 */
	private static final int BACK_REFRESH = 1;
	private String UserName = CacheUtils.getString(MainActivity.this, "ISLOG");
	/**
	 * 单选按钮组，用于切换不同页面
	 * @author Wangnan
	 */
	public RadioGroup rg_main;  //单选组按钮
	public static String isLoging;  //模拟登陆后用来通信fragment的用户数据
	private HomeFragment homeFragment = null;  //首页Fragment
	private PayTicketFragment payTicketFragment = null;  //购票Fragment
	private ShopFragment shopFragment = null;  //商城Fragment
	private DiscoverFragment discoverFragment = null;  //发现Fragment
	private UserFragment userFragment = null;  //我的Fragment
	private int position; //其他Activity与MainActivity完成通信后设置当前应该显示的Fragment位置

	private Bundle savedInstanceState;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		this.savedInstanceState = savedInstanceState;
		super.onCreate(savedInstanceState);
		// 去掉标题
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);

		// 初始化视图
		initView();

		// 初始化监听器
		initListener();

		// 初始选中第一个Fragment
		rg_main.check(R.id.rb_home);
	}


	/**
	 * 初始化视图
	 * 
	 * @author Wangnan
	 */
	private void initView() {
		rg_main = (RadioGroup) findViewById(R.id.rg_main);
	}

	/**
	 * 初始化监听器
	 * 
	 * @author Wangnan
	 */
	private void initListener() {
		rg_main.setOnCheckedChangeListener(new MainOnCheckedChangeListener());
		
	}
		
	/**
	 * 选中某个RadioButton的监听
	 * @author Wangnan
	 */
	class MainOnCheckedChangeListener implements RadioGroup.OnCheckedChangeListener{

		@Override
		public void onCheckedChanged(RadioGroup group, int checkedId) {
			
			//获取开始事物的对象
			FragmentTransaction beginTransaction = getSupportFragmentManager().beginTransaction();
			
			//隐藏所有的Fragment
			hideFragment(beginTransaction);
			
			switch (checkedId) {
			case R.id.rb_home:     //RadioGroup选中首页
				
				if(homeFragment == null){
					homeFragment = new HomeFragment();
					beginTransaction.add(R.id.fl_main, homeFragment);
				}else{
					beginTransaction.show(homeFragment);
				}
				break;
				
			case R.id.rb_payticket://RadioGroup选中购票
				
				if(payTicketFragment == null){
					payTicketFragment = new PayTicketFragment();
					beginTransaction.add(R.id.fl_main, payTicketFragment);
				}else{
					beginTransaction.show(payTicketFragment);
				}
				break;
				
			case R.id.rb_shop:     //RadioGroup选中商城
				
				if(shopFragment == null){
					shopFragment = new ShopFragment();
					beginTransaction.add(R.id.fl_main, shopFragment);
				}else{
					beginTransaction.show(shopFragment);
				}
				break;
				
			case R.id.rb_discover: //RadioGroup选中发现
				
				if(discoverFragment == null){
					discoverFragment = new DiscoverFragment();
					beginTransaction.add(R.id.fl_main, discoverFragment);
				}else{
					beginTransaction.show(discoverFragment);
				}
				break;
				
			case R.id.rb_user:      //RadioGroup选中我的
				
				if(userFragment == null){
					userFragment = new UserFragment();
					beginTransaction.add(R.id.fl_main, userFragment);
				}else{
					beginTransaction.show(userFragment);
				}
				break;

			default:
				break;
			}
			
			beginTransaction.commit();
		}
		
	}

	/**
	 * 将所有已经存在的Fragment设置为隐藏状态
	 * 
	 * @param beginTransaction
	 *            用于对Fragment执行操作的事务
	 * @author Wangnan
	 */
	public void hideFragment(FragmentTransaction beginTransaction) {
		if(homeFragment != null){
			beginTransaction.hide(homeFragment);
		}
		if(payTicketFragment != null){
			beginTransaction.hide(payTicketFragment);
		}
		if(shopFragment != null){
			beginTransaction.hide(shopFragment);
		}
		if(discoverFragment != null){
			beginTransaction.hide(discoverFragment);
		}
		if(userFragment != null){
			beginTransaction.hide(userFragment);
		}
		
	}
	
	//启动模式为SingelTask的Activity,在用带回掉的方式启动时，会出现界面重复(启动模式失效)
	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		UserName = CacheUtils.getString(MainActivity.this, "ISLOG");
		position = intent.getIntExtra("Position", -1);

		switch (position) {
		case 0:
			rg_main.check(R.id.rb_home);
			Log.e("TAG", "是选择0？");
			break;
		case 1:
			rg_main.check(R.id.rb_payticket);
			Log.e("TAG", "是选择1？");
			break;
		case 2:
			rg_main.check(R.id.rb_shop);
			Log.e("TAG", "是选择2？");
			break;
		case 3:
			rg_main.check(R.id.rb_discover);
			Log.e("TAG", "是选择3？");
			break;
		case 4:
			UserFragment.handler.sendEmptyMessage(REFRESH);
			rg_main.check(R.id.rb_user);
			Log.e("TAG", "是选择4？");
			break;

		default:
			break;
		}
	}
	
	/**
	 * Fragment界面刷新的方法,但没有页面跳转功能（注：联网界面下为节省流量，尽量少用，本地界面无限制）
	 * @author Wangnan
	 * @param index(0~4)
	 */
	public void RefreshFragment(int index){
		FragmentTransaction RefreshTransaction = getSupportFragmentManager().beginTransaction();
		switch (index) {
		case 0://刷新首页Fragment
			if(homeFragment == null){
				homeFragment = new HomeFragment();
				RefreshTransaction.add(R.id.fl_main, homeFragment);
			}else{
				RefreshTransaction.remove(homeFragment);
				homeFragment = new HomeFragment();
				RefreshTransaction.add(R.id.fl_main, homeFragment);
			}
			break;
		case 1://刷新购票Fragment
			if(payTicketFragment == null){
				payTicketFragment = new PayTicketFragment();
				RefreshTransaction.add(R.id.fl_main, payTicketFragment);
			}else{
				RefreshTransaction.remove(payTicketFragment);
				payTicketFragment = new PayTicketFragment();
				RefreshTransaction.add(R.id.fl_main, payTicketFragment);			
			}
			break;
		case 2://刷新商品界面
			if(shopFragment == null){
				shopFragment = new ShopFragment();
				RefreshTransaction.add(R.id.fl_main, shopFragment);
			}else{
				RefreshTransaction.remove(shopFragment);
				shopFragment = new ShopFragment();
				RefreshTransaction.add(R.id.fl_main, shopFragment);
			}
			break;
		case 3://刷新发现界面
			if(discoverFragment == null){
				discoverFragment = new DiscoverFragment();
				RefreshTransaction.add(R.id.fl_main, discoverFragment);
			}else{
				RefreshTransaction.remove(discoverFragment);
				discoverFragment = new DiscoverFragment();
				RefreshTransaction.add(R.id.fl_main, discoverFragment);
			}
			break;
		case 4://刷新我的界面
			if(userFragment == null){
				userFragment = new UserFragment();
				RefreshTransaction.add(R.id.fl_main, userFragment);
			}else{
				RefreshTransaction.remove(userFragment);
				userFragment = new UserFragment();
				RefreshTransaction.add(R.id.fl_main, userFragment);
			}
			break;

		default:
			break;
		}
		
		RefreshTransaction.commit();
	}
	
	/**
	 * 是否退出(连点两次)
	 */
	private boolean isExit = false;	
	private Handler backHandler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			if(msg.what == BACK_REFRESH){
				isExit = false;
			}
		}
	};
	
	/**
	 * 连续点击两次退出
	 */
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		
		if(keyCode == KeyEvent.KEYCODE_BACK){
			if(!isExit){
				isExit = true;
				Toast.makeText(this, "再点一次退出", 0).show();
				backHandler.sendEmptyMessageDelayed(BACK_REFRESH, 2000);
				return true;
			}
		}
		
		return super.onKeyDown(keyCode, event);
	}
}
