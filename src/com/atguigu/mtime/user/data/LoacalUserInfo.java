package com.atguigu.mtime.user.data;

import com.atguigu.mtime.R;

import android.app.Application;
import android.content.res.Resources;
import android.graphics.drawable.Drawable;

public class LoacalUserInfo extends Application {
	private int headIcon;
	private String userName;
	private String sex;
	private String phoneNum;
	private Drawable drawIcon;
	private String mimgPath;
	private String passWord;

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getMimgPath() {
		return mimgPath;
	}

	public void setMimgPath(String mimgPath) {
		this.mimgPath = mimgPath;
	}

	public Drawable getDrawIcon() {
		return drawIcon;
	}

	public void setDrawIcon(Drawable drawIcon) {
		this.drawIcon = drawIcon;
	}

	@Override
	public void onCreate() {
		headIcon = R.drawable.image_user_changeinfo_head;
		userName = "zhiliandui";
		sex = "男";
		phoneNum = "18701065967";
		drawIcon=null;
		mimgPath=null;
		passWord="zhiliandui";
		super.onCreate();
	}

	public int getHeadIcon() {
		return headIcon;
	}

	public void setHeadIcon(int headIcon) {
		this.headIcon = headIcon;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public String getPhoneNum() {
		return phoneNum;
	}

	public void setPhoneNum(String phoneNum) {
		this.phoneNum = phoneNum;
	}
}
