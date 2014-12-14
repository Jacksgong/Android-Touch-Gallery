package cn.dreamtobe.sample;

import android.app.Application;
import android.content.Context;

/**
 * Created by Jacks gong on 12/14/14.
 */
public class GallerySampleApplication extends Application {

	private static Context CONTEXT;

	@Override
	public void onCreate() {
		super.onCreate();

		CONTEXT = this;
	}

	public static Context getContext() {
		return CONTEXT;
	}
}
