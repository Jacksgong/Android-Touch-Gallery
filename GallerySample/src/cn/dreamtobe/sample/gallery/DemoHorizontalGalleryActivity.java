package cn.dreamtobe.sample.gallery;

import android.app.Activity;
import android.os.Bundle;

import java.util.ArrayList;
import java.util.List;

import cn.dreamtobe.sample.adapter.GalleryPagerAdapter;
import cn.dreamtobe.touchgallery.GalleryWidget.GalleryViewPager;

/**
 * Created by Jacks gong on 12/14/14.
 */
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

	private void initDemoData() {
		urls.add("http://f.hiphotos.baidu.com/image/w%3D2048/sign=527f77af184c510faec4e51a5461242d/d1a20cf431adcbefa332d761aeaf2edda3cc9f57.jpg");
		urls.add("http://d.hiphotos.baidu.com/image/w%3D2048/sign=1b1f93ad533d26972ed30f5d61c3b3fb/023b5bb5c9ea15ce8d204e6eb4003af33b87b28f.jpg");
		urls.add("http://f.hiphotos.baidu.com/image/w%3D2048/sign=eccebdc05bee3d6d22c680cb772e6c22/c8ea15ce36d3d5390bf3a1a43887e950342ab08f.jpg");
		urls.add("http://h.hiphotos.baidu.com/image/w%3D2048/sign=81d092a09252982205333ec3e3f27acb/11385343fbf2b21154e02b29c88065380dd78e8f.jpg");
		urls.add("http://c.hiphotos.baidu.com/image/w%3D2048/sign=0929195558afa40f3cc6c9dd9f5c024f/a08b87d6277f9e2f7dd1b36f1d30e924b999f3f5.jpg");
	}

}
