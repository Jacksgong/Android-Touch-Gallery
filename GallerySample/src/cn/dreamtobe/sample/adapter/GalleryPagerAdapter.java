package cn.dreamtobe.sample.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.text.TextUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import cn.dreamtobe.sample.AppUtil;
import cn.dreamtobe.sample.constant.CacheImage;
import cn.dreamtobe.sample.constant.Path;
import cn.dreamtobe.touchgallery.BaseGalleryPagerAdapter;

/**
 * @author Jacks gong
 * @data Nov 21, 2014 10:43:51 AM
 * @Describe
 */
public class GalleryPagerAdapter extends BaseGalleryPagerAdapter {

	public GalleryPagerAdapter() {
		super();
	}

	public GalleryPagerAdapter(Context context, List<String> resources) {
		super(context, resources);
		setLruSoftCache(CacheImage.CACHE_IV);
	}

	@Override
	public void save(String url, Bitmap bm) {
		saveBitmap(url, bm);
	}

	@Override
	public String getKey(String url) {
		return url;
	}

	@Override
	public String getPath(String url) {
		return Path.IV + AppUtil.md5(url);
	}

	protected void saveBitmap(String url, Bitmap b) {
		if (TextUtils.isEmpty(url) || b == null) {
			return;
		}

		if (!AppUtil.hasSDCard()) {
			return;
		}

		String path = getPath(url);

		if (path == null) {
			return;
		}

//		path += (suffix == null ? "" : suffix);

		File file = new File(path);

		if (file.exists()) {
			return;
		} else {
			file.getParentFile().mkdirs();
		}

		FileOutputStream output = null;
		try {
			output = new FileOutputStream(file);
			b.compress(Bitmap.CompressFormat.PNG, 100, output);
			output.flush();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (output != null) {
					output.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}

		}

	}

}
