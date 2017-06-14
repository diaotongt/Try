package com.atguigu.mtime.utils;

/**
 * 用于格式化时间的工具类
 * @author Wangnan
 *
 */
public final class TimeDateUtils {
	
	/**
	 * 格式化时间的方法
	 * @param millisecond
	 * @return 根据传入毫秒数,返回String类型的："_天前、_小时前、_分钟前、_刚刚";
	 * @author Wangnan
	 */
	public static String FormatTime(long millisecond){
		if((int)(millisecond / (24 * 3600 * 1000)) > 0){
			return (int)(millisecond / (24 * 3600 * 1000))+"天前";
		}else if((int)(millisecond / (3600 * 1000)) > 0){
			return (int)(millisecond / (3600 * 1000)) + "小时前";
		}else if((int)(millisecond / (60 * 1000)) > 0){
			return (int)(millisecond / (60 * 1000)) + "分钟前";
		}else{
			return "刚刚";
		}
	}
}
