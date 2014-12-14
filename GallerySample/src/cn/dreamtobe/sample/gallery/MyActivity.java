package cn.dreamtobe.sample.gallery;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class MyActivity extends Activity implements View.OnClickListener{
	/**
	 * Called when the activity is first created.
	 */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
	}

	@Override
	public void onClick(View v) {
		if (v.getId() == R.id.horizon_viewpager_btn) {
			startActivity(new Intent(this, DemoHorizontalGalleryActivity.class));
		}
	}
}
