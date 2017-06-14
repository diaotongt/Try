package com.atguigu.mtime.utils;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

public class LogUtil {

	private static final int flag = 1;

	/**
	 *  打印Log信息
	 * @param content
	 */
	public static void i(String content) {
		if (flag == 1) {
			Log.i("DTT", content);
		}
	}
	/**
	 *  打印Log错误信息
	 * @param content
	 */
	public static void e(String content) {
		if (flag == 1) {
			Log.e("DTT", content);
		}
	}

}
