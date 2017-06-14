package com.atguigu.mtime.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

import android.graphics.Bitmap;
import android.graphics.Bitmap.CompressFormat;
import android.graphics.BitmapFactory;

/**
 * 本地缓存工具类
 * 
 * @author 杨光福
 * @Time 2015-12-4 上午9:42:46
 */
public class LocalCacheUtils {

	private static final String MNT_SDCARD_BEIJINGNEWS = "/mnt/sdcard/zhe800/";

	/**
	 * 根据Url保存图片到本地
	 * 
	 * @param url
	 *            网络地址
	 * @param bitmap
	 *            图片
	 */
	public static void putBitmap2Local(String url, Bitmap bitmap) {
		// mnt/sdcard/beijingnews/lmlssdkllll
		// 文件名需要md5加密
		try {
			String fileName = MD5Encoder.encode(url);// lmlssdkllll
			File file = new File(MNT_SDCARD_BEIJINGNEWS, fileName);//// mnt/sdcard/beijingnews/lmlssdkllll
			File parentFiel = file.getParentFile();// mnt/sdcard/beijingnews/
			if (!parentFiel.exists()) {
				parentFiel.mkdirs();
			}
			FileOutputStream fileOutputStream = new FileOutputStream(file);
			bitmap.compress(CompressFormat.PNG, 100, fileOutputStream);
			fileOutputStream.flush();
			fileOutputStream.close();

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	/**
	 * 根据Url得到图片
	 * 
	 * @param url
	 * @return
	 */
	public static Bitmap getBitmapFromUrl(String url) {
		try {
			String fileName = MD5Encoder.encode(url);// lmlssdkllll
			File file = new File(MNT_SDCARD_BEIJINGNEWS, fileName);///// mnt/sdcard/beijingnews/lmlssdkllll
			if (file.exists()) {

				FileInputStream fis = new FileInputStream(file);
				Bitmap bitmap = BitmapFactory.decodeStream(fis);
				fis.close();
				return bitmap;
			}

		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
	
	

}
