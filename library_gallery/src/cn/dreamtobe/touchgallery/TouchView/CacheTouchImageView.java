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
package cn.dreamtobe.touchgallery.TouchView;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ImageView.ScaleType;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

import cn.dreamtobe.touchgallery.R;
import cn.dreamtobe.touchgallery.TouchView.InputStreamWrapper.InputStreamProgressListener;

/**
 * 
 * @author Jacks gong
 * @data Nov 21, 2014 10:02:51 AM
 * @Describe
 */
public class CacheTouchImageView extends FileTouchImageView {

	private final String TAG = "Library.CacheTouchImageView";
	private CacheTouchInterface mInterface;

	public CacheTouchImageView(Context ctx) {
		super(ctx);
	}

	public CacheTouchInterface getInterface() {
		return mInterface;
	}

	public void setInterface(CacheTouchInterface mInterface) {
		this.mInterface = mInterface;
	}

	@Override
	public void setUrl(String imageUrl) {
		new ImageLoadTask().execute(imageUrl);
	}

	public class ImageLoadTask extends AsyncTask<String, Integer, Bitmap> {
		@Override
		protected Bitmap doInBackground(String... strings) {
			String url = strings[0];
			Bitmap bm = null;
			if (mInterface == null) {
				return bm;
			}
			try {
				bm = mInterface.loadCache(mInterface.getKey(url));
				if (bm != null && !bm.isRecycled()) {
					Log.d(TAG, "load:cache");
					return bm;
				}

				bm = loadFile(mInterface.getPath(url));

				if (bm != null) {
					Log.d(TAG, "load:file");
					return bm;
				}

				bm = loadUrl(url);

				if (bm != null) {
					Log.d(TAG, "load:url");
					mInterface.save(url, bm);
					return bm;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			return bm;
		}

		@Override
		protected void onPostExecute(Bitmap bitmap) {
			if (bitmap == null) {
				mImageView.setScaleType(ScaleType.CENTER);
				bitmap = BitmapFactory.decodeResource(getResources(), R.drawable.no_photo);
				mImageView.setImageBitmap(bitmap);
			} else {
				mImageView.setScaleType(ScaleType.MATRIX);
				mImageView.setImageBitmap(bitmap);
			}
			mImageView.setVisibility(VISIBLE);
			mProgressBar.setVisibility(GONE);
		}

		@Override
		protected void onProgressUpdate(Integer... values) {
			mProgressBar.setProgress(values[0]);
		}

		private Bitmap loadUrl(final String url) {
			Bitmap bm = null;
			try {
				URL aURL = new URL(url);
				URLConnection conn = aURL.openConnection();
				conn.connect();
				InputStream is = conn.getInputStream();
				int totalLen = conn.getContentLength();
				InputStreamWrapper bis = new InputStreamWrapper(is, 8192, totalLen);
				bis.setProgressListener(new InputStreamProgressListener() {
					@Override
					public void onProgress(float progressValue, long bytesLoaded, long bytesTotal) {
						publishProgress((int) (progressValue * 100));
					}
				});
				bm = BitmapFactory.decodeStream(bis);
				bis.close();
				is.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return bm;
		}

		private Bitmap loadFile(final String path) {
			Bitmap bm = null;
			try {
				File file = new File(path);
				if (!file.exists()) {
					return bm;
				}
				FileInputStream fis = new FileInputStream(file);
				InputStreamWrapper bis = new InputStreamWrapper(fis, 8192, file.length());
				bis.setProgressListener(new InputStreamProgressListener() {
					@Override
					public void onProgress(float progressValue, long bytesLoaded, long bytesTotal) {
						publishProgress((int) (progressValue * 100));
					}
				});
				bm = BitmapFactory.decodeStream(bis);
				bis.close();
			} catch (Exception e) {
				e.printStackTrace();
			}
			return bm;
		}

	}

	public interface CacheTouchInterface {
		public void save(final String url, final Bitmap bm);

		public String getKey(final String url);

		public String getPath(final String url);

		public Bitmap loadCache(final String key);
	}

	public void clear() {
	}
}
