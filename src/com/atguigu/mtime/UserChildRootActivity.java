package com.atguigu.mtime;

import com.atguigu.mtime.user.childview.AdviceFragment;
import com.atguigu.mtime.user.childview.ChangeInfoFragment;
import com.atguigu.mtime.user.childview.FilmTicketOrderFragment;
import com.atguigu.mtime.user.childview.GiftCardFragment;
import com.atguigu.mtime.user.childview.GoForScoreFragment;
import com.atguigu.mtime.user.childview.GoodsOrderFragment;
import com.atguigu.mtime.user.childview.MyFilmFragment;
import com.atguigu.mtime.user.childview.MyFocusFragment;
import com.atguigu.mtime.user.childview.MyOffSetFragment;
import com.atguigu.mtime.user.childview.ScannerFragment;
import com.atguigu.mtime.user.childview.SettingsFragment;
import com.atguigu.mtime.user.childview.UserHelpFragment;
import com.atguigu.mtime.user.data.LoacalUserInfo;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.Toast;

public class UserChildRootActivity extends FragmentActivity {

	private static final String USERPOSITION = "USERPOSITION";
	private static final int FILMTICKETORDER = 4;
	private static final int GOODSORDER = 5;
	private static final int GIFTCARD = 6;
	private static final int MYOFFSET = 7;
	private static final int MYFILM = 8;
	private static final int MYFOCUS = 9;
	private static final int CHANGEINFO = 10;
	private static final int SETTINGS = 11;
	private static final int SCANNER = 12;
	private static final int ADVICE = 13;
	private static final int GOFORSCORE = 14;
	private static final int USERHELP = 15;
	private static final int FIND_PHOTO = 256;
	private int currentPosition;
	private static final String qq = "TAG";

	private AdviceFragment adviceFragment;
	private ChangeInfoFragment changeInfoFragment;
	private FilmTicketOrderFragment filmTicketOrderFragment;
	private GiftCardFragment giftCardFragment;
	private GoForScoreFragment goForScoreFragment;
	private GoodsOrderFragment goodsOrderFragment;
	private MyFilmFragment myFilmFragment;
	private MyFocusFragment myFocusFragment;
	private MyOffSetFragment myOffSetFragment;
	private ScannerFragment scannerFragment;
	private SettingsFragment settingsFragment;
	private UserHelpFragment userHelpFragment;
	LoacalUserInfo localUserInfo;
	private FrameLayout fl_act_user_child_root_main;

	// private FragmentTransaction beginTransaction;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_user_child_root);
		initLayout();
		onNewIntent(getIntent());
	}

	private void initLayout() {
		fl_act_user_child_root_main = (FrameLayout) findViewById(R.id.fl_act_user_child_root_main);
		// beginTransaction = getSupportFragmentManager().beginTransaction();
	}

	@Override
	protected void onNewIntent(Intent intent) {
		super.onNewIntent(intent);
		FragmentTransaction beginTransaction = getSupportFragmentManager()
				.beginTransaction();
		Log.e(qq, "调用了 onNewIntent()");
		int position = intent.getIntExtra(USERPOSITION, -1);
		switch (position) {
		case FILMTICKETORDER:
			currentPosition = FILMTICKETORDER;
			if (filmTicketOrderFragment == null) {
				filmTicketOrderFragment = new FilmTicketOrderFragment();
				beginTransaction.add(R.id.fl_act_user_child_root_main,
						filmTicketOrderFragment);
			} else {
				beginTransaction.show(filmTicketOrderFragment);
			}
			break;

		case GOODSORDER:
			currentPosition = GOODSORDER;
			if (goodsOrderFragment == null) {
				goodsOrderFragment = new GoodsOrderFragment();
				beginTransaction.add(R.id.fl_act_user_child_root_main,
						goodsOrderFragment);
			} else {
				beginTransaction.show(goodsOrderFragment);
			}
			break;

		case GIFTCARD:
			currentPosition = GIFTCARD;
			if (giftCardFragment == null) {
				giftCardFragment = new GiftCardFragment();
				beginTransaction.add(R.id.fl_act_user_child_root_main,
						giftCardFragment);
			} else {
				beginTransaction.show(giftCardFragment);
			}
			break;

		case MYOFFSET:
			currentPosition = MYOFFSET;
			if (myOffSetFragment == null) {
				myOffSetFragment = new MyOffSetFragment();
				beginTransaction.add(R.id.fl_act_user_child_root_main,
						myOffSetFragment);
			} else {
				beginTransaction.show(myOffSetFragment);
			}
			break;

		case MYFILM:
			currentPosition = MYFILM;
			if (myFilmFragment == null) {
				myFilmFragment = new MyFilmFragment();
				beginTransaction.add(R.id.fl_act_user_child_root_main,
						myFilmFragment);
			} else {
				beginTransaction.show(myFilmFragment);
			}
			break;

		case MYFOCUS:
			currentPosition = MYFOCUS;
			if (myFocusFragment == null) {
				myFocusFragment = new MyFocusFragment();
				beginTransaction.add(R.id.fl_act_user_child_root_main,
						myFocusFragment);
			} else {
				beginTransaction.show(myFocusFragment);
			}
			break;

		case CHANGEINFO:
			currentPosition = CHANGEINFO;
			if (changeInfoFragment == null) {
				changeInfoFragment = new ChangeInfoFragment();
				beginTransaction.add(R.id.fl_act_user_child_root_main,
						changeInfoFragment);
			} else {
				beginTransaction.remove(changeInfoFragment);
				changeInfoFragment = new ChangeInfoFragment();
				beginTransaction.add(R.id.fl_act_user_child_root_main,
						changeInfoFragment);
			}
			break;

		case SETTINGS:
			currentPosition = SETTINGS;
			if (settingsFragment == null) {
				settingsFragment = new SettingsFragment();
				beginTransaction.add(R.id.fl_act_user_child_root_main,
						settingsFragment);
			} else {
				beginTransaction.show(settingsFragment);
			}
			break;

		case SCANNER:
			currentPosition = SCANNER;
			if (scannerFragment == null) {
				scannerFragment = new ScannerFragment();
				beginTransaction.add(R.id.fl_act_user_child_root_main,
						scannerFragment);
			} else {
				beginTransaction.show(scannerFragment);
			}
			break;

		case ADVICE:
			currentPosition = ADVICE;
			if (adviceFragment == null) {
				adviceFragment = new AdviceFragment();
				beginTransaction.add(R.id.fl_act_user_child_root_main,
						adviceFragment);
			} else {
				beginTransaction.show(adviceFragment);
			}
			break;

		case GOFORSCORE:
			currentPosition = GOFORSCORE;
			if (goForScoreFragment == null) {
				goForScoreFragment = new GoForScoreFragment();
				beginTransaction.add(R.id.fl_act_user_child_root_main,
						goForScoreFragment);
			} else {
				beginTransaction.show(goForScoreFragment);
			}
			break;

		case USERHELP:
			currentPosition = USERHELP;
			if (userHelpFragment == null) {
				userHelpFragment = new UserHelpFragment();
				beginTransaction.add(R.id.fl_act_user_child_root_main,
						userHelpFragment);
			} else {
				beginTransaction.show(userHelpFragment);
			}
			break;

		default:
			break;
		}
		beginTransaction.commit();
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		Log.e("TAG", "调用了 onActivityResult()方法");

		if (currentPosition != CHANGEINFO) {
			return;
		}
		Log.e("TAG", "判断当前fragment对应的位置是否正确");
		Log.e("TAG", requestCode + "");
		switch (requestCode) {
		case 65792:
			switch (resultCode) {
			case Activity.RESULT_OK: {
				Log.e("TAG", "开始调用图片");
			}
				Uri uri = data.getData();
				Cursor cursor = UserChildRootActivity.this.getContentResolver()
						.query(uri, null, null, null, null);
				cursor.moveToFirst();
				String imgNo = cursor.getString(0); // 图片编号
				String imgPath = cursor.getString(1); // 图片文件路径
				String imgSize = cursor.getString(2); // 图片大小
				String imgName = cursor.getString(3); // 图片文件名
				cursor.close();
				Log.e("TAG", imgName);
				Log.e("TAG", imgPath);

				Options options = new BitmapFactory.Options();
				options.inJustDecodeBounds = false;
				options.inSampleSize = 2;
				Bitmap bitmap = BitmapFactory.decodeFile(imgPath, options);
				Drawable drawable = new BitmapDrawable(bitmap);
				localUserInfo = (LoacalUserInfo) UserChildRootActivity.this
						.getApplication();
				localUserInfo.setDrawIcon(drawable);
				localUserInfo.setMimgPath(imgPath);
				FragmentTransaction beginTransaction = getSupportFragmentManager()
						.beginTransaction();
				if (changeInfoFragment == null) {
					changeInfoFragment = new ChangeInfoFragment();
					beginTransaction.add(R.id.fl_act_user_child_root_main,
							changeInfoFragment);
				} else {
					beginTransaction.remove(changeInfoFragment);
					changeInfoFragment = new ChangeInfoFragment();
					beginTransaction.add(R.id.fl_act_user_child_root_main,
							changeInfoFragment);
				}
				beginTransaction.commit();
			}
			break;
		case Activity.RESULT_CANCELED:// 取消
			break;
		}
	}

}
