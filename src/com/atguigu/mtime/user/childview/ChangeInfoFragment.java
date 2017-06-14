package com.atguigu.mtime.user.childview;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;

import com.atguigu.mtime.MainActivity;
import com.atguigu.mtime.R;
import com.atguigu.mtime.UserGrandChildRootActivity;
import com.atguigu.mtime.user.data.LoacalUserInfo;
import com.atguigu.mtime.utils.CacheUtils;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.BitmapFactory.Options;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ImageView.ScaleType;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

public class ChangeInfoFragment extends Fragment implements OnClickListener {
	private static final String GRANDPOSITION = "GRANDPOSITION";
	private static final int HEAD = 204;
	private static final int NICKNAME = 205;
	private static final int SEX = 206;
	private static final int PHONE = 207;
	private static final int CHANGEPASSWORD = 208;
	private static final int RECEIVEADDRESS = 209;
	private static final int REFRESH = 255;
	private static final int FIND_PHOTO = 256;
	private static String UserName;
	private static Context context;
	LoacalUserInfo localUserInfo;
	private View view;
	private Intent intent;
	private ImageView iv_fm_user_changeinfo_back;
	private ImageView iv_fm_user_changeinfo_headicon;
	private TextView tv_fm_user_changeinfo_name;
	private TextView tv_fm_user_changeinfo_sex;
	private TextView tv_fm_user_changeinfo_phonenum;
	private LinearLayout ll_fm_user_changeinfo_name;
	private LinearLayout ll_fm_user_changeinfo_sex;
	private LinearLayout ll_fm_user_changeinfo_phonenum;
	private LinearLayout ll_fm_user_changeinfo_changepassword;
	private LinearLayout ll_fm_user_changeinfo_address;
	private RelativeLayout rl_fm_user_changeinfo_head;

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		this.context = getActivity();
		UserName = CacheUtils.getString(getActivity(), "ISLOG");
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		view = View.inflate(context, R.layout.fragment_user_change_info, null);
		initLayout();
		initOnClick();
		initData();
		return view;
	}

	private void initData() {
		intent = new Intent(context, UserGrandChildRootActivity.class);
		localUserInfo = (LoacalUserInfo) getActivity().getApplication();
		tv_fm_user_changeinfo_name.setText(localUserInfo.getUserName());
		tv_fm_user_changeinfo_sex.setText(localUserInfo.getSex());
		String headNum = localUserInfo.getPhoneNum().substring(0, 4);
		String endNum = localUserInfo.getPhoneNum().substring(8);
		tv_fm_user_changeinfo_phonenum.setText(headNum + "****" + endNum);
		if (localUserInfo.getDrawIcon() != null) {
			Log.e("TAG", "设置了新图片");
			iv_fm_user_changeinfo_headicon.setBackgroundDrawable(localUserInfo
					.getDrawIcon());
			// try {
			// File file = new File(localUserInfo.getMimgPath());
			// if(file .exists()){
			// FileInputStream is = new FileInputStream(file );
			// Bitmap newbitmap = BitmapFactory.decodeStream(is );
			//
			// iv_fm_user_changeinfo_headicon.setImageBitmap(newbitmap);
			//
			// is.close();
			//
			// }else {
			// Toast.makeText(context, "文件不存在", Toast.LENGTH_SHORT).show();
			// }
			//
			//
			// } catch (Exception e) {
			// // TODO Auto-generated catch block
			// e.printStackTrace();
			// }
		} else {
			iv_fm_user_changeinfo_headicon
					.setBackgroundResource(R.drawable.image_user_changeinfo_head);
		}
	}

	private void initOnClick() {
		iv_fm_user_changeinfo_back.setOnClickListener(this);
		ll_fm_user_changeinfo_name.setOnClickListener(this);
		ll_fm_user_changeinfo_sex.setOnClickListener(this);
		ll_fm_user_changeinfo_phonenum.setOnClickListener(this);
		ll_fm_user_changeinfo_changepassword.setOnClickListener(this);
		ll_fm_user_changeinfo_address.setOnClickListener(this);
		rl_fm_user_changeinfo_head.setOnClickListener(this);
	}

	private void initLayout() {
		iv_fm_user_changeinfo_back = (ImageView) view
				.findViewById(R.id.iv_fm_user_changeinfo_back);
		tv_fm_user_changeinfo_name = (TextView) view
				.findViewById(R.id.tv_fm_user_changeinfo_name);
		tv_fm_user_changeinfo_sex = (TextView) view
				.findViewById(R.id.tv_fm_user_changeinfo_sex);
		tv_fm_user_changeinfo_phonenum = (TextView) view
				.findViewById(R.id.tv_fm_user_changeinfo_phonenum);
		ll_fm_user_changeinfo_name = (LinearLayout) view
				.findViewById(R.id.ll_fm_user_changeinfo_name);
		ll_fm_user_changeinfo_sex = (LinearLayout) view
				.findViewById(R.id.ll_fm_user_changeinfo_sex);
		ll_fm_user_changeinfo_phonenum = (LinearLayout) view
				.findViewById(R.id.ll_fm_user_changeinfo_phonenum);
		ll_fm_user_changeinfo_changepassword = (LinearLayout) view
				.findViewById(R.id.ll_fm_user_changeinfo_changepassword);
		ll_fm_user_changeinfo_address = (LinearLayout) view
				.findViewById(R.id.ll_fm_user_changeinfo_address);
		rl_fm_user_changeinfo_head = (RelativeLayout) view
				.findViewById(R.id.rl_fm_user_changeinfo_head);
		iv_fm_user_changeinfo_headicon = (ImageView) view
				.findViewById(R.id.iv_fm_user_changeinfo_headicon);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_fm_user_changeinfo_back:
			Intent localIntetn = new Intent(context, MainActivity.class);
			localIntetn.putExtra("Position", 4);
			startActivity(localIntetn);
			getActivity().finish();
			break;

		case R.id.ll_fm_user_changeinfo_name:
			intent.putExtra(GRANDPOSITION, NICKNAME);
			startActivity(intent);
			break;

		case R.id.ll_fm_user_changeinfo_sex:
			intent.putExtra(GRANDPOSITION, SEX);
			startActivity(intent);
			break;

		case R.id.ll_fm_user_changeinfo_phonenum:
			intent.putExtra(GRANDPOSITION, PHONE);
			startActivity(intent);
			break;

		case R.id.rl_fm_user_changeinfo_head:
			Intent i = new Intent(
					Intent.ACTION_PICK,
					android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);// 调用android的图库
			startActivityForResult(i, FIND_PHOTO);
			break;

		case R.id.ll_fm_user_changeinfo_changepassword:
			intent.putExtra(GRANDPOSITION, CHANGEPASSWORD);
			startActivity(intent);
			break;

		case R.id.ll_fm_user_changeinfo_address:
			intent.putExtra(GRANDPOSITION, RECEIVEADDRESS);
			startActivity(intent);
			break;

		default:
			break;
		}
	}
}
