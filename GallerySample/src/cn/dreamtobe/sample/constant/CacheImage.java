package cn.dreamtobe.sample.constant;

import android.app.ActivityManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;


import java.lang.ref.SoftReference;

import cn.dreamtobe.sample.GallerySampleApplication;

/**
 * @author Jacks gong
 * @data 2014-8-18 上午1:07:33
 * @Describe
 */
public class CacheImage {

	public final static LruCache<String, SoftReference<Object>> CACHE_IV;

	static {
		int maxMemory = calculateMemoryCacheSize(GallerySampleApplication.getContext());
		int cacheSize = maxMemory / 8;
		CACHE_IV = new LruCache<String, SoftReference<Object>>(cacheSize) {
			@Override
			protected int sizeOf(String key, SoftReference<Object> o) {
				if (o == null || o.get() == null) {
					return 0;
				}

				Bitmap b = (Bitmap) o.get();
				if (b == null || b.isRecycled()) {
					return 0;
				}
				return getBitmapSize(b) / 1024;
			}
		};
	}

	public final static LruCache<String, SoftReference<Object>> CACHE_AVATAR;

	static {
		int maxMemory = calculateMemoryCacheSize(GallerySampleApplication.getContext());
		int cacheSize = maxMemory / 8;
		CACHE_AVATAR = new LruCache<String, SoftReference<Object>>(cacheSize) {
			@Override
			protected int sizeOf(String key, SoftReference<Object> o) {
				if (o == null || o.get() == null) {
					return 0;
				}

				Bitmap b = (Bitmap) o.get();
				if (b == null || b.isRecycled()) {
					return 0;
				}

				return getBitmapSize(b) / 1024;
			}
		};
	}

	static int calculateMemoryCacheSize(Context context) {
		ActivityManager am = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
		int memoryClass = am.getMemoryClass();
		// Target 20% of the available RAM.
		int size = 1024 * 1024 * memoryClass / 5;
		// Bound to max size for mem cache.
		return Math.min(size, MAX_MEM_CACHE_SIZE);
	}

	private static final int MAX_MEM_CACHE_SIZE = 20 * 1024 * 1024; // 20MB

	public static int getBitmapSize(Bitmap bitmap) {
//		Bitmap bitmap = value.getBitmap();

		if (android.os.Build.VERSION.SDK_INT >= 12) {
			return bitmap.getByteCount();
		}
		// Pre HC-MR1
		return bitmap.getRowBytes() * bitmap.getHeight();
	}
}
