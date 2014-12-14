# Android-Touch-Gallery


`Android touch gallery with net、local file or cache.`

## 1. 申明
	本项目library_gallery基于Truba的AndroidTouchGallery，往上封装一层，实现简单快速实现Viewpager上协调图片的缩放
	
## 2. 拓展部分

实现相关接口，底层即可完成自动选择从网路加载、从本地文件加载或者从Cache加载
下面是简单的案例：

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
	}
	
	
	public class DemoHorizontalGalleryActivity extends Activity {

	private GalleryViewPager mViewPager;

	private GalleryPagerAdapter mAdapter;

	final List<String> urls = new ArrayList<String>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_viewpager_gallery);

		initDemoData();

		mViewPager = (GalleryViewPager) findViewById(R.id.viewpager);
		mAdapter = new GalleryPagerAdapter(this, urls);

		mViewPager.setAdapter(mAdapter);
	}
	
详情可参看GallerySample

## 3. 运行效果
![image](https://github.com/Jacksgong/Android-Touch-Gallery/raw/master/readme/demo1.png)
![image](https://github.com/Jacksgong/Android-Touch-Gallery/raw/master/readme/demo2.png)
![image](https://github.com/Jacksgong/Android-Touch-Gallery/raw/master/readme/demo3.png)
![image](https://github.com/Jacksgong/Android-Touch-Gallery/raw/master/readme/demo4.png)
![image](https://github.com/Jacksgong/Android-Touch-Gallery/raw/master/readme/demo5.png)
License
===================
Copyright (c) 2012-2013 Roman Truba, 2014 China Jacksgong

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

	