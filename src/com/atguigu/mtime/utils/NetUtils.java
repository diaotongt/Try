package com.atguigu.mtime.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public final class NetUtils {
	
	/**
	 * 判断是否联网
	 * ConnectivityManager
	 * @param context
	 * @return
	 */
	public static boolean isConnected(Context context) {
		boolean connected = false;
		//获取手机所有连接的管理器(包括wifi，net等连接的管理)
		ConnectivityManager manager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
		
		// 获取active的NetworkInfo对象
		NetworkInfo networkInfo = manager.getActiveNetworkInfo();
		if(networkInfo != null){
			//得到是否为连接状态
			connected = networkInfo.isConnected();
		}
		return connected;
	}
}
