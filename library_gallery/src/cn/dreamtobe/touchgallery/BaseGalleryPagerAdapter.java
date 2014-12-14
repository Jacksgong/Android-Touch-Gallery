/*
 Copyright (c) 2014 China JacksGong

 Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 permit persons to whom the Software is furnished to do so, subject to the following conditions:

 The above copyright notice and this permission notice shall be included in all copies or substantial
 portions of the Software.

 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED
 TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL
 THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY,
 WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH
 THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */
package cn.dreamtobe.touchgallery;

import android.content.Context;
import android.graphics.Bitmap;
import android.support.v4.util.LruCache;
import android.view.ViewGroup;

import java.lang.ref.SoftReference;
import java.util.List;
import java.util.Map;

import cn.dreamtobe.touchgallery.GalleryWidget.BasePagerAdapter;
import cn.dreamtobe.touchgallery.GalleryWidget.GalleryViewPager;
import cn.dreamtobe.touchgallery.TouchView.CacheTouchImageView;
import cn.dreamtobe.touchgallery.TouchView.CacheTouchImageView.CacheTouchInterface;
import cn.dreamtobe.touchgallery.TouchView.UrlTouchImageView;

/**
 * 
 * @author Jacks gong
 * @data Nov 21, 2014 10:31:36 AM
 * @Describe
 */
public abstract class BaseGalleryPagerAdapter extends BasePagerAdapter implements CacheTouchInterface {

	private LruCache<String, SoftReference<Object>> mLruSoftCache;

	private LruCache<String, Object> mLruCache;

	private Map<String, Bitmap> mMapCache;

	public BaseGalleryPagerAdapter() {
		super();
	}

	public BaseGalleryPagerAdapter(Context context, List<String> resources) {
		super(context, resources);
	}

	@Override
	public void setPrimaryItem(ViewGroup container, int position, Object object) {
		super.setPrimaryItem(container, position, object);
		((GalleryViewPager) container).mCurrentView = ((UrlTouchImageView) object).getImageView();
	}

	@Override
	public Object instantiateItem(ViewGroup collection, int position) {
		if (mResources == null) {
			return null;
		}
		final CacheTouchImageView iv = new CacheTouchImageView(mContext);
		iv.setInterface(this);
		iv.setUrl(mResources.get(position));
		iv.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));

		collection.addView(iv, 0);
		return iv;
	}

	@Override
	public Bitmap loadCache(final String key) {
		Bitmap bm = null;
		if (mMapCache != null) {
			bm = mMapCache.get(key);
		}

		if (bm != null) {
			if (!bm.isRecycled()) {
				return bm;
			} else {
				mMapCache.remove(key);
			}
		}

		if (mLruCache != null) {
			Object o = mLruCache.get(key);
			if (o != null && o instanceof Bitmap) {
				bm = (Bitmap) o;
				mLruCache.remove(key);
				if (!bm.isRecycled()) {
					mLruCache.put(key, bm);
					return bm;
				}
			}
		}

		if (mLruSoftCache != null && mLruSoftCache.get(key) != null) {
			Object o = mLruSoftCache.get(key).get();
			if (o != null && o instanceof Bitmap) {
				bm = (Bitmap) o;
				mLruSoftCache.remove(key);
				if (!bm.isRecycled()) {
					mLruSoftCache.put(key, new SoftReference<Object>(bm));
					return bm;
				}
			}
		}

		return null;
	}

	public LruCache<String, SoftReference<Object>> getLruSoftCache() {
		return mLruSoftCache;
	}

	public void setLruSoftCache(LruCache<String, SoftReference<Object>> mLruSoftCache) {
		this.mLruSoftCache = mLruSoftCache;
	}

	public LruCache<String, Object> getLruCache() {
		return mLruCache;
	}

	public void setLruCache(LruCache<String, Object> mLruCache) {
		this.mLruCache = mLruCache;
	}

	public Map<String, Bitmap> getMapCache() {
		return mMapCache;
	}

	public void setMapCache(Map<String, Bitmap> mMapCache) {
		this.mMapCache = mMapCache;
	}
}
