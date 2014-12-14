package cn.dreamtobe.sample.constant;

import android.os.Environment;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import cn.dreamtobe.sample.AppUtil;

/**
 * Created by jacksgong on 12/14/14.
 */
public class Path {
	public static final String SDCARD = Environment.getExternalStorageDirectory().getPath();

	public static final String ROOT = SDCARD + "/gallery_sample/";

	public static final String IV = ROOT + "/iv/";


	public static List<String> PATH_LIST = new ArrayList<String>();

	static {
		PATH_LIST.add(ROOT);
		PATH_LIST.add(IV);
	}

	public static void init() {
		if (!AppUtil.hasSDCard()) {
			return;
		}

		for (String p : Path.PATH_LIST) {
			File f = new File(p);
			if (!f.exists()) {
				f.mkdirs();
			}
		}
	}


}
