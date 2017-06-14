package com.atguigu.mtime.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.ImageLoader.ImageCache;
import com.android.volley.toolbox.ImageLoader.ImageListener;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v4.util.LruCache;
import android.widget.ImageView;

/**
 * 下载图片的类
 * 
 * @author 朱志强
 *
 */
public class HttpDownBitmap {

	/**
	 * LruCache图片缓存类，使用LIFO算法，当缓存的图片达到一定值的时候就会将最久最长未使用的图片释放
	 */
	int cacheSize = 4 * 1024 * 1024; // 4MiB
	private LruCache<String, Bitmap> lruCache = new LruCache<String, Bitmap>(cacheSize) {
		protected int sizeOf(String key, Bitmap bitmap) {
			return bitmap.getRowBytes() * bitmap.getHeight();
		};
	};

	private HttpDownBitmap() {
	}

	private static HttpDownBitmap httpDownBitmap = null;

	public static HttpDownBitmap getInstance() {
		if (httpDownBitmap == null) {
			synchronized (HttpDownBitmap.class) {
				if (httpDownBitmap == null) {
					httpDownBitmap = new HttpDownBitmap();
				}
			}
		}
		return httpDownBitmap;
	}

	private Bitmap compressImage(Bitmap image) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		image.compress(Bitmap.CompressFormat.JPEG, 100, baos);// 质量压缩方法，这里100表示不压缩，把压缩后的数据存放到baos中
		int options = 100;
		while (baos.toByteArray().length / 1024 > 100) { // 循环判断如果压缩后图片是否大于100kb,大于继续压缩
			baos.reset();// 重置baos即清空baos
			options -= 10;// 每次都减少10
			image.compress(Bitmap.CompressFormat.JPEG, options, baos);// 这里压缩options%，把压缩后的数据存放到baos中

		}
		ByteArrayInputStream isBm = new ByteArrayInputStream(baos.toByteArray());// 把压缩后的数据baos存放到ByteArrayInputStream中
		Bitmap bitmap = BitmapFactory.decodeStream(isBm, null, null);// 把ByteArrayInputStream数据生成图片
		return bitmap;
	}

	ImageCache imageCache = new ImageCache() {

		@Override
		public void putBitmap(String url, Bitmap bitmap) {
			LogUtil.i("putBitmap())");
//			lruCache.put(url, bitmap);
			// LogUtil.i("LruCache Size : " + lruCache.size());
		}

		@Override
		public Bitmap getBitmap(String url) {
			LogUtil.i("getBitmap())");
			return lruCache.get(url);
		}
	};

	public void setImage(Context context, final ImageView imageView, final String imageUrl) {

		ImageLoader imageLoader = new ImageLoader(VolleyUtil.getQueue(context), imageCache);
		ImageListener imageListener = imageLoader.getImageListener(imageView, 0, 0);
		imageLoader.get(imageUrl, imageListener);

	}
	// public static void setImage(Context context, final ImageView imageView,
	// final String imageUrl) {
	//
	// // 1、首先从内存中获取
	// Bitmap bitmap = lruCache.get(imageUrl);
	// if (bitmap != null) {
	// imageView.setImageBitmap(bitmap);
	// LogUtil.i("从lru中获取");
	// return;
	// }
	//
	// // 2、如果没有的话，就从网络上面下载
	// // 取消这个ImageView已有的请求
	// VolleyUtil.getQueue(context).cancelAll(imageView);
	//
	// ImageRequest request = new ImageRequest(StringUtil.preUrl(imageUrl), new
	// Listener<Bitmap>() {
	// @Override
	// public void onResponse(Bitmap bitmap) {
	// imageView.setImageBitmap(bitmap);
	// lruCache.put(imageUrl, bitmap);
	// }
	// }, 0, 0, Config.RGB_565, new ErrorListener() {
	// @Override
	// public void onErrorResponse(VolleyError arg0) {
	// imageView.setImageResource(R.drawable.login_password_icon);
	// }
	// });
	// request.setTag(imageView); // 用于取消请求
	// VolleyUtil.getQueue(context).add(request);
	//
	// }

}
