package com.atguigu.mtime.utils;

import com.atguigu.mtime.discover.bean.DisCoverTopBean;
import com.atguigu.mtime.discover.bean.Discover_Refresh_Bean;

/**
 * 常量工具类
 * 
 * @author Mr lu
 *
 */
public class Constant {
	public static Discover_Refresh_Bean discover_refresh_bean;

	/**
	 * 设置顶部刷新数据视图的对象
	 */
	public static void setDisCoverTopBean(
			Discover_Refresh_Bean discover_refresh_bean) {
		Constant.discover_refresh_bean = discover_refresh_bean;
	}

	/**
	 * 获取顶部刷新数据视图的对象
	 */
	public static Discover_Refresh_Bean getDisCoverTopBean() {
		return discover_refresh_bean;
	}
}
