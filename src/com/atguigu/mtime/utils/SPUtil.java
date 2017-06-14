package com.atguigu.mtime.utils;

import android.content.Context;
import android.content.SharedPreferences;

public final class SPUtil {

	public static final String CITY = "CITY";
	public static final String CITY_DATA = "CITY_DATA";

	private static SPUtil spUtil = null;
	private static SharedPreferences sp = null;

	private SPUtil() {
	}

	public static SPUtil getInstance(Context context) {
		if (spUtil == null) {
			spUtil = new SPUtil();
			sp = context.getSharedPreferences("ms_sp", context.MODE_PRIVATE);
		}
		return spUtil;
	}

	public void putValue(String key, Object value) {
		if (value instanceof String) {
			sp.edit().putString(key, (String) value).commit();
		} else if (value instanceof Boolean) {
			sp.edit().putBoolean(key, (Boolean) value).commit();
		} else if (value instanceof Integer) {
			sp.edit().putInt(key, (Integer) value).commit();
		}
	}

	// 删除数据
	public void removeValue(String key) {
		sp.edit().remove(key);
	}

	public <T> T getValue(String key, T defValue) {
		T t = null;
		if (defValue == null || defValue instanceof String) {
			t = (T) sp.getString(key, (String) defValue);
		} else if (defValue instanceof Boolean) {
			Boolean boolean1 = sp.getBoolean(key, (Boolean) defValue);
			t = (T) boolean1;
		} else if (defValue instanceof Integer) {
			Integer int1 = sp.getInt(key, (Integer) defValue);
			t = (T) int1;
		}
		return t;
	}

}
