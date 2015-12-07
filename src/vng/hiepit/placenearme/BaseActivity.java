package vng.hiepit.placenearme;

import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

public class BaseActivity extends AppCompatActivity {
	private Toolbar mToolbar;

	protected Toolbar activateToolbar() {
		if (mToolbar == null) {
			mToolbar = (Toolbar) findViewById(R.id.toolbar);
			if (mToolbar != null) {
				setSupportActionBar(mToolbar);
			}
		}
		return mToolbar;
	}
}
