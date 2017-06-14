package com.atguigu.mtime.utils;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;

/**
 * 操作Volley的类，主要是管理volly的请求队列
 * 
 * @author 朱志强
 *
 */
public class VolleyUtil {

	/**
	 * volatile的作用是： 作为指令关键字，确保本条指令不会因编译器的优化而省略，且要求每次直接读值.
	 */
	private volatile static RequestQueue requestQueue;

	/**
	 * 使用双重校验的单利模式； 返回RequestQueue单例
	 **/
	public static RequestQueue getQueue(Context context) {
		if (requestQueue == null) {
			synchronized (VolleyUtil.class) {
				if (requestQueue == null) {
					requestQueue = Volley.newRequestQueue(context.getApplicationContext());
				}
			}
		}
		return requestQueue;
	}
}
